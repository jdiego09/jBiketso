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
import javafx.scene.control.Alert;
import javax.persistence.Query;
import jbiketso.model.entities.BikAccesoModulosView;
import jbiketso.model.entities.BikAccesoPantallasView;
import jbiketso.utils.AppWindowController;

/**
 *
 * @author jdiego
 */
public class SeguridadDao extends BaseDao {

    public ArrayList<BikAccesoPantallasView> getAccesosUsuario(String codigoUsuario) {
        ArrayList<BikAccesoPantallasView> accesos = new ArrayList<>();
        List<Object[]> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikAccesopantallasView.findByCodigoUsuario");
            query.setParameter("codigoUsuario", codigoUsuario);
            resultados = query.getResultList();
            for (Object[] row : resultados) {
                BikAccesoPantallasView acceso = new BikAccesoPantallasView((String) row[0], (String) row[1], (String) row[2],(String) row[3],(String) row[4],(String) row[5],(String) row[6],(String) row[7]);
                accesos.add(acceso);
            }
            return accesos;
        } catch (Exception ex) {
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error obteniendo accesos del usuario", "No se pudo cargar los accesos del usuario [" + codigoUsuario + "].");
            return accesos;
        }
    }

    public ArrayList<BikAccesoModulosView> getModulosUsuario(String codigoUsuario) {
        ArrayList<BikAccesoModulosView> modulos = new ArrayList<>();
        List<Object[]> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikAccesoModulosView.findByCodigoUsuario");
            query.setParameter("codigoUsuario", codigoUsuario);
            resultados = query.getResultList();
            for (Object[] row : resultados) {
                BikAccesoModulosView acceso = new BikAccesoModulosView((String) row[0], (String) row[1], (String) row[2]);
                modulos.add(acceso);
            }
            return modulos;
        } catch (Exception ex) {
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error obteniendo módulos del usuario", "No se pudo cargar los módulos del usuario [" + codigoUsuario + "].");
            return modulos;
        }
    }
}
