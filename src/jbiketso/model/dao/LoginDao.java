package jbiketso.model.dao;

import javax.persistence.Query;
import jbiketso.model.entities.BikUsuariosSistema;

public class LoginDao extends BaseDao{    

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
