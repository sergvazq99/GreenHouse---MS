package Presentacion.Controller.Command.CommandFabricante;

import Negocio.Fabricante.TFabricante;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandModificarFabricante implements Command {

	public Context execute(Object datos) {
		int ret = FactoriaNegocio.getInstance().getFabricanteSA().modificarFabricante((TFabricante) datos);
		if(ret > -1){
			return new Context(Evento.MODIFICAR_FABRICANTE_OK,ret);
		}else {
			return new Context(Evento.MODIFICAR_FABRICANTE_KO,ret);
		}
	}
}