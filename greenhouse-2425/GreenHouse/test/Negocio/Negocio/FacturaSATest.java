package Negocio;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Entrada.EntradaSA;
import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.FacturaSA;
import Negocio.Factura.TCarrito;
import Negocio.Factura.TFactura;
import Negocio.Factura.TFacturaConEntradas;
import Negocio.Factura.TLineaFactura;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.TInvernadero;

public class FacturaSATest {
	
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
	
	private TEntrada getTEntrada(int idInv) {
		TEntrada entrada = new TEntrada();
		entrada.setActivo(true);
		entrada.setFecha(new Date(System.currentTimeMillis()));
		entrada.setPrecio((float) 12);
		entrada.setStock(200);
		entrada.setIdInvernadero(idInv);
		
		return entrada;
	}
	
	private TInvernadero getTInvernadero() {
		TInvernadero invernadero = new TInvernadero();
		Random rand = new Random();
		invernadero.setActivo(true);
		invernadero.setNombre("test" + rand.nextInt(999));
		invernadero.setSustrato("test" + rand.nextInt(999));
		invernadero.setTipo_iluminacion("test" + rand.nextInt(999));
		
		return invernadero;
		
	}

	@Test
	public void testCerrarFactura() {

		FacturaSA facturaSA = FactoriaNegocio.getInstance().getFacturaSA();
		EntradaSA entradaSA = FactoriaNegocio.getInstance().getEntradaSA();
		InvernaderoSA invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();

		try {
			//poblamos BBDD
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEntrada = entradaSA.altaEntrada(entrada);
			
			//Creamos datos
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			TCarrito carrito = new TCarrito();
			carrito.setFactura(datos);
			TLineaFactura lineaFactura = new TLineaFactura();
			lineaFactura.setidEntrada(idEntrada);
			lineaFactura.setCantidad(2);
			lineaFactura.setPrecio((float) 12);
			
			Set<TLineaFactura> lista = new HashSet<TLineaFactura>();
			lista.add(lineaFactura);
			carrito.setLineaFactura(lista);
			
			//Damos de alta la factura
			int result = facturaSA.cerrarFactura(carrito);
			if(result < 0) {
				fail("Error al realizar la transacción. resultado: " + result);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testDevolverFactura() {
		
		FacturaSA facturaSA = FactoriaNegocio.getInstance().getFacturaSA();
		EntradaSA entradaSA = FactoriaNegocio.getInstance().getEntradaSA();
		InvernaderoSA invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();

		try {
			//poblamos BBDD
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEntrada = entradaSA.altaEntrada(entrada);
			
			//Creamos datos
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			TCarrito carrito = new TCarrito();
			carrito.setFactura(datos);
			TLineaFactura lineaFactura = new TLineaFactura();
			lineaFactura.setidEntrada(idEntrada);
			lineaFactura.setCantidad(2);
			lineaFactura.setPrecio((float) 12);
			
			Set<TLineaFactura> lista = new HashSet<TLineaFactura>();
			lista.add(lineaFactura);
			carrito.setLineaFactura(lista);
			
			//Damos de alta la factura
			int idFactura = facturaSA.cerrarFactura(carrito);
			
			lineaFactura.setidFactura(idFactura);
			
			//Damos de baja la factura
			int result = facturaSA.devolverFactura(lineaFactura);
			if(result < 0) {
				fail("Error al realizar la transacción. resultado: " + result);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testModificarFactura() {

		FacturaSA facturaSA = FactoriaNegocio.getInstance().getFacturaSA();
		EntradaSA entradaSA = FactoriaNegocio.getInstance().getEntradaSA();
		InvernaderoSA invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();

		try {
			//poblamos BBDD
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEntrada = entradaSA.altaEntrada(entrada);
			
			//Creamos datos
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			TCarrito carrito = new TCarrito();
			carrito.setFactura(datos);
			TLineaFactura lineaFactura = new TLineaFactura();
			lineaFactura.setidEntrada(idEntrada);
			lineaFactura.setCantidad(2);
			lineaFactura.setPrecio((float) 12);
			
			Set<TLineaFactura> lista = new HashSet<TLineaFactura>();
			lista.add(lineaFactura);
			carrito.setLineaFactura(lista);
			
			//Damos de alta la factura
			int idFactura = facturaSA.cerrarFactura(carrito);
			
			datos.setid(idFactura);
			datos.setPrecioTotal((float) 321);
			
			//modificamos la factura
			int result = facturaSA.modificarFactura(datos);
			if(result < 0) {
				fail("Error al realizar la transacción. resultado: " + result);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testMostrarFactura() {

		FacturaSA facturaSA = FactoriaNegocio.getInstance().getFacturaSA();
		EntradaSA entradaSA = FactoriaNegocio.getInstance().getEntradaSA();
		InvernaderoSA invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();

		try {
			//poblamos BBDD
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEntrada = entradaSA.altaEntrada(entrada);
			
			//Creamos datos
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			TCarrito carrito = new TCarrito();
			carrito.setFactura(datos);
			TLineaFactura lineaFactura = new TLineaFactura();
			lineaFactura.setidEntrada(idEntrada);
			lineaFactura.setCantidad(2);
			lineaFactura.setPrecio((float) 12);
			
			Set<TLineaFactura> lista = new HashSet<TLineaFactura>();
			lista.add(lineaFactura);
			carrito.setLineaFactura(lista);
			
			//Damos de alta la factura
			int idFactura = facturaSA.cerrarFactura(carrito);
			
			//modificamos la factura
			TFacturaConEntradas result = facturaSA.mostrarFacturaPorID(idFactura);
			if(result == null) {
				fail("Error al realizar la transacción. resultado: " + result);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}
	}

	@Test
	public void testListarFacturas() {

		FacturaSA facturaSA = FactoriaNegocio.getInstance().getFacturaSA();
		EntradaSA entradaSA = FactoriaNegocio.getInstance().getEntradaSA();
		InvernaderoSA invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();

		try {
			//poblamos BBDD
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEntrada = entradaSA.altaEntrada(entrada);
			
			//Creamos datos
			TFactura datos = new TFactura();
			datos.setPrecioTotal((float)123);
			datos.setFechaCompra(new java.util.Date());
			datos.setActivo(true);
			TCarrito carrito = new TCarrito();
			carrito.setFactura(datos);
			TLineaFactura lineaFactura = new TLineaFactura();
			lineaFactura.setidEntrada(idEntrada);
			lineaFactura.setCantidad(2);
			lineaFactura.setPrecio((float) 12);
			
			Set<TLineaFactura> lista = new HashSet<TLineaFactura>();
			lista.add(lineaFactura);
			carrito.setLineaFactura(lista);
			
			//Damos de alta la factura
			int idFactura = facturaSA.cerrarFactura(carrito);
			
			//modificamos la factura
			Set<TFactura> result = facturaSA.listarFacturas();
			if(result.size() == 0) {
				fail("Error al realizar la transacción. resultado: " + result);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			fail("Error desconocido");
		}

	}
}
