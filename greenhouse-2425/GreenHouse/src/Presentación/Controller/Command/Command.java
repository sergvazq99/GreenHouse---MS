package Presentacion.Controller.Command;

public abstract interface Command {

	public abstract Context execute(Object datos);
}