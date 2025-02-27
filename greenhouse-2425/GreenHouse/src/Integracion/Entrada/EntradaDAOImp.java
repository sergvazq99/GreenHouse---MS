package Integracion.Entrada;

import Negocio.Entrada.TEntrada;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

public class EntradaDAOImp implements EntradaDAO {

	public Integer altaEntrada(TEntrada entrada) {

		int id = -1;

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement(
					"INSERT INTO entrada (id_invernadero, fecha, precio, stock_entradas, activo) VALUES (?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, entrada.getIdInvernadero());
			ps.setDate(2, (Date) entrada.getFecha());
			ps.setFloat(3, entrada.getPrecio());
			ps.setInt(4, entrada.getStock());
			ps.setBoolean(5, entrada.getActivo());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				id = rs.getInt(1);

			if (ps != null)
				ps.close();

			if (rs != null)
				rs.close();

		} catch (Exception e) {

		}

		return id;
	}

	public Integer bajaEntrada(Integer id) {
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement("UPDATE entrada SET activo=false where id=?");

			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			return id;

		} catch (Exception e) {
			return -1;
		}
	}

	public Integer modificarEntrada(TEntrada entrada) {
		int res = -1;

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement(
					"UPDATE entrada SET id_invernadero=?, fecha=?, precio=?, stock_entradas=?, activo=? WHERE id=?");

			ps.setInt(1, entrada.getIdInvernadero());
			ps.setDate(2, (Date) entrada.getFecha());
			ps.setFloat(3, entrada.getPrecio());
			ps.setInt(4, entrada.getStock());
			ps.setBoolean(5, entrada.getActivo());
			ps.setInt(6, entrada.getId());
			ps.executeUpdate();
			ps.close();

			res = entrada.getId();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (res > 0) ? entrada.getId() : -1;
	}

	public TEntrada mostrarEntrada(Integer id) {
		TEntrada entrada = null;

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement("SELECT * FROM entrada WHERE id=? FOR UPDATE");

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				entrada = new TEntrada();
				entrada.setId(rs.getInt("id"));
				entrada.setIdInvernadero(rs.getInt("id_invernadero"));
				entrada.setFecha(rs.getDate("fecha"));
				entrada.setPrecio(rs.getFloat("precio"));
				entrada.setStock(rs.getInt("stock_entradas"));
				entrada.setActivo(rs.getBoolean("activo"));
			}
			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return entrada;
	}

	public Set<TEntrada> listarEntradas() {

		Set<TEntrada> entradas = new LinkedHashSet<TEntrada>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement("SELECT * FROM entrada FOR UPDATE");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TEntrada entrada = new TEntrada();
				entrada.setId(rs.getInt("id"));
				entrada.setIdInvernadero(rs.getInt("id_invernadero"));
				entrada.setFecha(rs.getDate("fecha"));
				entrada.setPrecio(rs.getFloat("precio"));
				entrada.setStock(rs.getInt("stock_entradas"));
				entrada.setActivo(rs.getBoolean("activo"));

				entradas.add(entrada);
			}

			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return entradas;
	}

	public Set<TEntrada> listarEntradasPorInvernadero(Integer idInvernadero) {

		Set<TEntrada> entradas = new LinkedHashSet<TEntrada>();

		try {

			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement("SELECT * FROM entrada WHERE id_invernadero=? FOR UPDATE");
			ps.setInt(1, idInvernadero);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TEntrada entrada = new TEntrada();
				entrada.setId(rs.getInt("id"));
				entrada.setIdInvernadero(rs.getInt("id_invernadero"));
				entrada.setFecha(rs.getDate("fecha"));
				entrada.setPrecio(rs.getFloat("precio"));
				entrada.setStock(rs.getInt("stock_entradas"));
				entrada.setActivo(rs.getBoolean("activo"));

				entradas.add(entrada);
			}

			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return entradas;
	}

	public TEntrada leerPorFechaUnica(Date fecha, Integer idInvernadero) {
		return null;
	}

	@Override
	public TEntrada leerPorIDInvernaderoYFecha(Date fecha, Integer idInvernadero) {

		TEntrada entrada = null;

		try {

			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();

			PreparedStatement ps = c.prepareStatement("SELECT * FROM entrada WHERE fecha = ? AND id_invernadero = ?");
			ps.setDate(1, fecha);
			ps.setInt(2, idInvernadero);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				entrada = new TEntrada();
				entrada.setId(rs.getInt("id"));
				entrada.setIdInvernadero(rs.getInt("id_invernadero"));
				entrada.setFecha(rs.getDate("fecha"));
				entrada.setPrecio(rs.getFloat("precio"));
				entrada.setStock(rs.getInt("stock_entradas"));
				entrada.setActivo(rs.getBoolean("activo"));
			}

			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return entrada;
	}

}