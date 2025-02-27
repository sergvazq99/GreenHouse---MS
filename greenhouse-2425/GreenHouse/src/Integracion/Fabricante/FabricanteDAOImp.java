package Integracion.Fabricante;

import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteExtranjero;
import Negocio.Fabricante.TFabricanteLocal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

@SuppressWarnings("resource")
public class FabricanteDAOImp implements FabricanteDAO {

	public Integer altaFabricante(TFabricante fabricante) {
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement(
					"INSERT INTO fabricante (cod_fabricante, nombre, telefono, activo) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			s.setString(1, fabricante.getCodFabricante());
			s.setString(2, fabricante.getNombre());
			s.setString(3, fabricante.getTelefono());
			s.setBoolean(4, fabricante.getActivo());
			s.executeUpdate();

			ResultSet r = s.getGeneratedKeys();
			if (r.next()) {
				int id = r.getInt(1);

				if (fabricante instanceof TFabricanteLocal) {
					s = c.prepareStatement(
							"INSERT INTO fabricante_local (id_fabricante, impuestos, subvenciones) Values (?, ?, ?)");
					s.setInt(1, id);
					s.setInt(2, ((TFabricanteLocal) fabricante).getImpuesto());
					s.setInt(3, ((TFabricanteLocal) fabricante).getSubvencion());

					if (s.executeUpdate() == 0) {
						s.close();
						r.close();
						return -1;
					}
				} else if (fabricante instanceof TFabricanteExtranjero) {
					s = c.prepareStatement(
							"INSERT INTO fabricante_extranjero (id_fabricante, aranceles, pais_origen) Values (?, ?, ?)");
					s.setInt(1, id);
					s.setInt(2, ((TFabricanteExtranjero) fabricante).getAranceles());
					s.setString(3, ((TFabricanteExtranjero) fabricante).getPaisDeOrigen());

					if (s.executeUpdate() == 0) {
						s.close();
						r.close();
						return -1;
					}
				}
				s.close();
				r.close();
				return id;
			} else
				return -1;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Integer bajaFabricante(Integer idFabricante) {
		try {

			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement statement = c.prepareStatement("UPDATE fabricante SET activo=false WHERE id=?");
			statement.setInt(1, idFabricante);

			statement.executeUpdate();
			statement.close();
			return idFabricante;
		} catch (Exception e) {
			return -1;
		}
	}

	public Integer modificarFabricante(TFabricante fabricante) {

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement(
					"UPDATE fabricante SET nombre=?, activo=?, telefono=?, cod_fabricante=? WHERE id=?");
			s.setString(1, fabricante.getNombre());
			s.setBoolean(2, fabricante.getActivo());
			s.setString(3, fabricante.getTelefono());
			s.setString(4, fabricante.getCodFabricante());
			s.setInt(5, fabricante.getId());

			s.executeUpdate();

			if (fabricante instanceof TFabricanteLocal) {
				s = c.prepareStatement("UPDATE fabricante_local SET impuestos=?, subvenciones=? WHERE id_fabricante=?");
				s.setInt(1, ((TFabricanteLocal) fabricante).getImpuesto());
				s.setInt(2, ((TFabricanteLocal) fabricante).getSubvencion());
				s.setInt(3, fabricante.getId());
			}

			if (fabricante instanceof TFabricanteExtranjero) {
				s = c.prepareStatement(
						"UPDATE fabricante_extranjero SET aranceles=?, pais_origen=? WHERE id_fabricante=?");
				s.setInt(1, ((TFabricanteExtranjero) fabricante).getAranceles());
				s.setString(2, ((TFabricanteExtranjero) fabricante).getPaisDeOrigen());
				s.setInt(3, fabricante.getId());
			}

			s.executeUpdate();
			s.close();

			return fabricante.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public Set<TFabricante> listarFabricantes() {
		Set<TFabricante> lFabricantes = new LinkedHashSet<>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM fabricante AS e " + "LEFT JOIN fabricante_local AS el ON e.id=el.id_fabricante "
							+ "LEFT JOIN fabricante_extranjero AS ez ON e.id=ez.id_fabricante FOR UPDATE");
			ResultSet r = s.executeQuery();

			while (r.next()) {
				TFabricante tFab;
				if (r.getString("pais_origen") == null) {
					tFab = new TFabricanteLocal();
					((TFabricanteLocal) tFab).setImpuesto(r.getInt("impuestos"));
					((TFabricanteLocal) tFab).setSubvencion(r.getInt("subvenciones"));
				} else {
					tFab = new TFabricanteExtranjero();
					((TFabricanteExtranjero) tFab).setAranceles(r.getInt("aranceles"));
					((TFabricanteExtranjero) tFab).setPaisDeOrigen(r.getString("pais_origen"));
				}
				tFab.setId(r.getInt("id"));
				tFab.setCodFabricante(r.getString("cod_fabricante"));
				tFab.setNombre(r.getString("nombre"));
				tFab.setTelefono(r.getString("telefono"));
				tFab.setActivo(r.getBoolean("activo"));
				lFabricantes.add(tFab);
			}

			r.close();
			s.close();
			return lFabricantes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Set<TFabricante> listarFabricantesExtrangeros() {

		return null;
	}

	public Set<TFabricante> listarFabricantesExtranjeros() {
		Set<TFabricante> lFabricantes = new LinkedHashSet<>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement("SELECT * FROM fabricante_extranjero AS e "
					+ "LEFT JOIN fabricante AS ez ON e.id_fabricante = ez.id FOR UPDATE");
			ResultSet r = s.executeQuery();

			while (r.next()) {

				TFabricanteExtranjero tExtranjero = new TFabricanteExtranjero();
				tExtranjero.setActivo(r.getBoolean("activo"));
				tExtranjero.setCodFabricante(r.getString("cod_fabricante"));
				tExtranjero.setId(r.getInt("id"));
				tExtranjero.setAranceles(r.getInt("aranceles"));
				tExtranjero.setNombre(r.getString("nombre"));
				tExtranjero.setPaisDeOrigen(r.getString("pais_origen"));
				tExtranjero.setTelefono(r.getString("telefono"));
				lFabricantes.add(tExtranjero);
			}

			r.close();
			s.close();
			return lFabricantes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Set<TFabricante> listarFabricantesLocales() {
		Set<TFabricante> lFabricantes = new LinkedHashSet<>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement("SELECT * FROM fabricante_local AS e "
					+ "LEFT JOIN fabricante AS ez ON e.id_fabricante = ez.id FOR UPDATE");
			ResultSet r = s.executeQuery();

			while (r.next()) {

				TFabricanteLocal tLocal = new TFabricanteLocal();
				tLocal.setActivo(r.getBoolean("activo"));
				tLocal.setImpuesto(r.getInt("impuestos"));
				tLocal.setCodFabricante(r.getString("cod_fabricante"));
				tLocal.setId(r.getInt("id"));
				tLocal.setNombre(r.getString("nombre"));
				tLocal.setSubvencion(r.getInt("impuestos"));
				tLocal.setTelefono(r.getString("telefono"));
				lFabricantes.add(tLocal);
			}

			r.close();
			s.close();
			return lFabricantes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public TFabricante mostrarFabricantePorId(Integer idFabricante) {
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM fabricante AS e JOIN fabricante_local AS el ON e.id=el.id_fabricante WHERE e.id=? FOR UPDATE");
			s.setInt(1, idFabricante);
			ResultSet r = s.executeQuery();
			TFabricante tf = null;

			if (r.next()) {
				TFabricanteLocal tLocal = new TFabricanteLocal();
				tLocal.setActivo(r.getBoolean("activo"));
				tLocal.setCodFabricante(r.getString("cod_fabricante"));
				tLocal.setId(r.getInt("id"));
				tLocal.setImpuesto(r.getInt("impuestos"));
				tLocal.setNombre(r.getString("nombre"));
				tLocal.setSubvencion(r.getInt("subvenciones"));
				tLocal.setTelefono(r.getString("telefono"));

				tf = tLocal;
			} else {
				s = c.prepareStatement(
						"SELECT * FROM fabricante AS e JOIN fabricante_extranjero AS ez ON e.id=ez.id_fabricante WHERE e.id=? FOR UPDATE");
				s.setInt(1, idFabricante);
				r = s.executeQuery();
				if (r.next()) {
					TFabricanteExtranjero tExtranjero = new TFabricanteExtranjero();
					tExtranjero.setActivo(r.getBoolean("activo"));
					tExtranjero.setAranceles(r.getInt("aranceles"));
					tExtranjero.setCodFabricante(r.getString("cod_fabricante"));
					tExtranjero.setId(r.getInt("id"));
					tExtranjero.setNombre(r.getString("nombre"));
					tExtranjero.setPaisDeOrigen(r.getString("pais_origen"));
					tExtranjero.setTelefono(r.getString("telefono"));

					tf = tExtranjero;
				}
			}

			r.close();
			s.close();
			return tf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public TFabricante leerPorCodFabricante(String codFabricante) {
		TFabricante tf = null;

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement(
					"SELECT * FROM fabricante AS e JOIN fabricante_local AS el ON e.id=el.id_fabricante WHERE e.cod_fabricante=? FOR UPDATE");
			s.setString(1, codFabricante);
			ResultSet r = s.executeQuery();

			if (r.next()) {
				TFabricanteLocal tfl = new TFabricanteLocal();
				tfl.setId(r.getInt("id"));
				tfl.setActivo(r.getBoolean("activo"));
				tfl.setCodFabricante(r.getString("cod_fabricante"));
				tfl.setNombre(r.getString("nombre"));
				tfl.setTelefono(r.getString("telefono"));
				tfl.setImpuesto(r.getInt("impuestos"));
				tfl.setSubvencion(r.getInt("subvenciones"));

				tf = tfl;
			} else {
				s = c.prepareStatement(
						"SELECT * FROM fabricante AS e JOIN fabricante_extranjero AS es ON e.id=es.id_fabricante WHERE e.cod_fabricante=? FOR UPDATE");
				s.setString(1, codFabricante);
				r = s.executeQuery();

				if (r.next()) {
					TFabricanteExtranjero tfe = new TFabricanteExtranjero();
					tfe.setId(r.getInt("id"));
					tfe.setActivo(r.getBoolean("activo"));
					tfe.setCodFabricante(r.getString("cod_fabricante"));
					tfe.setNombre(r.getString("nombre"));
					tfe.setTelefono(r.getString("telefono"));
					tfe.setAranceles(r.getInt("aranceles"));
					tfe.setPaisDeOrigen(r.getString("pais_origen"));

					tf = tfe;
				}
			}

			r.close();
			s.close();
			return tf;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}