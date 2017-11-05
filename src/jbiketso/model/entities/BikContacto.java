/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import jbiketso.utils.GenValorCombo;

/**
 *
 * @author jdiego
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_contacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikContacto.findAll", query = "SELECT b FROM BikContacto b")
    , @NamedQuery(name = "BikContacto.findByConCodigo", query = "SELECT b FROM BikContacto b WHERE b.conCodigo = :conCodigo")
    , @NamedQuery(name = "BikContacto.findByConTipo", query = "SELECT b FROM BikContacto b WHERE b.conTipo = :conTipo")
    , @NamedQuery(name = "BikContacto.findByConDetalle", query = "SELECT b FROM BikContacto b WHERE b.conDetalle = :conDetalle")
    , @NamedQuery(name = "BikContacto.findByConUsuarioingresa", query = "SELECT b FROM BikContacto b WHERE b.conUsuarioingresa = :conUsuarioingresa")
    , @NamedQuery(name = "BikContacto.findByConFechaingresa", query = "SELECT b FROM BikContacto b WHERE b.conFechaingresa = :conFechaingresa")
    , @NamedQuery(name = "BikContacto.findByConUsuariomodifica", query = "SELECT b FROM BikContacto b WHERE b.conUsuariomodifica = :conUsuariomodifica")
    , @NamedQuery(name = "BikContacto.findByConFechamodifica", query = "SELECT b FROM BikContacto b WHERE b.conFechamodifica = :conFechamodifica")})
public class BikContacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private Integer conCodigo;

    @Transient
    private ObjectProperty<GenValorCombo> conTipo;
    @Transient
    private SimpleStringProperty conDetalle;
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

    public BikContacto(String conTipo, String detalle) {
        String valor = null;
        this.conTipo = new SimpleObjectProperty();
        this.conDetalle = new SimpleStringProperty();
        switch (conTipo.toLowerCase()) {
            case "t":
                valor = "Teléfono";
                break;
            case "c":
                valor = "Correo";
                break;
            case "f":
                valor = "Fax";
                break;
            default:
                valor = "Otro";
                break;
        }
        this.conTipo.set(new GenValorCombo(conTipo, valor));
        this.conDetalle.set(detalle);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "con_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getConCodigo() {
        return conCodigo;
    }

    public void setConCodigo(Integer conCodigo) {
        this.conCodigo = conCodigo;
    }

    @Access(AccessType.PROPERTY)
    @Basic(optional = false)
    @Column(name = "con_tipo")
    public String getConTipo() {
        return conTipo.get().getCodigo();
    }

    public void setConTipo(String conTipo) {
        if (this.conTipo == null) {
            this.conTipo = new SimpleObjectProperty();
        }
        String valor = null;
        switch (conTipo.toLowerCase()) {
            case "t":
                valor = "Teléfono";
                break;
            case "c":
                valor = "Correo";
                break;
            case "f":
                valor = "Fax";
                break;
            default:
                valor = "Otro";
                break;
        }
        this.conTipo.set(new GenValorCombo(conTipo, valor));
    }

    public ObjectProperty getTipoContactoProperty() {
        if (this.conTipo == null) {
            this.conTipo = new SimpleObjectProperty();
        }
        return this.conTipo;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "con_detalle")
    public String getConDetalle() {
        return conDetalle.get();
    }

    public void setConDetalle(String conDetalle) {
        if (this.conDetalle == null) {
            this.conDetalle = new SimpleStringProperty();
        }
        this.conDetalle.set(conDetalle);
    }

    public SimpleStringProperty getDetalleContactoProperty() {
        if (this.conDetalle == null) {
            this.conDetalle = new SimpleStringProperty();
        }
        return this.conDetalle;
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
        return "jbiketso.model.entities.BikContacto[ conCodigo=" + conCodigo + " ]";
    }

}
