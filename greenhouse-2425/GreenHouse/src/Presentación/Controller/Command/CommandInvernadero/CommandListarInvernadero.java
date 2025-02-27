package Presentacion.Controller.Command.CommandInvernadero;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.TInvernadero;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarInvernadero implements Command {

	public Context execute(Object datos) {
		Set<TInvernadero> resultado = (Set<TInvernadero>) FactoriaNegocio.getInstance().getInvernaderoSA()
				.listarInvernadero();

		return new Context(Evento.LISTAR_INVERNADEROS_VISTA, resultado);
	}
}