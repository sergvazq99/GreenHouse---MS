package Negocio.VentaJPA;

import java.util.Set;

public class TCarrito {

	private Set<TLineaVenta> tLineaVenta;
	private TVenta tVenta;
	
	public TCarrito(TVenta tVenta, Set<TLineaVenta> tLineaVenta) {
		this.tVenta = tVenta;
		this.tLineaVenta = tLineaVenta;
	}

	public void setLineaVenta(Set<TLineaVenta> tLineaVenta) {
		this.tLineaVenta = tLineaVenta;
	}
	
	public void setVenta(TVenta tVenta) {
		this.tVenta = tVenta;
	}
	
	public Set<TLineaVenta> getLineaVenta() {
		return tLineaVenta;
	}
	public TVenta getVenta() {
		return tVenta;
	}
}