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
import jbiketso.model.entities.BikFuncionario;
import jbiketso.model.entities.BikPuesto;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.Parametros;
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

    public void setPuesto(BikPuesto puesto) {
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
            Logger.getLogger(PuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información del puesto con el código [" + codigo + "].");
            return result;
        }
    }

    public Resultado<BikPuesto> save() {
        Resultado<BikPuesto> result = new Resultado<>();
        try {
            if (this.puesto.getPueCodigo() == null || this.puesto.getPueCodigo() <= 0) {
                this.puesto.setPueUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
                this.puesto.setPueFechaingresa(new Date());
            } else {
                this.puesto.setPueUsuariomodifica(Aplicacion.getInstance().getUsuario().getUssCodigo());
                this.puesto.setPueFechamodifica(new Date());
            }
            this.puesto = (BikPuesto) super.save(this.puesto);
            result.setResultado(TipoResultado.SUCCESS);
            result.set(this.puesto);
            result.setMensaje("Puesto guardado correctamente.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(PuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al guardar el puesto [" + this.puesto.getPueDescripcion() + "].");
            return result;
        }
    }

    public Resultado<String> deletePuesto(BikPuesto borrar) {
        Resultado<String> resultado = new Resultado<>();
        try {
            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(borrar);
            BikPuesto existe = (BikPuesto) getEntityManager().find(BikPuesto.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(borrar)) {
                    borrar = getEntityManager().merge(borrar);
                }
                getEntityManager().remove(borrar);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(PuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar puesto.");
            return resultado;
        }
    }

    public Resultado<ArrayList<BikPuesto>> findAll() {
        Resultado<ArrayList<BikPuesto>> result = new Resultado<>();
        ArrayList<BikPuesto> puestos = new ArrayList<>();
        List<BikPuesto> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikPuesto.findAll");
            resultados = query.getResultList();
            resultados.stream().forEach(puestos::add);
            result.setResultado(TipoResultado.SUCCESS);
            result.set(puestos);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(PuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error consultando los puestos.");
            return result;
        }
    }

}
