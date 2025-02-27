package Presentacion.Controller.Command.CommandVentasJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.VentaJPA.TVenta;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class ModificarVentaCommand implements Command {
	
	public Context execute(Object datos) {
		int ret = FactoriaNegocio.getInstance().getVentaSA().modificarVenta((TVenta) datos);
		if (ret > -1)
			return new Context(Evento.MODIFICAR_VENTAS_OK, ret);
		else
			return new Context(Evento.MODIFICAR_VENTAS_KO, ret);
	}
}