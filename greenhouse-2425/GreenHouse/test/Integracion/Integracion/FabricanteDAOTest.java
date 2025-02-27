package Integracion;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.Test;

import Integracion.Fabricante.FabricanteDAO;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.FactoriaQuery.FactoriaQuery;
import Integracion.FactoriaQuery.Query;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Invernadero.TieneDAO;
import Integracion.SistemaDeRiego.SistemaDeRiegoDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteExtranjero;
import Negocio.Fabricante.TFabricanteLocal;
import Negocio.Invernadero.TInvernadero;
import Negocio.Invernadero.TTiene;
import Negocio.SistemaDeRiego.TSistemaDeRiego;

public class FabricanteDAOTest {

	private static SistemaDeRiegoDAO sistemaRiegoDAO;
	private static FabricanteDAO fabricanteDAO;
	private static InvernaderoDAO invernaderoDAO;
	private static TieneDAO tieneDAO;
	private static Query query;

	private boolean equals(TFabricante t1, TFabricante t2) {
		if (t1 == null || t2 == null)
			return false;
		return t1.getId().equals(t2.getId()) && t1.getNombre().equals(t2.getNombre())
				&& t1.getCodFabricante().equals(t2.getCodFabricante()) && t1.getTelefono().equals(t2.getTelefono())
				&& t1.getActivo().equals(t2.getActivo());
	}

	private TFabricante getTFabricanteLocal() {
		TFabricanteLocal tf = new TFabricanteLocal();
		tf.setActivo(true);
		tf.setCodFabricante(getNameRandom() + getNumRandom());
		tf.setId(getNumRandom());
		tf.setNombre(getNameRandom());
		tf.setTelefono(getTelRamdom());
		tf.setImpuesto(getNumRandom());
		tf.setSubvencion(getNumRandom());
		return tf;
	}

	private TFabricante getTFabricanteExtranjero() {
		TFabricanteExtranjero tf = new TFabricanteExtranjero();
		tf.setActivo(true);
		tf.setCodFabricante(getNameRandom() + getNumRandom());
		tf.setId(getNumRandom());
		tf.setNombre(getNameRandom());
		tf.setTelefono(getTelRamdom());
		tf.setPaisDeOrigen(getNameRandom());
		tf.setAranceles(getNumRandom());
		return tf;
	}

	private TInvernadero getTInvernadero() {
		TInvernadero tInvernadero = new TInvernadero();
		tInvernadero.setActivo(true);
		tInvernadero.setNombre(getNameRandom());
		tInvernadero.setSustrato(getNameRandom());
		tInvernadero.setTipo_iluminacion(getNameRandom());
		return tInvernadero;
	}

	private TSistemaDeRiego getTSistemaDeRiego(int idFabricante) {
		return new TSistemaDeRiego(getNumRandom(), getNameRandom(), getNumRandom(), getNumRandom(), getNumRandom(),
				true, idFabricante);
	}

	private TTiene getTTiene(int idSistRiego, int idInvernadero) {
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
		fabricanteDAO = FactoriaIntegracion.getInstance().getFabricanteDAO();
		invernaderoDAO = FactoriaIntegracion.getInstance().getInvernaderoDAO();
		tieneDAO = FactoriaIntegracion.getInstance().getDaoTiene();
		query = FactoriaQuery.getInstance().getNewQuery("ListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero");
	}

