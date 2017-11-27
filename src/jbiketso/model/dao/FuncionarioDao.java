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
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class FuncionarioDao extends BaseDao<Integer, BikFuncionario> {

    private static FuncionarioDao INSTANCE;
    private BikFuncionario funcionario;

    private FuncionarioDao() {

    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (FuncionarioDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new FuncionarioDao();
                }
            }
        }
    }

    public static FuncionarioDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setPersona(BikFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /*public Resultado<BikFuncionario> getFuncionarioByCedula(String cedulaFuncionario) {
        Resultado<BikFuncionario> result = new Resultado<>();
        BikFuncionario funcionario;
        try {
            Query query = getEntityManager().createNamedQuery("BikExpediente.findByCedulaUsuario");
            query.setParameter("cedula", usuario.getUsuPercodigo().getPerCedula());
            query.setParameter("codigoSede", usuario.getUsuSedcodigo().getSedCodigo());
            expediente = (BikExpediente) query.getSingleResult();

            result.setResultado(TipoResultado.SUCCESS);
            result.set(expediente);
            return result;
            
        } catch (NoResultException nre) {
            result.setResultado(TipoResultado.WARNING);
            result.setMensaje("El funcionario con la cédula [" + cedulaFuncionario + "], no se encuentra registrado.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información del funcionario con la cédula [" + cedulaFuncionario + "].");
            return result;
        }
    }*/

}