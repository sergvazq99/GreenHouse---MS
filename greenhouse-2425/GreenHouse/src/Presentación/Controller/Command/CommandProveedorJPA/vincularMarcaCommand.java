package Presentacion.Controller.Command.CommandProveedorJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.TMarcaProveedor;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class vincularMarcaCommand implements Command {

	public Context execute(Object datos) {
		TMarcaProveedor vinculacion = (TMarcaProveedor) datos;
		int resultado = FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(vinculacion.getIdProveedor(),
				vinculacion.getIdMarca());
		if (resultado > -1) {
			return new Context(Evento.VINCULAR_MARCA_PROVEEDOR_OK, resultado);
		} else {
			return new Context(Evento.VINCULAR_MARCA_PROVEEDOR_KO, resultado);
		}
	}

}