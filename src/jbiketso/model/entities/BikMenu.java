/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anayansy
 */
@Entity
@Table(name = "bik_menu",schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikMenu.findAll", query = "SELECT b FROM BikMenu b")
    , @NamedQuery(name = "BikMenu.findByMenCodigo", query = "SELECT b FROM BikMenu b WHERE b.menCodigo = :menCodigo")
    , @NamedQuery(name = "BikMenu.findByMenPantalla", query = "SELECT b FROM BikMenu b WHERE b.menPantalla = :menPantalla")
    , @NamedQuery(name = "BikMenu.findByMenEtiqueta", query = "SELECT b FROM BikMenu b WHERE b.menEtiqueta = :menEtiqueta")
    , @NamedQuery(name = "BikMenu.findByMenEstado", query = "SELECT b FROM BikMenu b WHERE b.menEstado = :menEstado")
    , @NamedQuery(name = "BikMenu.findByMenUsuarioingresa", query = "SELECT b FROM BikMenu b WHERE b.menUsuarioingresa = :menUsuarioingresa")
    , @NamedQuery(name = "BikMenu.findByMenFechaingresa", query = "SELECT b FROM BikMenu b WHERE b.menFechaingresa = :menFechaingresa")
    , @NamedQuery(name = "BikMenu.findByMenUsuariomodifica", query = "SELECT b FROM BikMenu b WHERE b.menUsuariomodifica = :menUsuariomodifica")
    , @NamedQuery(name = "BikMenu.findByMenFechamodifica", query = "SELECT b FROM BikMenu b WHERE b.menFechamodifica = :menFechamodifica")})
public class BikMenu implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proCodigomenu", fetch = FetchType.LAZY)
    private List<BikPermisoRol> bikPermisoRolList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "men_codigo")
    private Integer menCodigo;
    @Basic(optional = false)
    @Column(name = "men_pantalla")
    private String menPantalla;
    @Basic(optional = false)
    @Column(name = "men_etiqueta")
    private String menEtiqueta;
    @Basic(optional = false)
    @Column(name = "men_estado")
    private String menEstado;
    @Column(name = "men_usuarioingresa")
    private String menUsuarioingresa;
    @Column(name = "men_fechaingresa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date menFechaingresa;
    @Column(name = "men_usuariomodifica")
    private String menUsuariomodifica;
    @Column(name = "men_fechamodifica")
    @Temporal(TemporalType.TIMESTAMP)
    private Date menFechamodifica;
    @JoinColumn(name = "men_modcodigo", referencedColumnName = "mod_codigo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BikModulos menModcodigo;

    public BikMenu() {
    }

    public BikMenu(Integer menCodigo) {
        this.menCodigo = menCodigo;
    }

    public BikMenu(Integer menCodigo, String menPantalla, String menEtiqueta, String menEstado) {
        this.menCodigo = menCodigo;
        this.menPantalla = menPantalla;
        this.menEtiqueta = menEtiqueta;
        this.menEstado = menEstado;
    }

    public Integer getMenCodigo() {
        return menCodigo;
    }

    public void setMenCodigo(Integer menCodigo) {
        this.menCodigo = menCodigo;
    }

    public String getMenPantalla() {
        return menPantalla;
    }

    public void setMenPantalla(String menPantalla) {
        this.menPantalla = menPantalla;
    }

    public String getMenEtiqueta() {
        return menEtiqueta;
    }

    public void setMenEtiqueta(String menEtiqueta) {
        this.menEtiqueta = menEtiqueta;
    }

    public String getMenEstado() {
        return menEstado;
    }

    public void setMenEstado(String menEstado) {
        this.menEstado = menEstado;
    }

    public String getMenUsuarioingresa() {
        return menUsuarioingresa;
    }

    public void setMenUsuarioingresa(String menUsuarioingresa) {
        this.menUsuarioingresa = menUsuarioingresa;
    }

    public Date getMenFechaingresa() {
        return menFechaingresa;
    }

    public void setMenFechaingresa(Date menFechaingresa) {
        this.menFechaingresa = menFechaingresa;
    }

    public String getMenUsuariomodifica() {
        return menUsuariomodifica;
    }

    public void setMenUsuariomodifica(String menUsuariomodifica) {
        this.menUsuariomodifica = menUsuariomodifica;
    }

    public Date getMenFechamodifica() {
        return menFechamodifica;
    }

    public void setMenFechamodifica(Date menFechamodifica) {
        this.menFechamodifica = menFechamodifica;
    }

    public BikModulos getMenModcodigo() {
        return menModcodigo;
    }

    public void setMenModcodigo(BikModulos menModcodigo) {
        this.menModcodigo = menModcodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menCodigo != null ? menCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BikMenu)) {
            return false;
        }
        BikMenu other = (BikMenu) object;
        if ((this.menCodigo == null && other.menCodigo != null) || (this.menCodigo != null && !this.menCodigo.equals(other.menCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.BikMenu[ menCodigo=" + menCodigo + " ]";
    }

    @XmlTransient
    public List<BikPermisoRol> getBikPermisoRolList() {
        return bikPermisoRolList;
    }

    public void setBikPermisoRolList(List<BikPermisoRol> bikPermisoRolList) {
        this.bikPermisoRolList = bikPermisoRolList;
    }
    
}
