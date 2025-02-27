package Negocio.Entrada;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Entrada.EntradaDAO;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Invernadero.TInvernadero;

public class EntradaSAImp implements EntradaSA {

	@Override
	public Integer altaEntrada(TEntrada entrada) {
		// comprobaciones del formato de los datos

		// comprobamos si en el alta hay campos vacíos
		if (entrada == null || entrada.getIdInvernadero() == 0 || entrada.getFecha().equals(null)
				|| entrada.getPrecio() == 0 || entrada.getStock() == 0) {
			return -3; // enviamos error de que no se pueden dejar campos vacíos en el alta
		}

		int exito = -1;

		try {

			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			InvernaderoDAO invernaderoDAO = f.getInvernaderoDAO();
			TInvernadero invernadero = invernaderoDAO.mostrarInvernaderoPorID(entrada.getIdInvernadero());

			if (invernadero != null) {

				if (invernadero.isActivo()) {
					EntradaDAO entradaDao = f.getEntradaDAO();
					TEntrada entradaUnica = entradaDao.leerPorIDInvernaderoYFecha((Date) entrada.getFecha(),
							entrada.getIdInvernadero());

					if (entradaUnica == null) {
						exito = entradaDao.altaEntrada(entrada);
						t.commit();

					} else if (entradaUnica.getActivo()) { // si la entrada unica encontada está activa no puede activarse
						exito = -48;
						t.rollback();

					} else if (!entradaUnica.getActivo()) { // si es falso, se reactiva
						entrada.setId(entradaUnica.getId());
						exito = entradaDao.modificarEntrada(entrada);
						t.commit();

					} else {
						exito = -50; // Error: ya existe la entrada
						t.rollback();
					}

				} else {
					exito = -21; // Error: el id de invernadero no está activo
					t.rollback();
				}

			} else {
				exito = -20; // Error: el id de invernadero no existe
				t.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;

	}

	@Override
	public Integer bajaEntrada(Integer id) {
		int exito = -1;
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			EntradaDAO entradaDao = f.getEntradaDAO();
			TEntrada entrada = entradaDao.mostrarEntrada(id); // para comprobar que existe una entrada con ese id

			if (entrada != null) {

				if (entrada.getActivo()) {
					exito = entradaDao.bajaEntrada(id);
					t.commit();

				} else {
					exito = -52; // Error: el id es de una entrada ya inactivo
					t.rollback();
				}

			} else {
				exito = -51; // Error: id de una entrada que no existe
				t.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public Integer modificarEntrada(TEntrada entrada) {
		if (entrada.getId() == 0 || entrada.getFecha() == null || entrada.getPrecio() == 0 || entrada.getStock() == 0
				|| entrada.getIdInvernadero() == 0) {
			return -3;
		}

		int exito = -1;

		try {

			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			EntradaDAO entradaDao = f.getEntradaDAO();
			TEntrada entradaBuscar = entradaDao.mostrarEntrada(entrada.getId()); // para comprobar que existe una
																					// entrada
																					// con ese id
			if (entradaBuscar != null) {

				if (entradaBuscar.getActivo()) {

					// Comprobamos que el id de invernadero existe y está activo
					InvernaderoDAO invernaderoDAO = f.getInvernaderoDAO();
					TInvernadero invernadero = invernaderoDAO.mostrarInvernaderoPorID(entrada.getIdInvernadero());

					if (invernadero != null) {

						if (invernadero.isActivo()) {
							TEntrada entradaUnica = entradaDao.leerPorIDInvernaderoYFecha((Date) entrada.getFecha(),
									entrada.getIdInvernadero());

							if (entradaUnica == null) {
								exito = entradaDao.modificarEntrada(entrada);
								t.commit();

							} else if (!entradaUnica.getActivo()) { // Comprobamos si esa entrada que ya tiene los
																		// mismos datos esta dada de baja
								exito = -53; // la entrada ya existe con la misma fecha y está inactiva
								t.rollback();

							} else if (entradaUnica.getId() == entrada.getId()) {
								exito = entradaDao.modificarEntrada(entrada);
								t.commit();

							} else {
								//								exito = entradaDao.modificarEntrada(entrada);
								//								t.commit();
								exito = -50; // Error: ya existe la entrada con los mismos datos y está activa
								t.rollback();
							}

						} else {
							exito = -21; // Error: el invernadero asociado no está activo
							t.rollback();
						}

					} else {
						exito = -20; // Error: el invernadero asociado no existe
						t.rollback();
					}

				} else {
					exito = -52; // Error: id de una entrada inactiva
					t.rollback();
				}

			} else {
				exito = -51; // Error: el id es de una entrada que no existe
				t.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return exito;
	}

	@Override
	public TEntrada mostrarEntrada(Integer id) {
		TEntrada entradaMostrar = new TEntrada();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			EntradaDAO entradaDao = f.getEntradaDAO();
			TEntrada entradaBuscar = entradaDao.mostrarEntrada(id);

			if (entradaBuscar != null) {
				entradaMostrar.setId(entradaBuscar.getId());
				entradaMostrar.setIdInvernadero(entradaBuscar.getIdInvernadero());
				entradaMostrar.setFecha(entradaBuscar.getFecha());
				entradaMostrar.setPrecio(entradaBuscar.getPrecio());
				entradaMostrar.setStock(entradaBuscar.getStock());
				entradaMostrar.setActivo(entradaBuscar.getActivo());

				t.commit();

			} else {
				entradaMostrar = new TEntrada();
				entradaMostrar.setId(id); // Error: id de una entrada que no existe
				t.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return entradaMostrar;
	}

	@Override
	public Set<TEntrada> listarEntrada() {

		Set<TEntrada> entradas = new LinkedHashSet<>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			EntradaDAO entradaDao = f.getEntradaDAO();
			Set<TEntrada> entradasBuscar = entradaDao.listarEntradas();

			for (TEntrada entrada : entradasBuscar) {
				entradas.add(entrada);
			}

			entradasBuscar = null; // liberamos memoria
			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return entradas;
	}

	@Override
	public Set<TEntrada> listarEntradasPorInvernadero(Integer idInvernadero) {
		Set<TEntrada> entradas = new LinkedHashSet<>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			EntradaDAO entradaDAO = f.getEntradaDAO();
			TInvernadero invernadero = f.getInvernaderoDAO().mostrarInvernaderoPorID(idInvernadero);

			if (invernadero != null && invernadero.isActivo()) {

				entradas = entradaDAO.listarEntradasPorInvernadero(idInvernadero);
				t.commit();

			} else {
				TEntrada entrada = new TEntrada();
				entrada.setId(-1); // Error: id de invernadero no existe
				entradas.add(entrada);
				t.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return entradas;
	}

}