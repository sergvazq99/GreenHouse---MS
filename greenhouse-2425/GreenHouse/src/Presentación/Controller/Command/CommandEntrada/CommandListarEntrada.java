package Presentacion.Controller.Command.CommandEntrada;

import java.util.Set;

import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarEntrada implements Command {

	public Context execute(Object datos) {
		Set<TEntrada> resultado = FactoriaNegocio.getInstance().getEntradaSA().listarEntrada();
		return new Context(Evento.LISTAR_ENTRADAS_VISTA, resultado);
	}
}