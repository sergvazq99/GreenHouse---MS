package Presentacion.Controller.Command.CommandFactura;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.TLineaFactura;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class DevolverFacturaCommand implements Command {

	public Context execute(Object datos) {
		int res = FactoriaNegocio.getInstance().getFacturaSA().devolverFactura((TLineaFactura)datos);
		if(res > -1){
			return new Context(Evento.DEVOLVER_FACTURA_OK,res);
		}else {
			return new Context(Evento.DEVOLVER_FACTURA_KO,res);
		}
	}
}