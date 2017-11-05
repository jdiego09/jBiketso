/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.PersonaDao;
import jbiketso.utils.GenValorCombo;

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_persona", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikPersona.findAll", query = "SELECT b FROM BikPersona b")
    , @NamedQuery(name = "BikPersona.findByPerCodigo", query = "SELECT b FROM BikPersona b WHERE b.perCodigo = :perCodigo")
    , @NamedQuery(name = "BikPersona.findByPerCedula", query = "SELECT b FROM BikPersona b WHERE b.perCedula = :perCedula")
    , @NamedQuery(name = "BikPersona.findByPerNombres", query = "SELECT b FROM BikPersona b WHERE b.perNombres = :perNombres")
    , @NamedQuery(name = "BikPersona.findByPerPrimerapellido", query = "SELECT b FROM BikPersona b WHERE b.perPrimerapellido = :perPrimerapellido")
    , @NamedQuery(name = "BikPersona.findByPerSegundoapellido", query = "SELECT b FROM BikPersona b WHERE b.perSegundoapellido = :perSegundoapellido")
    , @NamedQuery(name = "BikPersona.findByPerFechanacimiento", query = "SELECT b FROM BikPersona b WHERE b.perFechanacimiento = :perFechanacimiento")
    , @NamedQuery(name = "BikPersona.findByPerGenero", query = "SELECT b FROM BikPersona b WHERE b.perGenero = :perGenero")
    , @NamedQuery(name = "BikPersona.findByPerNacionalidad", query = "SELECT b FROM BikPersona b WHERE b.perNacionalidad = :perNacionalidad")
    , @NamedQuery(name = "BikPersona.findByPerEstadocivil", query = "SELECT b FROM BikPersona b WHERE b.perEstadocivil = :perEstadocivil")
    , @NamedQuery(name = "BikPersona.findByPerProfesion", query = "SELECT b FROM BikPersona b WHERE b.perProfesion = :perProfesion")
    , @NamedQuery(name = "BikPersona.findByPerUsuarioingresa", query = "SELECT b FROM BikPersona b WHERE b.perUsuarioingresa = :perUsuarioingresa")
    , @NamedQuery(name = "BikPersona.findByPerFechaingresa", query = "SELECT b FROM BikPersona b WHERE b.perFechaingresa = :perFechaingresa")
    , @NamedQuery(name = "BikPersona.findByPerUsuariomodifica", query = "SELECT b FROM BikPersona b WHERE b.perUsuariomodifica = :perUsuariomodifica")
    , @NamedQuery(name = "BikPersona.findByPerFechamodifica", query = "SELECT b FROM BikPersona b WHERE b.perFechamodifica = :perFechamodifica")})
