package Negocio.Planta;

public class TPlanta {

	protected String nombre_cientifico;
	protected String nombre;
	protected Integer id;
	protected Boolean activo;
	protected Integer tipo;
	protected Integer id_invernadero;

	TPlanta(String nombre, String nombre_cientifico, Integer tipo, Integer id_invernadero) {
		this.nombre = nombre;
		this.nombre_cientifico = nombre_cientifico;
		this.tipo = tipo;
		this.id_invernadero = id_invernadero;
		this.activo = true;
	}

	public TPlanta() {
	}

	public String get_nombre_cientifico() {
		return nombre_cientifico;
	}

	public String get_nombre() {
		return nombre;
	}

	public Integer get_id() {
		return id;
	}

	public Integer get_tipo() {
		return tipo;
	}

	public Void set_tipo(String tipo) {
		return null;
	}

	public Integer get_id_invernadero() {
		return id_invernadero;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void set_nombre_cientifico(String nombreCientifico) {
		this.nombre_cientifico = nombreCientifico;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_id(Integer id) {
		this.id = id;
	}

	public void set_tipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void set_id_invernadero(Integer id_invernadero) {
		this.id_invernadero = id_invernadero;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String toString() {
		String tmp, tip = "Frutal", estado = "Si";
		if (!activo) {
			estado = "No";
		}

		if (tipo == 1) {
			tip = "No " + tip;
		}
		tmp = "Informacion de la Planta: " + "\n" + "Id: " + id + "\n" + "Nombre: " + nombre + "\n"
				+ "Nombre cientifico: " + nombre_cientifico + "\n" + "Tipo: " + tip + "\n" + "Id del Invernadero: "
				+ id_invernadero + "\n" + "Activo: " + estado + "\n";

		return tmp;
	}

}