
package Negocio.EmpleadoDeCajaJPA;

import java.util.Set;


public interface EmpleadoDeCajaSA {
	
	public Integer altaEmpleadoDeCaja(TEmpleadoDeCaja empleado);

	public Integer bajaEmpleadoDeCaja(Integer idEmpleado);

	public Integer ModificarEmpleadoDeCaja(TEmpleadoDeCaja empleado);

	public Set<TEmpleadoDeCaja> ListarEmpleadoDeCajaPorNombre(String nombre);

	public TEmpleadoDeCaja MostrarEmpleadoDeCajaPorId(Integer id);

	public Set<TEmpleadoDeCaja> ListarEmpleadosPorTurno(Integer idTurno);

	public Set<TEmpleadoDeCaja> ListarEmpleadosDeCaja();
}