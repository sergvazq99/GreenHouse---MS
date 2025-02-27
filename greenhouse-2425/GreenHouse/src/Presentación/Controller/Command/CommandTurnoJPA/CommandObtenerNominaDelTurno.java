/**
 * 
 */
package Presentacion.Controller.Command.CommandTurnoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandObtenerNominaDelTurno implements Command {
	public Context execute(Object datos) {
		Float resultado = FactoriaNegocio.getInstance().getTurnoJPA().obtenerNominaDelTurno((Integer)datos);
		if(resultado >= 0) {
			return new Context(Evento.OBTENER_NOMINA_DE_TURNO_OK,resultado);
		}else {
			return new Context(Evento.OBTENER_NOMINA_DE_TURNO_KO,resultado);
		}
	}
}