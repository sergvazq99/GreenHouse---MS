package Negocio.MarcaJPA;

public class TMarca {

	private Integer id;

	private String paisOrigen;

	private String nombre;

	private Boolean activo;

	public TMarca(Integer id, String nombre, String paisOrigen, Boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.paisOrigen = paisOrigen;
		this.activo = activo;
	}

//	public TMarca(String nombre, String paisOrigen, Boolean activo) {
//		this.nombre = nombre;
//		this.paisOrigen = paisOrigen;
//		this.activo = activo;
//	}
	
	public TMarca(Marca marca) {
		this.id = marca.getId();
		this.nombre = marca.getNombre();
		this.paisOrigen = marca.getPaisOrigen();
		this.activo = marca.getActivo();
	}

	public TMarca() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPais() {
		return this.paisOrigen;
	}

	public void setPais(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}