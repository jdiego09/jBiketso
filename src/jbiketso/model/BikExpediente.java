/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "bik_expediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikExpediente.findAll", query = "SELECT b FROM BikExpediente b")
    , @NamedQuery(name = "BikExpediente.findByExpCodigo", query = "SELECT b FROM BikExpediente b WHERE b.expCodigo = :expCodigo")
    , @NamedQuery(name = "BikExpediente.findByExpFechaingreso", query = "SELECT b FROM BikExpediente b WHERE b.expFechaingreso = :expFechaingreso")
    , @NamedQuery(name = "BikExpediente.findByExpFechasalida", query = "SELECT b FROM BikExpediente b WHERE b.expFechasalida = :expFechasalida")
    , @NamedQuery(name = "BikExpediente.findByExpEstado", query = "SELECT b FROM BikExpediente b WHERE b.expEstado = :expEstado")
    , @NamedQuery(name = "BikExpediente.findByExpTipoatencion", query = "SELECT b FROM BikExpediente b WHERE b.expTipoatencion = :expTipoatencion")
    , @NamedQuery(name = "BikExpediente.findByExpEstudiosocioeconomico", query = "SELECT b FROM BikExpediente b WHERE b.expEstudiosocioeconomico = :expEstudiosocioeconomico")
    , @NamedQuery(name = "BikExpediente.findByExpPersonashogar", query = "SELECT b FROM BikExpediente b WHERE b.expPersonashogar = :expPersonashogar")
    , @NamedQuery(name = "BikExpediente.findByExpDependientes", query = "SELECT b FROM BikExpediente b WHERE b.expDependientes = :expDependientes")
    , @NamedQuery(name = "BikExpediente.findByExpIngresopromedio", query = "SELECT b FROM BikExpediente b WHERE b.expIngresopromedio = :expIngresopromedio")
    , @NamedQuery(name = "BikExpediente.findByExpUsuarioingresa", query = "SELECT b FROM BikExpediente b WHERE b.expUsuarioingresa = :expUsuarioingresa")
    , @NamedQuery(name = "BikExpediente.findByExpFechaingresa", query = "SELECT b FROM BikExpediente b WHERE b.expFechaingresa = :expFechaingresa")
    , @NamedQuery(name = "BikExpediente.findByExpUsuariomodifica", query = "SELECT b FROM BikExpediente b WHERE b.expUsuariomodifica = :expUsuariomodifica")
    , @NamedQuery(name = "BikExpediente.findByExpFechamodifica", query = "SELECT b FROM BikExpediente b WHERE b.expFechamodifica = :expFechamodifica")})
