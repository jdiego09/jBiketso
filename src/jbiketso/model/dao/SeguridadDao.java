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
import jbiketso.utils.AccesoModulo;
import jbiketso.utils.AccesoPantalla;
import jbiketso.utils.AppWindowController;

/**
 *
 * @author jdiego
 */
public class SeguridadDao extends BaseDao {

    public ArrayList<AccesoPantalla> getAccesosUsuario(String codigoUsuario) {
        String strQuery = "SELECT \n"
                + "    m.menModcodigo.modCodigo codmodulo,\n"
                + "    m.menPantalla pantalla,\n"
                + "    m.menEtiqueta etiqueta,\n"
                + "    p.proConsulta consulta,\n"
                + "    p.proInserta inserta,\n"
                + "    p.proModifica modifica,\n"
                + "    p.proElimina elimina\n"
                + "    FROM\n"
                + "    BikMenu m,\n"
                + "    BikPermisoRol p,\n"
                + "    BikRolesUsuarios r,\n"
                + "    BikUsuariosSistema u\n"
                + "WHERE\n"
                + "    m.menModcodigo.modCodigo = p.proCodigomodulo\n"
                + "        AND m.menPantalla = p.proPantalla\n"
                + "        AND m.menEstado = 'A'\n"
                + "        AND p.proCodigorol = r.bikRolesUsuariosPK.rouRolcodigo\n"
                + "        AND r.bikRolesUsuariosPK.rouUsscodigo = u.ussCodigo\n"
                + "        AND r.rouEstado = 'A'\n"
                + "        AND u.ussCodigo = '" + codigoUsuario + "'"
                + "order by m.menModcodigo.modCodigo, m.menPantalla";
        ArrayList<AccesoPantalla> accesos = new ArrayList<>();

        try {
            Query query = getEntityManager().createNativeQuery(strQuery);
            accesos = (ArrayList<AccesoPantalla>) query.getResultList();
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
        try{
            Query query = getEntityManager().createNamedQuery("BikAccesoModulosView.findByCodigoUsuario");
            query.setParameter("codigoUsuario", codigoUsuario);
            resultados = query.getResultList();
            for (Object[] row : resultados){
                BikAccesoModulosView acceso = new BikAccesoModulosView((String)row[0],(String) row[1],(String) row[2]);
                modulos.add(acceso);
            }
            return modulos;
        }catch (Exception ex ){
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error obteniendo accesos del usuario", "No se pudo cargar los módulos del usuario [" + codigoUsuario + "].");
            return modulos;
        }        
    }
}
