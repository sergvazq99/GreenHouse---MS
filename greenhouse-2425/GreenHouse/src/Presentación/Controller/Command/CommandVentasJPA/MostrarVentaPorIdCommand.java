package Presentacion.Controller.Command.CommandVentasJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.VentaJPA.TVenta;
import Negocio.VentaJPA.TVentaConProductos;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class MostrarVentaPorIdCommand implements Command {

	public Context execute(Object datos) {
		TVentaConProductos res = FactoriaNegocio.getInstance().getVentaSA().mostrarPorId((Integer)datos );
		TVenta tVenta = res.getVenta();
		if(tVenta.getId() < 0)
			return new Context(Evento.MOSTRAR_VENTA_POR_ID_KO, tVenta);
		else
			return new Context(Evento.MOSTRAR_VENTA_POR_ID_OK, res);
	}
}