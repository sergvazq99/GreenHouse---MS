package Negocio.EMFSingleton;

import javax.persistence.EntityManagerFactory;

public class EMFSingletonImp extends EMFSingleton {

	private EntityManagerFactory entityManagerFactory;

	public EMFSingletonImp(EntityManagerFactory EMF) {
		entityManagerFactory = EMF;
	}

	@Override
	public EntityManagerFactory getEMF() {
		return entityManagerFactory;
	}

}