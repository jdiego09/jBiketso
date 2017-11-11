/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import jbiketso.utils.GenValorCombo;

@Entity
@Access(AccessType.FIELD)
@Table(name = "bik_expediente", schema = "biketso")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "BikExpediente.findAll", query = "SELECT b FROM BikExpediente b")
   , @NamedQuery(name = "BikExpediente.findByExpCodigo", query = "SELECT b FROM BikExpediente b WHERE b.expCodigo = :expCodigo")
})
public class BikExpediente implements Serializable {

   private static final long serialVersionUID = 1L;

   @Transient
   private ObjectProperty<Integer> codigo;
   @Transient
   private SimpleObjectProperty<LocalDate> expFechaIngreso;
   @Transient
   private SimpleObjectProperty<LocalDate> expFechaSalida;
   @Transient
   private ObjectProperty<GenValorCombo> expEstado;
   @Transient
   private ObjectProperty<GenValorCombo> expTipoAtencion;
   @Transient
   private ObjectProperty<Integer> expEstudioSocioeconomico;
   @Transient
   private ObjectProperty<Integer> expPersonasHogar;
   @Transient
   private ObjectProperty<Integer> expDependientes;
   // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Transient
   private ObjectProperty<BigDecimal> expIngresoPromedio;

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
   @JoinColumn(name = "exp_codencargado", referencedColumnName = "per_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   private BikPersona expCodencargado;
   @JoinColumn(name = "exp_sedcodigo", referencedColumnName = "sed_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   private BikSede expSedcodigo;
   @JoinColumn(name = "exp_usucodigo", referencedColumnName = "usu_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   private BikUsuario expUsucodigo;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoExpediente", fetch = FetchType.LAZY)
   private List<BikMedicamento> bikMedicamentoList;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikExpediente", fetch = FetchType.LAZY)
   private List<BikRequisitosExpediente> bikRequisitosExpedienteList;

   public BikExpediente() {
      this.codigo = new SimpleObjectProperty();
      this.expFechaIngreso = new SimpleObjectProperty();
      this.expFechaSalida = new SimpleObjectProperty();
      this.expEstado = new SimpleObjectProperty();
      this.expTipoAtencion = new SimpleObjectProperty();
      this.expEstudioSocioeconomico = new SimpleObjectProperty();
      this.expPersonasHogar = new SimpleObjectProperty();
      this.expDependientes = new SimpleObjectProperty();
      this.expIngresoPromedio = new SimpleObjectProperty();
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "exp_codigo")
   @Access(AccessType.PROPERTY)
   public Integer getExpCodigo() {
      return codigo.get();
   }

   public ObjectProperty getCodigoProperty() {
      if (this.codigo == null) {
         this.codigo = new SimpleObjectProperty();
      }
      return this.codigo;
   }

   public void setExpCodigo(Integer expCodigo) {
      if (this.codigo == null) {
         this.codigo = new SimpleObjectProperty();
      }
      this.codigo.set(expCodigo);
   }

   @Basic(optional = false)
   @Column(name = "exp_fechaingreso")
   @Temporal(TemporalType.DATE)
   @Access(AccessType.PROPERTY)
   public Date getExpFechaIngreso() {
      if (this.expFechaIngreso == null) {
         this.expFechaIngreso = new SimpleObjectProperty();
      }
      return java.sql.Date.valueOf(expFechaIngreso.get());
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
      this.expFechaIngreso.set(new java.sql.Date(expFechaingreso.getTime()).toLocalDate());
   }

   @Column(name = "exp_fechasalida")
   @Temporal(TemporalType.DATE)
   @Access(AccessType.PROPERTY)
   public Date getExpFechaSalida() {
      if (this.expFechaSalida == null) {
         this.expFechaSalida = new SimpleObjectProperty();
      }
      return java.sql.Date.valueOf(expFechaSalida.get());
   }

   public SimpleObjectProperty<LocalDate> getExpFechaSalidaProperty() {
      if (this.expFechaSalida == null) {
         this.expFechaSalida = new SimpleObjectProperty();
      }
      return this.expFechaSalida;
   }

   public void setExpFechaSalida(Date expFechasalida) {
      if (this.expFechaSalida == null) {
         this.expFechaSalida = new SimpleObjectProperty();
      }
      this.expFechaSalida.set(new java.sql.Date(expFechasalida.getTime()).toLocalDate());
   }

   @Basic(optional = false)
   @Column(name = "exp_estado")
   @Access(AccessType.PROPERTY)
   public String getExpEstado() {
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
         this.expEstudioSocioeconomico = new SimpleObjectProperty();
      }
      return expEstudioSocioeconomico.get();
   }

   public ObjectProperty getEstudioSocioEconomicoProperty() {
      if (this.expEstudioSocioeconomico == null) {
         this.expEstudioSocioeconomico = new SimpleObjectProperty();
      }
      return this.expEstudioSocioeconomico;
   }

   public void setExpEstudiosocioeconomico(Integer expEstudiosocioeconomico) {
      if (this.expEstudioSocioeconomico == null) {
         this.expEstudioSocioeconomico = new SimpleObjectProperty();
      }
      this.expEstudioSocioeconomico.set(expEstudiosocioeconomico);
   }

   @Column(name = "exp_personashogar")
   @Access(AccessType.PROPERTY)
   public Integer getExpPersonashogar() {
      if (this.expPersonasHogar == null) {
         this.expPersonasHogar = new SimpleObjectProperty();
      }
      return this.expPersonasHogar.get();
   }

   public ObjectProperty getPersonasHogarProperty() {
      if (this.expPersonasHogar == null) {
         this.expPersonasHogar = new SimpleObjectProperty();
      }
      return this.expPersonasHogar;
   }

   public void setExpPersonashogar(Integer expPersonashogar) {
      if (this.expPersonasHogar == null) {
         this.expPersonasHogar = new SimpleObjectProperty();
      }
      this.expPersonasHogar.set(expPersonashogar);
   }

   @Column(name = "exp_dependientes")
   @Access(AccessType.PROPERTY)
   public Integer getExpDependientes() {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleObjectProperty();
      }
      return this.expDependientes.get();
   }

