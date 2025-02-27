package Presentacion.Controller.Command.CommandPlanta;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Planta.*;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandModificarPlanta implements Command {

	public Context execute(Object datos) {
		
		TPlanta tmp = (TPlanta) datos;
		
		if(tmp.get_id_invernadero() == null){
			TPlanta p = FactoriaNegocio.getInstance().getPlantaSA().mostrarPlantaPorId(tmp.get_id());
			
			if(p != null){return new Context(Evento.MODIFICAR_PLANTA_VISTA, p);}
			else{
				return new Context(Evento.MODIFICAR_PLANTA_KO, -3);
			}
		}
		else{
		
		int	res = FactoriaNegocio.getInstance().getPlantaSA().modificarPlanta((TPlanta)datos);

		if(res > -1) {
			return new Context(Evento.MODIFICAR_PLANTA_OK, res);
		}else {
			return new Context(Evento.MODIFICAR_PLANTA_KO, res);
		}
		
		}
	}
}