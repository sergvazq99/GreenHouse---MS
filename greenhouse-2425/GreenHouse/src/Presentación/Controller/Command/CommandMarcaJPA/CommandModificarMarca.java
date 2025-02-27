package Presentacion.Controller.Command.CommandMarcaJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Negocio.MarcaJPA.TMarca;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandModificarMarca implements Command {

	public Context execute(Object datos) {
		int res = FactoriaNegocio.getInstance().getMarcaJPA().modificarMarca((TMarca) datos);

		if (res > -1) {
			return new Context(Evento.MODIFICAR_MARCA_OK, res);
		} else {
			return new Context(Evento.MODIFICAR_MARCA_KO, res);

		}

	}
}