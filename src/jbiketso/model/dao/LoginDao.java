package jbiketso.model.dao;

import javax.persistence.Query;
import jbiketso.model.entities.BikUsuariosSistema;

public class LoginDao extends BaseDao {    

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

    public BikUsuariosSistema findByUssCodigo(String codigo) {
        BikUsuariosSistema usuario = new BikUsuariosSistema();                
        try{
            Query query = getEntityManager().createNamedQuery("BikUsuariosSistema.findByUssCodigo");
            query.setParameter("ussCodigo", codigo);
            usuario = (BikUsuariosSistema) query.getSingleResult();
            return usuario;
        }catch (Exception ex ){
            return usuario;
        }
    }

}
