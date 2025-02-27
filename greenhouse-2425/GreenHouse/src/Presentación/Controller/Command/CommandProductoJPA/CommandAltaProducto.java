/**
 * 
 */
package Presentacion.Controller.Command.CommandProductoJPA;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProductoJPA.TProducto;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;


public class CommandAltaProducto implements Command {

	public Context execute(Object datos) {
		
		TProducto ok = (TProducto) datos;
		int resp = -1;
		int tmp ;
		
		
		if(ok.getTipoProducto() == 0){
			resp = FactoriaNegocio.getInstance().getProductoJPA().altaProductoAlimentacion((TProducto) datos);
			if(resp > 0)
			tmp = Evento.ALTA_PRODUCTO_ALIMENTACION_OK;
			else
				tmp = Evento.ALTA_PRODUCTO_ALIMENTACION_KO;
				
		}
		else{
			resp = FactoriaNegocio.getInstance().getProductoJPA().altaProductoSouvenirs((TProducto) datos);
			if(resp > 0)
			tmp = Evento.ALTA_PRODUCTO_SOUVENIRS_OK;
			else
				tmp = Evento.ALTA_PRODUCTO_SOUVENIRS_KO;
		}
		
		return new Context(tmp, resp);
		
	}
}