
package Negocio.EmpleadoDeCajaJPA;

public class TEmpleadoDeCaja {
	
	private Integer id;
	private String nombre;
	private String Apellido;
	private String DNI;
	private Integer Telefono;
	private Double Sueldo;
	private Integer id_Turno;
	private Boolean activo;
	
	private Integer tipo;
	
	public TEmpleadoDeCaja(Integer id, String dni, String nombre, String apellido, Double sueldo, Integer tlf, Integer id_turno, Boolean activo, Integer tipo){
		this.id = id;
		this.DNI = dni;
		this.nombre = nombre;
		this.Apellido = apellido;
		this.Sueldo = sueldo;
		this.Telefono=tlf;
		this.id_Turno=id_turno;
		this.activo=activo;
		this.tipo = tipo;
	}
	
	public TEmpleadoDeCaja(EmpleadoDeCaja empleado){
		this.id = empleado.getId();
		this.DNI = empleado.getDNI();
		this.nombre = empleado.getNombre();
		this.Apellido = empleado.getApellido();
		this.Sueldo=empleado.getSueldo();
		this.Telefono=empleado.getTelefono();
		this.id_Turno = empleado.getTurno().getId();
		this.activo=empleado.getActivo();
		this.tipo = empleado.getTipo();
	}
	
	public TEmpleadoDeCaja(){
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dni) {
		this.DNI = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		this.Apellido = apellido;
	}

	public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public Integer getTelefono() {
		return Telefono;
	}

	public void setTelefono(Integer tlf) {
		this.Telefono = tlf;
	}

	public Double getSueldo() {
		return Sueldo;
	}
	
	public void setSueldo(Double sueldo) {
		this.Sueldo = sueldo;
	}

	public Integer getId_Turno() {
		return id_Turno;
	}

	public void setId_Turno(Integer id_turno) {
		this.id_Turno = id_turno;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
}