package Negocio.FactoriaNegocio;

import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCajaSA;
import Negocio.EmpleadoDeCajaJPA.EmpleadoDeCajaSAImp;
import Negocio.Entrada.EntradaSA;
import Negocio.Entrada.EntradaSAImp;
import Negocio.Fabricante.FabricanteSA;
import Negocio.Fabricante.FabricanteSAImp;
import Negocio.Factura.FacturaSA;
import Negocio.Factura.FacturaSAImp;
import Negocio.Invernadero.InvernaderoSA;
import Negocio.Invernadero.InvernaderoSAImp;
import Negocio.MarcaJPA.MarcaSA;
import Negocio.MarcaJPA.MarcaSAImp;
import Negocio.Planta.PlantaSA;
import Negocio.Planta.PlantaSAImp;
import Negocio.ProductoJPA.ProductoSA;
import Negocio.ProductoJPA.ProductoSAImp;
import Negocio.ProveedorJPA.ProveedorSA;
import Negocio.ProveedorJPA.ProveedorSAImp;
import Negocio.SistemaDeRiego.SistemaDeRiegoSA;
import Negocio.SistemaDeRiego.SistemaDeRiegoSAImp;
import Negocio.TurnoJPA.TurnoSA;
import Negocio.TurnoJPA.TurnoSAImp;
import Negocio.VentaJPA.VentaSA;
import Negocio.VentaJPA.VentaSAImp;

public class FactoriaSAImp extends FactoriaNegocio {

	public EntradaSA getEntradaSA() {
		return new EntradaSAImp();
	}

	public FabricanteSA getFabricanteSA() {
		return new FabricanteSAImp();
	}

	public FacturaSA getFacturaSA() {
		return new FacturaSAImp();
	}

	public InvernaderoSA getInvernaderoSA() {
		return new InvernaderoSAImp();
	}

	public PlantaSA getPlantaSA() {
		return new PlantaSAImp();
	}

	public SistemaDeRiegoSA getSistemaDeRiegoSA() {
		return new SistemaDeRiegoSAImp();
	}

	public EmpleadoDeCajaSA getEmpleadoDeCajaJPA() {
		return new EmpleadoDeCajaSAImp();
	}

	public MarcaSA getMarcaJPA() {
		return new MarcaSAImp();
	}

	public ProveedorSA getProveedorJPA() {
		return new ProveedorSAImp();
	}

	public TurnoSA getTurnoJPA() {
		return new TurnoSAImp();
	}

	public VentaSA getVentaSA() {
		return new VentaSAImp();
	}
	
	public ProductoSA getProductoJPA(){
		return new ProductoSAImp();
}
	
}