package Presentacion.Controller.Command.CommandPlanta;

import java.util.HashSet;
import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Planta.*;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarPlantasPorInvernadero implements Command {

	public Context execute(Object datos) {
		Set<TPlanta> res = new HashSet<> ();

			res = FactoriaNegocio.getInstance().getPlantaSA().listarPlantasPorInvernadero((Integer)datos);

			if(res == null || res.isEmpty()){
				return new Context(Evento.LISTAR_PLANTAS_DE_INVERNADERO_KO, null);
			}
			else{
				
				return new Context(Evento.LISTAR_PLANTAS_DE_INVERNADERO_OK,res);
			}

	}
}