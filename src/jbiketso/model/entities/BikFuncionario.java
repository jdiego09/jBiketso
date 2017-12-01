/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_funcionario", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikFuncionario.findAll", query = "SELECT b FROM BikFuncionario b")
    , @NamedQuery(name = "BikFuncionario.findByFunCodigo", query = "SELECT b FROM BikFuncionario b WHERE b.funCodigo = :funCodigo")
    , @NamedQuery(name = "BikFuncionari.findByCedula", query = "SELECT f FROM BikFuncionario f join f.funPercodigo p where p.perCedula = :cedula")})
public class BikFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private SimpleIntegerProperty funCodigo;
    @Transient
    private ObjectProperty<GenValorCombo> funEstado;
    @Transient
    private ObjectProperty<GenValorCombo> funTipo;
    @Transient
    private SimpleDoubleProperty funSalarioBase;
    @Transient
    private SimpleObjectProperty<LocalDate> funFechaingreso;
    @Transient
    private SimpleObjectProperty<LocalDate> funFechasalida;
    @Transient
    private SimpleStringProperty funObservaciones;

    @Column(name = "fun_usuarioingresa")
    private String funUsuarioingresa;
    @Column(name = "fun_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funFechaingresa;
    @Column(name = "fun_usuariomodifica")
    private String funUsuariomodifica;
    @Column(name = "fun_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funFechamodifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accFuncodigo", fetch = FetchType.LAZY)
    private List<BikAccionesPersonal> bikAccionesPersonalList;
    @JoinColumn(name = "fun_percodigo", referencedColumnName = "per_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPersona funPercodigo;
    @JoinColumn(name = "fun_puecodigo", referencedColumnName = "pue_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikPuesto funPuecodigo;
    @JoinColumn(name = "fun_sedcodigo", referencedColumnName = "sed_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikSede funSedcodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaFuncodigo", fetch = FetchType.LAZY)
    private List<BikDetalleAgenda> bikDetalleAgendaList;

    public BikFuncionario() {
        this.funCodigo = new SimpleIntegerProperty();
        this.funEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
        this.funTipo = new SimpleObjectProperty(new GenValorCombo("P", "Propiedad"));
        this.funSalarioBase = new SimpleDoubleProperty(BigDecimal.ZERO.doubleValue());
        this.funFechaingreso = new SimpleObjectProperty(LocalDate.now());
        this.funFechasalida = new SimpleObjectProperty();
        this.funObservaciones = new SimpleStringProperty();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fun_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getFunCodigo() {
        if (this.funCodigo == null) {
            this.funCodigo = new SimpleIntegerProperty();
        }
        return funCodigo.get();
    }

    public SimpleIntegerProperty getCodigoProperty() {
        if (this.funCodigo == null) {
            this.funCodigo = new SimpleIntegerProperty();
        }
        return this.funCodigo;
    }

    public void setFunCodigo(Integer funCodigo) {
        if (this.funCodigo == null) {
            this.funCodigo = new SimpleIntegerProperty();
        }
        this.funCodigo.set(funCodigo);
    }

    @Basic(optional = false)
    @Column(name = "fun_estado")
    @Access(AccessType.PROPERTY)
    public String getFunEstado() {
        if (this.funEstado == null) {
            this.funEstado = new SimpleObjectProperty();
        }
        return funEstado.get().getCodigo();
    }

    public ObjectProperty getEstadoProperty() {
        if (this.funEstado == null) {
            this.funEstado = new SimpleObjectProperty();
        }
        return funEstado;
    }

    public void setFunEstado(String funEstado) {
        if (this.funEstado == null) {
            this.funEstado = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        if (funEstado.equalsIgnoreCase("a")) {
            valor = new GenValorCombo(funEstado, "Activo");
        } else {
            valor = new GenValorCombo(funEstado, "Inactivo");
        }
        this.funEstado.set(valor);
    }

    @Basic(optional = false)
    @Column(name = "fun_tipo")
    @Access(AccessType.PROPERTY)
    public String getFunTipo() {
        if (this.funTipo == null) {
            this.funTipo = new SimpleObjectProperty();
        }
        return funTipo.get().getCodigo();
    }

    public ObjectProperty getTipoProperty() {
        if (this.funTipo == null) {
            this.funTipo = new SimpleObjectProperty();
        }
        return funTipo;
    }

    public void setFunTipo(String funTipo) {
        if (this.funTipo == null) {
            this.funTipo = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        if (funTipo.equalsIgnoreCase("p")) {
            valor = new GenValorCombo(funTipo, "Propiedad");
        }
        if (funTipo.equalsIgnoreCase("v")) {
            valor = new GenValorCombo(funTipo, "Voluntario");
        }
        if (funTipo.equalsIgnoreCase("i")) {
            valor = new GenValorCombo(funTipo, "Interino");
        }
        this.funTipo.set(valor);
    }

    @Column(name = "fun_salario_base")
    @Access(AccessType.PROPERTY)
    public Double getFunSalarioBase() {
        if (this.funSalarioBase == null) {
            this.funSalarioBase = new SimpleDoubleProperty(BigDecimal.ZERO.doubleValue());
        }
        return funSalarioBase.get();
    }

    public SimpleDoubleProperty getSalarioBaseProperty() {
        if (this.funSalarioBase == null) {
            this.funSalarioBase = new SimpleDoubleProperty(BigDecimal.ZERO.doubleValue());
        }
        return funSalarioBase;
    }

    public void setFunSalarioBase(Double funSalarioBase) {
        if (this.funSalarioBase == null) {
            this.funSalarioBase = new SimpleDoubleProperty(BigDecimal.ZERO.doubleValue());
        }
        this.funSalarioBase.set(funSalarioBase);
    }

    @Basic(optional = false)
    @Column(name = "fun_fechaingreso")
    @Temporal(TemporalType.DATE)
    @Access(AccessType.PROPERTY)
    public Date getFunFechaingreso() {
        if (this.funFechaingreso == null) {
            this.funFechaingreso = new SimpleObjectProperty();
        }
        if (funFechaingreso != null && funFechaingreso.get() != null) {
            return Date.from(funFechaingreso.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public SimpleObjectProperty<LocalDate> getFechaIngresoProperty() {
        if (this.funFechaingreso == null) {
            this.funFechaingreso = new SimpleObjectProperty();
        }
        return this.funFechaingreso;
    }

    public void setFunFechaingreso(Date funFechaingreso) {
        if (this.funFechaingreso == null) {
            this.funFechaingreso = new SimpleObjectProperty();
        }
        if (funFechaingreso != null) {
            this.funFechaingreso.set(funFechaingreso.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    @Column(name = "fun_fechasalida")
    @Temporal(TemporalType.DATE)
    @Access(AccessType.PROPERTY)
    public Date getFunFechasalida() {
        if (this.funFechasalida == null) {
            this.funFechasalida = new SimpleObjectProperty();
        }
        if (funFechasalida != null && funFechasalida.get() != null) {
            return Date.from(funFechasalida.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public SimpleObjectProperty<LocalDate> getFechaSalidaProperty() {
        if (this.funFechasalida == null) {
            this.funFechasalida = new SimpleObjectProperty();
        }
        return this.funFechasalida;
    }

    public void setFunFechasalida(Date funFechasalida) {
        if (this.funFechasalida == null) {
            this.funFechasalida = new SimpleObjectProperty();
        }
        if (funFechasalida != null) {
            this.funFechasalida.set(funFechasalida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    @Column(name = "fun_observaciones")
    @Access(AccessType.PROPERTY)
    public String getFunObservaciones() {
        if (this.funObservaciones == null) {
            this.funObservaciones = new SimpleStringProperty();
        }
        return funObservaciones.get();
    }

    public SimpleStringProperty getObservacionesProperty() {
        if (this.funObservaciones == null) {
            this.funObservaciones = new SimpleStringProperty();
        }
        return this.funObservaciones;
    }

    public void setFunObservaciones(String funObservaciones) {
        if (this.funObservaciones == null) {
            this.funObservaciones = new SimpleStringProperty();
        }
        this.funObservaciones.set(funObservaciones);
    }

    public String getFunUsuarioingresa() {
        return funUsuarioingresa;
    }

    public void setFunUsuarioingresa(String funUsuarioingresa) {
        this.funUsuarioingresa = funUsuarioingresa;
    }

    public Date getFunFechaingresa() {
        return funFechaingresa;
    }

    public void setFunFechaingresa(Date funFechaingresa) {
        this.funFechaingresa = funFechaingresa;
    }

    public String getFunUsuariomodifica() {
        return funUsuariomodifica;
    }

    public void setFunUsuariomodifica(String funUsuariomodifica) {
        this.funUsuariomodifica = funUsuariomodifica;
    }

    public Date getFunFechamodifica() {
        return funFechamodifica;
    }

    public void setFunFechamodifica(Date funFechamodifica) {
        this.funFechamodifica = funFechamodifica;
    }

    @XmlTransient
    public List<BikAccionesPersonal> getBikAccionesPersonalList() {
        return bikAccionesPersonalList;
    }

    public void setBikAccionesPersonalList(List<BikAccionesPersonal> bikAccionesPersonalList) {
        this.bikAccionesPersonalList = bikAccionesPersonalList;
    }

    public BikPersona getFunPercodigo() {
        return funPercodigo;
    }

    public void setFunPercodigo(BikPersona funPercodigo) {
        this.funPercodigo = funPercodigo;
    }

    public BikPuesto getFunPuecodigo() {
        return funPuecodigo;
    }

    public void setFunPuecodigo(BikPuesto funPuecodigo) {
        this.funPuecodigo = funPuecodigo;
    }

    public BikSede getFunSedcodigo() {
        return funSedcodigo;
    }

    public void setFunSedcodigo(BikSede funSedcodigo) {
        this.funSedcodigo = funSedcodigo;
    }

    @XmlTransient
    public List<BikDetalleAgenda> getBikDetalleAgendaList() {
        return bikDetalleAgendaList;
    }

    public void setBikDetalleAgendaList(List<BikDetalleAgenda> bikDetalleAgendaList) {
        this.bikDetalleAgendaList = bikDetalleAgendaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funCodigo != null ? funCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikFuncionario)) {
            return false;
        }
        BikFuncionario other = (BikFuncionario) object;
        if ((this.funCodigo == null && other.funCodigo != null) || (this.funCodigo != null && !this.funCodigo.equals(other.funCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikFuncionario[ funCodigo=" + funCodigo + " ]";
    }

}
