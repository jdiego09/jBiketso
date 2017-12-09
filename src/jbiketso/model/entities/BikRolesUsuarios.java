/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Luis Diego
 */
@Entity
@Table(name = "bik_roles_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikRolesUsuarios.findAll", query = "SELECT b FROM BikRolesUsuarios b")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouEstado", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouEstado = :rouEstado")
    , @NamedQuery(name = "BikRolesUsuarios.findByRouCodigo", query = "SELECT b FROM BikRolesUsuarios b WHERE b.rouCodigo = :rouCodigo")
    , @NamedQuery(name = "BikRolesUsuarios.findByCodigoUsuario", query = "SELECT r FROM BikRolesUsuarios r  JOIN r.rouUsscodigo u WHERE u.ussCodigo = :codUsuario")})
public class BikRolesUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "rou_estado")
    private String rouEstado;
    @Column(name = "rou_usuarioingresa")
    private String rouUsuarioingresa;
    @Column(name = "rou_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rouFechaingresa;
    @Column(name = "rou_usuariomodifica")
    private String rouUsuariomodifica;
    @Column(name = "rou_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rouFechamodifica;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rou_codigo")
    private Integer rouCodigo;
    @JoinColumn(name = "rou_rolcodigo", referencedColumnName = "rol_codigo")
    @ManyToOne(optional = false)
    private BikRoles rouRolcodigo;
    @JoinColumn(name = "rou_usscodigo", referencedColumnName = "uss_codigo")
    @ManyToOne(optional = false)
    private BikUsuariosSistema rouUsscodigo;

    public BikRolesUsuarios() {
    }

    public BikRolesUsuarios(BikUsuariosSistema usuario, BikRoles rol) {
        this.rouUsscodigo = usuario;
        this.rouRolcodigo = rol;
    }

    public String getRouEstado() {
        return rouEstado;
    }

    public void setRouEstado(String rouEstado) {
        this.rouEstado = rouEstado;
    }

    public String getRouUsuarioingresa() {
        return rouUsuarioingresa;
    }

    public void setRouUsuarioingresa(String rouUsuarioingresa) {
        this.rouUsuarioingresa = rouUsuarioingresa;
    }

    public Date getRouFechaingresa() {
        return rouFechaingresa;
    }

    public void setRouFechaingresa(Date rouFechaingresa) {
        this.rouFechaingresa = rouFechaingresa;
    }

    public String getRouUsuariomodifica() {
        return rouUsuariomodifica;
    }

    public void setRouUsuariomodifica(String rouUsuariomodifica) {
        this.rouUsuariomodifica = rouUsuariomodifica;
    }

    public Date getRouFechamodifica() {
        return rouFechamodifica;
    }

    public void setRouFechamodifica(Date rouFechamodifica) {
        this.rouFechamodifica = rouFechamodifica;
    }

    public Integer getRouCodigo() {
        return rouCodigo;
    }

    public void setRouCodigo(Integer rouCodigo) {
        this.rouCodigo = rouCodigo;
    }

    public BikRoles getRouRolcodigo() {
        return rouRolcodigo;
    }

    public void setRouRolcodigo(BikRoles rouRolcodigo) {
        this.rouRolcodigo = rouRolcodigo;
    }

    public BikUsuariosSistema getRouUsscodigo() {
        return rouUsscodigo;
    }

    public void setRouUsscodigo(BikUsuariosSistema rouUsscodigo) {
        this.rouUsscodigo = rouUsscodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.rouRolcodigo);
        hash = 17 * hash + Objects.hashCode(this.rouUsscodigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BikRolesUsuarios other = (BikRolesUsuarios) obj;
        if (!Objects.equals(this.rouRolcodigo, other.rouRolcodigo)) {
            return false;
        }
        if (!Objects.equals(this.rouUsscodigo, other.rouUsscodigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.entities.BikRolesUsuarios[ rouCodigo=" + rouCodigo + " ]";
    }

}
