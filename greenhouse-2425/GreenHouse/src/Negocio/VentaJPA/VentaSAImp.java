package Negocio.VentaJPA;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja;
import Negocio.ProductoJPA.Producto;
import Negocio.ProductoJPA.TProducto;

public class VentaSAImp implements VentaSA {

	public Integer modificarVenta(TVenta tVenta) {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		Venta venta = em.find(Venta.class, tVenta.getId());

		if (venta == null) { // no existe la venta
			et.rollback();
			em.close();
			return -2;
		}
		if (!venta.getActivo()) { // venta dada de baja
			et.rollback();
			em.close();
			return -3;
		}

		EmpleadoDeCaja empleado = em.find(EmpleadoDeCaja.class, tVenta.getIdEmpleado());

		if (empleado == null) { // empleado no existe
			et.rollback();
			em.close();
			return -4;
		}

		venta.setFormaPago(tVenta.getFormaPago());
		venta.setEmpleado(empleado);

		try {
			et.commit();
			em.close();
			return venta.getId();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			em.close();
			return -1;
		}
	}

	public Set<TVenta> listarVentas() {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		TypedQuery<Venta> query = em.createNamedQuery("Negocio.VentaJPA.Venta.findAll", Venta.class);
		
		List<Venta> lQuery = new LinkedList<Venta>();
		lQuery = query.getResultList();
		Set<TVenta> lVenta = new LinkedHashSet<TVenta>();

		for (Venta v : lQuery)
			lVenta.add(v.toTransfer());

		em.close();
		return lVenta;
	}

	public TVentaConProductos mostrarPorId(Integer id) {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		TVentaConProductos vCProd = new TVentaConProductos();
		TVenta tVenta = new TVenta();

		Venta venta = em.find(Venta.class, id);

		if (venta == null) { // no existe la venta
			tVenta.setId(-2);
			et.rollback();
			em.close();
			vCProd.setVenta(tVenta);
			return vCProd;
		}

		Set<TProducto> productos = new HashSet<TProducto>();
		Set<TLineaVenta> lVentas = new LinkedHashSet<TLineaVenta>();

		vCProd.setVenta(venta.toTransfer());
		for (LineaVenta lv : venta.getLineaVenta()) {
			productos.add(lv.getProducto().entityToTransfer());
			lVentas.add(lv.ToTransfer());
		}

		vCProd.setProductos(productos);
		vCProd.setLineasVenta(lVentas);

		try {
			et.commit();
			em.close();
			return vCProd;
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			em.close();
			tVenta.setId(-2);
			vCProd.setVenta(tVenta);
			return vCProd;
		}

	}

	public Set<TVenta> ventasPorEmpleadoDeCaja(Integer id) {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EmpleadoDeCaja emCaja = em.find(EmpleadoDeCaja.class, id);

		if (emCaja == null)
			return null;

		TypedQuery<Venta> query = em.createNamedQuery("Negocio.VentaJPA.Venta.findByempleadoDeCaja", Venta.class);
		query.setParameter("empleadoDeCaja", emCaja);

		List<Venta> lQuery = query.getResultList();
		Set<TVenta> lVenta = new LinkedHashSet<TVenta>();

		for (Venta v : lQuery)
			lVenta.add(v.toTransfer());

		em.close();
		return lVenta;
	}

	public Integer devolverVenta(TLineaVenta tLinea) {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		Venta ventaD = em.find(Venta.class, tLinea.getIdVenta());

		if (ventaD == null) { // la Venta no existe
			et.rollback();
			em.close();
			return -2;
		}

		Producto prod = em.find(Producto.class, tLinea.getIdProducto());

		if (prod == null) { // el Producto no existe
			et.rollback();
			em.close();
			return -3;
		}

		LineaVenta lVenta = em.find(LineaVenta.class, new idLineaVenta(prod.getId(), tLinea.getIdVenta()));

		if (lVenta == null) { // la Linea de Venta no existe
			et.rollback();
			em.close();
			return -4;
		}

		if (tLinea.getCantidad() > lVenta.getCantidad()) { // no se puede devolver mas de lo que tenia la venta
			et.rollback();
			em.close();
			return -5;
		}

		prod.setStock(prod.getStock() + tLinea.getCantidad());
		ventaD.setPrecioTotal(ventaD.getPrecioTotal() - tLinea.getCantidad() * prod.getPrecio());

		if (ventaD.getPrecioTotal() == 0) {
			ventaD.setActivo(false);
			ventaD.removeLineaVenta(lVenta);
		}

		lVenta.setCantidad(lVenta.getCantidad() - tLinea.getCantidad());
		lVenta.setPrecio(lVenta.getPrecio() - tLinea.getCantidad() * prod.getPrecio());

		if (lVenta.getCantidad() == 0)
			em.remove(lVenta);

		try {
			et.commit();
			em.close();
			return ventaD.getId();
		} catch (Exception e) {
			et.rollback();
			em.close();
			e.printStackTrace();
		}

		return -1; // Error desconocido
	}

	public Integer procesarVenta(TCarrito carrito) {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();

		EmpleadoDeCaja emCaja = em.find(EmpleadoDeCaja.class, carrito.getVenta().getIdEmpleado(),
				LockModeType.OPTIMISTIC);

		if (emCaja == null) {// Empleado no existe
			et.rollback();
			em.close();
			return 20000000 + carrito.getVenta().getIdEmpleado();
		}

		if (!emCaja.getActivo()) {// Empledo dado de baja
			et.rollback();
			em.close();
			return 30000000 + carrito.getVenta().getIdEmpleado();
		}

		if (carrito.getLineaVenta().isEmpty()) {// Carrito vacio
			et.rollback();
			em.close();
			return 40000000;
		}

		double total = 0; // Creamos la venta
		Venta venta = new Venta(carrito.getVenta());
		venta.setActivo(true);
		venta.setFecha(new Date(Calendar.getInstance().getTime().getTime()));
		venta.setEmpleado(emCaja);
		em.persist(venta);

		for (TLineaVenta linV : carrito.getLineaVenta()) {// Iteramos sobre las lineas de venta
			Producto prod = em.find(Producto.class, linV.getIdProducto());
			if (prod == null) { // Producto no existe
				et.rollback();
				em.close();
				return 50000000 + linV.getIdProducto();
			}
			if (!prod.getActivo()) { // Producto dado de baja
				et.rollback();
				em.close();
				return 60000000 + linV.getIdProducto();
			}
			if (linV.getCantidad() > prod.getStock()) { // No hay stock suficiente
				et.rollback();
				em.close();
				return 70000000 + linV.getIdProducto();
			}

			prod.setStock(prod.getStock() - linV.getCantidad());
			linV.setIdVenta(venta.getId());
			LineaVenta lineaVenta = new LineaVenta(linV);
			double precio = linV.getCantidad() * prod.getPrecio();
			lineaVenta.setProducto(prod);
			lineaVenta.setVenta(venta);
			lineaVenta.setPrecio(precio);
			em.persist(lineaVenta);
			venta.setLineaVenta(lineaVenta);
			total += precio;
		}
		venta.setPrecioTotal(total);
		try {
			et.commit();
			em.close();
			return venta.getId();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
			em.close();
		}

		return 100000000; // Error desconocido
	}
}