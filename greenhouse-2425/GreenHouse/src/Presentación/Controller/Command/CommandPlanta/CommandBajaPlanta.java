package Presentacion.Controller.Command.CommandPlanta;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandBajaPlanta implements Command {

	public Context execute(Object datos) {
		int res = -1;
	
			res = FactoriaNegocio.getInstance().getPlantaSA().bajaPlanta((Integer)datos);
			if(res >= 0) {
				return new Context(Evento.BAJA_PLANTA_OK, res);
			}else {
				return new Context(Evento.BAJA_PLANTA_KO, res);
			}
		

	
	}
}