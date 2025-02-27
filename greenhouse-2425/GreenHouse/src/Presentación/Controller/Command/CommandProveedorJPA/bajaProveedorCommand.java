package Presentacion.Controller.Command.CommandProveedorJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class bajaProveedorCommand implements Command {
	public Context execute(Object datos) {
		int resultado = FactoriaNegocio.getInstance().getProveedorJPA().bajaProveedor((Integer) datos);

		if (resultado > -1) {
			return new Context(Evento.BAJA_PROVEEDOR_OK, resultado);
		} else {
			return new Context(Evento.BAJA_PROVEEDOR_KO, resultado);
		}
	}
}