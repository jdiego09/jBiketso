/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jdiego
 */
public class UsuarioDao extends BaseDao<Integer, BikUsuario> {

    private BikUsuario usuario;

    private static UsuarioDao INSTANCE;

    private UsuarioDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (UsuarioDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new UsuarioDao();
                }
            }
        }
    }

    public static UsuarioDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setUsuario(BikUsuario usuario) {
        this.usuario = usuario;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    
    public Resultado<BikUsuario> getUsuarioByCedula(String cedula) {
        Resultado<BikUsuario> result = new Resultado<>();
        try {
            Query query = getEntityManager().createNamedQuery("BikUsuario.findByCedula");
            query.setParameter("cedula", cedula);
            usuario = (BikUsuario) query.getSingleResult();

            result.setResultado(TipoResultado.SUCCESS);
            result.set(usuario);
            return result;
        } catch (NoResultException nre) {
            result.setResultado(TipoResultado.WARNING);
            result.setMensaje("El usuario con la cédula [" + cedula + "], no se encuentra registrado.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información del usuario con la cédula [" + cedula + "].");
            return result;
        }
    }
    
    /**
     * *
     * Función para guardar la información de un usuario del centro.
     *
     * @return el usuario guardado
     */
    public Resultado<BikUsuario> save() {
        Resultado<BikUsuario> result = new Resultado<>();
        try {
            if (usuario.getUsuCodigo() == null || usuario.getUsuCodigo() <= 0){
                usuario.setUsuUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
                usuario.setUsuFechaingresa(new Date());
            }
            usuario = (BikUsuario) super.save(usuario);
            result.setResultado(TipoResultado.SUCCESS);
            result.set(usuario);
            result.setMensaje("La información del usuario se guardó correctamente.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al guardar la información del usuario [" + usuario.getUsuPercodigo().getNombreCompleto() + "].");
            return result;
        }
    }

}
