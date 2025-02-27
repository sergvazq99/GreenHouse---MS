
package Integracion.FactoriaIntegracion;

import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Invernadero.TieneDAO;
import Integracion.Entrada.EntradaDAO;
import Integracion.Fabricante.FabricanteDAO;
import Integracion.Factura.FacturaDAO;
import Integracion.Factura.LineaFacturaDAO;
import Integracion.Planta.PlantaDAO;
import Integracion.SistemaDeRiego.SistemaDeRiegoDAO;

public abstract class FactoriaIntegracion {

	private static FactoriaIntegracion instance;

	public static FactoriaIntegracion getInstance() {
		if (instance == null)
			instance = new FactoriaIntegracionImp();
		return instance;
	}

	public abstract FacturaDAO getFacturaDAO();

	public abstract EntradaDAO getEntradaDAO();

	public abstract InvernaderoDAO getInvernaderoDAO();

	public abstract SistemaDeRiegoDAO getSistemaDeRiegoDAO();

	public abstract FabricanteDAO getFabricanteDAO();

	public abstract PlantaDAO getPlantaDAO();

	public SistemaDeRiegoDAO getSistemaDeRiesoDAO() {
		return null;
	}


	public TieneDAO getTieneDAO() {
		return null;

	}

	public LineaFacturaDAO getLineaFacturaDAO() {
		return null;
	}

	public abstract LineaFacturaDAO getDAOLineaFactura();

	public abstract TieneDAO getDaoTiene();

}