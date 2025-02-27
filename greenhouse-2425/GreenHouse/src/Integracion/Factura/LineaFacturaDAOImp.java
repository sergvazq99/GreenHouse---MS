package Integracion.Factura;

import Negocio.Factura.TLineaFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import Integracion.Transaction.TransaccionManager;

public class LineaFacturaDAOImp implements LineaFacturaDAO {

	public Integer crearLineaFactura(TLineaFactura lineaFactura) {
		try {
			Connection c = (Connection) TransaccionManager.getInstance().getTransaccion().getResource();
			PreparedStatement statement = c.prepareStatement(
					"INSERT INTO linea_factura (id_factura, id_entrada, cantidad, precio) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, lineaFactura.getidFactura());
			statement.setInt(2, lineaFactura.getidEntrada());
			statement.setInt(3, lineaFactura.getCantidad());
			statement.setDouble(4, lineaFactura.getPrecio());
			int affectedRows = statement.executeUpdate();

			statement.close();

			if (affectedRows == 0)
				return -1;

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public TLineaFactura bajaLineaFactura(Integer idFactura, Integer idEntrada) {
		TLineaFactura lineaFactura = null;
		try {
			Connection c = (Connection) TransaccionManager.getInstance().getTransaccion().getResource();
			PreparedStatement statement = c.prepareStatement(
					"DELETE FROM linea_factura WHERE id_factura = ? AND id_entrada = ?",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idFactura);
			statement.setInt(2, idEntrada);
			//int affectedRows = statement.executeUpdate();

			statement = c.prepareStatement(
					"SELECT * FROM linea_factura WHERE id_factura = ? AND id_entrada = ? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idFactura);
			statement.setInt(2, idEntrada);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				lineaFactura = new TLineaFactura();
				lineaFactura.setidFactura(result.getInt(1));
				lineaFactura.setidEntrada(result.getInt(2));
				lineaFactura.setCantidad(result.getInt(3));
				lineaFactura.setPrecio(result.getFloat(4));
			}

			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lineaFactura;
	}

	public Integer modificarLineaFactura(TLineaFactura tlineaFactura) {
		try {
			Connection c = (Connection) TransaccionManager.getInstance().getTransaccion().getResource();
			PreparedStatement statement = c.prepareStatement(
					"UPDATE linea_factura SET cantidad = ?, precio = ? WHERE id_factura = ? AND id_entrada = ?",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, tlineaFactura.getCantidad());
			statement.setFloat(2, tlineaFactura.getPrecio());
			statement.setInt(3, tlineaFactura.getidFactura());
			statement.setInt(4, tlineaFactura.getidEntrada());
			int affectedRows = statement.executeUpdate();

			statement.close();

			if (affectedRows == 0)
				return -1;

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public TLineaFactura mostrarLineaFactura(Integer idFactura, Integer idEntrada) {
		TLineaFactura lineafactura = null;
		try {
			Connection c = (Connection) TransaccionManager.getInstance().getTransaccion().getResource();
			PreparedStatement statement = c.prepareStatement(
					"SELECT * FROM linea_factura WHERE id_factura = ? AND id_entrada = ? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idFactura);
			statement.setInt(2, idEntrada);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				lineafactura = new TLineaFactura();
				lineafactura.setidFactura(result.getInt(1));
				lineafactura.setidEntrada(result.getInt(2));
				lineafactura.setCantidad(result.getInt(3));
				lineafactura.setPrecio(result.getFloat(4));
			}
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineafactura;
	}

	public Set<TLineaFactura> listarLineasDeFacturas() {
		Set<TLineaFactura> lineasfactura = null;

		try {
			Connection c = (Connection) TransaccionManager.getInstance().getTransaccion().getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM linea_factura");
			ResultSet result = statement.executeQuery();
			lineasfactura = new HashSet<TLineaFactura>();

			while (result.next()) {
				Integer idF = result.getInt(1);
				Integer idE = result.getInt(2);
				Integer cant = result.getInt(3);
				Float pre = result.getFloat(4);
				TLineaFactura f = new TLineaFactura();
				f.setidFactura(idF);
				f.setidEntrada(idE);
				f.setCantidad(cant);
				f.setPrecio(pre);
				lineasfactura.add(f);
			}

			result.close();
			statement.close();
			return lineasfactura;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineasfactura;
	}

	public Set<TLineaFactura> mostrarLineaFacturaPorFactura(Integer idFactura) {
		Set<TLineaFactura> lineasfactura = null;

		try {
			Connection c = (Connection) TransaccionManager.getInstance().getTransaccion().getResource();
			PreparedStatement statement = c.prepareStatement(
					"SELECT id_factura, id_entrada, cantidad, precio FROM linea_factura WHERE id_factura = ? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idFactura);
			ResultSet result = statement.executeQuery();
			lineasfactura = new HashSet<TLineaFactura>();

			while (result.next()) {
				Integer idF = result.getInt(1);
				Integer idE = result.getInt(2);
				Integer cant = result.getInt(3);
				Float pre = result.getFloat(4);
				TLineaFactura f = new TLineaFactura();
				f.setidFactura(idF);
				f.setidEntrada(idE);
				f.setCantidad(cant);
				f.setPrecio(pre);
				lineasfactura.add(f);
			}

			result.close();
			statement.close();
			return lineasfactura;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineasfactura;
	}
}