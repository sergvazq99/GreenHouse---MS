/**
 * 
 */
package Negocio.ProductoJPA;

import java.util.List;


public interface ProductoSA {

	public Integer altaProductoSouvenirs (TProducto producto);
	public Integer altaProductoAlimentacion (TProducto producto);


	public Integer bajaProducto(Integer idProducto);

	public List<TProducto> listarProductos();

	public List<TProducto> listarProductosPorMarca(Integer idMarca);

	public List<TProducto> listarProductosPorTipo(Integer tipo);

	
	public List<TProducto> listarProductoPorVenta(Integer idVenta);


	public Integer modificarProducto(TProducto producto);


	public TProducto mostrarProducto(Integer idProducto);
}