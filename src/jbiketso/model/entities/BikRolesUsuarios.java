/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "bik_roles_usuarios",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikRolesUsuarios.findAll", query = "SELECT b FROM BikRolesUsuarios b")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouUsscodigo", query = "SELECT b FROM BikRolesUsuarios b WHERE b.bikRolesUsuariosPK.rouUsscodigo = :rouUsscodigo")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouRolcodigo", query = "SELECT b FROM BikRolesUsuarios b WHERE b.bikRolesUsuariosPK.rouRolcodigo = :rouRolcodigo")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouEstado", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouEstado = :rouEstado")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouUsuarioingresa", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouUsuarioingresa = :rouUsuarioingresa")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouFechaingresa", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouFechaingresa = :rouFechaingresa")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouUsuariomodifica", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouUsuariomodifica = :rouUsuariomodifica")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouFechamodifica", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouFechamodifica = :rouFechamodifica")})
public class BikRolesUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BikRolesUsuariosPK bikRolesUsuariosPK;
    @Basic(optional = false)
    @Column(name = "rou_estado")
    private String rouEstado;
    @Column(name = "rou_usuarioingresa")
    private String rouUsuarioingresa;
    @Column(name = "rou_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rouFechaingresa;
    @Column(name = "rou_usuariomodifica")
    private String rouUsuariomodifica;
    @Column(name = "rou_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rouFechamodifica;
    @JoinColumn(name = "rou_rolcodigo", referencedColumnName = "rol_codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BkRoles bkRoles;
    @JoinColumn(name = "rou_usscodigo", referencedColumnName = "uss_codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikUsuariosSistema bikUsuariosSistema;

    public BikRolesUsuarios() {
    }

    public BikRolesUsuarios(BikRolesUsuariosPK bikRolesUsuariosPK) {
        this.bikRolesUsuariosPK = bikRolesUsuariosPK;
    }

    public BikRolesUsuarios(BikRolesUsuariosPK bikRolesUsuariosPK, String rouEstado) {
        this.bikRolesUsuariosPK = bikRolesUsuariosPK;
        this.rouEstado = rouEstado;
    }

    public BikRolesUsuarios(String rouUsscodigo, String rouRolcodigo) {
        this.bikRolesUsuariosPK = new BikRolesUsuariosPK(rouUsscodigo, rouRolcodigo);
    }

    public BikRolesUsuariosPK getBikRolesUsuariosPK() {
        return bikRolesUsuariosPK;
    }

    public void setBikRolesUsuariosPK(BikRolesUsuariosPK bikRolesUsuariosPK) {
        this.bikRolesUsuariosPK = bikRolesUsuariosPK;
    }

    public String getRouEstado() {
        return rouEstado;
    }

    public void setRouEstado(String rouEstado) {
        this.rouEstado = rouEstado;
    }

    public String getRouUsuarioingresa() {
        return rouUsuarioingresa;
    }

    public void setRouUsuarioingresa(String rouUsuarioingresa) {
        this.rouUsuarioingresa = rouUsuarioingresa;
    }

    public Date getRouFechaingresa() {
        return rouFechaingresa;
    }

    public void setRouFechaingresa(Date rouFechaingresa) {
        this.rouFechaingresa = rouFechaingresa;
    }

    public String getRouUsuariomodifica() {
        return rouUsuariomodifica;
    }

    public void setRouUsuariomodifica(String rouUsuariomodifica) {
        this.rouUsuariomodifica = rouUsuariomodifica;
    }

    public Date getRouFechamodifica() {
        return rouFechamodifica;
    }

    public void setRouFechamodifica(Date rouFechamodifica) {
        this.rouFechamodifica = rouFechamodifica;
    }

    public BkRoles getBkRoles() {
        return bkRoles;
    }

    public void setBkRoles(BkRoles bkRoles) {
        this.bkRoles = bkRoles;
    }

    public BikUsuariosSistema getBikUsuariosSistema() {
        return bikUsuariosSistema;
    }

    public void setBikUsuariosSistema(BikUsuariosSistema bikUsuariosSistema) {
        this.bikUsuariosSistema = bikUsuariosSistema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bikRolesUsuariosPK != null ? bikRolesUsuariosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikRolesUsuarios)) {
            return false;
        }
        BikRolesUsuarios other = (BikRolesUsuarios) object;
        if ((this.bikRolesUsuariosPK == null && other.bikRolesUsuariosPK != null) || (this.bikRolesUsuariosPK != null && !this.bikRolesUsuariosPK.equals(other.bikRolesUsuariosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikRolesUsuarios[ bikRolesUsuariosPK=" + bikRolesUsuariosPK + " ]";
    }
    
}
