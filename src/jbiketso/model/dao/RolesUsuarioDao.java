/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import jbiketso.model.entities.BikRolesUsuarios;
import jbiketso.utils.AppWindowController;

/**
 *
 * @author jdiego
 */
public class RolesUsuarioDao extends BaseDao{    
    public BikRolesUsuarios save(BikRolesUsuarios rolUsuario) {
        try {
            if (rolUsuario.getRouRolcodigo()!= null && !rolUsuario.getRouRolcodigo().getRolCodigo().isEmpty()) {
                rolUsuario = (BikRolesUsuarios) super.save(rolUsuario);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando rol por usuario", "No hay información que guardar.");
            }
            return rolUsuario;
        } catch (Exception ex) {
            Logger.getLogger(RolesUsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando rol por usuario", "Error al guardar la información.");
            return rolUsuario;
        }
    }
    
    public ArrayList<BikRolesUsuarios> findByRouUsscodigo(String codigoUsuario) {
         ArrayList<BikRolesUsuarios> rolesUsuario = new ArrayList<>();                
        try{
            Query query = getEntityManager().createNamedQuery("BikRolesUsuarios.findByRouUsscodigo");
            query.setParameter("ussCodigo", codigoUsuario);
            rolesUsuario = (ArrayList<BikRolesUsuarios>) query.getResultList();
            return rolesUsuario;
        }catch (Exception ex ){
            return rolesUsuario;
        }
    }
}
