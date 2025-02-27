package Negocio;

import static org.junit.Assert.fail;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.EmpleadoDeCajaJPA.EmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCajaSA;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoCompleto;
import Negocio.EmpleadoDeCajaJPA.TEmpleadoDeCaja;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.TurnoJPA.TTurno;
import Negocio.TurnoJPA.TurnoSA;

public class EmpleadoDeCajaSATest {
	private static TurnoSA turnoSA;
	private static EmpleadoDeCajaSA empleadoDeCajaSA;
	private static Random random;

	@BeforeClass
	public static void beforeClass() {
		empleadoDeCajaSA = FactoriaNegocio.getInstance().getEmpleadoDeCajaJPA();
		turnoSA = FactoriaNegocio.getInstance().getTurnoJPA();
		random = new Random();
	}
	
	@Test
	public void altaEmpleadoDeCaja() {
	   
	    TTurno turno = creaTTurno();
	    Integer idTurno = turnoSA.altaTurno(turno);

	    TEmpleadoDeCaja empleado = creaTEmpleadoCompleto();
	    
	    // Turno no existe
	    empleado.setId_Turno(-2);
	    Integer res = empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);

	    if (res != -404) {
	        fail("Error: Turno no existe, debe devolver -404 " + res);
	    }
	    
	    // Turno inactivo
	    turnoSA.bajaTurno(idTurno);
	    empleado.setId_Turno(idTurno);
	   
