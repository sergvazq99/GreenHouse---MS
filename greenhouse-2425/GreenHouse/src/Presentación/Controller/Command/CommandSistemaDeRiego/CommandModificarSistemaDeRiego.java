package Presentacion.Controller.Command.CommandSistemaDeRiego;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandModificarSistemaDeRiego implements Command {
	
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().modificarSisRiego((TSistemaDeRiego)datos);
		if(resultado > -1){
			return new Context(Evento.MODIFICAR_SISTEMA_DE_RIEGO_OK,resultado);
		}else {
			return new Context(Evento.MODIFICAR_SISTEMA_DE_RIEGO_KO,resultado);
		}
	}
}