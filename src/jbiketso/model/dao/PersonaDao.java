/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.AppWindowController;

/**
 *
 * @author Luis Diego
 */
public class PersonaDao extends BaseDao {

    public SimpleStringProperty cedula;
    public SimpleStringProperty nombres;
    public SimpleStringProperty primerApellido;
    public SimpleStringProperty segundoApellido;
    public SimpleObjectProperty fechaNacimiento;
    public SimpleObjectProperty genero;
    public SimpleStringProperty nacionalidad;
    public SimpleObjectProperty estadoCivil;
    public SimpleStringProperty profesion;

   /* guardar entidad que tiene detalle
   
   hay que setear primero el detalle
   
   persona.direccionesDao.add(detalleDireccion);
   for (DireccionDao direccionDao : persona.getDetallesDireccion()) {                
                    persona.getDetallesDireccion().add(usuarioSorteoAct);
                }
    }
   
   if (sorteo.getId() == null) {
                em.persist(sorteo);
            } else {
                for (UsuarioSorteo usuarioSorteo : sorteo.getUsuariosSorteo()) {
                    if (usuarioSorteo.getId() == null) {
                        em.persist(usuarioSorteo);
                    }
                }
                sorteo = em.merge(sorteo);
            }
    */

    public ArrayList<DireccionDao> direccionDao;
    
    private BikPersona persona;

    public PersonaDao(){
        this.cedula = new SimpleStringProperty();
        this.nombres = new SimpleStringProperty();
        this.primerApellido = new SimpleStringProperty();
        this.segundoApellido = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        this.genero = new SimpleObjectProperty();
        this.nacionalidad = new SimpleStringProperty();
        this.estadoCivil = new SimpleObjectProperty();
        this.profesion = new SimpleStringProperty();
        this.direccionDao = new ArrayList<>();
    }
    
    public PersonaDao(String cedula, String nombres, String primerApellido, String segundoApellido, Object fechaNacimiento, String genero, String nacionalidad, String estadoCivil, String profesion, ArrayList<DireccionDao> direccionDao) {
        this.cedula.set(cedula);
        this.nombres.set(nombres);
        this.primerApellido.set(primerApellido);
        this.segundoApellido.set(segundoApellido);
        this.fechaNacimiento.set(fechaNacimiento);
        this.genero.set(genero);
        this.nacionalidad.set(nacionalidad);
        this.estadoCivil.set(estadoCivil);
        this.profesion.set(profesion);
        this.direccionDao = direccionDao;
    }

    
    
    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getNombres() {
        return nombres.get();
    }

    public void setNombres(String nombres) {
        this.nombres.set(nombres);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido);
    }

    public Object getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public void setFechaNacimiento(Object fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public Object getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getNacionalidad() {
        return nacionalidad.get();
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad.set(nacionalidad);
    }

    public Object getEstadoCivil() {
        return estadoCivil.get();
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil.set(estadoCivil);
    }

    public String getProfesion() {
        return profesion.get();
    }

    public void setProfesion(String profesion) {
        this.profesion.set(profesion);
    }

    public ArrayList<DireccionDao> getDireccionDao() {
        return direccionDao;
    }

    public void setDireccionDao(ArrayList<DireccionDao> direccionDao) {
        this.direccionDao = direccionDao;
    }

    public BikPersona getPersona() {
        return persona;
    }

    public void setPersona(BikPersona persona) {
        this.persona = persona;
    }

    // Procedimiento para guardar la información de la persona.
    public BikPersona save(){
        try {
            
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = null;
            fechaNac = df.parse(getFechaNacimiento().toString());
            
            persona = new BikPersona(Integer.SIZE, getCedula(), getNombres(), getPrimerApellido(), getSegundoApellido(), fechaNac, getGenero().toString());
            persona = (BikPersona) super.save(persona);
            return persona;
            
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Error guardando la persona", "Error al guardar la persona.");
            return persona;
        }
    }    

}
