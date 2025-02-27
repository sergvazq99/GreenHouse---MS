package Integracion.Planta;

import Negocio.Planta.TPlanta;
import java.util.Set;

public interface PlantaDAO {

	public Integer altaPlanta(TPlanta planta);

	public Integer bajaPlanta(Integer id);

	public Set<TPlanta> listarPlantas();

	public Integer modificarPlanta(TPlanta planta);

	public TPlanta mostrarPorId(Integer id);

	public Set<TPlanta> mostrarPorTipo(Integer tipo);

	public Set<TPlanta> MostrarPorInvernadero(Integer id_invernadero);
}