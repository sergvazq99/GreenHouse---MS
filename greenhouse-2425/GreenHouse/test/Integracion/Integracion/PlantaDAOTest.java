package Integracion;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Set;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Planta.PlantaDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

import Negocio.Invernadero.TInvernadero;
import Negocio.Planta.TPlanta;
import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PlantaDAOTest {
	
	private boolean equals(TPlanta p1, TPlanta p2) {
		if (p1 == null || p2 == null)
			return false;

		if(p1 instanceof TPlantaFrutal && p1 instanceof TPlantaFrutal){	
			
			return p1.get_id().equals(p2.get_id())
					&& p1.getActivo().equals(p2.getActivo()) 
					&& p1.get_id_invernadero().equals(p2.get_id_invernadero())
					&& p1.get_nombre().equals(p2.get_nombre())
					&& p1.get_nombre_cientifico().equals(p2.get_nombre_cientifico())
					&& p1.get_tipo().equals(p2.get_tipo())
					&& ((TPlantaFrutal)p1).get_maduracion().equals(((TPlantaFrutal)p2).get_maduracion())
					&& ((TPlantaFrutal)p1).get_nombre_fruta().equals(((TPlantaFrutal)p2).get_nombre_fruta());
			
			
		}
		else if(p1 instanceof TPlantaNoFrutal && p1 instanceof TPlantaNoFrutal){	
			
			return p1.get_id().equals(p2.get_id())
					&& p1.getActivo().equals(p2.getActivo()) 
					&& p1.get_id_invernadero().equals(p2.get_id_invernadero())
					&& p1.get_nombre().equals(p2.get_nombre())
					&& p1.get_nombre_cientifico().equals(p2.get_nombre_cientifico())
					&& p1.get_tipo().equals(p2.get_tipo())
					&& ((TPlantaNoFrutal)p1).get_tipo_hoja().equals(((TPlantaNoFrutal)p2).get_tipo_hoja());
			
			
		}
		
		return false;

	}

	private Transaccion crearTransaccion() {
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			tm.newTransaccion();
			Transaccion t = tm.getTransaccion();
			return t;
		} catch (Exception e) {
			fail("Error transaccional");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Test
	public void A_alta() {

		PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
		InvernaderoDAO daoInvernadero = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		Transaccion t;
		t = crearTransaccion();

		try {
			t.start();

			Connection c = (Connection) t.getResource();

			// Desactivar las comprobaciones de claves foráneas, luego las vuelvo a activar,
			// solo para poder truncar todas las tablas implicadas
			Statement statement = c.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");

			PreparedStatement ps1 = c.prepareStatement("TRUNCATE planta_frutal");
			ps1.executeUpdate();
			PreparedStatement ps3 = c.prepareStatement("TRUNCATE planta_no_frutal");
			ps3.executeUpdate();
			PreparedStatement ps = c.prepareStatement("TRUNCATE planta");
			ps.executeUpdate();
			PreparedStatement ps2 = c.prepareStatement("TRUNCATE invernadero");
			ps2.executeUpdate();

			ps1.close();
			ps3.close();
			ps.close();
			ps2.close();

			// Volver a activar las comprobaciones de claves foráneas
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
			statement.close(); // Cerrar el Statement

			// id, sustrato, nombre, tipo iluminación, activo
			TInvernadero inv = new TInvernadero(1, "inv1", "abono", "artificial", true);
			inv.setId(daoInvernadero.altaInvernadero(inv));


			TPlantaNoFrutal p1 = new TPlantaNoFrutal("UNOno", "UNOno", 1, inv.getId(), "UNOno");

			if (daoPlanta.altaPlanta(p1) < 0) {
				fail("No se ha creado correctamente la planta");
			}

			t.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void B_baja() {

		try {
			PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
			Transaccion t;
			t = crearTransaccion();
			t.start();

			daoPlanta.bajaPlanta(1);
			TPlanta p = daoPlanta.mostrarPorId(1);
			if (p.getActivo()) {
				fail("No se ha eliminado correctamente la planta");
			}
			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void C_modificar() {
		PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
		InvernaderoDAO daoInvernadero = FactoriaIntegracion.getInstance().getInvernaderoDAO();

		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			Connection c = (Connection) t.getResource();

			// Desactivar las comprobaciones de claves foráneas, luego las vuelvo a activar,
			// solo para poder truncar todas las tablas implicadas
			Statement statement = c.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");

			PreparedStatement ps1 = c.prepareStatement("TRUNCATE planta_frutal");
			ps1.executeUpdate();
			PreparedStatement ps3 = c.prepareStatement("TRUNCATE planta_no_frutal");
			ps3.executeUpdate();
			PreparedStatement ps = c.prepareStatement("TRUNCATE planta");
			ps.executeUpdate();
			PreparedStatement ps2 = c.prepareStatement("TRUNCATE invernadero");
			ps2.executeUpdate();

			ps1.close();
			ps3.close();
			ps.close();
			ps2.close();

			// Volver a activar las comprobaciones de claves foráneas
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
			statement.close(); // Cerrar el Statement

//			LocalDate localDate = LocalDate.of(2012, 12, 12);
//			Date sqlDate = Date.valueOf(localDate); // Convertimos LocalDate a java.sql.Date
			TInvernadero inv = new TInvernadero(1, "inv1", "abono", "artificial", true);
			inv.setId(daoInvernadero.altaInvernadero(inv));

			TPlantaNoFrutal p2 = new TPlantaNoFrutal("UNOmodificado", "UNOmodificado", 1, inv.getId(), "UNOmodificado");
			TPlantaNoFrutal p1 = new TPlantaNoFrutal("UNOno", "UNOno", 1, inv.getId(), "UNOno");
			
			p2.set_id(daoPlanta.altaPlanta(p1));
			
		
			p1.set_nombre(p2.get_nombre());
			p1.set_nombre_cientifico(p2.get_nombre_cientifico());
			p1.set_tipo_hoja(p2.get_tipo_hoja());
			
			daoPlanta.modificarPlanta(p1);
					
			if (this.equals(daoPlanta.mostrarPorId(p2.get_id()), p2)) {
				fail("No se ha modificado correctamente la planta");
			}

			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void D_mostrar() {

		PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
		InvernaderoDAO daoInvernadero = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		Transaccion t;
		

		try {
			
			t = crearTransaccion();
			t.start();

			Connection c = (Connection) t.getResource();

			// Desactivar las comprobaciones de claves foráneas, luego las vuelvo a activar,
			// solo para poder truncar todas las tablas implicadas
			Statement statement = c.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");

			PreparedStatement ps1 = c.prepareStatement("TRUNCATE planta_frutal");
			ps1.executeUpdate();
			PreparedStatement ps3 = c.prepareStatement("TRUNCATE planta_no_frutal");
			ps3.executeUpdate();
			PreparedStatement ps = c.prepareStatement("TRUNCATE planta");
			ps.executeUpdate();
			PreparedStatement ps2 = c.prepareStatement("TRUNCATE invernadero");
			ps2.executeUpdate();

			ps1.close();
			ps3.close();
			ps.close();
			ps2.close();

			// Volver a activar las comprobaciones de claves foráneas
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
			statement.close(); // Cerrar el Statement

			TInvernadero inv = new TInvernadero(1, "inv1", "abono", "artificial", true);
			inv.setId(daoInvernadero.altaInvernadero(inv));


			TPlantaNoFrutal p1 = new TPlantaNoFrutal("UNOno", "UNOno", 1, inv.getId(), "UNOno");
			int id = daoPlanta.altaPlanta(p1);
			p1.set_id(id);
			TPlantaNoFrutal p2 = (TPlantaNoFrutal) daoPlanta.mostrarPorId(id);
			
			
			
			if (!equals(p1, p2)) {
				fail("No se ha leido correctamente la entrada");
			}

			t.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void E_listar_plantas() {
		PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
		InvernaderoDAO daoInvernadero = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			Connection c = (Connection) t.getResource();

			// Desactivar las comprobaciones de claves foráneas, luego las vuelvo a activar,
			// solo para poder truncar todas las tablas implicadas
			Statement statement = c.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");

			PreparedStatement ps1 = c.prepareStatement("TRUNCATE planta_frutal");
			ps1.executeUpdate();
			PreparedStatement ps3 = c.prepareStatement("TRUNCATE planta_no_frutal");
			ps3.executeUpdate();
			PreparedStatement ps = c.prepareStatement("TRUNCATE planta");
			ps.executeUpdate();
			PreparedStatement ps2 = c.prepareStatement("TRUNCATE invernadero");
			ps2.executeUpdate();

			ps1.close();
			ps3.close();
			ps.close();
			ps2.close();

			// Volver a activar las comprobaciones de claves foráneas
			statement.execute("SET FOREIGN_KEY_CHECKS = 1");
			statement.close(); // Cerrar el Statement
			
			
			
			TInvernadero inv = new TInvernadero(1, "inv1", "abono", "artificial", true);
			inv.setId(daoInvernadero.altaInvernadero(inv));
			
			TPlantaFrutal p1 = new TPlantaFrutal("UNO", "UNO", 0, inv.getId(), "UNO", "UNO");
			TPlantaNoFrutal p2 = new TPlantaNoFrutal("UNOno", "UNOno", 1, inv.getId(), "UNOno");
			p1.set_id(daoPlanta.altaPlanta(p1));
			p2.set_id(daoPlanta.altaPlanta(p2));
			
			
			Set<TPlanta> lista = daoPlanta.listarPlantas();

			for (TPlanta e : lista) {
				if (!equals(e, p1) && !equals(e, p2))
					fail("Fallo en la lista");
			}

			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void F_listar_plantas_por_invernadero() {
		PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
		InvernaderoDAO daoInvernadero = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			Connection c = (Connection) t.getResource();

			// Desactivar las comprobaciones de claves foráneas, luego las vuelvo a activar,
			// solo para poder truncar todas las tablas implicadas
			Statement statement = c.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");

			PreparedStatement ps1 = c.prepareStatement("TRUNCATE planta_frutal");
			ps1.executeUpdate();
			PreparedStatement ps3 = c.prepareStatement("TRUNCATE planta_no_frutal");
			ps3.executeUpdate();
			PreparedStatement ps = c.prepareStatement("TRUNCATE planta");
			ps.executeUpdate();
			PreparedStatement ps2 = c.prepareStatement("TRUNCATE invernadero");
			ps2.executeUpdate();

			ps1.close();
			ps3.close();
			ps.close();
			ps2.close();
			
			// creo un invernadero
			
			TInvernadero inv = new TInvernadero(1, "inv1", "abono", "artificial", true);
			inv.setId(daoInvernadero.altaInvernadero(inv));
			
			TPlantaFrutal p1 = new TPlantaFrutal("UNO", "UNO", 0, inv.getId(), "UNO", "UNO");
			TPlantaNoFrutal p2 = new TPlantaNoFrutal("UNOno", "UNOno", 1, inv.getId(), "UNOno");
			p1.set_id(daoPlanta.altaPlanta(p1));
			p2.set_id(daoPlanta.altaPlanta(p2));
			
			Set<TPlanta> lista = daoPlanta.MostrarPorInvernadero(inv.getId());

			for (TPlanta e : lista) {
				if (!equals(e, p1) && !equals(e, p2))
					fail("Fallo en la lista");
			}

			
			t.commit();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void F_listar_plantas_por_tipo() {
		PlantaDAO daoPlanta = FactoriaIntegracion.getInstance().getPlantaDAO();
		InvernaderoDAO daoInvernadero = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			Connection c = (Connection) t.getResource();

			// Desactivar las comprobaciones de claves foráneas, luego las vuelvo a activar,
			// solo para poder truncar todas las tablas implicadas
			Statement statement = c.createStatement();
			statement.execute("SET FOREIGN_KEY_CHECKS = 0");

			PreparedStatement ps1 = c.prepareStatement("TRUNCATE planta_frutal");
			ps1.executeUpdate();
			PreparedStatement ps3 = c.prepareStatement("TRUNCATE planta_no_frutal");
			ps3.executeUpdate();
			PreparedStatement ps = c.prepareStatement("TRUNCATE planta");
			ps.executeUpdate();
			PreparedStatement ps2 = c.prepareStatement("TRUNCATE invernadero");
			ps2.executeUpdate();

			ps1.close();
			ps3.close();
			ps.close();
			ps2.close();
			
			// creo un invernadero
			
			TInvernadero inv = new TInvernadero(1, "inv1", "abono", "artificial", true);
			inv.setId(daoInvernadero.altaInvernadero(inv));
			
			TPlantaFrutal p1 = new TPlantaFrutal("UNO", "UNO", 0, inv.getId(), "UNO", "UNO");
			TPlantaNoFrutal p2 = new TPlantaNoFrutal("UNOno", "UNOno", 1, inv.getId(), "UNOno");
			p1.set_id(daoPlanta.altaPlanta(p1));
			p2.set_id(daoPlanta.altaPlanta(p2));
			
			
			Set<TPlanta> lista = daoPlanta.mostrarPorTipo(0);

			for (TPlanta e : lista) {
				if (!equals(e, p1))
					fail("Fallo en la lista");
			}
			
			lista = daoPlanta.mostrarPorTipo(1);
			
			for (TPlanta e : lista) {
				if (!equals(e, p2))
					fail("Fallo en la lista");
			}
			
			t.commit();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
