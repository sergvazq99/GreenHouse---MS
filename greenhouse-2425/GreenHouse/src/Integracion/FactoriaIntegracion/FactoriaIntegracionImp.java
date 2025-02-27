
package Integracion.FactoriaIntegracion;

import Integracion.Entrada.EntradaDAO;
import Integracion.Entrada.EntradaDAOImp;
import Integracion.Fabricante.FabricanteDAO;
import Integracion.Fabricante.FabricanteDAOImp;
import Integracion.Factura.FacturaDAO;
import Integracion.Factura.FacturaDAOImp;
import Integracion.Factura.LineaFacturaDAO;
import Integracion.Factura.LineaFacturaDAOImp;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Invernadero.InvernaderoDAOImp;
import Integracion.Invernadero.TieneDAO;
import Integracion.Invernadero.TieneDAOImp;
import Integracion.Planta.PlantaDAO;
import Integracion.Planta.PlantaDAOImp;
import Integracion.SistemaDeRiego.SistemaDeRiegoDAO;
import Integracion.SistemaDeRiego.SistemaDeRiegoDAOImp;

public class FactoriaIntegracionImp extends FactoriaIntegracion {

	public FacturaDAO getFacturaDAO() {
		return new FacturaDAOImp();
	}

	public EntradaDAO getEntradaDAO() {
		return new EntradaDAOImp();
	}

	public InvernaderoDAO getInvernaderoDAO() {
		return new InvernaderoDAOImp();
	}

	public SistemaDeRiegoDAO getSistemaDeRiegoDAO() {
		return new SistemaDeRiegoDAOImp();
	}

	public FabricanteDAO getFabricanteDAO() {
		return new FabricanteDAOImp();
	}

	public PlantaDAO getPlantaDAO() {
		return new PlantaDAOImp();
	}

	public LineaFacturaDAO getDAOLineaFactura() {
		return new LineaFacturaDAOImp();
	}

	public TieneDAO getDaoTiene() {
		return new TieneDAOImp();
	}
}