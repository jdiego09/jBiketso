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

/**
 *
 * @author jdiego
 */
@Entity
@Table(name = "bik_accesopantallas_vw", schema = "biktso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BikAccesopantallasView.findByCodigoUsuario", query = "SELECT b.codigoUsuario,b.codigoModulo,b.pantalla,b.etiqueta,b.inserta,b.modifica,b.elimina FROM BikAccesopantallasView b WHERE b.codigoUsuario = :codigoUsuario")
})
public class BikAccesoPantallasView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Basic(optional = false)
    @Column(name = "codigoUsuario")
    private String codigoUsuario;
    @Basic(optional = false)
    @Column(name = "codigoModulo")
    private String codigoModulo;
    @Basic(optional = false)
    @Column(name = "pantalla")
    private String pantalla;
    @Basic(optional = false)
    @Column(name = "etiqueta")
    private String etiqueta;
    @Basic(optional = false)
    @Column(name = "consulta")
    private String consulta;
    @Basic(optional = false)
    @Column(name = "inserta")
    private String inserta;
    @Basic(optional = false)
    @Column(name = "modifica")
    private String modifica;
    @Basic(optional = false)
    @Column(name = "elimina")
    private String elimina;

    public BikAccesoPantallasView() {
    }

    public BikAccesoPantallasView(String codigoUsuario, String codigoModulo, String pantalla, String etiqueta, String consulta, String inserta, String modifica, String elimina) {
        this.codigoUsuario = codigoUsuario;
        this.codigoModulo = codigoModulo;
        this.pantalla = pantalla;
        this.etiqueta = etiqueta;
        this.consulta = consulta;
        this.inserta = inserta;
        this.modifica = modifica;
        this.elimina = elimina;
    }
        
    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getCodigoModulo() {
        return codigoModulo;
    }

    public void setCodigoModulo(String codigoModulo) {
        this.codigoModulo = codigoModulo;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getInserta() {
        return inserta;
    }

    public void setInserta(String inserta) {
        this.inserta = inserta;
    }

    public String getModifica() {
        return modifica;
    }

    public void setModifica(String modifica) {
        this.modifica = modifica;
    }

    public String getElimina() {
        return elimina;
    }

    public void setElimina(String elimina) {
        this.elimina = elimina;
    }

}
