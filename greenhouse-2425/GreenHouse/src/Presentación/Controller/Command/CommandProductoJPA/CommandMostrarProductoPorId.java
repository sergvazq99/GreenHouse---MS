/**
 * 
 */
package Presentacion.Controller.Command.CommandProductoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProductoJPA.TProducto;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandMostrarProductoPorId implements Command {

	public Context execute(Object datos) {
		TProducto res = new TProducto();
		
		res = FactoriaNegocio.getInstance().getProductoJPA().mostrarProducto((int) datos);
	if(res != null) {
		return new Context(Evento.MOSTRAR_PRODUCTO_POR_ID_OK, res);
	}else {
		return new Context(Evento.MOSTRAR_PRODUCTO_POR_ID_KO, res);
	}
	}
}