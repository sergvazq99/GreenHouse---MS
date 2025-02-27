
package Integracion.Transaction;

public abstract class TransactionManager {

	private static TransactionManager instance;

	public static TransactionManager getInstance() {

		return null;

	}

	public abstract Transaccion newTransaccion();

	public abstract Transaccion getTransaccion();

	public abstract Void deleteTransaccion();
}