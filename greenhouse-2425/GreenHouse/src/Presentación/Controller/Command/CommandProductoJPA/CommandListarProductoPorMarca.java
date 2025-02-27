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


public class CommandListarProductoPorMarca implements Command {

	public Context execute(Object datos) {
		List<TProducto> res  ;

		res = FactoriaNegocio.getInstance().getProductoJPA().listarProductosPorMarca((int)datos);

	
		if(res == null || res.isEmpty()){return new Context(Evento.LISTAR_PRODUCTOS_POR_MARCA_KO, null);}
		else{
			return new Context(Evento.LISTAR_PRODUCTOS_POR_MARCA_OK,res);
		}
	}
}