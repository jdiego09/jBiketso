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
@Table(name = "bik_persona")
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "per_codigo")
    private Integer perCodigo;
    @Basic(optional = false)
    @Column(name = "per_cedula")
    private String perCedula;
    @Basic(optional = false)
    @Column(name = "per_nombres")
    private String perNombres;
    @Basic(optional = false)
    @Column(name = "per_primerapellido")
    private String perPrimerapellido;
    @Basic(optional = false)
    @Column(name = "per_segundoapellido")
    private String perSegundoapellido;
    @Basic(optional = false)
    @Column(name = "per_fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date perFechanacimiento;
    @Basic(optional = false)
    @Column(name = "per_genero")
    private String perGenero;
    @Column(name = "per_nacionalidad")
    private String perNacionalidad;
    @Column(name = "per_estadocivil")
    private String perEstadocivil;
    @Column(name = "per_profesion")
    private String perProfesion;
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
    }

    public BikPersona(Integer perCodigo) {
        this.perCodigo = perCodigo;
    }

    public BikPersona(Integer perCodigo, String perCedula, String perNombres, String perPrimerapellido, String perSegundoapellido, Date perFechanacimiento, String perGenero) {
        this.perCodigo = perCodigo;
        this.perCedula = perCedula;
        this.perNombres = perNombres;
        this.perPrimerapellido = perPrimerapellido;
        this.perSegundoapellido = perSegundoapellido;
        this.perFechanacimiento = perFechanacimiento;
        this.perGenero = perGenero;
    }

    public Integer getPerCodigo() {
        return perCodigo;
    }

    public void setPerCodigo(Integer perCodigo) {
        this.perCodigo = perCodigo;
    }

    public String getPerCedula() {
        return perCedula;
    }

    public void setPerCedula(String perCedula) {
        this.perCedula = perCedula;
    }

    public String getPerNombres() {
        return perNombres;
    }

    public void setPerNombres(String perNombres) {
        this.perNombres = perNombres;
    }

    public String getPerPrimerapellido() {
        return perPrimerapellido;
    }

    public void setPerPrimerapellido(String perPrimerapellido) {
        this.perPrimerapellido = perPrimerapellido;
    }

    public String getPerSegundoapellido() {
        return perSegundoapellido;
    }

    public void setPerSegundoapellido(String perSegundoapellido) {
        this.perSegundoapellido = perSegundoapellido;
    }

    public Date getPerFechanacimiento() {
        return perFechanacimiento;
    }

    public void setPerFechanacimiento(Date perFechanacimiento) {
        this.perFechanacimiento = perFechanacimiento;
    }

    public String getPerGenero() {
        return perGenero;
    }

    public void setPerGenero(String perGenero) {
        this.perGenero = perGenero;
    }

    public String getPerNacionalidad() {
        return perNacionalidad;
    }

    public void setPerNacionalidad(String perNacionalidad) {
        this.perNacionalidad = perNacionalidad;
    }

    public String getPerEstadocivil() {
        return perEstadocivil;
    }

    public void setPerEstadocivil(String perEstadocivil) {
        this.perEstadocivil = perEstadocivil;
    }

    public String getPerProfesion() {
        return perProfesion;
    }

    public void setPerProfesion(String perProfesion) {
        this.perProfesion = perProfesion;
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
    
}
