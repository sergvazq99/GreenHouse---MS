package Integracion;



import static org.junit.Assert.*;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.Test;

import Integracion.Fabricante.FabricanteDAO;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Invernadero.TieneDAO;
import Integracion.SistemaDeRiego.SistemaDeRiegoDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteLocal;
import Negocio.Invernadero.TInvernadero;
import Negocio.Invernadero.TTiene;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

public class SistemaDeRiegoDAOTest {
	
	private static SistemaDeRiegoDAO sistemaRiegoDAO;
	private static FabricanteDAO fabricanteDAO;
	private static InvernaderoDAO invernaderoDAO;
	private static TieneDAO tieneDAO;
	
	
	
    // Comparar dos objetos TSistemaDeRiego
    private boolean equals(TSistemaDeRiego s1, TSistemaDeRiego s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.getId().equals(s2.getId()) &&
               s1.getNombre().equals(s2.getNombre()) &&
               s1.getPotenciaRiego().equals(s2.getPotenciaRiego()) &&
               s1.getCantidad_agua().equals(s2.getCantidad_agua()) &&
               s1.getFrecuencia().equals(s2.getFrecuencia()) &&
               s1.getActivo().equals(s2.getActivo()) &&
               s1.getIdFabricante().equals(s2.getIdFabricante());
    }

    // Crear un sistema de riego con valores predeterminados
    private TSistemaDeRiego getTSistemaDeRiego() {
		TFabricante tFabricante = getTFabricante();
		int idFabricante = fabricanteDAO.altaFabricante(tFabricante);
        return new TSistemaDeRiego(getNumRandom(), getNameRandom(), getNumRandom(), getNumRandom(), getNumRandom(), true, idFabricante);
    }
    
