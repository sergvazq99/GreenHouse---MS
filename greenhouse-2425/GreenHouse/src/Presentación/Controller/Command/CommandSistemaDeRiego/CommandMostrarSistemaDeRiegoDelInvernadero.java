package Presentacion.Controller.Command.CommandSistemaDeRiego;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.SistemaDeRiego.TSistemaDeRiego;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandMostrarSistemaDeRiegoDelInvernadero implements Command {

	public Context execute(Object datos) {
		Set<TSistemaDeRiego> resultado = FactoriaNegocio.getInstance().getSistemaDeRiegoSA().listarSisRiegoDelInvernadero((Integer)datos);
		if(resultado.size() == 1){
			TSistemaDeRiego sistRiego = resultado.iterator().next();
			if (sistRiego.getId() <= 0)
				return new Context(Evento.LISTAR_SISTEMA_DE_RIEGO_DE_INVERNADERO_KO,sistRiego);
			else{
				return new Context(Evento.LISTAR_SISTEMA_DE_RIEGO_DE_INVERNADERO_OK,resultado);
			}
		}else{
			return new Context(Evento.LISTAR_SISTEMA_DE_RIEGO_DE_INVERNADERO_OK,resultado);
		}
	}
}