package Negocio.Fabricante;

public class TFabricanteExtranjero extends TFabricante {
	private Integer aranceles;
	private String pais_de_origen;

	public Integer getAranceles() {
		return aranceles;
	}

	public void setAranceles(Integer aranceles) {
		this.aranceles = aranceles;
	}

	public String getPaisDeOrigen() {
		return pais_de_origen;
	}

	public void setPaisDeOrigen(String paisOrigen) {
		this.pais_de_origen = paisOrigen;
	}
}