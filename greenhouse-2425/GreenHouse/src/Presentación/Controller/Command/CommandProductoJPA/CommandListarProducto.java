/**
 * 
 */
package Presentacion.Controller.Command.CommandProductoJPA;

import java.util.List;


import Negocio.FactoriaNegocio.FactoriaNegocio;

import Negocio.ProductoJPA.TProducto;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandListarProducto implements Command {

	public Context execute(Object datos) {
		List<TProducto> res = FactoriaNegocio.getInstance().getProductoJPA().listarProductos();

		return new Context(Evento.LISTAR_PRODUCTOS_VISTA,res);
	}
}