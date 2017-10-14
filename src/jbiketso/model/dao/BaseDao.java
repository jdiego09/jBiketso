package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import jbiketso.utils.Parametros;

public class BaseDao<T> implements DaoBase<T> {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Parametros.getENTITYMANAGERFACTORY().createEntityManager();
        }
        return entityManager;
    }

    @Override
    public T save(T entity) {
        try {
            if (entityManager.find(entity.getClass(), Parametros.getPERSISTENCEUTIL().getIdentifier(entity)) == null) {
                entityManager.persist(entity);
            } else {
                entityManager.merge(entity);
            }
            return entity;
        } catch (Exception ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            return entity;
        }
    }

    @Override
    public void delete(T entity) {
        try {
            if (entityManager.find(entity.getClass(), Parametros.getPERSISTENCEUTIL().getIdentifier(entity)) != null) {
                entityManager.remove(entity);
            } 
        } catch (Exception ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }

    @Override
    public T findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
