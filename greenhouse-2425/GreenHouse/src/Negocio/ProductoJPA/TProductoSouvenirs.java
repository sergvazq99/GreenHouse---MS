/**
 * 
 */
package Negocio.ProductoJPA;


public class TProductoSouvenirs extends TProducto {

	public TProductoSouvenirs(String nombre, Double precio, Integer stock, Integer idMarca, int tipoProducto,String descripcion ) {
		super(nombre, precio, stock, idMarca, tipoProducto);
		this.descripcion = descripcion;
	}

	public TProductoSouvenirs() {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	private String descripcion;
	
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
		+ "Descripcion: " + descripcion + "\n";
	}

}