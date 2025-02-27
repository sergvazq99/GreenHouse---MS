package Presentacion.Controller.Command.CommandEntrada;

import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandModificarEntrada implements Command {

	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getEntradaSA().modificarEntrada((TEntrada) datos);
		if (resultado > -1) {
			return new Context(Evento.MODIFICAR_ENTRADA_OK, resultado);
		} else {
			return new Context(Evento.MODIFICAR_ENTRADA_KO, resultado);
		}
	}
}