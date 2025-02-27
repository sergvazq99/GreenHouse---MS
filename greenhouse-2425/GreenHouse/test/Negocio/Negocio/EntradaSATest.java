package Negocio;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Negocio.Entrada.EntradaSA;
import Negocio.Entrada.TEntrada;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.TInvernadero;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntradaSATest {

	//int id, int idInvernadero, Date fecha, Float precio, int stock_entradas, activo boolean
	private static EntradaSA entradaSA;
	private static InvernaderoSA invernaderoSA;

	// FECHA RANDOM
    private Date getRandomDate() {
        // Inicio de la fecha aleatoria en el 1 de enero de 2000
        Calendar iniCal = Calendar.getInstance();
        iniCal.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        java.util.Date start = iniCal.getTime();

        // Fin de la fecha aleatoria en el 31 de diciembre de 2050
        Calendar endCal = Calendar.getInstance();
        endCal.set(2050, Calendar.DECEMBER, 31, 23, 59, 59);
        java.util.Date end = endCal.getTime();

        // Generamos un timestamp aleatorio entre 'start' y 'end'
        long randomMillis = ThreadLocalRandom.current().nextLong(start.getTime(), end.getTime());

        // Creamos una nueva fecha a partir del timestamp generado
        return new Date(randomMillis);
    }
    
    // PRECIO RANDOM
    private Float getPrecioRandom() {
        // Genera un float entre 0.0 y 10000.0
        return ThreadLocalRandom.current().nextFloat() * 10000;
    }
    
    // STOCK ENTRADAS RANDOM
	private int getIntRandom() {
		return ThreadLocalRandom.current().nextInt(0, 10000 + 1);
	}
	
	// NOMBRE INV. RANDOM
	private String getNameRandom() {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder nombreAleatorio = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(caracteres.length());
			nombreAleatorio.append(caracteres.charAt(index));
		}
		return nombreAleatorio.toString();
	}
	
	
	
	private TEntrada getTEntrada(int idInv) {
		TEntrada entrada = new TEntrada();
		// el id en cuanto haga un alta le hago un set, en cada función, ya que no puede ser aleatorio
		entrada.setActivo(true);
		entrada.setFecha(getRandomDate());
		entrada.setPrecio(getPrecioRandom());
		entrada.setStock(getIntRandom());
		entrada.setIdInvernadero(idInv);
		//entrada.setIdInvernadero(getTInvernadero().getId());
		
		return entrada;
	}
	
	private TInvernadero getTInvernadero() {
		TInvernadero invernadero = new TInvernadero();
		invernadero.setActivo(true);
		invernadero.setNombre(getNameRandom());
		invernadero.setSustrato(getNameRandom());
		invernadero.setTipo_iluminacion(getNameRandom());
		
		return invernadero;
		
	}
	
	@BeforeClass
	public static void beforeClass() {
		entradaSA = FactoriaNegocio.getInstance().getEntradaSA();
		invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();

	}
	
	@Test
	public void A_Alta() {
		try {
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEnt = entradaSA.altaEntrada(entrada);
			entrada.setId(idEnt);
			if(idEnt < 0)
				fail("Error: debería devolver un entero positivo");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	@Test
	public void B_Baja() {
		try {
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			int idEnt = entradaSA.altaEntrada(getTEntrada(idInv));
			int bajaEnt = entradaSA.bajaEntrada(idEnt);
			
			if(bajaEnt < 0)
				fail("Error: debería devolver un entero positivo");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	@Test
	public void C_modificar() {
		
		try {
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			TEntrada entrada = getTEntrada(idInv);
			int idEnt = entradaSA.altaEntrada(entrada);
			entrada.setId(idEnt);
			// modifico el stock de la entrada
			entrada.setStock(getIntRandom() + getIntRandom());
			// hago el modificar
			int modificar = entradaSA.modificarEntrada(entrada);
			
			if(modificar < 0)
				fail("Error: debería devolver un entero positivo");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	@Test
	public void D_mostrar() {
		try {
			
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());
			int idEnt = entradaSA.altaEntrada(getTEntrada(idInv));
			TEntrada entrada = entradaSA.mostrarEntrada(idEnt);
	
			if(entrada.getId() != idEnt)
				fail("Error: deberían tener el mismo valor");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	@Test
	public void E_listar_entradas() {
		try {
			// creo 2 invernaderos para asociarlos cada uno a las 2 distintas entradas
			int idInv1 = invernaderoSA.altaInvernadero(getTInvernadero());
			int idInv2 = invernaderoSA.altaInvernadero(getTInvernadero());

			// creo 2 entradas
			int idEnt1 = entradaSA.altaEntrada(getTEntrada(idInv1));
			//TEntrada entrada1 = entradaSA.mostrarEntrada(idEnt1);
	
			int idEnt2 = entradaSA.altaEntrada(getTEntrada(idInv2));
			//TEntrada entrada2 = entradaSA.mostrarEntrada(idEnt2);
	
			Set<TEntrada> entradas = entradaSA.listarEntrada();
			
			boolean encontrado1 = false; 
			boolean encontrado2 = false;
			
			for(TEntrada entrada : entradas) {
				if(entrada.getId() == idEnt1)
					encontrado1 = true;
				else if(entrada.getId() == idEnt2)
					encontrado2 = true;
			}
			
			
			if(!encontrado1 || !encontrado2)
				fail("Error: en el listar");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void F_listar_entradas_por_invernadero() {
		try {
			int idInv = invernaderoSA.altaInvernadero(getTInvernadero());

			// creo 2 entradas asociadas al mismo invernadero
			int idEnt1 = entradaSA.altaEntrada(getTEntrada(idInv));
			//TEntrada entrada1 = entradaSA.mostrarEntrada(idEnt1);
	
			int idEnt2 = entradaSA.altaEntrada(getTEntrada(idInv));
			//TEntrada entrada2 = entradaSA.mostrarEntrada(idEnt2);
	
			Set<TEntrada> entradas = entradaSA.listarEntradasPorInvernadero(idInv);
			
			boolean encontrado1 = false; 
			boolean encontrado2 = false;
			
			for(TEntrada entrada : entradas) {
				if(entrada.getId() == idEnt1)
					encontrado1 = true;
				else if(entrada.getId() == idEnt2)
					encontrado2 = true;
			}
			
			if(!encontrado1 || !encontrado2)
				fail("Error: en el listar entradas por invernadero");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
}
