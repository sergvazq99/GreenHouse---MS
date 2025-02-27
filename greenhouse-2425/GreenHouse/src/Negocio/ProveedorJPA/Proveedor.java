package Negocio.ProveedorJPA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.persistence.NamedQueries;
import java.util.Set;
import Negocio.MarcaJPA.Marca;
import javax.persistence.ManyToMany;

@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "CIF") })
@Entity
@NamedQueries({
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByid", query = "select obj from Proveedor obj where :id = obj.id "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByCIF", query = "select obj from Proveedor obj where :CIF = obj.CIF "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findBynombre", query = "select obj from Proveedor obj where :nombre = obj.nombre "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findBytelefono", query = "select obj from Proveedor obj where :telefono = obj.telefono "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByversion", query = "select obj from Proveedor obj where :version = obj.version "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findByactivo", query = "select obj from Proveedor obj where :activo = obj.activo "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findBymarca", query = "select obj from Proveedor obj where :marca MEMBER OF obj.marca "),
		@NamedQuery(name = "Negocio.ProveedorJPA.Proveedor.findAll", query = "select obj from Proveedor obj order by obj.id") })

public class Proveedor implements Serializable {

	private static final long serialVersionUID = 0;

	public Proveedor() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String CIF;

	private String nombre;

	private String telefono;

	@Version
	private Integer version;

	private Boolean activo;

	@ManyToMany
	private Set<Marca> marca;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}


	public Set<Marca> getMarca() {
		return this.marca;
	}

	public void SetMarca(Set<Marca> marca) {
		this.marca = marca;
	}
	
	public void addMarca(Marca m) {
		this.marca.add(m);
	}
	
	public void removeMarca(Marca m) {
		this.marca.remove(m);
	}
	
	public void transferToEntity(TProveedor proveedor) {
		id = proveedor.getId();
		nombre = proveedor.getNombre();
		CIF = proveedor.getCIF();
		telefono = proveedor.getTelefono();
		activo = proveedor.getActivo();
	}

	public TProveedor entityToTransfer() {
		TProveedor proveedor = new TProveedor();
		proveedor.setId(id);
		proveedor.setNombre(nombre);
		proveedor.setCIF(CIF);
		proveedor.setTelefono(telefono);
		proveedor.setActivo(activo);

		return proveedor;
	}

}
