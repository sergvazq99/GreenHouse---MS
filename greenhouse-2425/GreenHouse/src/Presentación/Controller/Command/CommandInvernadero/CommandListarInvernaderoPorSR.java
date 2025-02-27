package Presentacion.Controller.Command.CommandInvernadero;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.TInvernadero;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarInvernaderoPorSR implements Command {

	public Context execute(Object datos) {
		Set<TInvernadero> resultado = (Set<TInvernadero>) FactoriaNegocio.getInstance().getInvernaderoSA()
				.listarInvernaderoPorSR((Integer) datos);
		if (resultado.size() == 1) {
			if(resultado.iterator().next().getId() == -1) {
				return new Context(Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO_KO, resultado);
			}
		}
		return new Context(Evento.LISTAR_INVERNADEROS_POR_SISTEMA_RIEGO_OK, resultado);
		
	}
}