package Presentacion.Controller.Command.CommandMarcaJPA;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.MarcaJPA.TMarca;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandListarMarcasPorProveedor implements Command {

	public Context execute(Object datos) {
		Set<TMarca> res = FactoriaNegocio.getInstance().getMarcaJPA().listarMarcasPorProveedor((int)datos);
		
		if(res.size() >= 1) {
			if(res.iterator().next().getId() > 0) {
				return new Context(Evento.LISTAR_MARCAS_POR_PROVEEDOR_OK, res);

			}
		}
		return new Context(Evento.LISTAR_MARCAS_POR_PROVEEDOR_KO, res);
	
	}
}