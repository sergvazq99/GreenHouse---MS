/**
 * 
 */
package Presentacion.Controller.Command.CommandTurnoJPA;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarTurnos implements Command {
	public Context execute(Object datos) {
		Set<TTurno> resultado = FactoriaNegocio.getInstance().getTurnoJPA().listarTurnos();
		return new Context(Evento.LISTAR_TURNO_VISTA,resultado);
	}
}