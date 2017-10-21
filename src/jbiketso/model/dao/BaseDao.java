package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import jbiketso.utils.Parametros;

public class BaseDao<K, E> implements DaoBase<K, E> {

    private Class<E> entityClass;
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Parametros.getENTITYMANAGERFACTORY().createEntityManager();
        }
        return entityManager;
    }

    @Override
    public E save(E entity) {
        try {
            K id = (K) Parametros.PERSISTENCEUTIL.getIdentifier(entity);
            E existe = (E) entityManager.find(entity.getClass(), id);
            if (existe != null) {
                entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
            return entity;
        } catch (Exception ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            return entity;
        }
    }

    @Override
    public void delete(E entity) {
        try {
            if (entityManager.find(entity.getClass(), Parametros.PERSISTENCEUTIL.getIdentifier(entity)) != null) {
                entityManager.remove(entity);
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public E findById(K id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception ex) {
            Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
