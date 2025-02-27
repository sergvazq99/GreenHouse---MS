
package Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA;

import java.util.Set;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class ListarEmpleadoDeCajaPorNombreCommand implements Command {

	public Context execute(Object datos) {
		Set<TEmpleadoDeCaja> resultado = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA().ListarEmpleadoDeCajaPorNombre((String) datos);
		if(resultado.size() == 1){
			TEmpleadoDeCaja empleado = resultado.iterator().next();
			if (empleado.getID() <= 0)
				return new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE_KO,empleado);
			else{
				return new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE_OK,resultado);
			}
		}else{
			return new Context(Evento.LISTAR_EMPLEADOS_DE_CAJA_POR_NOMBRE_OK,resultado);
		}
	}
}