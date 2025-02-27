package Presentacion.Controller.Command.CommandPlanta;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Planta.*;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandAltaPlanta implements Command {

	@Override
	public Context execute(Object datos) {

			int res = FactoriaNegocio.getInstance().getPlantaSA().altaPlanta((TPlanta) datos);
						
			if(res > -1) {
				return new Context(Evento.ALTA_PLANTA_OK, res);
			}else {
				return new Context(Evento.ALTA_PLANTA_KO, res);
			}

	}
}