package Presentacion.Controller.Command.CommandProveedorJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.TProveedor;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class mostarProveedorPorIdCommand implements Command {
	public Context execute(Object datos) {
		TProveedor resultado = FactoriaNegocio.getInstance().getProveedorJPA().mostrarProveedorPorID((Integer) datos);
		if (resultado.getId() > -1) {
			return new Context(Evento.MOSTRAR_PROVEEDORES_POR_ID_OK, resultado);
		} else {
			return new Context(Evento.MOSTRAR_PROVEEDORES_POR_ID_KO, resultado);
		}
	}
}