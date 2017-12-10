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
import jbiketso.utils.GenValorCombo;

/**
 *
 * @author Anayansy
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_puesto", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikPuesto.findAll", query = "SELECT b FROM BikPuesto b")
    , @NamedQuery(name = "BikPuesto.findByPueCodigo", query = "SELECT b FROM BikPuesto b WHERE b.pueCodigo = :pueCodigo")
    , @NamedQuery(name = "BikPuesto.findPuestos", query = "select p from BikPuesto p\n"
            + "Where p.pueDescripcion like :puesto\n"
            + "and p.pueEstado = 'A'")})
public class BikPuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    private SimpleIntegerProperty pueCodigo;
    @Transient
    private SimpleStringProperty pueDescripcion;
    @Transient
    private ObjectProperty<GenValorCombo> pueEstado;
    @Column(name = "pue_usuarioingresa")
    private String pueUsuarioingresa;
    @Column(name = "pue_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pueFechaingresa;
    @Column(name = "pue_usuariomodifica")
    private String pueUsuariomodifica;
    @Column(name = "pue_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pueFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funPuecodigo", fetch = FetchType.LAZY)
    private List<BikFuncionario> bikFuncionarioList;

    public BikPuesto() {
        this.pueDescripcion = new SimpleStringProperty();
        this.pueEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pue_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getPueCodigo() {
        if (this.pueCodigo == null) {
            this.pueCodigo = new SimpleIntegerProperty();
        }
        return pueCodigo.get();
    }

    public void setPueCodigo(Integer pueCodigo) {
        if (this.pueCodigo == null) {
            this.pueCodigo = new SimpleIntegerProperty();
        }
        this.pueCodigo.set(pueCodigo);
    }

    public SimpleIntegerProperty getPueCodigoProperty() {
        if (this.pueCodigo == null) {
            this.pueCodigo = new SimpleIntegerProperty();
        }
        return this.pueCodigo;
    }

    public SimpleStringProperty getCodigoStringProperty() {
        return new SimpleStringProperty(String.valueOf(this.pueCodigo.get()));
    }

    @Basic(optional = false)
    @Column(name = "pue_descripcion")
    @Access(AccessType.PROPERTY)
    public String getPueDescripcion() {
        if (this.pueDescripcion == null) {
            this.pueDescripcion = new SimpleStringProperty();
        }
        return pueDescripcion.get();
    }

    public void setPueDescripcion(String pueDescripcion) {
        if (this.pueDescripcion == null) {
            this.pueDescripcion = new SimpleStringProperty();
        }
        this.pueDescripcion.set(pueDescripcion);
    }

    public SimpleStringProperty getPueDescripcionProperty() {
        if (this.pueDescripcion == null) {
            this.pueDescripcion = new SimpleStringProperty();
        }
        return this.pueDescripcion;
    }

    @Basic(optional = false)
    @Column(name = "pue_estado")
    @Access(AccessType.PROPERTY)
    public String getPueEstado() {
        if (this.pueEstado == null) {
            this.pueEstado = new SimpleObjectProperty();
        }
        return pueEstado.get().getCodigo();
    }

    public void setPueEstado(String pueEstado) {
        GenValorCombo valorEstado = null;
        if (this.pueEstado == null) {
            this.pueEstado = new SimpleObjectProperty();
        }
        if (pueEstado.equalsIgnoreCase("a")) {
            valorEstado = new GenValorCombo("A", "Activo");
        } else {
            valorEstado = new GenValorCombo("I", "Inactivo");
        }
        this.pueEstado.set(valorEstado);
    }

    public ObjectProperty getEstadoProperty() {
        if (this.pueEstado == null) {
            this.pueEstado = new SimpleObjectProperty();
        }
        return pueEstado;
    }
    
    public String getDescripcionEstado() {
        if (this.pueEstado == null) {
            this.pueEstado = new SimpleObjectProperty();
        }
        return this.pueEstado.get().getDescripcion();
    }

    public String getPueUsuarioingresa() {
        return pueUsuarioingresa;
    }

    public void setPueUsuarioingresa(String pueUsuarioingresa) {
        this.pueUsuarioingresa = pueUsuarioingresa;
    }

    public Date getPueFechaingresa() {
        return pueFechaingresa;
    }

    public void setPueFechaingresa(Date pueFechaingresa) {
        this.pueFechaingresa = pueFechaingresa;
    }

    public String getPueUsuariomodifica() {
        return pueUsuariomodifica;
    }

    public void setPueUsuariomodifica(String pueUsuariomodifica) {
        this.pueUsuariomodifica = pueUsuariomodifica;
    }

    public Date getPueFechamodifica() {
        return pueFechamodifica;
    }

    public void setPueFechamodifica(Date pueFechamodifica) {
        this.pueFechamodifica = pueFechamodifica;
    }

    @XmlTransient
    public List<BikFuncionario> getBikFuncionarioList() {
        return bikFuncionarioList;
    }

    public void setBikFuncionarioList(List<BikFuncionario> bikFuncionarioList) {
        this.bikFuncionarioList = bikFuncionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pueCodigo != null ? pueCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikPuesto)) {
            return false;
        }
        BikPuesto other = (BikPuesto) object;
        if ((this.pueCodigo == null && other.pueCodigo != null) || (this.pueCodigo != null && !this.pueCodigo.equals(other.pueCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikPuesto[ pueCodigo=" + pueCodigo + " ]";
    }

}
