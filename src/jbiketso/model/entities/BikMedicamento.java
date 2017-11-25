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
@Table(name = "bik_medicamento", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikMedicamento.findAll", query = "SELECT b FROM BikMedicamento b")
    , @NamedQuery(name = "BikMedicamento.findByMedCodigo", query = "SELECT b FROM BikMedicamento b WHERE b.medCodigo = :medCodigo")
    , @NamedQuery(name = "BikMedicamento.findByExpedienteActivos", query = "select m from BikMedicamento m join m.codigoExpediente e where e.expCodigo = :codigoExpediente and m.medEstado = 'A'")})
public class BikMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private ObjectProperty<Integer> medCodigo;
    @Transient
    private ObjectProperty<GenValorCombo> medEstado;
    @Transient
    private SimpleStringProperty medMedicamento;
    @Transient
    private SimpleStringProperty medObservaciones;

    @Column(name = "med_usuarioingresa")
    private String medUsuarioingresa;
    @Column(name = "med_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date medFechaingresa;
    @Column(name = "med_usuariomodifica")
    private String medUsuariomodifica;
    @Column(name = "med_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date medFechamodifica;
    @JoinColumn(name = "med_expcodigo", referencedColumnName = "exp_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikExpediente codigoExpediente;

    public BikMedicamento() {
        this.medCodigo = new SimpleObjectProperty();
        this.medEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
        this.medObservaciones = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "med_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getMedCodigo() {
        return medCodigo.get();
    }

    public ObjectProperty getCodigoProperty() {
        if (this.medCodigo == null) {
            this.medCodigo = new SimpleObjectProperty();
        }
        return this.medCodigo;
    }

    public void setMedCodigo(Integer medCodigo) {
        if (this.medCodigo == null) {
            this.medCodigo = new SimpleObjectProperty();
        }
        this.medCodigo.set(medCodigo);
    }

    @Basic(optional = false)
    @Column(name = "med_estado")
    @Access(AccessType.PROPERTY)
    public String getMedEstado() {
        return medEstado.get().getCodigo();
    }

    public ObjectProperty getEstadoProperty() {
        if (this.medCodigo == null) {
            this.medCodigo = new SimpleObjectProperty();
        }
        return this.medEstado;
    }

    public void setMedEstado(String medEstado) {
        GenValorCombo valor = null;
        if (medEstado.equalsIgnoreCase("a")) {
            valor = new GenValorCombo(medEstado, "Activo");
        } else {
            valor = new GenValorCombo(medEstado, "Inactivo");
        }
        if (this.medCodigo == null) {
            this.medCodigo = new SimpleObjectProperty();
        }
        this.medEstado.set(valor);
    }

    @Basic(optional = false)
    @Column(name = "med_medicamento")
    @Access(AccessType.PROPERTY)
    public String getMedMedicamento() {
        return medMedicamento.get();
    }

    public SimpleStringProperty getMedicamentoProperty() {
        if (this.medMedicamento == null) {
            this.medMedicamento = new SimpleStringProperty();
        }
        return this.medMedicamento;
    }

    public void setMedMedicamento(String medMedicamento) {
        if (this.medMedicamento == null) {
            this.medMedicamento = new SimpleStringProperty();
        }
        this.medMedicamento.set(medMedicamento);
    }

    @Column(name = "med_observaciones")
    @Access(AccessType.PROPERTY)
    public String getMedObservaciones() {
        return medObservaciones.get();
    }

    public SimpleStringProperty getObservacionesProperty() {
        if (this.medObservaciones == null) {
            this.medObservaciones = new SimpleStringProperty();
        }
        return this.medObservaciones;
    }

    public void setMedObservaciones(String medObservaciones) {
        if (this.medObservaciones == null) {
            this.medObservaciones = new SimpleStringProperty();
        }
        this.medObservaciones.set(medObservaciones);
    }

    public String getMedUsuarioingresa() {
        return medUsuarioingresa;
    }

    public void setMedUsuarioingresa(String medUsuarioingresa) {
        this.medUsuarioingresa = medUsuarioingresa;
    }

    public Date getMedFechaingresa() {
        return medFechaingresa;
    }

    public void setMedFechaingresa(Date medFechaingresa) {
        this.medFechaingresa = medFechaingresa;
    }

    public String getMedUsuariomodifica() {
        return medUsuariomodifica;
    }

    public void setMedUsuariomodifica(String medUsuariomodifica) {
        this.medUsuariomodifica = medUsuariomodifica;
    }

    public Date getMedFechamodifica() {
        return medFechamodifica;
    }

    public void setMedFechamodifica(Date medFechamodifica) {
        this.medFechamodifica = medFechamodifica;
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
        hash += (medCodigo.get() != null ? medCodigo.get().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikMedicamento)) {
            return false;
        }
        BikMedicamento other = (BikMedicamento) object;
        if ((this.medCodigo.get() == null && other.medCodigo.get() != null) || (this.medCodigo.get() != null && !this.medCodigo.get().equals(other.medCodigo.get()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikMedicamento[ medCodigo=" + medCodigo.get() + " ]";
    }

}
