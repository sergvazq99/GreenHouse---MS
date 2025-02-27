package Integracion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Invernadero.TieneDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

import Negocio.Fabricante.TFabricante;
import Negocio.Invernadero.TInvernadero;
import Negocio.Invernadero.TTiene;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

public class TieneDAOTest {

	private static TieneDAO tieneDAO;

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

	private boolean equals(TTiene a, TTiene b) {
		if (a == null || b == null) {
			return false;
		}

		return a.getId_Invernadero() == b.getId_Invernadero() && a.getId_SistemasDeRiego() == b.getId_SistemasDeRiego();
	}

	@BeforeClass
	public static void setUp() throws Exception {
		t.start();
		tieneDAO = FactoriaIntegracion.getInstance().getDaoTiene();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		t.rollback();
	}

	@Test
	public void testVincularSRaInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvTieneTest1", "Tierra", "LED");

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
		fabricante.setNombre("FabTieneTest1");
		fabricante.setCodFabricante("CodTieneTest1");
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
		sisRiego.setNombre("SisRiegoTieneTest1");
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

		// Llama a la función altaInvernadero
		TTiene tiene = new TTiene();
		tiene.setId_Invernadero(id_inv);
		tiene.setId_SistemasDeRiego(id_sr);

		Integer exito = tieneDAO.vincularInvernaderoConSisRiego(tiene);

		// Verifica que el ID retornado sea válido
		assertTrue("El ID debe ser mayor que 0", exito > 0);

	}

	@Test
	public void testDesvincularSRdeInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvTieneTest2", "Tierra", "LED");

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
		fabricante.setNombre("FabricanteTieneTest2");
		fabricante.setCodFabricante("CodTieneTest2");
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
		sisRiego.setNombre("SisRiegoTieneTest2");
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

		// Llama a la función altaInvernadero
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

		Integer exito = tieneDAO.desvincularInvernaderoConSisRiego(tiene);

		// Verifica que el ID retornado sea válido
		assertTrue("El ID debe ser mayor que 0", exito > 0);

	}

	@Test
	public void testListarTiene() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvTieneTest3", "Tierra", "LED");

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
		fabricante.setNombre("FabricanteTieneTest3");
		fabricante.setCodFabricante("CodTieneTest3");
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
		sisRiego.setNombre("SisRiegoTieneTest3");
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

		// Llama a la función altaInvernadero
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

		Set<TTiene> lista = tieneDAO.listarTiene();
		// Verifica que el ID retornado sea válido
		assertTrue("El ID debe ser mayor que 0", lista.size() > 0);

	}

	@Test
	public void testMostrarTiene() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvTieneTest4", "Tierra", "LED");

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
		fabricante.setNombre("FabricanteTieneTest4");
		fabricante.setCodFabricante("CodTieneTest4");
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
		sisRiego.setNombre("SisRiegoTieneTest4");
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

		// Llama a la función altaInvernadero
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

		TTiene exito = tieneDAO.mostrarTiene(tiene);
		// Verifica que el ID retornado sea válido
		assertTrue("El ID debe ser mayor que 0", equals(tiene, exito));
	}

	@Test
	public void testListarTienePorInvernadero() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvTieneTest5", "Tierra", "LED");

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
		fabricante.setNombre("FabricanteTieneTest5");
		fabricante.setCodFabricante("CodTieneTest5");
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
		sisRiego.setNombre("SisRiegoTieneTest5");
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

		// Llama a la función altaInvernadero
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

		Set<TTiene> lista = tieneDAO.mostrarTienePorInvernadero(id_inv);
		// Verifica que el ID retornado sea válido
		assertTrue("El ID debe ser mayor que 0", lista.size() > 0);

	}

	@Test
	public void testListarTienePorSistemaDeRiego() throws Exception {

		TInvernadero invernadero = new TInvernadero("InvTieneTest6", "Tierra", "LED");

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
		fabricante.setNombre("FabricanteTieneTest6");
		fabricante.setCodFabricante("CodTieneTest6");
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
		sisRiego.setNombre("SisRiegoTieneTest6");
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

		// Llama a la función altaInvernadero
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

		Set<TTiene> lista = tieneDAO.mostrarTienePorSisRiego(id_sr);
		// Verifica que el ID retornado sea válido
		assertTrue("El ID debe ser mayor que 0", lista.size() > 0);

	}
}
