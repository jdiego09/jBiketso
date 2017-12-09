/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikRoles;
import jbiketso.model.entities.BikRolesUsuarios;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.Parametros;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class UsuariosSistemaDao extends BaseDao<Integer, BikUsuariosSistema> {

    private static UsuariosSistemaDao INSTANCE;
    private BikUsuariosSistema usuarioSistema;

    public UsuariosSistemaDao() {

    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (UsuariosSistemaDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new UsuariosSistemaDao();
                }
            }
        }
    }

    public static UsuariosSistemaDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setUsuarioSistema(BikUsuariosSistema usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<BikUsuariosSistema> getUsuarioByCodigo(String codigo) {
        Resultado<BikUsuariosSistema> result = new Resultado<>();
        BikUsuariosSistema usuario;
        try {
            Query query = getEntityManager().createNamedQuery("BikUsuariosSistema.findByUssCodigo");
            query.setParameter("ussCodigo", codigo);
            usuario = (BikUsuariosSistema) query.getSingleResult();

            result.setResultado(TipoResultado.SUCCESS);
            result.set(usuario);
            return result;

        } catch (NoResultException nre) {
            result.setResultado(TipoResultado.WARNING);
            result.setMensaje("El usuario con la código [" + codigo + "], no se encuentra registrado.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información del usuario con código [" + codigo + "].");
            return result;
        }
    }

    public Resultado<ArrayList<BikUsuariosSistema>> findUsuariosSistema() {
        Resultado<ArrayList<BikUsuariosSistema>> result = new Resultado<>();
        ArrayList<BikUsuariosSistema> usuarios = new ArrayList<>();
        List<BikUsuariosSistema> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikUsuariosSistema.findAll");
            resultados = query.getResultList();
            resultados.forEach(m -> {
                usuarios.add(m);
            });
            result.setResultado(TipoResultado.SUCCESS);
            result.set(usuarios);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error consultando los usuarios del sistema.");
            return result;
        }
    }

    public Resultado<ArrayList<BikRolesUsuarios>> getRolesUsuario(String usuario) {
        Resultado<ArrayList<BikRolesUsuarios>> resultado = new Resultado<>();
        ArrayList<BikRolesUsuarios> listaRoles = new ArrayList<>();
        List<BikRolesUsuarios> roles;
        try {
            Query query = getEntityManager().createNamedQuery("BikRolesUsuarios.findByCodigoUsuario");
            query.setParameter("codUsuario", usuario);
            roles = query.getResultList();
            roles.stream().forEach(listaRoles::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaRoles);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer los roles del usuario [" + usuario + "].");
            return resultado;
        }
    }

    public Resultado<String> deleteRol(BikRolesUsuarios rolUsuario) {
        Resultado<String> resultado = new Resultado<>();
        try {

            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(rolUsuario);
            BikRolesUsuarios existe = (BikRolesUsuarios) getEntityManager().find(BikRolesUsuarios.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(rolUsuario)) {
                    rolUsuario = getEntityManager().merge(rolUsuario);
                }
                getEntityManager().remove(rolUsuario);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar el rol para el usuario " + rolUsuario.getRouRolcodigo().getRolCodigo() + ".");
            return resultado;
        }
    }

    // Procedimiento para guardar la información del usuario del sistema.
    public Resultado<BikUsuariosSistema> save() {
        Resultado<BikUsuariosSistema> resultado = new Resultado<>();
        try {
            
            this.usuarioSistema.setUssFechaingresa(new Date());
            this.usuarioSistema.setUssUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
            this.usuarioSistema = (BikUsuariosSistema) super.save(this.usuarioSistema);

            if (this.usuarioSistema.getUssCodigo() != null) {
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(this.usuarioSistema);
                resultado.setMensaje("Usuario guardado correctamente.");

            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(this.usuarioSistema);
                resultado.setMensaje("No se pudo guardar la persona.");
            }

            return resultado;

        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar el usuario.");
            return resultado;
        }
    }

}
