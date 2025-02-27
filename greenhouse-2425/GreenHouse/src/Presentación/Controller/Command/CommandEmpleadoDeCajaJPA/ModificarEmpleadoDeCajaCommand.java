
package Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA;

import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class ModificarEmpleadoDeCajaCommand implements Command {
	public Context execute(Object datos) {
		int res = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA().ModificarEmpleadoDeCaja((TEmpleadoDeCaja) datos);
		if (res > -1) {
			return new Context(Evento.MODIFICAR_EMPLEADO_DE_CAJA_OK, res);
		} else {
			return new Context(Evento.MODIFICAR_EMPLEADO_DE_CAJA_KO, res);
		}
	}
}