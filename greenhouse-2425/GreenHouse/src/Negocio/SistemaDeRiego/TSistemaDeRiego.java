package Negocio.SistemaDeRiego;

public class TSistemaDeRiego {

	private Integer id;
	private String nombre;
	private Integer potenciaRiego;
	private Integer cantidad_agua;
	private Integer frecuencia;
	private Boolean activo;

	private Integer idFabricante;

	public TSistemaDeRiego() {
		this.id = -1;
		this.nombre = "";
		this.potenciaRiego = 0;
		this.cantidad_agua = 0;
		this.frecuencia = 0;
		this.activo = false;
		this.idFabricante = -1;
	}

	public TSistemaDeRiego(Integer id, String nombre, Integer potenciaRiego, Integer cantidad_agua, Integer frecuencia,
			Boolean activo, Integer idFabricante) {
		this.id = id;
		this.nombre = nombre;
		this.potenciaRiego = potenciaRiego;
		this.cantidad_agua = cantidad_agua;
		this.frecuencia = frecuencia;
		this.activo = activo;
		this.idFabricante = idFabricante;

	}

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

	public Integer getPotenciaRiego() {
		return potenciaRiego;
	}

	public void setPotenciaRiego(Integer potenciaRiego) {
		this.potenciaRiego = potenciaRiego;
	}

	public Integer getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Integer getCantidad_agua() {
		return cantidad_agua;
	}

	public void setCantidad_agua(Integer cantidadAgua) {
		this.cantidad_agua = cantidadAgua;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getIdFabricante() {
		return idFabricante;
	}

	public void setIdFabricante(Integer idFabricante) {
		this.idFabricante = idFabricante;
	}

}