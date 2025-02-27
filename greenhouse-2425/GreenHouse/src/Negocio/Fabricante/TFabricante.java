package Negocio.Fabricante;

public class TFabricante {
	private Integer id;
	private String nombre;
	private String cod_fabricante;
	private String telefono;
	private Boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodFabricante() {
		return cod_fabricante;
	}

	public void setCodFabricante(String codFabricante) {
		this.cod_fabricante = codFabricante;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}