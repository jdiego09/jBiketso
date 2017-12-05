/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikBitacoraAtencion;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jdiego
 */
public class BitacoraAtencionDao extends BaseDao<Integer, BikBitacoraAtencion> {

    private static BitacoraAtencionDao INSTANCE;
    private BikBitacoraAtencion bitacora;

    private BitacoraAtencionDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (BitacoraAtencionDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new BitacoraAtencionDao();
                }
            }
        }
    }

    public static BitacoraAtencionDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setBitacora(BikBitacoraAtencion bitacora) {
        this.bitacora = bitacora;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<ArrayList<BikBitacoraAtencion>> getDetalleBitacora(BikBitacoraAtencion atencion) {
        Resultado<ArrayList<BikBitacoraAtencion>> resultado = new Resultado<>();
        ArrayList<BikBitacoraAtencion> listaAtencion = new ArrayList<>();
        List<BikBitacoraAtencion> atenciones;
        try {
            Query query = getEntityManager().createNamedQuery("BikBitacoraAtencion.findByCedulaDesc");
            query.setParameter("tipo", atencion.getBiaTipo());
            query.setParameter("cedula", atencion.getBiaCodusuario().getUsuPercodigo().getPerCedula());
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
            resultado.setMensaje("Error al traer el de talle de la atención recibida por el usuario [" + atencion.getBiaCodusuario().getUsuPercodigo().getNombreCompleto() + "].");
            return resultado;
        }
    }

    // Procedimiento para guardar la información de la persona.
    public Resultado<BikBitacoraAtencion> save() {
        Resultado<BikBitacoraAtencion> resultado = new Resultado<>();
        try {
            bitacora = (BikBitacoraAtencion) super.save(bitacora);

            if (bitacora.getBiaCodigo() != null && bitacora.getBiaCodigo() > 0) {
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(bitacora);
                resultado.setMensaje("Atención guardada correctamente.");

            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(bitacora);
                resultado.setMensaje("No se pudo guardar la atención brindada.");
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
