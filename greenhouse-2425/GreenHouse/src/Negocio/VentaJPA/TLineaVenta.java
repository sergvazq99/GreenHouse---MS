package Negocio.VentaJPA;

public class TLineaVenta {
	
	private Integer idProducto;
	
	private Integer cantidad;
	private Integer idVenta;
	private Double precio;
	
	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdPoducto(Integer id) {
		idProducto = id;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer id) {
		idVenta = id;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}