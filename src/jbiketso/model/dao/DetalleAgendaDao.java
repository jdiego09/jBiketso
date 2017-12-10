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
import jbiketso.model.entities.BikDetalleAgenda;
import jbiketso.utils.Parametros;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jdiego
 */
public class DetalleAgendaDao extends BaseDao<Integer, BikDetalleAgenda> {

    private static DetalleAgendaDao INSTANCE;
    private BikDetalleAgenda detalle;

    private DetalleAgendaDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (DetalleAgendaDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new DetalleAgendaDao();
                }
            }
        }
    }

    public static DetalleAgendaDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setAgenda(BikDetalleAgenda detalle) {
        this.detalle = detalle;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<Boolean> existeEvento(Date fechaInicio, Date fechaFin) {
        Resultado<Boolean> resultado = new Resultado<>();
        ArrayList<BikDetalleAgenda> listaEventos = new ArrayList<>();
        List<BikDetalleAgenda> eventos;
        try {
            Query query = getEntityManager().createNamedQuery("BikDetalleAgenda.findEntreFechas");
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            eventos = query.getResultList();
            eventos.stream().forEach(listaEventos::add);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("No se puede registrar el evento en las fechas indicadas. Hay [" + eventos.size() + "] eventos registrados en esas fechas.");
            resultado.set(true);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(false);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(AgendaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al verificar evento.");
            return resultado;
        }
    }

    // Procedimiento para guardar la información de la persona.
    public Resultado<BikDetalleAgenda> save() {
        Resultado<BikDetalleAgenda> resultado = new Resultado<>();
        try {
            detalle = (BikDetalleAgenda) super.save(detalle);
            if (detalle.getDeaCodigo() != null && detalle.getDeaCodigo() > 0) {
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(detalle);
                resultado.setMensaje("El detalle de la agenda se guardó correctamente.");
            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(detalle);
                resultado.setMensaje("No se pudo guardar el detalle de la agenda.");
            }
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(DetalleAgendaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar el detalle de la agenda.");
            return resultado;
        }
    }

    public Resultado<String> deleteEvento(BikDetalleAgenda agenda) {
        Resultado<String> resultado = new Resultado<>();
        try {

            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(agenda);
            BikDetalleAgenda existe = (BikDetalleAgenda) getEntityManager().find(BikDetalleAgenda.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(agenda)) {
                    agenda = getEntityManager().merge(agenda);
                }
                getEntityManager().remove(agenda);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(DetalleAgendaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar evento de la agenda.");
            return resultado;
        }
    }
}
