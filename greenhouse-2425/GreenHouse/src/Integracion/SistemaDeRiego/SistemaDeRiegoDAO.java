package Integracion.SistemaDeRiego;

import Negocio.SistemaDeRiego.TSistemaDeRiego;
import java.util.Set;

public interface SistemaDeRiegoDAO {

	public Integer altaSistemaDeRiego(TSistemaDeRiego sistemaDeRiego);

	public Integer bajaSistemaDeRiego(Integer id);

	public Integer modificarSistemaDeRiego(TSistemaDeRiego sistemaDeRiego);

	public TSistemaDeRiego mostrarSistemaDeRiegoPorID(Integer id);

	public Set<TSistemaDeRiego> listarSistemaDeRiegoPorFabricante(Integer idFabricante);

	public Set<TSistemaDeRiego> listarSistemaDeRiego();

	public Set<TSistemaDeRiego> listarSistemaDeRiegoInvernadero(Integer idInvernadero);

	public TSistemaDeRiego leerPorNombreUnico(String nombre);
}