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
@Table(name = "bik_medicamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikMedicamento.findAll", query = "SELECT b FROM BikMedicamento b")
    , @NamedQuery(name = "BikMedicamento.findByMedCodigo", query = "SELECT b FROM BikMedicamento b WHERE b.medCodigo = :medCodigo")
    , @NamedQuery(name = "BikMedicamento.findByMedEstado", query = "SELECT b FROM BikMedicamento b WHERE b.medEstado = :medEstado")
    , @NamedQuery(name = "BikMedicamento.findByMedMedicamento", query = "SELECT b FROM BikMedicamento b WHERE b.medMedicamento = :medMedicamento")
    , @NamedQuery(name = "BikMedicamento.findByMedDosis", query = "SELECT b FROM BikMedicamento b WHERE b.medDosis = :medDosis")
    , @NamedQuery(name = "BikMedicamento.findByMedHorario", query = "SELECT b FROM BikMedicamento b WHERE b.medHorario = :medHorario")
    , @NamedQuery(name = "BikMedicamento.findByMedObservaciones", query = "SELECT b FROM BikMedicamento b WHERE b.medObservaciones = :medObservaciones")
    , @NamedQuery(name = "BikMedicamento.findByMedUsuarioingresa", query = "SELECT b FROM BikMedicamento b WHERE b.medUsuarioingresa = :medUsuarioingresa")
    , @NamedQuery(name = "BikMedicamento.findByMedFechaingresa", query = "SELECT b FROM BikMedicamento b WHERE b.medFechaingresa = :medFechaingresa")
    , @NamedQuery(name = "BikMedicamento.findByMedUsuariomodifica", query = "SELECT b FROM BikMedicamento b WHERE b.medUsuariomodifica = :medUsuariomodifica")
    , @NamedQuery(name = "BikMedicamento.findByMedFechamodifica", query = "SELECT b FROM BikMedicamento b WHERE b.medFechamodifica = :medFechamodifica")})
public class BikMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "med_codigo")
    private Integer medCodigo;
    @Basic(optional = false)
    @Column(name = "med_estado")
    private String medEstado;
    @Basic(optional = false)
    @Column(name = "med_medicamento")
    private String medMedicamento;
    @Basic(optional = false)
    @Column(name = "med_dosis")
    private String medDosis;
    @Basic(optional = false)
    @Column(name = "med_horario")
    private String medHorario;
    @Column(name = "med_observaciones")
    private String medObservaciones;
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
    private BikExpediente medExpcodigo;

    public BikMedicamento() {
    }

    public BikMedicamento(Integer medCodigo) {
        this.medCodigo = medCodigo;
    }

    public BikMedicamento(Integer medCodigo, String medEstado, String medMedicamento, String medDosis, String medHorario) {
        this.medCodigo = medCodigo;
        this.medEstado = medEstado;
        this.medMedicamento = medMedicamento;
        this.medDosis = medDosis;
        this.medHorario = medHorario;
    }

    public Integer getMedCodigo() {
        return medCodigo;
    }

    public void setMedCodigo(Integer medCodigo) {
        this.medCodigo = medCodigo;
    }

    public String getMedEstado() {
        return medEstado;
    }

    public void setMedEstado(String medEstado) {
        this.medEstado = medEstado;
    }

    public String getMedMedicamento() {
        return medMedicamento;
    }

    public void setMedMedicamento(String medMedicamento) {
        this.medMedicamento = medMedicamento;
    }

    public String getMedDosis() {
        return medDosis;
    }

    public void setMedDosis(String medDosis) {
        this.medDosis = medDosis;
    }

    public String getMedHorario() {
        return medHorario;
    }

    public void setMedHorario(String medHorario) {
        this.medHorario = medHorario;
    }

    public String getMedObservaciones() {
        return medObservaciones;
    }

    public void setMedObservaciones(String medObservaciones) {
        this.medObservaciones = medObservaciones;
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

    public BikExpediente getMedExpcodigo() {
        return medExpcodigo;
    }

    public void setMedExpcodigo(BikExpediente medExpcodigo) {
        this.medExpcodigo = medExpcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medCodigo != null ? medCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikMedicamento)) {
            return false;
        }
        BikMedicamento other = (BikMedicamento) object;
        if ((this.medCodigo == null && other.medCodigo != null) || (this.medCodigo != null && !this.medCodigo.equals(other.medCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikMedicamento[ medCodigo=" + medCodigo + " ]";
    }
    
}
