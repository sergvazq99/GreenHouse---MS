package Presentacion.Controller.Command.CommandVentasJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.VentaJPA.TLineaVenta;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class DevolverVentaCommand implements Command {

	public Context execute(Object datos) {
		int ret = FactoriaNegocio.getInstance().getVentaSA().devolverVenta((TLineaVenta) datos);
		if (ret > -1)
			return new Context(Evento.DEVOLVER_VENTA_OK, ret);
		else
			return new Context(Evento.DEVOLVER_VENTA_KO, ret);
	}
}