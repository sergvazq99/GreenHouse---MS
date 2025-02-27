package Negocio.VentaJPA;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class idLineaVenta implements Serializable {

	private static final long serialVersionUID = 0;

	public idLineaVenta() {
	}
	
	public idLineaVenta(Integer idPorducto, Integer IdVenta) {
		this.idProducto = idPorducto;
		this.idVenta = IdVenta;
	}

	private Integer idProducto;
	private Integer idVenta;

	public Integer getIdventa() {
		return idVenta;
	}

	public void setIdVenta(Integer id) {
		idVenta = id;
	}

	public void setIdProducto(Integer id) {
		idProducto = id;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof idLineaVenta))
			return false;
		idLineaVenta pk = (idLineaVenta) obj;
		if ((idProducto == null && pk.idProducto != null) || (idProducto != null && !idProducto.equals(pk.idProducto)))
			return false;
		if ((idVenta == null && pk.idVenta != null) || (idVenta != null && !idVenta.equals(pk.idVenta)))
			return false;
		return true;
	}

	public int hashCode() {
	    final int prime = 31;
	    int hash = 17;

	    hash = hash * prime + ((idVenta == null) ? 0 : idVenta.hashCode());
	    hash = hash * prime + ((idProducto == null) ? 0 : idProducto.hashCode());

	    return hash;
	}
}