	    res = empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);

	    if (res != -403) {
	        fail("Error: Turno inactivo, debe devolver -403 " + res);
	    }
	     
	    //Alta correcta
	    turno = creaTTurno();
	    idTurno = turnoSA.altaTurno(turno);
	    
	    empleado.setId_Turno(idTurno);
	    res = empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);
	    int idEmpleado = res;

	    if (res <= 0) {
	        fail("Error: Alta empleado returna número negativo:  " + res);
	    }
	    
	    // Alta Empleado existente
	    res = empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);
	    
	    if (res != -501) {
	        fail("Error: Empleado repetido activo, debe devolver -501 y devuelve: " + res);
	    }

	    // Reacitvacion
	    empleadoDeCajaSA.bajaEmpleadoDeCaja(idEmpleado);

	    res = empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);
	    
	    if (res <= 0) {
	    	 fail("Error: Reactivar empleado returna número negativo:  " + res);
	    }
	}
	
    @Test
    public void bajaEmpleadoDeCaja() {
        
        TTurno turno = creaTTurno();
        Integer idTurno = turnoSA.altaTurno(turno);

	    TEmpleadoDeCaja empleado = creaTEmpleadoCompleto();
	    
	    empleado.setId_Turno(idTurno);
        Integer idEmpleado =  empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);

        // Baja Correcta
        Integer res = empleadoDeCajaSA.bajaEmpleadoDeCaja(idEmpleado);

        if (res <= 0) {
        	 fail("Error: Baja empleado returna número negativo:  " + res);
        }

        // Baja Empleado Inactivo
        res = empleadoDeCajaSA.bajaEmpleadoDeCaja(idEmpleado);
        
        if (res != -403) {
            fail("Error: Baja empleado inactivo, debe devolver -403 y devuelve: " + res);
        }

        // Baja Empleado No Existe
        res = empleadoDeCajaSA.bajaEmpleadoDeCaja(-1);
        
        if (res != -404) {
            fail("Error: Baja empleado inexistente, debe devolver -404 y devuelve: " + res);
        }
    }
    
    @Test
    public void mostrarEmpleadoDeCaja() {
        
    	TTurno turno = creaTTurno();
        Integer idTurno = turnoSA.altaTurno(turno);
        turno.setId(idTurno);

        TEmpleadoCompleto empleado = creaTEmpleadoCompleto();
        empleado.setId_Turno(idTurno);
        
        Integer idEmpleado =  empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);
        empleado.setID(idEmpleado);

        // Mostrar Empleado Correcto
        TEmpleadoCompleto res = (TEmpleadoCompleto) empleadoDeCajaSA.MostrarEmpleadoDeCajaPorId(idEmpleado);
        
        if(!equals(res, empleado))
        	fail("Error: Empleado a mostar no es igual que el empleado");

        // Mostrar Empleado No Existe
        TEmpleadoDeCaja trabajadorInexistente = empleadoDeCajaSA.MostrarEmpleadoDeCajaPorId(-1);
        
        if (trabajadorInexistente != null) {
            fail("Error: Mostrar empleado inexistente debe devolver null");
        }
    }
    
    @Test
    public void listarEmpleadosDeCaja() {

    	TTurno turno = creaTTurno();
        Integer idTurno = turnoSA.altaTurno(turno);
        turno.setId(idTurno);
      
        TEmpleadoCompleto emp1 = creaTEmpleadoCompleto();
        TEmpleadoCompleto emp2 = creaTEmpleadoCompleto();
        emp1.setId_Turno(idTurno);
        emp2.setId_Turno(idTurno);
        
        Integer idEmp1 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp1);
        Integer idEmp2 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp2);
        
   
        Set<TEmpleadoDeCaja> lista = empleadoDeCajaSA.ListarEmpleadosDeCaja();
        
        boolean enc1 = false, enc2 = false;
        for (TEmpleadoDeCaja empleado : lista) {
            if (empleado.getID().equals(idEmp1)) {
            	enc1 = true;
            }
            if (empleado.getID().equals(idEmp2)) {
            	enc2 = true;
            }
        }

        if (!enc1 || !enc2)
            fail("Error: Listar Debe mostar todos los empleado. Empleado 1: " + enc1 + ", Empleado 2: " + enc2);
    }

    @Test
    public void listarEmpleadosDeCajaPorTurno() {

    	TTurno turno = creaTTurno();
        Integer idTurno = turnoSA.altaTurno(turno);
        turno.setId(idTurno);
        
        TTurno turno2 = creaTTurno();
        Integer idTurno2 = turnoSA.altaTurno(turno2);
        turno2.setId(idTurno2);

        TEmpleadoCompleto emp1 = creaTEmpleadoCompleto();
        TEmpleadoCompleto emp2 = creaTEmpleadoCompleto();
        TEmpleadoCompleto emp3 = creaTEmpleadoCompleto();
        emp1.setId_Turno(idTurno);
        emp2.setId_Turno(idTurno);
        emp3.setId_Turno(idTurno2);
        
        Integer idEmp1 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp1);
        Integer idEmp2 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp2);
        Integer idEmp3 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp3);

        Set<TEmpleadoDeCaja> lista = empleadoDeCajaSA.ListarEmpleadosPorTurno(idTurno);
        
        boolean enc1 = false, enc2 = false, enc3 = false;
        for (TEmpleadoDeCaja empleado : lista) {
            if (empleado.getID().equals(idEmp1)) {
            	enc1 = true;
            }
            if (empleado.getID().equals(idEmp2)) {
            	enc2 = true;
            }
            if (empleado.getID().equals(idEmp3)) {
            	enc3 = true;
            }
        }
        
        if (!enc1 || !enc2)
            fail("Error: Empleados 1 y/o 2 no encontrados. Trabajador 1: " + enc1 + ", Trabajador 2: " + enc2);
    
        if(enc3)
        	fail("Error: La lista no debe contener al empleado 3");

       }

    @Test
    public void listarEmpleadosDeCajaPorNombre() {

    	TTurno turno = creaTTurno();
        Integer idTurno = turnoSA.altaTurno(turno);
        turno.setId(idTurno);
        

        TEmpleadoCompleto emp1 = creaTEmpleadoCompleto();
        TEmpleadoCompleto emp2 = creaTEmpleadoCompleto();
        TEmpleadoCompleto emp3 = creaTEmpleadoCompleto();
        emp1.setId_Turno(idTurno);
        emp2.setId_Turno(idTurno);
        emp3.setId_Turno(idTurno);
        
        emp1.setNombre("EMPLEADO");
        emp2.setNombre("EMPLEADO");
        
        Integer idEmp1 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp1);
        Integer idEmp2 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp2);
        Integer idEmp3 = empleadoDeCajaSA.altaEmpleadoDeCaja(emp3);

        Set<TEmpleadoDeCaja> lista = empleadoDeCajaSA.ListarEmpleadoDeCajaPorNombre("EMPLEADO");
        
        boolean enc1 = false, enc2 = false, enc3 = false;
        for (TEmpleadoDeCaja empleado : lista) {
            if (empleado.getID().equals(idEmp1)) {
            	enc1 = true;
            }
            if (empleado.getID().equals(idEmp2)) {
            	enc2 = true;
            }
            if (empleado.getID().equals(idEmp3)) {
            	enc3 = true;
            }
        }
        
        if (!enc1 || !enc2)
            fail("Error: Empleados 1 y/o 2 no encontrados. Trabajador 1: " + enc1 + ", Trabajador 2: " + enc2);
    
        if(enc3)
        	fail("Error: La lista no debe contener al empleado 3");

       }
    
    
    @Test
    public void modificarEmpleado() {
        
    	TTurno turno = creaTTurno();
    	Integer idTurno = turnoSA.altaTurno(turno);
    	turno.setId(idTurno);

    	TEmpleadoCompleto empleado = creaTEmpleadoCompleto();
        empleado.setId_Turno(idTurno);
        
        Integer idEmpleado =  empleadoDeCajaSA.altaEmpleadoDeCaja(empleado);
        empleado.setID(idEmpleado);
        
        TTurno turno2 = creaTTurno();
    	Integer idTurno2 = turnoSA.altaTurno(turno2);
    	turno2.setId(idTurno2);

        // Modificar Empleado
    	TEmpleadoCompleto empMod = creaTEmpleadoCompleto();
        empMod.setID(idEmpleado);
        empMod.setId_Turno(idTurno2);

        Integer res = empleadoDeCajaSA.ModificarEmpleadoDeCaja(empMod);

        if (!idEmpleado.equals(res)) {
            fail("Error: El resultado debe ser el id del empleado y es " + res);
        }
        
        //Verificar modificación
        TEmpleadoCompleto empRes = (TEmpleadoCompleto) empleadoDeCajaSA.MostrarEmpleadoDeCajaPorId(idEmpleado);
        
        if(!equals(empRes, empMod)){
        	fail("Error: No se ha modificado el empleado");
        }

        
    }
    
    
    private boolean equals(TEmpleadoCompleto emp1, TEmpleadoCompleto emp2) {
        boolean igual = emp1.getID().equals(emp2.getID()) && emp1.getDNI().equals(emp2.getDNI())
                && emp1.getNombre().equals(emp2.getNombre()) && emp1.getApellido().equals(emp2.getApellido()) &&
                emp1.getActivo() == emp2.getActivo()
                && emp1.getTelefono().equals(emp2.getTelefono())
                && emp1.getId_Turno().equals(emp2.getId_Turno())
                && emp1.getSueldo_Base().equals(emp2.getSueldo_Base()) && emp1.getComplementos().equals(emp2.getComplementos());

        return igual;
    }


	public TTurno creaTTurno() {
		TTurno turno = new TTurno();
		turno.setHorario(" turno " + random.nextInt());
		turno.setActivo(true);
		return turno;
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
