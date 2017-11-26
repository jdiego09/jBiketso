/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikExpediente;
import jbiketso.model.entities.BikMedicamento;
import jbiketso.model.entities.BikPadecimiento;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.Parametros;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jcalvo
 */
public class ExpedienteDao extends BaseDao<Integer, BikExpediente> {

    private BikExpediente expediente;

    private static ExpedienteDao INSTANCE;

    private ExpedienteDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (ExpedienteDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new ExpedienteDao();
                }
            }
        }
    }

    public static ExpedienteDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setExpediente(BikExpediente expediente) {
        this.expediente = expediente;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Resultado<BikExpediente> getExpedienteByCedula(BikUsuario usuario) {
        Resultado<BikExpediente> result = new Resultado<>();
        BikExpediente expediente;
        try {
            Query query = getEntityManager().createNamedQuery("BikExpediente.findByCedulaUsuario");
            query.setParameter("cedula", usuario.getUsuPercodigo().getPerCedula());
            query.setParameter("codigoSede", usuario.getUsuSedcodigo().getSedCodigo());
            expediente = (BikExpediente) query.getSingleResult();

            result.setResultado(TipoResultado.SUCCESS);
            result.set(expediente);
            return result;
        } catch (NoResultException nre) {
            result.setResultado(TipoResultado.WARNING);
            result.setMensaje("El expediente para el usuario con la cédula [" + usuario.getUsuPercodigo().getPerCedula() + "], no se encuentra registrado.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información del expediente para el usuario con la cédula [" + usuario.getUsuPercodigo().getPerCedula() + "].");
            return result;
        }
    }

    /**
     * *
     * Función para guardar la información del expediente
     *
     * @return el expediente guardado
     */
    public Resultado<BikExpediente> save() {
        Resultado<BikExpediente> result = new Resultado<>();
        try {
            if (expediente.getExpCodigo() == null || expediente.getExpCodigo() <= 0) {
                expediente.setExpFechaIngreso(new Date());
                expediente.setExpUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
                expediente.getExpUsucodigo().setUsuSedcodigo(Aplicacion.getInstance().getDefaultSede());
            } else {
                expediente.setExpFechamodifica(new Date());
                expediente.setExpUsuariomodifica(Aplicacion.getInstance().getUsuario().getUssCodigo());
            }

            expediente = (BikExpediente) super.save(expediente);
            result.setResultado(TipoResultado.SUCCESS);
            result.set(expediente);
            result.setMensaje("El expediente del usuario se guardó correctamente.");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al guardar la información expediente del usuario [" + expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompleto() + "].");
            return result;
        }
    }

    public Resultado<ArrayList<BikMedicamento>> getMedicamentosActivos(BikExpediente expediente) {
        Resultado<ArrayList<BikMedicamento>> resultado = new Resultado<>();
        ArrayList<BikMedicamento> listaMedicamentos = new ArrayList<>();
        List<BikMedicamento> medicamentos;
        try {
            Query query = getEntityManager().createNamedQuery("BikMedicamento.findByExpedienteActivos");
            query.setParameter("codigoExpediente", expediente.getExpCodigo());
            medicamentos = query.getResultList();
            medicamentos.stream().forEach(listaMedicamentos::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaMedicamentos);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer medicamentos del usuario [" + expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompleto() + "].");
            return resultado;
        }
    }

    public Resultado<String> deleteMedicamento(BikMedicamento medicamento) {
        Resultado<String> resultado = new Resultado<>();
        try {

            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(medicamento);
            BikMedicamento existe = (BikMedicamento) getEntityManager().find(BikMedicamento.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(medicamento)) {
                    medicamento = getEntityManager().merge(medicamento);
                }
                getEntityManager().remove(medicamento);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar el medicamento.");
            return resultado;
        }
    }

    public Resultado<ArrayList<BikPadecimiento>> getPadecimientosActivos(BikExpediente expediente) {
        Resultado<ArrayList<BikPadecimiento>> resultado = new Resultado<>();
        ArrayList<BikPadecimiento> listaPadecimientos = new ArrayList<>();
        List<BikPadecimiento> padecimientos;
        try {
            Query query = getEntityManager().createNamedQuery("BikPadecimiento.findByExpedienteActivos");
            query.setParameter("codigoExpediente", expediente.getExpCodigo());
            padecimientos = query.getResultList();
            padecimientos.stream().forEach(listaPadecimientos::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaPadecimientos);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer padecimientos del usuario [" + expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompleto() + "].");
            return resultado;
        }
    }

    public Resultado<String> deletePadecimiento(BikPadecimiento padecimiento) {
        Resultado<String> resultado = new Resultado<>();
        try {

            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(padecimiento);
            BikPadecimiento existe = (BikPadecimiento) getEntityManager().find(BikPadecimiento.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(padecimiento)) {
                    padecimiento = getEntityManager().merge(padecimiento);
                }
                getEntityManager().remove(padecimiento);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(ExpedienteDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar el padecimiento.");
            return resultado;
        }
    }

}
