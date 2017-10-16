/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.ReadOnly;

/**
 *
 * @author jdiego
 */
@ReadOnly
@Entity
@Table(name = "bik_accesomodulos_vw", schema = "biketso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikAccesoModulosView.findByCodigoUsuario", query = "SELECT b.codigoUsuario,b.codigoModulo,b.descripcionModulo FROM BikAccesoModulosView b WHERE b.codigoUsuario = :codigoUsuario")})
public class BikAccesoModulosView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Basic(optional = false)
    @Column(name = "rou_usscodigo")
    private String codigoUsuario;
    @Basic(optional = false)
    @Column(name = "pro_codigomodulo")
    private String codigoModulo;
    @Basic(optional = false)
    @Column(name = "mod_descripcion")
    private String descripcionModulo;

    public BikAccesoModulosView() {
    }

    public BikAccesoModulosView(String codigoUsuario, String codigoModulo, String descripcionModulo) {
        this.codigoUsuario = codigoUsuario;
        this.codigoModulo = codigoModulo;
        this.descripcionModulo = descripcionModulo;
    }
        
    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String rouUsscodigo) {
        this.codigoUsuario = rouUsscodigo;
    }

    public String getCodigoModulo() {
        return codigoModulo;
    }

    public void setCodigoModulo(String proCodigomodulo) {
        this.codigoModulo = proCodigomodulo;
    }

    public String getDescripcionModulo() {
        return descripcionModulo;
    }

    public void setDescripcionModulo(String modDescripcion) {
        this.descripcionModulo = modDescripcion;
    }

}