   public ObjectProperty getPersonasDependientesProperty() {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleObjectProperty();
      }
      return this.expDependientes;
   }

   public void setExpDependientes(Integer expDependientes) {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleObjectProperty();
      }
      this.expDependientes.set(expDependientes);
   }

   @Column(name = "exp_ingresopromedio")
   @Access(AccessType.PROPERTY)
   public BigDecimal getExpIngresopromedio() {
      if (this.expIngresoPromedio == null) {
         this.expIngresoPromedio = new SimpleObjectProperty();
      }
      return this.expIngresoPromedio.get();
   }

   public ObjectProperty getIngresoPromedioProperty() {
      if (this.expIngresoPromedio == null) {
         this.expIngresoPromedio = new SimpleObjectProperty();
      }
      return this.expIngresoPromedio;
   }

   public void setExpIngresopromedio(BigDecimal expIngresopromedio) {
      if (this.expIngresoPromedio == null) {
         this.expIngresoPromedio = new SimpleObjectProperty();
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

   public BikPersona getExpCodencargado() {
      return expCodencargado;
   }

   public void setExpCodencargado(BikPersona expCodencargado) {
      this.expCodencargado = expCodencargado;
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
      int hash = 0;
      hash += (codigo.get() != null ? codigo.get().hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      // TODO: Warning - this method won't work in the case the id fields are not set
      if (!(object instanceof BikExpediente)) {
         return false;
      }
      BikExpediente other = (BikExpediente) object;
      if ((this.codigo.get() == null && other.codigo.get() != null) || (this.codigo.get() != null && !this.codigo.get().equals(other.codigo.get()))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "jbiketso.model.BikExpediente[ expCodigo=" + codigo.get() + " ]";
   }

}
