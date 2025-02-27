package Negocio.Fabricante;

import java.util.Set;

public interface FabricanteSA {
	public Integer altaFabricante(TFabricante fabricante);

	public Integer bajaFabricante(Integer idFabricante);

	public Integer modificarFabricante(TFabricante fabricante);

	public TFabricante mostrarFabricantePorId(Integer id);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idInvernadero
	* @return
	* @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	*/
	public String ListarInformaciondeFabricantesdeSistemasdeRiegodeUnInvernadero(Integer idInvernadero);

	public Set<TFabricante> listarFabricantes();

	public Set<TFabricante> listarFabricantesLocales();

	public Set<TFabricante> listarFabricantesExtranjeros();

	public Set<TFabricante> listarFabricantesPorInvernadero(Integer id);
}