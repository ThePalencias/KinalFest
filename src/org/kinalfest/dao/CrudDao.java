package org.kinalfest.dao;
import java.util.List;
public interface CrudDao<T> {
    void insertar(T entidad);
    void actualizar(T entidad);
    void eliminar(int id);
    List<T> listar();
    T buscarPorId(int id);
}
