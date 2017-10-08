/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anayansy
 */
@Entity
@Table(name = "bik_requisitos_expediente",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikRequisitosExpediente.findAll", query = "SELECT b FROM BikRequisitosExpediente b")
    , @NamedQuery(name = "BikRequisitosExpediente.findByReeExpcodigo", query = "SELECT b FROM BikRequisitosExpediente b WHERE b.bikRequisitosExpedientePK.reeExpcodigo = :reeExpcodigo")
    , @NamedQuery(name = "BikRequisitosExpediente.findByReeReqcodigo", query = "SELECT b FROM BikRequisitosExpediente b WHERE b.bikRequisitosExpedientePK.reeReqcodigo = :reeReqcodigo")
    , @NamedQuery(name = "BikRequisitosExpediente.findByReeUsuarioingresa", query = "SELECT b FROM BikRequisitosExpediente b WHERE b.reeUsuarioingresa = :reeUsuarioingresa")
    , @NamedQuery(name = "BikRequisitosExpediente.findByReeFechaingresa", query = "SELECT b FROM BikRequisitosExpediente b WHERE b.reeFechaingresa = :reeFechaingresa")
    , @NamedQuery(name = "BikRequisitosExpediente.findByReeUsuariomodifica", query = "SELECT b FROM BikRequisitosExpediente b WHERE b.reeUsuariomodifica = :reeUsuariomodifica")
    , @NamedQuery(name = "BikRequisitosExpediente.findByReeFechamodifica", query = "SELECT b FROM BikRequisitosExpediente b WHERE b.reeFechamodifica = :reeFechamodifica")})
public class BikRequisitosExpediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BikRequisitosExpedientePK bikRequisitosExpedientePK;
    @Column(name = "ree_usuarioingresa")
    private String reeUsuarioingresa;
    @Column(name = "ree_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reeFechaingresa;
    @Column(name = "ree_usuariomodifica")
    private String reeUsuariomodifica;
    @Column(name = "ree_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reeFechamodifica;
    @JoinColumn(name = "ree_expcodigo", referencedColumnName = "exp_codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikExpediente bikExpediente;
    @JoinColumn(name = "ree_reqcodigo", referencedColumnName = "req_codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikRequisitos bikRequisitos;

    public BikRequisitosExpediente() {
    }

    public BikRequisitosExpediente(BikRequisitosExpedientePK bikRequisitosExpedientePK) {
        this.bikRequisitosExpedientePK = bikRequisitosExpedientePK;
    }

    public BikRequisitosExpediente(int reeExpcodigo, int reeReqcodigo) {
        this.bikRequisitosExpedientePK = new BikRequisitosExpedientePK(reeExpcodigo, reeReqcodigo);
    }

    public BikRequisitosExpedientePK getBikRequisitosExpedientePK() {
        return bikRequisitosExpedientePK;
    }

    public void setBikRequisitosExpedientePK(BikRequisitosExpedientePK bikRequisitosExpedientePK) {
        this.bikRequisitosExpedientePK = bikRequisitosExpedientePK;
    }

    public String getReeUsuarioingresa() {
        return reeUsuarioingresa;
    }

    public void setReeUsuarioingresa(String reeUsuarioingresa) {
        this.reeUsuarioingresa = reeUsuarioingresa;
    }

    public Date getReeFechaingresa() {
        return reeFechaingresa;
    }

    public void setReeFechaingresa(Date reeFechaingresa) {
        this.reeFechaingresa = reeFechaingresa;
    }

    public String getReeUsuariomodifica() {
        return reeUsuariomodifica;
    }

    public void setReeUsuariomodifica(String reeUsuariomodifica) {
        this.reeUsuariomodifica = reeUsuariomodifica;
    }

    public Date getReeFechamodifica() {
        return reeFechamodifica;
    }

    public void setReeFechamodifica(Date reeFechamodifica) {
        this.reeFechamodifica = reeFechamodifica;
    }

    public BikExpediente getBikExpediente() {
        return bikExpediente;
    }

    public void setBikExpediente(BikExpediente bikExpediente) {
        this.bikExpediente = bikExpediente;
    }

    public BikRequisitos getBikRequisitos() {
        return bikRequisitos;
    }

    public void setBikRequisitos(BikRequisitos bikRequisitos) {
        this.bikRequisitos = bikRequisitos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bikRequisitosExpedientePK != null ? bikRequisitosExpedientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikRequisitosExpediente)) {
            return false;
        }
        BikRequisitosExpediente other = (BikRequisitosExpediente) object;
        if ((this.bikRequisitosExpedientePK == null && other.bikRequisitosExpedientePK != null) || (this.bikRequisitosExpedientePK != null && !this.bikRequisitosExpedientePK.equals(other.bikRequisitosExpedientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikRequisitosExpediente[ bikRequisitosExpedientePK=" + bikRequisitosExpedientePK + " ]";
    }
    
}
