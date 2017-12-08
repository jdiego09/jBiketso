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
import jbiketso.model.entities.BikAgenda;
import jbiketso.model.entities.BikDetalleAgenda;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jdiego
 */
public class AgendaDao extends BaseDao<Integer, BikAgenda> {

    private static AgendaDao INSTANCE;
    private BikAgenda agenda;

    private AgendaDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (BitacoraAtencionDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new AgendaDao();
                }
            }
        }
    }

    public static AgendaDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setAgenda(BikAgenda agenda) {
        this.agenda = agenda;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<ArrayList<BikDetalleAgenda>> getDetalleAgenda(Date fechaInicio, Date fechaFin) {
        Resultado<ArrayList<BikDetalleAgenda>> resultado = new Resultado<>();
        ArrayList<BikDetalleAgenda> listaAtencion = new ArrayList<>();
        List<BikDetalleAgenda> atenciones;
        try {
            Query query = getEntityManager().createNamedQuery("BikDetalleAgenda.findPendientesFecha");
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            atenciones = query.getResultList();
            atenciones.stream().forEach(listaAtencion::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaAtencion);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(BitacoraAtencionDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer el detalle de la agenda.");
            return resultado;
        }
    }

    // Procedimiento para guardar la información de la persona.
    public Resultado<BikAgenda> save() {
        Resultado<BikAgenda> resultado = new Resultado<>();
        try {
            agenda = (BikAgenda) super.save(agenda);

            if (agenda.getAgeCodigo() != null && agenda.getAgeCodigo() > 0) {
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(agenda);
                resultado.setMensaje("Agenda guardada correctamente.");

            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(agenda);
                resultado.setMensaje("No se pudo guardar la agenda.");
            }

            return resultado;

        } catch (Exception ex) {
            Logger.getLogger(BitacoraAtencionDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar la persona.");
            return resultado;
        }
    }
}
