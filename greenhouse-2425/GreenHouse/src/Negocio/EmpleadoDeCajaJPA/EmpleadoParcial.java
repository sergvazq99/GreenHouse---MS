
package Negocio.EmpleadoDeCajaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.EmpleadoDeCajaJPA.EmpleadoParcial.findByid", query = "select obj from EmpleadoParcial obj where :id = obj.id "),
		@NamedQuery(name = "Negocio.EmpleadoDeCajaJPA.EmpleadoParcial.findByPrecio_h", query = "select obj from EmpleadoParcial obj where :Precio_h = obj.Precio_h "),
		@NamedQuery(name = "Negocio.EmpleadoDeCajaJPA.EmpleadoParcial.findByHoras", query = "select obj from EmpleadoParcial obj where :Horas = obj.Horas ") })
public class EmpleadoParcial extends EmpleadoDeCaja implements Serializable {
	
	private static final long serialVersionUID = 0;

	private Double Precio_h;
	private Double Horas;
	
	public EmpleadoParcial() {
	}
	
	public EmpleadoParcial(TEmpleadoDeCaja empleado) {
    	this.transferToEntity(empleado);
    }
	
	public TEmpleadoDeCaja entityToTransfer() {
		return new TEmpleadoParcial(super.getId(), super.getDNI(), super.getNombre(), super.getApellido(), super.getSueldo(),
				super.getTelefono(), super.getTurno().getId(), super.getActivo(), Precio_h, Horas);
	}
	
	public void transferToEntity(TEmpleadoDeCaja empleado) {
	    	super.transferToEntity(empleado);
	    	TEmpleadoParcial empParcial = (TEmpleadoParcial) empleado;
	    	this.setHoras(empParcial.getHoras());
	    	this.setPrecio_h(empParcial.getPrecio_h());
	}

	public Double calcularSueldo() {
		return this.Horas * this.Precio_h;
	}
	
	public Double getHoras() {
		return Horas;
	}

	public void setHoras(Double horas) {
		this.Horas = horas;
	}

	public Double getPrecio_h() {
		return Precio_h;
	}

	public void setPrecio_h(Double precio) {
		this.Precio_h = precio;
	}

}