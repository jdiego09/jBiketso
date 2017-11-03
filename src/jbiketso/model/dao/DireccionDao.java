/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import jbiketso.model.entities.BikDireccion;

/**
 *
 * @author Luis Diego
 */
public class DireccionDao {

    public SimpleObjectProperty<Integer> codigoDireccion;
    public SimpleStringProperty detDireccion;
    public PersonaDao personaDao;

    public DireccionDao() {
        this.codigoDireccion = new SimpleObjectProperty();
        this.detDireccion = new SimpleStringProperty();
        this.personaDao = new PersonaDao();
    }

    public DireccionDao(Integer codigoDireccion, PersonaDao personaDao, String detDireccion) {

        this.codigoDireccion = new SimpleObjectProperty<>();
        this.detDireccion = new SimpleStringProperty();
        this.personaDao = new PersonaDao();

        this.codigoDireccion.set(codigoDireccion);
        this.detDireccion.set(detDireccion);
        this.personaDao = personaDao;

    }

    public DireccionDao(BikDireccion direccion) {

        this.codigoDireccion = new SimpleObjectProperty<>();
        this.detDireccion = new SimpleStringProperty();
        this.personaDao = new PersonaDao();

        this.codigoDireccion.set(direccion.getDirCodigo());
        //if (direccion.getDirPercodigo() != null) //this.personaDao.getset(direccion.getDirPercodigo().getPerCodigo());
        //{
        this.detDireccion.set(direccion.getDirDetalle());
        //}

    }

    public PersonaDao getPersonaDao() {
        return personaDao;
    }

    public void setPersonaDao(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public String getDetDireccion() {
        return detDireccion.get();
    }

    public void setDetDireccion(String detDireccion) {
        this.detDireccion.set(detDireccion);
    }

    public Integer getCodigoDireccion() {
        return codigoDireccion.get();
    }

    public void setCodigoDireccion(Integer codigoDireccion) {
        this.codigoDireccion.set(codigoDireccion);
    }

}
