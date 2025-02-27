package Negocio.ProveedorJPA;

import java.util.Set;

public interface ProveedorSA {

	public Integer altaProveedor(TProveedor tProv);

	public Integer bajaProveedor(Integer id);

	public Integer modificarProveedor(TProveedor tProv);

	public Set<TProveedor> listarProveedor();

	public TProveedor mostrarProveedorPorID(Integer id);
	
	public TProveedor mostrarProveedorPorCIF(String CIF);

	public Integer vincularMarca(Integer idProv, Integer idMarca);

	public Integer desvincularMarca(Integer idProv, Integer idMarca);

	public Set<TProveedor> listarProveedoresDeMarca(Integer idMarca);
}