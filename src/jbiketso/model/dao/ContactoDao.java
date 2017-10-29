/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Luis Diego
 */
public class ContactoDao {
    
    public SimpleObjectProperty<Integer> codigoContacto;
    public SimpleObjectProperty<Integer> codigoPersona;
    public SimpleStringProperty tipo;
    public SimpleStringProperty detContacto;

    public ContactoDao() {
        this.codigoContacto = new SimpleObjectProperty();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.tipo = new SimpleStringProperty();
        this.detContacto = new SimpleStringProperty();        
    }
    
    public ContactoDao(Integer codigoContacto, Integer codigoPersona, String tipo, String detContacto) {
        
        this.codigoContacto = new SimpleObjectProperty();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.tipo = new SimpleStringProperty();
        this.detContacto = new SimpleStringProperty();        
        
        this.codigoContacto.set(codigoContacto);
        this.codigoPersona.set(codigoPersona);
        this.tipo.set(tipo);
        this.detContacto.set(detContacto);
        
    }

    public Integer getCodigoPersona() {
        return codigoPersona.get();
    }

    public void setCodigoPersona(Integer codigoPersona) {
        this.codigoPersona.set(codigoPersona);
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public String getDetContacto() {
        return detContacto.get();
    }

    public void setDetContacto(String detContacto) {
        this.detContacto.set(detContacto);
    }

    public Integer getCodigoContacto() {
        return codigoContacto.get();
    }

    public void setCodigoContacto(Integer codigoContacto) {
        this.codigoContacto.set(codigoContacto);
    }
   
    
}
