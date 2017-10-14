package jbiketso.model.dao;

public interface DaoBase<T> {

    T save(T entity);

    void delete(T entity);

    T findById(Integer id);
}
