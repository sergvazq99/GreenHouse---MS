package Integracion.Factura;

import Negocio.Factura.TLineaFactura;
import java.util.Set;

public interface LineaFacturaDAO {

	public Integer crearLineaFactura(TLineaFactura lineaFactura);

	public TLineaFactura bajaLineaFactura(Integer idFactura, Integer idEntrada);

	public Integer modificarLineaFactura(TLineaFactura tlineaFactura);

	public TLineaFactura mostrarLineaFactura(Integer idFactura, Integer idEntrada);

	public Set<TLineaFactura> listarLineasDeFacturas();

	public Set<TLineaFactura> mostrarLineaFacturaPorFactura(Integer idFactura);
}