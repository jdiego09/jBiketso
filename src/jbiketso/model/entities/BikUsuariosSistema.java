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
import jbiketso.utils.GenValorCombo;

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_usuarios_sistema", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikUsuariosSistema.findAll", query = "SELECT b FROM BikUsuariosSistema b")
    , @NamedQuery(name = "BikUsuariosSistema.findByUssCodigo", query = "SELECT b FROM BikUsuariosSistema b WHERE b.ussCodigo = :ussCodigo")
})
public class BikUsuariosSistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private SimpleStringProperty ussCodigo;
    @Transient
    private ObjectProperty<GenValorCombo> ussEstado;
    @Transient
    private SimpleStringProperty ussContrasena;

    @Column(name = "uss_usuarioingresa")
    private String ussUsuarioingresa;
    @Column(name = "uss_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ussFechaingresa;
    @Column(name = "uss_usuariomodifica")
    private String ussUsuariomodifica;
    @Column(name = "uss_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ussFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rouUsscodigo", fetch = FetchType.LAZY)
    private List<BikRolesUsuarios> bikRolesUsuariosList;

    public BikUsuariosSistema() {
        this.ussCodigo = new SimpleStringProperty();
        this.ussEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
        this.ussContrasena = new SimpleStringProperty();
    }

    @Id
    @Basic(optional = false)
    @Column(name = "uss_codigo")
    @Access(AccessType.PROPERTY)
    public String getUssCodigo() {
        if (this.ussCodigo == null) {
            this.ussCodigo = new SimpleStringProperty();
        }
        return ussCodigo.get();
    }

    public void setUssCodigo(String ussCodigo) {
        if (this.ussCodigo == null) {
            this.ussCodigo = new SimpleStringProperty();
        }
        this.ussCodigo.set(ussCodigo);
    }

    public SimpleStringProperty getUssCodigoProperty() {
        if (this.ussCodigo == null) {
            this.ussCodigo = new SimpleStringProperty();
        }
        return this.ussCodigo;
    }

    @Basic(optional = false)
    @Column(name = "uss_estado")
    @Access(AccessType.PROPERTY)
    public String getUssEstado() {
        if (this.ussEstado == null) {
            this.ussEstado = new SimpleObjectProperty();
        }
        return ussEstado.get().getCodigo();
    }

    public void setUssEstado(String ussEstado) {
        if (this.ussEstado == null) {
            this.ussEstado = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        if (ussEstado.equalsIgnoreCase("a")) {
            valor = new GenValorCombo("A", "Activo");
        } else {

            valor = new GenValorCombo("I", "Inactivo");
        }
        this.ussEstado.set(valor);
    }

    public ObjectProperty getUssEstadoProperty() {
        if (this.ussEstado == null) {
            this.ussEstado = new SimpleObjectProperty();
        }
        return this.ussEstado;
    }

    public String getDescripcionEstado() {
        if (this.ussEstado == null) {
            this.ussEstado = new SimpleObjectProperty();
        }
        return this.ussEstado.get().getDescripcion();
    }

    @Column(name = "uss_contrasena")
    @Access(AccessType.PROPERTY)
    public String getUssContrasena() {
        if (this.ussContrasena == null) {
            this.ussContrasena = new SimpleStringProperty();
        }
        return ussContrasena.get();
    }

    public void setUssContrasena(String ussContrasena) {
        if (this.ussContrasena == null) {
            this.ussContrasena = new SimpleStringProperty();
        }
        this.ussContrasena.set(ussContrasena);
    }

    public SimpleStringProperty getUssContrasenaProperty() {
        if (this.ussContrasena == null) {
            this.ussContrasena = new SimpleStringProperty();
        }
        return this.ussContrasena;
    }
    
    public String getUssUsuarioingresa() {
        return ussUsuarioingresa;
    }

    public void setUssUsuarioingresa(String ussUsuarioingresa) {
        this.ussUsuarioingresa = ussUsuarioingresa;
    }

    public Date getUssFechaingresa() {
        return ussFechaingresa;
    }

    public void setUssFechaingresa(Date ussFechaingresa) {
        this.ussFechaingresa = ussFechaingresa;
    }

    public String getUssUsuariomodifica() {
        return ussUsuariomodifica;
    }

    public void setUssUsuariomodifica(String ussUsuariomodifica) {
        this.ussUsuariomodifica = ussUsuariomodifica;
    }

    public Date getUssFechamodifica() {
        return ussFechamodifica;
    }

    public void setUssFechamodifica(Date ussFechamodifica) {
        this.ussFechamodifica = ussFechamodifica;
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
        hash += (ussCodigo != null ? ussCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikUsuariosSistema)) {
            return false;
        }
        BikUsuariosSistema other = (BikUsuariosSistema) object;
        if ((this.ussCodigo == null && other.ussCodigo != null) || (this.ussCodigo != null && !this.ussCodigo.equals(other.ussCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.entities.BikUsuariosSistema[ ussCodigo=" + ussCodigo + " ]";
    }

}
