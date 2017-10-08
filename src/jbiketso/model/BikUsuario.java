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
@Table(name = "bik_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikUsuario.findAll", query = "SELECT b FROM BikUsuario b")
    , @NamedQuery(name = "BikUsuario.findByUsuCodigo", query = "SELECT b FROM BikUsuario b WHERE b.usuCodigo = :usuCodigo")
    , @NamedQuery(name = "BikUsuario.findByUsuEstado", query = "SELECT b FROM BikUsuario b WHERE b.usuEstado = :usuEstado")
    , @NamedQuery(name = "BikUsuario.findByUsuUsuarioingresa", query = "SELECT b FROM BikUsuario b WHERE b.usuUsuarioingresa = :usuUsuarioingresa")
    , @NamedQuery(name = "BikUsuario.findByUsuFechaingresa", query = "SELECT b FROM BikUsuario b WHERE b.usuFechaingresa = :usuFechaingresa")
    , @NamedQuery(name = "BikUsuario.findByUsuUsuariomodifica", query = "SELECT b FROM BikUsuario b WHERE b.usuUsuariomodifica = :usuUsuariomodifica")
    , @NamedQuery(name = "BikUsuario.findByUsuFechamodifica", query = "SELECT b FROM BikUsuario b WHERE b.usuFechamodifica = :usuFechamodifica")})
public class BikUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usu_codigo")
    private Integer usuCodigo;
    @Basic(optional = false)
    @Column(name = "usu_estado")
    private String usuEstado;
    @Column(name = "usu_usuarioingresa")
    private String usuUsuarioingresa;
    @Column(name = "usu_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaingresa;
    @Column(name = "usu_usuariomodifica")
    private String usuUsuariomodifica;
    @Column(name = "usu_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikUsuario", fetch = FetchType.LAZY)
    private List<BikBitacoraAtencion> bikBitacoraAtencionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expUsucodigo", fetch = FetchType.LAZY)
    private List<BikExpediente> bikExpedienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaCodusuario", fetch = FetchType.LAZY)
    private List<BikDetalleAgenda> bikDetalleAgendaList;
    @JoinColumn(name = "usu_codencargadolegal", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona usuCodencargadolegal;
    @JoinColumn(name = "usu_percodigo", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona usuPercodigo;
    @JoinColumn(name = "usu_sedcodigo", referencedColumnName = "sed_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikSede usuSedcodigo;

    public BikUsuario() {
    }

    public BikUsuario(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public BikUsuario(Integer usuCodigo, String usuEstado) {
        this.usuCodigo = usuCodigo;
        this.usuEstado = usuEstado;
    }

    public Integer getUsuCodigo() {
        return usuCodigo;
    }

    public void setUsuCodigo(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }

    public String getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
    }

    public String getUsuUsuarioingresa() {
        return usuUsuarioingresa;
    }

    public void setUsuUsuarioingresa(String usuUsuarioingresa) {
        this.usuUsuarioingresa = usuUsuarioingresa;
    }

    public Date getUsuFechaingresa() {
        return usuFechaingresa;
    }

    public void setUsuFechaingresa(Date usuFechaingresa) {
        this.usuFechaingresa = usuFechaingresa;
    }

    public String getUsuUsuariomodifica() {
        return usuUsuariomodifica;
    }

    public void setUsuUsuariomodifica(String usuUsuariomodifica) {
        this.usuUsuariomodifica = usuUsuariomodifica;
    }

    public Date getUsuFechamodifica() {
        return usuFechamodifica;
    }

    public void setUsuFechamodifica(Date usuFechamodifica) {
        this.usuFechamodifica = usuFechamodifica;
    }

    @XmlTransient
    public List<BikBitacoraAtencion> getBikBitacoraAtencionList() {
        return bikBitacoraAtencionList;
    }

    public void setBikBitacoraAtencionList(List<BikBitacoraAtencion> bikBitacoraAtencionList) {
        this.bikBitacoraAtencionList = bikBitacoraAtencionList;
    }

    @XmlTransient
    public List<BikExpediente> getBikExpedienteList() {
        return bikExpedienteList;
    }

    public void setBikExpedienteList(List<BikExpediente> bikExpedienteList) {
        this.bikExpedienteList = bikExpedienteList;
    }

    @XmlTransient
    public List<BikDetalleAgenda> getBikDetalleAgendaList() {
        return bikDetalleAgendaList;
    }

    public void setBikDetalleAgendaList(List<BikDetalleAgenda> bikDetalleAgendaList) {
        this.bikDetalleAgendaList = bikDetalleAgendaList;
    }

    public BikPersona getUsuCodencargadolegal() {
        return usuCodencargadolegal;
    }

    public void setUsuCodencargadolegal(BikPersona usuCodencargadolegal) {
        this.usuCodencargadolegal = usuCodencargadolegal;
    }

    public BikPersona getUsuPercodigo() {
        return usuPercodigo;
    }

    public void setUsuPercodigo(BikPersona usuPercodigo) {
        this.usuPercodigo = usuPercodigo;
    }

    public BikSede getUsuSedcodigo() {
        return usuSedcodigo;
    }

    public void setUsuSedcodigo(BikSede usuSedcodigo) {
        this.usuSedcodigo = usuSedcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCodigo != null ? usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikUsuario)) {
            return false;
        }
        BikUsuario other = (BikUsuario) object;
        if ((this.usuCodigo == null && other.usuCodigo != null) || (this.usuCodigo != null && !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikUsuario[ usuCodigo=" + usuCodigo + " ]";
    }
    
}
