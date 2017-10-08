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
@Table(name = "bk_roles",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BkRoles.findAll", query = "SELECT b FROM BkRoles b")
    , @NamedQuery(name = "BkRoles.findByRolCodigo", query = "SELECT b FROM BkRoles b WHERE b.rolCodigo = :rolCodigo")
    , @NamedQuery(name = "BkRoles.findByRolDescripcion", query = "SELECT b FROM BkRoles b WHERE b.rolDescripcion = :rolDescripcion")
    , @NamedQuery(name = "BkRoles.findByRolEstado", query = "SELECT b FROM BkRoles b WHERE b.rolEstado = :rolEstado")
    , @NamedQuery(name = "BkRoles.findByRolConsulta", query = "SELECT b FROM BkRoles b WHERE b.rolConsulta = :rolConsulta")
    , @NamedQuery(name = "BkRoles.findByRolInserta", query = "SELECT b FROM BkRoles b WHERE b.rolInserta = :rolInserta")
    , @NamedQuery(name = "BkRoles.findByRolModifica", query = "SELECT b FROM BkRoles b WHERE b.rolModifica = :rolModifica")
    , @NamedQuery(name = "BkRoles.findByRolElimina", query = "SELECT b FROM BkRoles b WHERE b.rolElimina = :rolElimina")
    , @NamedQuery(name = "BkRoles.findByRolPantalla", query = "SELECT b FROM BkRoles b WHERE b.rolPantalla = :rolPantalla")
    , @NamedQuery(name = "BkRoles.findByRolUsuarioingresa", query = "SELECT b FROM BkRoles b WHERE b.rolUsuarioingresa = :rolUsuarioingresa")
    , @NamedQuery(name = "BkRoles.findByRolFechaingresa", query = "SELECT b FROM BkRoles b WHERE b.rolFechaingresa = :rolFechaingresa")
    , @NamedQuery(name = "BkRoles.findByRolUsuariomodifica", query = "SELECT b FROM BkRoles b WHERE b.rolUsuariomodifica = :rolUsuariomodifica")
    , @NamedQuery(name = "BkRoles.findByRolFechamodifica", query = "SELECT b FROM BkRoles b WHERE b.rolFechamodifica = :rolFechamodifica")})
public class BkRoles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "rol_codigo")
    private String rolCodigo;
    @Basic(optional = false)
    @Column(name = "rol_descripcion")
    private String rolDescripcion;
    @Basic(optional = false)
    @Column(name = "rol_estado")
    private String rolEstado;
    @Basic(optional = false)
    @Column(name = "rol_consulta")
    private String rolConsulta;
    @Basic(optional = false)
    @Column(name = "rol_inserta")
    private String rolInserta;
    @Basic(optional = false)
    @Column(name = "rol_modifica")
    private String rolModifica;
    @Basic(optional = false)
    @Column(name = "rol_elimina")
    private String rolElimina;
    @Column(name = "rol_pantalla")
    private String rolPantalla;
    @Column(name = "rol_usuarioingresa")
    private String rolUsuarioingresa;
    @Column(name = "rol_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolFechaingresa;
    @Column(name = "rol_usuariomodifica")
    private String rolUsuariomodifica;
    @Column(name = "rol_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolFechamodifica;
    @JoinColumn(name = "rol_modcodigo", referencedColumnName = "mod_codigo")
    @ManyToOne(fetch = FetchType.LAZY)
    private BikModulos rolModcodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bkRoles", fetch = FetchType.LAZY)
    private List<BikRolesUsuarios> bikRolesUsuariosList;

    public BkRoles() {
    }

    public BkRoles(String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public BkRoles(String rolCodigo, String rolDescripcion, String rolEstado, String rolConsulta, String rolInserta, String rolModifica, String rolElimina) {
        this.rolCodigo = rolCodigo;
        this.rolDescripcion = rolDescripcion;
        this.rolEstado = rolEstado;
        this.rolConsulta = rolConsulta;
        this.rolInserta = rolInserta;
        this.rolModifica = rolModifica;
        this.rolElimina = rolElimina;
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getRolDescripcion() {
        return rolDescripcion;
    }

    public void setRolDescripcion(String rolDescripcion) {
        this.rolDescripcion = rolDescripcion;
    }

    public String getRolEstado() {
        return rolEstado;
    }

    public void setRolEstado(String rolEstado) {
        this.rolEstado = rolEstado;
    }

    public String getRolConsulta() {
        return rolConsulta;
    }

    public void setRolConsulta(String rolConsulta) {
        this.rolConsulta = rolConsulta;
    }

    public String getRolInserta() {
        return rolInserta;
    }

    public void setRolInserta(String rolInserta) {
        this.rolInserta = rolInserta;
    }

    public String getRolModifica() {
        return rolModifica;
    }

    public void setRolModifica(String rolModifica) {
        this.rolModifica = rolModifica;
    }

    public String getRolElimina() {
        return rolElimina;
    }

    public void setRolElimina(String rolElimina) {
        this.rolElimina = rolElimina;
    }

    public String getRolPantalla() {
        return rolPantalla;
    }

    public void setRolPantalla(String rolPantalla) {
        this.rolPantalla = rolPantalla;
    }

    public String getRolUsuarioingresa() {
        return rolUsuarioingresa;
    }

    public void setRolUsuarioingresa(String rolUsuarioingresa) {
        this.rolUsuarioingresa = rolUsuarioingresa;
    }

    public Date getRolFechaingresa() {
        return rolFechaingresa;
    }

    public void setRolFechaingresa(Date rolFechaingresa) {
        this.rolFechaingresa = rolFechaingresa;
    }

    public String getRolUsuariomodifica() {
        return rolUsuariomodifica;
    }

    public void setRolUsuariomodifica(String rolUsuariomodifica) {
        this.rolUsuariomodifica = rolUsuariomodifica;
    }

    public Date getRolFechamodifica() {
        return rolFechamodifica;
    }

    public void setRolFechamodifica(Date rolFechamodifica) {
        this.rolFechamodifica = rolFechamodifica;
    }

    public BikModulos getRolModcodigo() {
        return rolModcodigo;
    }

    public void setRolModcodigo(BikModulos rolModcodigo) {
        this.rolModcodigo = rolModcodigo;
    }

    @XmlTransient
    public List<BikRolesUsuarios> getBikRolesUsuariosList() {
        return bikRolesUsuariosList;
    }

    public void setBikRolesUsuariosList(List<BikRolesUsuarios> bikRolesUsuariosList) {
        this.bikRolesUsuariosList = bikRolesUsuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolCodigo != null ? rolCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BkRoles)) {
            return false;
        }
        BkRoles other = (BkRoles) object;
        if ((this.rolCodigo == null && other.rolCodigo != null) || (this.rolCodigo != null && !this.rolCodigo.equals(other.rolCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BkRoles[ rolCodigo=" + rolCodigo + " ]";
    }
    
}
