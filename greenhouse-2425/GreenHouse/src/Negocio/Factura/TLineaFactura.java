package Negocio.Factura;

public class TLineaFactura {

	private Integer idFactura;

	private Integer idEntrada;

	private Integer cantidad;

	private Float precio;

	public Integer getidFactura() {
		return idFactura;
	}

	public void setidFactura(Integer id) {
		idFactura = id;
	}

	public Integer getidEntrada() {
		return idEntrada;
	}

	public void setidEntrada(Integer id) {
		idEntrada = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}
}