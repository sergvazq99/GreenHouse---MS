
package Negocio.EmpleadoDeCajaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.EmpleadoDeCajaJPA.EmpleadoCompleto.findByid", query = "select obj from EmpleadoCompleto obj where :id = obj.id "),
		@NamedQuery(name = "Negocio.EmpleadoDeCajaJPA.EmpleadoCompleto.findBySueldo_Base", query = "select obj from EmpleadoCompleto obj where :Sueldo_Base = obj.Sueldo_Base "),
		@NamedQuery(name = "Negocio.EmpleadoDeCajaJPA.EmpleadoCompleto.findByComplementos", query = "select obj from EmpleadoCompleto obj where :Complementos = obj.Complementos ") })
public class EmpleadoCompleto extends EmpleadoDeCaja implements Serializable {
	
	private static final long serialVersionUID = 0;

	private Double Sueldo_Base;
	private Double Complementos;
	
	public EmpleadoCompleto() {
	}
	
	public EmpleadoCompleto(TEmpleadoDeCaja empleado) {
    	this.transferToEntity(empleado);
    }
	
	public TEmpleadoDeCaja entityToTransfer() {
		return new TEmpleadoCompleto(super.getId(), super.getDNI(), super.getNombre(), super.getApellido(), super.getSueldo(),
				super.getTelefono(), super.getTurno().getId(), super.getActivo(), Sueldo_Base, Complementos);
	}
	
	public void transferToEntity(TEmpleadoDeCaja empleado) {
    	super.transferToEntity(empleado);
    	TEmpleadoCompleto empParcial = (TEmpleadoCompleto) empleado;
    	this.setSueldo_Base(empParcial.getSueldo_Base());
    	this.setComplementos(empParcial.getComplementos());
	}

	public Double calcularSueldo() {
		return  Sueldo_Base + Complementos;
	}

	public Double getSueldo_Base() {
		return Sueldo_Base;
	}

	public void setSueldo_Base(Double sueldo_Base) {
		this.Sueldo_Base = sueldo_Base;
	}

	public Double getComplementos() {
		return Complementos;
	}

	public void setComplementos(Double complementos) {
		this.Complementos = complementos;
	}

	
}