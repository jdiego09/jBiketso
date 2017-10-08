/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model;

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
@Table(name = "bik_acciones_personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikAccionesPersonal.findAll", query = "SELECT b FROM BikAccionesPersonal b")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccCodigo", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accCodigo = :accCodigo")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccTipo", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accTipo = :accTipo")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechainicio", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechainicio = :accFechainicio")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechafinal", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechafinal = :accFechafinal")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccEstado", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accEstado = :accEstado")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccObservaciones", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accObservaciones = :accObservaciones")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccCalificacion", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accCalificacion = :accCalificacion")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccUsuarioaplica", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accUsuarioaplica = :accUsuarioaplica")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechaaplica", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechaaplica = :accFechaaplica")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccUsuarioanula", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accUsuarioanula = :accUsuarioanula")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechaanula", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechaanula = :accFechaanula")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccUsuarioingresa", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accUsuarioingresa = :accUsuarioingresa")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechaingresa", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechaingresa = :accFechaingresa")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccUsuariomodifica", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accUsuariomodifica = :accUsuariomodifica")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechamodifica", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechamodifica = :accFechamodifica")})
public class BikAccionesPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acc_codigo")
    private Integer accCodigo;
    @Basic(optional = false)
    @Column(name = "acc_tipo")
    private String accTipo;
    @Basic(optional = false)
    @Column(name = "acc_fechainicio")
    @Temporal(TemporalType.DATE)
    private Date accFechainicio;
    @Basic(optional = false)
    @Column(name = "acc_fechafinal")
    @Temporal(TemporalType.DATE)
    private Date accFechafinal;
    @Basic(optional = false)
    @Column(name = "acc_estado")
    private String accEstado;
    @Column(name = "acc_observaciones")
    private String accObservaciones;
    @Column(name = "acc_calificacion")
    private Integer accCalificacion;
    @Column(name = "acc_usuarioaplica")
    private String accUsuarioaplica;
    @Column(name = "acc_fechaaplica")
    @Temporal(TemporalType.DATE)
    private Date accFechaaplica;
    @Column(name = "acc_usuarioanula")
    private String accUsuarioanula;
    @Column(name = "acc_fechaanula")
    @Temporal(TemporalType.DATE)
    private Date accFechaanula;
    @Column(name = "acc_usuarioingresa")
    private String accUsuarioingresa;
    @Column(name = "acc_fechaingresa")
    @Temporal(TemporalType.DATE)
    private Date accFechaingresa;
    @Column(name = "acc_usuariomodifica")
    private String accUsuariomodifica;
    @Column(name = "acc_fechamodifica")
    @Temporal(TemporalType.DATE)
    private Date accFechamodifica;
    @JoinColumn(name = "acc_funcodigo", referencedColumnName = "fun_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikFuncionario accFuncodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaAcccodigo", fetch = FetchType.LAZY)
    private List<BikEvaluacion> bikEvaluacionList;

    public BikAccionesPersonal() {
    }

    public BikAccionesPersonal(Integer accCodigo) {
        this.accCodigo = accCodigo;
    }

    public BikAccionesPersonal(Integer accCodigo, String accTipo, Date accFechainicio, Date accFechafinal, String accEstado) {
        this.accCodigo = accCodigo;
        this.accTipo = accTipo;
        this.accFechainicio = accFechainicio;
        this.accFechafinal = accFechafinal;
        this.accEstado = accEstado;
    }

    public Integer getAccCodigo() {
        return accCodigo;
    }

    public void setAccCodigo(Integer accCodigo) {
        this.accCodigo = accCodigo;
    }

    public String getAccTipo() {
        return accTipo;
    }

    public void setAccTipo(String accTipo) {
        this.accTipo = accTipo;
    }

    public Date getAccFechainicio() {
        return accFechainicio;
    }

    public void setAccFechainicio(Date accFechainicio) {
        this.accFechainicio = accFechainicio;
    }

    public Date getAccFechafinal() {
        return accFechafinal;
    }

    public void setAccFechafinal(Date accFechafinal) {
        this.accFechafinal = accFechafinal;
    }

    public String getAccEstado() {
        return accEstado;
    }

    public void setAccEstado(String accEstado) {
        this.accEstado = accEstado;
    }

    public String getAccObservaciones() {
        return accObservaciones;
    }

    public void setAccObservaciones(String accObservaciones) {
        this.accObservaciones = accObservaciones;
    }

    public Integer getAccCalificacion() {
        return accCalificacion;
    }

    public void setAccCalificacion(Integer accCalificacion) {
        this.accCalificacion = accCalificacion;
    }

    public String getAccUsuarioaplica() {
        return accUsuarioaplica;
    }

    public void setAccUsuarioaplica(String accUsuarioaplica) {
        this.accUsuarioaplica = accUsuarioaplica;
    }

    public Date getAccFechaaplica() {
        return accFechaaplica;
    }

    public void setAccFechaaplica(Date accFechaaplica) {
        this.accFechaaplica = accFechaaplica;
    }

    public String getAccUsuarioanula() {
        return accUsuarioanula;
    }

    public void setAccUsuarioanula(String accUsuarioanula) {
        this.accUsuarioanula = accUsuarioanula;
    }

    public Date getAccFechaanula() {
        return accFechaanula;
    }

    public void setAccFechaanula(Date accFechaanula) {
        this.accFechaanula = accFechaanula;
    }

    public String getAccUsuarioingresa() {
        return accUsuarioingresa;
    }

    public void setAccUsuarioingresa(String accUsuarioingresa) {
        this.accUsuarioingresa = accUsuarioingresa;
    }

    public Date getAccFechaingresa() {
        return accFechaingresa;
    }

    public void setAccFechaingresa(Date accFechaingresa) {
        this.accFechaingresa = accFechaingresa;
    }

    public String getAccUsuariomodifica() {
        return accUsuariomodifica;
    }

    public void setAccUsuariomodifica(String accUsuariomodifica) {
        this.accUsuariomodifica = accUsuariomodifica;
    }

    public Date getAccFechamodifica() {
        return accFechamodifica;
    }

    public void setAccFechamodifica(Date accFechamodifica) {
        this.accFechamodifica = accFechamodifica;
    }

    public BikFuncionario getAccFuncodigo() {
        return accFuncodigo;
    }

    public void setAccFuncodigo(BikFuncionario accFuncodigo) {
        this.accFuncodigo = accFuncodigo;
    }

    @XmlTransient
    public List<BikEvaluacion> getBikEvaluacionList() {
        return bikEvaluacionList;
    }

    public void setBikEvaluacionList(List<BikEvaluacion> bikEvaluacionList) {
        this.bikEvaluacionList = bikEvaluacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accCodigo != null ? accCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikAccionesPersonal)) {
            return false;
        }
        BikAccionesPersonal other = (BikAccionesPersonal) object;
        if ((this.accCodigo == null && other.accCodigo != null) || (this.accCodigo != null && !this.accCodigo.equals(other.accCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikAccionesPersonal[ accCodigo=" + accCodigo + " ]";
    }
    
}
