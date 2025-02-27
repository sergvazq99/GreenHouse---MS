package Negocio.Invernadero;

public class TInvernadero {

	private Integer id;

	private String sustrato;

	private String nombre;

	private String tipo_iluminacion;

	private Boolean activo;

	public TInvernadero(String nombre, String sustrato, String tipo_iluminacion) {
		this.nombre = nombre;
		this.sustrato = sustrato;
		this.tipo_iluminacion = tipo_iluminacion;
	}

	public TInvernadero(Integer id, String nombre, String sustrato, String tipo_iluminacion, Boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.sustrato = sustrato;
		this.tipo_iluminacion = tipo_iluminacion;
		this.activo = activo;
	}

	public TInvernadero() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSustrato() {
		return sustrato;
	}

	public void setSustrato(String sustrato) {
		this.sustrato = sustrato;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo_iluminacion() {
		return tipo_iluminacion;
	}

	public void setTipo_iluminacion(String tipo_iluminacion) {
		this.tipo_iluminacion = tipo_iluminacion;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getID() {
		return null;

	}

	public Void setID(String ID) {
		return null;
	}


	public String toString() {
		return null;
	}

}