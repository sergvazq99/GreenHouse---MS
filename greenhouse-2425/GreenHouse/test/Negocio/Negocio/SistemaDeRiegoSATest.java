package Negocio;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.Fabricante.FabricanteSA;
import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteLocal;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.TInvernadero;
import Negocio.SistemaDeRiego.SistemaDeRiegoSA;
import Negocio.SistemaDeRiego.TSistemaDeRiego;


public class SistemaDeRiegoSATest {
	
	private static SistemaDeRiegoSA sistRiegoSA;
	private static FabricanteSA fabricanteSA;
	private static InvernaderoSA invernaderoSA;
	
	@BeforeClass
	public static void beforeClass(){
		sistRiegoSA = FactoriaNegocio.getInstance().getSistemaDeRiegoSA();
		fabricanteSA = FactoriaNegocio.getInstance().getFabricanteSA();
		invernaderoSA = FactoriaNegocio.getInstance().getInvernaderoSA();
	}
		
	private TSistemaDeRiego getTSistemaDeRiego() {
		TFabricante tFabricante = getTFabricante();
		int idFabricante = fabricanteSA.altaFabricante(tFabricante);
        return new TSistemaDeRiego(getNumRandom(), getNameRandom(), getNumRandom(), getNumRandom(), getNumRandom(), true, idFabricante);
    }
	
	private TFabricante getTFabricante() {
		TFabricanteLocal  tFabricanteLocal= new TFabricanteLocal();
		tFabricanteLocal.setActivo(true);
		tFabricanteLocal.setCodFabricante(getNameRandom());
		tFabricanteLocal.setNombre(getNameRandom());
		tFabricanteLocal.setTelefono("664112233");
		tFabricanteLocal.setImpuesto(getNumRandom());
		tFabricanteLocal.setSubvencion(getNumRandom());
		
        return tFabricanteLocal;	
	}
	
	private TInvernadero getTInvernadero() {
		TInvernadero tInvernadero = new TInvernadero();
		tInvernadero.setActivo(true);
		tInvernadero.setNombre(getNameRandom());
		tInvernadero.setSustrato(getNameRandom());
		tInvernadero.setTipo_iluminacion(getNameRandom());
		
	  	return tInvernadero;
	}
	

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

	private int getNumRandom(){
		return ThreadLocalRandom.current().nextInt(0, 10000 + 1);
	}
	
