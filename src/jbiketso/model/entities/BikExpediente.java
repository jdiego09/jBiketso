/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.LocalDateConverter;

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_expediente", schema = "biketso")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "BikExpediente.findAll", query = "SELECT b FROM BikExpediente b")
   , @NamedQuery(name = "BikExpediente.findByExpCodigo", query = "SELECT b FROM BikExpediente b WHERE b.expCodigo = :expCodigo")
   , @NamedQuery(name = "BikExpediente.findByCedulaUsuario", query = "SELECT b FROM BikExpediente b join b.expUsucodigo u join u.usuPercodigo p WHERE p.perCedula = :cedula")
})
public class BikExpediente implements Serializable {

   private static final long serialVersionUID = 1L;

   @Transient
   private SimpleIntegerProperty codigo;
   @Transient
   private SimpleObjectProperty<LocalDate> expFechaIngreso;
   @Transient
   private SimpleObjectProperty<LocalDate> expFechaSalida;
   @Transient
   private ObjectProperty<GenValorCombo> expEstado;
   @Transient
   private ObjectProperty<GenValorCombo> expTipoAtencion;
   @Transient
   private SimpleIntegerProperty expEstudioSocioeconomico;
   @Transient
   private SimpleIntegerProperty expPersonasHogar;
   @Transient
   private SimpleIntegerProperty expDependientes;
   @Transient
   private SimpleDoubleProperty expIngresoPromedio;

