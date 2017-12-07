package jbiketso.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
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

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_acciones_personal", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikAccionesPersonal.findAll", query = "SELECT b FROM BikAccionesPersonal b")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccCodigo", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accCodigo = :accCodigo")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccTipo", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accTipo = :accTipo")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechainicio", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechainicio = :accFechainicio")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccFechafinal", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accFechafinal = :accFechafinal")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccEstado", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accEstado = :accEstado")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccObservaciones", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accObservaciones = :accObservaciones")
    , @NamedQuery(name = "BikAccionesPersonal.findByAccCalificacion", query = "SELECT b FROM BikAccionesPersonal b WHERE b.accCalificacion = :accCalificacion")
    , @NamedQuery(name = "BikAccionesPersonal.findByCedula", query = "SELECT a FROM BikAccionesPersonal a JOIN a.accFuncodigo f JOIN f.funPercodigo p WHERE p.perCedula = :cedula")})
public class BikAccionesPersonal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private SimpleIntegerProperty accCodigo;
    @Transient
    private ObjectProperty<GenValorCombo> accTipo;
    @Transient
    private SimpleObjectProperty<LocalDate> accFechainicio;
    @Transient
    private SimpleObjectProperty<LocalDate> accFechafinal;
    @Transient
    private ObjectProperty<GenValorCombo> accEstado;
    @Transient
    private SimpleStringProperty accObservaciones;
    @Column(name = "acc_calificacion")
    private Integer accCalificacion;
    @Column(name = "acc_usuarioaplica")
    private String accUsuarioaplica;
    @Column(name = "acc_fechaaplica")
    @Temporal(TemporalType.DATE)
    private Date accFechaaplica;
    @Column(name = "acc_usuarioanula")
    private String accUsuarioanula;
    @Column(name = "acc_fechaanula")
    @Temporal(TemporalType.DATE)
    private Date accFechaanula;
    @Column(name = "acc_usuarioingresa")
    private String accUsuarioingresa;
    @Column(name = "acc_fechaingresa")
    @Temporal(TemporalType.DATE)
    private Date accFechaingresa;
    @Column(name = "acc_usuariomodifica")
    private String accUsuariomodifica;
    @Column(name = "acc_fechamodifica")
    @Temporal(TemporalType.DATE)
    private Date accFechamodifica;
    @JoinColumn(name = "acc_funcodigo", referencedColumnName = "fun_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikFuncionario accFuncodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaAcccodigo", fetch = FetchType.LAZY)
    private List<BikEvaluacion> bikEvaluacionList;

    public BikAccionesPersonal() {
        this.accCodigo = new SimpleIntegerProperty();
        this.accTipo = new SimpleObjectProperty(new GenValorCombo("ING", "Ingreso"));
        this.accFechainicio = new SimpleObjectProperty();
        this.accFechafinal = new SimpleObjectProperty();
        this.accEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
        this.accObservaciones = new SimpleStringProperty();
    }

    /*public BikAccionesPersonal(Integer accCodigo) {
        this.accCodigo = accCodigo;
    }

    public BikAccionesPersonal(Integer accCodigo, String accTipo, Date accFechainicio, Date accFechafinal, String accEstado) {
        this.accCodigo = accCodigo;
        this.accTipo = accTipo;
        this.accFechainicio = accFechainicio;
        this.accFechafinal = accFechafinal;
        this.accEstado = accEstado;
    }*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "acc_codigo")
    @Access(AccessType.PROPERTY)
    public Integer getAccCodigo() {
        if (this.accCodigo == null) {
            this.accCodigo = new SimpleIntegerProperty();
        }
        return accCodigo.get();
    }

    public void setAccCodigo(Integer accCodigo) {
        if (this.accCodigo == null) {
            this.accCodigo = new SimpleIntegerProperty();
        }
        this.accCodigo.set(accCodigo);
    }

    public SimpleIntegerProperty getAccCodigoProperty() {
        if (this.accCodigo == null) {
            this.accCodigo = new SimpleIntegerProperty();
        }
        return this.accCodigo;
    }

    @Basic(optional = false)
    @Column(name = "acc_tipo")
    @Access(AccessType.PROPERTY)
    public String getAccTipo() {
        if (this.accTipo == null) {
            this.accTipo = new SimpleObjectProperty();
        }
        return accTipo.get().getCodigo();
    }

    public void setAccTipo(String accTipo) {
        if (this.accTipo == null) {
            this.accTipo = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        if (accTipo.equalsIgnoreCase("ing")) {
            valor = new GenValorCombo(accTipo, "Ingreso");
        } else if (accTipo.equalsIgnoreCase("vac")) {
            valor = new GenValorCombo(accTipo, "Vacaciones");
        } else if (accTipo.equalsIgnoreCase("ren")) {
            valor = new GenValorCombo(accTipo, "Renuncia");
        } else if (accTipo.equalsIgnoreCase("des")) {
            valor = new GenValorCombo(accTipo, "Despido");
        } else if (accTipo.equalsIgnoreCase("cal")) {
            valor = new GenValorCombo(accTipo, "Calificación funcionario");
        } else if (accTipo.equalsIgnoreCase("inc")) {
            valor = new GenValorCombo(accTipo, "Incapacidad");
        } else if (accTipo.equalsIgnoreCase("per")) {
            valor = new GenValorCombo(accTipo, "Permiso");
        }
        this.accTipo.set(valor);
    }

    public ObjectProperty getAccTipoProperty() {
        if (this.accTipo == null) {
            this.accTipo = new SimpleObjectProperty();
        }
        return accTipo;
    }

    public String getDescripcionTipoAccion() {
        if (this.accTipo == null) {
            this.accTipo = new SimpleObjectProperty();
        }
        return this.accTipo.get().getDescripcion();
    }

    @Basic(optional = false)
    @Column(name = "acc_fechainicio")
    @Temporal(TemporalType.DATE)
    @Access(AccessType.PROPERTY)
    public Date getAccFechainicio() {
        if (accFechainicio != null && accFechainicio.get() != null) {
            return Date.from(accFechainicio.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public void setAccFechainicio(Date accFechainicio) {
        if (accFechainicio != null) {
            this.accFechainicio.set(accFechainicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public SimpleObjectProperty getAccFechainicioProperty() {
        if (this.accFechainicio == null) {
            this.accFechainicio = new SimpleObjectProperty();
        }
        return this.accFechainicio;
    }

    @Basic(optional = false)
    @Column(name = "acc_fechafinal")
    @Temporal(TemporalType.DATE)
    @Access(AccessType.PROPERTY)
    public Date getAccFechafinal() {
        if (accFechafinal != null && accFechafinal.get() != null) {
            return Date.from(accFechafinal.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }

    public void setAccFechafinal(Date accFechafinal) {
        if (accFechafinal != null) {
            this.accFechafinal.set(accFechafinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public SimpleObjectProperty getAccFechafinalProperty() {
        if (this.accFechafinal == null) {
            this.accFechafinal = new SimpleObjectProperty();
        }
        return this.accFechafinal;
    }

    @Basic(optional = false)
    @Column(name = "acc_estado")
    @Access(AccessType.PROPERTY)
    public String getAccEstado() {
        if (this.accEstado == null) {
            this.accEstado = new SimpleObjectProperty();
        }
        return accEstado.get().getCodigo();
    }

    public void setAccEstado(String accEstado) {
        GenValorCombo valorEstado = null;
        if (this.accEstado == null) {
            this.accEstado = new SimpleObjectProperty();
        }
        if (accEstado.equalsIgnoreCase("a")) {
            valorEstado = new GenValorCombo("A", "Activo");
        } else if (accEstado.equalsIgnoreCase("i")) {
            valorEstado = new GenValorCombo("I", "Inactivo");
        }
        this.accEstado.set(valorEstado);
    }

    public ObjectProperty getAccEstadoProperty() {
        if (this.accEstado == null) {
            this.accEstado = new SimpleObjectProperty();
        }
        return this.accEstado;
    }

    public String getDescripcionEstado() {
        if (this.accEstado == null) {
            this.accEstado = new SimpleObjectProperty();
        }
        return this.accEstado.get().getDescripcion();
    }

    @Column(name = "acc_observaciones")
    @Access(AccessType.PROPERTY)
    public String getAccObservaciones() {
        if (this.accObservaciones == null) {
            this.accObservaciones = new SimpleStringProperty();
        }
        return accObservaciones.get();
    }

    public void setAccObservaciones(String accObservaciones) {
        if (this.accObservaciones == null) {
            this.accObservaciones = new SimpleStringProperty();
        }
        this.accObservaciones.set(accObservaciones);
    }

    public SimpleStringProperty getAccObservacionesProperty() {
        if (this.accObservaciones == null) {
            this.accObservaciones = new SimpleStringProperty();
        }
        return this.accObservaciones;
    }

    public Integer getAccCalificacion() {
        return accCalificacion;
    }

    public void setAccCalificacion(Integer accCalificacion) {
        this.accCalificacion = accCalificacion;
    }

    public String getAccUsuarioaplica() {
        return accUsuarioaplica;
    }

    public void setAccUsuarioaplica(String accUsuarioaplica) {
        this.accUsuarioaplica = accUsuarioaplica;
    }

    public Date getAccFechaaplica() {
        return accFechaaplica;
    }

    public void setAccFechaaplica(Date accFechaaplica) {
        this.accFechaaplica = accFechaaplica;
    }

    public String getAccUsuarioanula() {
        return accUsuarioanula;
    }

    public void setAccUsuarioanula(String accUsuarioanula) {
        this.accUsuarioanula = accUsuarioanula;
    }

    public Date getAccFechaanula() {
        return accFechaanula;
    }

    public void setAccFechaanula(Date accFechaanula) {
        this.accFechaanula = accFechaanula;
    }

    public String getAccUsuarioingresa() {
        return accUsuarioingresa;
    }

    public void setAccUsuarioingresa(String accUsuarioingresa) {
        this.accUsuarioingresa = accUsuarioingresa;
    }

    public Date getAccFechaingresa() {
        return accFechaingresa;
    }

    public void setAccFechaingresa(Date accFechaingresa) {
        this.accFechaingresa = accFechaingresa;
    }

    public String getAccUsuariomodifica() {
        return accUsuariomodifica;
    }

    public void setAccUsuariomodifica(String accUsuariomodifica) {
        this.accUsuariomodifica = accUsuariomodifica;
    }

    public Date getAccFechamodifica() {
        return accFechamodifica;
    }

    public void setAccFechamodifica(Date accFechamodifica) {
        this.accFechamodifica = accFechamodifica;
    }

    public BikFuncionario getAccFuncodigo() {
        return accFuncodigo;
    }

    public void setAccFuncodigo(BikFuncionario accFuncodigo) {
        this.accFuncodigo = accFuncodigo;
    }

    @XmlTransient
    public List<BikEvaluacion> getBikEvaluacionList() {
        return bikEvaluacionList;
    }

    public void setBikEvaluacionList(List<BikEvaluacion> bikEvaluacionList) {
        this.bikEvaluacionList = bikEvaluacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accCodigo != null ? accCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikAccionesPersonal)) {
            return false;
        }
        BikAccionesPersonal other = (BikAccionesPersonal) object;
        if ((this.accCodigo == null && other.accCodigo != null) || (this.accCodigo != null && !this.accCodigo.equals(other.accCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikAccionesPersonal[ accCodigo=" + accCodigo + " ]";
    }

}
