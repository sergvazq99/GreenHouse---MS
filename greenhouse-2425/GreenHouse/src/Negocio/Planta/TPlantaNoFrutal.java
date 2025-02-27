package Negocio.Planta;

public class TPlantaNoFrutal extends TPlanta {

	private String tipo_hoja;

	public TPlantaNoFrutal(String nombre, String nombre_cientifico, Integer tipo, Integer id_invernadero,
			String tipo_hoja) {
		super(nombre, nombre_cientifico, tipo, id_invernadero);
		this.tipo_hoja = tipo_hoja;
	}

	public TPlantaNoFrutal() {
	}

	public String get_tipo_hoja() {
		return tipo_hoja;
	}

	public void set_tipo_hoja(String tipo_hoja) {
		this.tipo_hoja = tipo_hoja;
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
				+ id_invernadero + "\n" + "Activo: " + estado + "\n" + "Tipo de la hoja: " + this.tipo_hoja + "\n";

		return tmp;
	}
}