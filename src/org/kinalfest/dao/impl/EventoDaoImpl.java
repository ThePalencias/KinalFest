package org.kinalfest.dao.impl;
import org.kinalfest.dao.EventoDao;
import org.kinalfest.model.Evento;
import org.kinalfest.util.Conexion;
import org.kinalfest.exceptions.DBException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
public class EventoDaoImpl implements EventoDao {
    private void ejecutar(String nombre,Object... args) {
        String placeholders=String.join(",",Collections.nCopies(args.length,"?"));
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call "+nombre+"("+placeholders+")}")) {
            for(int i=0;i<args.length;i++) st.setObject(i+1,args[i]);
            st.execute();
        } catch(SQLException e) { throw new DBException("Error en "+nombre,e); }
    }
    @Override public void registrar(int lugar,int organizador,LocalDate fecha,String descripcion,double costo,int aforo,String afiche) {
        ejecutar("sp_RegistrarEvento",lugar,organizador,java.sql.Date.valueOf(fecha),descripcion,costo,aforo,afiche);
    }
    @Override public void asignarRecurso(int evento,int recurso,int cantidad) {
        ejecutar("sp_AgregarDetalleRecurso",evento,recurso,cantidad);
    }
    @Override public void aprobar(int evento,int director) { ejecutar("sp_AprobarEvento",evento,director); }
    @Override public void emitirTicket(int evento,int taquillero) { ejecutar("sp_EmitirTicket",evento,taquillero); }
    @Override public List<Evento> listar() {
        List<Evento> datos=new ArrayList<>();
        try(Connection c=Conexion.getInstancia().getConnection();
            CallableStatement st=c.prepareCall("{call sp_ListarEventos()}");
            ResultSet rs=st.executeQuery()) {
            while(rs.next()) {
                Evento e=new Evento();
                e.setIdEvento(rs.getInt("idEvento"));
                e.setIdLugar(rs.getInt("idLugar"));
                e.setIdOrganizador(rs.getInt("idOrganizador"));
                e.setFechaEvento(rs.getDate("fechaEvento").toLocalDate());
                e.setDescripcion(rs.getString("descripcion"));
                e.setCostoEntrada(rs.getDouble("costoEntrada"));
                e.setEstado(rs.getString("estado"));
                datos.add(e);
            }
        } catch(SQLException e) { throw new DBException("Error al listar eventos",e); }
        return datos;
    }
}
