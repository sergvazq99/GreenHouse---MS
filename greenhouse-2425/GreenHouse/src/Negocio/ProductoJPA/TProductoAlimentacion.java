/**
 * 
 */
package Negocio.ProductoJPA;

public class TProductoAlimentacion extends TProducto {


	public TProductoAlimentacion(String nombre, Double precio, Integer stock, Integer idMarca, int tipoProducto
			,String tipo, Double peso,  Double precioKilo) {
		super(nombre, precio, stock, idMarca, tipoProducto);
		
		this.peso = peso;
		this.precioKilo = precioKilo;
		this.tipo = tipo;
		
	}

	public TProductoAlimentacion() {
	}

	private String tipo;
	
	private Double peso;

	private Double precioKilo;

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getPrecioKilo() {
		return precioKilo;
	}

	public void setPrecioKilo(Double precioKilo) {
		this.precioKilo = precioKilo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString() {
	
		String estado = "Si", tip = "Alimentacion";
		if (!activo) {
			estado = "No";
		}
		if(tipoProducto == 1){
			tip = "Souvenir";
		}

		return "Informacion del Producto: " + "\n" 
		+ "Id: " + id + "\n" 
		+ "Nombre: " + nombre + "\n"
		+ "Precio: " + precio + "\n" 
		+ "Stock: " + stock + "\n" 
		+ "Tipo del Producto: " + tip + "\n" 
		+ "Id de la Marca: " + idMarca + "\n" 
		+ "Activo: " + estado + "\n"
		+ "Tipo: " + tipo + "\n"
		+ "Peso: " + peso + "\n"
		+ "Precio por Kilo: " + precioKilo + "\n";
	}



}