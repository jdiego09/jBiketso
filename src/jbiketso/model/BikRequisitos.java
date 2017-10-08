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
@Table(name = "bik_requisitos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikRequisitos.findAll", query = "SELECT b FROM BikRequisitos b")
    , @NamedQuery(name = "BikRequisitos.findByReqCodigo", query = "SELECT b FROM BikRequisitos b WHERE b.reqCodigo = :reqCodigo")
    , @NamedQuery(name = "BikRequisitos.findByReqNombre", query = "SELECT b FROM BikRequisitos b WHERE b.reqNombre = :reqNombre")
    , @NamedQuery(name = "BikRequisitos.findByReqDescripcion", query = "SELECT b FROM BikRequisitos b WHERE b.reqDescripcion = :reqDescripcion")
    , @NamedQuery(name = "BikRequisitos.findByReqTipoatencion", query = "SELECT b FROM BikRequisitos b WHERE b.reqTipoatencion = :reqTipoatencion")
    , @NamedQuery(name = "BikRequisitos.findByReqEsobligatorio", query = "SELECT b FROM BikRequisitos b WHERE b.reqEsobligatorio = :reqEsobligatorio")
    , @NamedQuery(name = "BikRequisitos.findByReqEstado", query = "SELECT b FROM BikRequisitos b WHERE b.reqEstado = :reqEstado")
    , @NamedQuery(name = "BikRequisitos.findByReqUsuarioingresa", query = "SELECT b FROM BikRequisitos b WHERE b.reqUsuarioingresa = :reqUsuarioingresa")
    , @NamedQuery(name = "BikRequisitos.findByReqFechaingresa", query = "SELECT b FROM BikRequisitos b WHERE b.reqFechaingresa = :reqFechaingresa")
    , @NamedQuery(name = "BikRequisitos.findByReqUsuariomodifica", query = "SELECT b FROM BikRequisitos b WHERE b.reqUsuariomodifica = :reqUsuariomodifica")
    , @NamedQuery(name = "BikRequisitos.findByReqFechamodifica", query = "SELECT b FROM BikRequisitos b WHERE b.reqFechamodifica = :reqFechamodifica")})
public class BikRequisitos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "req_codigo")
    private Integer reqCodigo;
    @Basic(optional = false)
    @Column(name = "req_nombre")
    private String reqNombre;
    @Basic(optional = false)
    @Column(name = "req_descripcion")
    private String reqDescripcion;
    @Basic(optional = false)
    @Column(name = "req_tipoatencion")
    private String reqTipoatencion;
    @Basic(optional = false)
    @Column(name = "req_esobligatorio")
    private String reqEsobligatorio;
    @Basic(optional = false)
    @Column(name = "req_estado")
    private String reqEstado;
    @Column(name = "req_usuarioingresa")
    private String reqUsuarioingresa;
    @Column(name = "req_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reqFechaingresa;
    @Column(name = "req_usuariomodifica")
    private String reqUsuariomodifica;
    @Column(name = "req_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reqFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikRequisitos", fetch = FetchType.LAZY)
    private List<BikRequisitosExpediente> bikRequisitosExpedienteList;

    public BikRequisitos() {
    }

    public BikRequisitos(Integer reqCodigo) {
        this.reqCodigo = reqCodigo;
    }

    public BikRequisitos(Integer reqCodigo, String reqNombre, String reqDescripcion, String reqTipoatencion, String reqEsobligatorio, String reqEstado) {
        this.reqCodigo = reqCodigo;
        this.reqNombre = reqNombre;
        this.reqDescripcion = reqDescripcion;
        this.reqTipoatencion = reqTipoatencion;
        this.reqEsobligatorio = reqEsobligatorio;
        this.reqEstado = reqEstado;
    }

    public Integer getReqCodigo() {
        return reqCodigo;
    }

    public void setReqCodigo(Integer reqCodigo) {
        this.reqCodigo = reqCodigo;
    }

    public String getReqNombre() {
        return reqNombre;
    }

    public void setReqNombre(String reqNombre) {
        this.reqNombre = reqNombre;
    }

    public String getReqDescripcion() {
        return reqDescripcion;
    }

    public void setReqDescripcion(String reqDescripcion) {
        this.reqDescripcion = reqDescripcion;
    }

    public String getReqTipoatencion() {
        return reqTipoatencion;
    }

    public void setReqTipoatencion(String reqTipoatencion) {
        this.reqTipoatencion = reqTipoatencion;
    }

    public String getReqEsobligatorio() {
        return reqEsobligatorio;
    }

    public void setReqEsobligatorio(String reqEsobligatorio) {
        this.reqEsobligatorio = reqEsobligatorio;
    }

    public String getReqEstado() {
        return reqEstado;
    }

    public void setReqEstado(String reqEstado) {
        this.reqEstado = reqEstado;
    }

    public String getReqUsuarioingresa() {
        return reqUsuarioingresa;
    }

    public void setReqUsuarioingresa(String reqUsuarioingresa) {
        this.reqUsuarioingresa = reqUsuarioingresa;
    }

    public Date getReqFechaingresa() {
        return reqFechaingresa;
    }

    public void setReqFechaingresa(Date reqFechaingresa) {
        this.reqFechaingresa = reqFechaingresa;
    }

    public String getReqUsuariomodifica() {
        return reqUsuariomodifica;
    }

    public void setReqUsuariomodifica(String reqUsuariomodifica) {
        this.reqUsuariomodifica = reqUsuariomodifica;
    }

    public Date getReqFechamodifica() {
        return reqFechamodifica;
    }

    public void setReqFechamodifica(Date reqFechamodifica) {
        this.reqFechamodifica = reqFechamodifica;
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
        hash += (reqCodigo != null ? reqCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikRequisitos)) {
            return false;
        }
        BikRequisitos other = (BikRequisitos) object;
        if ((this.reqCodigo == null && other.reqCodigo != null) || (this.reqCodigo != null && !this.reqCodigo.equals(other.reqCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikRequisitos[ reqCodigo=" + reqCodigo + " ]";
    }
    
}
