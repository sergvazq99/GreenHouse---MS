package Negocio.Factura;

import java.util.HashSet;
import java.util.Set;

public class TFacturaConEntradas {

	private Set<TLineaFactura> tLineaFactura;

	private TFactura tFactura;

	public Set<TLineaFactura> gettLineaFactura() {
		return tLineaFactura;
	}

	public Void incluirLineaFactura(TLineaFactura tLineaFatura) {
		return null;
	}

	public void incluirLineaEntrada(TLineaFactura TLineaFactura) {
		if (tLineaFactura == null)
			tLineaFactura = new HashSet<TLineaFactura>();
		tLineaFactura.add(TLineaFactura);
	}

	public TFactura gettFactura() {
		return tFactura;
	}

	public void settFactura(TFactura tFactura) {
		this.tFactura = tFactura;
	}

}