package Presentacion.Controller.Command.CommandFactura;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.TCarrito;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CerrarFacturaCommand implements Command {
	
	public Context execute(Object datos) {
		TCarrito carrito = (TCarrito) datos;
		int res = FactoriaNegocio.getInstance().getFacturaSA().cerrarFactura(carrito);
		if(res > -1){
			return new Context(Evento.CERRAR_FACTURA_OK,res);
		}else {
			return new Context(Evento.CERRAR_FACTURA_KO,res);
		}
	}
}