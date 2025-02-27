package Integracion.SistemaDeRiego;

import Negocio.SistemaDeRiego.TSistemaDeRiego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

public class SistemaDeRiegoDAOImp implements SistemaDeRiegoDAO {

	public Integer altaSistemaDeRiego(TSistemaDeRiego sistemaDeRiego) {
		int res = -1;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO sistemas_riego (nombre, potencia_riego, cantidad_agua, frecuencia, activo, id_fabricante) VALUES (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			// Asignar parametos
			ps.setString(1, sistemaDeRiego.getNombre());
			ps.setInt(2, sistemaDeRiego.getPotenciaRiego());
			ps.setInt(3, sistemaDeRiego.getCantidad_agua());
			ps.setInt(4, sistemaDeRiego.getFrecuencia());
			ps.setBoolean(5, sistemaDeRiego.getActivo());
			ps.setInt(6, sistemaDeRiego.getIdFabricante());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				res = rs.getInt(1); // Obtener id generado
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public Integer bajaSistemaDeRiego(Integer id) {
		int res = -1;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement("UPDATE sistemas_riego SET activo = false WHERE id = ?");

			// Asignar id
			ps.setInt(1, id);
			res = ps.executeUpdate();

			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (res > 0) ? id : -1;
	}

	public Integer modificarSistemaDeRiego(TSistemaDeRiego sistemaDeRiego) {
		int res = -1;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement(
					"UPDATE sistemas_riego SET nombre = ?, potencia_riego = ?, cantidad_agua = ?, frecuencia = ?, activo = ?, id_fabricante = ? WHERE id = ?");

			// Asignar parametos
			ps.setString(1, sistemaDeRiego.getNombre());
			ps.setInt(2, sistemaDeRiego.getPotenciaRiego());
			ps.setInt(3, sistemaDeRiego.getCantidad_agua());
			ps.setInt(4, sistemaDeRiego.getFrecuencia());
			ps.setBoolean(5, sistemaDeRiego.getActivo());
			ps.setInt(6, sistemaDeRiego.getIdFabricante());
			ps.setInt(7, sistemaDeRiego.getId());

			res = ps.executeUpdate();

			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (res > 0) ? sistemaDeRiego.getId() : -1;
	}

	public TSistemaDeRiego mostrarSistemaDeRiegoPorID(Integer id) {
		TSistemaDeRiego sistemaDeRiego = null;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sistemas_riego WHERE id = ? FOR UPDATE");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			// Asignar parametos de vuelta al tsistemaDeRiego
			if (rs.next()) {
				sistemaDeRiego = new TSistemaDeRiego();
				sistemaDeRiego.setId(rs.getInt("id"));
				sistemaDeRiego.setNombre(rs.getString("nombre"));
				sistemaDeRiego.setPotenciaRiego(rs.getInt("potencia_riego"));
				sistemaDeRiego.setCantidad_agua(rs.getInt("cantidad_agua"));
				sistemaDeRiego.setFrecuencia(rs.getInt("frecuencia"));
				sistemaDeRiego.setActivo(rs.getBoolean("activo"));
				sistemaDeRiego.setIdFabricante(rs.getInt("id_fabricante"));
			}

			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sistemaDeRiego;
	}

	public Set<TSistemaDeRiego> listarSistemaDeRiegoPorFabricante(Integer idFabricante) {
		Set<TSistemaDeRiego> sistemasDeRiego = new LinkedHashSet<>();

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM sistemas_riego WHERE id_fabricante = ? FOR UPDATE");
			ps.setInt(1, idFabricante);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TSistemaDeRiego sistemaDeRiego = new TSistemaDeRiego();
				sistemaDeRiego.setId(rs.getInt("id"));
				sistemaDeRiego.setNombre(rs.getString("nombre"));
				sistemaDeRiego.setPotenciaRiego(rs.getInt("potencia_riego"));
				sistemaDeRiego.setCantidad_agua(rs.getInt("cantidad_agua"));
				sistemaDeRiego.setFrecuencia(rs.getInt("frecuencia"));
				sistemaDeRiego.setActivo(rs.getBoolean("activo"));
				sistemaDeRiego.setIdFabricante(rs.getInt("id_fabricante"));

				sistemasDeRiego.add(sistemaDeRiego);
			}

			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sistemasDeRiego;
	}

	public Set<TSistemaDeRiego> listarSistemaDeRiego() {
		Set<TSistemaDeRiego> sistemasDeRiego = new LinkedHashSet<>();

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sistemas_riego FOR UPDATE");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TSistemaDeRiego sistemaDeRiego = new TSistemaDeRiego();
				sistemaDeRiego.setId(rs.getInt("id"));
				sistemaDeRiego.setNombre(rs.getString("nombre"));
				sistemaDeRiego.setPotenciaRiego(rs.getInt("potencia_riego"));
				sistemaDeRiego.setCantidad_agua(rs.getInt("cantidad_agua"));
				sistemaDeRiego.setFrecuencia(rs.getInt("frecuencia"));
				sistemaDeRiego.setActivo(rs.getBoolean("activo"));
				sistemaDeRiego.setIdFabricante(rs.getInt("id_fabricante"));

				sistemasDeRiego.add(sistemaDeRiego);
			}

			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sistemasDeRiego;
	}

	public TSistemaDeRiego leerPorNombreUnico(String nombre) {
		TSistemaDeRiego sistemaDeRiego = null;

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sistemas_riego WHERE nombre = ? ");
			ps.setString(1, nombre);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				sistemaDeRiego = new TSistemaDeRiego();
				sistemaDeRiego.setId(rs.getInt("id"));
				sistemaDeRiego.setNombre(rs.getString("nombre"));
				sistemaDeRiego.setPotenciaRiego(rs.getInt("potencia_riego"));
				sistemaDeRiego.setCantidad_agua(rs.getInt("cantidad_agua"));
				sistemaDeRiego.setFrecuencia(rs.getInt("frecuencia"));
				sistemaDeRiego.setActivo(rs.getBoolean("activo"));
				sistemaDeRiego.setIdFabricante(rs.getInt("id_fabricante"));
			}

			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sistemaDeRiego;
	}

	public Set<TSistemaDeRiego> listarSistemaDeRiegoInvernadero(Integer idInvernadero) {
		Set<TSistemaDeRiego> sistemasDeRiego = new LinkedHashSet<>();

		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion trans = tManager.getTransaccion();
			Connection connection = (Connection) trans.getResource();

			PreparedStatement ps = connection.prepareStatement("SELECT sr.* " + "FROM sistemas_riego_de_invernadero ir "
					+ "JOIN sistemas_riego sr ON ir.id_sistema_riego = sr.id " + "WHERE ir.id_invernadero = ?");
			ps.setInt(1, idInvernadero);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TSistemaDeRiego sistemaDeRiego = new TSistemaDeRiego();
				sistemaDeRiego.setId(rs.getInt("id"));
				sistemaDeRiego.setNombre(rs.getString("nombre"));
				sistemaDeRiego.setPotenciaRiego(rs.getInt("potencia_riego"));
				sistemaDeRiego.setCantidad_agua(rs.getInt("cantidad_agua"));
				sistemaDeRiego.setFrecuencia(rs.getInt("frecuencia"));
				sistemaDeRiego.setActivo(rs.getBoolean("activo"));
				sistemaDeRiego.setIdFabricante(rs.getInt("id_fabricante"));

				sistemasDeRiego.add(sistemaDeRiego);
			}

			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sistemasDeRiego;
	}
}