package Negocio.MarcaJPA;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.ProductoJPA.Producto;
import Negocio.ProveedorJPA.Proveedor;

public class MarcaSAImp implements MarcaSA {

	public synchronized Integer altaMarca(TMarca marca) {
		Integer id = -1;
		boolean exito = false;
		Marca marcaExistente = null;
		Marca nuevaMarca = null;

		if (!validarNombre(marca.getNombre())) {
			return -4; // datos incorrectos
		}

		// Empieza una transacción
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();

		// Obtenemos la Marca por nombre
		TypedQuery<Marca> query = em.createNamedQuery("Negocio.MarcaJPA.Marca.findBynombre", Marca.class);
		query.setParameter("nombre", marca.getNombre());

		try {
			marcaExistente = query.getSingleResult();
		} catch (Exception e) {
			// System.out.println("No existe marca con el mismo nombre");
		}

		if (marcaExistente != null) {
			if (!marcaExistente.getActivo()) {
//				if(marcaExistente.getPaisOrigen().equals(marca.getPais())) {
//					
//				} else {
//					t.rollback();
//					em.close();
//					return -24;
//				}
				// Reactivamos
				marca.setId(marcaExistente.getId());
				marcaExistente.transferToEntity(marca);
				id = marcaExistente.getId();
				try {
					t.commit();
					em.close();
					return id;
				} catch (Exception e) {
					t.rollback();
					em.close();
					return id;
				}

			} else {
				// si la marca ya existe y está activa, error: ya existe
				t.rollback();
				em.close();
				return -2;
			}

		} else {
			nuevaMarca = new Marca(marca);
			em.persist(nuevaMarca);
			exito = true;
		}

		try {
			t.commit();
			if (exito)
				id = nuevaMarca.getId();
		} catch (Exception e) {
			t.rollback();
			return -3; // error en la transacción
		}

		em.close();

		return id;
	}

	public Integer bajaMarca(Integer id) {

		int res = -1;

		if (!validarId(id)) {
			return -4;
		}

		try {
			EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
			EntityTransaction t = em.getTransaction();
			t.begin();

			Marca marcaExiste = em.find(Marca.class, id);

			if (marcaExiste != null) {
				if (marcaExiste.getActivo()) {
					if (marcaExiste.getProveedores().isEmpty()) {
						List<Producto> productos = marcaExiste.getProductos();
						if(productos.isEmpty()) {
							marcaExiste.setActivo(false);
							t.commit();
							res = marcaExiste.getId();
						} else {
							boolean algunProducto = false;
							for(Producto p: productos) {
								if (p.getActivo())
									algunProducto = true;
							}
							if(!algunProducto) {
								marcaExiste.setActivo(false);
								t.commit();
								res = marcaExiste.getId();
							} else {
								t.rollback();
								em.close();
								return -13; // La marca tiene productos
							}
							
						}
						

					} else {
						t.rollback();
						em.close();
						return -12; // La marca está vinculada a un proveedor
					}

				} else {
					t.rollback();
					em.close();
					return -2; // marca ya inactiva
				}

			} else {
				t.rollback();
				em.close();
				return -3; // la marca no existe
			}
		} catch (Exception e) {
			res = -1;
		}
		return res;
	}

//	public Integer bajaMarca(Integer id) {
//
//		int res = -1;
//
//		if (!validarId(id)) {
//			System.out.println("Formato incorrecto para el ID de marca");
//			return -4;
//		}
//
//		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();
//		EntityTransaction t = em.getTransaction();
//		t.begin();
//
//		Marca marca = em.find(Marca.class, id);
//
//		if (marca == null) {
//			t.rollback();
//			em.close();
//			return -3;
//		}
//
//		if (marca != null && marca.getActivo()) {
//
//			List<Proveedor> listaProveedores = marca.getProveedores();
//
//			// Desvincular de la marca sus proveedores
//			for (Proveedor p : listaProveedores) {
//				p.getMarca().remove(marca);
//			}
//
//			listaProveedores.clear();
//			marca.setActivo(false);
//
//			try {
//				t.commit();
//				res = marca.getId();
//
//			} catch (Exception e) {
//				t.rollback();
//				em.close();
//				return res;
//			}
//
//		} else {
//			if (!marca.getActivo()) {
//				t.rollback();
//				em.close();
//				return -2; // marca ya inactiva
//			}
//		}
//
//		em.close();
//		return res;
//	}

