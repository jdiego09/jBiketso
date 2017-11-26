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
import javafx.beans.property.SimpleIntegerProperty;
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

/**
 *
 * @author Anayansy
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_sede", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikSede.findAll", query = "SELECT b FROM BikSede b")
    , @NamedQuery(name = "BikSede.findBySedCodigo", query = "SELECT b FROM BikSede b WHERE b.sedCodigo = :sedCodigo")
    , @NamedQuery(name = "BikSede.findBySedNombre", query = "SELECT b FROM BikSede b WHERE b.sedNombre = :sedNombre")
    , @NamedQuery(name = "BikSede.findByCodigoCentro", query = "SELECT s FROM BikSede s join s.sedCencodigo c WHERE c.cenCodigo = :codigoCentro")})
public class BikSede implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private SimpleIntegerProperty sedCodigo;
    @Transient
    private SimpleStringProperty sedNombre;
    @Transient
    private SimpleStringProperty sedDescripcion;
    @Transient
    private SimpleStringProperty sedTelefonos;
    @Transient
    private SimpleStringProperty sedFax;
    @Transient
    private SimpleStringProperty sedEmail;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pueSedcodigo", fetch = FetchType.LAZY)
    private List<BikPuesto> bikPuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuSedcodigo", fetch = FetchType.LAZY)
    private List<BikUsuario> bikUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ageSedcodigo", fetch = FetchType.LAZY)
    private List<BikAgenda> bikAgendaList;

    public BikSede() {
        this.sedNombre = new SimpleStringProperty();
        this.sedDescripcion = new SimpleStringProperty();
        this.sedTelefonos = new SimpleStringProperty();
        this.sedFax = new SimpleStringProperty();
        this.sedEmail = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sed_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getSedCodigo() {
        if (this.sedCodigo == null) {
            this.sedCodigo = new SimpleIntegerProperty();
        }
        return sedCodigo.get();
    }

    public void setSedCodigo(Integer sedCodigo) {
        if (this.sedCodigo == null) {
            this.sedCodigo = new SimpleIntegerProperty();
        }
        this.sedCodigo.set(sedCodigo);
    }

    public SimpleIntegerProperty getSedCodigoProperty() {
        if (this.sedCodigo == null) {
            this.sedCodigo = new SimpleIntegerProperty();
        }
        return this.sedCodigo;
    }

    @Basic(optional = false)
    @Column(name = "sed_nombre")
    @Access(AccessType.PROPERTY)
    public String getSedNombre() {
        if (this.sedNombre == null) {
            this.sedNombre = new SimpleStringProperty();
        }
        return sedNombre.get();
    }

    public void setSedNombre(String sedNombre) {
        if (this.sedNombre == null) {
            this.sedNombre = new SimpleStringProperty();
        }
        this.sedNombre.set(sedNombre);
    }

    public SimpleStringProperty getSedNombreProperty() {
        if (this.sedNombre == null) {
            this.sedNombre = new SimpleStringProperty();
        }
        return sedNombre;
    }

    @Basic(optional = false)
    @Column(name = "sed_descripcion")
    @Access(AccessType.PROPERTY)
    public String getSedDescripcion() {
        if (this.sedDescripcion == null) {
            this.sedDescripcion = new SimpleStringProperty();
        }
        return sedDescripcion.get();
    }

    public void setSedDescripcion(String sedDescripcion) {
        if (this.sedDescripcion == null) {
            this.sedDescripcion = new SimpleStringProperty();
        }
        this.sedDescripcion.set(sedDescripcion);
    }

    public SimpleStringProperty getSedDescripcionProperty() {
        if (this.sedDescripcion == null) {
            this.sedDescripcion = new SimpleStringProperty();
        }
        return sedDescripcion;
    }

    @Basic(optional = false)
    @Column(name = "sed_telefonos")
    @Access(AccessType.PROPERTY)
    public String getSedTelefonos() {
        if (this.sedTelefonos == null) {
            this.sedTelefonos = new SimpleStringProperty();
        }
        return sedTelefonos.get();
    }

    public void setSedTelefonos(String sedTelefonos) {
        if (this.sedTelefonos == null) {
            this.sedTelefonos = new SimpleStringProperty();
        }
        this.sedTelefonos.set(sedTelefonos);
    }

    public SimpleStringProperty getSedTelefonosProperty() {
        if (this.sedTelefonos == null) {
            this.sedTelefonos = new SimpleStringProperty();
        }
        return sedTelefonos;
    }

    @Column(name = "sed_fax")
    @Access(AccessType.PROPERTY)
    public String getSedFax() {
        if (this.sedFax == null) {
            this.sedFax = new SimpleStringProperty();
        }
        return sedFax.get();
    }

    public void setSedFax(String sedFax) {
        if (this.sedFax == null) {
            this.sedFax = new SimpleStringProperty();
        }
        this.sedFax.set(sedFax);
    }

    public SimpleStringProperty getSedFaxProperty() {
        if (this.sedFax == null) {
            this.sedFax = new SimpleStringProperty();
        }
        return sedFax;
    }

    @Column(name = "sed_email")
    @Access(AccessType.PROPERTY)
    public String getSedEmail() {
        if (this.sedEmail == null) {
            this.sedEmail = new SimpleStringProperty();
        }
        return sedEmail.get();
    }

    public void setSedEmail(String sedEmail) {
        if (this.sedEmail == null) {
            this.sedEmail = new SimpleStringProperty();
        }
        this.sedEmail.set(sedEmail);
    }

    public SimpleStringProperty getSedEmailProperty() {
        if (this.sedEmail == null) {
            this.sedEmail = new SimpleStringProperty();
        }
        return sedEmail;
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
