package Integracion.Transaction;

public abstract class FactoriaTransaccion {

	private static FactoriaTransaccion instance;


	public static void getInstance(FactoriaTransaccion instance) {

	}

	public static synchronized FactoriaTransaccion getInstance() {
		if (instance == null) {
			instance = new FactoriaTransaccionImp();
		}
		return instance;
	}

	public abstract Transaccion createTransaction() throws Exception;
}