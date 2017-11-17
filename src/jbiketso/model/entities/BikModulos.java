/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Table(name = "bik_modulos", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikModulos.findAll", query = "SELECT b FROM BikModulos b order by b.modCodigo")
    , @NamedQuery(name = "BikModulos.findByModCodigo", query = "SELECT b FROM BikModulos b WHERE b.modCodigo = :modCodigo")
    , @NamedQuery(name = "BikModulos.findByModDescripcion", query = "SELECT b FROM BikModulos b WHERE b.modDescripcion = :modDescripcion")
    , @NamedQuery(name = "BikModulos.findByModEstado", query = "SELECT b FROM BikModulos b WHERE b.modEstado = :modEstado")
    , @NamedQuery(name = "BikModulos.findByModUsuarioingresa", query = "SELECT b FROM BikModulos b WHERE b.modUsuarioingresa = :modUsuarioingresa")
    , @NamedQuery(name = "BikModulos.findByModFechaingresa", query = "SELECT b FROM BikModulos b WHERE b.modFechaingresa = :modFechaingresa")
    , @NamedQuery(name = "BikModulos.findByModUsuariomodifica", query = "SELECT b FROM BikModulos b WHERE b.modUsuariomodifica = :modUsuariomodifica")
    , @NamedQuery(name = "BikModulos.findByModFechamodifica", query = "SELECT b FROM BikModulos b WHERE b.modFechamodifica = :modFechamodifica")})
public class BikModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private SimpleStringProperty modCodigo;
    @Transient
    private SimpleStringProperty modDescripcion;
    @Transient
    private ObjectProperty<GenValorCombo> estado;

    @Column(name = "mod_usuarioingresa")
    private String modUsuarioingresa;
    @Column(name = "mod_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modFechaingresa;
    @Column(name = "mod_usuariomodifica")
    private String modUsuariomodifica;
    @Column(name = "mod_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modFechamodifica;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menModcodigo", fetch = FetchType.LAZY)
    @XmlTransient
    private List<BikMenu> bikMenuList;

    public BikModulos() {
        this.modCodigo = new SimpleStringProperty();
        this.modDescripcion = new SimpleStringProperty();
        this.estado = new SimpleObjectProperty();
    }

    public BikModulos(String modCodigo) {
        this.modCodigo = new SimpleStringProperty();
        this.modCodigo.set(modCodigo);
    }

    public BikModulos(String modCodigo, String modDescripcion) {
        this.modCodigo = new SimpleStringProperty();
        this.modDescripcion = new SimpleStringProperty();

        this.modCodigo.set(modCodigo);
        this.modDescripcion.set(modDescripcion);
    }

    public BikModulos(String modCodigo, String modDescripcion, String modEstado) {
        this.modCodigo = new SimpleStringProperty();
        this.modDescripcion = new SimpleStringProperty();
        this.estado = new SimpleObjectProperty();

        this.modCodigo.set(modCodigo);
        this.modDescripcion.set(modDescripcion);
        GenValorCombo valorEstado = null;
        if (modEstado.equalsIgnoreCase("a")) {
            valorEstado = new GenValorCombo("A", "Activo");
        } else {
            valorEstado = new GenValorCombo("I", "Inactivo");
        }
        this.estado.set(valorEstado);
    }

    @Id
    @Basic(optional = false)
    @Column(name = "mod_codigo")
    @Access(AccessType.PROPERTY)
    public String getModCodigo() {
        return modCodigo.get();
    }

    public void setModCodigo(String modCodigo) {
        if (this.modCodigo == null) {
            this.modCodigo = new SimpleStringProperty();
        }
        this.modCodigo.set(modCodigo);
    }

    public SimpleStringProperty getCodigoModuloProperty() {
        if (this.modCodigo == null) {
            this.modCodigo = new SimpleStringProperty();
        }
        return this.modCodigo;
    }

    @Basic(optional = false)
    @Column(name = "mod_descripcion")
    @Access(AccessType.PROPERTY)
    public String getModDescripcion() {
        return modDescripcion.get();
    }

    public void setModDescripcion(String modDescripcion) {
        if (this.modDescripcion == null) {
            this.modDescripcion = new SimpleStringProperty();
        }
        this.modDescripcion.set(modDescripcion);
    }

    @Basic(optional = false)
    @Column(name = "mod_estado")
    @Access(AccessType.PROPERTY)
    public String getModEstado() {
        return estado.get().getCodigo();
    }

    public SimpleStringProperty getDescripcionModuloProperty() {
        if (this.modDescripcion == null) {
            this.modDescripcion = new SimpleStringProperty();
        }
        return this.modDescripcion;
    }

    public void setModEstado(String modEstado) {
        GenValorCombo valorEstado = null;
        if (this.estado == null) {
            this.estado = new SimpleObjectProperty();
        }
        if (modEstado.equalsIgnoreCase("a")) {
            valorEstado = new GenValorCombo("A", "Activo");
        } else {
            valorEstado = new GenValorCombo("I", "Inactivo");
        }
        this.estado.set(valorEstado);
    }

    public String getDescripcionEstado() {
        if (this.estado == null) {
            this.estado = new SimpleObjectProperty();
        }
        return this.estado.get().getDescripcion();
    }

    public ObjectProperty<GenValorCombo> getEstadoProperty() {
        if (this.estado == null) {
            this.estado = new SimpleObjectProperty();
        }
        return this.estado;
    }

    public String getModUsuarioingresa() {
        return modUsuarioingresa;
    }

    public void setModUsuarioingresa(String modUsuarioingresa) {
        this.modUsuarioingresa = modUsuarioingresa;
    }

    public Date getModFechaingresa() {
        return modFechaingresa;
    }

    public void setModFechaingresa(Date modFechaingresa) {
        this.modFechaingresa = modFechaingresa;
    }

    public String getModUsuariomodifica() {
        return modUsuariomodifica;
    }

    public void setModUsuariomodifica(String modUsuariomodifica) {
        this.modUsuariomodifica = modUsuariomodifica;
    }

    public Date getModFechamodifica() {
        return modFechamodifica;
    }

    public void setModFechamodifica(Date modFechamodifica) {
        this.modFechamodifica = modFechamodifica;
    }

    public List<BikMenu> getBikMenuList() {
        return bikMenuList;
    }

    public void setBikMenuList(List<BikMenu> bikMenuList) {
        this.bikMenuList = bikMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.modCodigo.get());
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
        final BikModulos other = (BikModulos) obj;
        if (!this.modCodigo.get().equals(other.modCodigo.get())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikModulos[ modCodigo=" + modCodigo.get() + " ]";
    }

}
