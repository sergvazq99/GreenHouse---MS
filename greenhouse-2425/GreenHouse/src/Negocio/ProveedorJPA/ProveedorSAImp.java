package Negocio.ProveedorJPA;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import Negocio.EMFSingleton.EMFSingleton;
import Negocio.MarcaJPA.Marca;

public class ProveedorSAImp implements ProveedorSA {

	public synchronized Integer altaProveedor(TProveedor tProv) {
		int exito = -1; // Error general
		Proveedor provExiste;
		EntityManager em = null;

		if (!this.validarCamposRellanados(tProv)) {
			exito = -2;// Campos vacios
		} else if (!this.comprobarTelefono(tProv.getTelefono())) {
			exito = -3; // Telefono mal definido
		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();
				TypedQuery<Proveedor> query = em.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findByCIF",
						Proveedor.class);
				query.setParameter("CIF", tProv.getCIF());

				try {
					provExiste = query.getSingleResult();
				} catch (NoResultException e) {
					provExiste = null;
				}

				EntityTransaction transaction = em.getTransaction();
				transaction.begin();

				if (provExiste == null) {
					Proveedor nuevoProveedor = new Proveedor();
					nuevoProveedor.transferToEntity(tProv);
					em.persist(nuevoProveedor);
					transaction.commit();
					exito = nuevoProveedor.getId();
				} else {
					if (provExiste.getActivo()) {
						exito = -4; // YA HAY UN PROVEEDOR ACTIVO CON ESE CIF
						transaction.rollback();
					} else {
						tProv.setId(provExiste.getId());
						provExiste.transferToEntity(tProv);
						transaction.commit();
						exito = provExiste.getId();
					}
				}
			} catch (Exception e) {
				exito = -1;
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}

