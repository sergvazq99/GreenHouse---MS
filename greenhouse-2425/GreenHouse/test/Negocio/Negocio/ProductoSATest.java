package Negocio;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCajaSA;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.MarcaJPA.Marca;
import Negocio.MarcaJPA.MarcaSA;
import Negocio.MarcaJPA.TMarca;
import Negocio.ProductoJPA.Producto;
import Negocio.ProductoJPA.ProductoSA;
import Negocio.ProductoJPA.ProductoSouvenirs;
import Negocio.ProductoJPA.TProducto;
import Negocio.ProductoJPA.TProductoAlimentacion;
import Negocio.ProductoJPA.TProductoSouvenirs;
import Negocio.TurnoJPA.TTurno;
import Negocio.TurnoJPA.Turno;
import Negocio.TurnoJPA.TurnoSA;
import Negocio.VentaJPA.TCarrito;
import Negocio.VentaJPA.TLineaVenta;
import Negocio.VentaJPA.TVenta;
import Negocio.VentaJPA.VentaSA;

public class ProductoSATest {
	private EntityManager em;
	private EmpleadoDeCajaSA empleadoDeCajaSA;
	private TurnoSA turnoSA;
	private VentaSA ventaSA;
	private MarcaSA marcaSA;
	private ProductoSA productoSA;
	private static Random random;
	
	@Before
	public void setUp() {
		em = EMFSingleton.getInstance().getEMF().createEntityManager();
		
		empleadoDeCajaSA = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA();
		turnoSA = FactoriaNegocio.getInstance().getTurnoJPA();
		ventaSA = FactoriaNegocio.getInstance().getVentaSA();
		marcaSA = FactoriaNegocio.getInstance().getMarcaJPA();
		productoSA = FactoriaNegocio.getInstance().getProductoJPA();
		random = new Random();
	}

	@After
	public void tearDown() {

		if (em.isOpen()) {
			em.close();
		}
	}
	
	
	@Test
	public void testAltaProductoAlimentacion() {
		
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);

	
		TProductoAlimentacion p = new TProductoAlimentacion(getNameRandom(), 1.0, 1, idMarca, 0, "test", 1.0, 1.0);
		
		int result = FactoriaNegocio.getInstance().getProductoJPA().altaProductoAlimentacion(p);
		
		assertTrue( result > 0);
	}
	
	
	@Test
	public void testAltaProductoSouvenirs() {
		
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);

	
		
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int p = productoSA.altaProductoSouvenirs(producto);
		

		assertTrue( p > 0);
	}
	
	@Test
	public void testMostrarProductoPorId() {
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);
		
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);	

		TProducto result = FactoriaNegocio.getInstance().getProductoJPA().mostrarProducto(idProd);
		
		assertTrue(result != null);

	}
	
	@Test
	public void testListar() {
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);	

		List<TProducto> result = FactoriaNegocio.getInstance().getProductoJPA().listarProductos();
		
		assertTrue(result != null);
	}
	
	@Test
	public void testListarPorMarca() {
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);
		
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);	


		List<TProducto> result = FactoriaNegocio.getInstance().getProductoJPA().listarProductosPorMarca(idMarca);
		
		assertTrue(result != null);
	}
	
	@Test
	public void testListarPorTipo() {
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);
		
		
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);
		
		List<TProducto> result = FactoriaNegocio.getInstance().getProductoJPA().listarProductosPorTipo(1);
		
		assertTrue(result != null);
	}
	
	@Test
	public void testBaja() {
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);
		
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);
		
		int result = FactoriaNegocio.getInstance().getProductoJPA().bajaProducto(idProd);
		
		assertTrue( result > 0);
	}
	
	@Test
	public void testModificar() {
		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);
		
		
		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);
		
		producto.setId(idProd);
		double precioModificado = 3.2;
		producto.setPrecio(precioModificado);
		
		int prod = productoSA.modificarProducto(producto);
		
		TProducto result = productoSA.mostrarProducto(idProd);
		
		assertTrue( result.getPrecio() == precioModificado);
	}
	
	@Test
	public void testMostrarProductoPorVenta() {
		TTurno turno = getTurno();
		int idTurno = turnoSA.altaTurno(turno);

		TMarca marca = getMarca();
		int idMarca = marcaSA.altaMarca(marca);

		TEmpleadoDeCaja empleado = getEmpleado();
		empleado.setId_Turno(idTurno);
		int idEmp = empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);

		TProducto producto = getProducto();
		producto.setIdMarca(idMarca);
		int idProd = productoSA.altaProductoSouvenirs(producto);

		TVenta venta = new TVenta();

		venta.setActivo(true);
		venta.setFecha(new Date(Calendar.getInstance().getTime().getTime()));
		venta.setFormaDePago("Test");
		venta.setIdEmpleado(idEmp);
		venta.setPrecioTotal(10.0);

		TLineaVenta lv = new TLineaVenta();
		lv.setCantidad(1);
		lv.setPrecio(10.0);
		lv.setIdPoducto(idProd);

		Set<TLineaVenta> lista = new LinkedHashSet<TLineaVenta>();
		lista.add(lv);

		TCarrito carrito = new TCarrito(venta, lista);

		int idV = ventaSA.procesarVenta(carrito);
		
		List<TProducto> result = productoSA.listarProductoPorVenta(idV);
		
		assertTrue(result != null);

	}
	

	private TMarca getMarca() {
		TMarca marca = new TMarca();
		marca.setNombre(getNameRandom());
		marca.setPais(getNameRandom());
		marca.setActivo(true);
		return marca;
	}

	private TProducto getProducto() {
		TProducto producto = new TProductoSouvenirs();
		producto.setActivo(true);
		producto.setNombre(getNameRandom());
		producto.setPrecio(10.0);
		producto.setStock(9999);
		producto.setTipoProducto(1);
		((TProductoSouvenirs) producto).setDescripcion(getNameRandom());
		return producto;
	}

	private String getNameRandom() {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder nombreAleatorio = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(caracteres.length());
			nombreAleatorio.append(caracteres.charAt(index));
		}
		return nombreAleatorio.toString();
	}

	public TTurno getTurno() {
		TTurno turno = new TTurno();
		turno.setHorario(" turno " + new Random().nextInt());
		turno.setActivo(true);
		return turno;
	}

	private TEmpleadoDeCaja getEmpleado() {
		Random random = new Random();

		String dni = UUID.randomUUID().toString().substring(0, 8);

		String nombre = "Empleado" + UUID.randomUUID().toString().substring(0, 8);
		String apellido = "Apellido" + UUID.randomUUID().toString().substring(0, 8);

		Double sueldo = 1500.0 + random.nextDouble() * 2000.0;
		Integer telefono = 600000000 + random.nextInt(100000000);

		Double sueldoBase = random.nextDouble() * 10.0;
		Double complementos = 5.0 + random.nextDouble() * 5.0;

		return new TEmpleadoCompleto(null, dni, nombre, apellido, sueldo, telefono, null, true, sueldoBase,
				complementos);
	}

	
	
	

}
