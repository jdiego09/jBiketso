/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.utils.GenValorCombo;

/**
 *
 * @author Luis Diego
 */
public class ContactoDao {
    
    public SimpleObjectProperty<Integer> codigoContacto;
    public SimpleObjectProperty<Integer> codigoPersona;
    public ObjectProperty<GenValorCombo> tipo;
    public SimpleStringProperty detContacto;

    public ContactoDao() {
        this.codigoContacto = new SimpleObjectProperty();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.tipo = new SimpleObjectProperty();
        this.detContacto = new SimpleStringProperty();        
    }
    
    public ContactoDao(Integer codigoContacto, Integer codigoPersona, String tipo, String detContacto) {
        
        this.codigoContacto = new SimpleObjectProperty();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.tipo = new SimpleObjectProperty();
        this.detContacto = new SimpleStringProperty();        
        
        this.codigoContacto.set(codigoContacto);
        this.codigoPersona.set(codigoPersona);
        if (tipo.equalsIgnoreCase("t")) {
            this.tipo.set(new GenValorCombo("T", "Teléfono"));
        } else if (tipo.equalsIgnoreCase("c")){
            this.tipo.set(new GenValorCombo("C", "Correo"));
        } else if (tipo.equalsIgnoreCase("f")) {
            this.tipo.set(new GenValorCombo("F", "Fax"));
        }
        this.detContacto.set(detContacto);
        
    }

      public ContactoDao(BikContacto contacto) {

        this.codigoContacto = new SimpleObjectProperty();
        this.codigoPersona = new SimpleObjectProperty<>();
        this.tipo = new SimpleObjectProperty();
        this.detContacto = new SimpleStringProperty();        
        
        this.codigoContacto.set(contacto.getConCodigo());
        this.codigoPersona.set(contacto.getConPercodigo().getPerCodigo());
        if (contacto.getConTipo().equalsIgnoreCase("t")) {
            this.tipo.set(new GenValorCombo("T", "Teléfono"));
        } else if (contacto.getConTipo().equalsIgnoreCase("c")){
            this.tipo.set(new GenValorCombo("C", "Correo"));
        } else if (contacto.getConTipo().equalsIgnoreCase("f")) {
            this.tipo.set(new GenValorCombo("F", "Fax"));
        }
        this.detContacto.set(contacto.getConDetalle());

    }

    public Integer getCodigoPersona() {
        return codigoPersona.get();
    }

    public void setCodigoPersona(Integer codigoPersona) {
        this.codigoPersona.set(codigoPersona);
    }

    public GenValorCombo getTipo() {
        return tipo.get();
    }

    public void setTipo(GenValorCombo tipo) {
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
