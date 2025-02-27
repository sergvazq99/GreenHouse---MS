package Negocio;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Fabricante.TFabricanteLocal;
import Negocio.Fabricante.TFabricante;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.TInvernadero;
import Negocio.Invernadero.TTiene;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

public class InvernaderoSATest {

	private static InvernaderoSA invernaderoSA;

	private static Transaccion crearTransaccion() {
		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			tManager.newTransaccion();
			return tManager.getTransaccion();
		} catch (Exception e) {
			fail("Error transaccional");
			return null;
		}
	}

	private boolean equals(TInvernadero a, TInvernadero b) {
		if (a == null || b == null) {
			return false;
		}

		return a.getId() == b.getId() && a.getNombre().equals(b.getNombre()) && a.getSustrato().equals(b.getSustrato())
				&& a.getTipo_iluminacion().equals(b.getTipo_iluminacion()) && a.isActivo() == b.isActivo();
	}

	@BeforeClass
	public static void setUp() throws Exception {
		invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();
	}

	@Test
	public void testAltaInvernadero() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvAltaTest", "Tierra", "LED");
		invernadero.setActivo(true);

		Integer resultadoOK1 = invernaderoSA.altaInvernadero(invernadero);
		assertTrue("(Alta Bien) El ID debe ser mayor que 0", resultadoOK1 > 0);

		Integer resultadoKO1 = invernaderoSA.altaInvernadero(invernadero);
		assertTrue("(Alta Mal - Nombre Repetido y Activo) El ID debe ser menor que 0", resultadoKO1 < 0);

		invernaderoSA.bajaInvernadero(resultadoOK1);
		Integer resultadoOK2 = invernaderoSA.altaInvernadero(invernadero);
		assertTrue("(Alta Bien - Reactivacion) El ID debe ser mayor que 0", resultadoOK2 > 0);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, resultadoOK1);
		statement.execute();
		statement.close();

		t.commit();

	}

	@Test
	public void testBajaInvernadero() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvBajaTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		Integer resultadoOK1 = invernaderoSA.bajaInvernadero(invernadero.getId());
		assertTrue("(Baja Bien) El ID debe ser mayor que 0", resultadoOK1 > 0);

		Integer resultadoKO1 = invernaderoSA.bajaInvernadero(invernadero.getId());
		assertTrue("(Baja Mal - Invenadero Dado de Baja) El ID debe ser menor que 0", resultadoKO1 < 0);

		Integer resultadoKO2 = invernaderoSA.bajaInvernadero(invernadero.getId() + 1);
		assertTrue("(Baja Mal - Invernadero no existe) El ID debe ser menor que 0", resultadoKO2 < 0);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, invernadero.getId());
		statement.execute();
		statement.close();

		t.commit();

	}

	@Test
	public void testModificarInvernadero() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvModificarTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		Integer resultadoOK1 = invernaderoSA.modificarInvernadero(invernadero);
		assertTrue("(Modificar Bien) El ID debe ser mayor que 0", resultadoOK1 > 0);

		TInvernadero invernaderoMal = new TInvernadero("InvModificarTestMal", "Tierra", "LED");
		invernaderoMal.setActivo(true);
		invernaderoMal.setId(invernadero.getId() + 1);

		Integer resultadoKO1 = invernaderoSA.modificarInvernadero(invernaderoMal);
		assertTrue("(Modificar Mal - Invenadero No Existe) El ID debe ser menor que 0", resultadoKO1 < 0);

		invernaderoMal.setId(invernaderoSA.altaInvernadero(invernaderoMal));

		invernadero.setNombre(invernaderoMal.getNombre());
		Integer resultadoKO2 = invernaderoSA.modificarInvernadero(invernadero);
		assertTrue("(Modificar Mal - Invernadero con mismo nombre que uno existente) El ID debe ser menor que 0",
				resultadoKO2 < 0);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ? or id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, invernadero.getId());
		statement.setInt(2, invernaderoMal.getId());
		statement.execute();
		statement.close();

		t.commit();

	}

	@Test
	public void testListarInvernadero() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvListarTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		Set<TInvernadero> invernaderos = (Set<TInvernadero>) invernaderoSA.listarInvernadero();
		assertTrue("(Listar Bien) El size debe ser 1", invernaderos.size() >= 1);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, invernadero.getId());
		statement.execute();
		statement.close();

		t.commit();

	}

	@Test
	public void testMostrarInvernaderoPorId() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvMostrarIDTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		TInvernadero invernaderoExiste = invernaderoSA.mostrarInvernaderoPorID(invernadero.getId());
		assertTrue("(Mostrar Por ID Bien) El invernadero debe ser distinto de null",
				invernaderoExiste != null && invernaderoExiste.getId() > 0 && equals(invernaderoExiste, invernadero));

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, invernadero.getId());
		statement.execute();
		statement.close();

		t.commit();

		TInvernadero invernaderoNoExiste = invernaderoSA.mostrarInvernaderoPorID(invernadero.getId());
		assertTrue("(Mostrar Por ID Mal) El id del invernadero debe ser igual a -1", invernaderoNoExiste.getId() == -1);

	}

	@Test
	public void testMostrarInvernaderoPorNombre() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvMostrarNombreTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		TInvernadero invernaderoExiste = invernaderoSA.mostrarInvernaderoPorNombre(invernadero.getNombre());
		assertTrue("(Mostrar Por Nombre Bien) El invernadero debe ser distinto de null",
				invernaderoExiste != null && invernaderoExiste.getId() > 0 && equals(invernaderoExiste, invernadero));

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();
		statement.close();

		t.commit();

		TInvernadero invernaderoNoExiste = invernaderoSA.mostrarInvernaderoPorNombre(invernadero.getNombre());
		assertTrue("(Mostrar Por Nombre Mal) El id del invernadero debe ser igual a -1",
				invernaderoNoExiste.getId() == -1);

	}

	@Test
	public void testListarInvernaderoPorSR() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvMostrarNombreTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		TFabricanteLocal tFabricanteLocal = new TFabricanteLocal();
		tFabricanteLocal.setActivo(true);
		tFabricanteLocal.setCodFabricante("CodTestListarPorSR");
		tFabricanteLocal.setNombre("CodTestListarPorSR");
		tFabricanteLocal.setTelefono("111222333");
		tFabricanteLocal.setImpuesto(11);
		tFabricanteLocal.setSubvencion(11);

		TFabricante fabricante = tFabricanteLocal;
		fabricante.setId(FactoriaNegocio.getInstance().getFabricanteSA().altaFabricante(fabricante));

		TSistemaDeRiego sisRiego = new TSistemaDeRiego();
		sisRiego.setNombre("SisRiegoTestListarPorSR");
		sisRiego.setFrecuencia(1);
		sisRiego.setCantidad_agua(1);
		sisRiego.setPotenciaRiego(1);
		sisRiego.setIdFabricante(fabricante.getId());
		sisRiego.setActivo(true);
		sisRiego.setId(FactoriaNegocio.getInstance().getSistemaDeRiegoSA().altaSisRiego(sisRiego));

		TTiene tiene = new TTiene();
		tiene.setId_Invernadero(invernadero.getId());
		tiene.setId_SistemasDeRiego(sisRiego.getId());

		invernaderoSA.vincularSRInvernadero(tiene.getId_SistemasDeRiego(), tiene.getId_Invernadero());
		Set<TInvernadero> invernaderosBien = (Set<TInvernadero>) invernaderoSA.listarInvernaderoPorSR(sisRiego.getId());
		assertTrue("(Listar Por Sistema de Riego Bien) El size debe ser distinto 0", invernaderosBien.size() > 0);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();

		PreparedStatement statement = c
				.prepareStatement("DELETE FROM sistemas_riego_de_invernadero where id_invernadero = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM sistemas_riego where id = ?");
		statement.setInt(1, sisRiego.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM fabricante_local where id_fabricante = ?");
		statement.setInt(1, fabricante.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM fabricante where id = ?");
		statement.setInt(1, fabricante.getId());
		statement.execute();

		statement.close();

		t.commit();

		Set<TInvernadero> invernaderosMal = (Set<TInvernadero>) invernaderoSA.listarInvernaderoPorSR(sisRiego.getId());
		assertTrue("(Listar Por Sistema de Riego Bien) El size debe ser distinto 0",
				invernaderosMal.size() == 1 && invernaderosMal.iterator().next().getId() == -1);

	}

	@Test
	public void testVincularInvernaderoYSR() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvVincularTest", "Tierra", "LED");
		invernadero.setActivo(true);

		Integer id_inv = invernaderoSA.altaInvernadero(invernadero);
		invernadero.setId(id_inv);

		TFabricanteLocal tFabricanteLocal = new TFabricanteLocal();
		tFabricanteLocal.setActivo(true);
		tFabricanteLocal.setCodFabricante("CodVincularTest");
		tFabricanteLocal.setNombre("NombreVincularTest");
		tFabricanteLocal.setTelefono("111222333");
		tFabricanteLocal.setImpuesto(11);
		tFabricanteLocal.setSubvencion(11);

		TFabricante fabricante = tFabricanteLocal;
		Integer id_fab = FactoriaNegocio.getInstance().getFabricanteSA().altaFabricante(fabricante);
		fabricante.setId(id_fab);

		TSistemaDeRiego sisRiego = new TSistemaDeRiego();
		sisRiego.setNombre("SisRiegoVincularTest");
		sisRiego.setFrecuencia(1);
		sisRiego.setCantidad_agua(1);
		sisRiego.setPotenciaRiego(1);
		sisRiego.setIdFabricante(fabricante.getId());
		sisRiego.setActivo(true);

		Integer id_sr = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().altaSisRiego(sisRiego);
		sisRiego.setId(id_sr);

		Integer exito = invernaderoSA.vincularSRInvernadero(id_sr, id_inv);
		assertTrue("(Vincular Sistema de Riego a Invernadero Bien) El exito debe ser mayor que 0", exito > 0);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();

		PreparedStatement statement = c
				.prepareStatement("DELETE FROM sistemas_riego_de_invernadero where id_invernadero = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM sistemas_riego where id = ?");
		statement.setInt(1, sisRiego.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM fabricante_local where id_fabricante = ?");
		statement.setInt(1, fabricante.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM fabricante where id = ?");
		statement.setInt(1, fabricante.getId());
		statement.execute();

		statement.close();

		t.commit();

		Integer fallo = invernaderoSA.vincularSRInvernadero(id_sr, id_inv);
		assertTrue("(Vincular Sistema de Riego a Invernadero Mal) El exito debe ser menor que 0", fallo < 0);

	}

	@Test
	public void testDesvincularInvernaderoYSR() throws Exception {
		TInvernadero invernadero = new TInvernadero("DesvincularTest", "Tierra", "LED");
		invernadero.setActivo(true);

		Integer id_inv = invernaderoSA.altaInvernadero(invernadero);
		invernadero.setId(id_inv);

		TFabricanteLocal tFabricanteLocal = new TFabricanteLocal();
		tFabricanteLocal.setActivo(true);
		tFabricanteLocal.setCodFabricante("CodDesvincularTest");
		tFabricanteLocal.setNombre("NombreDesvincularTest");
		tFabricanteLocal.setTelefono("111222333");
		tFabricanteLocal.setImpuesto(11);
		tFabricanteLocal.setSubvencion(11);

		TFabricante fabricante = tFabricanteLocal;
		Integer id_fab = FactoriaNegocio.getInstance().getFabricanteSA().altaFabricante(fabricante);
		fabricante.setId(id_fab);

		TSistemaDeRiego sisRiego = new TSistemaDeRiego();
		sisRiego.setNombre("SisRiegoDesvincularTest");
		sisRiego.setFrecuencia(1);
		sisRiego.setCantidad_agua(1);
		sisRiego.setPotenciaRiego(1);
		sisRiego.setIdFabricante(fabricante.getId());
		sisRiego.setActivo(true);

		Integer id_sr = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().altaSisRiego(sisRiego);
		sisRiego.setId(id_sr);

		invernaderoSA.vincularSRInvernadero(id_sr, id_inv);

		Integer exito = invernaderoSA.desvincularSRInvernadero(id_sr, id_inv);
		assertTrue("(Desvincular Sistema de Riego de Invernadero Bien) El exito debe ser mayor que 0", exito > 0);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();

		PreparedStatement statement = c
				.prepareStatement("DELETE FROM sistemas_riego_de_invernadero where id_invernadero = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?");
		statement.setInt(1, invernadero.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM sistemas_riego where id = ?");
		statement.setInt(1, sisRiego.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM fabricante_local where id_fabricante = ?");
		statement.setInt(1, fabricante.getId());
		statement.execute();

		statement = c.prepareStatement("DELETE FROM fabricante where id = ?");
		statement.setInt(1, fabricante.getId());
		statement.execute();

		statement.close();

		t.commit();

		Integer fallo = invernaderoSA.vincularSRInvernadero(id_sr, id_inv);
		assertTrue("(Desvincular Sistema de Riego de Invernadero Mal) El exito debe ser menor que 0", fallo < 0);

	}

	@Test
	public void testcalcularLasTresFechasMasVendidasDeUnInvernadero() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvQueryTest", "Tierra", "LED");
		invernadero.setActivo(true);
		invernadero.setId(invernaderoSA.altaInvernadero(invernadero));

		Set<Date> invernaderos = (Set<Date>) invernaderoSA
				.calcularLasTresFechasMasVendidasDeUnInvernadero(invernadero.getId());
		assertTrue("(Query Bien) El size debe ser >= que 0 o <= 3",
				invernaderos.size() >= 0 && invernaderos.size() <= 3);

		Transaccion t = crearTransaccion();
		t.start();
		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement("DELETE FROM invernadero WHERE id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, invernadero.getId());
		statement.execute();
		statement.close();

		t.commit();

	}
}
