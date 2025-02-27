package Integracion.FactoriaQuery;

import java.util.Set;

import Integracion.Transaction.Transaccion;
import Integracion.Transaction.TransaccionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;

import Negocio.Fabricante.TFabricante;
import Negocio.Fabricante.TFabricanteExtranjero;
import Negocio.Fabricante.TFabricanteLocal;

public class ListarInformacionFabricantePorSistemasDeRiegoDeUnInvernadero implements Query {

	@Override
	public Object execute(Object object) {
		Integer id = (Integer) object;
		Set<TFabricante> lFabricantes = new LinkedHashSet<TFabricante>();

		try {
			TransaccionManager tm = TransaccionManager.getInstance();
			Transaccion t = tm.getTransaccion();
			Connection c = (Connection) t.getResource();
			PreparedStatement s = c.prepareStatement("SELECT F.* FROM fabricante F "
					+ "RIGHT JOIN fabricante_extranjero AS fe ON F.id = fe.id_fabricante "
					+ "JOIN sistemas_riego S ON F.id = S.id_fabricante "
					+ "JOIN sistemas_riego_de_invernadero SJI ON S.id = SJI.id_sistema_riego WHERE SJI.id_invernadero = ?");

			s.setInt(1, id);

			ResultSet r = s.executeQuery();

			while (r.next()) {
				TFabricanteExtranjero tf = new TFabricanteExtranjero();
				tf.setId(r.getInt("id"));
				tf.setActivo(r.getBoolean("activo"));
				tf.setCodFabricante(r.getString("cod_fabricante"));
				tf.setNombre(r.getString("nombre"));
				tf.setTelefono(r.getString("telefono"));
				if (tf.getActivo())
					lFabricantes.add(tf);
			}

			s = c.prepareStatement("SELECT F.* FROM fabricante F "
					+ "RIGHT JOIN fabricante_local AS fl ON F.id = fl.id_fabricante "
					+ "JOIN sistemas_riego S ON F.id = S.id_fabricante "
					+ "JOIN sistemas_riego_de_invernadero SJI ON S.id = SJI.id_sistema_riego WHERE SJI.id_invernadero = ?");

			s.setInt(1, id);

			r = s.executeQuery();

			while (r.next()) {
				TFabricanteLocal tf = new TFabricanteLocal();
				tf.setId(r.getInt("id"));
				tf.setActivo(r.getBoolean("activo"));
				tf.setCodFabricante(r.getString("cod_fabricante"));
				tf.setNombre(r.getString("nombre"));
				tf.setTelefono(r.getString("telefono"));
				if (tf.getActivo())
					lFabricantes.add(tf);
			}

			r.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lFabricantes;
	}
}