public class BikExpediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "exp_codigo")
    private Integer expCodigo;
    @Basic(optional = false)
    @Column(name = "exp_fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date expFechaingreso;
    @Column(name = "exp_fechasalida")
    @Temporal(TemporalType.DATE)
    private Date expFechasalida;
    @Basic(optional = false)
    @Column(name = "exp_estado")
    private String expEstado;
    @Basic(optional = false)
    @Column(name = "exp_tipoatencion")
    private String expTipoatencion;
    @Column(name = "exp_estudiosocioeconomico")
    private Integer expEstudiosocioeconomico;
    @Column(name = "exp_personashogar")
    private Integer expPersonashogar;
    @Column(name = "exp_dependientes")
    private Integer expDependientes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "exp_ingresopromedio")
    private BigDecimal expIngresopromedio;
    @Column(name = "exp_usuarioingresa")
    private String expUsuarioingresa;
    @Column(name = "exp_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expFechaingresa;
    @Column(name = "exp_usuariomodifica")
    private String expUsuariomodifica;
    @Column(name = "exp_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "padExpcodigo", fetch = FetchType.LAZY)
    private List<BikPadecimiento> bikPadecimientoList;
    @JoinColumn(name = "exp_codencargado", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona expCodencargado;
    @JoinColumn(name = "exp_sedcodigo", referencedColumnName = "sed_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikSede expSedcodigo;
    @JoinColumn(name = "exp_usucodigo", referencedColumnName = "usu_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikUsuario expUsucodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medExpcodigo", fetch = FetchType.LAZY)
    private List<BikMedicamento> bikMedicamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikExpediente", fetch = FetchType.LAZY)
    private List<BikRequisitosExpediente> bikRequisitosExpedienteList;

    public BikExpediente() {
    }

    public BikExpediente(Integer expCodigo) {
        this.expCodigo = expCodigo;
    }

    public BikExpediente(Integer expCodigo, Date expFechaingreso, String expEstado, String expTipoatencion) {
        this.expCodigo = expCodigo;
        this.expFechaingreso = expFechaingreso;
        this.expEstado = expEstado;
        this.expTipoatencion = expTipoatencion;
    }

    public Integer getExpCodigo() {
        return expCodigo;
    }

    public void setExpCodigo(Integer expCodigo) {
        this.expCodigo = expCodigo;
    }

    public Date getExpFechaingreso() {
        return expFechaingreso;
    }

    public void setExpFechaingreso(Date expFechaingreso) {
        this.expFechaingreso = expFechaingreso;
    }

    public Date getExpFechasalida() {
        return expFechasalida;
    }

    public void setExpFechasalida(Date expFechasalida) {
        this.expFechasalida = expFechasalida;
    }

    public String getExpEstado() {
        return expEstado;
    }

    public void setExpEstado(String expEstado) {
        this.expEstado = expEstado;
    }

    public String getExpTipoatencion() {
        return expTipoatencion;
    }

    public void setExpTipoatencion(String expTipoatencion) {
        this.expTipoatencion = expTipoatencion;
    }

    public Integer getExpEstudiosocioeconomico() {
        return expEstudiosocioeconomico;
    }

    public void setExpEstudiosocioeconomico(Integer expEstudiosocioeconomico) {
        this.expEstudiosocioeconomico = expEstudiosocioeconomico;
    }

    public Integer getExpPersonashogar() {
        return expPersonashogar;
    }

    public void setExpPersonashogar(Integer expPersonashogar) {
        this.expPersonashogar = expPersonashogar;
    }

    public Integer getExpDependientes() {
        return expDependientes;
    }

    public void setExpDependientes(Integer expDependientes) {
        this.expDependientes = expDependientes;
    }

    public BigDecimal getExpIngresopromedio() {
        return expIngresopromedio;
    }

    public void setExpIngresopromedio(BigDecimal expIngresopromedio) {
        this.expIngresopromedio = expIngresopromedio;
    }

    public String getExpUsuarioingresa() {
        return expUsuarioingresa;
    }

    public void setExpUsuarioingresa(String expUsuarioingresa) {
        this.expUsuarioingresa = expUsuarioingresa;
    }

    public Date getExpFechaingresa() {
        return expFechaingresa;
    }

    public void setExpFechaingresa(Date expFechaingresa) {
        this.expFechaingresa = expFechaingresa;
    }

    public String getExpUsuariomodifica() {
        return expUsuariomodifica;
    }

    public void setExpUsuariomodifica(String expUsuariomodifica) {
        this.expUsuariomodifica = expUsuariomodifica;
    }

    public Date getExpFechamodifica() {
        return expFechamodifica;
    }

    public void setExpFechamodifica(Date expFechamodifica) {
        this.expFechamodifica = expFechamodifica;
    }

    @XmlTransient
    public List<BikPadecimiento> getBikPadecimientoList() {
        return bikPadecimientoList;
    }

    public void setBikPadecimientoList(List<BikPadecimiento> bikPadecimientoList) {
        this.bikPadecimientoList = bikPadecimientoList;
    }

    public BikPersona getExpCodencargado() {
        return expCodencargado;
    }

    public void setExpCodencargado(BikPersona expCodencargado) {
        this.expCodencargado = expCodencargado;
    }

    public BikSede getExpSedcodigo() {
        return expSedcodigo;
    }

    public void setExpSedcodigo(BikSede expSedcodigo) {
        this.expSedcodigo = expSedcodigo;
    }

    public BikUsuario getExpUsucodigo() {
        return expUsucodigo;
    }

    public void setExpUsucodigo(BikUsuario expUsucodigo) {
        this.expUsucodigo = expUsucodigo;
    }

    @XmlTransient
    public List<BikMedicamento> getBikMedicamentoList() {
        return bikMedicamentoList;
    }

    public void setBikMedicamentoList(List<BikMedicamento> bikMedicamentoList) {
        this.bikMedicamentoList = bikMedicamentoList;
    }

    @XmlTransient
    public List<BikRequisitosExpediente> getBikRequisitosExpedienteList() {
        return bikRequisitosExpedienteList;
    }

    public void setBikRequisitosExpedienteList(List<BikRequisitosExpediente> bikRequisitosExpedienteList) {
        this.bikRequisitosExpedienteList = bikRequisitosExpedienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expCodigo != null ? expCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikExpediente)) {
            return false;
        }
        BikExpediente other = (BikExpediente) object;
        if ((this.expCodigo == null && other.expCodigo != null) || (this.expCodigo != null && !this.expCodigo.equals(other.expCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikExpediente[ expCodigo=" + expCodigo + " ]";
    }
    
}
