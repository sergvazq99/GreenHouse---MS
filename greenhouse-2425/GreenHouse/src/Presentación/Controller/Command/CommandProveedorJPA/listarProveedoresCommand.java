package Presentacion.Controller.Command.CommandProveedorJPA;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class listarProveedoresCommand implements Command {

	public Context execute(Object datos) {
		Set<TProveedor> resultado = (Set<TProveedor>) FactoriaNegocio.getInstance().getProveedorJPA().listarProveedor();

		return new Context(Evento.LISTAR_PROVEEDORES_VISTA, resultado);

	}
}