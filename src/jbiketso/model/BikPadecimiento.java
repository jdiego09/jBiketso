/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model;

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
@Table(name = "bik_padecimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikPadecimiento.findAll", query = "SELECT b FROM BikPadecimiento b")
    , @NamedQuery(name = "BikPadecimiento.findByPadCodigo", query = "SELECT b FROM BikPadecimiento b WHERE b.padCodigo = :padCodigo")
    , @NamedQuery(name = "BikPadecimiento.findByPadEstado", query = "SELECT b FROM BikPadecimiento b WHERE b.padEstado = :padEstado")
    , @NamedQuery(name = "BikPadecimiento.findByPadPadecimiento", query = "SELECT b FROM BikPadecimiento b WHERE b.padPadecimiento = :padPadecimiento")
    , @NamedQuery(name = "BikPadecimiento.findByPadObservaciones", query = "SELECT b FROM BikPadecimiento b WHERE b.padObservaciones = :padObservaciones")
    , @NamedQuery(name = "BikPadecimiento.findByPadUsuarioingresa", query = "SELECT b FROM BikPadecimiento b WHERE b.padUsuarioingresa = :padUsuarioingresa")
    , @NamedQuery(name = "BikPadecimiento.findByPadFechaingresa", query = "SELECT b FROM BikPadecimiento b WHERE b.padFechaingresa = :padFechaingresa")
    , @NamedQuery(name = "BikPadecimiento.findByPadUsuariomodifica", query = "SELECT b FROM BikPadecimiento b WHERE b.padUsuariomodifica = :padUsuariomodifica")
    , @NamedQuery(name = "BikPadecimiento.findByPadFechamodifica", query = "SELECT b FROM BikPadecimiento b WHERE b.padFechamodifica = :padFechamodifica")})
public class BikPadecimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pad_codigo")
    private Integer padCodigo;
    @Basic(optional = false)
    @Column(name = "pad_estado")
    private String padEstado;
    @Basic(optional = false)
    @Column(name = "pad_padecimiento")
    private String padPadecimiento;
    @Basic(optional = false)
    @Column(name = "pad_observaciones")
    private String padObservaciones;
    @Column(name = "pad_usuarioingresa")
    private String padUsuarioingresa;
    @Column(name = "pad_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date padFechaingresa;
    @Column(name = "pad_usuariomodifica")
    private String padUsuariomodifica;
    @Column(name = "pad_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date padFechamodifica;
    @JoinColumn(name = "pad_expcodigo", referencedColumnName = "exp_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikExpediente padExpcodigo;

    public BikPadecimiento() {
    }

    public BikPadecimiento(Integer padCodigo) {
        this.padCodigo = padCodigo;
    }

    public BikPadecimiento(Integer padCodigo, String padEstado, String padPadecimiento, String padObservaciones) {
        this.padCodigo = padCodigo;
        this.padEstado = padEstado;
        this.padPadecimiento = padPadecimiento;
        this.padObservaciones = padObservaciones;
    }

    public Integer getPadCodigo() {
        return padCodigo;
    }

    public void setPadCodigo(Integer padCodigo) {
        this.padCodigo = padCodigo;
    }

    public String getPadEstado() {
        return padEstado;
    }

    public void setPadEstado(String padEstado) {
        this.padEstado = padEstado;
    }

    public String getPadPadecimiento() {
        return padPadecimiento;
    }

    public void setPadPadecimiento(String padPadecimiento) {
        this.padPadecimiento = padPadecimiento;
    }

    public String getPadObservaciones() {
        return padObservaciones;
    }

    public void setPadObservaciones(String padObservaciones) {
        this.padObservaciones = padObservaciones;
    }

    public String getPadUsuarioingresa() {
        return padUsuarioingresa;
    }

    public void setPadUsuarioingresa(String padUsuarioingresa) {
        this.padUsuarioingresa = padUsuarioingresa;
    }

    public Date getPadFechaingresa() {
        return padFechaingresa;
    }

    public void setPadFechaingresa(Date padFechaingresa) {
        this.padFechaingresa = padFechaingresa;
    }

    public String getPadUsuariomodifica() {
        return padUsuariomodifica;
    }

    public void setPadUsuariomodifica(String padUsuariomodifica) {
        this.padUsuariomodifica = padUsuariomodifica;
    }

    public Date getPadFechamodifica() {
        return padFechamodifica;
    }

    public void setPadFechamodifica(Date padFechamodifica) {
        this.padFechamodifica = padFechamodifica;
    }

    public BikExpediente getPadExpcodigo() {
        return padExpcodigo;
    }

    public void setPadExpcodigo(BikExpediente padExpcodigo) {
        this.padExpcodigo = padExpcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (padCodigo != null ? padCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikPadecimiento)) {
            return false;
        }
        BikPadecimiento other = (BikPadecimiento) object;
        if ((this.padCodigo == null && other.padCodigo != null) || (this.padCodigo != null && !this.padCodigo.equals(other.padCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikPadecimiento[ padCodigo=" + padCodigo + " ]";
    }
    
}
