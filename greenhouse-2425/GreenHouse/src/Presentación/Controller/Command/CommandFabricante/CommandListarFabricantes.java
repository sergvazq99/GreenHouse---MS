package Presentacion.Controller.Command.CommandFabricante;

import java.util.Set;

import Negocio.Fabricante.TFabricante;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarFabricantes implements Command {
	
	public Context execute(Object datos) {
		Set<TFabricante> res = FactoriaNegocio.getInstance().getFabricanteSA().listarFabricantes();
		return new Context(Evento.LISTAR_FABRICANTES_VISTA, res);
	}
}