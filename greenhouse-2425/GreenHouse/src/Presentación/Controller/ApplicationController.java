package Presentacion.Controller;

import Presentacion.Controller.Command.Context;

public abstract class ApplicationController {

	private static ApplicationController instance;

	public static synchronized ApplicationController getInstance() {
		if (instance == null) {
			instance = new ApplicationControllerImp();
		}
		return instance;
	}

	public abstract void manageRequest(Context context);

}