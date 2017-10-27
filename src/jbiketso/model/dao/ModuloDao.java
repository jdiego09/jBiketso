package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Query;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.GenEstados;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class ModuloDao extends BaseDao<String, BikModulos> {

    public SimpleStringProperty codigo;
    public SimpleStringProperty descripcion;
    public ObjectProperty<GenEstados> estado;

    private BikModulos modulo;

    public ModuloDao() {
        this.codigo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleObjectProperty<>();
    }

    public ModuloDao(String codigo, String descripcion, String estado) {
        this.codigo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.estado = new SimpleObjectProperty<>();

        this.codigo.set(codigo);
        this.descripcion.set(descripcion);
        if (estado.equalsIgnoreCase("a")) {
            this.estado.set(new GenEstados("A", "Activo"));
        } else {
            this.estado.set(new GenEstados("I", "Inactivo"));
        }

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

    public ObjectProperty<GenEstados> estadoProperty() {
        return estado;
    }

    public GenEstados getEstado() {
        return estado.get();
    }

    public void setEstado(GenEstados estado) {
        this.estado.set(estado);
    }

    public Resultado<BikModulos> save() {
        Resultado<BikModulos> result = new Resultado<>();
        try {
            modulo = new BikModulos(getCodigo(), getDescripcion(), getEstado().getCodigo());

            if (modulo.getModCodigo() != null && !modulo.getModCodigo().isEmpty()) {
                modulo = (BikModulos) super.save(modulo);
            } else {
                result.setResultado(TipoResultado.ERROR);
                result.setMensaje("No se ha indicado el código del módulo.");
                return result;
            }
            result.setResultado(TipoResultado.SUCCESS);
            result.set(modulo);
            result.setMensaje("Módulo guardado correctamente.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al guardar el módulo [" + modulo.getModCodigo() + "].");
            return result;
        }
    }

    public Resultado<BikModulos> delete() {
        Resultado<BikModulos> result = new Resultado<>();
        try {
            modulo = new BikModulos(getCodigo(), getDescripcion(), getEstado().getCodigo());
            if (modulo.getModCodigo() != null || !modulo.getModCodigo().isEmpty()) {
                super.delete(modulo);
                result.setResultado(TipoResultado.SUCCESS);
                result.setMensaje("El módulo [ " + modulo.getModCodigo() + "], fue eliminado correctamente.");
                return result;
            } else {
                result.setResultado(TipoResultado.ERROR);
                result.setMensaje("No se indicó un módulo para eliminar.");
                return result;
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("No se pudo eliminar el módulo [ " + modulo.getModCodigo() + "].");
            return result;
        }
    }

    @Override
    public BikModulos findById(String id) {
        return (BikModulos) super.findById(id);
    }

    public Resultado<ArrayList<BikModulos>> findByEstado(String estado) {
        Resultado<ArrayList<BikModulos>> result = new Resultado<>();
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
            result.setResultado(TipoResultado.SUCCESS);
            result.set(modulos);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(SeguridadDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.SUCCESS);
            result.setMensaje("Error consultando los módulos");
            return result;
        }
    }

}
