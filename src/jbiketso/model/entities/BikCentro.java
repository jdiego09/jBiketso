/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.utils.GenValorCombo;

/**
 *
 * @author Anayansy
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_centro", schema = "biketso")
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
    @Transient
    private Integer cenCodigo;
    @Transient
    private SimpleStringProperty cenNombre;
    @Transient
    private ObjectProperty<GenValorCombo> cenEstado;
    @Transient
    private SimpleStringProperty cenCedulajuridica;
    @Transient
    private SimpleStringProperty cenLogo;
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
    @Transient
    private ObjectProperty<BikPersona> cenCodrepresentantelegal;

    public BikCentro() {
        this.cenNombre = new SimpleStringProperty();
        this.cenEstado = new SimpleObjectProperty();
        this.cenCedulajuridica = new SimpleStringProperty();
        this.cenLogo = new SimpleStringProperty();
        this.cenCodrepresentantelegal = new SimpleObjectProperty();
    }

   public BikCentro(Integer cenCodigo) {
        this.cenCodigo = cenCodigo;
    }

    /*public BikCentro(Integer cenCodigo, String cenNombre, String cenEstado) {
        
        this.cenNombre = new SimpleStringProperty();
        this.cenEstado = new SimpleObjectProperty();
        
        this.cenCodigo = cenCodigo;
        this.cenNombre.set(cenEstado);
        this.cenEstado = cenEstado;
    }*/
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cen_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getCenCodigo() {
        return cenCodigo;
    }

    public void setCenCodigo(Integer cenCodigo) {
        this.cenCodigo = cenCodigo;
    }

    @Basic(optional = false)
    @Column(name = "cen_nombre")
    @Access(AccessType.PROPERTY)
    public String getCenNombre() {
        return cenNombre.get();
    }

    public void setCenNombre(String cenNombre) {
        this.cenNombre.set(cenNombre);
    }

    public SimpleStringProperty getCenNombreProperty() {
        return cenNombre;
    }

    @Basic(optional = false)
    @Column(name = "cen_estado")
    @Access(AccessType.PROPERTY)
    public String getCenEstado() {
        return cenEstado.get().getCodigo();
    }

    public void setCenEstado(String cenEstado) {
        GenValorCombo valorEstado = null;
        if (this.cenEstado == null) {
            this.cenEstado = new SimpleObjectProperty();
        }
        if (cenEstado.equalsIgnoreCase("a")) {
            valorEstado = new GenValorCombo("A", "Activo");
        } else {
            valorEstado = new GenValorCombo("I", "Inactivo");
        }
        this.cenEstado.set(valorEstado);
    }
    
    public ObjectProperty getCenEstadoProperty() {
        return cenEstado;
    }
    
    public String getDescripcionEstado() {
        return this.cenEstado.get().getDescripcion();
    }

    @Column(name = "cen_cedulajuridica")
    @Access(AccessType.PROPERTY)
    public String getCenCedulajuridica() {
        return cenCedulajuridica.get();
    }

    public void setCenCedulajuridica(String cenCedulajuridica) {
        this.cenCedulajuridica.set(cenCedulajuridica);
    }
    
    public SimpleStringProperty getCenCedulajuridicaProperty() {
        return cenCedulajuridica;
    }

    @Column(name = "cen_logo")
    @Access(AccessType.PROPERTY)
    public String getCenLogo() {
        return cenLogo.get();
    }

    public void setCenLogo(String cenLogo) {
        this.cenLogo.set(cenLogo);
    }
    
    public SimpleStringProperty getCenLogoProperty() {
        return cenLogo;
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

    @JoinColumn(name = "cen_codrepresentantelegal", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Access(AccessType.PROPERTY)
    public BikPersona getCenCodrepresentantelegal()  {
        return cenCodrepresentantelegal.get();
    }

    public void setCenCodrepresentantelegal(BikPersona cenCodrepresentantelegal) {
        this.cenCodrepresentantelegal.set(cenCodrepresentantelegal);
    }
    
    public ObjectProperty<BikPersona> getCenCodrepresentantelegalProperty() {
        return cenCodrepresentantelegal;
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
