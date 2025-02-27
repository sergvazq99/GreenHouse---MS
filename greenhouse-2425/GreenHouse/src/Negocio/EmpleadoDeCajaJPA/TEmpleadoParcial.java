
package Negocio.EmpleadoDeCajaJPA;

public class TEmpleadoParcial extends TEmpleadoDeCaja {
	
	private Double Precio_h;
	private Double Horas;
	
	public TEmpleadoParcial(Integer id, String dni, String nombre, String apellido, Double sueldo, Integer tlf,
			Integer id_turno, Boolean activo, Double precio_h, Double horas) {
		super(id, dni, nombre, apellido, sueldo, tlf, id_turno, activo, 1); // 1 sera empleado Parcial
		this.setPrecio_h(precio_h);
		this.setHoras(horas);
	}
	
	public TEmpleadoParcial(){
	}

	public Double getPrecio_h() {
		return Precio_h;
	}

	public void setPrecio_h(Double precio_h) {
		Precio_h = precio_h;
	}

	public Double getHoras() {
		return Horas;
	}

	public void setHoras(Double horas) {
		Horas = horas;
	}
	
}