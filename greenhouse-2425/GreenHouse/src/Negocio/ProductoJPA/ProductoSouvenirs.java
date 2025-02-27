/**
 * 
 */
package Negocio.ProductoJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
@NamedQuery(name = "Negocio.ProductoJPA.ProductoSouvenirs.findBydescripcion", query = "select obj from ProductoSouvenirs obj where :descripcion = obj.descripcion ")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ProductoSouvenirs extends Producto implements Serializable {

	private static final long serialVersionUID = 0;


	public ProductoSouvenirs() {
	}
	
	public ProductoSouvenirs(TProducto p) {
		super(p);
		
	}


	private String descripcion;


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String d) {
		descripcion = d;
	}
	
	public TProductoSouvenirs entityToTransfer() {
		TProductoSouvenirs ttp = new TProductoSouvenirs();
		ttp.setActivo(activo);
		ttp.setId(id);
		ttp.setIdMarca(marca.getId());
		ttp.setNombre(nombre);
		ttp.setPrecio(precio);
		ttp.setStock(stock);
		ttp.setIdMarca(marca.getId());
		ttp.setDescripcion(descripcion);
		ttp.setTipoProducto(1);

		return ttp;
	}
}