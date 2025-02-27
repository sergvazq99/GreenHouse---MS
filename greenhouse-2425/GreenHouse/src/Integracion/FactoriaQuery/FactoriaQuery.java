package Integracion.FactoriaQuery;

public abstract class FactoriaQuery {

	private static FactoriaQuery instance;

	public static FactoriaQuery getInstance() {

		if (instance == null)
			instance = new FactoriaQueryImp();

		return instance;
	}

	public Query getNewQuery(Integer id) {

		return null;
	}

	public abstract Query getNewQuery(String nombre);

}