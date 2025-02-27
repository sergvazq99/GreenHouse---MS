package Presentacion.Controller.Command.CommandEntrada;

import java.util.Set;

import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarEntradasPorInvernadero implements Command {

	@Override
	public Context execute(Object datos) {
		Set<TEntrada> resultado = FactoriaNegocio.getInstance().getEntradaSA()
				.listarEntradasPorInvernadero((Integer) datos);

		if (resultado.size() == 1) {
			TEntrada unaEntrada = resultado.iterator().next();
			if (unaEntrada.getId() <= 0)
				return new Context(Evento.LISTAR_ENTRADAS_POR_INVERNADERO_KO, unaEntrada);
			else
				return new Context(Evento.LISTAR_ENTRADAS_POR_INVERNADERO_OK, resultado);
		} else {
			return new Context(Evento.LISTAR_ENTRADAS_POR_INVERNADERO_OK, resultado);
		}
	}

}
