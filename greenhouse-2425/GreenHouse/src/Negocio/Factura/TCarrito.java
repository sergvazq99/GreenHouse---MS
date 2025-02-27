package Negocio.Factura;

import java.util.Set;

public class TCarrito {

	private Set<TLineaFactura> tLineaFactura;

	private TFactura tFactura;

	public Set<TLineaFactura> getLineasFactura() {
		return tLineaFactura;
	}

	public TFactura getFactura() {
		return tFactura;
	}

	public Void setLineasFactura(Set<TLineaFactura> tLineaFactura) {
		return null;
	}

	public void setLineaFactura(Set<TLineaFactura> tLineaFactura) {
		this.tLineaFactura = tLineaFactura;
	}

	public void setFactura(TFactura tFactura) {
		this.tFactura = tFactura;
	}
}