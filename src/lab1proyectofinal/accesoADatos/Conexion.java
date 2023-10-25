package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Grupo-3
 */
public class Conexion {

    private static final String CLASSPATH = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB = "proyecto_transversal";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private Conexion() {
    }

    public static Connection getInstance() {

        if (connection == null) {
            try {
                Class.forName(CLASSPATH);

                connection = DriverManager.getConnection(URL + DB, USUARIO, PASSWORD);

                System.out.println("[Conexion] Conexión exitosa");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                System.out.println("[Conexion Error " + e.getErrorCode() + "] " + e.getMessage());
                e.printStackTrace();
            }
        }

        return connection;
    }

}
