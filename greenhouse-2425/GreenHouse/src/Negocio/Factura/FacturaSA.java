package Negocio.Factura;

import java.util.Set;

public interface FacturaSA {

	public Integer cerrarFactura(TCarrito carrito);

	public TFacturaConEntradas mostrarFacturaPorID(Integer id);

	public Set<TFactura> listarFacturas();

	public Integer modificarFactura(TFactura tfactura);

	public Integer devolverFactura(TLineaFactura tlineaFactura);
}