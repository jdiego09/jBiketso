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
import jbiketso.model.entities.BikCentro;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikSede;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class CentroDao extends BaseDao<Integer, BikCentro> {

    private static CentroDao INSTANCE;
    private BikCentro centro;

    private CentroDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (CentroDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new CentroDao();
                }
            }
        }
    }

    public static CentroDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setCentro(BikCentro centro) {
        this.centro = centro;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<ArrayList<BikCentro>> findAll() {
        Resultado<ArrayList<BikCentro>> resultado = new Resultado<>();
        ArrayList<BikCentro> centros = new ArrayList<>();
        List<BikCentro> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikCentro.findAll");
            resultados = query.getResultList();
            resultados.forEach(m -> {
                m.getDescripcionEstado();
                centros.add(m);
            });
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(centros);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error consultando los centros.");
            return resultado;
        }
    }

    public Resultado<BikSede> findSedeByCodigo(Integer codigoSede) {
        BikSede sede;
        Resultado<BikSede> resultado = new Resultado<>();
        try {
            Query query = getEntityManager().createNamedQuery("BikSede.findBySedCodigo");
            query.setParameter("sedCodigo", codigoSede);
            sede = (BikSede) query.getSingleResult();

            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(sede);
            return resultado;
        } catch (NoResultException nre) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, nre);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("No existe la sede con el código [" + String.valueOf(codigoSede) + "].");
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error consultando la sede con el código [" + String.valueOf(codigoSede) + "].");
            return resultado;
        }
    }

    // Procedimiento para guardar la información del centro.
    public Resultado<BikCentro> save() {
        Resultado<BikCentro> resultado = new Resultado<>();
        try {

            BikPersona persona = new BikPersona();
            persona.setPerCodigo(3);
            centro.setCenCodrepresentantelegal(persona);

            centro = (BikCentro) super.save(centro);

            if (centro.getCenCodigo() != null) {
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(centro);
                resultado.setMensaje("Centro guardado correctamente.");

            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(centro);
                resultado.setMensaje("No se pudo guardar el centro.");
            }

            return resultado;

        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar el módulo [" + this.centro.getCenCodigo() + "].");
            return resultado;
        }
    }

}
