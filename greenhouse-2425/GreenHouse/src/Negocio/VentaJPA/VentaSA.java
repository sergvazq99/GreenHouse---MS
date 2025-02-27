package Negocio.VentaJPA;

import java.util.Set;

public interface VentaSA {

	public Integer modificarVenta(TVenta tVenta);

	public Set<TVenta> listarVentas();

	public TVentaConProductos mostrarPorId(Integer id);

	public Set<TVenta> ventasPorEmpleadoDeCaja(Integer id);

	public Integer devolverVenta(TLineaVenta venta);

	public Integer procesarVenta(TCarrito carrito);
}