   @Column(name = "exp_usuarioingresa")
   private String expUsuarioingresa;
   @Column(name = "exp_fechaingresa")
   @Temporal(TemporalType.TIMESTAMP)
   private Date expFechaingresa;
   @Column(name = "exp_usuariomodifica")
   private String expUsuariomodifica;
   @Column(name = "exp_fechamodifica")
   @Temporal(TemporalType.TIMESTAMP)
   private Date expFechamodifica;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "padExpcodigo", fetch = FetchType.LAZY)
   private List<BikPadecimiento> bikPadecimientoList;
   @JoinColumn(name = "exp_sedcodigo", referencedColumnName = "sed_codigo")
   @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
   private BikSede expSedcodigo;
   @JoinColumn(name = "exp_usucodigo", referencedColumnName = "usu_codigo")
   @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
   private BikUsuario expUsucodigo;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoExpediente", fetch = FetchType.LAZY)
   private List<BikMedicamento> bikMedicamentoList;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikExpediente", fetch = FetchType.LAZY)
   private List<BikRequisitosExpediente> bikRequisitosExpedienteList;

   public BikExpediente() {
      this.codigo = new SimpleIntegerProperty();
      this.expFechaIngreso = new SimpleObjectProperty(LocalDate.now());
      this.expFechaSalida = new SimpleObjectProperty();
      this.expEstado = new SimpleObjectProperty();
      this.expTipoAtencion = new SimpleObjectProperty();
      this.expEstudioSocioeconomico = new SimpleIntegerProperty();
      this.expPersonasHogar = new SimpleIntegerProperty();
      this.expDependientes = new SimpleIntegerProperty();
      this.expIngresoPromedio = new SimpleDoubleProperty();
   }

   public BikExpediente(Boolean inicializar) {
      this.codigo = new SimpleIntegerProperty();
      this.expFechaIngreso = new SimpleObjectProperty(LocalDate.now());
      this.expFechaSalida = new SimpleObjectProperty();
      this.expEstado = new SimpleObjectProperty();
      this.expTipoAtencion = new SimpleObjectProperty();
      this.expEstudioSocioeconomico = new SimpleIntegerProperty();
      this.expPersonasHogar = new SimpleIntegerProperty();
      this.expDependientes = new SimpleIntegerProperty();
      this.expIngresoPromedio = new SimpleDoubleProperty();
      if (inicializar) {
         this.expUsucodigo = new BikUsuario();
         this.expSedcodigo = new BikSede();
         this.bikMedicamentoList = new ArrayList<>();
         this.bikPadecimientoList = new ArrayList<>();
      }
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "exp_codigo")
   @Access(AccessType.PROPERTY)
   public Integer getExpCodigo() {
      if (this.codigo == null) {
         this.codigo = new SimpleIntegerProperty();
      }
      return codigo.get();
   }

   public SimpleIntegerProperty getCodigoProperty() {
      if (this.codigo == null) {
         this.codigo = new SimpleIntegerProperty();
      }
      return this.codigo;
   }

   public void setExpCodigo(Integer expCodigo) {
      if (this.codigo == null) {
         this.codigo = new SimpleIntegerProperty();
      }
      this.codigo.set(expCodigo);
   }

   @Basic(optional = false)
   @Column(name = "exp_fechaingreso")
   @Temporal(TemporalType.DATE)
   @Access(AccessType.PROPERTY)
   public Date getExpFechaIngreso() {
      if (expFechaIngreso == null) {
         expFechaIngreso = new SimpleObjectProperty();
      }
      if (expFechaIngreso != null && expFechaIngreso.get() != null) {
         return Date.from(expFechaIngreso.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
      } else {
         return null;
      }
   }

   public SimpleObjectProperty<LocalDate> getExpFechaIngresoProperty() {
      if (this.expFechaIngreso == null) {
         this.expFechaIngreso = new SimpleObjectProperty();
      }
      return this.expFechaIngreso;
   }

   public void setExpFechaIngreso(Date expFechaingreso) {
      if (this.expFechaIngreso == null) {
         this.expFechaIngreso = new SimpleObjectProperty();
      }
      if (expFechaingreso != null) {
         this.expFechaIngreso.set(expFechaingreso.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      }
   }

   @Column(name = "exp_fechasalida")
   @Temporal(TemporalType.DATE)
   @Access(AccessType.PROPERTY)
   public Date getExpFechaSalida() {
      if (this.expFechaSalida == null) {
         this.expFechaSalida = new SimpleObjectProperty();
      }
      if (expFechaSalida != null && expFechaSalida.get() != null) {
         return Date.from(expFechaSalida.get().atStartOfDay(ZoneId.systemDefault()).toInstant());
      } else {
         return null;
      }
   }

   public SimpleObjectProperty<LocalDate> getExpFechaSalidaProperty() {
      if (this.expFechaSalida == null) {
         this.expFechaSalida = new SimpleObjectProperty();
      }
      return this.expFechaSalida;
   }

   public void setExpFechaSalida(Date fechaSalida) {
      if (this.expFechaSalida == null) {
         this.expFechaSalida = new SimpleObjectProperty();
      }
      if (fechaSalida != null) {
         this.expFechaIngreso.set(fechaSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
      }
   }

   @Basic(optional = false)
   @Column(name = "exp_estado")
   @Access(AccessType.PROPERTY)
   public String getExpEstado() {
      if (this.expEstado == null) {
         this.expEstado = new SimpleObjectProperty();
      }
      return expEstado.get().getCodigo();
   }

   public ObjectProperty getEstadoProperty() {
      if (this.expEstado == null) {
         this.expEstado = new SimpleObjectProperty();
      }
      return this.expEstado;
   }

   public void setExpEstado(String expEstado) {
      GenValorCombo valor = null;
      if (expEstado.equalsIgnoreCase("a")) {
         valor = new GenValorCombo(expEstado, "Activo");
      } else {
         valor = new GenValorCombo(expEstado, "Inactivo");
      }
      if (this.expEstado == null) {
         this.expEstado = new SimpleObjectProperty();
      }
      this.expEstado.set(valor);
   }

   @Basic(optional = false)
   @Column(name = "exp_tipoatencion")
   @Access(AccessType.PROPERTY)
   public String getExpTipoatencion() {
      if (this.expTipoAtencion == null) {
         this.expTipoAtencion = new SimpleObjectProperty();
      }
      return expTipoAtencion.get().getCodigo();
   }

   public ObjectProperty getTipoAtencionProperty() {
      if (this.expTipoAtencion == null) {
         this.expTipoAtencion = new SimpleObjectProperty();
      }
      return this.expTipoAtencion;
   }

   public void setExpTipoatencion(String expTipoatencion) {
      GenValorCombo valor = null;
      if (expTipoatencion.equalsIgnoreCase("d")) {
         //por días
         valor = new GenValorCombo(expTipoatencion, "Días");
      }
      if (expTipoatencion.equalsIgnoreCase("p")) {
         //permanente
         valor = new GenValorCombo(expTipoatencion, "Permanente 24h");
      }
      //ver que otras había
      if (this.expTipoAtencion == null) {
         this.expTipoAtencion = new SimpleObjectProperty();
      }
      this.expTipoAtencion.set(valor);
   }

   @Column(name = "exp_estudiosocioeconomico")
   @Access(AccessType.PROPERTY)
   public Integer getExpEstudiosocioeconomico() {
      if (this.expEstudioSocioeconomico == null) {
         this.expEstudioSocioeconomico = new SimpleIntegerProperty();
      }
      return this.expEstudioSocioeconomico.get();
   }

   public SimpleIntegerProperty getEstudioSocioEconomicoProperty() {
      if (this.expEstudioSocioeconomico == null) {
         this.expEstudioSocioeconomico = new SimpleIntegerProperty();
      }
      return this.expEstudioSocioeconomico;
   }

   public void setExpEstudiosocioeconomico(Integer expEstudiosocioeconomico) {
      if (this.expEstudioSocioeconomico == null) {
         this.expEstudioSocioeconomico = new SimpleIntegerProperty();
      }
      this.expEstudioSocioeconomico.set(expEstudiosocioeconomico);
   }

   @Column(name = "exp_personashogar")
   @Access(AccessType.PROPERTY)
   public Integer getExpPersonashogar() {
      if (this.expPersonasHogar == null) {
         this.expPersonasHogar = new SimpleIntegerProperty();
      }
      return Integer.parseInt(String.valueOf(this.expPersonasHogar.get()));
   }

   public SimpleIntegerProperty getPersonasHogarProperty() {
      if (this.expPersonasHogar == null) {
         this.expPersonasHogar = new SimpleIntegerProperty();
      }
      return this.expPersonasHogar;
   }

   public void setExpPersonashogar(Integer expPersonashogar) {
      if (this.expPersonasHogar == null) {
         this.expPersonasHogar = new SimpleIntegerProperty();
      }
      this.expPersonasHogar.set(expPersonashogar);
   }

   @Column(name = "exp_dependientes")
   @Access(AccessType.PROPERTY)
   public Integer getExpDependientes() {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleIntegerProperty();
      }
      return Integer.parseInt(String.valueOf(this.expDependientes.get()));
   }

   public SimpleIntegerProperty getPersonasDependientesProperty() {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleIntegerProperty();
      }
      return this.expDependientes;
   }

   public void setExpDependientes(Integer expDependientes) {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleIntegerProperty();
      }
      this.expDependientes.set(expDependientes);
   }

   @Column(name = "exp_ingresopromedio")
   @Access(AccessType.PROPERTY)
   public Double getExpIngresopromedio() {
      if (this.expIngresoPromedio == null) {
         this.expIngresoPromedio = new SimpleDoubleProperty();
      }
      return this.expIngresoPromedio.get();

   }

   public SimpleDoubleProperty getIngresoPromedioProperty() {
      if (this.expIngresoPromedio == null) {
         this.expIngresoPromedio = new SimpleDoubleProperty();
      }
      return this.expIngresoPromedio;
   }

   public void setExpIngresopromedio(Double expIngresopromedio) {
      if (this.expIngresoPromedio == null) {
         this.expIngresoPromedio = new SimpleDoubleProperty();
      }
      this.expIngresoPromedio.set(expIngresopromedio);
   }

   public String getExpUsuarioingresa() {
      return expUsuarioingresa;
   }

   public void setExpUsuarioingresa(String expUsuarioingresa) {
      this.expUsuarioingresa = expUsuarioingresa;
   }

   public Date getExpFechaingresa() {
      return expFechaingresa;
   }

   public void setExpFechaingresa(Date expFechaingresa) {
      this.expFechaingresa = expFechaingresa;
   }

   public String getExpUsuariomodifica() {
      return expUsuariomodifica;
   }

   public void setExpUsuariomodifica(String expUsuariomodifica) {
      this.expUsuariomodifica = expUsuariomodifica;
   }

   public Date getExpFechamodifica() {
      return expFechamodifica;
   }

   public void setExpFechamodifica(Date expFechamodifica) {
      this.expFechamodifica = expFechamodifica;
   }

   public List<BikPadecimiento> getBikPadecimientoList() {
      return bikPadecimientoList;
   }

   public void setBikPadecimientoList(List<BikPadecimiento> bikPadecimientoList) {
      this.bikPadecimientoList = bikPadecimientoList;
   }

   public BikSede getExpSedcodigo() {
      return expSedcodigo;
   }

   public void setExpSedcodigo(BikSede expSedcodigo) {
      this.expSedcodigo = expSedcodigo;
   }

   public BikUsuario getExpUsucodigo() {
      return expUsucodigo;
   }

   public void setExpUsucodigo(BikUsuario expUsucodigo) {
      this.expUsucodigo = expUsucodigo;
   }

   public List<BikMedicamento> getBikMedicamentoList() {
      return bikMedicamentoList;
   }

   public void setBikMedicamentoList(List<BikMedicamento> bikMedicamentoList) {
      this.bikMedicamentoList = bikMedicamentoList;
   }

   public List<BikRequisitosExpediente> getBikRequisitosExpedienteList() {
      return bikRequisitosExpedienteList;
   }

   public void setBikRequisitosExpedienteList(List<BikRequisitosExpediente> bikRequisitosExpedienteList) {
      this.bikRequisitosExpedienteList = bikRequisitosExpedienteList;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 37 * hash + Objects.hashCode(this.codigo);
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
      final BikExpediente other = (BikExpediente) obj;
      if (!Objects.equals(this.codigo, other.codigo)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "jbiketso.model.BikExpediente[ expCodigo=" + codigo.get() + " ]";
   }

}
