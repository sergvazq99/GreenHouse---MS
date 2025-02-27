/**
 * 
 */
package Negocio.ProductoJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.ProductoJPA.ProductoAlimentacion.findBypeso", query = "select obj from ProductoAlimentacion obj where :peso = obj.peso "),
		@NamedQuery(name = "Negocio.ProductoJPA.ProductoAlimentacion.findByprecioKilo", query = "select obj from ProductoAlimentacion obj where :precioKilo = obj.precioKilo "),
		@NamedQuery(name = "Negocio.ProductoJPA.ProductoAlimentacion.findBytipo", query = "select obj from ProductoAlimentacion obj where :tipo = obj.tipo ") })
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ProductoAlimentacion extends Producto implements Serializable {

	private static final long serialVersionUID = 0;


	public ProductoAlimentacion() {
	}

	public ProductoAlimentacion(TProducto producto) {
		super(producto);
	}

	private double peso;

	private double precioKilo;

	private String tipo;


	public double getPeso() {
		return peso;
	}


	public double getPrecioKilo() {
		return precioKilo;
	}

	
	public String getTipo() {
	return tipo;
	}


	public void setPeso(double p) {
		this.peso = p;
	}


	public void setPrecioKilo(double p) {
		this.precioKilo = p;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public TProductoAlimentacion entityToTransfer() {
		TProductoAlimentacion ttp = new TProductoAlimentacion();
		ttp.setActivo(activo);
		ttp.setId(id);
		ttp.setIdMarca(marca.getId());
		ttp.setNombre(nombre);
		ttp.setPrecio(precio);
		ttp.setStock(stock);
		ttp.setIdMarca(marca.getId());
		ttp.setPeso(peso);
		ttp.setPrecioKilo(precioKilo);
		ttp.setTipo(tipo);
		ttp.setTipoProducto(0);

		return ttp;
	}
}