package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javax.persistence.Query;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;

public class ModuloDao extends BaseDao<String, BikModulos> {

    public SimpleStringProperty codigo;
    public SimpleStringProperty descripcion;
    public SimpleStringProperty estado;

    private BikModulos modulo;

    public ModuloDao() {
        this.codigo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
    }

    public ModuloDao(String codigo, String descripcion, String estado) {
        this.codigo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();

        this.codigo.set(codigo);
        this.descripcion.set(descripcion);
        this.estado.set(estado);
    }

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
            modulo = new BikModulos(getCodigo(), getDescripcion(), getEstado());

            if (modulo.getModCodigo() != null && !modulo.getModCodigo().isEmpty()) {
                modulo = (BikModulos) super.save(modulo);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando módulo", "No hay información que guardar.");
            }
            AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Información guardada", "Módulo guardado correctamente.");
            return modulo;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando módulo", "Error al guardar el módulo.");
            return modulo;
        }
    }

    public void delete() {
        try {
            modulo = new BikModulos(getCodigo(), getDescripcion(), getEstado());
            if (modulo.getModCodigo() != null || !modulo.getModCodigo().isEmpty()) {
                super.delete(modulo);
                AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Información eliminada", "Módulo eliminado correctamente.");
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Error eliminando módulo", "No se indicó un módulo para eliminar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error eliminando módulo", "Error al eliminar el módulo.");
        }
    }

    @Override
    public BikModulos findById(String id) {
        return (BikModulos) super.findById(id);
    }

    public ArrayList<BikModulos> findByEstado(String estado) {
        ArrayList<BikModulos> modulos = new ArrayList<>();
        List<BikModulos> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikModulos.findByModEstado");
            query.setParameter("modEstado", estado);
            resultados = query.getResultList();
            resultados.forEach(m -> {
                m.getDescripcionEstado();
                modulos.add(m);
            });
            return modulos;
        } catch (Exception ex) {
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error obteniendo módulos", "No se pudo cargar los módulos.");
            return modulos;
        }
    }

}
