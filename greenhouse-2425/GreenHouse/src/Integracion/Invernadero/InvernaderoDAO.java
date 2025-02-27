package Integracion.Invernadero;

import Negocio.Invernadero.TInvernadero;
import java.util.Set;

public interface InvernaderoDAO {

	//
	public Integer altaInvernadero(TInvernadero invernadero);

	//
	public Integer bajaInvernadero(Integer id);

	//
	public Integer modificarInvernadero(TInvernadero invernadero);

	//
	public TInvernadero mostrarInvernaderoPorID(Integer id);

	//
	public TInvernadero mostrarInvernaderoPorNombre(String nombre);

	//
	public Set<TInvernadero> listarInvernadero();

	//
	public Set<TInvernadero> listarInvernaderoPorSR(Integer id_sistema_de_riego);

}