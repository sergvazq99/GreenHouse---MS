package Integracion.Transaction;

import java.util.concurrent.ConcurrentHashMap;

public class TransaccionManagerImp extends TransaccionManager {

	private ConcurrentHashMap<Thread, Transaccion> transacciones;

	public TransaccionManagerImp() {
		transacciones = new ConcurrentHashMap<Thread, Transaccion>();
	}

	@Override
	public Transaccion newTransaccion() throws Exception {
		if (transacciones.get(Thread.currentThread()) == null) {
			Transaccion t = FactoriaTransaccion.getInstance().createTransaction();
			transacciones.put(Thread.currentThread(), t);
			return t;
		}
		throw new Exception();

	}

	@Override
	public Transaccion getTransaccion() throws Exception {
		if (transacciones.get(Thread.currentThread()) != null) {
			return transacciones.get(Thread.currentThread());
		}
		throw new Exception();
	}

	@Override
	public void deleteTransaccion() throws Exception {
		if (transacciones.get(Thread.currentThread()) != null) {
			transacciones.remove(Thread.currentThread());
		} else
			throw new Exception();
	}
}