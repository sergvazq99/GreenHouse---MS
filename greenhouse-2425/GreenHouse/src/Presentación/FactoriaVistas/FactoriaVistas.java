
package Presentacion.FactoriaVistas;

import Presentacion.Controller.IGUI;
import Presentacion.Controller.Command.Context;

public abstract class FactoriaVistas {
	
	private static FactoriaVistas instance;

	public static FactoriaVistas getIntance() {
		if(instance == null)
		{
			instance = new FactoriaVistasImp();
		}
		return instance;
	}
	
	public abstract IGUI generarVista(Context context);
}