/**
 * 
 */
package Presentacion.Controller.Command.CommandTurnoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandBajaTurno implements Command {
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getTurnoJPA().bajaTurno((Integer)datos);
		if(resultado >= 0) {
			return new Context(Evento.BAJA_TURNO_OK,resultado);
		}else {
			return new Context(Evento.BAJA_TURNO_KO,resultado);
		}
	}
}