package Presentacion.Controller;

import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.CommandFactory;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.FactoriaVistas;

public class ApplicationControllerImp extends ApplicationController {

	private IGUI vistaActual;

	public void manageRequest(Context context) {
		Command c = CommandFactory.getInstance().getCommand(context.getEvento());
		if (c == null) {
			vistaActual = FactoriaVistas.getIntance().generarVista(context);
			return;
		} else {
			Context responseContext = c.execute(context.getDatos());
			if (FactoriaVistas.getIntance().generarVista(responseContext) == null)
				vistaActual.actualizar(responseContext);
		}
	}
}