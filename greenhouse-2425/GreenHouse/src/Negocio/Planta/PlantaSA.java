package Negocio.Planta;

import java.util.Set;

public interface PlantaSA {

	public Integer altaPlanta(TPlanta planta);

	public Integer bajaPlanta(Integer id);

	public Integer modificarPlanta(TPlanta planta);

	public Set<TPlanta> listarPlanta();

	public TPlanta mostrarPlantaPorId(Integer id);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param tipo
	* @return
	* @generated "UML a JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	*/
	public Set<TPlanta> listarPlantasPorTipo(Integer tipo);

	public Set<TPlanta> listarPlantasPorTipo(String tipo);

	public Set<TPlanta> listarPlantasPorInvernadero(Integer id_invernadero);
}