public class BikPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private Integer perCodigo;
    @Transient
    private SimpleStringProperty perCedula;
    @Transient
    private SimpleStringProperty perNombres;
    @Transient
    private SimpleStringProperty perPrimerapellido;
    @Transient
    private SimpleStringProperty perSegundoapellido;
    @Transient
    private SimpleObjectProperty<LocalDate> perFechanacimiento;
    @Transient
    private ObjectProperty<GenValorCombo> perGenero;
    @Transient
    private SimpleStringProperty perNacionalidad;
    @Transient
    private ObjectProperty<GenValorCombo> perEstadocivil;
    @Transient
    private SimpleStringProperty perProfesion;

    @Column(name = "per_usuarioingresa")
    private String perUsuarioingresa;
    @Column(name = "per_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaingresa;
    @Column(name = "per_usuariomodifica")
    private String perUsuariomodifica;
    @Column(name = "per_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechamodifica;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dirPercodigo", fetch = FetchType.LAZY)
    private List<BikDireccion> bikDireccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funPercodigo", fetch = FetchType.LAZY)
    private List<BikFuncionario> bikFuncionarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sedCodencargado", fetch = FetchType.LAZY)
    private List<BikSede> bikSedeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expCodencargado", fetch = FetchType.LAZY)
    private List<BikExpediente> bikExpedienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cenCodrepresentantelegal", fetch = FetchType.LAZY)
    private List<BikCentro> bikCentroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conPercodigo", fetch = FetchType.LAZY)
    private List<BikContacto> bikContactoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuCodencargadolegal", fetch = FetchType.LAZY)
    private List<BikUsuario> bikUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuPercodigo", fetch = FetchType.LAZY)
    private List<BikUsuario> bikUsuarioList1;

    public BikPersona() {
        this.perCodigo = null;
        this.perCedula = new SimpleStringProperty();
        this.perNombres = new SimpleStringProperty();
        this.perPrimerapellido = new SimpleStringProperty();
        this.perSegundoapellido = new SimpleStringProperty();
        this.perFechanacimiento = new SimpleObjectProperty();
        this.perGenero = new SimpleObjectProperty();
        this.perNacionalidad = new SimpleStringProperty();
        this.perEstadocivil = new SimpleObjectProperty();
        this.perProfesion = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "per_codigo", nullable = false)
    @Access(AccessType.PROPERTY)
    public Integer getPerCodigo() {
        return perCodigo;
    }

    public void setPerCodigo(Integer perCodigo) {
        this.perCodigo = perCodigo;
    }

    @Basic(optional = false)
    @Column(name = "per_cedula")
    @Access(AccessType.PROPERTY)
    public String getPerCedula() {
        return perCedula.get();
    }

    public void setPerCedula(String perCedula) {
        this.perCedula.set(perCedula);
    }

    public SimpleStringProperty getPerCedulaProperty() {
        if (this.perCedula == null) {
            this.perCedula = new SimpleStringProperty();
        }
        return this.perCedula;
    }

    @Basic(optional = false)
    @Column(name = "per_nombres")
    @Access(AccessType.PROPERTY)
    public String getPerNombres() {
        return perNombres.get();
    }

    public void setPerNombres(String perNombres) {
        this.perNombres.set(perNombres);
    }

    public SimpleStringProperty getPerNombresProperty() {
        if (this.perNombres == null) {
            this.perNombres = new SimpleStringProperty();
        }
        return this.perNombres;
    }

    @Basic(optional = false)
    @Column(name = "per_primerapellido")
    @Access(AccessType.PROPERTY)
    public String getPerPrimerapellido() {
        return perPrimerapellido.get();
    }

    public void setPerPrimerapellido(String perPrimerapellido) {
        this.perPrimerapellido.set(perPrimerapellido);
    }

    public SimpleStringProperty getPerPrimerapellidoProperty() {
        if (this.perPrimerapellido == null) {
            this.perPrimerapellido = new SimpleStringProperty();
        }
        return this.perPrimerapellido;
    }

    @Basic(optional = false)
    @Column(name = "per_segundoapellido")
    @Access(AccessType.PROPERTY)
    public String getPerSegundoapellido() {
        return perSegundoapellido.get();
    }

    public void setPerSegundoapellido(String perSegundoapellido) {
        this.perSegundoapellido.set(perSegundoapellido);
    }

    public SimpleStringProperty getPerSegundoapellidoProperty() {
        if (this.perSegundoapellido == null) {
            this.perSegundoapellido = new SimpleStringProperty();
        }
        return this.perSegundoapellido;
    }

    @Basic(optional = false)
    @Column(name = "per_fechanacimiento")
    @Temporal(TemporalType.DATE)
    @Access(AccessType.PROPERTY)
    public Date getPerFechanacimiento() {
        return java.sql.Date.valueOf(perFechanacimiento.get());
    }

    public void setPerFechanacimiento(Date perFechanacimiento) {
        this.perFechanacimiento.set(new java.sql.Date(perFechanacimiento.getTime()).toLocalDate());
    }

    public SimpleObjectProperty getPerFechanacimientoProperty() {
        if (this.perFechanacimiento == null) {
            this.perFechanacimiento = new SimpleObjectProperty();
        }
        return this.perFechanacimiento;
    }

    @Basic(optional = false)
    @Column(name = "per_genero")
    @Access(AccessType.PROPERTY)
    public String getPerGenero() {
        return perGenero.get().getCodigo();
    }

    public void setPerGenero(String perGenero) {
        GenValorCombo valorGenero = null;
        if (this.perGenero == null) {
            this.perGenero = new SimpleObjectProperty();
        }
        if (perGenero.equalsIgnoreCase("m")) {
            valorGenero = new GenValorCombo("M", "Masculino");
        } else {
            valorGenero = new GenValorCombo("F", "Femenino");
        }
        this.perGenero.set(valorGenero);
    }

    public ObjectProperty getPerGeneroProperty() {
        if (this.perGenero == null) {
            this.perGenero = new SimpleObjectProperty();
        }
        return this.perGenero;
    }

    @Column(name = "per_nacionalidad")
    @Access(AccessType.PROPERTY)
    public String getPerNacionalidad() {
        return perNacionalidad.get();
    }

    public void setPerNacionalidad(String perNacionalidad) {
        this.perNacionalidad.set(perNacionalidad);
    }

    public SimpleStringProperty getPerNacionalidadProperty() {
        if (this.perNacionalidad == null) {
            this.perNacionalidad = new SimpleStringProperty();
        }
        return this.perNacionalidad;
    }

    @Column(name = "per_estadocivil")
    @Access(AccessType.PROPERTY)
    public String getPerEstadocivil() {
        return perEstadocivil.getName();
    }

    public void setPerEstadocivil(String perEstadocivil) {
        GenValorCombo valorEstadoCivil = null;
        if (this.perEstadocivil == null) {
            this.perEstadocivil = new SimpleObjectProperty();
        }
        if (perEstadocivil.equalsIgnoreCase("s")) {
            valorEstadoCivil = new GenValorCombo("S", "Soltero");
        } else if (perEstadocivil.equalsIgnoreCase("c")) {
            valorEstadoCivil = new GenValorCombo("C", "Casado");
        } else if (perEstadocivil.equalsIgnoreCase("d")) {
            valorEstadoCivil = new GenValorCombo("D", "Divorsiado");
        } else if (perEstadocivil.equalsIgnoreCase("u")) {
            valorEstadoCivil = new GenValorCombo("U", "Unión libre");
        } else if (perEstadocivil.equalsIgnoreCase("o")) {
            valorEstadoCivil = new GenValorCombo("O", "Otro");
        }
        this.perEstadocivil.set(valorEstadoCivil);
    }

    public ObjectProperty getPerEstadocivilProperty() {
        if (this.perEstadocivil == null) {
            this.perEstadocivil = new SimpleObjectProperty();
        }
        return this.perEstadocivil;
    }

    @Column(name = "per_profesion")
    @Access(AccessType.PROPERTY)
    public String getPerProfesion() {
        return perProfesion.get();
    }

    public void setPerProfesion(String perProfesion) {
        this.perProfesion.set(perProfesion);
    }

    public SimpleStringProperty getPerProfesionProperty() {
        if (this.perProfesion == null) {
            this.perProfesion = new SimpleStringProperty();
        }
        return this.perProfesion;
    }

    public String getPerUsuarioingresa() {
        return perUsuarioingresa;
    }

    public void setPerUsuarioingresa(String perUsuarioingresa) {
        this.perUsuarioingresa = perUsuarioingresa;
    }

    public Date getPerFechaingresa() {
        return perFechaingresa;
    }

    public void setPerFechaingresa(Date perFechaingresa) {
        this.perFechaingresa = perFechaingresa;
    }

    public String getPerUsuariomodifica() {
        return perUsuariomodifica;
    }

    public void setPerUsuariomodifica(String perUsuariomodifica) {
        this.perUsuariomodifica = perUsuariomodifica;
    }

    public Date getPerFechamodifica() {
        return perFechamodifica;
    }

    public void setPerFechamodifica(Date perFechamodifica) {
        this.perFechamodifica = perFechamodifica;
    }

    @XmlTransient
    public List<BikDireccion> getBikDireccionList() {
        return bikDireccionList;
    }

    public void setBikDireccionList(List<BikDireccion> bikDireccionList) {
        this.bikDireccionList = bikDireccionList;
    }

    @XmlTransient
    public List<BikFuncionario> getBikFuncionarioList() {
        return bikFuncionarioList;
    }

    public void setBikFuncionarioList(List<BikFuncionario> bikFuncionarioList) {
        this.bikFuncionarioList = bikFuncionarioList;
    }

    @XmlTransient
    public List<BikSede> getBikSedeList() {
        return bikSedeList;
    }

    public void setBikSedeList(List<BikSede> bikSedeList) {
        this.bikSedeList = bikSedeList;
    }

    @XmlTransient
    public List<BikExpediente> getBikExpedienteList() {
        return bikExpedienteList;
    }

    public void setBikExpedienteList(List<BikExpediente> bikExpedienteList) {
        this.bikExpedienteList = bikExpedienteList;
    }

    @XmlTransient
    public List<BikCentro> getBikCentroList() {
        return bikCentroList;
    }

    public void setBikCentroList(List<BikCentro> bikCentroList) {
        this.bikCentroList = bikCentroList;
    }

    @XmlTransient
    public List<BikContacto> getBikContactoList() {
        return bikContactoList;
    }

    public void setBikContactoList(List<BikContacto> bikContactoList) {
        this.bikContactoList = bikContactoList;
    }

    @XmlTransient
    public List<BikUsuario> getBikUsuarioList() {
        return bikUsuarioList;
    }

    public void setBikUsuarioList(List<BikUsuario> bikUsuarioList) {
        this.bikUsuarioList = bikUsuarioList;
    }

    @XmlTransient
    public List<BikUsuario> getBikUsuarioList1() {
        return bikUsuarioList1;
    }

    public void setBikUsuarioList1(List<BikUsuario> bikUsuarioList1) {
        this.bikUsuarioList1 = bikUsuarioList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perCodigo != null ? perCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikPersona)) {
            return false;
        }
        BikPersona other = (BikPersona) object;
        if ((this.perCodigo == null && other.perCodigo != null) || (this.perCodigo != null && !this.perCodigo.equals(other.perCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikPersona[ perCodigo=" + perCodigo + " ]";
    }

    public String getNombreCompleto() {
        return this.perNombres + " " + this.perPrimerapellido + " " + this.perSegundoapellido;
    }

}
