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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "bik_contacto",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikContacto.findAll", query = "SELECT b FROM BikContacto b")
    , @NamedQuery(name = "BikContacto.findByConCodigo", query = "SELECT b FROM BikContacto b WHERE b.conCodigo = :conCodigo")
    , @NamedQuery(name = "BikContacto.findByConTipo", query = "SELECT b FROM BikContacto b WHERE b.conTipo = :conTipo")
    , @NamedQuery(name = "BikContacto.findByConDireccionExacta", query = "SELECT b FROM BikContacto b WHERE b.conDireccionExacta = :conDireccionExacta")
    , @NamedQuery(name = "BikContacto.findByConDetalle", query = "SELECT b FROM BikContacto b WHERE b.conDetalle = :conDetalle")
    , @NamedQuery(name = "BikContacto.findByConUsuarioingresa", query = "SELECT b FROM BikContacto b WHERE b.conUsuarioingresa = :conUsuarioingresa")
    , @NamedQuery(name = "BikContacto.findByConFechaingresa", query = "SELECT b FROM BikContacto b WHERE b.conFechaingresa = :conFechaingresa")
    , @NamedQuery(name = "BikContacto.findByConUsuariomodifica", query = "SELECT b FROM BikContacto b WHERE b.conUsuariomodifica = :conUsuariomodifica")
    , @NamedQuery(name = "BikContacto.findByConFechamodifica", query = "SELECT b FROM BikContacto b WHERE b.conFechamodifica = :conFechamodifica")})
public class BikContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "con_codigo")
    private Integer conCodigo;
    @Basic(optional = false)
    @Column(name = "con_tipo")
    private String conTipo;
    @Basic(optional = false)
    @Column(name = "con_direccion exacta")
    private String conDireccionExacta;
    @Column(name = "con_detalle")
    private String conDetalle;
    @Column(name = "con_usuarioingresa")
    private String conUsuarioingresa;
    @Column(name = "con_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conFechaingresa;
    @Column(name = "con_usuariomodifica")
    private String conUsuariomodifica;
    @Column(name = "con_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conFechamodifica;
    @JoinColumn(name = "con_percodigo", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona conPercodigo;

    public BikContacto() {
    }

    public BikContacto(Integer conCodigo) {
        this.conCodigo = conCodigo;
    }

    public BikContacto(Integer conCodigo, String conTipo, String conDireccionExacta) {
        this.conCodigo = conCodigo;
        this.conTipo = conTipo;
        this.conDireccionExacta = conDireccionExacta;
    }

    public Integer getConCodigo() {
        return conCodigo;
    }

    public void setConCodigo(Integer conCodigo) {
        this.conCodigo = conCodigo;
    }

    public String getConTipo() {
        return conTipo;
    }

    public void setConTipo(String conTipo) {
        this.conTipo = conTipo;
    }

    public String getConDireccionExacta() {
        return conDireccionExacta;
    }

    public void setConDireccionExacta(String conDireccionExacta) {
        this.conDireccionExacta = conDireccionExacta;
    }

    public String getConDetalle() {
        return conDetalle;
    }

    public void setConDetalle(String conDetalle) {
        this.conDetalle = conDetalle;
    }

    public String getConUsuarioingresa() {
        return conUsuarioingresa;
    }

    public void setConUsuarioingresa(String conUsuarioingresa) {
        this.conUsuarioingresa = conUsuarioingresa;
    }

    public Date getConFechaingresa() {
        return conFechaingresa;
    }

    public void setConFechaingresa(Date conFechaingresa) {
        this.conFechaingresa = conFechaingresa;
    }

    public String getConUsuariomodifica() {
        return conUsuariomodifica;
    }

    public void setConUsuariomodifica(String conUsuariomodifica) {
        this.conUsuariomodifica = conUsuariomodifica;
    }

    public Date getConFechamodifica() {
        return conFechamodifica;
    }

    public void setConFechamodifica(Date conFechamodifica) {
        this.conFechamodifica = conFechamodifica;
    }

    public BikPersona getConPercodigo() {
        return conPercodigo;
    }

    public void setConPercodigo(BikPersona conPercodigo) {
        this.conPercodigo = conPercodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conCodigo != null ? conCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikContacto)) {
            return false;
        }
        BikContacto other = (BikContacto) object;
        if ((this.conCodigo == null && other.conCodigo != null) || (this.conCodigo != null && !this.conCodigo.equals(other.conCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikContacto[ conCodigo=" + conCodigo + " ]";
    }
    
}
