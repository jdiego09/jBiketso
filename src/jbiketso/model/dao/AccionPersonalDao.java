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
import javax.persistence.Query;
import jbiketso.model.entities.BikAccionesPersonal;
import jbiketso.model.entities.BikEvaluacion;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class AccionPersonalDao extends BaseDao<Integer, BikAccionesPersonal> {

    private static AccionPersonalDao INSTANCE;
    private BikAccionesPersonal accionPersonal;

    public AccionPersonalDao() {

    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (AccionPersonalDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new AccionPersonalDao();
                }
            }
        }
    }

    public static AccionPersonalDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setAccionPersonal(BikAccionesPersonal accionPersonal) {
        this.accionPersonal = accionPersonal;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<ArrayList<BikAccionesPersonal>> findAccionesPersonal(String cedula) {
        Resultado<ArrayList<BikAccionesPersonal>> result = new Resultado<>();
        ArrayList<BikAccionesPersonal> acciones = new ArrayList<>();
        List<BikAccionesPersonal> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikAccionesPersonal.findByCedula");
            query.setParameter("cedula", cedula);
            resultados = query.getResultList();
            resultados.forEach(m -> {
                acciones.add(m);
            });
            result.setResultado(TipoResultado.SUCCESS);
            result.set(acciones);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error consultando las acciones del personal del funcionario.");
            return result;
        }
    }

    public Resultado<ArrayList<BikEvaluacion>> findEvaluaciones(Integer codigoAccion) {
        Resultado<ArrayList<BikEvaluacion>> result = new Resultado<>();
        ArrayList<BikEvaluacion> evaluaciones = new ArrayList<>();
        List<BikEvaluacion> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikEvaluacion.findByCodigoAccion");
            query.setParameter("codAccion", codigoAccion);
            resultados = query.getResultList();
            resultados.forEach(m -> {
                m.getDescripcionTipoEvaluacion();
                evaluaciones.add(m);
            });
            result.setResultado(TipoResultado.SUCCESS);
            result.set(evaluaciones);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error consultando las evaluaciones.");
            return result;
        }
    }

}
