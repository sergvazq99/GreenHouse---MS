package Presentacion.Controller.Command.CommandPlanta;

import java.util.HashSet;
import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Planta.TPlanta;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarPlantasPorTipo implements Command {

	public Context execute(Object datos) {
		Set<TPlanta> res =  new HashSet<>() ;

			res = FactoriaNegocio.getInstance().getPlantaSA().listarPlantasPorTipo((String)datos);

		
	if(res == null || res.isEmpty()){return new Context(Evento.LISTAR_PLANTAS_POR_TIPO_KO, null);}
	else{
		return new Context(Evento.LISTAR_PLANTAS_POR_TIPO_OK,res);
	}
	}
	
}