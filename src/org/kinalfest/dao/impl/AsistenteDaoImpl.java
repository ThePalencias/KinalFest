package org.kinalfest.dao.impl;
import org.kinalfest.dao.AsistenteDao;
import org.kinalfest.model.Asistente;
import org.kinalfest.util.Conexion;
import org.kinalfest.exceptions.DBException;
import java.sql.*;
import java.util.*;
/** Operaciones CRUD mediante CallableStatement. */
public class AsistenteDaoImpl implements AsistenteDao {
    private void ejecutar(String proc, Object... valores) {
        String marcas=String.join(",",Collections.nCopies(valores.length,"?"));
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call "+proc+"("+marcas+")}")) {
            for(int i=0;i<valores.length;i++) st.setObject(i+1,valores[i]);
            st.execute();
        } catch(SQLException e) { throw new DBException("Error en "+proc,e); }
    }
    @Override public void insertar(Asistente x) {
        ejecutar("sp_AgregarAsistente", x.getNit(), x.getNombres(), x.getApellidos(), x.getTelefono(), x.getEmail());
    }
    @Override public void actualizar(Asistente x) {
        ejecutar("sp_ActualizarAsistente", x.getIdAsistente(), x.getNit(), x.getNombres(), x.getApellidos(), x.getTelefono(), x.getEmail());
    }
    @Override public void eliminar(int id) { ejecutar("sp_EliminarAsistente", id); }
    private Asistente mapear(ResultSet rs) throws SQLException {
        Asistente x=new Asistente();
        x.setIdAsistente(rs.getInt("idAsistente"));
        x.setNit(rs.getString("nit"));
        x.setNombres(rs.getString("nombres"));
        x.setApellidos(rs.getString("apellidos"));
        x.setTelefono(rs.getString("telefono"));
        x.setEmail(rs.getString("email"));
        return x;
    }
    @Override public List<Asistente> listar() {
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call sp_ListarAsistentes()}")) {
            try(ResultSet rs=st.executeQuery()) {
                List<Asistente> datos=new ArrayList<>();
                while(rs.next()) datos.add(mapear(rs));
                return datos;
            }
        } catch(SQLException e) { throw new DBException("Error en sp_ListarAsistentes",e); }
    }
    @Override public Asistente buscarPorId(int id) {
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call sp_BuscarAsistentePorId(?)}")) {
            st.setInt(1,id);
            try(ResultSet rs=st.executeQuery()) {
                return rs.next()?mapear(rs):null;
            }
        } catch(SQLException e) { throw new DBException("Error en sp_BuscarAsistentePorId",e); }
    }
}
