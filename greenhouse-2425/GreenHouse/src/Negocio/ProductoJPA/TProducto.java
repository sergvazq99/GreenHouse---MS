/**
 * 
 */
package Negocio.ProductoJPA;

import Negocio.VentaJPA.TVentaConProductos;

public class TProducto {

	protected Integer id;
	
	protected String nombre;

	protected Double precio;

	protected Integer stock;

	protected Boolean activo;

	protected Integer idMarca;

	protected TVentaConProductos tVentaConProductos;
	
	// 0 es Alimentacion
	// 1 es Souvenir
	
	protected int tipoProducto;

	public TProducto(String nombre, Double precio, Integer stock, Integer idMarca, int tipoProducto){
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.idMarca = idMarca;
		this.activo = true;
		this.tipoProducto = tipoProducto;
	}

	public TProducto() {
	}

	public int getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(int tipoProducto) {
		this.tipoProducto = tipoProducto;
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


	public Double getPrecio() {
		return precio;
	}


	public void setPrecio(Double precio) {
		this.precio = precio;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Boolean getActivo() {
		return activo;
	}


	public void setActivo(Boolean activo) {
		this.activo = activo;
	}


	public Integer getIdMarca() {
		return idMarca;
	}


	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}


	public TVentaConProductos gettVentaConProductos() {
		return tVentaConProductos;
	}


	public void settVentaConProductos(TVentaConProductos tVentaConProductos) {
		this.tVentaConProductos = tVentaConProductos;
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
		+ "Activo: " + estado + "\n";
	}

}