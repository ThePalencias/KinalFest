package org.kinalfest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kinalfest.exceptions.DBException;

public final class Conexion {

    private static final Logger LOG =
            Logger.getLogger(Conexion.class.getName());

    private static Conexion instancia;

    private final String URL ="jdbc:mysql://localhost:3306/libreriadb_in4cm"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";
    private final String USER = "IN4CM";
    private final String PASSWORD = "#NdimAM4";

    private Conexion() {
    }

    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }

        return instancia;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conexion = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );

            LOG.log(Level.INFO,
                    "Conexion a MySQL establecida correctamente.");

            return conexion;

        } catch (ClassNotFoundException | SQLException e) {

            LOG.log(Level.SEVERE,
                    "Error al conectar con MySQL", e);

            throw new DBException(
                    "No se pudo conectar a la base de datos.",
                    e
            );
        }
    }

    public void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }

            } catch (SQLException e) {
                LOG.log(Level.WARNING,
                        "Error al cerrar la conexion", e);
            }
        }
    }
}
