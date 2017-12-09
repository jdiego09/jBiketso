package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Table(name = "bik_agenda", schema = "biketso")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "BikAgenda.findAll", query = "SELECT b FROM BikAgenda b")
   , @NamedQuery(name = "BikAgenda.findByCodigo", query = "SELECT b FROM BikAgenda b WHERE b.ageCodigo = :codigoAgenda")})
public class BikAgenda implements Serializable {

   private static final long serialVersionUID = 1L;

   @Transient
   private SimpleIntegerProperty ageCodigo;
   @Transient
   private SimpleStringProperty ageDescripcion;
   @Transient
   private ObjectProperty<GenValorCombo> ageTipo;
   @Transient
   private ObjectProperty<GenValorCombo> ageEstado;

   @Column(name = "age_usuarioingresa")
   private String ageUsuarioingresa;
   @Column(name = "age_fechaingresa")
   @Temporal(TemporalType.TIMESTAMP)
   private Date ageFechaingresa;
   @Column(name = "age_usuariomodifica")
   private String ageUsuariomodifica;
   @Column(name = "age_fechamodifica")
   @Temporal(TemporalType.TIMESTAMP)
   private Date ageFechamodifica;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaAgecodigo", fetch = FetchType.LAZY)
   private List<BikDetalleAgenda> bikDetalleAgendaList;
   @JoinColumn(name = "age_sedcodigo", referencedColumnName = "sed_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   private BikSede ageSedcodigo;

   public BikAgenda() {
      this.ageCodigo = new SimpleIntegerProperty();
      this.ageDescripcion = new SimpleStringProperty();
      this.ageTipo = new SimpleObjectProperty(new GenValorCombo("A", "Agenda"));
      this.ageEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activa"));
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "age_codigo")
   @Access(AccessType.PROPERTY)
   public Integer getAgeCodigo() {
      if (this.ageCodigo == null) {
         this.ageCodigo = new SimpleIntegerProperty();
      }
      return ageCodigo.get();
   }

   public SimpleIntegerProperty getCodigoProperty() {
      if (this.ageCodigo == null) {
         this.ageCodigo = new SimpleIntegerProperty();
      }
      return ageCodigo;
   }

   public void setAgeCodigo(Integer ageCodigo) {
      if (this.ageCodigo == null) {
         this.ageCodigo = new SimpleIntegerProperty();
      }
      this.ageCodigo.set(ageCodigo);
   }

   @Basic(optional = false)
   @Column(name = "age_descripcion")
   @Access(AccessType.PROPERTY)
   public String getAgeDescripcion() {
      if (this.ageDescripcion == null) {
         this.ageDescripcion = new SimpleStringProperty();
      }
      return ageDescripcion.get();
   }

   public SimpleStringProperty getDescripcionProperty() {
      if (this.ageDescripcion == null) {
         this.ageDescripcion = new SimpleStringProperty();
      }
      return this.ageDescripcion;
   }

   public void setAgeDescripcion(String ageDescripcion) {
      if (this.ageDescripcion == null) {
         this.ageDescripcion = new SimpleStringProperty();
      }
      this.ageDescripcion.set(ageDescripcion);
   }

   @Basic(optional = false)
   @Column(name = "age_tipo")
   @Access(AccessType.PROPERTY)
   public String getAgeTipo() {
      if (this.ageTipo == null) {
         this.ageTipo = new SimpleObjectProperty();
      }
      return ageTipo.get().getCodigo();
   }

   public ObjectProperty getTipoProperty() {
      if (this.ageTipo == null) {
         this.ageTipo = new SimpleObjectProperty(new GenValorCombo("A", "Agenda"));
      }
      return this.ageTipo;
   }

   public void setAgeTipo(String ageTipo) {
      if (this.ageTipo == null) {
         this.ageTipo = new SimpleObjectProperty();
      }
      GenValorCombo valor = null;
      if (ageTipo.equalsIgnoreCase("a")) {
         valor = new GenValorCombo("A", "Agenda");
      }
      this.ageTipo.set(valor);
   }

   @Basic(optional = false)
   @Column(name = "age_estado")
   @Access(AccessType.PROPERTY)
   public String getAgeEstado() {
      if (this.ageEstado == null) {
         this.ageEstado = new SimpleObjectProperty();
      }
      return ageEstado.get().getCodigo();
   }

   public ObjectProperty getEstadoProperty() {
      if (this.ageEstado == null) {
         this.ageEstado = new SimpleObjectProperty(new GenValorCombo("A", "Activo"));
      }
      return this.ageEstado;
   }

   public void setAgeEstado(String ageEstado) {
      if (this.ageEstado == null) {
         this.ageEstado = new SimpleObjectProperty();
      }
      GenValorCombo valor = null;
      switch (ageEstado) {
         case "a":
            valor = new GenValorCombo("A", "Activo");
            break;
         case "i":
            valor = new GenValorCombo("I", "Inactivo");
            break;
         default:
            valor = new GenValorCombo("A", "Activo");
            break;
      }
      this.ageEstado.set(valor);
   }

   public String getAgeUsuarioingresa() {
      return ageUsuarioingresa;
   }

   public void setAgeUsuarioingresa(String ageUsuarioingresa) {
      this.ageUsuarioingresa = ageUsuarioingresa;
   }

   public Date getAgeFechaingresa() {
      return ageFechaingresa;
   }

   public void setAgeFechaingresa(Date ageFechaingresa) {
      this.ageFechaingresa = ageFechaingresa;
   }

   public String getAgeUsuariomodifica() {
      return ageUsuariomodifica;
   }

   public void setAgeUsuariomodifica(String ageUsuariomodifica) {
      this.ageUsuariomodifica = ageUsuariomodifica;
   }

   public Date getAgeFechamodifica() {
      return ageFechamodifica;
   }

   public void setAgeFechamodifica(Date ageFechamodifica) {
      this.ageFechamodifica = ageFechamodifica;
   }

   @XmlTransient
   public List<BikDetalleAgenda> getBikDetalleAgendaList() {
      return bikDetalleAgendaList;
   }

   public void setBikDetalleAgendaList(List<BikDetalleAgenda> bikDetalleAgendaList) {
      this.bikDetalleAgendaList = bikDetalleAgendaList;
   }

   public BikSede getAgeSedcodigo() {
      return ageSedcodigo;
   }

   public void setAgeSedcodigo(BikSede ageSedcodigo) {
      this.ageSedcodigo = ageSedcodigo;
   }

   @Override
   public int hashCode() {
      int hash = 5;
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
      final BikAgenda other = (BikAgenda) obj;
      if (!Objects.equals(this.ageCodigo.get(), other.ageCodigo.get())) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "jbiketso.model.BikAgenda[ ageCodigo=" + ageCodigo.get() + " ]";
   }

}