		return exito;
	}

	public Integer bajaProveedor(Integer id) {
		int exito = -1; // Error general
		Proveedor provExiste;
		EntityManager em = null;

		if (id < 1) {
			exito = -2;// Id debe ser mayor que 0

		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();

				EntityTransaction transaction = em.getTransaction();
				transaction.begin();

				provExiste = em.find(Proveedor.class, id);
				if (provExiste != null) {
					if (provExiste.getActivo()) {
						if (provExiste.getMarca().isEmpty()) {
							provExiste.setActivo(false);
							transaction.commit();
							exito = provExiste.getId();
						} else {
							transaction.rollback();
							exito = -5; // El proveedor está vinculado a una marca
						}
					} else {
						transaction.rollback();
						exito = -3; // El proveedor ya esta dado de baja
					}
				} else {
					transaction.rollback();
					exito = -4; // El proveedor no existe
				}
			} catch (Exception e) {
				exito = -1;
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}

		return exito;
	}

	public Integer modificarProveedor(TProveedor tProv) {
		int exito = -1; // Error general
		Proveedor provExiste;
		EntityManager em = null;

		if (!this.validarCamposRellanados(tProv)) {
			exito = -2;// Campos vacios
		} else if (!this.comprobarTelefono(tProv.getTelefono())) {
			exito = -3; // Telefono mal definido
		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();

				provExiste = em.find(Proveedor.class, tProv.getId());

				EntityTransaction transaction = em.getTransaction();
				transaction.begin();

				if (provExiste == null || !provExiste.getActivo()) {
					exito = -4; // NO HAY UN PROVEEDOR ACTIVO CON ESE ID
					transaction.rollback();
				} else {
					TypedQuery<Proveedor> query = em.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findByCIF",
							Proveedor.class);
					query.setParameter("CIF", tProv.getCIF());
					Proveedor proveedorCIF;
					try {
						proveedorCIF = query.getSingleResult();
					} catch (NoResultException e) {
						proveedorCIF = null;
					}

					if (proveedorCIF == null || (proveedorCIF != null && proveedorCIF.getId() == tProv.getId())) {
						tProv.setActivo(provExiste.getActivo());
						provExiste.transferToEntity(tProv);
						transaction.commit();
						exito = provExiste.getId();
					} else {
						exito = -5; // YA HAY UN PROVEEDOR CON ESE CIF
						transaction.rollback();
					}
				}
			} catch (Exception e) {
				exito = -1;
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}

		return exito;
	}

	public Set<TProveedor> listarProveedor() {
		EntityManager em = EMFSingleton.getInstance().getEMF().createEntityManager();

		TypedQuery<Proveedor> query = em.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findAll", Proveedor.class);
		List<Proveedor> l = query.getResultList();
		Set<TProveedor> proveedores = new LinkedHashSet<TProveedor>();

		for (Proveedor p : l) {
			proveedores.add(p.entityToTransfer());
		}

		em.close();

		return proveedores;
	}

	public TProveedor mostrarProveedorPorID(Integer id) {
		TProveedor prov = new TProveedor();
		prov.setId(-1); // Error general
		EntityManager em = null;

		if (id < 1) {
			prov.setId(-2); // Id debe ser mayor que 0

		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();

				Proveedor provExiste = em.find(Proveedor.class, id);

				if (provExiste != null) {
					prov = provExiste.entityToTransfer();

				} else {
					prov.setId(-3); // El proveedor no existe
				}
			} catch (Exception e) {
				prov.setId(-1); // Error general
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}
		return prov;
	}

	public TProveedor mostrarProveedorPorCIF(String CIF) {
		TProveedor prov = new TProveedor();
		prov.setId(-1); // Error general
		EntityManager em = null;

		if (CIF == null || CIF.isEmpty()) {
			prov.setId(-2); // CIF debe estar completo

		} else {
			try {

				em = EMFSingleton.getInstance().getEMF().createEntityManager();
				TypedQuery<Proveedor> query = em.createNamedQuery("Negocio.ProveedorJPA.Proveedor.findByCIF",
						Proveedor.class);
				query.setParameter("CIF", CIF);

				Proveedor provExiste = query.getSingleResult();

				if (provExiste != null) {
					prov = provExiste.entityToTransfer();

				} else {
					prov.setId(-3); // El proveedor no existe
				}
			} catch (Exception e) {
				prov.setId(-1); // Error general
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}
		return prov;
	}

	public Integer vincularMarca(Integer idProv, Integer idMarca) {
		int exito = -1;
		EntityManager em = null;
		if (idProv < 1 || idMarca < 1) {
			exito = -2; // IDs deben ser mayores que 0
		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();
				EntityTransaction transaction = em.getTransaction();
				transaction.begin();

				Proveedor prov = em.find(Proveedor.class, idProv);
				Marca marca = em.find(Marca.class, idMarca, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (prov != null && prov.getActivo()) {
					if (marca != null && marca.getActivo()) {
						if (!prov.getMarca().contains(marca)) {
							prov.addMarca(marca);
							marca.addProveedor(prov);
							exito = 1;
							transaction.commit();
						} else {
							exito = -5;
							transaction.rollback();
						}
					} else {
						exito = -4;
						transaction.rollback();
					}
				} else {
					exito = -3;
					transaction.rollback();
				}
			} catch (Exception e) {
				exito = -1; // Error general
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}
		return exito;
	}

	public Integer desvincularMarca(Integer idProv, Integer idMarca) {
		int exito = -1;
		EntityManager em = null;
		if (idProv < 1 || idMarca < 1) {
			exito = -2; // IDs deben ser mayores que 0
		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();
				EntityTransaction transaction = em.getTransaction();
				transaction.begin();

				Proveedor prov = em.find(Proveedor.class, idProv);
				Marca marca = em.find(Marca.class, idMarca, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (prov != null && prov.getActivo()) {
					if (marca != null && marca.getActivo()) {
						if (prov.getMarca().contains(marca)) {
							prov.removeMarca(marca);
							marca.removeProveedor(prov);
							exito = 1;
							transaction.commit();
						} else {
							exito = -5;
							transaction.rollback();
						}
					} else {
						exito = -4;
						transaction.rollback();
					}
				} else {
					exito = -3;
					transaction.rollback();
				}
			} catch (Exception e) {
				exito = -1; // Error general
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}
		return exito;
	}

	public Set<TProveedor> listarProveedoresDeMarca(Integer idMarca) {
		Set<TProveedor> proveedoresDeMarca = new LinkedHashSet<>();

		EntityManager em = null;
		if (idMarca < 1) {
			TProveedor p = new TProveedor();
			p.setId(-2);
			proveedoresDeMarca.clear();
			proveedoresDeMarca.add(p);
		} else {
			try {
				em = EMFSingleton.getInstance().getEMF().createEntityManager();
				Marca marca = em.find(Marca.class, idMarca);

				if (marca == null) {
					TProveedor p = new TProveedor();
					p.setId(-3);
					proveedoresDeMarca.clear();
					proveedoresDeMarca.add(p);
				} else {

					List<Proveedor> provMarca = marca.getProveedores();

					for (Proveedor p : provMarca) {
						proveedoresDeMarca.add(new TProveedor(p));
					}
				}
			} catch (Exception e) {
				TProveedor p = new TProveedor();
				p.setId(-1);
				proveedoresDeMarca.clear();
				proveedoresDeMarca.add(p);
			} finally {
				if (em != null) {
					em.close();
				}
			}
		}

		return proveedoresDeMarca;
	}

	/* FUNCIONES DE SOPORTE */

	private boolean comprobarTelefono(String telefono) {
		return telefono.matches("\\d{9}");
	}

	private boolean validarCamposRellanados(TProveedor proveedor) {
		if (proveedor.getCIF().isEmpty() || proveedor.getNombre().isEmpty() || proveedor.getTelefono().isEmpty()) {
			return false;
		}

		return true;
	}

}