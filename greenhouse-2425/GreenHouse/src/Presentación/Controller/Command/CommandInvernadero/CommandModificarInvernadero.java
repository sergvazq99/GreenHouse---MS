package Presentacion.Controller.Command.CommandInvernadero;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.TInvernadero;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandModificarInvernadero implements Command {
	
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getInvernaderoSA().modificarInvernadero((TInvernadero) datos);
		if (resultado > -1) {
			return new Context(Evento.MODIFICAR_INVERNADERO_OK, resultado);
		} else {
			return new Context(Evento.MODIFICAR_INVERNADERO_KO, resultado);
		}
	}
}