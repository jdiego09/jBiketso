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
public class BikRequisitosExpedientePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ree_expcodigo")
    private int reeExpcodigo;
    @Basic(optional = false)
    @Column(name = "ree_reqcodigo")
    private int reeReqcodigo;

    public BikRequisitosExpedientePK() {
    }

    public BikRequisitosExpedientePK(int reeExpcodigo, int reeReqcodigo) {
        this.reeExpcodigo = reeExpcodigo;
        this.reeReqcodigo = reeReqcodigo;
    }

    public int getReeExpcodigo() {
        return reeExpcodigo;
    }

    public void setReeExpcodigo(int reeExpcodigo) {
        this.reeExpcodigo = reeExpcodigo;
    }

    public int getReeReqcodigo() {
        return reeReqcodigo;
    }

    public void setReeReqcodigo(int reeReqcodigo) {
        this.reeReqcodigo = reeReqcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) reeExpcodigo;
        hash += (int) reeReqcodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikRequisitosExpedientePK)) {
            return false;
        }
        BikRequisitosExpedientePK other = (BikRequisitosExpedientePK) object;
        if (this.reeExpcodigo != other.reeExpcodigo) {
            return false;
        }
        if (this.reeReqcodigo != other.reeReqcodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikRequisitosExpedientePK[ reeExpcodigo=" + reeExpcodigo + ", reeReqcodigo=" + reeReqcodigo + " ]";
    }
    
}