	private TFabricante getTFabricante() {
		TFabricanteLocal  tFabricanteLocal= new TFabricanteLocal();
		tFabricanteLocal.setActivo(true);
		tFabricanteLocal.setCodFabricante(getNameRandom());
		tFabricanteLocal.setNombre(getNameRandom());
		tFabricanteLocal.setTelefono("111222333");
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
    
    private TTiene getTTiene(int idSistRiego, int idInvernadero){
    	TTiene tTiene = new TTiene();
    	tTiene.setId_Invernadero(idInvernadero);
    	tTiene.setId_SistemasDeRiego(idSistRiego);
    	return tTiene;
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

	private Transaccion crearTransaccion() {
		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			tManager.newTransaccion();
			return tManager.getTransaccion();
		} catch (Exception e) {
			fail("Error transaccional");
			return null;
		}
	}
	
	@BeforeClass
	public static void beforeClass() {
		sistemaRiegoDAO = FactoriaIntegracion.getInstance().getSistemaDeRiegoDAO();
		fabricanteDAO =	FactoriaIntegracion.getInstance().getFabricanteDAO();
		invernaderoDAO = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		tieneDAO = FactoriaIntegracion.getInstance().getDaoTiene();
	}
	
	

	@Test
	public void testAltaSistemaDeRiego() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
			if (idSistemaDeRiego < 0) {
				trans.rollback();
				fail("Error: altaSistemaDeRiego() deberia retornar ID > 0");
			}
			trans.commit(); 
		} catch (Exception e) {
			
			fail("Excepción");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBajaSistemaDeRiego() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
			Integer result = sistemaRiegoDAO.bajaSistemaDeRiego(idSistemaDeRiego);
			if (result < 0) {
				trans.rollback();
				fail("Error: bajaSistemaDeRiego() deberia retornar un n�mero positivo");
			}
			trans.commit(); 
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMostrarSistemaDeRiego() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
			sistRiego.setId(idSistemaDeRiego);

			if (!equals(sistRiego, sistemaRiegoDAO.mostrarSistemaDeRiegoPorID(sistRiego.getId()))) {
				trans.rollback();
				fail("Error: mostrarSistemaDeRiegoPorID() sistRiego no coincide con el mostrado");
			}

			trans.commit(); 
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testModificarSistemaDeRiego() {
	    try {
	    	Transaccion trans = crearTransaccion();
			trans.start();
			TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
			sistRiego.setId(idSistemaDeRiego);
	        
			sistRiego.setNombre(getNameRandom());
			sistRiego.setActivo(false);

	        Integer resultado = sistemaRiegoDAO.modificarSistemaDeRiego(sistRiego);

	        if (resultado < 0) {
	        	trans.rollback();
	            fail("Error: modificarSistemaDeRiego() no devuelve numero postivo");
	        }

	        TSistemaDeRiego sistRiegoMod = sistemaRiegoDAO.mostrarSistemaDeRiegoPorID(sistRiego.getId());
	        if (!sistRiego.getNombre().equals(sistRiegoMod.getNombre())
	                || !sistRiego.getActivo().equals(sistRiegoMod.getActivo())) {
	        	trans.rollback();
	            fail("Error: Los atributos del sistemadeRiego no coinciden");
	        }

	        trans.commit();
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}
	
	@Test
	public void testListarSistemaDeRiego() {
	    try {
	    	Transaccion trans = crearTransaccion();
			trans.start();

			TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			TSistemaDeRiego sistRiego2 = getTSistemaDeRiego(); 

	        Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
	        sistRiego.setId(idSistemaDeRiego);
	        boolean encontrado = false;
	        Integer idSistemaDeRiego2 = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego2);	        
	        sistRiego2.setId(idSistemaDeRiego2);
	        boolean encontrado2 = false;
	       	     
	        Set<TSistemaDeRiego> sistemasRiego = sistemaRiegoDAO.listarSistemaDeRiego();
	       
	        for (TSistemaDeRiego sistR : sistemasRiego) {
	            if (sistR.getId().equals(sistRiego.getId())) {
	            	encontrado = true;
	            } else if (sistR.getId().equals(sistRiego2.getId())) {
	            	encontrado2 = true;
	            }
	        }

	        if (!encontrado || !encontrado2) {
	        	fail("Error: La lista no muestra todos los sistemas de riego");
	            trans.rollback();	            
	        }

	        trans.commit();
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}
	
	@Test
	public void testListarSistemaDeRiegoPorFabricante() {
	    try {
	    	Transaccion trans = crearTransaccion();
	    	trans.start();
	    	
	    	TSistemaDeRiego sistRiego = getTSistemaDeRiego();
	    	Integer idFabricante = sistRiego.getIdFabricante();
			TSistemaDeRiego sistRiego2 = getTSistemaDeRiego(); 
			sistRiego2.setIdFabricante(idFabricante);

	        Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
	        sistRiego.setId(idSistemaDeRiego);
	        sistRiego.setIdFabricante(idFabricante);
	        boolean encontrado = false;
	        Integer idSistemaDeRiego2 = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego2);	        
	        sistRiego2.setId(idSistemaDeRiego2);
	        sistRiego2.setIdFabricante(idFabricante);
	        boolean encontrado2 = false;

	        Set<TSistemaDeRiego> sistRiegoFabricante = sistemaRiegoDAO.listarSistemaDeRiegoPorFabricante(idFabricante);

	        for (TSistemaDeRiego sistR : sistRiegoFabricante) {
	            if (sistR.getId().equals(sistRiego.getId())) {
	            	encontrado = true;
	            } else if (sistR.getId().equals(sistRiego2.getId())) {
	            	encontrado2 = true;
	            }
	        }
	        
	        if (!encontrado || !encontrado2) {
	        	fail("Error: La lista no muestra todos los sistemas de riego");
	            trans.rollback();	            
	        }

	        trans.commit();
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}
	
	@Test
	public void testListarSistemaDeRiegoEnInvernaderos() {
	    try {
	    	Transaccion trans = crearTransaccion();
	    	trans.start();	    	
	    	
	    	TSistemaDeRiego sistRiego = getTSistemaDeRiego();
			TSistemaDeRiego sistRiego2 = getTSistemaDeRiego(); 

	        Integer idSistemaDeRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
	        sistRiego.setId(idSistemaDeRiego);	     
	        boolean encontrado = false;
	        Integer idSistemaDeRiego2 = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego2);	        
	        sistRiego2.setId(idSistemaDeRiego2);	        
	        boolean encontrado2 = false;
	        
	        TInvernadero tInvernadero= getTInvernadero();
	        Integer idInvernadero = invernaderoDAO.altaInvernadero(tInvernadero);  

	        tieneDAO.altaTiene(getTTiene(idSistemaDeRiego, idInvernadero));
	        tieneDAO.altaTiene(getTTiene(idSistemaDeRiego2, idInvernadero));
	        
	        Set<TSistemaDeRiego> sistRiegoInvernadero = sistemaRiegoDAO.listarSistemaDeRiegoInvernadero(idInvernadero);

	        for (TSistemaDeRiego sistR : sistRiegoInvernadero) {
	            if (sistR.getId().equals(sistRiego.getId())) {
	            	encontrado = true;
	            } else if (sistR.getId().equals(sistRiego2.getId())) {
	            	encontrado2 = true;
	            }
	        }
	        
	        if (!encontrado || !encontrado2) {
	        	fail("Error: La lista no muestra todos los sistemas de riego");
	            trans.rollback();	            
	        }

	        trans.commit();
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}
	
	@Test
	public void testNombreUnico() {
	    try {
	    	Transaccion trans = crearTransaccion();
	    	trans.start();

	       
	        TSistemaDeRiego sistRiego = getTSistemaDeRiego();
	        String nombre = sistRiego.getNombre();

	        Integer idSisRiego = sistemaRiegoDAO.altaSistemaDeRiego(sistRiego);
	        sistRiego.setId(idSisRiego);
	        
	        if (!equals(sistRiego, sistemaRiegoDAO.leerPorNombreUnico(nombre))) {
				trans.rollback();
				fail("Error: leerPorNombreUnico() sistRiego no coincide con el mostrado");
			}

	        trans.commit();
	    } catch (Exception e) {
	        fail("Excepción");
	        e.printStackTrace();
	    }
	}

}
