package Negocio;

import static org.junit.Assert.fail;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.Invernadero.TInvernadero;
import Negocio.Planta.PlantaSA;
import Negocio.Planta.TPlanta;
import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantaSATest {
	
	private int getIntRandom() {
		return ThreadLocalRandom.current().nextInt(0,2);
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
	
	
	private TPlanta getPlanta(int idInv) {
		
		int tipo = this.getIntRandom();
		
		if(tipo == 1){
			
			TPlantaNoFrutal planta = new TPlantaNoFrutal(
					this.getNameRandom(),
					this.getNameRandom(),
					1,
					idInv,
					this.getNameRandom()
					);

			return planta;
		}
		else{
			TPlantaFrutal planta = new TPlantaFrutal(
					this.getNameRandom(),
					this.getNameRandom(),
					0,
					idInv,
					this.getNameRandom(),
					this.getNameRandom());
						
			return planta;
		}

	}
	
	
	private TInvernadero getTInvernadero() {
		TInvernadero invernadero = new TInvernadero();
		invernadero.setActivo(true);
		invernadero.setNombre(getNameRandom());
		invernadero.setSustrato(getNameRandom());
		invernadero.setTipo_iluminacion(getNameRandom());
		
		return invernadero;
		
	}
	
	
	@Test
	public void A_Alta() {
		try {
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			TPlanta planta = this.getPlanta(idInv);

			if(neg.getPlantaSA().altaPlanta(planta) < 0)
				fail("Error: debe devolver un entero positivo");
		
		} catch (Exception e) { 
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	@Test
	public void B_Baja() {
		try {
			
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			TPlanta planta = this.getPlanta(idInv);
			PlantaSA SA = neg.getPlantaSA();
			
			if(SA.bajaPlanta(SA.altaPlanta(planta)) < 0)
				fail("Error: debe devolver un entero positivo");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void C_modificar() {
		
		try {
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			
			TPlanta planta = this.getPlanta(idInv);
			PlantaSA SA = neg.getPlantaSA();
			
			planta.set_id(SA.altaPlanta(planta));
			
			planta.set_nombre(getNameRandom());
			
			
			if(SA.modificarPlanta(planta) < 0)
				fail("Error: debe devolver un entero positivo");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	@Test
	public void D_mostrar() {
		try {
			
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			TPlanta planta = this.getPlanta(idInv);
			PlantaSA SA = neg.getPlantaSA();
			int idPlanta = SA.altaPlanta(planta);
			planta.set_id(idPlanta);
	
			if(idPlanta != SA.mostrarPlantaPorId(idPlanta).get_id())
				fail("Error: debe tener el mismo valor ");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void E_listar_plantas() {
		try {
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			
			TPlanta uno = this.getPlanta(idInv);
			TPlanta dos = this.getPlanta(idInv);
			
			PlantaSA SA = neg.getPlantaSA();
			
			uno.set_id(SA.altaPlanta(uno));
			dos.set_id(SA.altaPlanta(dos));
	
			Set<TPlanta> lista = SA.listarPlanta();
			
			boolean ent1 = false, ent2 = false;
			
			for(TPlanta p : lista) {
				if(p.get_id() == uno.get_id())
					ent1 = true;
				else if(p.get_id() == dos.get_id())
					ent2 = true;
			}
			
			
			if(!ent1 || !ent2)
				fail("Error: en el listar");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void F_listar_plantas_por_invernadero() {
		try {
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			
			TPlanta uno = this.getPlanta(idInv);
			TPlanta dos = this.getPlanta(idInv);
			
			PlantaSA SA = neg.getPlantaSA();
			
			uno.set_id(SA.altaPlanta(uno));
			dos.set_id(SA.altaPlanta(dos));
	
			Set<TPlanta> lista = SA.listarPlantasPorInvernadero(idInv);
			
			boolean ent1 = false, ent2 = false;
			
			for(TPlanta p : lista) {
				if(p.get_id() == uno.get_id())
					ent1 = true;
				else if(p.get_id() == dos.get_id())
					ent2 = true;
			}
			
			
			if(!ent1 || !ent2)
				fail("Error: en el listar");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void F_listar_plantas_por_tipo() {
		try {
			FactoriaNegocio neg = FactoriaNegocio.getInstance();
			int idInv = neg.getInvernaderoSA().altaInvernadero(getTInvernadero());
			
			TPlantaNoFrutal uno = new TPlantaNoFrutal(
					this.getNameRandom(),
					this.getNameRandom(),
					1,
					idInv,
					this.getNameRandom()
					);
		TPlantaNoFrutal dos = new TPlantaNoFrutal(
					this.getNameRandom(),
					this.getNameRandom(),
					1,
					idInv,
					this.getNameRandom()
					);;
			
			PlantaSA SA = neg.getPlantaSA();
			
			uno.set_id(SA.altaPlanta(uno));
			dos.set_id(SA.altaPlanta(dos));
	
			Set<TPlanta> lista = SA.listarPlantasPorTipo("No Frutal");
			
			boolean ent1 = false, ent2 = false;
			
			for(TPlanta p : lista) {
				if(p.get_id() == uno.get_id())
					ent1 = true;
				else if(p.get_id() == dos.get_id())
					ent2 = true;
			}
			
			
			if(!ent1 || !ent2)
				fail("Error: en el listar");
		
		} catch (Exception e) {
			fail("Excepcion");
			e.printStackTrace();
		}
	}
	
	

}
