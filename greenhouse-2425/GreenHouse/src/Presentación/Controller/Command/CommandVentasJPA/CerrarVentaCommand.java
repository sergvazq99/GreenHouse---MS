package Presentacion.Controller.Command.CommandVentasJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.VentaJPA.TCarrito;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CerrarVentaCommand implements Command {

	public Context execute(Object datos) {
		int ret = FactoriaNegocio.getInstance().getVentaSA().procesarVenta((TCarrito) datos);
		int event = - ret / 10000000;
		if (event > -1)
			return new Context(Evento.CERRAR_VENTA_OK, ret);
		else
			return new Context(Evento.CERRAR_VENTA_KO, ret);
	}
}