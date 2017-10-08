/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model;

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
@Table(name = "bik_modulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikModulos.findAll", query = "SELECT b FROM BikModulos b")
    , @NamedQuery(name = "BikModulos.findByModCodigo", query = "SELECT b FROM BikModulos b WHERE b.modCodigo = :modCodigo")
    , @NamedQuery(name = "BikModulos.findByModDescripcion", query = "SELECT b FROM BikModulos b WHERE b.modDescripcion = :modDescripcion")
    , @NamedQuery(name = "BikModulos.findByModEstado", query = "SELECT b FROM BikModulos b WHERE b.modEstado = :modEstado")
    , @NamedQuery(name = "BikModulos.findByModUsuarioingresa", query = "SELECT b FROM BikModulos b WHERE b.modUsuarioingresa = :modUsuarioingresa")
    , @NamedQuery(name = "BikModulos.findByModFechaingresa", query = "SELECT b FROM BikModulos b WHERE b.modFechaingresa = :modFechaingresa")
    , @NamedQuery(name = "BikModulos.findByModUsuariomodifica", query = "SELECT b FROM BikModulos b WHERE b.modUsuariomodifica = :modUsuariomodifica")
    , @NamedQuery(name = "BikModulos.findByModFechamodifica", query = "SELECT b FROM BikModulos b WHERE b.modFechamodifica = :modFechamodifica")})
public class BikModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "mod_codigo")
    private String modCodigo;
    @Basic(optional = false)
    @Column(name = "mod_descripcion")
    private String modDescripcion;
    @Basic(optional = false)
    @Column(name = "mod_estado")
    private String modEstado;
    @Column(name = "mod_usuarioingresa")
    private String modUsuarioingresa;
    @Column(name = "mod_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modFechaingresa;
    @Column(name = "mod_usuariomodifica")
    private String modUsuariomodifica;
    @Column(name = "mod_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modFechamodifica;
    @OneToMany(mappedBy = "rolModcodigo", fetch = FetchType.LAZY)
    private List<BkRoles> bkRolesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menModcodigo", fetch = FetchType.LAZY)
    private List<BikMenu> bikMenuList;

    public BikModulos() {
    }

    public BikModulos(String modCodigo) {
        this.modCodigo = modCodigo;
    }

    public BikModulos(String modCodigo, String modDescripcion, String modEstado) {
        this.modCodigo = modCodigo;
        this.modDescripcion = modDescripcion;
        this.modEstado = modEstado;
    }

    public String getModCodigo() {
        return modCodigo;
    }

    public void setModCodigo(String modCodigo) {
        this.modCodigo = modCodigo;
    }

    public String getModDescripcion() {
        return modDescripcion;
    }

    public void setModDescripcion(String modDescripcion) {
        this.modDescripcion = modDescripcion;
    }

    public String getModEstado() {
        return modEstado;
    }

    public void setModEstado(String modEstado) {
        this.modEstado = modEstado;
    }

    public String getModUsuarioingresa() {
        return modUsuarioingresa;
    }

    public void setModUsuarioingresa(String modUsuarioingresa) {
        this.modUsuarioingresa = modUsuarioingresa;
    }

    public Date getModFechaingresa() {
        return modFechaingresa;
    }

    public void setModFechaingresa(Date modFechaingresa) {
        this.modFechaingresa = modFechaingresa;
    }

    public String getModUsuariomodifica() {
        return modUsuariomodifica;
    }

    public void setModUsuariomodifica(String modUsuariomodifica) {
        this.modUsuariomodifica = modUsuariomodifica;
    }

    public Date getModFechamodifica() {
        return modFechamodifica;
    }

    public void setModFechamodifica(Date modFechamodifica) {
        this.modFechamodifica = modFechamodifica;
    }

    @XmlTransient
    public List<BkRoles> getBkRolesList() {
        return bkRolesList;
    }

    public void setBkRolesList(List<BkRoles> bkRolesList) {
        this.bkRolesList = bkRolesList;
    }

    @XmlTransient
    public List<BikMenu> getBikMenuList() {
        return bikMenuList;
    }

    public void setBikMenuList(List<BikMenu> bikMenuList) {
        this.bikMenuList = bikMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modCodigo != null ? modCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikModulos)) {
            return false;
        }
        BikModulos other = (BikModulos) object;
        if ((this.modCodigo == null && other.modCodigo != null) || (this.modCodigo != null && !this.modCodigo.equals(other.modCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikModulos[ modCodigo=" + modCodigo + " ]";
    }
    
}