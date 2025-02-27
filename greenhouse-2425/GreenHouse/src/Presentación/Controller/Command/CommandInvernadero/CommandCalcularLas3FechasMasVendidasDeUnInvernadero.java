package Presentacion.Controller.Command.CommandInvernadero;

import java.sql.Date;
import java.util.Set;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Presentacion.Controller.Command.Command;
import Presentacion.Controller.Command.Context;
import Presentacion.FactoriaVistas.Evento;

public class CommandCalcularLas3FechasMasVendidasDeUnInvernadero implements Command {

	public Context execute(Object datos) {
		Set<Date> resultado = (Set<Date>) FactoriaNegocio.getInstance().getInvernaderoSA()
				.calcularLasTresFechasMasVendidasDeUnInvernadero((Integer) datos);

		if (resultado.size() == 1) {
			if (resultado.iterator().next() == null) {
				return new Context(Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO_KO, resultado);
			}
		}
		return new Context(Evento.CALCULAR_LAS_3_FECHAS_MAS_VENDIDAS_DE_UN_INVERNADERO_OK, resultado);
	}
}