package Integracion.Invernadero;

import Negocio.Invernadero.TTiene;
import java.util.Set;

public interface TieneDAO {

	//
	public Integer altaTiene(TTiene tiene);

	//
	public Integer bajaTiene(Integer idInvernadero, Integer idSisRiego);

	//
	public Set<TTiene> listarTiene();

	//
	public Integer vincularInvernaderoConSisRiego(TTiene tiene);

	//
	public Integer desvincularInvernaderoConSisRiego(TTiene tiene);

	//
	public Set<TTiene> mostrarTienePorInvernadero(Integer idInvernadero);

	//
	public Set<TTiene> mostrarTienePorSisRiego(Integer idSisRiego);

	//
	public TTiene mostrarTiene(TTiene tiene);
}