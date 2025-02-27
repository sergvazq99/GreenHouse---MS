package Presentacion.Controller.Command.CommandProveedorJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.TMarcaProveedor;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class desvincularMarcaCommand implements Command {

	public Context execute(Object datos) {
		TMarcaProveedor desvinculacion = (TMarcaProveedor) datos;
		int resultado = FactoriaNegocio.getInstance().getProveedorJPA()
				.desvincularMarca(desvinculacion.getIdProveedor(), desvinculacion.getIdMarca());
		if (resultado > -1) {
			return new Context(Evento.DESVINCULAR_MARCA_PROVEEDOR_OK, resultado);
		} else {
			return new Context(Evento.DESVINCULAR_MARCA_PROVEEDOR_KO, resultado);
		}
	}
}