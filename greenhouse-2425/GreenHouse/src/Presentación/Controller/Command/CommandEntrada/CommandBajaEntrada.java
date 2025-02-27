package Presentacion.Controller.Command.CommandEntrada;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandBajaEntrada implements Command {

	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getEntradaSA().bajaEntrada((Integer) datos);
		if (resultado > -1) {
			return new Context(Evento.BAJA_ENTRADA_OK, resultado);
		} else {
			return new Context(Evento.BAJA_ENTRADA_KO, resultado);
		}
	}
}