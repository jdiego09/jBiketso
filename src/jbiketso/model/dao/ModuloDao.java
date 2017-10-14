package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.AppWindowController;

public class ModuloDao extends BaseDao {

    public BikModulos save(BikModulos modulo) {
        try {
            if (modulo.getModCodigo() != null && !modulo.getModCodigo().isEmpty()) {
                modulo = (BikModulos) super.save(modulo);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando módulo", "No hay información que guardar.");
            }
            return modulo;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando módulo", "Error al guardar el módulo.");
            return modulo;
        }
    }

    public void delete(BikModulos modulo) {
        try {
            if (modulo.getModCodigo() != null || !modulo.getModCodigo().isEmpty()) {
                super.delete(modulo);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Error eliminando módulo", "No se indicó un módulo para eliminar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error eliminando módulo", "Error al eliminar el módulo.");
        }
    }

    @Override
    public BikModulos findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
