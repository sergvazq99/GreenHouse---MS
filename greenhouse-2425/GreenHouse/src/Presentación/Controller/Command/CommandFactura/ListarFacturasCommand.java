package Presentacion.Controller.Command.CommandFactura;

import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.TFactura;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class ListarFacturasCommand implements Command {

	public Context execute(Object datos) {
		Set<TFactura> res = FactoriaNegocio.getInstance().getFacturaSA().listarFacturas();
		 return new Context(Evento.LISTAR_FACTURAS_VISTA, res);
	}
}