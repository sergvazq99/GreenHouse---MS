package Negocio.Entrada;

import java.util.Set;

public interface EntradaSA {

	public Integer altaEntrada(TEntrada entrada);

	public Integer bajaEntrada(Integer id);

	public Set<TEntrada> listarEntrada();

	public Integer modificarEntrada(TEntrada entrada);

	public TEntrada mostrarEntrada(Integer id);

	public Set<TEntrada> listarEntradasPorInvernadero(Integer idInvernadero);
}