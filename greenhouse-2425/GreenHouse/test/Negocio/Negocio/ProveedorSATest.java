package Negocio;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.FactoriaNegocio.FactoriaNegocio;
import Negocio.ProveedorJPA.Proveedor;
import Negocio.ProveedorJPA.TProveedor;
import Negocio.MarcaJPA.Marca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.*;

import static org.junit.Assert.*;

public class ProveedorSATest {

	private EntityManager em;

	@Before
	public void setUp() {
		em = EMFSingleton.getInstance().getEMF().createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Proveedor p WHERE p.nombre LIKE '%Test%'").executeUpdate();
		em.createQuery("DELETE FROM Marca m WHERE m.nombre LIKE '%Test%'").executeUpdate();
		em.getTransaction().commit();
	}

	@After
	public void tearDown() {
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Proveedor p WHERE p.nombre LIKE '%Test%'").executeUpdate();
		em.createQuery("DELETE FROM Marca m WHERE m.nombre LIKE '%Test%'").executeUpdate();
		em.getTransaction().commit();

		if (em.isOpen()) {
			em.close();
		}
	}

	// TESTS ALTA
	@Test
	public void testAltaProveedorCamposIncompletos() {
		TProveedor tProv = new TProveedor();
		tProv.setCIF("");
		tProv.setNombre("Proveedor Test");
		tProv.setTelefono("123456789");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().altaProveedor(tProv);

		assertEquals(-2, result); // Código de error por campos incompletos
	}

	@Test
	public void testAltaProveedorTelefonoInvalido() {
		TProveedor tProv = new TProveedor();
		tProv.setCIF("12345678A");
		tProv.setNombre("Proveedor Test");
		tProv.setTelefono("12345");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().altaProveedor(tProv);

		assertEquals(-3, result); // Código de error por teléfono inválido
	}

	@Test
	public void testAltaProveedorExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = new TProveedor();
		tProv.setCIF("12345678A");
		tProv.setNombre("Proveedor Test");
		tProv.setTelefono("123456789");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().altaProveedor(tProv);

