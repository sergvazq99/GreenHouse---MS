package Negocio.Factura;

import java.util.HashSet;
import java.util.Set;

import Integracion.Entrada.EntradaDAO;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Factura.FacturaDAO;
import Integracion.Factura.LineaFacturaDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Entrada.TEntrada;

public class FacturaSAImp implements FacturaSA {

	public Integer cerrarFactura(TCarrito carrito) {
		TransaccionManager tm = TransaccionManager.getInstance();
		try {
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion fDAO = FactoriaIntegracion.getInstance();
			if (!carrito.getLineasFactura().isEmpty()) {
				EntradaDAO daoEntrada = fDAO.getEntradaDAO();
				float precio_total = 0;
				for (TLineaFactura lineaFact : carrito.getLineasFactura()) {
					TEntrada entrada = daoEntrada.mostrarEntrada(lineaFact.getidEntrada());
					if (entrada != null) {
						if (entrada.getActivo()) {
							if (lineaFact.getCantidad() <= entrada.getStock()) {
								//Calculamos el stock y el precio total
								entrada.setStock(entrada.getStock() - lineaFact.getCantidad());
								daoEntrada.modificarEntrada(entrada);
								float precio_lineaF = lineaFact.getCantidad() * entrada.getPrecio();
								lineaFact.setPrecio(precio_lineaF);
								precio_total = precio_total + precio_lineaF;
							} else {
								//No hay Stock suficiente
								t.rollback();
								return -2;
							}
						} else {
							//La entrada está dada de baja
							t.rollback();
							return -2;
						}
					} else {
						//La entrada no existe
						t.rollback();
						return -2;
					}
				}
				FacturaDAO daoFactura = fDAO.getFacturaDAO();
				TFactura factura = carrito.getFactura();
				factura.setPrecioTotal(precio_total);
				int id = daoFactura.cerrarFactura(factura);
				if (id > 0) {
					LineaFacturaDAO daoLF = fDAO.getDAOLineaFactura();
					for (TLineaFactura lf : carrito.getLineasFactura()) {
						lf.setidFactura(id);
						int r = daoLF.crearLineaFactura(lf);
						if (r < 0) {
							//Fallo al crear la linea de facturación
							t.rollback();
							return -3;
						}
					}
				} else {
					//Error al cerrar factura
					t.rollback();
					return -1;
				}
				t.commit();
				return id;
			} else {
				//Carrito vacio
				t.rollback();
				return -2;
			}
		} catch (Exception e) {
			//Error desconocido
			e.printStackTrace();
			return -3;
		}
	}

	public TFacturaConEntradas mostrarFacturaPorID(Integer id) {
		TFacturaConEntradas facturaEntradas = new TFacturaConEntradas();
		TFactura factura = new TFactura();
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			Transaccion t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion fDAO = FactoriaIntegracion.getInstance();
			FacturaDAO daoFactura = fDAO.getFacturaDAO();
			TFactura facturaBD = daoFactura.mostrarFactura(id);
			if (facturaBD != null) {
				facturaEntradas.settFactura(facturaBD);

				Set<TLineaFactura> lineasfacturaBD = fDAO.getDAOLineaFactura().mostrarLineaFacturaPorFactura(id);

				for (TLineaFactura tLineaFactura : lineasfacturaBD) {
					facturaEntradas.incluirLineaEntrada(tLineaFactura);

				}
				t.commit();
			} else {
				factura.setid(-2);
				facturaEntradas.settFactura(factura);
				t.rollback();
			}

		} catch (Exception e) {
			//Error desconocido
			e.printStackTrace();
			factura.setid(-2);
			facturaEntradas.settFactura(factura);
		}
		return facturaEntradas;
	}

	public Set<TFactura> listarFacturas() {
		Set<TFactura> facturas = new HashSet<>();
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			Transaccion t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			FacturaDAO daoFactura = f.getFacturaDAO();
			Set<TFactura> facturasBuscar = daoFactura.listarFactura();
			for (TFactura factura : facturasBuscar) {
				facturas.add(factura);
			}
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facturas;
	}

	public Integer modificarFactura(TFactura tfactura) {
		int r = -1;
		try {
			FactoriaIntegracion fIntegracion = FactoriaIntegracion.getInstance();
			FacturaDAO daoFactura = fIntegracion.getFacturaDAO();
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			TFactura facturaBD = daoFactura.mostrarFactura(tfactura.getid());
			if (facturaBD != null) {
				if (facturaBD.getActivo()) {
					tfactura.setPrecioTotal(facturaBD.getPrecioTotal());
					tfactura.setActivo(facturaBD.getActivo());
					r = daoFactura.modificarFactura(tfactura);
					if (r < 0) {
						t.rollback();
						return -1;
					}
					t.commit();
				} else {
					t.rollback();
					return -1;
				}
			} else {
				t.rollback();
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return r;
		}
		return r;
	}

	public Integer devolverFactura(TLineaFactura tlineaFactura) {
		TransaccionManager tm = TransaccionManager.getInstance();
		FactoriaIntegracion fDAO = FactoriaIntegracion.getInstance();
		int r = -1;
		try {
			Transaccion t = tm.newTransaccion();
			t.start();
			FacturaDAO daoFactura = fDAO.getFacturaDAO();
			TFactura factura = daoFactura.mostrarFactura(tlineaFactura.getidFactura());
			if (factura != null && factura.getActivo()) {

				Set<TLineaFactura> lineasfacturaBD = fDAO.getDAOLineaFactura()
						.mostrarLineaFacturaPorFactura(factura.getid());

				for (TLineaFactura tLineaFacturaABorrar : lineasfacturaBD) {

					EntradaDAO daoEntrada = fDAO.getEntradaDAO();
					TEntrada entrada = daoEntrada.mostrarEntrada(tLineaFacturaABorrar.getidEntrada());
					if (entrada != null) {
						LineaFacturaDAO daoLF = fDAO.getDAOLineaFactura();
						TLineaFactura lf = daoLF.mostrarLineaFactura(tlineaFactura.getidFactura(), entrada.getId());
						if (lf != null) {
							if (!entrada.getActivo()) {
								t.rollback();
								return -1;
							}
							entrada.setStock(entrada.getStock() + lf.getCantidad());
							r = daoEntrada.modificarEntrada(entrada);
							if (r < 0) {
								t.rollback();
								return -1;
							}
							TLineaFactura baja = daoLF.bajaLineaFactura(lf.getidFactura(), lf.getidEntrada());
							r = baja == null ? 1 : -1;
							if (r < 0) {
								t.rollback();
								return -1;
							}
						}

					}
				}

				r = daoFactura.devolverFactura(factura.getid());
				if (r < 0) {
					t.rollback();
					return -1;
				} else {
					t.commit();
					return 1;
				}
			} else {
				//Factura no existe o ya está dada de baja
				t.rollback();
				return -2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return r;
		}
	}
}