package Presentacion;

import Presentacion.Controller.ApplicationController;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class main {
	public static void main(String[] args) {
		Context c = new Context(Evento.VISTA_PRINCIPAL, null);
		ApplicationController.getInstance().manageRequest(c);
	}
}
