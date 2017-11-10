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
import javax.xml.bind.annotation.XmlTransient;
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
   private ObjectProperty<Integer> expCodigo;
   @Transient
   private SimpleObjectProperty<LocalDate> expFechaingreso;
   @Transient
   private SimpleObjectProperty<LocalDate> expFechasalida;
   @Transient
   private SimpleObjectProperty<GenValorCombo> expEstado;
   @Transient
   private SimpleObjectProperty<GenValorCombo> expTipoatencion;
   @Transient
   private ObjectProperty<Integer> expEstudiosocioeconomico;
   @Transient
   private ObjectProperty<Integer> expPersonashogar;
   @Transient
   private ObjectProperty<Integer> expDependientes;
   // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Transient
   private ObjectProperty<BigDecimal> expIngresopromedio;
   @Transient
   private String expUsuarioingresa;
   @Transient
   private Date expFechaingresa;
   @Transient
   private String expUsuariomodifica;
   @Transient
   private Date expFechamodifica;

   private List<BikPadecimiento> bikPadecimientoList;
   private BikPersona expCodencargado;
   private BikSede expSedcodigo;
   private BikUsuario expUsucodigo;
   private List<BikMedicamento> bikMedicamentoList;
   private List<BikRequisitosExpediente> bikRequisitosExpedienteList;

   public BikExpediente() {
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "exp_codigo")
   @Access(AccessType.PROPERTY)
   public Integer getExpCodigo() {
      return expCodigo.get();
   }

   public ObjectProperty<Integer> getCodigoProperty() {
      if (this.expCodigo == null) {
         this.expCodigo = new SimpleObjectProperty();
      }
      return this.expCodigo;
   }
   
   public void setExpCodigo(Integer expCodigo) {
      if (this.expCodigo == null) {
         this.expCodigo = new SimpleObjectProperty();
      }
      this.expCodigo.set(expCodigo);
   }

   @Transient
   @Basic(optional = false)
   @Column(name = "exp_fechaingreso")
   @Temporal(TemporalType.DATE)
   @Access(AccessType.PROPERTY)
   public Date getExpFechaingreso() {
      if (this.expFechaingreso == null) {
         this.expFechaingreso = new SimpleObjectProperty();
      }
      return java.sql.Date.valueOf(expFechaingreso.get());
   }

   public SimpleObjectProperty<LocalDate> getExpFechaingresoProperty() {
      if (this.expFechaingreso == null) {
         this.expFechaingreso = new SimpleObjectProperty();
      }
      return this.expFechaingreso;
   }

   public void setExpFechaingreso(Date expFechaingreso) {
      if (this.expFechaingreso == null) {
         this.expFechaingreso = new SimpleObjectProperty();
      }
      this.expFechaingreso.set(new java.sql.Date(expFechaingreso.getTime()).toLocalDate());
   }

   @Transient
   @Column(name = "exp_fechasalida")
   @Temporal(TemporalType.DATE)
   @Access(AccessType.PROPERTY)
   public Date getExpFechasalida() {
      if (this.expFechasalida == null) {
         this.expFechasalida = new SimpleObjectProperty();
      }
      return java.sql.Date.valueOf(expFechasalida.get());
   }

   public SimpleObjectProperty<LocalDate> getExpFechasalidaProperty() {
      if (this.expFechasalida == null) {
         this.expFechasalida = new SimpleObjectProperty();
      }
      return this.expFechasalida;
   }

   public void setExpFechasalida(Date expFechasalida) {
      if (this.expFechasalida == null) {
         this.expFechasalida = new SimpleObjectProperty();
      }
      this.expFechasalida.set(new java.sql.Date(expFechasalida.getTime()).toLocalDate());
   }

   @Transient
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

   @Transient
   @Basic(optional = false)
   @Column(name = "exp_tipoatencion")
   @Access(AccessType.PROPERTY)
   public String getExpTipoatencion() {
      if (this.expTipoatencion == null) {
         this.expTipoatencion = new SimpleObjectProperty();
      }
      return expTipoatencion.get().getCodigo();
   }

   public ObjectProperty getTipoAtencionProperty() {
      if (this.expTipoatencion == null) {
         this.expTipoatencion = new SimpleObjectProperty();
      }
      return this.expTipoatencion;
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
      if (this.expTipoatencion == null) {
         this.expTipoatencion = new SimpleObjectProperty();
      }
      this.expTipoatencion.set(valor);
   }

   @Column(name = "exp_estudiosocioeconomico")
   @Access(AccessType.PROPERTY)
   public Integer getExpEstudiosocioeconomico() {
      if (this.expEstudiosocioeconomico == null) {
         this.expEstudiosocioeconomico = new SimpleObjectProperty();
      }
      return expEstudiosocioeconomico.get();
   }
   
   public ObjectProperty getEstudioSocioEconomicoProperty() {
        if (this.expEstudiosocioeconomico == null) {
            this.expEstudiosocioeconomico = new SimpleObjectProperty();
        }
        return this.expEstudiosocioeconomico;
    }

   public void setExpEstudiosocioeconomico(Integer expEstudiosocioeconomico) {
      if (this.expEstudiosocioeconomico == null) {
         this.expEstudiosocioeconomico = new SimpleObjectProperty();
      }
      this.expEstudiosocioeconomico.set(expEstudiosocioeconomico);
   }

   @Transient
   @Column(name = "exp_personashogar")
   @Access(AccessType.PROPERTY)
   public Integer getExpPersonashogar() {
      if (this.expPersonashogar == null) {
         this.expPersonashogar = new SimpleObjectProperty();
      }
      return this.expPersonashogar.get();
   }
   
   public ObjectProperty getPersonasHogarProperty() {
        if (this.expPersonashogar == null) {
            this.expPersonashogar = new SimpleObjectProperty();
        }
        return this.expPersonashogar;
    }

   public void setExpPersonashogar(Integer expPersonashogar) {
      if (this.expPersonashogar == null) {
         this.expPersonashogar = new SimpleObjectProperty();
      }
      this.expPersonashogar.set(expPersonashogar);
   }

   @Transient
   @Column(name = "exp_dependientes")
   @Access(AccessType.PROPERTY)
   public Integer getExpDependientes() {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleObjectProperty();
      }
      return this.expDependientes.get();
   }

   public void setExpDependientes(Integer expDependientes) {
      if (this.expDependientes == null) {
         this.expDependientes = new SimpleObjectProperty();
      }
      this.expDependientes.set(expDependientes);
   }

   @Transient
   @Column(name = "exp_ingresopromedio")
   @Access(AccessType.PROPERTY)
   public BigDecimal getExpIngresopromedio() {
      if (this.expIngresopromedio == null) {
         this.expIngresopromedio = new SimpleObjectProperty();
      }
      return this.expIngresopromedio.get();
   }

   public void setExpIngresopromedio(BigDecimal expIngresopromedio) {
      if (this.expIngresopromedio == null) {
         this.expIngresopromedio = new SimpleObjectProperty();
      }
      this.expIngresopromedio.set(expIngresopromedio);
   }

   @Column(name = "exp_usuarioingresa")
   public String getExpUsuarioingresa() {
      return expUsuarioingresa;
   }

   public void setExpUsuarioingresa(String expUsuarioingresa) {
      this.expUsuarioingresa = expUsuarioingresa;
   }

   @Column(name = "exp_fechaingresa")
   @Temporal(TemporalType.TIMESTAMP)
   public Date getExpFechaingresa() {
      return expFechaingresa;
   }

   public void setExpFechaingresa(Date expFechaingresa) {
      this.expFechaingresa = expFechaingresa;
   }

   @Column(name = "exp_usuariomodifica")
   public String getExpUsuariomodifica() {
      return expUsuariomodifica;
   }

   public void setExpUsuariomodifica(String expUsuariomodifica) {
      this.expUsuariomodifica = expUsuariomodifica;
   }

   @Column(name = "exp_fechamodifica")
   @Temporal(TemporalType.TIMESTAMP)
   public Date getExpFechamodifica() {
      return expFechamodifica;
   }

   public void setExpFechamodifica(Date expFechamodifica) {
      this.expFechamodifica = expFechamodifica;
   }

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "padExpcodigo", fetch = FetchType.LAZY)
   @XmlTransient
   public List<BikPadecimiento> getBikPadecimientoList() {
      return bikPadecimientoList;
   }

   public void setBikPadecimientoList(List<BikPadecimiento> bikPadecimientoList) {
      this.bikPadecimientoList = bikPadecimientoList;
   }

   @JoinColumn(name = "exp_codencargado", referencedColumnName = "per_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   public BikPersona getExpCodencargado() {
      return expCodencargado;
   }

   public void setExpCodencargado(BikPersona expCodencargado) {
      this.expCodencargado = expCodencargado;
   }

   @JoinColumn(name = "exp_sedcodigo", referencedColumnName = "sed_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   public BikSede getExpSedcodigo() {
      return expSedcodigo;
   }

   public void setExpSedcodigo(BikSede expSedcodigo) {
      this.expSedcodigo = expSedcodigo;
   }

   @JoinColumn(name = "exp_usucodigo", referencedColumnName = "usu_codigo")
   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   public BikUsuario getExpUsucodigo() {
      return expUsucodigo;
   }

   public void setExpUsucodigo(BikUsuario expUsucodigo) {
      this.expUsucodigo = expUsucodigo;
   }

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "medExpcodigo", fetch = FetchType.LAZY)
   @XmlTransient
   public List<BikMedicamento> getBikMedicamentoList() {
      return bikMedicamentoList;
   }

   public void setBikMedicamentoList(List<BikMedicamento> bikMedicamentoList) {
      this.bikMedicamentoList = bikMedicamentoList;
   }

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "bikExpediente", fetch = FetchType.LAZY)
   @XmlTransient
   public List<BikRequisitosExpediente> getBikRequisitosExpedienteList() {
      return bikRequisitosExpedienteList;
   }

   public void setBikRequisitosExpedienteList(List<BikRequisitosExpediente> bikRequisitosExpedienteList) {
      this.bikRequisitosExpedienteList = bikRequisitosExpedienteList;
   }

   @Override
   public int hashCode() {
      int hash = 0;
      hash += (expCodigo != null ? expCodigo.hashCode() : 0);
      return hash;
   }

   @Override
   public boolean equals(Object object) {
      // TODO: Warning - this method won't work in the case the id fields are not set
      if (!(object instanceof BikExpediente)) {
         return false;
      }
      BikExpediente other = (BikExpediente) object;
      if ((this.expCodigo.get() == null && other.expCodigo.get() != null) || (this.expCodigo.get() != null && !this.expCodigo.get().equals(other.expCodigo.get()))) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "jbiketso.model.BikExpediente[ expCodigo=" + expCodigo.get() + " ]";
   }

}
