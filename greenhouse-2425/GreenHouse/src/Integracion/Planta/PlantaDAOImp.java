package Integracion.Planta;

import Negocio.Planta.TPlanta;
import Negocio.Planta.TPlantaFrutal;
import Negocio.Planta.TPlantaNoFrutal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;
import java.util.HashSet;

import Integracion.Transaction.*;

public class PlantaDAOImp implements PlantaDAO {

	@SuppressWarnings("resource")
	@Override
	public Integer altaPlanta(TPlanta planta) {
		try {

			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();
			//frutal -> id, nombre_fruta, maduracion
			//no -> id, tipo_hoja
			PreparedStatement s = c.prepareStatement(
					"INSERT INTO planta (id_invernadero, nombre, nombre_cientifico, activo) VALUES(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, planta.get_id_invernadero());
			s.setString(2, planta.get_nombre());
			s.setString(3, planta.get_nombre_cientifico());
			s.setBoolean(4, planta.getActivo());
			s.executeUpdate();

			ResultSet r = s.getGeneratedKeys();

			if (r.next()) {

				int id = r.getInt(1);

				if (planta instanceof TPlantaFrutal) {
					s = c.prepareStatement(
							"INSERT INTO planta_frutal (id_planta, nombre_fruta, maduracion) VALUES(?,?,?)");
					s.setInt(1, id);
					s.setString(2, ((TPlantaFrutal) planta).get_nombre_fruta());
					s.setString(3, ((TPlantaFrutal) planta).get_maduracion());
					//s.setBoolean(4, planta.getActivo());
					if (s.executeUpdate() == 0) {
						s.close();
						r.close();
						return -1;
					}
				} else if (planta instanceof TPlantaNoFrutal) {
					s = c.prepareStatement("INSERT INTO planta_no_frutal (id, tipo_hoja) VALUES(?,?)");
					s.setInt(1, id);
					s.setString(2, ((TPlantaNoFrutal) planta).get_tipo_hoja());
					//s.setBoolean(3, planta.getActivo());
					if (s.executeUpdate() == 0) {
						s.close();
						r.close();
						return -1;
					}
				}
				s.close();
				r.close();

				return id;
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Integer bajaPlanta(Integer id) {
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement("UPDATE planta SET activo = ? WHERE id = ?");
			s.setBoolean(1, false);
			s.setInt(2, id);

			s.executeUpdate();
			s.close();
			return id;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public Set<TPlanta> listarPlantas() {

		Set<TPlanta> lplantas = new HashSet<>();
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM planta AS p " + "JOIN planta_frutal AS pf ON p.id = pf.id_planta FOR UPDATE"
			//+ "LEFT JOIN planta_no_frutal AS pn ON p.id = pn.id FOR UPDATE"
			);
			ResultSet r = s.executeQuery();

			TPlanta planta;
			while (r.next()) {

				TPlantaFrutal tpfrutal = new TPlantaFrutal();
				tpfrutal.set_id(r.getInt("id_planta"));
				tpfrutal.set_nombre(r.getString("nombre"));
				tpfrutal.set_nombre_cientifico(r.getString("nombre_cientifico"));
				tpfrutal.setActivo(r.getBoolean("activo"));
				tpfrutal.set_tipo(0);
				tpfrutal.set_nombre_fruta(r.getString("nombre_fruta"));
				tpfrutal.set_maduracion(r.getString("maduracion"));
				tpfrutal.set_id_invernadero(r.getInt("id_invernadero"));

				planta = tpfrutal;

				lplantas.add(planta);
			}
			r.close();
			s.close();
			s = c.prepareStatement("SELECT * FROM planta AS p " + //"JOIN planta_frutal AS pf ON p.id = pf.id_planta FOR UPDATE"
					" JOIN planta_no_frutal AS pn ON p.id = pn.id FOR UPDATE");
			r = s.executeQuery();

			while (r.next()) {

				TPlantaNoFrutal tpno = new TPlantaNoFrutal();
				tpno.set_id(r.getInt("id"));
				tpno.set_nombre(r.getString("nombre"));
				tpno.set_nombre_cientifico(r.getString("nombre_cientifico"));
				tpno.setActivo(r.getBoolean("activo"));
				tpno.set_tipo(1);
				tpno.set_tipo_hoja(r.getString("tipo_hoja"));
				tpno.set_id_invernadero(r.getInt("id_invernadero"));
				planta = tpno;
				lplantas.add(planta);
			}

			r.close();
			s.close();

			return lplantas;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("resource")
	@Override
	public Integer modificarPlanta(TPlanta planta) {

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c
					.prepareStatement("UPDATE planta SET nombre = ?, nombre_cientifico = ?, activo = ? WHERE id = ?");
			s.setString(1, planta.get_nombre());
			s.setString(2, planta.get_nombre_cientifico());
			s.setBoolean(3, planta.getActivo());
			s.setInt(4, planta.get_id());
			s.executeUpdate();
			s.close();
			if (planta instanceof TPlantaFrutal) {
				s = c.prepareStatement("UPDATE planta_frutal SET nombre_fruta = ?, maduracion = ? WHERE id_planta = ?");
				s.setString(1, ((TPlantaFrutal) planta).get_nombre_fruta());
				s.setString(2, ((TPlantaFrutal) planta).get_maduracion());
				s.setInt(3, planta.get_id());

			} else if (planta instanceof TPlantaNoFrutal) {
				s = c.prepareStatement("UPDATE planta_no_frutal SET tipo_hoja = ? WHERE id = ?");
				s.setString(1, ((TPlantaNoFrutal) planta).get_tipo_hoja());
				s.setInt(2, planta.get_id());

			}
			s.executeUpdate();
			s.close();
			return planta.get_id();

		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public TPlanta mostrarPorId(Integer id) {
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement("SELECT * FROM planta AS p "
					+ "JOIN planta_frutal AS pf ON p.id = pf.id_planta " + "WHERE p.id = ? FOR UPDATE");
			s.setInt(1, id);
			ResultSet r = s.executeQuery();
			TPlanta planta = null;

			if (r.next()) {
				TPlantaFrutal tpfrutal = new TPlantaFrutal();
				tpfrutal.set_id(r.getInt("id_planta"));
				tpfrutal.set_id_invernadero(r.getInt("id_invernadero"));
				tpfrutal.set_nombre(r.getString("nombre"));
				tpfrutal.set_nombre_cientifico(r.getString("nombre_cientifico"));
				tpfrutal.setActivo(r.getBoolean("activo"));
				tpfrutal.set_nombre_fruta(r.getString("nombre_fruta"));
				tpfrutal.set_maduracion(r.getString("maduracion"));
				tpfrutal.set_tipo(0);

				planta = tpfrutal;
			} else {
				s = c.prepareStatement("SELECT * FROM planta AS p " + "JOIN planta_no_frutal AS pn ON p.id = pn.id "
						+ "WHERE p.id = ? FOR UPDATE");
				s.setInt(1, id);
				r = s.executeQuery();

				if (r.next()) {
					TPlantaNoFrutal tpno = new TPlantaNoFrutal();
					tpno.set_id(r.getInt("id"));
					tpno.set_nombre(r.getString("nombre"));
					tpno.set_nombre_cientifico(r.getString("nombre_cientifico"));
					tpno.setActivo(r.getBoolean("activo"));
					tpno.set_id_invernadero(r.getInt("id_invernadero"));
					tpno.set_tipo_hoja(r.getString("tipo_hoja"));
					tpno.set_tipo(1);
					planta = tpno;
				}
			}

			r.close();
			s.close();
			return planta;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Set<TPlanta> mostrarPorTipo(Integer tipo) {
		Set<TPlanta> lplantas = new HashSet<>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement s;
			ResultSet r;
			TPlanta planta;

			if (tipo == 0) {

				s = c.prepareStatement(
						"SELECT * FROM planta AS p " + "JOIN planta_frutal AS pf ON p.id = pf.id_planta FOR UPDATE"
				//+ "LEFT JOIN planta_no_frutal AS pn ON p.id = pn.id FOR UPDATE"
				);
				r = s.executeQuery();

				while (r.next()) {

					TPlantaFrutal tpfrutal = new TPlantaFrutal();
					tpfrutal.set_id(r.getInt("id_planta"));
					tpfrutal.set_nombre(r.getString("nombre"));
					tpfrutal.set_nombre_cientifico(r.getString("nombre_cientifico"));
					tpfrutal.setActivo(r.getBoolean("activo"));
					tpfrutal.set_tipo(0);
					tpfrutal.set_nombre_fruta(r.getString("nombre_fruta"));
					tpfrutal.set_maduracion(r.getString("maduracion"));
					tpfrutal.set_id_invernadero(r.getInt("id_invernadero"));

					planta = tpfrutal;

					lplantas.add(planta);
				}

			} else {
				s = c.prepareStatement("SELECT * FROM planta AS p " + //"JOIN planta_frutal AS pf ON p.id = pf.id_planta FOR UPDATE"
						" JOIN planta_no_frutal AS pn ON p.id = pn.id FOR UPDATE");
				r = s.executeQuery();

				while (r.next()) {

					TPlantaNoFrutal tpno = new TPlantaNoFrutal();
					tpno.set_id(r.getInt("id"));
					tpno.set_nombre(r.getString("nombre"));
					tpno.set_nombre_cientifico(r.getString("nombre_cientifico"));
					tpno.setActivo(r.getBoolean("activo"));
					tpno.set_tipo(1);
					tpno.set_tipo_hoja(r.getString("tipo_hoja"));
					tpno.set_id_invernadero(r.getInt("id_invernadero"));
					planta = tpno;
					lplantas.add(planta);
				}

			}

			r.close();
			s.close();

			return lplantas;
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public Set<TPlanta> MostrarPorInvernadero(Integer id_invernadero) {
		Set<TPlanta> lplantas = new HashSet<>();
		TPlanta planta;
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c
					.prepareStatement("SELECT * FROM planta AS p " + "JOIN planta_frutal AS pf ON p.id = pf.id_planta "
			//+ "LEFT JOIN planta_no_frutal AS pn ON p.id = pn.id "
							+ "WHERE p.id_invernadero = ?");
			s.setInt(1, id_invernadero);
			ResultSet r = s.executeQuery();

			while (r.next()) {

				TPlantaFrutal tpfrutal = new TPlantaFrutal();
				tpfrutal.set_id(r.getInt("id_planta"));
				tpfrutal.set_nombre(r.getString("nombre"));
				tpfrutal.set_nombre_cientifico(r.getString("nombre_cientifico"));
				tpfrutal.setActivo(r.getBoolean("activo"));
				tpfrutal.set_tipo(0);
				tpfrutal.set_nombre_fruta(r.getString("nombre_fruta"));
				tpfrutal.set_maduracion(r.getString("maduracion"));
				tpfrutal.set_id_invernadero(r.getInt("id_invernadero"));

				planta = tpfrutal;

				lplantas.add(planta);

			}

			r.close();
			s.close();

			s = c.prepareStatement("SELECT * FROM planta AS p "
					//+ "LEFT JOIN planta_frutal AS pf ON p.id = pf.id_planta "
					+ "JOIN planta_no_frutal AS pn ON p.id = pn.id " + "WHERE p.id_invernadero = ?");
			s.setInt(1, id_invernadero);
			r = s.executeQuery();

			while (r.next()) {

				TPlantaNoFrutal tpno = new TPlantaNoFrutal();
				tpno.set_id(r.getInt("id"));
				tpno.set_nombre(r.getString("nombre"));
				tpno.set_nombre_cientifico(r.getString("nombre_cientifico"));
				tpno.setActivo(r.getBoolean("activo"));
				tpno.set_tipo(1);
				tpno.set_tipo_hoja(r.getString("tipo_hoja"));
				tpno.set_id_invernadero(r.getInt("id_invernadero"));
				planta = tpno;
				lplantas.add(planta);
			}

			r.close();
			s.close();

			return lplantas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}