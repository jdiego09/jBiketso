/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import jbiketso.model.entities.BikDireccion;

/**
 *
 * @author Luis Diego
 */
public class DireccionDao {
    
    public SimpleObjectProperty<Integer> codigoDireccion;
    public SimpleObjectProperty<Integer> codigoPersona;
    public SimpleStringProperty detDireccion;

    public DireccionDao() {
        this.codigoDireccion = new SimpleObjectProperty();
        this.codigoPersona = new SimpleObjectProperty();
        this.detDireccion = new SimpleStringProperty();
    }    
    
    public DireccionDao(Integer codigoDireccion, Integer codigoPersona, String detDireccion) {

        this.codigoDireccion = new SimpleObjectProperty<>();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.detDireccion = new SimpleStringProperty();
        
        this.codigoDireccion.set(codigoDireccion);
        this.codigoPersona.set(codigoPersona);
        this.detDireccion.set(detDireccion);

    }
    
   public DireccionDao(BikDireccion direccion) {

        this.codigoDireccion = new SimpleObjectProperty<>();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.detDireccion = new SimpleStringProperty();
        
        this.codigoDireccion.set(direccion.getDirCodigo());
        this.codigoPersona.set(direccion.getDirPercodigo().getPerCodigo());
        this.detDireccion.set(direccion.getDirDetalle());

    }
    
    public ObjectProperty<Integer> getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(Integer codigoPersona) {
        this.codigoPersona.set(codigoPersona);
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
