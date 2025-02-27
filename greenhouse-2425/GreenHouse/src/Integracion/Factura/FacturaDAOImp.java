package Integracion.Factura;

import Negocio.Factura.TFactura;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

public class FacturaDAOImp implements FacturaDAO {

	public Integer cerrarFactura(TFactura tfactura) {
		TransaccionManager tManager = TransaccionManager.getInstance();
		int exito = -1;
		try {
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement(
					"INSERT INTO factura(precio_total ,fecha_compra, activo) VALUES (?,NOW(), true)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setFloat(1, tfactura.getPrecioTotal());
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			if (result.next())
				exito = result.getInt(1);
			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	public TFactura mostrarFactura(Integer id) {
		TFactura factura = null;
		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM factura WHERE id = ? FOR UPDATE");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				factura = new TFactura();
				factura.setid(result.getInt(1));
				factura.setPrecioTotal(result.getFloat(2));
				factura.setFechaCompra(new Date(result.getDate(3).getTime()));
				factura.setActivo(result.getBoolean(4));
			}
			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factura;
	}

	public Set<TFactura> listarFactura() {
		Set<TFactura> facturas = new LinkedHashSet<TFactura>();
		try {
			Transaccion t = TransaccionManager.getInstance().getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c.prepareStatement("SELECT * FROM factura FOR UPDATE");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				TFactura factura = new TFactura();
				factura.setid(result.getInt(1));
				factura.setPrecioTotal(result.getFloat(2));
				factura.setFechaCompra(new Date(result.getDate(3).getTime()));
				factura.setActivo(result.getBoolean(4));
				facturas.add(factura);
			}
			statement.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return facturas;
	}

	public Integer modificarFactura(TFactura tfactura) {
		int exito = -1;
		try {
			TransaccionManager tManager = TransaccionManager.getInstance();
			Transaccion t = tManager.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement statement = c
					.prepareStatement("UPDATE factura SET precio_total = ?, fecha_compra = ?, activo = ? WHERE id = ?");

			statement.setFloat(1, tfactura.getPrecioTotal());
			statement.setDate(2, new java.sql.Date(tfactura.getFechaCompra().getTime()));
			statement.setBoolean(3, tfactura.getActivo());
			statement.setInt(4, tfactura.getid());
			exito = statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (exito != -1)
			return tfactura.getid();
		else
			return exito;
	}

	public Integer devolverFactura(Integer id) {
		int exito = -1;
		try {
			Transaccion t = TransaccionManager.getInstance().getTransaccion();
			Connection c = (Connection) t.getResource();
			Statement s = c.createStatement();
			exito = s.executeUpdate("UPDATE factura SET activo = 0 WHERE id = " + id + ";");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}
}