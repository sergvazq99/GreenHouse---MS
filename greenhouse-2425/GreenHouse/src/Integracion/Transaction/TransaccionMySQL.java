package Integracion.Transaction;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class TransaccionMySQL implements Transaccion {
	private static final String DB_NAME_PROP = "dbname";
	private static final String DB_HOST_PROP = "host";
	private static final String DB_PASSWORD_PROP = "password";
	private static final String DB_PORT_PROP = "port";
	private static final String DB_USER_PROP = "user";

	private String DB_properties;

	private Connection conexion;

	public TransaccionMySQL() throws Exception {
		try {
			DB_properties = "config/dbconfig.properties";
			Properties prop = new Properties();
			prop.load(new FileInputStream(DB_properties));
			String host = prop.getProperty(DB_HOST_PROP);
			String port = prop.getProperty(DB_PORT_PROP);
			String db = prop.getProperty(DB_NAME_PROP);
			String user = prop.getProperty(DB_USER_PROP);
			String password = prop.getProperty(DB_PASSWORD_PROP);

			String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + db + "?user=" + user + "&password="
					+ password + "&useSSL=false" + "&serverTimezone=Europe/Madrid";

			conexion = DriverManager.getConnection(connectionString);

		} catch (SQLException e) {
			conexion = null;
			System.out.println("Error al establecer la conexion");
			System.out.println(e.getMessage());
		}
	}

	public Void cerrarConnection() {

		return null;
	}

	public void start() throws Exception {
		conexion.setAutoCommit(false);
	}

	public void commit() throws Exception {
		conexion.commit();
		conexion.close();
		TransaccionManager t = TransaccionManager.getInstance();
		t.deleteTransaccion();
	}

	public void rollback() throws Exception {
		conexion.rollback();
		conexion.close();
		TransaccionManager t = TransaccionManager.getInstance();
		t.deleteTransaccion();
	}

	public Object getResource() {
		return conexion;
	}

}