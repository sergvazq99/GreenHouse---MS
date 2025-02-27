package Integracion.Fabricante;

import Negocio.Fabricante.TFabricante;
import java.util.Set;

public interface FabricanteDAO {

	public Integer altaFabricante(TFabricante fabricante);

	public Integer bajaFabricante(Integer idFabricante);

	public Integer modificarFabricante(TFabricante fabricante);

	public TFabricante mostrarFabricantePorId(Integer idFabricante);

	public Set<TFabricante> listarFabricantes();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	*/
	public Set<TFabricante> listarFabricantesExtrangeros();

	public Set<TFabricante> listarFabricantesExtranjeros();

	public Set<TFabricante> listarFabricantesLocales();

	public TFabricante leerPorCodFabricante(String codFabricante);

}