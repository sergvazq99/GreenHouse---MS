package Integracion.Factura;

import Negocio.Factura.TFactura;
import java.util.Set;

public interface FacturaDAO {

	public Integer cerrarFactura(TFactura tfactura);

	public TFactura mostrarFactura(Integer id);

	public Set<TFactura> listarFactura();

	public Integer modificarFactura(TFactura tfactura);

	public Integer devolverFactura(Integer id);
}