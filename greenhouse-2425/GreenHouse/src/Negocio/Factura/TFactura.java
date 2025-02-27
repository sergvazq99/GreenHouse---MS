package Negocio.Factura;

import java.util.Date;

public class TFactura {

	private Integer id;

	private Float precioTotal;

	private Date fechaCompra;

	private Boolean activo;

	public Integer getid() {
		return id;
	}

	public void setid(Integer id) {
		this.id = id;
	}

	public Float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {

		this.activo = activo;
	}
}