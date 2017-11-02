/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Query;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author jdiego
 */
public class UsuarioDao extends BaseDao<Integer, BikUsuario> {

   private BikUsuario usuario;

   public PersonaDao usuarioDao;
   public PersonaDao encargadoDao;

   public SimpleStringProperty nombreUsuario;
   public SimpleStringProperty nombreEncargado;

   public UsuarioDao() {
      this.usuarioDao = new PersonaDao();
      this.encargadoDao = new PersonaDao();
      this.nombreUsuario = new SimpleStringProperty();
      this.nombreEncargado = new SimpleStringProperty();
   }

   public PersonaDao getUsuarioDao() {
      return usuarioDao;
   }

   public void setUsuarioDao(PersonaDao usuarioDao) {
      this.usuarioDao = usuarioDao;
   }

   public PersonaDao getEncargadoDao() {
      return encargadoDao;
   }

   public void setEncargadoDao(PersonaDao encargadoDao) {
      this.encargadoDao = encargadoDao;
   }

   public SimpleStringProperty getNombreUsuario() {
      return nombreUsuario;
   }

   public void setNombreUsuario(SimpleStringProperty nombreUsuario) {
      this.nombreUsuario = nombreUsuario;
   }

   public SimpleStringProperty getNombreEncargado() {
      return nombreEncargado;
   }

   public void setNombreEncargado(SimpleStringProperty nombreEncargado) {
      this.nombreEncargado = nombreEncargado;
   }

   
   
   /**
    * *
    * Función para obtener la información de una persona de acuerdo a su número
    * de identificación.
    *
    * @param cedula número de identificación de la persona buscada
    * @return la información de la persona buscada
    */
   public Resultado<BikPersona> getPersona(String cedula) {
      Resultado<BikPersona> result = new Resultado<>();
      BikPersona persona;

      try {
         Query query = getEntityManager().createNamedQuery("BikPersona.findByPerCedula");
         query.setParameter("perCedula", cedula);
         persona = (BikPersona) query.getSingleResult();

         result.setResultado(TipoResultado.SUCCESS);
         result.set(persona);
         return result;
      } catch (Exception ex) {
         Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
         result.setResultado(TipoResultado.ERROR);
         result.setMensaje("Error al traer información de la persona con la cédula [" + cedula + "].");
         return result;
      }
   }

   /**
    * *
    * Función para guardar la información de un usuario del centro.
    *
    * @return el usuario guardado
    */
   public Resultado<BikUsuario> save() {
      Resultado<BikUsuario> result = new Resultado<>();
      try {
         usuario = new BikUsuario();
         usuario = (BikUsuario) super.save(usuario);
         result.setResultado(TipoResultado.SUCCESS);
         result.set(usuario);
         result.setMensaje("La información del usuario se guardó correctamente.");
         return result;
      } catch (Exception ex) {
         Logger.getLogger(ModuloDao.class.getName()).log(Level.SEVERE, null, ex);
         result.setResultado(TipoResultado.ERROR);
         result.setMensaje("Error al guardar la información del usuario [" + usuario.getUsuPercodigo().getNombreCompleto() + "].");
         return result;
      }
   }

}
