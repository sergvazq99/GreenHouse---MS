package Negocio;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.EMFSingleton.EMFSingleton;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.Entrada.EntradaSA;
import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Factura.FacturaSA;
import Negocio.Factura.TCarrito;
import Negocio.Factura.TFactura;
import Negocio.Factura.TFacturaConEntradas;
import Negocio.Factura.TLineaFactura;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.TInvernadero;
import Negocio.TurnoJPA.TTurno;

public class TurnoSATest {
	private EntityManager em;
	
	private boolean equals(TTurno e1, TTurno e2) {
		if (e1 == null || e2 == null)
			return false;

		return e1.getId().equals(e2.getId()) && e1.getHorario().equals(e2.getHorario());
	}
	

	@Test
	public void testAltaTurno() {
		TTurno tTurnoCorrecto = new TTurno();
		Random random = new Random();
		tTurnoCorrecto.setHorario("pruebaAlta"+(random.nextInt()%100));		
		int result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		
		assertTrue(result >= 1);
		
		result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		
		assertTrue(result < 0);
		
	}

	@Test
	public void testBajaTurno() {
		TTurno tTurnoCorrecto = new TTurno();
		Random random = new Random();
		tTurnoCorrecto.setHorario("pruebaBaja"+(random.nextInt()%100));
		
		int result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		
		assertTrue(result >= 1);
		
		result = FactoriaNegocio.getInstance().getTurnoJPA().bajaTurno(result);
		
		assertTrue(result >= 1);
		
	}

	@Test
	public void testModificarTurno() {
		TTurno tTurnoCorrecto = new TTurno();
		Random random = new Random();
		tTurnoCorrecto.setHorario("pruebaModificar"+(random.nextInt()%100));
		
		int result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		
		assertTrue(result >= 1);
		
		tTurnoCorrecto.setActivo(true);
		tTurnoCorrecto.setHorario("pruebaModificada"+(random.nextInt()%100));
		tTurnoCorrecto.setId(result);
		
		result = FactoriaNegocio.getInstance().getTurnoJPA().modificarTurno(tTurnoCorrecto);
		
		assertTrue(result >= 1);
		
	}

	@Test
	public void testMostrarTurno() {
		TTurno tTurnoCorrecto = new TTurno();
		Random random = new Random();
		tTurnoCorrecto.setHorario("pruebaMostrar"+(random.nextInt()%100));
		
		int result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		tTurnoCorrecto.setId(result);
		tTurnoCorrecto.setActivo(true);
		
		assertTrue(result >= 1);
		
		TTurno tTurnoGuardado = FactoriaNegocio.getInstance().getTurnoJPA().mostrarTurno(result);
		
		assertTrue(equals(tTurnoCorrecto, tTurnoGuardado));
	}

	@Test
	public void testListarTurno() {
		TTurno tTurnoCorrecto = new TTurno();
		Random random = new Random();
		tTurnoCorrecto.setHorario("pruebaListar"+(random.nextInt()%100));
		
		int result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		
		assertTrue(result >= 1);
		
		Set<TTurno> tTurnosGuardado = FactoriaNegocio.getInstance().getTurnoJPA().listarTurnos();
		
		assertTrue(tTurnosGuardado.size()>0);
	}
	
	@Test
	public void testMostrarNominaDeTurno() {
		TTurno tTurnoCorrecto = new TTurno();
		Random random = new Random();
		tTurnoCorrecto.setHorario("pruebaNomina"+(random.nextInt()%100));
		
		int result = FactoriaNegocio.getInstance().getTurnoJPA().altaTurno(tTurnoCorrecto);
		
		assertTrue(result >= 1);
		
		TEmpleadoDeCaja empleado = creaTEmpleadoCompleto();
		
		empleado.setId_Turno(result);
		int idTurno = result;
	    result = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA().altaEmpleadoDeCaja(empleado);
				
		Float nomina = FactoriaNegocio.getInstance().getTurnoJPA().obtenerNominaDelTurno(idTurno);
		
		assertTrue(nomina>0);
	}
	
	private TEmpleadoCompleto creaTEmpleadoCompleto() {
	    Random random = new Random();

	  
	    String dni = UUID.randomUUID().toString().substring(0, 8);

	    String nombre = "Empleado" + UUID.randomUUID().toString().substring(0, 8);
	    String apellido = "Apellido" + UUID.randomUUID().toString().substring(0, 8);

	    Double sueldo = 1500.0 + random.nextDouble() * 2000.0;
	    Integer telefono = 600000000 + random.nextInt(100000000);

	    Double sueldoBase = random.nextDouble() * 10.0;
	    Double complementos = 5.0 + random.nextDouble() * 5.0;

	    return new TEmpleadoCompleto(null, dni, nombre, apellido, sueldo, telefono, null, true, sueldoBase, complementos);
	}
}
