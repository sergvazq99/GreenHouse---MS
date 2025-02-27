package Presentacion.Controller.Command.CommandEntrada;

import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandMostrarEntrada implements Command {

	public Context execute(Object datos) {
		TEntrada resultado = FactoriaNegocio.getInstance().getEntradaSA().mostrarEntrada((int) datos);
		
		if (resultado.getIdInvernadero() != null && resultado.getId() > -1) {
			return new Context(Evento.MOSTRAR_ENTRADA_OK, resultado);
		} else {
			return new Context(Evento.MOSTRAR_ENTRADA_KO, resultado);
		}
	}
}