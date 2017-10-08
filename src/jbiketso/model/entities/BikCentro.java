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
@Table(name = "bik_centro",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikCentro.findAll", query = "SELECT b FROM BikCentro b")
    , @NamedQuery(name = "BikCentro.findByCenCodigo", query = "SELECT b FROM BikCentro b WHERE b.cenCodigo = :cenCodigo")
    , @NamedQuery(name = "BikCentro.findByCenNombre", query = "SELECT b FROM BikCentro b WHERE b.cenNombre = :cenNombre")
    , @NamedQuery(name = "BikCentro.findByCenEstado", query = "SELECT b FROM BikCentro b WHERE b.cenEstado = :cenEstado")
    , @NamedQuery(name = "BikCentro.findByCenCedulajuridica", query = "SELECT b FROM BikCentro b WHERE b.cenCedulajuridica = :cenCedulajuridica")
    , @NamedQuery(name = "BikCentro.findByCenLogo", query = "SELECT b FROM BikCentro b WHERE b.cenLogo = :cenLogo")
    , @NamedQuery(name = "BikCentro.findByCenUsuarioingresa", query = "SELECT b FROM BikCentro b WHERE b.cenUsuarioingresa = :cenUsuarioingresa")
    , @NamedQuery(name = "BikCentro.findByCenFechaingresa", query = "SELECT b FROM BikCentro b WHERE b.cenFechaingresa = :cenFechaingresa")
    , @NamedQuery(name = "BikCentro.findByCenUsuariomodifica", query = "SELECT b FROM BikCentro b WHERE b.cenUsuariomodifica = :cenUsuariomodifica")
    , @NamedQuery(name = "BikCentro.findByCenFechamodifica", query = "SELECT b FROM BikCentro b WHERE b.cenFechamodifica = :cenFechamodifica")})
public class BikCentro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cen_codigo")
    private Integer cenCodigo;
    @Basic(optional = false)
    @Column(name = "cen_nombre")
    private String cenNombre;
    @Basic(optional = false)
    @Column(name = "cen_estado")
    private String cenEstado;
    @Column(name = "cen_cedulajuridica")
    private String cenCedulajuridica;
    @Column(name = "cen_logo")
    private String cenLogo;
    @Column(name = "cen_usuarioingresa")
    private String cenUsuarioingresa;
    @Column(name = "cen_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cenFechaingresa;
    @Column(name = "cen_usuariomodifica")
    private String cenUsuariomodifica;
    @Column(name = "cen_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cenFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sedCencodigo", fetch = FetchType.LAZY)
    private List<BikSede> bikSedeList;
    @JoinColumn(name = "cen_codrepresentantelegal", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona cenCodrepresentantelegal;

    public BikCentro() {
    }

    public BikCentro(Integer cenCodigo) {
        this.cenCodigo = cenCodigo;
    }

    public BikCentro(Integer cenCodigo, String cenNombre, String cenEstado) {
        this.cenCodigo = cenCodigo;
        this.cenNombre = cenNombre;
        this.cenEstado = cenEstado;
    }

    public Integer getCenCodigo() {
        return cenCodigo;
    }

    public void setCenCodigo(Integer cenCodigo) {
        this.cenCodigo = cenCodigo;
    }

    public String getCenNombre() {
        return cenNombre;
    }

    public void setCenNombre(String cenNombre) {
        this.cenNombre = cenNombre;
    }

    public String getCenEstado() {
        return cenEstado;
    }

    public void setCenEstado(String cenEstado) {
        this.cenEstado = cenEstado;
    }

    public String getCenCedulajuridica() {
        return cenCedulajuridica;
    }

    public void setCenCedulajuridica(String cenCedulajuridica) {
        this.cenCedulajuridica = cenCedulajuridica;
    }

    public String getCenLogo() {
        return cenLogo;
    }

    public void setCenLogo(String cenLogo) {
        this.cenLogo = cenLogo;
    }

    public String getCenUsuarioingresa() {
        return cenUsuarioingresa;
    }

    public void setCenUsuarioingresa(String cenUsuarioingresa) {
        this.cenUsuarioingresa = cenUsuarioingresa;
    }

    public Date getCenFechaingresa() {
        return cenFechaingresa;
    }

    public void setCenFechaingresa(Date cenFechaingresa) {
        this.cenFechaingresa = cenFechaingresa;
    }

    public String getCenUsuariomodifica() {
        return cenUsuariomodifica;
    }

    public void setCenUsuariomodifica(String cenUsuariomodifica) {
        this.cenUsuariomodifica = cenUsuariomodifica;
    }

    public Date getCenFechamodifica() {
        return cenFechamodifica;
    }

    public void setCenFechamodifica(Date cenFechamodifica) {
        this.cenFechamodifica = cenFechamodifica;
    }

    @XmlTransient
    public List<BikSede> getBikSedeList() {
        return bikSedeList;
    }

    public void setBikSedeList(List<BikSede> bikSedeList) {
        this.bikSedeList = bikSedeList;
    }

    public BikPersona getCenCodrepresentantelegal() {
        return cenCodrepresentantelegal;
    }

    public void setCenCodrepresentantelegal(BikPersona cenCodrepresentantelegal) {
        this.cenCodrepresentantelegal = cenCodrepresentantelegal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cenCodigo != null ? cenCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikCentro)) {
            return false;
        }
        BikCentro other = (BikCentro) object;
        if ((this.cenCodigo == null && other.cenCodigo != null) || (this.cenCodigo != null && !this.cenCodigo.equals(other.cenCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikCentro[ cenCodigo=" + cenCodigo + " ]";
    }
    
}
