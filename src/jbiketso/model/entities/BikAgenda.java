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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "bik_agenda",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikAgenda.findAll", query = "SELECT b FROM BikAgenda b")
    , @NamedQuery(name = "BikAgenda.findByAgeCodigo", query = "SELECT b FROM BikAgenda b WHERE b.ageCodigo = :ageCodigo")
    , @NamedQuery(name = "BikAgenda.findByAgeDescripcion", query = "SELECT b FROM BikAgenda b WHERE b.ageDescripcion = :ageDescripcion")
    , @NamedQuery(name = "BikAgenda.findByAgeTipo", query = "SELECT b FROM BikAgenda b WHERE b.ageTipo = :ageTipo")
    , @NamedQuery(name = "BikAgenda.findByAgeEstado", query = "SELECT b FROM BikAgenda b WHERE b.ageEstado = :ageEstado")
    , @NamedQuery(name = "BikAgenda.findByAgeUsuarioingresa", query = "SELECT b FROM BikAgenda b WHERE b.ageUsuarioingresa = :ageUsuarioingresa")
    , @NamedQuery(name = "BikAgenda.findByAgeFechaingresa", query = "SELECT b FROM BikAgenda b WHERE b.ageFechaingresa = :ageFechaingresa")
    , @NamedQuery(name = "BikAgenda.findByAgeUsuariomodifica", query = "SELECT b FROM BikAgenda b WHERE b.ageUsuariomodifica = :ageUsuariomodifica")
    , @NamedQuery(name = "BikAgenda.findByAgeFechamodifica", query = "SELECT b FROM BikAgenda b WHERE b.ageFechamodifica = :ageFechamodifica")})
public class BikAgenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "age_codigo")
    private Integer ageCodigo;
    @Basic(optional = false)
    @Column(name = "age_descripcion")
    private String ageDescripcion;
    @Basic(optional = false)
    @Column(name = "age_tipo")
    private String ageTipo;
    @Basic(optional = false)
    @Column(name = "age_estado")
    private String ageEstado;
    @Column(name = "age_usuarioingresa")
    private String ageUsuarioingresa;
    @Column(name = "age_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ageFechaingresa;
    @Column(name = "age_usuariomodifica")
    private String ageUsuariomodifica;
    @Column(name = "age_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ageFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaAgecodigo", fetch = FetchType.LAZY)
    private List<BikDetalleAgenda> bikDetalleAgendaList;
    @JoinColumn(name = "age_sedcodigo", referencedColumnName = "sed_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikSede ageSedcodigo;

    public BikAgenda() {
    }

    public BikAgenda(Integer ageCodigo) {
        this.ageCodigo = ageCodigo;
    }

    public BikAgenda(Integer ageCodigo, String ageDescripcion, String ageTipo, String ageEstado) {
        this.ageCodigo = ageCodigo;
        this.ageDescripcion = ageDescripcion;
        this.ageTipo = ageTipo;
        this.ageEstado = ageEstado;
    }

    public Integer getAgeCodigo() {
        return ageCodigo;
    }

    public void setAgeCodigo(Integer ageCodigo) {
        this.ageCodigo = ageCodigo;
    }

    public String getAgeDescripcion() {
        return ageDescripcion;
    }

    public void setAgeDescripcion(String ageDescripcion) {
        this.ageDescripcion = ageDescripcion;
    }

    public String getAgeTipo() {
        return ageTipo;
    }

    public void setAgeTipo(String ageTipo) {
        this.ageTipo = ageTipo;
    }

    public String getAgeEstado() {
        return ageEstado;
    }

    public void setAgeEstado(String ageEstado) {
        this.ageEstado = ageEstado;
    }

    public String getAgeUsuarioingresa() {
        return ageUsuarioingresa;
    }

    public void setAgeUsuarioingresa(String ageUsuarioingresa) {
        this.ageUsuarioingresa = ageUsuarioingresa;
    }

    public Date getAgeFechaingresa() {
        return ageFechaingresa;
    }

    public void setAgeFechaingresa(Date ageFechaingresa) {
        this.ageFechaingresa = ageFechaingresa;
    }

    public String getAgeUsuariomodifica() {
        return ageUsuariomodifica;
    }

    public void setAgeUsuariomodifica(String ageUsuariomodifica) {
        this.ageUsuariomodifica = ageUsuariomodifica;
    }

    public Date getAgeFechamodifica() {
        return ageFechamodifica;
    }

    public void setAgeFechamodifica(Date ageFechamodifica) {
        this.ageFechamodifica = ageFechamodifica;
    }

    @XmlTransient
    public List<BikDetalleAgenda> getBikDetalleAgendaList() {
        return bikDetalleAgendaList;
    }

    public void setBikDetalleAgendaList(List<BikDetalleAgenda> bikDetalleAgendaList) {
        this.bikDetalleAgendaList = bikDetalleAgendaList;
    }

    public BikSede getAgeSedcodigo() {
        return ageSedcodigo;
    }

    public void setAgeSedcodigo(BikSede ageSedcodigo) {
        this.ageSedcodigo = ageSedcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ageCodigo != null ? ageCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikAgenda)) {
            return false;
        }
        BikAgenda other = (BikAgenda) object;
        if ((this.ageCodigo == null && other.ageCodigo != null) || (this.ageCodigo != null && !this.ageCodigo.equals(other.ageCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikAgenda[ ageCodigo=" + ageCodigo + " ]";
    }
    
}
