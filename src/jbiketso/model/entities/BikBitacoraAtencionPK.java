/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Anayansy
 */
@Embeddable
public class BikBitacoraAtencionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "bia_codigo")
    private int biaCodigo;
    @Basic(optional = false)
    @Column(name = "bia_codusuario")
    private int biaCodusuario;

    public BikBitacoraAtencionPK() {
    }

    public BikBitacoraAtencionPK(int biaCodigo, int biaCodusuario) {
        this.biaCodigo = biaCodigo;
        this.biaCodusuario = biaCodusuario;
    }

    public int getBiaCodigo() {
        return biaCodigo;
    }

    public void setBiaCodigo(int biaCodigo) {
        this.biaCodigo = biaCodigo;
    }

    public int getBiaCodusuario() {
        return biaCodusuario;
    }

    public void setBiaCodusuario(int biaCodusuario) {
        this.biaCodusuario = biaCodusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) biaCodigo;
        hash += (int) biaCodusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikBitacoraAtencionPK)) {
            return false;
        }
        BikBitacoraAtencionPK other = (BikBitacoraAtencionPK) object;
        if (this.biaCodigo != other.biaCodigo) {
            return false;
        }
        if (this.biaCodusuario != other.biaCodusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikBitacoraAtencionPK[ biaCodigo=" + biaCodigo + ", biaCodusuario=" + biaCodusuario + " ]";
    }
    
}
