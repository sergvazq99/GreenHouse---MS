package Negocio.Fabricante;

public class TFabricanteLocal extends TFabricante {
	private Integer impuesto;
	private Integer subvencion;

	public Integer getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Integer impuesto) {
		this.impuesto = impuesto;
	}

	public Integer getSubvencion() {
		return subvencion;
	}

	public void setSubvencion(Integer subvencion) {
		this.subvencion = subvencion;
	}
}