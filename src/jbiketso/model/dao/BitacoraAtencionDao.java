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
import jbiketso.model.entities.BikBitacoraAtencionPK;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.Parametros;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jdiego
 */
public class BitacoraAtencionDao extends BaseDao<BikBitacoraAtencionPK, BikBitacoraAtencion> {

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

    public void setPersona(BikBitacoraAtencion bitacora) {
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
            query.setParameter("cedula", atencion.getBikUsuario().getUsuPercodigo().getPerCedula());
            atenciones = query.getResultList();
            atenciones.stream().forEach(listaAtencion::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaAtencion);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer el de talle de la atención recibida por el usuario [" + atencion.getBikUsuario().getUsuPercodigo().getNombreCompleto() + "].");
            return resultado;
        }
    }
}
