package org.kinalfest.dao;
import java.util.List;
import org.kinalfest.model.Evento;
public interface EventoDao {
    void registrar(int lugar,int organizador,java.time.LocalDate fecha,String descripcion,double costo,int aforo,String afiche);
    List<Evento> listar();
    void asignarRecurso(int evento,int recurso,int cantidad);
    void aprobar(int evento,int director);
    void emitirTicket(int evento,int taquillero);
}
