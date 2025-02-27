package Negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.MarcaJPA.Marca;
import Negocio.MarcaJPA.TMarca;
import Negocio.ProveedorJPA.Proveedor;

public class MarcaSATest {

	private EntityManager em;

	@Before
	public void setUp() {
		em = EMFSingleton.getInstance().getEMF().createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Marca m WHERE m.nombre LIKE '%Test%'").executeUpdate();
		em.createQuery("DELETE FROM Proveedor p WHERE p.nombre LIKE '%Test%'").executeUpdate();
		em.getTransaction().commit();
	}

	@After
	public void tearDown() {

		em.getTransaction().begin();
		em.createQuery("DELETE FROM Marca m WHERE m.nombre LIKE '%Test%'").executeUpdate();
		em.createQuery("DELETE FROM Proveedor p WHERE p.nombre LIKE '%Test%'").executeUpdate();
		em.getTransaction().commit();

		if (em.isOpen()) {
			em.close();
		}
	}

	// Test alta
	@Test
	public void testAltaMarca() {
		Marca marca = new Marca();
		marca.setNombre("Slay Test");
		marca.setPaisOrigen("Spain");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		marca.setId(em.find(Marca.class, marca.getId()).getId());

		TMarca tMarca = new TMarca();
		tMarca.setNombre("Slay Test");
		tMarca.setPais("Spain");
		// tMarca.setActivo(true);

		int result = FactoriaNegocio.getInstance().getMarcaJPA().altaMarca(tMarca);

		assertTrue(result == -2);

	}

	// Test baja
	@Test
	public void testBajaMarca() {
		Marca marca = new Marca();
		marca.setNombre("Slay Test");
		marca.setPaisOrigen("Spain");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		Integer result = FactoriaNegocio.getInstance().getMarcaJPA().bajaMarca(marca.getId());

		assertEquals(marca.getId(), result);

	}

	// Test modificar
	@Test
	public void testModificarMarca() {
		Marca marca = new Marca();
		marca.setNombre("Slay Test");
		marca.setPaisOrigen("Spain");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		TMarca tMarca = new TMarca();
		tMarca.setId(marca.getId());
		tMarca.setNombre("Slay Test");
		tMarca.setPais("Spain");

		Integer result = FactoriaNegocio.getInstance().getMarcaJPA().modificarMarca(tMarca);

		assertEquals(marca.getId(), result);
	}

	// Test mostrar por id
	@Test
	public void testMostrarMarcaPorId() {
		Marca marca = new Marca();
		marca.setNombre("Slay Test");
		marca.setPaisOrigen("Spain");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		TMarca tMarca = FactoriaNegocio.getInstance().getMarcaJPA().mostrarMarcaPorId(marca.getId());

		assertTrue(tMarca.getId() > 0);

	}

	// Test listar marcas
	@Test
	public void testListarMarcas() {
		Marca marca = new Marca();
		marca.setNombre("Ok Test");
		marca.setPaisOrigen("Spain");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();
		
		Set<TMarca> result = FactoriaNegocio.getInstance().getMarcaJPA().listarMarcas();
		
		boolean encontrado = false;
		for(TMarca m : result) {
			if(m.getNombre().equals(marca.getNombre())) {
				encontrado = true;
			}
		}
		
		assertTrue(encontrado);
	}

	// Test listar marcas
	@Test
	public void testListarMarcasPorProveedor() {
		Marca marca = new Marca();
		marca.setNombre("Slay Test");
		marca.setPaisOrigen("Spain");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();
		
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);
		
		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();
		
		FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());

		Set<TMarca> result = FactoriaNegocio.getInstance().getMarcaJPA().listarMarcasPorProveedor(proveedor.getId());

		assertEquals(1, result.size());
	}
}
