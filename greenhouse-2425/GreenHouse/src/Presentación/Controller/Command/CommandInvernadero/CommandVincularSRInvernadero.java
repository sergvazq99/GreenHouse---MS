package Presentacion.Controller.Command.CommandInvernadero;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.TTiene;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandVincularSRInvernadero implements Command {

	public Context execute(Object datos) {
		TTiene tiene = (TTiene) datos;
		int resultado = FactoriaNegocio.getInstance().getInvernaderoSA()
				.vincularSRInvernadero(tiene.getId_SistemasDeRiego(), tiene.getId_Invernadero());
		if (resultado > -1) {
			return new Context(Evento.VINCULAR_SISTEMA_RIEGO_A_INVERNADERO_OK, resultado);
		} else {
			return new Context(Evento.VINCULAR_SISTEMA_RIEGO_A_INVERNADERO_KO, resultado);
		}
	}
}