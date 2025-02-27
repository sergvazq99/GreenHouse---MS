package Presentacion.Controller.Command.CommandInvernadero;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandBajaInvernadero implements Command {
	
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getInvernaderoSA().bajaInvernadero((Integer)datos);
		if(resultado > -1){
			return new Context(Evento.BAJA_INVERNADERO_OK,resultado);
		}else {
			return new Context(Evento.BAJA_INVERNADERO_KO,resultado);
		}
	}
}