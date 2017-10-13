/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anayansy
 */
@Entity
@Table(name = "bik_usuarios_sistema",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikUsuariosSistema.findAll", query = "SELECT b FROM BikUsuariosSistema b")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssCodigo", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussCodigo = :ussCodigo")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssEstado", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussEstado = :ussEstado")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssUsuarioingresa", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussUsuarioingresa = :ussUsuarioingresa")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssFechaingresa", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussFechaingresa = :ussFechaingresa")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssUsuariomodifica", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussUsuariomodifica = :ussUsuariomodifica")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssFechamodifica", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussFechamodifica = :ussFechamodifica")})
public class BikUsuariosSistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "uss_codigo")
    private String ussCodigo;
    @Basic(optional = false)
    @Column(name = "uss_estado")
    private String ussEstado;
    @Column(name = "uss_usuarioingresa")
    private String ussUsuarioingresa;
    @Column(name = "uss_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ussFechaingresa;
    @Column(name = "uss_usuariomodifica")
    private String ussUsuariomodifica;
    @Column(name = "uss_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ussFechamodifica;
    @Basic(optional = false)
    @Column(name = "uss_contrasena")
    private String ussContrasena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikUsuariosSistema", fetch = FetchType.LAZY)
    private List<BikRolesUsuarios> bikRolesUsuariosList;

    public BikUsuariosSistema() {
    }

    public BikUsuariosSistema(String ussCodigo) {
        this.ussCodigo = ussCodigo;
    }

    public BikUsuariosSistema(String ussCodigo, String ussEstado) {
        this.ussCodigo = ussCodigo;
        this.ussEstado = ussEstado;
    }

    public String getUssCodigo() {
        return ussCodigo;
    }

    public void setUssCodigo(String ussCodigo) {
        this.ussCodigo = ussCodigo;
    }

    public String getUssEstado() {
        return ussEstado;
    }

    public void setUssEstado(String ussEstado) {
        this.ussEstado = ussEstado;
    }

    public String getUssUsuarioingresa() {
        return ussUsuarioingresa;
    }

    public void setUssUsuarioingresa(String ussUsuarioingresa) {
        this.ussUsuarioingresa = ussUsuarioingresa;
    }

    public Date getUssFechaingresa() {
        return ussFechaingresa;
    }

    public void setUssFechaingresa(Date ussFechaingresa) {
        this.ussFechaingresa = ussFechaingresa;
    }

    public String getUssUsuariomodifica() {
        return ussUsuariomodifica;
    }

    public void setUssUsuariomodifica(String ussUsuariomodifica) {
        this.ussUsuariomodifica = ussUsuariomodifica;
    }

    public Date getUssFechamodifica() {
        return ussFechamodifica;
    }

    public void setUssFechamodifica(Date ussFechamodifica) {
        this.ussFechamodifica = ussFechamodifica;
    }

    public String getUssContrasena() {
        return ussContrasena;
    }

    public void setUssContrasena(String ussContrasena) {
        this.ussContrasena = ussContrasena;
    }    
    
    @XmlTransient
    public List<BikRolesUsuarios> getBikRolesUsuariosList() {
        return bikRolesUsuariosList;
    }

    public void setBikRolesUsuariosList(List<BikRolesUsuarios> bikRolesUsuariosList) {
        this.bikRolesUsuariosList = bikRolesUsuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ussCodigo != null ? ussCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikUsuariosSistema)) {
            return false;
        }
        BikUsuariosSistema other = (BikUsuariosSistema) object;
        if ((this.ussCodigo == null && other.ussCodigo != null) || (this.ussCodigo != null && !this.ussCodigo.equals(other.ussCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikUsuariosSistema[ ussCodigo=" + ussCodigo + " ]";
    }
    
}
