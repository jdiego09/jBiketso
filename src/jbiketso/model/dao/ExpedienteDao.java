/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikExpediente;
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

   public void setUsuario(BikExpediente expediente) {
      this.expediente = expediente;
   }

   //para que solamente exista una instancia del objeto
   @Override
   public Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   public Resultado<BikExpediente> getExpedienteByCedula(String cedula) {
      Resultado<BikExpediente> result = new Resultado<>();
      try {
         Query query = getEntityManager().createNamedQuery("BikUsuario.findByCedula");
         query.setParameter("cedula", cedula);
         expediente = (BikExpediente) query.getSingleResult();

         result.setResultado(TipoResultado.SUCCESS);
         result.set(expediente);
         return result;
      } catch (NoResultException nre) {
         result.setResultado(TipoResultado.WARNING);
         result.setMensaje("El expediente del usuario con la cédula [" + cedula + "], no se encuentra registrado.");
         return result;
      } catch (Exception ex) {
         Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
         result.setResultado(TipoResultado.ERROR);
         result.setMensaje("Error al traer información del expediente del usuario con la cédula [" + cedula + "].");
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
         expediente = new BikExpediente();
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

}
