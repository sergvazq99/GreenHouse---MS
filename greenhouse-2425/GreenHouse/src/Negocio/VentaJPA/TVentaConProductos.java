package Negocio.VentaJPA;

import java.util.Set;
import Negocio.ProductoJPA.TProducto;

public class TVentaConProductos {

	private Set<TProducto> tProductos;
	private TVenta tVenta;
	private Set<TLineaVenta> tLineaVenta;

	public void setProductos(Set<TProducto> tProductos) {
		this.tProductos = tProductos;
	}

	public void setVenta(TVenta tVenta) {
		this.tVenta = tVenta;
	}

	public void setLineasVenta(Set<TLineaVenta> tLineaVenta) {
		this.tLineaVenta = tLineaVenta;
	}

	public Set<TProducto> getProductos() {
		return tProductos;
	}

	public TVenta getVenta() {
		return tVenta;
	}

	public Set<TLineaVenta> getLineasVenta() {
		return tLineaVenta;
	}

}