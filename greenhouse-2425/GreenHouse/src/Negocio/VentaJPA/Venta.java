package Negocio.VentaJPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCaja;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByid", query = "select obj from Venta obj where :id = obj.id "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByversion", query = "select obj from Venta obj where :version = obj.version "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByactivo", query = "select obj from Venta obj where :activo = obj.activo "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByprecioTotal", query = "select obj from Venta obj where :precioTotal = obj.precioTotal "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByformaPago", query = "select obj from Venta obj where :formaPago = obj.formaPago "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByfecha", query = "select obj from Venta obj where :fecha = obj.fecha "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findBylieanVenta", query = "select obj from Venta obj where :lieanVenta MEMBER OF obj.lieanVenta "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findByempleadoDeCaja", query = "select obj from Venta obj where :empleadoDeCaja = obj.empleadoDeCaja "),
		@NamedQuery(name = "Negocio.VentaJPA.Venta.findAll", query = "select obj from Venta obj") })

public class Venta implements Serializable {

	private static final long serialVersionUID = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Version
	private Integer version;
	private Boolean activo;
	private Double precioTotal;
	private String formaPago;
	private Date fecha;
	
	@OneToMany(mappedBy = "venta")
	private Set<LineaVenta> lieanVenta;
	
	@ManyToOne
	private EmpleadoDeCaja empleadoDeCaja;

	public Venta() {
	}

	public Venta(TVenta tVenta) {
		lieanVenta = new HashSet<LineaVenta>();
		id = tVenta.getId();
		activo = tVenta.getActivo();
		precioTotal = tVenta.getPrecioTotal();
		formaPago = tVenta.getFormaPago();
		fecha = tVenta.getFecha();
	}

	public Integer getId() {
		return  id;
	}

	public Set<LineaVenta> getLineaVenta() {
		return lieanVenta;
	}

	public EmpleadoDeCaja getEmpleadoDeCaja() {
		return empleadoDeCaja;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public void setId(Integer Id) {
		this.id = Id;
	}
	
	public void setEmpleado(EmpleadoDeCaja empleado) {
		this.empleadoDeCaja = empleado;
	}

	public Double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public TVenta toTransfer() {
		TVenta tVenta = new TVenta();
		
		tVenta.setActivo(activo);
		tVenta.setFecha(fecha);
		tVenta.setFormaDePago(formaPago);
		tVenta.setId(id);
		tVenta.setPrecioTotal(precioTotal);
		tVenta.setIdEmpleado(empleadoDeCaja.getId());
		return tVenta;
	}
	
	public void setLineaVenta(LineaVenta lineaVenta) {
		this.lieanVenta.add(lineaVenta);
	}
	
	public void removeLineaVenta(LineaVenta lineaVenta) {
		this.lieanVenta.remove(lineaVenta);
	}
}