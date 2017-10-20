/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import jbiketso.model.entities.BikMenu;
import jbiketso.model.entities.BikModulos;
import jbiketso.model.entities.BikPermisoRol;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;

/**
 *
 * @author jdiego
 */
public class SeguridadDao extends BaseDao {

    public ArrayList<BikPermisoRol> getAccesosUsuario(String codigoRol) {
        ArrayList<BikPermisoRol> accesos = new ArrayList<>();
        List<BikPermisoRol> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikPermisoRol.findMenuByRol");
            query.setParameter("codigoRol", Arrays.asList(codigoRol.split(",")));
            resultados = query.getResultList();
            resultados.forEach(accesos::add);
            return accesos;
        } catch (Exception ex) {
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error obteniendo accesos del usuario", "No se pudo cargar los accesos del usuario [" + Aplicacion.getInstance().getUsuario().getUssCodigo() + "].");
            return accesos;
        }
    }

    public ArrayList<BikModulos> getModulosUsuario(String codigoRol) {
        ArrayList<BikModulos> modulos = new ArrayList<>();
        List<BikModulos> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikPermisoRol.findModulosByRol");
            query.setParameter("codigoRol", Arrays.asList(codigoRol.split(",")));
            resultados = query.getResultList();
            resultados.forEach(modulos::add);
            return modulos;
        } catch (Exception ex) {
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error obteniendo módulos del usuario", "No se pudo cargar los módulos del usuario [" + Aplicacion.getInstance().getUsuario().getUssCodigo() + "].");
            return modulos;
        }
    }
}
