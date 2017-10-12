package jbiketso.model.dao;

import javax.persistence.EntityManager;
import jbiketso.utils.Parametros;

public class BaseDao implements DaoBase {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Parametros.getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    @Override
    public void save(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
