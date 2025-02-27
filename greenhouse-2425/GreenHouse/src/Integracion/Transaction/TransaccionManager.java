package Integracion.Transaction;

public abstract class TransaccionManager {

	private static TransaccionManager instance;

	public static synchronized TransaccionManager getInstance() {
		if (instance == null) {
			instance = new TransaccionManagerImp();
		}
		return instance;
	}

	public abstract Transaccion newTransaccion() throws Exception;

	public abstract Transaccion getTransaccion() throws Exception;

	public abstract void deleteTransaccion() throws Exception;
}