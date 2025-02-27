/**
 * 
 */
package Presentacion.Controller.Command.CommandTurnoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandMostrarTurno implements Command {
	public Context execute(Object datos) {
		TTurno resultado = FactoriaNegocio.getInstance().getTurnoJPA().mostrarTurno((Integer)datos);
		if(resultado.getId() >= 0) {
			return new Context(Evento.MOSTRAR_TURNO_OK,resultado);
		}else {
			return new Context(Evento.MOSTRAR_TURNO_KO,resultado);
		}
	}
}