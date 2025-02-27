package Negocio;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.Fabricante.FabricanteSA;
import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteExtranjero;
import Negocio.Fabricante.TFabricanteLocal;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.TInvernadero;
import Negocio.SistemaDeRiego.SistemaDeRiegoSA;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

public class FabricanteSATest {

	private static SistemaDeRiegoSA sistRiegoSA;
	private static FabricanteSA fabricanteSA;
	private static InvernaderoSA invernaderoSA;

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

	private int getNumRandom() {
		return ThreadLocalRandom.current().nextInt(0, 10000 + 1);
	}

	private String getTelRamdom() {
		String caracteres = "0123456789";
		StringBuilder nombreAleatorio = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 9; i++) {
			int index = random.nextInt(caracteres.length());
			nombreAleatorio.append(caracteres.charAt(index));
		}
		return nombreAleatorio.toString();
	}

	private TSistemaDeRiego getTSistemaDeRiego(int idFab) {
		return new TSistemaDeRiego(getNumRandom(), getNameRandom(), getNumRandom(), getNumRandom(), getNumRandom(),
				true, idFab);
	}

	private TFabricante getTFabricanteLocal() {
		TFabricanteLocal tFabricanteLocal = new TFabricanteLocal();
		tFabricanteLocal.setActivo(true);
		tFabricanteLocal.setCodFabricante(getNameRandom());
		tFabricanteLocal.setNombre(getNameRandom());
		tFabricanteLocal.setTelefono(getTelRamdom());
		tFabricanteLocal.setImpuesto(getNumRandom());
		tFabricanteLocal.setSubvencion(getNumRandom());
		
		return tFabricanteLocal;
	}
	
	private TFabricante getTFabricanteExtranjero() {
		TFabricanteExtranjero tFabricanteExtranjero = new TFabricanteExtranjero();
		tFabricanteExtranjero.setActivo(true);
		tFabricanteExtranjero.setCodFabricante(getNameRandom());
		tFabricanteExtranjero.setNombre(getNameRandom());
		tFabricanteExtranjero.setTelefono(getTelRamdom());
		tFabricanteExtranjero.setPaisDeOrigen(getNameRandom());
		tFabricanteExtranjero.setAranceles(getNumRandom());
		
		return tFabricanteExtranjero;
	}

	private TInvernadero getTInvernadero() {
		TInvernadero tInvernadero = new TInvernadero();
		tInvernadero.setActivo(true);
		tInvernadero.setNombre(getNameRandom());
		tInvernadero.setSustrato(getNameRandom());
		tInvernadero.setTipo_iluminacion(getNameRandom());

		return tInvernadero;
	}
	
	@BeforeClass
	public static void beforeClass() {
		sistRiegoSA = FactoriaNegocio.getInstance().getSistemaDeRiegoSA();
		fabricanteSA = FactoriaNegocio.getInstance().getFabricanteSA();
		invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();
	}

	@Test
	public void TestAltaFabricanteLocal() {

		try {
			if (fabricanteSA.altaFabricante(getTFabricanteLocal()) < 0)
				fail("Error: altaFabricante() debería devolver un entero positivo");
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}

	}
	
	@Test
	public void TestAltaFabricanteExtranjero() {

		try {
			if (fabricanteSA.altaFabricante(getTFabricanteExtranjero()) < 0)
				fail("Error: altaFabricante() debería devolver un entero positivo");
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}

	}

	@Test
	public void TestBajaFabricante() {

		try {
			if (fabricanteSA.bajaFabricante(fabricanteSA.altaFabricante(getTFabricanteLocal())) < 0) {
				fail("Error: bajaFabricante() debería devolver un entero positivo");
			}
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void TestBajaFabricanteInexistente() {

		try {
			int res = fabricanteSA.bajaFabricante(999999);
			if (res != -3) {
				fail("Error: bajaFabricante() debe dar error -3");
			}
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void TestBajaFabricanteInactivo() {

		try {
			int res = fabricanteSA.bajaFabricante(fabricanteSA.altaFabricante(getTFabricanteLocal()));
			if (res < 0)
				fail("Error: bajaFabricante() debería devolver un entero positivo");

			if ( fabricanteSA.bajaFabricante(res) != -2)
				fail("Error: bajaFabricante() debe dar error -2");

		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestMostrarFabricante(){
		try{
			int id1 = fabricanteSA.altaFabricante(getTFabricanteLocal());
			TFabricante tf = fabricanteSA.mostrarFabricantePorId(id1);
			if(tf.getId() != id1){
				fail("Error debería mostrar el sistema de riego correcto");
			}	
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}			
	}

	@Test
	public void TestModificarSistemaDeRiego(){
		try{
			TFabricante tf = getTFabricanteLocal();
			fabricanteSA.altaFabricante(tf);
			tf.setCodFabricante(getNumRandom() + getNameRandom());
			int res = fabricanteSA.modificarFabricante(tf);
			if(res < 0){
				fail("Error al Modificar " + res );	
			}
			
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}		
	}
	
	@Test
	public void TestListarFabricantes(){
		
		try{
			TFabricante tf1 = getTFabricanteLocal();
			TFabricante tf2 = getTFabricanteExtranjero();
	
			int id1 = fabricanteSA.altaFabricante(tf1);
			int id2 = fabricanteSA.altaFabricante(tf2);
	            
	        Set<TFabricante> fabricantes = fabricanteSA.listarFabricantes();
		
	        boolean encontrado1 = false;
	        boolean encontrado2 = false;
	
	        for (TFabricante sistema : fabricantes) {
	            if (sistema.getId() == id1) {
	            	encontrado1 = true;
	            } else if (sistema.getId() == id2) {
	            	encontrado2 = true;
	            }
	        }
	        if (!encontrado1 || !encontrado2) {
	            fail("Error: No listo todos los sistemas");
	        }
		}catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
		
	}
	
	@Test
	public void TestListarFabricantesLocales(){
		
		try{
			TFabricante tf1 = getTFabricanteLocal();
			TFabricante tf2 = getTFabricanteLocal();
	
			int id1 = fabricanteSA.altaFabricante(tf1);
			int id2 = fabricanteSA.altaFabricante(tf2);
	            
	        Set<TFabricante> fabricantes = fabricanteSA.listarFabricantesLocales();
		
	        boolean encontrado1 = false;
	        boolean encontrado2 = false;
	
	        for (TFabricante sistema : fabricantes) {
	            if (sistema.getId() == id1) {
	            	encontrado1 = true;
	            } else if (sistema.getId() == id2) {
	            	encontrado2 = true;
	            }
	        }
	        if (!encontrado1 || !encontrado2) {
	            fail("Error: No listo todos los sistemas");
	        }
		}catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
		
	}
	
	@Test
	public void TestListarFabricantesExtranjeros(){
		
		try{
			TFabricante tf1 = getTFabricanteExtranjero();
			TFabricante tf2 = getTFabricanteExtranjero();
	
			int id1 = fabricanteSA.altaFabricante(tf1);
			int id2 = fabricanteSA.altaFabricante(tf2);
	            
	        Set<TFabricante> fabricantes = fabricanteSA.listarFabricantesExtranjeros();
		
	        boolean encontrado1 = false;
	        boolean encontrado2 = false;
	
	        for (TFabricante sistema : fabricantes) {
	            if (sistema.getId() == id1) {
	            	encontrado1 = true;
	            } else if (sistema.getId() == id2) {
	            	encontrado2 = true;
	            }
	        }
	        if (!encontrado1 || !encontrado2) {
	            fail("Error: No listo todos los sistemas");
	        }
		}catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
		
	}
	
	@Test
	public void TestListarListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero() {
		
		try{
			TFabricante tf1 = getTFabricanteExtranjero();
			TFabricante tf2 = getTFabricanteExtranjero();
	
			int id1 = fabricanteSA.altaFabricante(tf1);
			int id2 = fabricanteSA.altaFabricante(tf2);
	        
			TSistemaDeRiego ts1 = getTSistemaDeRiego(id1);
			int ids1 = sistRiegoSA.altaSisRiego(ts1);
			
			TSistemaDeRiego ts2 = getTSistemaDeRiego(id2);
			int ids2 = sistRiegoSA.altaSisRiego(ts2);
			
			TInvernadero ti = getTInvernadero();
			
			int idi = invernaderoSA.altaInvernadero(ti);
			
			invernaderoSA.vincularSRInvernadero(ids1, idi);
			invernaderoSA.vincularSRInvernadero(ids2, idi);
			

	        Set<TFabricante> fabricantes = fabricanteSA.listarFabricantesPorInvernadero(idi);
		
	        boolean encontrado1 = false;
	        boolean encontrado2 = false;
	
	        for (TFabricante sistema : fabricantes) {
	            if (sistema.getId().equals(id1)) {
	            	encontrado1 = true;
	            } else if (sistema.getId().equals(id2)) {
	            	encontrado2 = true;
	            }
	        }
	        if (!encontrado1 || !encontrado2) {
	            fail("Error: No listo todos los sistemas");
	        }
		}catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}
}
