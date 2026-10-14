package org.kinalfest.dao.impl;

import org.kinalfest.dao.OrganizadorDao;
import org.kinalfest.model.Organizador;
import org.kinalfest.util.Conexion;
import org.kinalfest.exceptions.DBException;
import java.sql.*;
import java.util.*;

public class OrganizadorDaoImpl implements OrganizadorDao {

    private void ejecutar(String proc, Object... valores) {
        String marcas = String.join(",", Collections.nCopies(valores.length, "?"));

        try (Connection c = Conexion.getInstancia().getConnection();
             CallableStatement st = c.prepareCall("{call " + proc + "(" + marcas + ")}")) {

            for (int i = 0; i < valores.length; i++) {
                st.setObject(i + 1, valores[i]);
            }

            st.execute();

        } catch (SQLException e) {
            throw new DBException("Error en " + proc, e);
        }
    }

    @Override
    public void insertar(Organizador x) {
        ejecutar("sp_AgregarOrganizador",
                x.getNombres(),
                x.getApellidos(),
                x.getTelefono(),
                x.getEspecialidad());
    }

    @Override
    public void actualizar(Organizador x) {
        ejecutar("sp_ActualizarOrganizador",
                x.getIdOrganizador(),
                x.getNombres(),
                x.getApellidos(),
                x.getTelefono(),
                x.getEspecialidad());
    }

    @Override
    public void eliminar(int id) {
        ejecutar("sp_EliminarOrganizador", id);
    }

    private Organizador mapear(ResultSet rs) throws SQLException {
        Organizador x = new Organizador();

        x.setIdOrganizador(rs.getInt("idOrganizador"));
        x.setNombres(rs.getString("nombres"));
        x.setApellidos(rs.getString("apellidos"));
        x.setTelefono(rs.getString("telefono"));
        x.setEspecialidad(rs.getString("especialidad"));

        return x;
    }

    @Override
    public List<Organizador> listar() {
        try (Connection c = Conexion.getInstancia().getConnection();
             CallableStatement st = c.prepareCall("{call sp_ListarOrganizadores()}")) {

            try (ResultSet rs = st.executeQuery()) {
                List<Organizador> datos = new ArrayList<>();

                while (rs.next()) {
                    datos.add(mapear(rs));
                }

                return datos;
            }

        } catch (SQLException e) {
            throw new DBException("Error en sp_ListarOrganizadores", e);
        }
    }

    @Override
    public Organizador buscarPorId(int id) {
        try (Connection c = Conexion.getInstancia().getConnection();
             CallableStatement st = c.prepareCall("{call sp_BuscarOrganizadorPorId(?)}")) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            throw new DBException("Error en sp_BuscarOrganizadorPorId", e);
        }
    }
}
