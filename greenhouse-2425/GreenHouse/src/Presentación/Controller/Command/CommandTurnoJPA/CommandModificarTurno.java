/**
 * 
 */
package Presentacion.Controller.Command.CommandTurnoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandModificarTurno implements Command {
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getTurnoJPA().modificarTurno((TTurno)datos);
		if(resultado >= 0) {
			return new Context(Evento.MODIFICAR_TURNO_OK,resultado);
		}else {
			return new Context(Evento.MODIFICAR_TURNO_KO,resultado);
		}
	}
}