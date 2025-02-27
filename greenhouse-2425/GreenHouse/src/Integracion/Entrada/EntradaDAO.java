package Integracion.Entrada;

import Negocio.Entrada.TEntrada;

import java.sql.Date;
import java.util.Set;

public interface EntradaDAO {

	public Integer altaEntrada(TEntrada entrada);

	public Integer bajaEntrada(Integer id);

	public Set<TEntrada> listarEntradas();

	public Integer modificarEntrada(TEntrada entrada);

	public TEntrada mostrarEntrada(Integer id);

	public Set<TEntrada> listarEntradasPorInvernadero(Integer idInvernadero);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param fecha
	* @param idInvernadero
	* @return
	* @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	*/
	public TEntrada leerPorFechaUnica(Date fecha, Integer idInvernadero);

	public TEntrada leerPorIDInvernaderoYFecha(Date fecha, Integer idInvernadero);
}