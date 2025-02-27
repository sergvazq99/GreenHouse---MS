package Integracion.Invernadero;

import Negocio.Invernadero.TInvernadero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

public class InvernaderoDAOImp implements InvernaderoDAO {

	public Integer altaInvernadero(TInvernadero invernadero) {
		int exito = -1;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement(
					"INSERT INTO invernadero(nombre, sustrato, tipo_iluminacion, activo) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, invernadero.getNombre());
			statement.setString(2, invernadero.getSustrato());
			statement.setString(3, invernadero.getTipo_iluminacion());
			statement.setBoolean(4, true);

			statement.executeUpdate();

			ResultSet result = statement.getGeneratedKeys();
			if (result.next()) {
				exito = result.getInt(1);
			}

			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exito;
	}

	public Integer bajaInvernadero(Integer id) {
		int exito = -1;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("UPDATE invernadero SET activo = false WHERE id = ?");

			statement.setInt(1, id);

			exito = statement.executeUpdate();

			if (exito == 1) {
				exito = id;
			}

			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exito;
	}

	public Integer modificarInvernadero(TInvernadero invernadero) {
		int exito = -1;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement(
					"UPDATE invernadero SET nombre = ?, sustrato = ?, tipo_iluminacion = ?, activo = ? where id = ?");

			statement.setString(1, invernadero.getNombre());
			statement.setString(2, invernadero.getSustrato());
			statement.setString(3, invernadero.getTipo_iluminacion());
			statement.setBoolean(4, invernadero.isActivo());
			statement.setInt(5, invernadero.getId());

			exito = statement.executeUpdate();

			if (exito == 1) {
				exito = invernadero.getId();
			}

			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exito;
	}

	public TInvernadero mostrarInvernaderoPorID(Integer id) {
		TInvernadero invernadero = null;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM invernadero WHERE id = ? FOR UPDATE");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				invernadero = new TInvernadero();
				invernadero.setId(result.getInt("id"));
				invernadero.setNombre(result.getString("nombre"));
				invernadero.setSustrato(result.getString("sustrato"));
				invernadero.setTipo_iluminacion(result.getString("tipo_iluminacion"));
				invernadero.setActivo(result.getBoolean("activo"));
			}

			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invernadero;
	}

	public TInvernadero mostrarInvernaderoPorNombre(String nombre) {
		TInvernadero invernadero = null;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c
					.prepareStatement("SELECT * FROM invernadero WHERE nombre like ? FOR UPDATE");
			statement.setString(1, nombre);

			ResultSet result = statement.executeQuery();

			if (result.next()) {
				invernadero = new TInvernadero();
				invernadero.setId(result.getInt("id"));
				invernadero.setNombre(result.getString("nombre"));
				invernadero.setSustrato(result.getString("sustrato"));
				invernadero.setTipo_iluminacion(result.getString("tipo_iluminacion"));
				invernadero.setActivo(result.getBoolean("activo"));
			}

			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invernadero;
	}

	public Set<TInvernadero> listarInvernadero() {
		Set<TInvernadero> invernaderos = new LinkedHashSet<>();
		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM invernadero FOR UPDATE");

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				TInvernadero invernadero = new TInvernadero();
				invernadero.setId(result.getInt("id"));
				invernadero.setNombre(result.getString("nombre"));
				invernadero.setSustrato(result.getString("sustrato"));
				invernadero.setTipo_iluminacion(result.getString("tipo_iluminacion"));
				invernadero.setActivo(result.getBoolean("activo"));
				invernaderos.add(invernadero);
			}

			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invernaderos;
	}

	public Set<TInvernadero> listarInvernaderoPorSR(Integer id_sistema_de_riego) {
		Set<TInvernadero> invernaderos = new LinkedHashSet<>();
		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("SELECT inv.* " + "FROM sistemas_riego_de_invernadero sri "
					+ "JOIN invernadero inv ON sri.id_invernadero = inv.id " + "WHERE sri.id_sistema_riego = ? ");

			statement.setInt(1, id_sistema_de_riego);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				TInvernadero invernadero = new TInvernadero();
				invernadero.setId(result.getInt("id"));
				invernadero.setNombre(result.getString("nombre"));
				invernadero.setSustrato(result.getString("sustrato"));
				invernadero.setTipo_iluminacion(result.getString("tipo_iluminacion"));
				invernadero.setActivo(result.getBoolean("activo"));
				invernaderos.add(invernadero);
			}

			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invernaderos;
	}

}