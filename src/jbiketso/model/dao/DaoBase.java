package jbiketso.model.dao;

public interface DaoBase<T> {

    void save(T entity);

    void delete(T entity);

    T findById(Integer id);
}
