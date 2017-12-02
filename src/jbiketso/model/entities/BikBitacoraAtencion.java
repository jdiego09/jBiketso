package jbiketso.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Access(javax.persistence.AccessType.FIELD)
@Table(name = "bik_bitacora_atencion", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikBitacoraAtencion.findAll", query = "SELECT b FROM BikBitacoraAtencion b")
    , @NamedQuery(name = "BikBitacoraAtencion.findByCedulaDesc", query = "select b from BikBitacoraAtencion b join b.bikUsuario u join u.usuPercodigo p\n"
            + "  where b.biaTipo like :tipo"
            + "    and p.perCedula = :cedula\n"
            + "  order by b.biaFechainicio desc")})
public class BikBitacoraAtencion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected BikBitacoraAtencionPK bikBitacoraAtencionPK;
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
    @JoinColumn(name = "bia_codusuario", referencedColumnName = "usu_codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikUsuario bikUsuario;
    
    public BikBitacoraAtencion() {
        this.bikBitacoraAtencionPK = new BikBitacoraAtencionPK();
        this.bikUsuario = new BikUsuario();
        this.bikUsuario.setUsuPercodigo(new BikPersona());
        this.biaFechainicio = new SimpleObjectProperty(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.biaTipo = new SimpleObjectProperty(new GenValorCombo("N", "Toma de signos"));
        this.biaDetalle = new SimpleStringProperty();
    }
    
    @EmbeddedId
    @Access(AccessType.PROPERTY)
    public BikBitacoraAtencionPK getBikBitacoraAtencionPK() {
        return bikBitacoraAtencionPK;
    }
    
    public void setBikBitacoraAtencionPK(BikBitacoraAtencionPK bikBitacoraAtencionPK) {
        this.bikBitacoraAtencionPK = bikBitacoraAtencionPK;
    }
    
    @Basic(optional = false)
    @Column(name = "bia_fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    @Access(AccessType.PROPERTY)
    public Date getBiaFechainicio() {
        if (biaFechainicio == null) {
            biaFechainicio = new SimpleObjectProperty();
        }
        if (biaFechainicio != null && biaFechainicio.get() != null) {
            return Date.from(biaFechainicio.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            return null;
        }
    }
    
    public SimpleObjectProperty getFechaInicioProperty() {
        if (biaFechainicio == null) {
            biaFechainicio = new SimpleObjectProperty();
        }
        return this.biaFechainicio;
    }
    
    public void setBiaFechainicio(Date biaFechainicio) {
        if (this.biaFechainicio == null) {
            this.biaFechainicio = new SimpleObjectProperty();
        }
        if (biaFechainicio != null) {
            this.biaFechainicio.set(biaFechainicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
    
    @Basic(optional = false)
    @Column(name = "bia_tipo")
    @Access(AccessType.PROPERTY)
    public String getBiaTipo() {
        if (this.biaTipo == null) {
            this.biaTipo = new SimpleObjectProperty();
        }
        return biaTipo.get().getCodigo();
    }
    
    public ObjectProperty getTipoAtencionProperty() {
        if (this.biaTipo == null) {
            this.biaTipo = new SimpleObjectProperty();
        }
        return biaTipo;
    }
    
    public void setBiaTipo(String biaTipo) {
        if (this.biaTipo == null) {
            this.biaTipo = new SimpleObjectProperty();
        }
        GenValorCombo valor = null;
        if (biaTipo.equalsIgnoreCase("C")) {
            valor = new GenValorCombo("C", "Chequeo médico");
        }
        if (biaTipo.equalsIgnoreCase("T")) {
            valor = new GenValorCombo("T", "Toma de signos");
        }
        if (biaTipo.equalsIgnoreCase("I")) {
            valor = new GenValorCombo("I", "Ingreso al centro");
        }
        if (biaTipo.equalsIgnoreCase("S")) {
            valor = new GenValorCombo("S", "Salida del centro");
        }
        this.biaTipo.set(valor);
    }
    
    @Basic(optional = false)
    @Column(name = "bia_detalle")
    @Access(AccessType.PROPERTY)
    public String getBiaDetalle() {
        if (this.biaDetalle == null) {
            this.biaDetalle = new SimpleStringProperty();
        }
        return biaDetalle.get();
    }
    
    public SimpleStringProperty getDetalleProperty() {
        if (this.biaDetalle == null) {
            this.biaDetalle = new SimpleStringProperty();
        }
        return biaDetalle;
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
    
    public BikUsuario getBikUsuario() {
        return bikUsuario;
    }
    
    public void setBikUsuario(BikUsuario bikUsuario) {
        this.bikUsuario = bikUsuario;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bikBitacoraAtencionPK != null ? bikBitacoraAtencionPK.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikBitacoraAtencion)) {
            return false;
        }
        BikBitacoraAtencion other = (BikBitacoraAtencion) object;
        if ((this.bikBitacoraAtencionPK == null && other.bikBitacoraAtencionPK != null) || (this.bikBitacoraAtencionPK != null && !this.bikBitacoraAtencionPK.equals(other.bikBitacoraAtencionPK))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "jbiketso.model.BikBitacoraAtencion[ bikBitacoraAtencionPK=" + bikBitacoraAtencionPK + " ]";
    }
    
}
