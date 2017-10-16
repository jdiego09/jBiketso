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
@Table(name = "bik_sede",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikSede.findAll", query = "SELECT b FROM BikSede b")
    , @NamedQuery(name = "BikSede.findBySedCodigo", query = "SELECT b FROM BikSede b WHERE b.sedCodigo = :sedCodigo")
    , @NamedQuery(name = "BikSede.findBySedNombre", query = "SELECT b FROM BikSede b WHERE b.sedNombre = :sedNombre")
    , @NamedQuery(name = "BikSede.findBySedDescripcion", query = "SELECT b FROM BikSede b WHERE b.sedDescripcion = :sedDescripcion")
    , @NamedQuery(name = "BikSede.findBySedTelefonos", query = "SELECT b FROM BikSede b WHERE b.sedTelefonos = :sedTelefonos")
    , @NamedQuery(name = "BikSede.findBySedFax", query = "SELECT b FROM BikSede b WHERE b.sedFax = :sedFax")
    , @NamedQuery(name = "BikSede.findBySedEmail", query = "SELECT b FROM BikSede b WHERE b.sedEmail = :sedEmail")
    , @NamedQuery(name = "BikSede.findBySedUsuarioingresa", query = "SELECT b FROM BikSede b WHERE b.sedUsuarioingresa = :sedUsuarioingresa")
    , @NamedQuery(name = "BikSede.findBySedFechaingresa", query = "SELECT b FROM BikSede b WHERE b.sedFechaingresa = :sedFechaingresa")
    , @NamedQuery(name = "BikSede.findBySedUsuariomodifica", query = "SELECT b FROM BikSede b WHERE b.sedUsuariomodifica = :sedUsuariomodifica")
    , @NamedQuery(name = "BikSede.findBySedFechamodifica", query = "SELECT b FROM BikSede b WHERE b.sedFechamodifica = :sedFechamodifica")})
