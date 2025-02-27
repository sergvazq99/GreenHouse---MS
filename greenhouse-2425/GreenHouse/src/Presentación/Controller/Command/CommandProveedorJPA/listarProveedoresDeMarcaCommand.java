package Presentacion.Controller.Command.CommandProveedorJPA;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class listarProveedoresDeMarcaCommand implements Command {

	public Context execute(Object datos) {
		Set<TProveedor> resultado = (Set<TProveedor>) FactoriaNegocio.getInstance().getProveedorJPA()
				.listarProveedoresDeMarca((Integer) datos);
		if (resultado.size() >= 1) {
			if (resultado.iterator().next().getId() > 0) {
				return new Context(Evento.LISTAR_PROVEEDORES_DE_MARCA_OK, resultado);
			}
		}
		return new Context(Evento.LISTAR_PROVEEDORES_DE_MARCA_KO, resultado);
	}
}