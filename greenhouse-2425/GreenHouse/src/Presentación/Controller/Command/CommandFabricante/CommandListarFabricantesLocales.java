package Presentacion.Controller.Command.CommandFabricante;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarFabricantesLocales implements Command {
	public Context execute(Object datos) {
		datos = FactoriaNegocio.getInstance().getFabricanteSA().listarFabricantesLocales();
		return new Context(Evento.LISTAR_FABRICANTES_LOCALES_VISTA, datos);
	}
}