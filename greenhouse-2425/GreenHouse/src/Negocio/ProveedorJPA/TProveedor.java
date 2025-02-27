
package Negocio.ProveedorJPA;

public class TProveedor {
	
	private Integer id;
	
	private String CIF;
	
	private String nombre;
	
	private String telefono;
	
	private Boolean activo;
	
	public TProveedor() {
		
	}
	
	public TProveedor(Integer id, String CIF, String nombre, String telefono, Boolean activo) {
		super();
		this.id = id;
		this.CIF = CIF;
		this.nombre = nombre;
		this.telefono = telefono;
		this.activo = activo;
	}

	public TProveedor(Proveedor p) {
		super();
		this.id = p.getId();
		this.CIF = p.getCIF();
		this.nombre = p.getNombre();
		this.telefono = p.getTelefono();
		this.activo = p.getActivo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCIF() {
		return CIF;
	}

	public void setCIF(String cIF) {
		CIF = cIF;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	

	
}