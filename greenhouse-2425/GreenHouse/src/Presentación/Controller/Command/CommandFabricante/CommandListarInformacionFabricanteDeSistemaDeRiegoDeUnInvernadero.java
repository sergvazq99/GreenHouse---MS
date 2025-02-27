package Presentacion.Controller.Command.CommandFabricante;

import java.util.Set;

import Negocio.Fabricante.TFabricante;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandListarInformacionFabricanteDeSistemaDeRiegoDeUnInvernadero implements Command {

	public Context execute(Object datos) {
		Set<TFabricante> res = FactoriaNegocio.getInstance().getFabricanteSA()
				.listarFabricantesPorInvernadero((Integer) datos);
		if (res == null) {
			return new Context(Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO_KO, res);
		} else
			return new Context(Evento.LISTAR_INFORMACION_FABRICANTES_DE_SISTEMA_DE_RIEGO_DE_UN_INVERNADERO_OK, res);
	}
}