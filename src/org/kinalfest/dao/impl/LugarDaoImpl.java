package org.kinalfest.dao.impl;

import org.kinalfest.dao.LugarDao;
import org.kinalfest.model.Lugar;
import org.kinalfest.util.Conexion;
import org.kinalfest.exceptions.DBException;
import java.sql.*;
import java.util.*;

public class LugarDaoImpl implements LugarDao {

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
    public void insertar(Lugar x) {
        ejecutar("sp_AgregarLugar",
                x.getNombreLugar(),
                x.getEdificio(),
                x.getCapacidad(),
                x.getCodigoLugar());
    }

    @Override
    public void actualizar(Lugar x) {
        ejecutar("sp_ActualizarLugar",
                x.getIdLugar(),
                x.getNombreLugar(),
                x.getEdificio(),
                x.getCapacidad(),
                x.getCodigoLugar());
    }

    @Override
    public void eliminar(int id) {
        ejecutar("sp_EliminarLugar", id);
    }

    private Lugar mapear(ResultSet rs) throws SQLException {
        Lugar x = new Lugar();

        x.setIdLugar(rs.getInt("idLugar"));
        x.setNombreLugar(rs.getString("nombreLugar"));
        x.setEdificio(rs.getString("edificio"));
        x.setCapacidad(rs.getInt("capacidad"));
        x.setCodigoLugar(rs.getString("codigoLugar"));

        return x;
    }

    @Override
    public List<Lugar> listar() {
        try (Connection c = Conexion.getInstancia().getConnection();
             CallableStatement st = c.prepareCall("{call sp_ListarLugares()}")) {

            try (ResultSet rs = st.executeQuery()) {
                List<Lugar> datos = new ArrayList<>();

                while (rs.next()) {
                    datos.add(mapear(rs));
                }

                return datos;
            }

        } catch (SQLException e) {
            throw new DBException("Error en sp_ListarLugares", e);
        }
    }

    @Override
    public Lugar buscarPorId(int id) {
        try (Connection c = Conexion.getInstancia().getConnection();
             CallableStatement st = c.prepareCall("{call sp_BuscarLugarPorId(?)}")) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }

        } catch (SQLException e) {
            throw new DBException("Error en sp_BuscarLugarPorId", e);
        }
    }
}