		assertTrue(result == -4);

	}

	@Test
	public void testAltaReactivarProveedorExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(false);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		proveedor.setId(em.find(Proveedor.class, proveedor.getId()).getId());

		TProveedor tProv = new TProveedor();
		tProv.setCIF("12345678A");
		tProv.setNombre("Proveedor Test");
		tProv.setTelefono("123456789");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().altaProveedor(tProv);

		assertTrue(result == proveedor.getId());

	}

	@Test
	public void testAltaProveedorNoExitoso() {
		TProveedor tProv = new TProveedor();
		tProv.setCIF("12345678A");
		tProv.setNombre("Proveedor Test");
		tProv.setTelefono("123456789");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().altaProveedor(tProv);

		assertTrue(result > 0); // ID del nuevo proveedor

		Proveedor proveedor = em.find(Proveedor.class, result);
		assertNotNull(proveedor);
		assertEquals("12345678A", proveedor.getCIF());
	}

	// TESTS BAJA
	@Test
	public void testBajaProveedorNoExiste() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(false);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().bajaProveedor(proveedor.getId() + 1);

		assertEquals(-4, result); // Código de error porque no existe el proveedor
	}

	@Test
	public void testBajaProveedorExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Integer result = FactoriaNegocio.getInstance().getProveedorJPA().bajaProveedor(proveedor.getId());

		assertEquals(proveedor.getId(), result);

	}

	@Test
	public void testBajaProveedorNoExistoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(false);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().bajaProveedor(proveedor.getId());

		assertEquals(-3, result); // Código de error porque no existe el proveedor
	}

	@Test
	public void testBajaProveedorVinculado() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());

		int result = FactoriaNegocio.getInstance().getProveedorJPA().bajaProveedor(proveedor.getId());

		FactoriaNegocio.getInstance().getProveedorJPA().desvincularMarca(proveedor.getId(), marca.getId());

		assertEquals(-5, result); // Código de error porque no existe el proveedor
	}

	// TESTS MODIFICAR
	@Test
	public void testModificarProveedorExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = new TProveedor();
		tProv.setId(proveedor.getId());
		tProv.setCIF("87654321B");
		tProv.setNombre("Proveedor Modificado Test");
		tProv.setTelefono("987654321");

		Integer result = FactoriaNegocio.getInstance().getProveedorJPA().modificarProveedor(tProv);

		assertEquals(proveedor.getId(), result);
	}

	@Test
	public void testModificarProveedorNoExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(false);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = new TProveedor();
		tProv.setId(proveedor.getId());
		tProv.setCIF("87654321B");
		tProv.setNombre("Proveedor Modificado Test");
		tProv.setTelefono("987654321");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().modificarProveedor(tProv);

		assertEquals(-4, result);
	}

	@Test
	public void testModificarProveedorNoExitosoTelefono() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = new TProveedor();
		tProv.setId(proveedor.getId());
		tProv.setCIF("87654321B");
		tProv.setNombre("Proveedor Modificado Test");
		tProv.setTelefono("98765432");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().modificarProveedor(tProv);

		assertEquals(-3, result);
	}

	@Test
	public void testModificarProveedorNoExitosoCamposVacios() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = new TProveedor();
		tProv.setId(proveedor.getId());
		tProv.setCIF("87654321B");
		tProv.setNombre("Proveedor Modificado Test");
		tProv.setTelefono("");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().modificarProveedor(tProv);

		assertEquals(-2, result);
	}

	@Test
	public void testModificarProveedorNoExitosoCIFExistente() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Proveedor proveedor2 = new Proveedor();
		proveedor2.setCIF("12345678B");
		proveedor2.setNombre("Proveedor Test");
		proveedor2.setTelefono("123456789");
		proveedor2.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor2);
		em.getTransaction().commit();

		TProveedor tProv = new TProveedor();
		tProv.setId(proveedor.getId());
		tProv.setCIF("12345678B");
		tProv.setNombre("Proveedor Modificado Test");
		tProv.setTelefono("987654321");

		int result = FactoriaNegocio.getInstance().getProveedorJPA().modificarProveedor(tProv);

		assertEquals(-5, result);
	}

	// TEST LISTAR
	@Test
	public void testListarProveedor() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Set<TProveedor> result = FactoriaNegocio.getInstance().getProveedorJPA().listarProveedor();
		boolean encontrado = false;
		for (TProveedor p : result) {
			if (p.getNombre().equals(proveedor.getNombre())) {
				encontrado = true;
			}
		}

		assertTrue(encontrado);
	}

	// TESTS MOSTRAR POR ID
	@Test
	public void testMostrarPorIdExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = FactoriaNegocio.getInstance().getProveedorJPA().mostrarProveedorPorID(proveedor.getId());

		assertTrue(tProv.getId() > 0);
	}

	@Test
	public void testMostrarPorIdNoExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = FactoriaNegocio.getInstance().getProveedorJPA().mostrarProveedorPorID(proveedor.getId() + 1);

		assertTrue(tProv.getId() <= 0);
	}

	// TESTS MOSTRAR POR CIF
	@Test
	public void testMostrarPorCIFExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		TProveedor tProv = FactoriaNegocio.getInstance().getProveedorJPA().mostrarProveedorPorCIF(proveedor.getCIF());

		assertTrue(tProv.getId() > 0);
	}

	@Test
	public void testMostrarPorCIFNoExitoso() {

		String CIF = "12345678A";
		TProveedor tProv = FactoriaNegocio.getInstance().getProveedorJPA().mostrarProveedorPorCIF(CIF);

		assertTrue(tProv.getId() <= 0);
	}

	// TESTS VINCULAR
	@Test
	public void testVincularExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());
		assertEquals(1, result);
	}

	@Test
	public void testVincularNoExitosoYaVinculados() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());

		int result = FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());
		assertEquals(-5, result);
	}

	@Test
	public void testVincularNoExitosoMarcaInexistente() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(false);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());
		assertEquals(-4, result);
	}

	@Test
	public void testVincularNoExitosoProveedorInexistente() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(false);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());
		assertEquals(-3, result);
	}

	// TESTS DESVINCULAR
	@Test
	public void testDesvincularExitoso() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();
		FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());

		int result = FactoriaNegocio.getInstance().getProveedorJPA().desvincularMarca(proveedor.getId(), marca.getId());
		assertEquals(1, result);
	}

	@Test
	public void testDesvincularNoExitosoNoVinculados() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().desvincularMarca(proveedor.getId(), marca.getId());
		assertEquals(-5, result);
	}

	@Test
	public void testDesvincularNoExitosoMarcaInexistente() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(false);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().desvincularMarca(proveedor.getId(), marca.getId());
		assertEquals(-4, result);
	}

	@Test
	public void testDesvincularNoExitosoProveedorInexistente() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(false);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		int result = FactoriaNegocio.getInstance().getProveedorJPA().desvincularMarca(proveedor.getId(), marca.getId());
		assertEquals(-3, result);
	}

	// TESTS LISTAR POR MARCA
	@Test
	public void testListarProveedorPorMarca() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCIF("12345678A");
		proveedor.setNombre("Proveedor Test");
		proveedor.setTelefono("123456789");
		proveedor.setActivo(true);

		em.getTransaction().begin();
		em.persist(proveedor);
		em.getTransaction().commit();

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		FactoriaNegocio.getInstance().getProveedorJPA().vincularMarca(proveedor.getId(), marca.getId());

		Set<TProveedor> result = FactoriaNegocio.getInstance().getProveedorJPA()
				.listarProveedoresDeMarca(marca.getId());

		assertEquals(1, result.size());
	}

	@Test
	public void testListarProveedorPorMarcaVacio() {

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		Set<TProveedor> result = FactoriaNegocio.getInstance().getProveedorJPA()
				.listarProveedoresDeMarca(marca.getId());

		assertEquals(0, result.size());
	}

	@Test
	public void testListarProveedorPorMarcaNoExiste() {

		Marca marca = new Marca();
		marca.setNombre("Marca Test");
		marca.setPaisOrigen("Pais Test");
		marca.setActivo(true);

		em.getTransaction().begin();
		em.persist(marca);
		em.getTransaction().commit();

		Set<TProveedor> list = FactoriaNegocio.getInstance().getProveedorJPA()
				.listarProveedoresDeMarca(marca.getId() + 1);

		int result = list.iterator().next().getId();
		assertEquals(-3, result);
	}

}
