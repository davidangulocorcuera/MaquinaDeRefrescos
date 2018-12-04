package accesoDatos;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String,String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion" + e1.getMessage());
			//e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
        HashMap<Integer,Deposito>  hm_depositos = new HashMap<Integer,Deposito>();
		try (PreparedStatement stmt = conn1.prepareStatement("SELECT * FROM depositos")) { // Extrae listado de personas
			ResultSet rs = stmt.executeQuery(); // Almacena resultados
			Deposito deposito = new Deposito();
			while (rs.next()) { // Itera resultados
				deposito.setId(rs.getInt("id"));
				deposito.setNombreMoneda(rs.getString("nombre"));
				deposito.setValor(rs.getInt("valor"));
				deposito.setCantidad(rs.getInt("cantidad"));
				hm_depositos.put(rs.getInt("valor"), deposito);
				deposito = new Deposito();
			}

			} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return hm_depositos;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String,Dispensador>  hm_dispensadores = new HashMap<String,Dispensador>();
		try (PreparedStatement stmt = conn1.prepareStatement("SELECT * FROM dispensadores")) { // Extrae listado de personas
			ResultSet rs = stmt.executeQuery(); // Almacena resultados
			Dispensador dispensador = new Dispensador();
			while (rs.next()) { // Itera resultados
				dispensador.setId(rs.getInt("id"));
				dispensador.setClave(rs.getString("clave"));
				dispensador.setNombreProducto(rs.getString("nombre"));
				dispensador.setPrecio(rs.getInt("precio"));
				dispensador.setCantidad(rs.getInt("cantidad"));
				hm_dispensadores.put(rs.getString("clave"), dispensador);
				dispensador = new Dispensador();
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return hm_dispensadores;

	}


	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			Integer k = entry.getKey();
			Deposito v = entry.getValue();
			Deposito deposito = depositos.get(k);
			try {
				PreparedStatement stmt = conn1.prepareStatement("UPDATE depositos SET cantidad = ? WHERE valor = ? " );


			stmt.setInt(1,deposito.getCantidad());
					stmt.setInt(2,deposito.getValor());
			stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				}
			}
			return todoOK;}


	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		boolean bl_ok = true;
		for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			String k = entry.getKey();
			Dispensador v = entry.getValue();

			Dispensador dispensador = dispensadores.get(k);
			try {
				 PreparedStatement stmt = conn1.prepareStatement("UPDATE dispensadores SET cantidad = ? WHERE clave = ? " );


				stmt.setInt(1,dispensador.getCantidad());
				stmt.setString(2,dispensador.getClave());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return todoOK;}


} // Fin de la clase