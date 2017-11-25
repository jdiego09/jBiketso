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

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_padecimiento", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikPadecimiento.findAll", query = "SELECT b FROM BikPadecimiento b")
    , @NamedQuery(name = "BikPadecimiento.findByCodigo", query = "SELECT b FROM BikPadecimiento b WHERE b.padCodigo = :codigo")
    , @NamedQuery(name = "BikPadecimiento.findByExpedienteActivos", query = "select p from BikPadecimiento p join p.codigoExpediente e where e.expCodigo = :codigoExpediente and p.padEstado = 'A'")})
public class BikPadecimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private ObjectProperty<Integer> padCodigo;
    @Transient
    private ObjectProperty<GenValorCombo> padEstado;
    @Transient
    private SimpleStringProperty padPadecimiento;
    @Transient
    private SimpleStringProperty padObservaciones;

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
    private BikExpediente codigoExpediente;

    public BikPadecimiento() {
        this.padCodigo = new SimpleObjectProperty();
        this.padEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
        this.padPadecimiento = new SimpleStringProperty();
        this.padObservaciones = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pad_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getPadCodigo() {
        return padCodigo.get();
    }

    public ObjectProperty getCodigoProperty() {
        if (this.padCodigo == null) {
            this.padCodigo = new SimpleObjectProperty();
        }
        return this.padCodigo;
    }

    public void setPadCodigo(Integer padCodigo) {
        if (this.padCodigo == null) {
            this.padCodigo = new SimpleObjectProperty();
        }
        this.padCodigo.set(padCodigo);
    }

    @Basic(optional = false)
    @Column(name = "pad_estado")
    @Access(AccessType.PROPERTY)
    public String getPadEstado() {
        return padEstado.get().getCodigo();
    }

    public ObjectProperty getEstadoProperty() {
        if (this.padEstado == null) {
            this.padEstado = new SimpleObjectProperty();
        }
        return this.padEstado;
    }

    public void setPadEstado(String padEstado) {
        GenValorCombo valor = null;
        if (padEstado.equalsIgnoreCase("a")) {
            valor = new GenValorCombo(padEstado, "Activo");
        } else {
            valor = new GenValorCombo(padEstado, "Inactivo");
        }
        this.padEstado.set(valor);
    }

    @Basic(optional = false)
    @Column(name = "pad_padecimiento")
    @Access(AccessType.PROPERTY)
    public String getPadPadecimiento() {
        return padPadecimiento.get();
    }

    public SimpleStringProperty getPadecimientoProperty() {
        if (this.padPadecimiento == null) {
            this.padPadecimiento = new SimpleStringProperty();
        }
        return this.padPadecimiento;
    }

    public void setPadPadecimiento(String padPadecimiento) {
        if (this.padPadecimiento == null) {
            this.padPadecimiento = new SimpleStringProperty();
        }
        this.padPadecimiento.set(padPadecimiento);
    }

    @Basic(optional = false)
    @Column(name = "pad_observaciones")
    @Access(AccessType.PROPERTY)
    public String getPadObservaciones() {
        return padObservaciones.get();
    }

    public SimpleStringProperty getObservacionesProperty() {
        if (this.padObservaciones == null) {
            this.padObservaciones = new SimpleStringProperty();
        }
        return this.padObservaciones;
    }

    public void setPadObservaciones(String padObservaciones) {
        if (this.padObservaciones == null) {
            this.padObservaciones = new SimpleStringProperty();
        }
        this.padObservaciones.set(padObservaciones);
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

    public BikExpediente getCodigoExpediente() {
        return codigoExpediente;
    }

    public void setCodigoExpediente(BikExpediente codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (padCodigo.get() != null ? padCodigo.get().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikPadecimiento)) {
            return false;
        }
        BikPadecimiento other = (BikPadecimiento) object;
        if ((this.padCodigo.get() == null && other.padCodigo.get() != null) || (this.padCodigo.get() != null && !this.padCodigo.get().equals(other.padCodigo.get()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikPadecimiento[ padCodigo=" + padCodigo.get() + " ]";
    }

}
