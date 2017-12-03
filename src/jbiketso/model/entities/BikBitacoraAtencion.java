/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import jbiketso.utils.GenValorCombo;

/**
 *
 * @author jdiego
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_bitacora_atencion", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikBitacoraAtencion.findAll", query = "SELECT b FROM BikBitacoraAtencion b")
    })
public class BikBitacoraAtencion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private SimpleIntegerProperty biaCodigo;
    @Transient
    private SimpleObjectProperty<LocalDate> biaFechainicio;
    @Transient
    private ObjectProperty<GenValorCombo> biaTipo;
    @Transient
    private SimpleStringProperty biaDetalle;

    @Column(name = "bia_usuarioingresa")
    private String biaUsuarioingresa;
    @Column(name = "bia_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biaFechaingresa;
    @Column(name = "bia_usuariomodifica")
    private String biaUsuariomodifica;
    @Column(name = "bia_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date biaFechamodifica;
    @JoinColumn(name = "bia_codusuario", referencedColumnName = "usu_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikUsuario biaCodusuario;

    public BikBitacoraAtencion() {
        this.biaCodigo = new SimpleIntegerProperty();
        this.biaCodusuario = new BikUsuario();
        this.biaFechainicio = new SimpleObjectProperty(LocalDate.now());
        this.biaDetalle = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bia_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getBiaCodigo() {
        if (this.biaCodigo == null) {
            this.biaCodigo = new SimpleIntegerProperty();
        }
        return biaCodigo.get();
    }

    public SimpleIntegerProperty getCodigoProperty() {
        if (this.biaCodigo == null) {
            this.biaCodigo = new SimpleIntegerProperty();
        }
        return this.biaCodigo;
    }

    public void setBiaCodigo(Integer biaCodigo) {
        if (this.biaCodigo == null) {
            this.biaCodigo = new SimpleIntegerProperty();
        }
        this.biaCodigo.set(biaCodigo);
    }

    @Basic(optional = false)
    @Column(name = "bia_fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    @Access(AccessType.PROPERTY)
    public Date getBiaFechainicio() {
        if (this.biaFechainicio != null && this.biaFechainicio.get() != null) {
            return Date.from(this.biaFechainicio.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public SimpleObjectProperty getFechaProperty() {
        if (this.biaFechainicio == null) {
            this.biaFechainicio = new SimpleObjectProperty();
        }
        return this.biaFechainicio;
    }

    public void setBiaFechainicio(Date biaFechainicio) {
        if (biaFechainicio != null) {
            this.biaFechainicio.set(biaFechainicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    @Basic(optional = false)
    @Column(name = "bia_tipo")
    @Access(AccessType.PROPERTY)
    public String getBiaTipo() {
        if (this.biaTipo == null) {
            this.biaTipo = new SimpleObjectProperty(new GenValorCombo("N", "Atención"));
        }
        return this.biaTipo.get().getCodigo();
    }

    public ObjectProperty<GenValorCombo> getTipoAtencionProperty() {
        if (this.biaTipo == null) {
            this.biaTipo = new SimpleObjectProperty(new GenValorCombo("A", "Atención brindada"));
        }
        return this.biaTipo;
    }

    public void setBiaTipo(String biaTipo) {
        if (this.biaTipo == null) {
            this.biaTipo = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        switch (biaTipo.toLowerCase()) {
            //control de asistencia
            case "i":
                valor = new GenValorCombo("I", "Ingreso al centro");
                break;
            case "s":
                valor = new GenValorCombo("S", "Salida del centro");
                break;
            //chequeos médicos y toma de signos
            case "c":
                valor = new GenValorCombo("C", "Chequeo médico");
                break;
            case "t":
                valor = new GenValorCombo("I", "Toma de signos");
                break;
            default:
                valor = new GenValorCombo("N", "");
                break;
        }
        this.biaTipo.set(valor);
    }

    @Basic(optional = false)
    @Column(name = "bia_detalle")
    @Access(AccessType.PROPERTY)
    public String getBiaDetalle() {
        return biaDetalle.get();
    }

    public SimpleStringProperty getDetalleProperty() {
        if (this.biaDetalle == null) {
            this.biaDetalle = new SimpleStringProperty();
        }
        return this.biaDetalle;
    }

    public void setBiaDetalle(String biaDetalle) {
        if (this.biaDetalle == null) {
            this.biaDetalle = new SimpleStringProperty();
        }
        this.biaDetalle.set(biaDetalle);
    }

    public String getBiaUsuarioingresa() {
        return biaUsuarioingresa;
    }

    public void setBiaUsuarioingresa(String biaUsuarioingresa) {
        this.biaUsuarioingresa = biaUsuarioingresa;
    }

    public Date getBiaFechaingresa() {
        return biaFechaingresa;
    }

    public void setBiaFechaingresa(Date biaFechaingresa) {
        this.biaFechaingresa = biaFechaingresa;
    }

    public String getBiaUsuariomodifica() {
        return biaUsuariomodifica;
    }

    public void setBiaUsuariomodifica(String biaUsuariomodifica) {
        this.biaUsuariomodifica = biaUsuariomodifica;
    }

    public Date getBiaFechamodifica() {
        return biaFechamodifica;
    }

    public void setBiaFechamodifica(Date biaFechamodifica) {
        this.biaFechamodifica = biaFechamodifica;
    }

    public BikUsuario getBiaCodusuario() {
        return biaCodusuario;
    }

    public void setBiaCodusuario(BikUsuario biaCodusuario) {
        this.biaCodusuario = biaCodusuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.biaCodigo);
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
        final BikBitacoraAtencion other = (BikBitacoraAtencion) obj;
        if (!Objects.equals(this.biaCodigo, other.biaCodigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.entities.BikBitacoraAtencion[ biaCodigo=" + biaCodigo + " ]";
    }

}
