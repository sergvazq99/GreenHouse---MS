package Integracion;

import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Factura.FacturaDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Factura.TFactura;

public class FacturaDAOTest {
		
	private boolean equals(TFactura e1, TFactura e2) {
		if (e1 == null || e2 == null)
			return false;

		return e1.getid().equals(e2.getid()) && e1.getFechaCompra().equals(e2.getFechaCompra())
				&& e1.getPrecioTotal().equals(e2.getPrecioTotal()) && (e1.getActivo() == (e2.getActivo()));
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
	public void testCerrarFactura() {

		FacturaDAO daoFactura = FactoriaIntegracion.getInstance().getFacturaDAO();
		Transaccion t;
		t = crearTransaccion();

		try {
			t.start();
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			int result = daoFactura.cerrarFactura(datos);
			if(result < 0) {
				t.rollback();
				fail("Error al realizar la transacción. resultado: " + result);
			}

			t.rollback();
		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testDevolverFactura() {
		
		FacturaDAO daoFactura = FactoriaIntegracion.getInstance().getFacturaDAO();
		
		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			int nuevaFactura = daoFactura.cerrarFactura(datos);
			if(nuevaFactura < 0) {
				t.rollback();
				fail("Error al crear el dato de prueba para el test. resultado: " + nuevaFactura);
			}
			int result = daoFactura.devolverFactura(nuevaFactura);
			if(result < 0) {
				t.rollback();
				fail("Error al realizar la transacción. resultado: " + result);
			}

			t.rollback();

		} catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testModificarFactura() {

		FacturaDAO daoFactura = FactoriaIntegracion.getInstance().getFacturaDAO();

		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			int nuevaFactura = daoFactura.cerrarFactura(datos);
			if(nuevaFactura < 0) {
				t.rollback();
				fail("Error al crear el dato de prueba para el test. resultado: " + nuevaFactura);
			}
			TFactura facturaOriginal = daoFactura.mostrarFactura(nuevaFactura);
			if(facturaOriginal == null) {
				t.rollback();
				fail("Error al obtener el dato de prueba de la base de datos.");
			}
			TFactura facturaCopia = facturaOriginal;
			facturaCopia.setPrecioTotal((float)321);
			datos.setid(nuevaFactura);
			int result = daoFactura.modificarFactura(facturaCopia);
			if(result >= 0) {
				facturaOriginal = daoFactura.mostrarFactura(nuevaFactura);
				if(facturaOriginal == null) {
					t.rollback();
					fail("Error al obtener el dato de prueba de la base de datos.");
				} else if(!equals(facturaOriginal, facturaCopia)) {
					t.rollback();
					fail("Error al obtener el dato de prueba de la base de datos.");
				}
			} else {
				t.rollback();
				fail("Error al realizar la transacción. resultado: " + result);
			}
			
			t.rollback();

		} catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testMostrarFactura() {

		FacturaDAO daoFactura = FactoriaIntegracion.getInstance().getFacturaDAO();
		Transaccion t;
		t = crearTransaccion();

		try {
			t.start();
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			int nuevaFactura = daoFactura.cerrarFactura(datos);
			if(nuevaFactura < 0) {
				t.rollback();
				fail("Error al crear el dato de prueba para el test. resultado: " + nuevaFactura);
			}
			TFactura facturaOriginal = daoFactura.mostrarFactura(nuevaFactura);
			if(facturaOriginal == null) {
				t.rollback();
				fail("Error al obtener el dato de prueba de la base de datos.");
			}
			if(!facturaOriginal.getPrecioTotal().equals(datos.getPrecioTotal())) {
				t.rollback();
				fail("Error al realizar la transacción. los precios totales no coinciden");
			}

			t.rollback();
		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testListarFacturas() {
		FacturaDAO daoFactura = FactoriaIntegracion.getInstance().getFacturaDAO();

		try {
			Transaccion t;
			t = crearTransaccion();
			t.start();
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			int nuevaFactura = daoFactura.cerrarFactura(datos);
			if(nuevaFactura < 0) {
				t.rollback();
				fail("Error al crear el primer dato de prueba para el test. resultado: " + nuevaFactura);
			}
			datos.setPrecioTotal((float)321);
			nuevaFactura = daoFactura.cerrarFactura(datos);
			if(nuevaFactura < 0) {
				t.rollback();
				fail("Error al crear el segundo dato de prueba para el test. resultado: " + nuevaFactura);
			}
			
			Set<TFactura> result = daoFactura.listarFactura();
			
			if(result == null || result.size() < 2) {
				t.rollback();
				fail("Fallo al realizar la transacción, la lista es nula o no ha devuelto todos los datos");
			}

			t.rollback();

		} catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}

	}
}
