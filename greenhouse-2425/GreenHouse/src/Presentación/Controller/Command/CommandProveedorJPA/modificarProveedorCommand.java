package Presentacion.Controller.Command.CommandProveedorJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class modificarProveedorCommand implements Command {
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getProveedorJPA().modificarProveedor((TProveedor) datos);

		if (resultado > -1) {
			return new Context(Evento.MODIFICAR_PROVEEDORES_OK, resultado);
		} else {
			return new Context(Evento.MODIFICAR_PROVEEDORES_KO, resultado);
		}
	}
}