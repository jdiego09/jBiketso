/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author Anayansy
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_direccion", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikDireccion.findAll", query = "SELECT b FROM BikDireccion b")
    , @NamedQuery(name = "BikDireccion.findByDirCodigo", query = "SELECT b FROM BikDireccion b WHERE b.dirCodigo = :dirCodigo")
    , @NamedQuery(name = "BikDireccion.findByDirDetalle", query = "SELECT b FROM BikDireccion b WHERE b.dirDetalle = :dirDetalle")
    , @NamedQuery(name = "BikDireccion.findByDirUsuarioingresa", query = "SELECT b FROM BikDireccion b WHERE b.dirUsuarioingresa = :dirUsuarioingresa")
    , @NamedQuery(name = "BikDireccion.findByDirFechaingresa", query = "SELECT b FROM BikDireccion b WHERE b.dirFechaingresa = :dirFechaingresa")
    , @NamedQuery(name = "BikDireccion.findByDirUsuariomodifica", query = "SELECT b FROM BikDireccion b WHERE b.dirUsuariomodifica = :dirUsuariomodifica")
    , @NamedQuery(name = "BikDireccion.findByDirFechamodifica", query = "SELECT b FROM BikDireccion b WHERE b.dirFechamodifica = :dirFechamodifica")})
public class BikDireccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private Integer dirCodigo;

    @Transient
    private SimpleStringProperty dirDetalle;
    @Column(name = "dir_usuarioingresa")
    private String dirUsuarioingresa;
    @Column(name = "dir_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dirFechaingresa;
    @Column(name = "dir_usuariomodifica")
    private String dirUsuariomodifica;
    @Column(name = "dir_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dirFechamodifica;
    @JoinColumn(name = "dir_percodigo", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona dirPercodigo;

    public BikDireccion() {
    }

    public BikDireccion(String dirDetalle) {
        if (this.dirDetalle == null) {
            this.dirDetalle = new SimpleStringProperty();
        }
        this.dirDetalle.set(dirDetalle);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dir_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getDirCodigo() {
        return dirCodigo;
    }

    public void setDirCodigo(Integer dirCodigo) {
        this.dirCodigo = dirCodigo;
    }

    @Basic(optional = false)
    @Column(name = "dir_detalle")
    @Access(AccessType.PROPERTY)
    public String getDirDetalle() {
        return dirDetalle.get();
    }

    public void setDirDetalle(String dirDetalle) {
        if (this.dirDetalle == null) {
            this.dirDetalle = new SimpleStringProperty();
        }
        this.dirDetalle.set(dirDetalle);
    }

    public SimpleStringProperty getDetalleDireccionProperty(){
        if (this.dirDetalle == null) {
            this.dirDetalle = new SimpleStringProperty();
        }
        return this.dirDetalle;
    }
    
    public String getDirUsuarioingresa() {
        return dirUsuarioingresa;
    }

    public void setDirUsuarioingresa(String dirUsuarioingresa) {
        this.dirUsuarioingresa = dirUsuarioingresa;
    }

    public Date getDirFechaingresa() {
        return dirFechaingresa;
    }

    public void setDirFechaingresa(Date dirFechaingresa) {
        this.dirFechaingresa = dirFechaingresa;
    }

    public String getDirUsuariomodifica() {
        return dirUsuariomodifica;
    }

    public void setDirUsuariomodifica(String dirUsuariomodifica) {
        this.dirUsuariomodifica = dirUsuariomodifica;
    }

    public Date getDirFechamodifica() {
        return dirFechamodifica;
    }

    public void setDirFechamodifica(Date dirFechamodifica) {
        this.dirFechamodifica = dirFechamodifica;
    }

    public BikPersona getDirPercodigo() {
        return dirPercodigo;
    }

    public void setDirPercodigo(BikPersona dirPercodigo) {
        this.dirPercodigo = dirPercodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dirCodigo != null ? dirCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikDireccion)) {
            return false;
        }
        BikDireccion other = (BikDireccion) object;
        if ((this.dirCodigo == null && other.dirCodigo != null) || (this.dirCodigo != null && !this.dirCodigo.equals(other.dirCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikDireccion[ dirCodigo=" + dirCodigo + " ]";
    }

}
