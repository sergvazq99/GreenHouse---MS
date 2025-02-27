package Negocio.SistemaDeRiego;

import java.util.Set;

public interface SistemaDeRiegoSA {

	public Integer altaSisRiego(TSistemaDeRiego sisRiego);

	public Integer bajaSisRiego(Integer id);

	public Integer modificarSisRiego(TSistemaDeRiego sisRiego);

	public TSistemaDeRiego mostrarSisRiego(Integer id);

	public Set<TSistemaDeRiego> listarSisRiego();

	public Set<TSistemaDeRiego> listarSisRiegoPorFabricante(Integer idFabricante);

	public Set<TSistemaDeRiego> listarSisRiegoDelInvernadero(Integer idInvernadero);
}