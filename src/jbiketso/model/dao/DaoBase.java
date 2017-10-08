package jbiketso.model.dao;

public interface DaoBase<T> {

    void guardar(T entity);

    void eliminar(T entity);

    T findById(Integer id);
}
