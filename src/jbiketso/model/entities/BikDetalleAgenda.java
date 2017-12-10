/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Access;
import javax.persistence.AccessType;
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

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_detalle_agenda", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikDetalleAgenda.findAll", query = "SELECT b FROM BikDetalleAgenda b")
    , @NamedQuery(name = "BikDetalleAgenda.findPendientesFecha", query = "select a from BikDetalleAgenda a\n"
            + "where a.deaFechainicio >= :fechaInicio\n"
            + "and a.deaFechafin < :fechaFin\n"
            + "and a.deaEstado = 'P'\n"
            + "order by a.deaFechainicio")
    , @NamedQuery(name = "BikDetalleAgenda.findEntreFechas", query = "SELECT d FROM BikDetalleAgenda d\n"
            + "where (:fechaInicio between d.deaFechainicio and d.deaFechafin)\n"
            + "or (:fechaFin between d.deaFechainicio and d.deaFechafin)")})
public class BikDetalleAgenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private SimpleIntegerProperty deaCodigo;
    @Transient
    private SimpleObjectProperty<LocalDateTime> deaFechainicio;
    @Transient
    private SimpleObjectProperty<LocalDateTime> deaFechafin;
    @Transient
    private SimpleStringProperty deaTitulo;
    @Transient
    private SimpleStringProperty deaDetalle;
    @Transient
    private ObjectProperty<GenValorCombo> deaEstado;
    @Column(name = "dea_usuarioingresa")
    private String deaUsuarioingresa;
    @Column(name = "dea_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deaFechaingresa;
    @Column(name = "dea_usuariomodifica")
    private String deaUsuariomodifica;
    @Column(name = "dea_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deaFechamodifica;
    @JoinColumn(name = "dea_agecodigo", referencedColumnName = "age_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikAgenda deaAgecodigo;
    @JoinColumn(name = "dea_funcodigo", referencedColumnName = "fun_codigo")
    @ManyToOne(fetch = FetchType.LAZY)
    private BikFuncionario deaFuncodigo;
    @JoinColumn(name = "dea_codusuario", referencedColumnName = "usu_codigo")
    @ManyToOne(fetch = FetchType.LAZY)
    private BikUsuario deaCodusuario;

    public BikDetalleAgenda() {
        this.deaCodigo = new SimpleIntegerProperty();
        this.deaEstado = new SimpleObjectProperty(new GenValorCombo("P", "Pendiente"));
        this.deaDetalle = new SimpleStringProperty();
        this.deaFechainicio = new SimpleObjectProperty();
        this.deaFechafin = new SimpleObjectProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dea_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getDeaCodigo() {
        if (this.deaCodigo == null) {
            this.deaCodigo = new SimpleIntegerProperty();
        }
        return deaCodigo.get();
    }

    public SimpleIntegerProperty getCodigoProperty() {
        if (this.deaCodigo == null) {
            this.deaCodigo = new SimpleIntegerProperty();
        }
        return deaCodigo;
    }

    public void setDeaCodigo(Integer deaCodigo) {
        if (this.deaCodigo == null) {
            this.deaCodigo = new SimpleIntegerProperty();
        }
        this.deaCodigo.set(deaCodigo);
    }

    @Column(name = "dea_fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    @Access(AccessType.PROPERTY)
    public Date getDeaFechainicio() {
        if (deaFechainicio != null && deaFechainicio.get() != null) {
            return Date.from(this.deaFechainicio.get().atZone(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public SimpleObjectProperty getFechaInicioProperty() {
        if (this.deaFechainicio == null) {
            this.deaFechainicio = new SimpleObjectProperty(LocalDate.now());
        }
        return this.deaFechainicio;
    }

    public void setDeaFechainicio(Date deaFechainicio) {
        if (deaFechainicio != null) {
            this.deaFechainicio.set(LocalDateTime.ofInstant(deaFechainicio.toInstant(), ZoneId.systemDefault()));
        }
    }

    @Column(name = "dea_fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    @Access(AccessType.PROPERTY)
    public Date getDeaFechafin() {
        if (deaFechafin != null && deaFechafin.get() != null) {
            return Date.from(this.deaFechafin.get().atZone(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public SimpleObjectProperty getFechaFinProperty() {
        if (this.deaFechafin == null) {
            this.deaFechafin = new SimpleObjectProperty(LocalDate.now());
        }
        return this.deaFechafin;
    }

    public void setDeaFechafin(Date deaFechafin) {
        if (deaFechafin != null) {
            this.deaFechafin.set(LocalDateTime.ofInstant(deaFechafin.toInstant(), ZoneId.systemDefault()));
        }
    }

    @Column(name = "dea_titulo")
    @Access(AccessType.PROPERTY)
    public String getDeaTitulo() {
        if (this.deaTitulo == null) {
            this.deaTitulo = new SimpleStringProperty();
        }
        return this.deaTitulo.get();
    }

    public SimpleStringProperty getTituloProperty() {
        if (this.deaTitulo == null) {
            this.deaTitulo = new SimpleStringProperty();
        }
        return this.deaTitulo;
    }

    public void setDeaTitulo(String deaTitulo) {
        if (this.deaTitulo == null) {
            this.deaTitulo = new SimpleStringProperty();
        }
        this.deaTitulo.set(deaTitulo);
    }

    @Column(name = "dea_detalle")
    @Access(AccessType.PROPERTY)
    public String getDeaDetalle() {
        if (this.deaDetalle == null) {
            this.deaDetalle = new SimpleStringProperty();
        }
        return this.deaDetalle.get();
    }

    public SimpleStringProperty getDetalleProperty() {
        if (this.deaDetalle == null) {
            this.deaDetalle = new SimpleStringProperty();
        }
        return this.deaDetalle;
    }

    public void setDeaDetalle(String deaDetalle) {
        if (this.deaDetalle == null) {
            this.deaDetalle = new SimpleStringProperty();
        }
        this.deaDetalle.set(deaDetalle);
    }

    @Basic(optional = false)
    @Column(name = "dea_estado")
    @Access(AccessType.PROPERTY)
    public String getDeaEstado() {
        if (this.deaEstado == null) {
            this.deaEstado = new SimpleObjectProperty();
        }
        return deaEstado.get().getCodigo();
    }

    public ObjectProperty getEstadoProperty() {
        if (this.deaEstado == null) {
            this.deaEstado = new SimpleObjectProperty(new GenValorCombo("P", "Pendiente"));
        }
        return deaEstado;
    }

    public void setDeaEstado(String deaEstado) {
        if (this.deaEstado == null) {
            this.deaEstado = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        switch (deaEstado) {
            case "p":
                valor = new GenValorCombo("P", "Pendiente");
                break;
            case "c":
                valor = new GenValorCombo("C", "Cancelado");
                break;
            case "a":
                valor = new GenValorCombo("A", "Atendido");
                break;
            default:
                valor = new GenValorCombo("P", "Pendiente");
                break;
        }
        this.deaEstado.set(valor);
    }

    public String getDeaUsuarioingresa() {
        return deaUsuarioingresa;
    }

    public void setDeaUsuarioingresa(String deaUsuarioingresa) {
        this.deaUsuarioingresa = deaUsuarioingresa;
    }

    public Date getDeaFechaingresa() {
        return deaFechaingresa;
    }

    public void setDeaFechaingresa(Date deaFechaingresa) {
        this.deaFechaingresa = deaFechaingresa;
    }

    public String getDeaUsuariomodifica() {
        return deaUsuariomodifica;
    }

    public void setDeaUsuariomodifica(String deaUsuariomodifica) {
        this.deaUsuariomodifica = deaUsuariomodifica;
    }

    public Date getDeaFechamodifica() {
        return deaFechamodifica;
    }

    public void setDeaFechamodifica(Date deaFechamodifica) {
        this.deaFechamodifica = deaFechamodifica;
    }

    public BikAgenda getDeaAgecodigo() {
        return deaAgecodigo;
    }

    public void setDeaAgecodigo(BikAgenda deaAgecodigo) {
        this.deaAgecodigo = deaAgecodigo;
    }

    public BikFuncionario getDeaFuncodigo() {
        return deaFuncodigo;
    }

    public void setDeaFuncodigo(BikFuncionario deaFuncodigo) {
        this.deaFuncodigo = deaFuncodigo;
    }

    public BikUsuario getDeaCodusuario() {
        return deaCodusuario;
    }

    public void setDeaCodusuario(BikUsuario deaCodusuario) {
        this.deaCodusuario = deaCodusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deaCodigo != null ? deaCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikDetalleAgenda)) {
            return false;
        }
        BikDetalleAgenda other = (BikDetalleAgenda) object;
        if ((this.deaCodigo == null && other.deaCodigo != null) || (this.deaCodigo != null && !this.deaCodigo.equals(other.deaCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikDetalleAgenda[ deaCodigo=" + deaCodigo + " ]";
    }

}
