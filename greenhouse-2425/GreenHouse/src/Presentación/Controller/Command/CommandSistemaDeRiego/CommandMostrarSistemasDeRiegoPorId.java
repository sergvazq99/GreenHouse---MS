package Presentacion.Controller.Command.CommandSistemaDeRiego;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandMostrarSistemasDeRiegoPorId implements Command {
	
	public Context execute(Object datos) {
		TSistemaDeRiego resultado = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().mostrarSisRiego((Integer)datos);
		if(resultado.getIdFabricante() != null && resultado.getIdFabricante() > -1){ 
			return new Context(Evento.MOSTRAR_SISTEMA_DE_RIEGO_OK,resultado);
		}else {
			return new Context(Evento.MOSTRAR_SISTEMA_DE_RIEGO_KO,resultado);
		}
	} 
}