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
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class ModuloDao extends BaseDao<String, BikModulos> {

    private static ModuloDao INSTANCE;
    private BikModulos modulo;

    private ModuloDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (ModuloDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new ModuloDao();
                }
            }
        }
    }

    public static ModuloDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setModulo(BikModulos modulo) {
        this.modulo = modulo;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<BikModulos> save() {
        Resultado<BikModulos> result = new Resultado<>();
        try {
            if (this.modulo.getModCodigo() != null && !this.modulo.getModCodigo().isEmpty()) {
                this.modulo = (BikModulos) super.save(this.modulo);
            } else {
                result.setResultado(TipoResultado.ERROR);
                result.setMensaje("No se ha indicado el código del módulo.");
                return result;
            }
            result.setResultado(TipoResultado.SUCCESS);
            result.set(this.modulo);
            result.setMensaje("Módulo guardado correctamente.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al guardar el módulo [" + this.modulo.getModCodigo() + "].");
            return result;
        }
    }

    public Resultado<BikModulos> delete() {
        Resultado<BikModulos> result = new Resultado<>();
        try {
            if (this.modulo.getModCodigo() != null || !modulo.getModCodigo().isEmpty()) {
                super.delete(this.modulo);
                result.setResultado(TipoResultado.SUCCESS);
                result.setMensaje("El módulo [ " + this.modulo.getModCodigo() + "], fue eliminado correctamente.");
                return result;
            } else {
                result.setResultado(TipoResultado.ERROR);
                result.setMensaje("No se indicó un módulo para eliminar.");
                return result;
            }
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("No se pudo eliminar el módulo [ " + this.modulo.getModCodigo() + "].");
            return result;
        }
    }

    @Override
    public BikModulos findById(String id) {
        return (BikModulos) super.findById(id);
    }

    public Resultado<ArrayList<BikModulos>> findAll() {
        Resultado<ArrayList<BikModulos>> result = new Resultado<>();
        ArrayList<BikModulos> modulos = new ArrayList<>();
        List<BikModulos> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikModulos.findAll");
            resultados = query.getResultList();
            resultados.forEach(m -> {
                m.getDescripcionEstado();
                modulos.add(m);
            });
            result.setResultado(TipoResultado.SUCCESS);
            result.set(modulos);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error consultando los módulos");
            return result;
        }
    }

    public Resultado<ArrayList<BikModulos>> findByEstado(String estado) {
        Resultado<ArrayList<BikModulos>> result = new Resultado<>();
        ArrayList<BikModulos> modulos = new ArrayList<>();
        List<BikModulos> resultados;
        try {
            Query query = getEntityManager().createNamedQuery("BikModulos.findByEstado");
            query.setParameter("estado", estado);
            resultados = query.getResultList();
            resultados.forEach(m -> {
                m.getDescripcionEstado();
                modulos.add(m);
            });
            result.setResultado(TipoResultado.SUCCESS);
            result.set(modulos);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error consultando los módulos con estado [" + estado + "].");
            return result;
        }
    }
}
