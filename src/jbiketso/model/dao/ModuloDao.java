package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.AppWindowController;

public class ModuloDao extends BaseDao {

    private SimpleStringProperty codigo;
    private SimpleStringProperty descripcion;
    private SimpleStringProperty estado;

    private BikModulos modulo;

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public BikModulos save() {
        try {
            modulo = new BikModulos(getCodigo(),getDescripcion(),getEstado());
            
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

    public void delete() {
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

    public BikModulos findById(String id) {
        return (BikModulos) super.findById(id);
    }

}
