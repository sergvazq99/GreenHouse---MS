package Negocio.VentaJPA;

import java.sql.Date;


public class TVenta {

	private Integer id;

	private Double precioTotal;
	private String formaPago;
	private Date fecha;
	private Boolean activo;
	private Integer idEmpleado;
	public Integer getId() {
		return id;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public Date getFecha() {
		return fecha;
	}

	public Boolean getActivo() {
		return activo;
	}
	
	public Integer getIdEmpleado() {
		return idEmpleado;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public void setFormaDePago(String formaPago) {
		this.formaPago = formaPago;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public void setIdEmpleado(Integer id) {
		idEmpleado = id;
	}
}