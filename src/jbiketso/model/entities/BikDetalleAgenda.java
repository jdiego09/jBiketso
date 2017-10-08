/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

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
@Table(name = "bik_detalle_agenda",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikDetalleAgenda.findAll", query = "SELECT b FROM BikDetalleAgenda b")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaCodigo", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaCodigo = :deaCodigo")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaFechainicio", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaFechainicio = :deaFechainicio")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaFechafin", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaFechafin = :deaFechafin")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaDetalle", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaDetalle = :deaDetalle")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaEstado", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaEstado = :deaEstado")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaUsuarioingresa", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaUsuarioingresa = :deaUsuarioingresa")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaFechaingresa", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaFechaingresa = :deaFechaingresa")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaUsuariomodifica", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaUsuariomodifica = :deaUsuariomodifica")
    , @NamedQuery(name = "BikDetalleAgenda.findByDeaFechamodifica", query = "SELECT b FROM BikDetalleAgenda b WHERE b.deaFechamodifica = :deaFechamodifica")})
public class BikDetalleAgenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dea_codigo")
    private Integer deaCodigo;
    @Column(name = "dea_fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deaFechainicio;
    @Column(name = "dea_fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deaFechafin;
    @Basic(optional = false)
    @Column(name = "dea_detalle")
    private String deaDetalle;
    @Basic(optional = false)
    @Column(name = "dea_estado")
    private String deaEstado;
    @Column(name = "dea_usuarioingresa")
    private String deaUsuarioingresa;
    @Column(name = "dea_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deaFechaingresa;
    @Column(name = "dea_usuariomodifica")
    private String deaUsuariomodifica;
    @Column(name = "dea_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deaFechamodifica;
    @JoinColumn(name = "dea_agecodigo", referencedColumnName = "age_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikAgenda deaAgecodigo;
    @JoinColumn(name = "dea_funcodigo", referencedColumnName = "fun_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikFuncionario deaFuncodigo;
    @JoinColumn(name = "dea_codusuario", referencedColumnName = "usu_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikUsuario deaCodusuario;

    public BikDetalleAgenda() {
    }

    public BikDetalleAgenda(Integer deaCodigo) {
        this.deaCodigo = deaCodigo;
    }

    public BikDetalleAgenda(Integer deaCodigo, String deaDetalle, String deaEstado) {
        this.deaCodigo = deaCodigo;
        this.deaDetalle = deaDetalle;
        this.deaEstado = deaEstado;
    }

    public Integer getDeaCodigo() {
        return deaCodigo;
    }

    public void setDeaCodigo(Integer deaCodigo) {
        this.deaCodigo = deaCodigo;
    }

    public Date getDeaFechainicio() {
        return deaFechainicio;
    }

    public void setDeaFechainicio(Date deaFechainicio) {
        this.deaFechainicio = deaFechainicio;
    }

    public Date getDeaFechafin() {
        return deaFechafin;
    }

    public void setDeaFechafin(Date deaFechafin) {
        this.deaFechafin = deaFechafin;
    }

    public String getDeaDetalle() {
        return deaDetalle;
    }

    public void setDeaDetalle(String deaDetalle) {
        this.deaDetalle = deaDetalle;
    }

    public String getDeaEstado() {
        return deaEstado;
    }

    public void setDeaEstado(String deaEstado) {
        this.deaEstado = deaEstado;
    }

    public String getDeaUsuarioingresa() {
        return deaUsuarioingresa;
    }

    public void setDeaUsuarioingresa(String deaUsuarioingresa) {
        this.deaUsuarioingresa = deaUsuarioingresa;
    }

    public Date getDeaFechaingresa() {
        return deaFechaingresa;
    }

    public void setDeaFechaingresa(Date deaFechaingresa) {
        this.deaFechaingresa = deaFechaingresa;
    }

    public String getDeaUsuariomodifica() {
        return deaUsuariomodifica;
    }

    public void setDeaUsuariomodifica(String deaUsuariomodifica) {
        this.deaUsuariomodifica = deaUsuariomodifica;
    }

    public Date getDeaFechamodifica() {
        return deaFechamodifica;
    }

    public void setDeaFechamodifica(Date deaFechamodifica) {
        this.deaFechamodifica = deaFechamodifica;
    }

    public BikAgenda getDeaAgecodigo() {
        return deaAgecodigo;
    }

    public void setDeaAgecodigo(BikAgenda deaAgecodigo) {
        this.deaAgecodigo = deaAgecodigo;
    }

    public BikFuncionario getDeaFuncodigo() {
        return deaFuncodigo;
    }

    public void setDeaFuncodigo(BikFuncionario deaFuncodigo) {
        this.deaFuncodigo = deaFuncodigo;
    }

    public BikUsuario getDeaCodusuario() {
        return deaCodusuario;
    }

    public void setDeaCodusuario(BikUsuario deaCodusuario) {
        this.deaCodusuario = deaCodusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deaCodigo != null ? deaCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikDetalleAgenda)) {
            return false;
        }
        BikDetalleAgenda other = (BikDetalleAgenda) object;
        if ((this.deaCodigo == null && other.deaCodigo != null) || (this.deaCodigo != null && !this.deaCodigo.equals(other.deaCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikDetalleAgenda[ deaCodigo=" + deaCodigo + " ]";
    }
    
}
