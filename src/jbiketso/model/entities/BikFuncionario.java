/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "bik_funcionario",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikFuncionario.findAll", query = "SELECT b FROM BikFuncionario b")
    , @NamedQuery(name = "BikFuncionario.findByFunCodigo", query = "SELECT b FROM BikFuncionario b WHERE b.funCodigo = :funCodigo")
    , @NamedQuery(name = "BikFuncionario.findByFunEstado", query = "SELECT b FROM BikFuncionario b WHERE b.funEstado = :funEstado")
    , @NamedQuery(name = "BikFuncionario.findByFunTipo", query = "SELECT b FROM BikFuncionario b WHERE b.funTipo = :funTipo")
    , @NamedQuery(name = "BikFuncionario.findByFunSalarioBase", query = "SELECT b FROM BikFuncionario b WHERE b.funSalarioBase = :funSalarioBase")
    , @NamedQuery(name = "BikFuncionario.findByFunFechaingreso", query = "SELECT b FROM BikFuncionario b WHERE b.funFechaingreso = :funFechaingreso")
    , @NamedQuery(name = "BikFuncionario.findByFunFechasalida", query = "SELECT b FROM BikFuncionario b WHERE b.funFechasalida = :funFechasalida")
    , @NamedQuery(name = "BikFuncionario.findByFunObservaciones", query = "SELECT b FROM BikFuncionario b WHERE b.funObservaciones = :funObservaciones")
    , @NamedQuery(name = "BikFuncionario.findByFunUsuarioingresa", query = "SELECT b FROM BikFuncionario b WHERE b.funUsuarioingresa = :funUsuarioingresa")
    , @NamedQuery(name = "BikFuncionario.findByFunFechaingresa", query = "SELECT b FROM BikFuncionario b WHERE b.funFechaingresa = :funFechaingresa")
    , @NamedQuery(name = "BikFuncionario.findByFunUsuariomodifica", query = "SELECT b FROM BikFuncionario b WHERE b.funUsuariomodifica = :funUsuariomodifica")
    , @NamedQuery(name = "BikFuncionario.findByFunFechamodifica", query = "SELECT b FROM BikFuncionario b WHERE b.funFechamodifica = :funFechamodifica")})
public class BikFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fun_codigo")
    private Integer funCodigo;
    @Basic(optional = false)
    @Column(name = "fun_estado")
    private String funEstado;
    @Basic(optional = false)
    @Column(name = "fun_tipo")
    private String funTipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fun_salario base")
    private BigDecimal funSalarioBase;
    @Basic(optional = false)
    @Column(name = "fun_fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date funFechaingreso;
    @Column(name = "fun_fechasalida")
    @Temporal(TemporalType.DATE)
    private Date funFechasalida;
    @Column(name = "fun_observaciones")
    private String funObservaciones;
    @Column(name = "fun_usuarioingresa")
    private String funUsuarioingresa;
    @Column(name = "fun_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funFechaingresa;
    @Column(name = "fun_usuariomodifica")
    private String funUsuariomodifica;
    @Column(name = "fun_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accFuncodigo", fetch = FetchType.LAZY)
    private List<BikAccionesPersonal> bikAccionesPersonalList;
    @JoinColumn(name = "fun_percodigo", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona funPercodigo;
    @JoinColumn(name = "fun_puecodigo", referencedColumnName = "pue_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPuesto funPuecodigo;
    @JoinColumn(name = "fun_sedcodigo", referencedColumnName = "sed_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikSede funSedcodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaFuncodigo", fetch = FetchType.LAZY)
    private List<BikDetalleAgenda> bikDetalleAgendaList;

    public BikFuncionario() {
    }

    public BikFuncionario(Integer funCodigo) {
        this.funCodigo = funCodigo;
    }

    public BikFuncionario(Integer funCodigo, String funEstado, String funTipo, Date funFechaingreso) {
        this.funCodigo = funCodigo;
        this.funEstado = funEstado;
        this.funTipo = funTipo;
        this.funFechaingreso = funFechaingreso;
    }

    public Integer getFunCodigo() {
        return funCodigo;
    }

    public void setFunCodigo(Integer funCodigo) {
        this.funCodigo = funCodigo;
    }

    public String getFunEstado() {
        return funEstado;
    }

    public void setFunEstado(String funEstado) {
        this.funEstado = funEstado;
    }

    public String getFunTipo() {
        return funTipo;
    }

    public void setFunTipo(String funTipo) {
        this.funTipo = funTipo;
    }

    public BigDecimal getFunSalarioBase() {
        return funSalarioBase;
    }

    public void setFunSalarioBase(BigDecimal funSalarioBase) {
        this.funSalarioBase = funSalarioBase;
    }

    public Date getFunFechaingreso() {
        return funFechaingreso;
    }

    public void setFunFechaingreso(Date funFechaingreso) {
        this.funFechaingreso = funFechaingreso;
    }

    public Date getFunFechasalida() {
        return funFechasalida;
    }

    public void setFunFechasalida(Date funFechasalida) {
        this.funFechasalida = funFechasalida;
    }

    public String getFunObservaciones() {
        return funObservaciones;
    }

    public void setFunObservaciones(String funObservaciones) {
        this.funObservaciones = funObservaciones;
    }

    public String getFunUsuarioingresa() {
        return funUsuarioingresa;
    }

    public void setFunUsuarioingresa(String funUsuarioingresa) {
        this.funUsuarioingresa = funUsuarioingresa;
    }

    public Date getFunFechaingresa() {
        return funFechaingresa;
    }

    public void setFunFechaingresa(Date funFechaingresa) {
        this.funFechaingresa = funFechaingresa;
    }

    public String getFunUsuariomodifica() {
        return funUsuariomodifica;
    }

    public void setFunUsuariomodifica(String funUsuariomodifica) {
        this.funUsuariomodifica = funUsuariomodifica;
    }

    public Date getFunFechamodifica() {
        return funFechamodifica;
    }

    public void setFunFechamodifica(Date funFechamodifica) {
        this.funFechamodifica = funFechamodifica;
    }

    @XmlTransient
    public List<BikAccionesPersonal> getBikAccionesPersonalList() {
        return bikAccionesPersonalList;
    }

    public void setBikAccionesPersonalList(List<BikAccionesPersonal> bikAccionesPersonalList) {
        this.bikAccionesPersonalList = bikAccionesPersonalList;
    }

    public BikPersona getFunPercodigo() {
        return funPercodigo;
    }

    public void setFunPercodigo(BikPersona funPercodigo) {
        this.funPercodigo = funPercodigo;
    }

    public BikPuesto getFunPuecodigo() {
        return funPuecodigo;
    }

    public void setFunPuecodigo(BikPuesto funPuecodigo) {
        this.funPuecodigo = funPuecodigo;
    }

    public BikSede getFunSedcodigo() {
        return funSedcodigo;
    }

    public void setFunSedcodigo(BikSede funSedcodigo) {
        this.funSedcodigo = funSedcodigo;
    }

    @XmlTransient
    public List<BikDetalleAgenda> getBikDetalleAgendaList() {
        return bikDetalleAgendaList;
    }

    public void setBikDetalleAgendaList(List<BikDetalleAgenda> bikDetalleAgendaList) {
        this.bikDetalleAgendaList = bikDetalleAgendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funCodigo != null ? funCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikFuncionario)) {
            return false;
        }
        BikFuncionario other = (BikFuncionario) object;
        if ((this.funCodigo == null && other.funCodigo != null) || (this.funCodigo != null && !this.funCodigo.equals(other.funCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikFuncionario[ funCodigo=" + funCodigo + " ]";
    }
    
}
