

package Negocio.EmpleadoDeCajaJPA;


public class TEmpleadoCompleto extends TEmpleadoDeCaja {

	private Double Sueldo_Base;
	private Double Complementos;

	public TEmpleadoCompleto(Integer id, String dni, String nombre, String apellido, Double sueldo, Integer tlf,
			Integer id_turno, Boolean activo, Double sueldo_base, Double complementos) {
		super(id, dni, nombre, apellido, sueldo, tlf, id_turno, activo, 0); //0 sera empleados completos
		this.setSueldo_Base(sueldo_base);
		this.setComplementos(complementos);
	}
	public TEmpleadoCompleto(){
	}

	public Double getSueldo_Base() {
		return Sueldo_Base;
	}

	public void setSueldo_Base(Double sueldo_Base) {
		Sueldo_Base = sueldo_Base;
	}

	public Double getComplementos() {
		return Complementos;
	}

	public void setComplementos(Double complementos) {
		Complementos = complementos;
	}
}