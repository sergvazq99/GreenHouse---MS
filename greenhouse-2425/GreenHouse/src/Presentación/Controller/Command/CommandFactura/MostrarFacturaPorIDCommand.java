package Presentacion.Controller.Command.CommandFactura;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.TFactura;
import Negocio.Factura.TFacturaConEntradas;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class MostrarFacturaPorIDCommand implements Command {

	public Context execute(Object datos) {
		TFacturaConEntradas res = FactoriaNegocio.getInstance().getFacturaSA().mostrarFacturaPorID((Integer)datos);
		TFactura factura = res.gettFactura();
		if (factura.getid() <= 0)
			return new Context(Evento.MOSTRAR_FACTURA_POR_ID_KO,res);
		else
			return new Context(Evento.MOSTRAR_FACTURA_POR_ID_OK,res);
	}
}