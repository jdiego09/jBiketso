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
 * @author jdiego
 */
@Entity
@Table(name = "bik_permiso_rol", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikPermisoRol.findById", query = "SELECT b FROM BikPermisoRol b WHERE b.id = :id")
    , @NamedQuery(name = "BikPermisoRol.findModulosByRol", query = "select distinct b.proCodigomenu.menModcodigo.modCodigo, b.proCodigomenu.menModcodigo.modDescripcion from BikPermisoRol b\n"
            + "where b.proCodigorol.rolCodigo in :codigoRol and b.proCodigomenu.menModcodigo.modEstado = 'A'")
    , @NamedQuery(name = "BikPermisoRol.findMenuByRol", query = "select distinct b.proCodigomenu, b.proConsulta, b.proInserta, b.proModifica, b.proElimina from BikPermisoRol b\n"
            + "where b.proCodigorol.rolCodigo in :codigoRol\n"
            + "and b.proCodigomenu.menEstado = 'A'")
})
public class BikPermisoRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "pro_codigomenu", referencedColumnName = "men_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikMenu proCodigomenu;
    @JoinColumn(name = "pro_codigorol", referencedColumnName = "rol_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikRoles proCodigorol;

    public BikPermisoRol() {
    }

    public BikPermisoRol(Integer id) {
        this.id = id;
    }

    public BikPermisoRol(Integer id, String proConsulta, String proInserta, String proModifica, String proElimina) {
        this.id = id;
        this.proConsulta = proConsulta;
        this.proInserta = proInserta;
        this.proModifica = proModifica;
        this.proElimina = proElimina;
    }

    public BikPermisoRol(BikMenu proCodigomenu, String proConsulta, String proInserta, String proModifica, String proElimina) {
        this.proConsulta = proConsulta;
        this.proInserta = proInserta;
        this.proModifica = proModifica;
        this.proElimina = proElimina;
        this.proCodigomenu = proCodigomenu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BikMenu getProCodigomenu() {
        return proCodigomenu;
    }

    public void setProCodigomenu(BikMenu proCodigomenu) {
        this.proCodigomenu = proCodigomenu;
    }

    public BikRoles getProCodigorol() {
        return proCodigorol;
    }

    public void setProCodigorol(BikRoles proCodigorol) {
        this.proCodigorol = proCodigorol;
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
