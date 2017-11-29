/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jdiego
 */
@Entity
@Table(name = "bik_nivel_socioeconomico", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikNivelSocioeconomico.findAll", query = "SELECT b FROM BikNivelSocioeconomico b ORDER BY b.nseCodigo ASC")})
public class BikNivelSocioeconomico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nse_codigo")
    private Integer nseCodigo;
    @Column(name = "nse_descripcion")
    private String nseDescripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nse_montoinicial")
    private Double nseMontoinicial;
    @Column(name = "nse_montofinal")
    private Double nseMontofinal;
    @Column(name = "nse_usuarioingresa")
    private String nseUsuarioingresa;
    @Column(name = "nse_fechaingresa")
    @Temporal(TemporalType.DATE)
    private Date nseFechaingresa;
    @Column(name = "nse_usuariomodifica")
    private String nseUsuariomodifica;
    @Column(name = "nse_fechamodifica")
    @Temporal(TemporalType.DATE)
    private Date nseFechamodifica;

    public BikNivelSocioeconomico() {
    }

    public BikNivelSocioeconomico(Integer nseCodigo) {
        this.nseCodigo = nseCodigo;
    }

    public Integer getNseCodigo() {
        return nseCodigo;
    }

    public void setNseCodigo(Integer nseCodigo) {
        this.nseCodigo = nseCodigo;
    }

    public String getNseDescripcion() {
        return nseDescripcion;
    }

    public void setNseDescripcion(String nseDescripcion) {
        this.nseDescripcion = nseDescripcion;
    }

    public Double getNseMontoinicial() {
        return nseMontoinicial;
    }

    public void setNseMontoinicial(Double nseMontoinicial) {
        this.nseMontoinicial = nseMontoinicial;
    }

    public Double getNseMontofinal() {
        return nseMontofinal;
    }

    public void setNseMontofinal(Double nseMontofinal) {
        this.nseMontofinal = nseMontofinal;
    }

    public String getNseUsuarioingresa() {
        return nseUsuarioingresa;
    }

    public void setNseUsuarioingresa(String nseUsuarioingresa) {
        this.nseUsuarioingresa = nseUsuarioingresa;
    }

    public Date getNseFechaingresa() {
        return nseFechaingresa;
    }

    public void setNseFechaingresa(Date nseFechaingresa) {
        this.nseFechaingresa = nseFechaingresa;
    }

    public String getNseUsuariomodifica() {
        return nseUsuariomodifica;
    }

    public void setNseUsuariomodifica(String nseUsuariomodifica) {
        this.nseUsuariomodifica = nseUsuariomodifica;
    }

    public Date getNseFechamodifica() {
        return nseFechamodifica;
    }

    public void setNseFechamodifica(Date nseFechamodifica) {
        this.nseFechamodifica = nseFechamodifica;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.nseCodigo);
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
        final BikNivelSocioeconomico other = (BikNivelSocioeconomico) obj;
        if (!Objects.equals(this.nseCodigo, other.nseCodigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jbiketso.model.entities.BikNivelSocioeconomico[ nseCodigo=" + nseCodigo + " ]";
    }

}
