package Negocio.Planta;

import java.util.HashSet;

import java.util.Set;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Invernadero.InvernaderoDAO;
import Integracion.Planta.PlantaDAO;
import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;
import Negocio.Invernadero.TInvernadero;

public class PlantaSAImp implements PlantaSA {

	public Integer altaPlanta(TPlanta planta) {
		int exito = -1;
		Transaccion t = null;
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			InvernaderoDAO daoinv = f.getInvernaderoDAO();
			PlantaDAO dao = f.getPlantaDAO();

			TInvernadero inv = daoinv.mostrarInvernaderoPorID(planta.get_id_invernadero());

			if (inv == null) {

				exito = -1;
				t.rollback();
			} else {

				if (!inv.isActivo()) {
					exito = -3;
				} else {
					exito = dao.altaPlanta(planta);
				}

				if (exito > -1) {
					t.commit();
				} else {
					t.rollback();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return exito;
	}

	public Integer bajaPlanta(Integer id) {
		int exito = -1;
		Transaccion t = null;
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			PlantaDAO dao = f.getPlantaDAO();

			TPlanta p = dao.mostrarPorId(id);

			if (p == null) {
				t.rollback();
				exito = -2;

			} else {

				if (!p.getActivo()) {
					exito = -3;
				} else {
					exito = dao.bajaPlanta(id);
				}

				if (exito > -1) {
					t.commit();
				} else {
					t.rollback();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	public Integer modificarPlanta(TPlanta planta) {
		int exito = -1;
		Transaccion t = null;
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();
			InvernaderoDAO daoinv = f.getInvernaderoDAO();

			TInvernadero inv = daoinv.mostrarInvernaderoPorID(planta.get_id_invernadero());

			if (inv == null || !inv.isActivo()) {
				exito = -2;

			} else {

				PlantaDAO dao = f.getPlantaDAO();

				exito = dao.modificarPlanta(planta);

			}

			if (exito > -1) {
				t.commit();
			} else {
				t.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	public Set<TPlanta> listarPlanta() {
		Transaccion t = null;
		Set<TPlanta> p = new HashSet<>();
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			PlantaDAO dao = f.getPlantaDAO();

			p = dao.listarPlantas();

			if (p == null) {
				t.rollback();

			} else {

				t.commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;

	}

	public TPlanta mostrarPlantaPorId(Integer id) {
		Transaccion t = null;
		TPlanta p = null;
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			PlantaDAO dao = f.getPlantaDAO();

			p = dao.mostrarPorId(id);

			if (p == null) {
				t.rollback();

			} else {

				t.commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public Set<TPlanta> listarPlantasPorTipo(Integer tipo) {
		return null;
	}

	public Set<TPlanta> listarPlantasPorTipo(String tipo) {
		Transaccion t = null;
		Set<TPlanta> p = new HashSet<>();
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			PlantaDAO dao = f.getPlantaDAO();
			int tip = 1;
			if (tipo == "Frutal") {
				tip = 0;
			}

			p = dao.mostrarPorTipo(tip);

			if (p == null) {
				t.rollback();

			} else {

				t.commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public Set<TPlanta> listarPlantasPorInvernadero(Integer id_invernadero) {
		Transaccion t = null;
		Set<TPlanta> p = null;
		try {
			TransaccionManager transaction = TransaccionManager.getInstance();
			t = transaction.newTransaccion();
			t.start();
			FactoriaIntegracion f = FactoriaIntegracion.getInstance();

			PlantaDAO dao = f.getPlantaDAO();
			InvernaderoDAO daoinv = f.getInvernaderoDAO();
			TInvernadero inv = daoinv.mostrarInvernaderoPorID(id_invernadero);

			if (inv == null) {
				t.rollback();
				return null;
			}

			p = dao.MostrarPorInvernadero(id_invernadero);

			if (p == null) {
				t.rollback();

			} else {
				t.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}
}