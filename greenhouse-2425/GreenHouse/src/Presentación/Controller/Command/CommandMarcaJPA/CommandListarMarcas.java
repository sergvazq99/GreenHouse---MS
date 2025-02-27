package Presentacion.Controller.Command.CommandMarcaJPA;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.MarcaJPA.TMarca;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandListarMarcas implements Command {

	public Context execute(Object datos) {
		Set<TMarca> res = FactoriaNegocio.getInstance().getMarcaJPA().listarMarcas();
		return new Context (Evento.LISTAR_MARCAS_VISTA, res);

	}
}