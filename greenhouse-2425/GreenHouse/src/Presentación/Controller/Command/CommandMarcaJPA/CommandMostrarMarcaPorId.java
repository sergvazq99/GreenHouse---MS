package Presentacion.Controller.Command.CommandMarcaJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.FactoriaNegocio.FactoriaSA;
import Negocio.MarcaJPA.TMarca;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandMostrarMarcaPorId implements Command {

	public Context execute(Object datos) {

		TMarca res = FactoriaNegocio.getInstance().getMarcaJPA().mostrarMarcaPorId((Integer) datos);

		if (res.getId() > -1) {
			return new Context(Evento.MOSTRAR_MARCA_OK, res);
		} else {
			return new Context(Evento.MOSTRAR_MARCA_KO, res);

		}
	}
}