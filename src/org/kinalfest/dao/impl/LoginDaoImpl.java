package org.kinalfest.dao.impl;
import org.kinalfest.dao.LoginDao;
import org.kinalfest.model.UsuarioSesion;
import org.kinalfest.util.Conexion;
import org.kinalfest.exceptions.DBException;
import java.sql.*;
public class LoginDaoImpl implements LoginDao {
    @Override public UsuarioSesion autenticar(String usuario, String clave) {
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call sp_login(?,?)}")) {
            st.setString(1,usuario); st.setString(2,clave);
            try(ResultSet rs=st.executeQuery()) {
                if(!rs.next()) return null;
                UsuarioSesion u=new UsuarioSesion();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setUsuario(rs.getString("usuario"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch(SQLException e) { throw new DBException("Error al autenticar",e); }
    }
}