	@Test
	public void testAltaFabricanteLocal() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			if (fabricanteDAO.altaFabricante(getTFabricanteLocal()) < 0) {
				trans.rollback();
				fail("Error: altaFabricante() debería devolver un entero positivo");
			}
			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testAltaFabricanteExtranjero() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			if (fabricanteDAO.altaFabricante(getTFabricanteExtranjero()) < 0) {
				trans.rollback();
				fail("Error: altaFabricante() debería devolver un entero positivo");
			}
			trans.commit();
		} catch (Exception e) {

			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testBajaFabricante() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			TFabricante tf = getTFabricanteExtranjero();
			if (fabricanteDAO.bajaFabricante(fabricanteDAO.bajaFabricante(tf.getId())) < 0) {
				trans.rollback();
				fail("Error: bajaFabricante() debería devolver un entero positivo");
			}
			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testMostrarFabricante() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			TFabricante tf = getTFabricanteLocal();
			tf.setId(fabricanteDAO.altaFabricante(tf));

			if (!equals(tf, fabricanteDAO.mostrarFabricantePorId(tf.getId()))) {
				trans.rollback();
				fail("Error: mostrarFabricantePorID() tf no coincide con el mostrado");
			}

			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testListarFabricantes() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();

			TFabricante tf1 = getTFabricanteLocal();
			TFabricante tf2 = getTFabricanteExtranjero();

			Integer idF1 = fabricanteDAO.altaFabricante(tf1);
			tf1.setId(idF1);
			boolean encontrado = false;
			Integer idF2 = fabricanteDAO.altaFabricante(tf2);
			tf2.setId(idF2);
			boolean encontrado2 = false;

			Set<TFabricante> fabricantes = fabricanteDAO.listarFabricantes();

			for (TFabricante fab : fabricantes) {
				if (fab.getId().equals(tf1.getId())) {
					encontrado = true;
				} else if (fab.getId().equals(tf2.getId())) {
					encontrado2 = true;
				}
			}

			if (!encontrado || !encontrado2) {
				fail("Error: La lista no muestra todos los fabricantes");
				trans.rollback();
			}

			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testListarFabricantesLocales() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();

			TFabricante tf1 = getTFabricanteLocal();
			TFabricante tf2 = getTFabricanteLocal();

			Integer idF1 = fabricanteDAO.altaFabricante(tf1);
			tf1.setId(idF1);
			boolean encontrado = false;
			Integer idF2 = fabricanteDAO.altaFabricante(tf2);
			tf2.setId(idF2);
			boolean encontrado2 = false;

			Set<TFabricante> fabricantes = fabricanteDAO.listarFabricantes();

			for (TFabricante fab : fabricantes) {
				if (fab.getId().equals(tf1.getId())) {
					encontrado = true;
				} else if (fab.getId().equals(tf2.getId())) {
					encontrado2 = true;
				}
			}

			if (!encontrado || !encontrado2) {
				fail("Error: La lista no muestra todos los fabricantes locales");
				trans.rollback();
			}

			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testListarFabricantesExtranjeros() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();

			TFabricante tf1 = getTFabricanteExtranjero();
			TFabricante tf2 = getTFabricanteExtranjero();

			Integer idF1 = fabricanteDAO.altaFabricante(tf1);
			tf1.setId(idF1);
			boolean encontrado = false;
			Integer idF2 = fabricanteDAO.altaFabricante(tf2);
			tf2.setId(idF2);
			boolean encontrado2 = false;

			Set<TFabricante> fabricantes = fabricanteDAO.listarFabricantes();

			for (TFabricante fab : fabricantes) {
				if (fab.getId().equals(tf1.getId())) {
					encontrado = true;
				} else if (fab.getId().equals(tf2.getId())) {
					encontrado2 = true;
				}
			}

			if (!encontrado || !encontrado2) {
				fail("Error: La lista no muestra todos los fabricantes extranjeros");
				trans.rollback();
			}

			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@Test
	public void testLeerPorCodigoFabricante() {
		try {
			Transaccion trans = crearTransaccion();
			trans.start();
			TFabricante tf = getTFabricanteLocal();
			tf.setId(fabricanteDAO.altaFabricante(tf));

			if (!equals(tf, fabricanteDAO.leerPorCodFabricante(tf.getCodFabricante()))) {
				trans.rollback();
				fail("Error: leerPorCodFabricante() tf no coincide con el mostrado");
			}

			trans.commit();
		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero() {
		try {
			Transaccion t = crearTransaccion();
			t.start();

			TFabricante tf1 = getTFabricanteExtranjero();
			TFabricante tf2 = getTFabricanteLocal();

			Integer idF1 = fabricanteDAO.altaFabricante(tf1);
			tf1.setId(idF1);
			Integer idF2 = fabricanteDAO.altaFabricante(tf2);
			tf2.setId(idF2);

			TInvernadero ti = getTInvernadero();
			int idInv = invernaderoDAO.altaInvernadero(ti);

			TSistemaDeRiego si1 = getTSistemaDeRiego(idF1);
			int idSist1 = sistemaRiegoDAO.altaSistemaDeRiego(si1);

			TSistemaDeRiego si2 = getTSistemaDeRiego(idF2);
			int idSist2 = sistemaRiegoDAO.altaSistemaDeRiego(si2);

			tieneDAO.altaTiene(getTTiene(idSist1, idInv));

			tieneDAO.altaTiene(getTTiene(idSist2, idInv));

			boolean encontrado = false;
			boolean encontrado2 = false;
			t.commit();

			t = crearTransaccion();
			t.start();
			Set<TFabricante> fabricantes = (Set<TFabricante>) query.execute(idInv);
			t.commit();
			
			for (TFabricante fab : fabricantes) {
				if (fab.getId().equals(tf1.getId())) {
					encontrado = true;
				} else if (fab.getId().equals(tf2.getId())) {
					encontrado2 = true;
				}
			}

			if (!encontrado || !encontrado2) {
				fail("Error: La lista no muestra todos los fabricantes extranjeros");
				t.rollback();
			}

		} catch (Exception e) {
			fail("Excepción");
			e.printStackTrace();
		}
	}
}
