package Presentacion.Controller.Command.CommandFabricante;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarFabricantesExtranjeros implements Command {
	public Context execute(Object datos) {
		datos = FactoriaNegocio.getInstance().getFabricanteSA().listarFabricantesExtranjeros();
		return new Context(Evento.LISTAR_FABRICANTES_EXTRANJEROS_VISTA, datos);
	}
}