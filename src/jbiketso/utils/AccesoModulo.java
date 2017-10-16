/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

/**
 *
 * @author jdiego
 */
public class AccesoModulo {
    
    private String modulo;
    private String descripcion;

    public AccesoModulo() {
    }

    public AccesoModulo(String modulo, String descripcion) {
        this.modulo = modulo;
        this.descripcion = descripcion;
    }
    
    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
