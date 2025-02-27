package Presentacion.Controller.Command;

public class Context {

	private Integer evento;

	private Object datos;

	public Context(Integer evento, Object datos) {
		this.evento = evento;
		this.datos = datos;
	}

	public Context(Integer evento) {
		this.evento = evento;
	}

	public Integer getEvento() {
		return evento;
	}

	public Object getDatos() {
		return datos;
	}

	public void setContext(Integer evento, Object datos) {
		this.datos = datos;
		this.evento = evento;
	}

	public void setEvento(Integer evento) {
		this.evento = evento;
	}
}