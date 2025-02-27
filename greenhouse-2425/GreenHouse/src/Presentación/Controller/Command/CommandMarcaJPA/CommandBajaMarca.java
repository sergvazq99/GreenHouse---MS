package Presentacion.Controller.Command.CommandMarcaJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandBajaMarca implements Command {
	
	public Context execute(Object datos) {
		int res = FactoriaNegocio.getInstance().getMarcaJPA().bajaMarca((Integer) datos);

		if (res > -1) {
			return new Context(Evento.BAJA_MARCA_OK, res);
		} else {
			return new Context(Evento.BAJA_MARCA_KO, res);
		}
	}
}