package Negocio.MarcaJPA;

import java.util.Set;

public interface MarcaSA {

	public Integer bajaMarca(Integer id);

	public Integer modificarMarca(TMarca marca);

	public Set<TMarca> listarMarcas();

	public TMarca mostrarMarcaPorId(Integer id);

	public Set<TMarca> listarMarcasPorProveedor(Integer idProv);

	public Integer altaMarca(TMarca marca);
}