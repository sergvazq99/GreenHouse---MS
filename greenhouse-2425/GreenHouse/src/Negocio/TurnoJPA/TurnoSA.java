package Negocio.TurnoJPA;

import java.util.Set;

public interface TurnoSA {

	public Integer altaTurno(TTurno turno);

	public Integer bajaTurno(Integer idTurno);

	public Integer modificarTurno(TTurno turno);

	public TTurno mostrarTurno(Integer idTurno);

	public Set<TTurno> listarTurnos();

	public Float obtenerNominaDelTurno(Integer idTurno);
}