package Presentacion.Controller.Command.CommandSistemaDeRiego;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandAltaSistemaDeRiego implements Command {

	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().altaSisRiego((TSistemaDeRiego)datos);
		if(resultado > -1){
			return new Context(Evento.ALTA_SISTEMA_DE_RIEGO_OK,resultado);
		}else {
			return new Context(Evento.ALTA_SISTEMA_DE_RIEGO_KO,resultado);
		}
	}
}