public class BikSede implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sed_codigo")
    private Integer sedCodigo;
    @Basic(optional = false)
    @Column(name = "sed_nombre")
    private String sedNombre;
    @Basic(optional = false)
    @Column(name = "sed_descripcion")
    private String sedDescripcion;
    @Basic(optional = false)
    @Column(name = "sed_telefonos")
    private String sedTelefonos;
    @Column(name = "sed_fax")
    private String sedFax;
    @Column(name = "sed_email")
    private String sedEmail;
    @Column(name = "sed_usuarioingresa")
    private String sedUsuarioingresa;
    @Column(name = "sed_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sedFechaingresa;
    @Column(name = "sed_usuariomodifica")
    private String sedUsuariomodifica;
    @Column(name = "sed_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sedFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funSedcodigo", fetch = FetchType.LAZY)
    private List<BikFuncionario> bikFuncionarioList;
    @JoinColumn(name = "sed_cencodigo", referencedColumnName = "cen_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikCentro sedCencodigo;
    @JoinColumn(name = "sed_codencargado", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona sedCodencargado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expSedcodigo", fetch = FetchType.LAZY)
    private List<BikExpediente> bikExpedienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pueSedcodigo", fetch = FetchType.LAZY)
    private List<BikPuesto> bikPuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuSedcodigo", fetch = FetchType.LAZY)
    private List<BikUsuario> bikUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ageSedcodigo", fetch = FetchType.LAZY)
    private List<BikAgenda> bikAgendaList;

    public BikSede() {
    }

    public BikSede(Integer sedCodigo) {
        this.sedCodigo = sedCodigo;
    }

    public BikSede(Integer sedCodigo, String sedNombre, String sedDescripcion, String sedTelefonos) {
        this.sedCodigo = sedCodigo;
        this.sedNombre = sedNombre;
        this.sedDescripcion = sedDescripcion;
        this.sedTelefonos = sedTelefonos;
    }

    public Integer getSedCodigo() {
        return sedCodigo;
    }

    public void setSedCodigo(Integer sedCodigo) {
        this.sedCodigo = sedCodigo;
    }

    public String getSedNombre() {
        return sedNombre;
    }

    public void setSedNombre(String sedNombre) {
        this.sedNombre = sedNombre;
    }

    public String getSedDescripcion() {
        return sedDescripcion;
    }

    public void setSedDescripcion(String sedDescripcion) {
        this.sedDescripcion = sedDescripcion;
    }

    public String getSedTelefonos() {
        return sedTelefonos;
    }

    public void setSedTelefonos(String sedTelefonos) {
        this.sedTelefonos = sedTelefonos;
    }

    public String getSedFax() {
        return sedFax;
    }

    public void setSedFax(String sedFax) {
        this.sedFax = sedFax;
    }

    public String getSedEmail() {
        return sedEmail;
    }

    public void setSedEmail(String sedEmail) {
        this.sedEmail = sedEmail;
    }

    public String getSedUsuarioingresa() {
        return sedUsuarioingresa;
    }

    public void setSedUsuarioingresa(String sedUsuarioingresa) {
        this.sedUsuarioingresa = sedUsuarioingresa;
    }

    public Date getSedFechaingresa() {
        return sedFechaingresa;
    }

    public void setSedFechaingresa(Date sedFechaingresa) {
        this.sedFechaingresa = sedFechaingresa;
    }

    public String getSedUsuariomodifica() {
        return sedUsuariomodifica;
    }

    public void setSedUsuariomodifica(String sedUsuariomodifica) {
        this.sedUsuariomodifica = sedUsuariomodifica;
    }

    public Date getSedFechamodifica() {
        return sedFechamodifica;
    }

    public void setSedFechamodifica(Date sedFechamodifica) {
        this.sedFechamodifica = sedFechamodifica;
    }

    @XmlTransient
    public List<BikFuncionario> getBikFuncionarioList() {
        return bikFuncionarioList;
    }

    public void setBikFuncionarioList(List<BikFuncionario> bikFuncionarioList) {
        this.bikFuncionarioList = bikFuncionarioList;
    }

    public BikCentro getSedCencodigo() {
        return sedCencodigo;
    }

    public void setSedCencodigo(BikCentro sedCencodigo) {
        this.sedCencodigo = sedCencodigo;
    }

    public BikPersona getSedCodencargado() {
        return sedCodencargado;
    }

    public void setSedCodencargado(BikPersona sedCodencargado) {
        this.sedCodencargado = sedCodencargado;
    }

    @XmlTransient
    public List<BikExpediente> getBikExpedienteList() {
        return bikExpedienteList;
    }

    public void setBikExpedienteList(List<BikExpediente> bikExpedienteList) {
        this.bikExpedienteList = bikExpedienteList;
    }

    @XmlTransient
    public List<BikPuesto> getBikPuestoList() {
        return bikPuestoList;
    }

    public void setBikPuestoList(List<BikPuesto> bikPuestoList) {
        this.bikPuestoList = bikPuestoList;
    }

    @XmlTransient
    public List<BikUsuario> getBikUsuarioList() {
        return bikUsuarioList;
    }

    public void setBikUsuarioList(List<BikUsuario> bikUsuarioList) {
        this.bikUsuarioList = bikUsuarioList;
    }

    @XmlTransient
    public List<BikAgenda> getBikAgendaList() {
        return bikAgendaList;
    }

    public void setBikAgendaList(List<BikAgenda> bikAgendaList) {
        this.bikAgendaList = bikAgendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sedCodigo != null ? sedCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikSede)) {
            return false;
        }
        BikSede other = (BikSede) object;
        if ((this.sedCodigo == null && other.sedCodigo != null) || (this.sedCodigo != null && !this.sedCodigo.equals(other.sedCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikSede[ sedCodigo=" + sedCodigo + " ]";
    }
    
}