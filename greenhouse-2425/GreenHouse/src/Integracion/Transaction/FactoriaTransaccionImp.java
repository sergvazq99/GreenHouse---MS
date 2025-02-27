package Integracion.Transaction;

public class FactoriaTransaccionImp extends FactoriaTransaccion {

	public Transaccion createTransaction() throws Exception {
		return new TransaccionMySQL();

	}
}