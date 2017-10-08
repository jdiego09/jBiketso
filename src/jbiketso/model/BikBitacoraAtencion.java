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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "bik_bitacora_atencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikBitacoraAtencion.findAll", query = "SELECT b FROM BikBitacoraAtencion b")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaCodigo", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.bikBitacoraAtencionPK.biaCodigo = :biaCodigo")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaCodusuario", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.bikBitacoraAtencionPK.biaCodusuario = :biaCodusuario")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaFechainicio", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaFechainicio = :biaFechainicio")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaFechafin", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaFechafin = :biaFechafin")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaDetalle", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaDetalle = :biaDetalle")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaUsuarioingresa", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaUsuarioingresa = :biaUsuarioingresa")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaFechaingresa", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaFechaingresa = :biaFechaingresa")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaUsuariomodifica", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaUsuariomodifica = :biaUsuariomodifica")
    , @NamedQuery(name = "BikBitacoraAtencion.findByBiaFechamodifica", query = "SELECT b FROM BikBitacoraAtencion b WHERE b.biaFechamodifica = :biaFechamodifica")})
public class BikBitacoraAtencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BikBitacoraAtencionPK bikBitacoraAtencionPK;
    @Basic(optional = false)
    @Column(name = "bia_fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biaFechainicio;
    @Column(name = "bia_fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biaFechafin;
    @Basic(optional = false)
    @Column(name = "bia_detalle")
    private String biaDetalle;
    @Column(name = "bia_usuarioingresa")
    private String biaUsuarioingresa;
    @Column(name = "bia_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biaFechaingresa;
    @Column(name = "bia_usuariomodifica")
    private String biaUsuariomodifica;
    @Column(name = "bia_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biaFechamodifica;
    @JoinColumn(name = "bia_codusuario", referencedColumnName = "usu_codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikUsuario bikUsuario;

    public BikBitacoraAtencion() {
    }

    public BikBitacoraAtencion(BikBitacoraAtencionPK bikBitacoraAtencionPK) {
        this.bikBitacoraAtencionPK = bikBitacoraAtencionPK;
    }

    public BikBitacoraAtencion(BikBitacoraAtencionPK bikBitacoraAtencionPK, Date biaFechainicio, String biaDetalle) {
        this.bikBitacoraAtencionPK = bikBitacoraAtencionPK;
        this.biaFechainicio = biaFechainicio;
        this.biaDetalle = biaDetalle;
    }

    public BikBitacoraAtencion(int biaCodigo, int biaCodusuario) {
        this.bikBitacoraAtencionPK = new BikBitacoraAtencionPK(biaCodigo, biaCodusuario);
    }

    public BikBitacoraAtencionPK getBikBitacoraAtencionPK() {
        return bikBitacoraAtencionPK;
    }

    public void setBikBitacoraAtencionPK(BikBitacoraAtencionPK bikBitacoraAtencionPK) {
        this.bikBitacoraAtencionPK = bikBitacoraAtencionPK;
    }

    public Date getBiaFechainicio() {
        return biaFechainicio;
    }

    public void setBiaFechainicio(Date biaFechainicio) {
        this.biaFechainicio = biaFechainicio;
    }

    public Date getBiaFechafin() {
        return biaFechafin;
    }

    public void setBiaFechafin(Date biaFechafin) {
        this.biaFechafin = biaFechafin;
    }

    public String getBiaDetalle() {
        return biaDetalle;
    }

    public void setBiaDetalle(String biaDetalle) {
        this.biaDetalle = biaDetalle;
    }

    public String getBiaUsuarioingresa() {
        return biaUsuarioingresa;
    }

    public void setBiaUsuarioingresa(String biaUsuarioingresa) {
        this.biaUsuarioingresa = biaUsuarioingresa;
    }

    public Date getBiaFechaingresa() {
        return biaFechaingresa;
    }

    public void setBiaFechaingresa(Date biaFechaingresa) {
        this.biaFechaingresa = biaFechaingresa;
    }

    public String getBiaUsuariomodifica() {
        return biaUsuariomodifica;
    }

    public void setBiaUsuariomodifica(String biaUsuariomodifica) {
        this.biaUsuariomodifica = biaUsuariomodifica;
    }

    public Date getBiaFechamodifica() {
        return biaFechamodifica;
    }

    public void setBiaFechamodifica(Date biaFechamodifica) {
        this.biaFechamodifica = biaFechamodifica;
    }

    public BikUsuario getBikUsuario() {
        return bikUsuario;
    }

    public void setBikUsuario(BikUsuario bikUsuario) {
        this.bikUsuario = bikUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bikBitacoraAtencionPK != null ? bikBitacoraAtencionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikBitacoraAtencion)) {
            return false;
        }
        BikBitacoraAtencion other = (BikBitacoraAtencion) object;
        if ((this.bikBitacoraAtencionPK == null && other.bikBitacoraAtencionPK != null) || (this.bikBitacoraAtencionPK != null && !this.bikBitacoraAtencionPK.equals(other.bikBitacoraAtencionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikBitacoraAtencion[ bikBitacoraAtencionPK=" + bikBitacoraAtencionPK + " ]";
    }
    
}
