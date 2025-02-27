package Negocio.ProveedorJPA;

public class TMarcaProveedor {
	private Integer id_proveedor;

	private Integer id_marca;

	public TMarcaProveedor() {

	}

	public TMarcaProveedor(Integer id_proveedor, Integer id_marca) {
		this.id_proveedor = id_proveedor;
		this.id_marca = id_marca;
	}

	public Integer getIdProveedor() {
		return id_proveedor;
	}

	public void setIdProveedor(Integer id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public Integer getIdMarca() {
		return id_marca;
	}

	public void setIdMarca(Integer id_marca) {
		this.id_marca = id_marca;
	}
}
