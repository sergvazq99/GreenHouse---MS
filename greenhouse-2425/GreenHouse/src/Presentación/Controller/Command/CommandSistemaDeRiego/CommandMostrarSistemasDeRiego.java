package Presentacion.Controller.Command.CommandSistemaDeRiego;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandMostrarSistemasDeRiego implements Command {
	
	public Context execute(Object datos) {
		Set<TSistemaDeRiego> resultado = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().listarSisRiego();
		
		return new Context(Evento.LISTAR_SISTEMAS_RIEGO_VISTA, resultado); 
	}
}