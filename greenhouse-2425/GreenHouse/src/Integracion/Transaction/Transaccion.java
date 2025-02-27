package Integracion.Transaction;

public interface Transaccion {

	public void start() throws Exception;

	public void commit() throws Exception;

	public void rollback() throws Exception;

	public Object getResource();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	*/
	public Void cerrarConnection();

}