	@Test
	public void altaSistemaDeRiegoFabricanteActivo() {
		
		try {
			TFabricante fabricante = getTFabricante();
			int idFabricante = fabricanteSA.altaFabricante(fabricante);
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			tSistemaRiego.setIdFabricante(idFabricante);
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			if(idSistemaRiego < 0){
				fail("Error: altaSisRiego() debería retornar ID > 0");
			}
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void altaSistemaDeRiegoFabricanteNOActivo() {
		
		try {
			TFabricante fabricante = getTFabricante();
			int idFabricante = fabricanteSA.altaFabricante(fabricante);
			fabricanteSA.bajaFabricante(idFabricante);
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			tSistemaRiego.setIdFabricante(idFabricante);
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			if(idSistemaRiego > 0){
				fail("Error: altaSisRiego() debería retornar ID < 0 ya que fabricante inactivo");
			}
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void bajaSistemaDeRiego(){
		
		try{
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			int res = sistRiegoSA.bajaSisRiego(idSistemaRiego);
			if(res < 0){
				fail("Error: bajaSistemaDeRiego() no devuelve Id > 0");
			}
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}	
	}
	
	@Test
	public void bajaSistemaDeRiegoInexistente(){
		
		try{
			int idSistemaRiego = 999999;
			int res = sistRiegoSA.bajaSisRiego(idSistemaRiego);
			if(res != -404){
				fail("Error: bajaSistemaDeRiego() debe dar error -404");
			}
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}	
	}
	
	@Test
	public void bajaSistemaDeRiegoInactivo(){
		
		try{
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			int res = sistRiegoSA.bajaSisRiego(idSistemaRiego);
			if(res < 0){
				fail("Error: bajaSistemaDeRiego() no devuelve Id > 0");
			}
			int res2 =  sistRiegoSA.bajaSisRiego(idSistemaRiego);
			if(res2 != -2){
				fail("Error: bajaSistemaDeRiego() debe dar error -2");
			}
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}	
	}
	
	@Test
	public void mostrarSistemaDeRiego(){
		try{
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			TSistemaDeRiego sistRes = sistRiegoSA.mostrarSisRiego(idSistemaRiego);
			if(sistRes.getId() != idSistemaRiego){
				fail("Error debería mostrar el sistema de riego correcto");
			}	
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}			
	}
	
	@Test
	public void modificarSistemaDeRiego(){
		try{
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			tSistemaRiego.setCantidad_agua(getNumRandom());
			tSistemaRiego.setId(idSistemaRiego);
			int res = sistRiegoSA.modificarSisRiego(tSistemaRiego);
			if(res < 0){
				fail("Error al Modificar " + res );	
			}
			
		}catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}		
	}
	
	
	@Test
	public void listarSistemaDeRiego(){
		
		try{
			TSistemaDeRiego tSistemaRiego = getTSistemaDeRiego();
			TSistemaDeRiego tSistemaRiego2 = getTSistemaDeRiego();
	
			int idSistemaRiego = sistRiegoSA.altaSisRiego(tSistemaRiego);
			int idSistemaRiego2 = sistRiegoSA.altaSisRiego(tSistemaRiego2);
	            
	        Set<TSistemaDeRiego> sistemasDeRiego = sistRiegoSA.listarSisRiego();
		
	        boolean foundtSistemaRiego = false;
	        boolean foundtSistemaRiego2 = false;
	
	        for (TSistemaDeRiego sistema : sistemasDeRiego) {
	            if (sistema.getId() == idSistemaRiego) {
	            	foundtSistemaRiego = true;
	            } else if (sistema.getId() == idSistemaRiego2) {
	            	foundtSistemaRiego2 = true;
	            }
	        }
	        if (!foundtSistemaRiego || !foundtSistemaRiego2) {
	            fail("Error: No listo todos los sistemas");
	        }
		}catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
		
	}
	
	@Test
	public void testListarSistemaDeRiegoPorFabricante() {
	    try {
	    	
	    	
	    	TSistemaDeRiego sistRiego = getTSistemaDeRiego();
	    	Integer idFabricante = sistRiego.getIdFabricante();
			TSistemaDeRiego sistRiego2 = getTSistemaDeRiego(); 
			sistRiego2.setIdFabricante(idFabricante);

	        Integer idSistemaDeRiego = sistRiegoSA.altaSisRiego(sistRiego);
	        sistRiego.setId(idSistemaDeRiego);
	        
	        boolean encontrado = false;
	        Integer idSistemaDeRiego2 = sistRiegoSA.altaSisRiego(sistRiego2);	        
	        sistRiego2.setId(idSistemaDeRiego2);
	     
	        boolean encontrado2 = false;

	        Set<TSistemaDeRiego> sistRiegoFabricante = sistRiegoSA.listarSisRiegoPorFabricante(idFabricante);

	        for (TSistemaDeRiego sistR : sistRiegoFabricante) {
	            if (sistR.getId().equals(sistRiego.getId())) {
	            	encontrado = true;
	            } else if (sistR.getId().equals(sistRiego2.getId())) {
	            	encontrado2 = true;
	            }
	        }
	        
	        if (!encontrado || !encontrado2) {
	        	fail("Error: La lista no muestra todos los sistemas de riego");           
	        }
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}
	
	@Test
	public void testListarSistemaDeRiegoEnInvernaderos() {
	    try {
	    	TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			TSistemaDeRiego sistRiego2 = getTSistemaDeRiego(); 

	        Integer idSistemaDeRiego = sistRiegoSA.altaSisRiego(sistRiego);
	        sistRiego.setId(idSistemaDeRiego);	     
	        boolean encontrado = false;
	        Integer idSistemaDeRiego2 = sistRiegoSA.altaSisRiego(sistRiego2);	        
	        sistRiego2.setId(idSistemaDeRiego2);	        
	        boolean encontrado2 = false;
	              
	        TInvernadero tInvernadero= getTInvernadero();
	        Integer idInvernadero = invernaderoSA.altaInvernadero(tInvernadero);  
	        
	        invernaderoSA.vincularSRInvernadero(idSistemaDeRiego, idInvernadero);
	        invernaderoSA.vincularSRInvernadero(idSistemaDeRiego2, idInvernadero);
	        
	        Set<TSistemaDeRiego> sistRiegoInvernadero = sistRiegoSA.listarSisRiegoDelInvernadero(idInvernadero);

	        for (TSistemaDeRiego sistR : sistRiegoInvernadero) {
	            if (sistR.getId().equals(sistRiego.getId())) {
	            	encontrado = true;
	            } else if (sistR.getId().equals(sistRiego2.getId())) {
	            	encontrado2 = true;
	            }
	        }
	        
	        if (!encontrado || !encontrado2) {
	        	fail("Error: La lista no muestra todos los sistemas de riego");	               
	        }	     
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}

}
