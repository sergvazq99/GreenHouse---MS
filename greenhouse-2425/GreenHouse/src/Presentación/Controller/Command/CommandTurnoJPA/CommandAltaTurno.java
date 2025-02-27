/**
 * 
 */
package Presentacion.Controller.Command.CommandTurnoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandAltaTurno implements Command {
	public Context execute(Object datos) {
		TTurno tturno = (TTurno) datos;
		int resultado = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tturno);
		if(resultado >= 0) {
			return new Context(Evento.ALTA_TURNO_OK,resultado);
		}else {
			return new Context(Evento.ALTA_TURNO_KO,resultado);
		}
	}
}