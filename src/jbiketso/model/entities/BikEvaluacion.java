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

/**
 *
 * @author Anayansy
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_evaluacion",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikEvaluacion.findAll", query = "SELECT b FROM BikEvaluacion b")
    , @NamedQuery(name = "BikEvaluacion.findByEvaCodigo", query = "SELECT b FROM BikEvaluacion b WHERE b.evaCodigo = :evaCodigo")
    , @NamedQuery(name = "BikEvaluacion.findByEvaTipo", query = "SELECT b FROM BikEvaluacion b WHERE b.evaTipo = :evaTipo")
    , @NamedQuery(name = "BikEvaluacion.findByEvaCalificacion", query = "SELECT b FROM BikEvaluacion b WHERE b.evaCalificacion = :evaCalificacion")
    , @NamedQuery(name = "BikEvaluacion.findByEvaObservaciones", query = "SELECT b FROM BikEvaluacion b WHERE b.evaObservaciones = :evaObservaciones")
    , @NamedQuery(name = "BikEvaluacion.findByEvaUsuarioingresa", query = "SELECT b FROM BikEvaluacion b WHERE b.evaUsuarioingresa = :evaUsuarioingresa")
    , @NamedQuery(name = "BikEvaluacion.findByEvaFechaingresa", query = "SELECT b FROM BikEvaluacion b WHERE b.evaFechaingresa = :evaFechaingresa")
    , @NamedQuery(name = "BikEvaluacion.findByEvaUsuariomodifica", query = "SELECT b FROM BikEvaluacion b WHERE b.evaUsuariomodifica = :evaUsuariomodifica")
    , @NamedQuery(name = "BikEvaluacion.findByEvaFechamodifica", query = "SELECT b FROM BikEvaluacion b WHERE b.evaFechamodifica = :evaFechamodifica")})
public class BikEvaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private Integer evaCodigo;
    @Transient
    private SimpleStringProperty evaTipo;
    @Transient
    private ObjectProperty<Integer> evaCalificacion;
    @Transient
    private SimpleStringProperty evaObservaciones;
    @Column(name = "eva_usuarioingresa")
    private String evaUsuarioingresa;
    @Column(name = "eva_fechaingresa")
    @Temporal(TemporalType.DATE)
    private Date evaFechaingresa;
    @Column(name = "eva_usuariomodifica")
    private String evaUsuariomodifica;
    @Column(name = "eva_fechamodifica")
    @Temporal(TemporalType.DATE)
    private Date evaFechamodifica;
    @JoinColumn(name = "eva_acccodigo", referencedColumnName = "acc_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikAccionesPersonal evaAcccodigo;

    public BikEvaluacion() {
        this.evaTipo = new SimpleStringProperty();
        this.evaCalificacion = new SimpleObjectProperty();
        this.evaObservaciones = new SimpleStringProperty();
    }

    /*public BikEvaluacion(Integer evaCodigo) {
        this.evaCodigo = evaCodigo;
    }

    public BikEvaluacion(Integer evaCodigo, String evaTipo, int evaCalificacion) {
        this.evaCodigo = evaCodigo;
        this.evaTipo = evaTipo;
        this.evaCalificacion = evaCalificacion;
    }*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "eva_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getEvaCodigo() {
        return evaCodigo;
    }

    public void setEvaCodigo(Integer evaCodigo) {
        this.evaCodigo = evaCodigo;
    }

    @Basic(optional = false)
    @Column(name = "eva_tipo")
    @Access(AccessType.PROPERTY)
    public String getEvaTipo() {
        return evaTipo.get();
    }

    public void setEvaTipo(String evaTipo) {
        this.evaTipo.set(evaTipo);
    }
    
    public SimpleStringProperty getCenEstadoProperty() {
        return this.evaTipo;
    }

    @Basic(optional = false)
    @Column(name = "eva_calificacion")
    @Access(AccessType.FIELD)
    public int getEvaCalificacion() {
        return evaCalificacion.get();
    }

    public void setEvaCalificacion(int evaCalificacion) {
        this.evaCalificacion.set(evaCalificacion);
    }
    
    public ObjectProperty getEvaCalificacionProperty() {
        return this.evaCalificacion;
    }

    @Column(name = "eva_observaciones")
    @Access(AccessType.FIELD)
    public String getEvaObservaciones() {
        return evaObservaciones.get();
    }

    public void setEvaObservaciones(String evaObservaciones) {
        this.evaObservaciones.set(evaObservaciones);
    }
    
    public SimpleStringProperty getEvaObservacionesProperty() {
        return evaObservaciones;
    }

    public String getEvaUsuarioingresa() {
        return evaUsuarioingresa;
    }

    public void setEvaUsuarioingresa(String evaUsuarioingresa) {
        this.evaUsuarioingresa = evaUsuarioingresa;
    }

    public Date getEvaFechaingresa() {
        return evaFechaingresa;
    }

    public void setEvaFechaingresa(Date evaFechaingresa) {
        this.evaFechaingresa = evaFechaingresa;
    }

    public String getEvaUsuariomodifica() {
        return evaUsuariomodifica;
    }

    public void setEvaUsuariomodifica(String evaUsuariomodifica) {
        this.evaUsuariomodifica = evaUsuariomodifica;
    }

    public Date getEvaFechamodifica() {
        return evaFechamodifica;
    }

    public void setEvaFechamodifica(Date evaFechamodifica) {
        this.evaFechamodifica = evaFechamodifica;
    }

    public BikAccionesPersonal getEvaAcccodigo() {
        return evaAcccodigo;
    }

    public void setEvaAcccodigo(BikAccionesPersonal evaAcccodigo) {
        this.evaAcccodigo = evaAcccodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaCodigo != null ? evaCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikEvaluacion)) {
            return false;
        }
        BikEvaluacion other = (BikEvaluacion) object;
        if ((this.evaCodigo == null && other.evaCodigo != null) || (this.evaCodigo != null && !this.evaCodigo.equals(other.evaCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikEvaluacion[ evaCodigo=" + evaCodigo + " ]";
    }
    
}
