package Presentacion.Controller.Command.CommandFactura;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.TFactura;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class ModificarFacturaCommand implements Command {

	public Context execute(Object datos) {
		int res = FactoriaNegocio.getInstance().getFacturaSA().modificarFactura((TFactura)datos);
		if(res > -1){
			return new Context(Evento.MODIFICAR_FACTURA_OK,res);
		}else {
			return new Context(Evento.MODIFICAR_FACTURA_KO,res);
		}
	}
}