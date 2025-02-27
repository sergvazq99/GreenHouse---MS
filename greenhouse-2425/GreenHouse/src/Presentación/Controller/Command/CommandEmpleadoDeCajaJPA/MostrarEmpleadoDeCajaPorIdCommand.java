/**
 * 
 */
package Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class MostrarEmpleadoDeCajaPorIdCommand implements Command {
	
	public Context execute(Object datos) {
		TEmpleadoDeCaja res = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA().MostrarEmpleadoDeCajaPorId((int) datos);
		if (res != null) {
			return new Context(Evento.MOSTAR_EMPLEADO_DE_CAJA_POR_ID_OK, res);
		} else {
			return new Context(Evento. MOSTAR_EMPLEADO_DE_CAJA_POR_ID_KO, res);
		}
	}
}