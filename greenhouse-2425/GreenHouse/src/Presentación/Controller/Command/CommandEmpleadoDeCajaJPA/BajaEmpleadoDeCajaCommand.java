
package Presentacion.Controller.Command.CommandEmpleadoDeCajaJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class BajaEmpleadoDeCajaCommand implements Command {
	
	public Context execute(Object datos) {
		int res = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA().bajaEmpleadoDeCaja((Integer) datos);
		if (res > -1) {
			return new Context(Evento.BAJA_EMPLEADO_DE_CAJA_OK, res);
		} else {
			return new Context(Evento.BAJA_EMPLEADO_DE_CAJA_KO, res);
		}
	}
}