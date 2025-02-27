package Negocio;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCajaSA;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.MarcaJPA.MarcaSA;
import Negocio.MarcaJPA.TMarca;
import Negocio.ProductoJPA.ProductoSA;
import Negocio.ProductoJPA.TProducto;
import Negocio.ProductoJPA.TProductoSouvenirs;
import Negocio.TurnoJPA.TTurno;
import Negocio.TurnoJPA.TurnoSA;
import Negocio.VentaJPA.TCarrito;
import Negocio.VentaJPA.TLineaVenta;
import Negocio.VentaJPA.TVenta;
import Negocio.VentaJPA.TVentaConProductos;
import Negocio.VentaJPA.VentaSA;

public class VentaSATest {

	private static Random random;
	private EntityManager em;
	private EmpleadoDeCajaSA empleadoDeCajaSA;
	private TurnoSA turnoSA;
	private VentaSA ventaSA;
	private MarcaSA marcaSA;
	private ProductoSA productoSA;

	@Before
	public void setUp() {
		empleadoDeCajaSA = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA();
		turnoSA = FactoriaNegocio.getInstance().getTurnoJPA();
		ventaSA = FactoriaNegocio.getInstance().getVentaSA();
		marcaSA = FactoriaNegocio.getInstance().getMarcaJPA();
		productoSA = FactoriaNegocio.getInstance().getProductoJPA();
		em = EMFSingleton.getInstance().getEMF().createEntityManager();
		random = new Random();

	}

	@After
	public void tearDown() {

		if (em.isOpen()) {
			em.close();
		}
	}

	@Test
	public void procesarVenta() {

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

		int res = ventaSA.procesarVenta(carrito);

		assert (res > 0);
	}

	@Test
	public void modificarVenta() {

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

		int res = ventaSA.procesarVenta(carrito);

		TVenta mod = new TVenta();

		mod.setId(res);

		mod.setActivo(true);
		mod.setFecha(new Date(Calendar.getInstance().getTime().getTime()));
		mod.setFormaDePago("Test mod");
		mod.setIdEmpleado(idEmp);
		mod.setPrecioTotal(10.0);

		int ret = ventaSA.modificarVenta(mod);

		assert (ret > 0);
	}

	@Test
	public void devolverVenta() {

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

		int idVenta = ventaSA.procesarVenta(carrito);

		lv.setIdVenta(idVenta);
		
		int res = ventaSA.devolverVenta(lv);

		assert (res > 0);
	}

	@Test
	public void mostrarVenta() {

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

		int idVenta = ventaSA.procesarVenta(carrito);

		TVentaConProductos ret = ventaSA.mostrarPorId(idVenta);
		
		assert(ret.getProductos().size() > 0);
	}

	@Test
	public void listarVenta() {

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

		ventaSA.procesarVenta(carrito);

		
		Set<TVenta> vLista = ventaSA.listarVentas();
		
		assert (vLista.size() > 0);
	}

	@Test
	public void ventaPorEmpleado() {

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

		ventaSA.procesarVenta(carrito);
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
		turno.setHorario(" turno " + random.nextInt());
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