	public Integer modificarMarca(TMarca marca) {

		int res = -1;
		EntityManager em = null;

		if (!validarNombre(marca.getNombre())) {
			return -4;
		}
		try {
			em = EMFSingleton.getInstance().getEMF().createEntityManager();
			EntityTransaction t = em.getTransaction();
			t.begin();

			Marca marcaExiste = em.find(Marca.class, marca.getId());
			if (marcaExiste == null) {
				t.rollback();
				em.close();
				return -7; // marca no existe con ese id, o no está activa con ese id
			} else {
				TypedQuery<Marca> query = em.createNamedQuery("Negocio.MarcaJPA.Marca.findBynombre", Marca.class);
				query.setParameter("nombre", marca.getNombre());
				Marca marcaNombre = null;
				try {
					marcaNombre = query.getSingleResult();
				} catch (NoResultException e) {
					marcaNombre = null;
				}

				if (marcaNombre == null || (marcaNombre != null && marcaNombre.getId() == marca.getId())) {
					if (marcaExiste.getActivo()) {
						marca.setActivo(marcaExiste.getActivo());
						marcaExiste.transferToEntity(marca);
						t.commit();
						res = marcaExiste.getId();
					} else {
						t.rollback();
						em.close();
						return -8; // la marca que quiero modificar está inactiva
					}
				} else {
					t.rollback();
					em.close();
					return -5; // ya existe una marca con ese nombre
				}
			}

		} catch (Exception e) {
			em.close();
			res = -1;
		}

		return res;
	}

	public TMarca mostrarMarcaPorId(Integer id) {

		TMarca marca = new TMarca();
		
		if (!validarId(id)) {
			marca.setId(-2); // id debe ser número positivo >1
			return marca;
		}

		EMFSingleton entityManagerFactory = EMFSingleton.getInstance();
		EntityManager entityManager = entityManagerFactory.getEMF().createEntityManager();

		Marca marcaExiste = entityManager.find(Marca.class, id);

//		if(marcaExiste != null) {
//			marcaExiste
//		}
		
		if (marcaExiste == null) {
			marca.setId(-3);
			return marca;
		}

		TMarca tMarca = new TMarca(id, marcaExiste.getNombre(), marcaExiste.getPaisOrigen(), marcaExiste.getActivo());

		entityManager.close();
		return tMarca;
	}

	public Set<TMarca> listarMarcas() {
		// no hay transacción porque hay un listar
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();

		TypedQuery<Marca> query = em.createNamedQuery("Negocio.MarcaJPA.Marca.findAll", Marca.class);

		List<Marca> l = query.getResultList(); // obtenemos una lista de marcas
		Set<TMarca> lista = new LinkedHashSet<TMarca>();

		for (Marca m : l) {
			TMarca t = m.entityToTransfer();
			lista.add(t);
		}

		em.close();
		return lista;

	}

	public Set<TMarca> listarMarcasPorProveedor(Integer idProv) {
		Set<TMarca> marcasDeProveedor = new LinkedHashSet<TMarca>();

		EntityManager em = null;

		if (idProv < 1) {
			TMarca m = new TMarca();
			m.setId(-2);
			marcasDeProveedor.clear();
			marcasDeProveedor.add(m);
		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();
				Proveedor proveedor = em.find(Proveedor.class, idProv);

				if (proveedor == null) {
					TMarca m = new TMarca();
					m.setId(-3);
					marcasDeProveedor.clear();
					marcasDeProveedor.add(m);
				} else {
					Set<Marca> marcaProv = proveedor.getMarca();

					for (Marca m : marcaProv) {
						marcasDeProveedor.add(new TMarca(m));
					}

				}

			} catch (Exception e) {
				TMarca m = new TMarca();
				m.setId(-4);
				marcasDeProveedor.clear();
				marcasDeProveedor.add(m);

			} finally {
				if (em != null) {
					em.close();
				}
			}
		}
		return marcasDeProveedor;
	}

	// Métodos auxiliares
	private Boolean validarNombre(String nombre) {
		return nombre != null && !nombre.isEmpty();
	}

	private boolean validarId(Integer id) {
		return id != null && id > 0;
	}
}