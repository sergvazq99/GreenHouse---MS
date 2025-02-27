package Presentacion.Controller.Command.CommandFabricante;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandGUIBajaFabricante implements Command {

	public Context execute(Object datos) {
		int ret = FactoriaNegocio.getInstance().getFabricanteSA().bajaFabricante((Integer)datos);
		if(ret > -1){
			return new Context(Evento.BAJA_FABRICANTE_OK,ret);
		}else {
			return new Context(Evento.BAJA_FABRICANTE_KO,ret);
		}
	}
}