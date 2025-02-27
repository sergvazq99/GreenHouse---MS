package Integracion.FactoriaQuery;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

public class calcularLasTresFechasMasVendidasDeUnInvernadero implements Query {

	@Override
	public Object execute(Object object) {
		Set<Date> fechas = new HashSet<Date>();
		Integer id_Invernadero = (Integer) object;

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement(""
					+ "SELECT e.fecha AS fecha_entrada " 
					+ "FROM entrada e "
					+ "JOIN linea_factura lf ON e.id = lf.id_entrada " 
					+ "JOIN factura f ON lf.id_factura = f.id "
					+ "WHERE e.id_invernadero = ? " 
					+ "AND e.activo = 1 " 
					+ "AND f.activo = 1 " 
					+ "GROUP BY e.fecha "
					+ "ORDER BY SUM(lf.cantidad) DESC " 
					+ "LIMIT 3;");

			s.setInt(1, id_Invernadero);

			ResultSet r = s.executeQuery();

			while (r.next()) {
				Date date = new Date(r.getDate("fecha_entrada").getTime());
				fechas.add(date);
			}

			r.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fechas;
	}

}