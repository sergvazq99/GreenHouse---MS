package Presentacion.Controller.Command.CommandFabricante;

import Negocio.Fabricante.TFabricante;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandMostrarFabricantePorId implements Command {

	public Context execute(Object datos) {
		TFabricante tf = FactoriaNegocio.getInstance().getFabricanteSA().mostrarFabricantePorId((Integer) datos);
		if (tf.getCodFabricante() == null)
			return new Context(Evento.MOSTRAR_FABRICANTE_POR_ID_KO, tf);
		return new Context(Evento.MOSTRAR_FABRICANTE_POR_ID_OK, tf);
	}
}