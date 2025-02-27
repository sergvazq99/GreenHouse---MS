package Presentacion.Controller.Command.CommandInvernadero;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.TInvernadero;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandMostrarInvernaderoPorID implements Command {
	
	public Context execute(Object datos) {
		TInvernadero resultado = FactoriaNegocio.getInstance().getInvernaderoSA().mostrarInvernaderoPorID((Integer) datos);
		if(resultado.getId() > -1){ 
			return new Context(Evento.MOSTRAR_INVERNADERO_POR_ID_OK,resultado);
		}else {
			return new Context(Evento.MOSTRAR_INVERNADERO_POR_ID_KO,resultado);
		}
	}
}