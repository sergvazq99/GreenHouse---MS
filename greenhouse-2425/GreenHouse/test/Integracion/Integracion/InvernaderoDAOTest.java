package Integracion;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Fabricante.TFabricante;
import Negocio.Invernadero.TInvernadero;
import Negocio.Invernadero.TTiene;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

public class InvernaderoDAOTest {

	private static InvernaderoDAO invernaderoDAO;

	private static Transaccion t = crearTransaccion();

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
		t.start();
		invernaderoDAO = FactoriaIntegracion.getInstance().getInvernaderoDAO();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		t.rollback();
	}

	@Test
	public void testAltaInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvAltaTest", "Tierra", "LED");

		Integer resultado = invernaderoDAO.altaInvernadero(invernadero);

		assertTrue("El ID debe ser mayor que 0", resultado > 0);

	}

	@Test
	public void testBajaInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvBajaTest", "Tierra", "LED");

		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement(
				"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, invernadero.getNombre());
		statement.setString(2, invernadero.getSustrato());
		statement.setString(3, invernadero.getTipo_iluminacion());
		statement.setBoolean(4, true);
		statement.executeUpdate();

		ResultSet result = statement.getGeneratedKeys();

		int id_baja = 0;
		if (result.next()) {
			id_baja = result.getInt(1);
		}

		result.close();
		statement.close();

		Integer resultado = invernaderoDAO.bajaInvernadero(id_baja);

		assertTrue("El ID debe ser mayor que 0", resultado > 0);

	}

	@Test
	public void testModificarInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvModificarTest", "Tierra", "LED");

		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement(
				"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, invernadero.getNombre());
		statement.setString(2, invernadero.getSustrato());
		statement.setString(3, invernadero.getTipo_iluminacion());
		statement.setBoolean(4, true);
		statement.executeUpdate();

		ResultSet result = statement.getGeneratedKeys();

		int id_modificar = 0;
		if (result.next()) {
			id_modificar = result.getInt(1);
		}

		result.close();
		statement.close();

		TInvernadero invernaderoModificado = new TInvernadero("InvModificarTest", "Tierra", "LED");
		invernaderoModificado.setId(id_modificar);
		invernaderoModificado.setActivo(true);

		Integer resultado = invernaderoDAO.modificarInvernadero(invernaderoModificado);

		assertTrue("El ID debe ser mayor que 0", resultado > 0);

	}

	@Test
	public void testMostrarPorIDInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvMostrarIDTest", "Tierra", "LED");

		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement(
				"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, invernadero.getNombre());
		statement.setString(2, invernadero.getSustrato());
		statement.setString(3, invernadero.getTipo_iluminacion());
		statement.setBoolean(4, true);
		statement.executeUpdate();

		ResultSet result = statement.getGeneratedKeys();

		int id_mostrar = 0;
		if (result.next()) {
			id_mostrar = result.getInt(1);
		}

		result.close();
		statement.close();

		invernadero.setId(id_mostrar);
		invernadero.setActivo(true);

		TInvernadero invernaderoLeido = invernaderoDAO.mostrarInvernaderoPorID(id_mostrar);

		assertTrue("Invernadero e InvernaderoLeido deben ser Iguales", equals(invernadero, invernaderoLeido));

	}

	@Test
	public void testMostrarPorNombreInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvMostrarNameTest", "Tierra", "LED");

		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement(
				"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, invernadero.getNombre());
		statement.setString(2, invernadero.getSustrato());
		statement.setString(3, invernadero.getTipo_iluminacion());
		statement.setBoolean(4, true);
		statement.executeUpdate();

		ResultSet result = statement.getGeneratedKeys();

		int id_mostrar = 0;
		if (result.next()) {
			id_mostrar = result.getInt(1);
		}

		result.close();
		statement.close();

		invernadero.setId(id_mostrar);
		invernadero.setActivo(true);
		String nombre_mostrar = invernadero.getNombre();

		TInvernadero invernaderoLeido = invernaderoDAO.mostrarInvernaderoPorNombre(nombre_mostrar);

		assertTrue("Invernadero e InvernaderoLeido deben ser Iguales", equals(invernadero, invernaderoLeido));

	}

	@Test
	public void testListarInvernadero() throws Exception {
		TInvernadero invernadero = new TInvernadero("InvListarTest", "Tierra", "LED");

		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement(
				"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, invernadero.getNombre());
		statement.setString(2, invernadero.getSustrato());
		statement.setString(3, invernadero.getTipo_iluminacion());
		statement.setBoolean(4, true);
		statement.executeUpdate();

		statement.close();

		Set<TInvernadero> lista = invernaderoDAO.listarInvernadero();

		assertTrue("La Lista debe contener al menos 1 Invernadero", lista.size() > 0);

	}

	@Test
	public void testListarInvernaderoPorSR() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvListarSRTest", "Tierra", "LED");

		Connection c = (Connection) t.getResource();
		PreparedStatement statement = c.prepareStatement(
				"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, invernadero.getNombre());
		statement.setString(2, invernadero.getSustrato());
		statement.setString(3, invernadero.getTipo_iluminacion());
		statement.setBoolean(4, true);
		statement.executeUpdate();

		ResultSet result = statement.getGeneratedKeys();

		int id_inv = 0;

		if (result.next()) {
			id_inv = result.getInt(1);
		}

		result.close();
		statement.close();

		TFabricante fabricante = new TFabricante();
		fabricante.setNombre("FabricanteTest1");
		fabricante.setCodFabricante("CodTest1");
		fabricante.setTelefono("111222333");

		Connection c1 = (Connection) t.getResource();
		PreparedStatement statement1 = c1.prepareStatement(
				"INSERT INTO fabricante(cod_fabricante, nombre, telefono, activo) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement1.setString(1, fabricante.getCodFabricante());
		statement1.setString(2, fabricante.getNombre());
		statement1.setString(3, fabricante.getTelefono());
		statement1.setBoolean(4, true);
		statement1.executeUpdate();

		ResultSet result1 = statement1.getGeneratedKeys();

		int id_fab = 0;

		if (result1.next()) {
			id_fab = result1.getInt(1);
		}

		result1.close();
		statement1.close();

		TSistemaDeRiego sisRiego = new TSistemaDeRiego();
		sisRiego.setNombre("SisRiegoTest1");
		sisRiego.setPotenciaRiego(1);
		sisRiego.setFrecuencia(10);
		sisRiego.setCantidad_agua(10);

		int id_sr = 0;

		Connection c2 = (Connection) t.getResource();
		PreparedStatement statement2 = c2.prepareStatement(
				"INSERT INTO sistemas_riego(id_fabricante, nombre, potencia_riego, frecuencia, cantidad_agua, activo) VALUES (?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement2.setInt(1, id_fab);
		statement2.setString(2, sisRiego.getNombre());
		statement2.setInt(3, sisRiego.getPotenciaRiego());
		statement2.setInt(4, sisRiego.getFrecuencia());
		statement2.setInt(5, sisRiego.getCantidad_agua());
		statement2.setBoolean(6, true);

		statement2.executeUpdate();

		ResultSet result2 = statement2.getGeneratedKeys();

		if (result2.next()) {
			id_sr = result2.getInt(1);
		}

		result2.close();
		statement2.close();

		TTiene tiene = new TTiene();
		tiene.setId_Invernadero(id_inv);
		tiene.setId_SistemasDeRiego(id_sr);

		Connection c3 = (Connection) t.getResource();
		PreparedStatement statement3 = c3.prepareStatement(
				"INSERT INTO sistemas_riego_de_invernadero(id_invernadero, id_sistema_riego) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement3.setInt(1, id_inv);
		statement3.setInt(2, id_sr);

		statement3.executeUpdate();

		statement3.close();

		Set<TInvernadero> lista = invernaderoDAO.listarInvernaderoPorSR(id_sr);

		assertTrue("La Lista debe contener al menos 1 Invernadero", lista.size() > 0);

	}
}
