package org.kinalfest.dao.impl;
import org.kinalfest.dao.RecursoDao;
import org.kinalfest.model.Recurso;
import org.kinalfest.util.Conexion;
import org.kinalfest.exceptions.DBException;
import java.sql.*;
import java.util.*;
/** Operaciones CRUD mediante CallableStatement. */
public class RecursoDaoImpl implements RecursoDao {
    private void ejecutar(String proc, Object... valores) {
        String marcas=String.join(",",Collections.nCopies(valores.length,"?"));
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call "+proc+"("+marcas+")}")) {
            for(int i=0;i<valores.length;i++) st.setObject(i+1,valores[i]);
            st.execute();
        } catch(SQLException e) { throw new DBException("Error en "+proc,e); }
    }
    @Override public void insertar(Recurso x) {
        ejecutar("sp_AgregarRecurso", x.getNombreRecurso(), x.getPrecioAlquiler(), x.getStock(), x.getProveedor());
    }
    @Override public void actualizar(Recurso x) {
        ejecutar("sp_ActualizarRecurso", x.getIdRecurso(), x.getNombreRecurso(), x.getPrecioAlquiler(), x.getStock(), x.getProveedor());
    }
    @Override public void eliminar(int id) { ejecutar("sp_EliminarRecurso", id); }
    private Recurso mapear(ResultSet rs) throws SQLException {
        Recurso x=new Recurso();
        x.setIdRecurso(rs.getInt("idRecurso"));
        x.setNombreRecurso(rs.getString("nombreRecurso"));
        x.setPrecioAlquiler(rs.getDouble("precioAlquiler"));
        x.setStock(rs.getInt("stock"));
        x.setProveedor(rs.getString("proveedor"));
        return x;
    }
    @Override public List<Recurso> listar() {
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call sp_ListarRecursos()}")) {
            try(ResultSet rs=st.executeQuery()) {
                List<Recurso> datos=new ArrayList<>();
                while(rs.next()) datos.add(mapear(rs));
                return datos;
            }
        } catch(SQLException e) { throw new DBException("Error en sp_ListarRecursos",e); }
    }
    @Override public Recurso buscarPorId(int id) {
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call sp_BuscarRecursoPorId(?)}")) {
            st.setInt(1,id);
            try(ResultSet rs=st.executeQuery()) {
                return rs.next()?mapear(rs):null;
            }
        } catch(SQLException e) { throw new DBException("Error en sp_BuscarRecursoPorId",e); }
    }
}
