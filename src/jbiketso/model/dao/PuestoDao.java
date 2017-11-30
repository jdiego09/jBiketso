/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikFuncionario;
import jbiketso.model.entities.BikPuesto;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class PuestoDao extends BaseDao<Integer, BikPuesto> {

    private static PuestoDao INSTANCE;
    private BikPuesto puesto;

    private PuestoDao() {

    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (PuestoDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new PuestoDao();
                }
            }
        }
    }

    public static PuestoDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setPersona(BikPuesto puesto) {
        this.puesto = puesto;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    public Resultado<BikPuesto> getPuestoByCodigo(Integer codigo) {
        Resultado<BikPuesto> result = new Resultado<>();
        BikPuesto puesto;
    try {
            Query query = getEntityManager().createNamedQuery("BikPuesto.findByPueCodigo");
            query.setParameter("pueCodigo", codigo);
            puesto = (BikPuesto) query.getSingleResult();

            result.setResultado(TipoResultado.SUCCESS);
            result.set(puesto);
            return result;

        } catch (NoResultException nre) {
            result.setResultado(TipoResultado.WARNING);
            result.setMensaje("El puesto con el código [" + codigo + "], no se encuentra registrado.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información del puesto con el código [" + codigo + "].");
            return result;
        }
    }
    
    
}
