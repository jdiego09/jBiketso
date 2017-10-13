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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "bik_permiso_rol", schema="biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikPermisoRol.findAll", query = "SELECT b FROM BikPermisoRol b")
    , @NamedQuery(name = "BikPermisoRol.findById", query = "SELECT b FROM BikPermisoRol b WHERE b.id = :id")
    , @NamedQuery(name = "BikPermisoRol.findByProCodigorol", query = "SELECT b FROM BikPermisoRol b WHERE b.proCodigorol = :proCodigorol")
    , @NamedQuery(name = "BikPermisoRol.findByProCodigomodulo", query = "SELECT b FROM BikPermisoRol b WHERE b.proCodigomodulo = :proCodigomodulo")
    , @NamedQuery(name = "BikPermisoRol.findByProPantalla", query = "SELECT b FROM BikPermisoRol b WHERE b.proPantalla = :proPantalla")
    , @NamedQuery(name = "BikPermisoRol.findByProConsulta", query = "SELECT b FROM BikPermisoRol b WHERE b.proConsulta = :proConsulta")
    , @NamedQuery(name = "BikPermisoRol.findByProInserta", query = "SELECT b FROM BikPermisoRol b WHERE b.proInserta = :proInserta")
    , @NamedQuery(name = "BikPermisoRol.findByProModifica", query = "SELECT b FROM BikPermisoRol b WHERE b.proModifica = :proModifica")
    , @NamedQuery(name = "BikPermisoRol.findByProElimina", query = "SELECT b FROM BikPermisoRol b WHERE b.proElimina = :proElimina")
    , @NamedQuery(name = "BikPermisoRol.findByProUsuarioingresa", query = "SELECT b FROM BikPermisoRol b WHERE b.proUsuarioingresa = :proUsuarioingresa")
    , @NamedQuery(name = "BikPermisoRol.findByProFechaingresa", query = "SELECT b FROM BikPermisoRol b WHERE b.proFechaingresa = :proFechaingresa")
    , @NamedQuery(name = "BikPermisoRol.findByProUsuariomodifica", query = "SELECT b FROM BikPermisoRol b WHERE b.proUsuariomodifica = :proUsuariomodifica")
    , @NamedQuery(name = "BikPermisoRol.findByProFechamodifica", query = "SELECT b FROM BikPermisoRol b WHERE b.proFechamodifica = :proFechamodifica")})
public class BikPermisoRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "pro_codigorol")
    private String proCodigorol;
    @Basic(optional = false)
    @Column(name = "pro_codigomodulo")
    private String proCodigomodulo;
    @Basic(optional = false)
    @Column(name = "pro_pantalla")
    private String proPantalla;
    @Basic(optional = false)
    @Column(name = "pro_consulta")
    private String proConsulta;
    @Basic(optional = false)
    @Column(name = "pro_inserta")
    private String proInserta;
    @Basic(optional = false)
    @Column(name = "pro_modifica")
    private String proModifica;
    @Basic(optional = false)
    @Column(name = "pro_elimina")
    private String proElimina;
    @Column(name = "pro_usuarioingresa")
    private String proUsuarioingresa;
    @Column(name = "pro_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proFechaingresa;
    @Column(name = "pro_usuariomodifica")
    private String proUsuariomodifica;
    @Column(name = "pro_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proFechamodifica;

    public BikPermisoRol() {
    }

    public BikPermisoRol(Integer id) {
        this.id = id;
    }

    public BikPermisoRol(Integer id, String proCodigorol, String proCodigomodulo, String proPantalla, String proConsulta, String proInserta, String proModifica, String proElimina) {
        this.id = id;
        this.proCodigorol = proCodigorol;
        this.proCodigomodulo = proCodigomodulo;
        this.proPantalla = proPantalla;
        this.proConsulta = proConsulta;
        this.proInserta = proInserta;
        this.proModifica = proModifica;
        this.proElimina = proElimina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProCodigorol() {
        return proCodigorol;
    }

    public void setProCodigorol(String proCodigorol) {
        this.proCodigorol = proCodigorol;
    }

    public String getProCodigomodulo() {
        return proCodigomodulo;
    }

    public void setProCodigomodulo(String proCodigomodulo) {
        this.proCodigomodulo = proCodigomodulo;
    }

    public String getProPantalla() {
        return proPantalla;
    }

    public void setProPantalla(String proPantalla) {
        this.proPantalla = proPantalla;
    }

    public String getProConsulta() {
        return proConsulta;
    }

    public void setProConsulta(String proConsulta) {
        this.proConsulta = proConsulta;
    }

    public String getProInserta() {
        return proInserta;
    }

    public void setProInserta(String proInserta) {
        this.proInserta = proInserta;
    }

    public String getProModifica() {
        return proModifica;
    }

    public void setProModifica(String proModifica) {
        this.proModifica = proModifica;
    }

    public String getProElimina() {
        return proElimina;
    }

    public void setProElimina(String proElimina) {
        this.proElimina = proElimina;
    }

    public String getProUsuarioingresa() {
        return proUsuarioingresa;
    }

    public void setProUsuarioingresa(String proUsuarioingresa) {
        this.proUsuarioingresa = proUsuarioingresa;
    }

    public Date getProFechaingresa() {
        return proFechaingresa;
    }

    public void setProFechaingresa(Date proFechaingresa) {
        this.proFechaingresa = proFechaingresa;
    }

    public String getProUsuariomodifica() {
        return proUsuariomodifica;
    }

    public void setProUsuariomodifica(String proUsuariomodifica) {
        this.proUsuariomodifica = proUsuariomodifica;
    }

    public Date getProFechamodifica() {
        return proFechamodifica;
    }

    public void setProFechamodifica(Date proFechamodifica) {
        this.proFechamodifica = proFechamodifica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikPermisoRol)) {
            return false;
        }
        BikPermisoRol other = (BikPermisoRol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.entities.BikPermisoRol[ id=" + id + " ]";
    }
    
}
