/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import java.util.Objects;

/**
 *
 * @author jdiego
 */
public class AccesoPantalla {
    private String modulo;
    private String pantalla;
    private String etiqueta;
    private String consulta;
    private String inserta;
    private String modifica;
    private String elimina;

    public AccesoPantalla() {
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getInserta() {
        return inserta;
    }

    public void setInserta(String inserta) {
        this.inserta = inserta;
    }

    public String getModifica() {
        return modifica;
    }

    public void setModifica(String modifica) {
        this.modifica = modifica;
    }

    public String getElimina() {
        return elimina;
    }

    public void setElimina(String elimina) {
        this.elimina = elimina;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.modulo);
        hash = 97 * hash + Objects.hashCode(this.pantalla);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccesoPantalla other = (AccesoPantalla) obj;
        if (!Objects.equals(this.modulo, other.modulo)) {
            return false;
        }
        if (!Objects.equals(this.pantalla, other.pantalla)) {
            return false;
        }
        return true;
    }
    
    
}
