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
public class BikRolesUsuariosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "rou_usscodigo")
    private String rouUsscodigo;
    @Basic(optional = false)
    @Column(name = "rou_rolcodigo")
    private String rouRolcodigo;

    public BikRolesUsuariosPK() {
    }

    public BikRolesUsuariosPK(String rouUsscodigo, String rouRolcodigo) {
        this.rouUsscodigo = rouUsscodigo;
        this.rouRolcodigo = rouRolcodigo;
    }

    public String getRouUsscodigo() {
        return rouUsscodigo;
    }

    public void setRouUsscodigo(String rouUsscodigo) {
        this.rouUsscodigo = rouUsscodigo;
    }

    public String getRouRolcodigo() {
        return rouRolcodigo;
    }

    public void setRouRolcodigo(String rouRolcodigo) {
        this.rouRolcodigo = rouRolcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rouUsscodigo != null ? rouUsscodigo.hashCode() : 0);
        hash += (rouRolcodigo != null ? rouRolcodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikRolesUsuariosPK)) {
            return false;
        }
        BikRolesUsuariosPK other = (BikRolesUsuariosPK) object;
        if ((this.rouUsscodigo == null && other.rouUsscodigo != null) || (this.rouUsscodigo != null && !this.rouUsscodigo.equals(other.rouUsscodigo))) {
            return false;
        }
        if ((this.rouRolcodigo == null && other.rouRolcodigo != null) || (this.rouRolcodigo != null && !this.rouRolcodigo.equals(other.rouRolcodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.entities.BikRolesUsuariosPK[ rouUsscodigo=" + rouUsscodigo + ", rouRolcodigo=" + rouRolcodigo + " ]";
    }
    
}
