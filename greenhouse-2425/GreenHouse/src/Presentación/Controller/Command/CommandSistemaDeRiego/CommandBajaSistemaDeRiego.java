package Presentacion.Controller.Command.CommandSistemaDeRiego;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandBajaSistemaDeRiego implements Command {

	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().bajaSisRiego((Integer)datos);
		if(resultado > -1){
			return new Context(Evento.BAJA_SISTEMA_DE_RIEGO_OK,resultado);
		}else {
			return new Context(Evento.BAJA_SISTEMA_DE_RIEGO_KO,resultado);
		}
	}


}