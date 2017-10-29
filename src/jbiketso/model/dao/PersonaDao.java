/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class PersonaDao extends BaseDao {

    public SimpleStringProperty cedula;
    public SimpleStringProperty nombres;
    public SimpleStringProperty primerApellido;
    public SimpleStringProperty segundoApellido;
    public SimpleObjectProperty<Date> fechaNacimiento;
    public SimpleObjectProperty genero;
    public SimpleStringProperty nacionalidad;
    public SimpleStringProperty estadoCivil;
    public SimpleStringProperty profesion;

    public ArrayList<DireccionDao> direccionDao;
    public ArrayList<ContactoDao> contactoDao;

    private BikPersona persona;

    public PersonaDao() {
        this.cedula = new SimpleStringProperty();
        this.nombres = new SimpleStringProperty();
        this.primerApellido = new SimpleStringProperty();
        this.segundoApellido = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        //this.genero = new SimpleStringProperty();
        this.nacionalidad = new SimpleStringProperty();
        this.estadoCivil = new SimpleStringProperty();
        this.profesion = new SimpleStringProperty();
        this.direccionDao = new ArrayList<>();
    }

    public PersonaDao(String cedula, String nombres, String primerApellido, String segundoApellido, Date fechaNacimiento, String genero, String nacionalidad, String estadoCivil, String profesion, ArrayList<DireccionDao> direccionDao, ArrayList<ContactoDao> contactoDao) {

        this.cedula = new SimpleStringProperty();
        this.nombres = new SimpleStringProperty();
        this.primerApellido = new SimpleStringProperty();
        this.segundoApellido = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        this.genero = new SimpleStringProperty();
        this.nacionalidad = new SimpleStringProperty();
        this.estadoCivil = new SimpleStringProperty();
        this.profesion = new SimpleStringProperty();
        this.direccionDao = new ArrayList<>();

        this.cedula.set(cedula);
        this.nombres.set(nombres);
        this.primerApellido.set(primerApellido);
        this.segundoApellido.set(segundoApellido);
        this.fechaNacimiento.set(fechaNacimiento);
        this.genero.set(genero);
        this.nacionalidad.set(nacionalidad);
        this.estadoCivil.set(estadoCivil);
        this.profesion.set(profesion);
        this.direccionDao = direccionDao;

    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getNombres() {
        return nombres.get();
    }

    public void setNombres(String nombres) {
        this.nombres.set(nombres);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getNacionalidad() {
        return nacionalidad.get();
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad.set(nacionalidad);
    }

    public String getEstadoCivil() {
        return estadoCivil.get();
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil.set(estadoCivil);
    }

    public String getProfesion() {
        return profesion.get();
    }

    public void setProfesion(String profesion) {
        this.profesion.set(profesion);
    }

    public ArrayList<DireccionDao> getDireccionDao() {
        return direccionDao;
    }

    public void setDireccionDao(ArrayList<DireccionDao> direccionDao) {
        this.direccionDao = direccionDao;
    }

    public ArrayList<ContactoDao> getContactoDao() {
        return contactoDao;
    }

    public void setContactoDao(ArrayList<ContactoDao> contactoDao) {
        this.contactoDao = contactoDao;
    }

    public BikPersona getPersona() {
        return persona;
    }

    public void setPersona(BikPersona persona) {
        this.persona = persona;
    }

    // Procedimiento para guardar la información de la persona.
    public Resultado<BikPersona> save() {
        Resultado<BikPersona> resultado = new Resultado<>();
        try {

            persona = new BikPersona(getCedula(), getNombres(), getPrimerApellido(), getSegundoApellido(), getFechaNacimiento(), getGenero());
            persona = (BikPersona) super.save(persona);

            for (DireccionDao direccion : this.getDireccionDao()) {
                if (direccion.getCodigoDireccion() == null) {
                    BikDireccion nuevaDireccion = new BikDireccion(direccion);
                    persona.getBikDireccionList().add(nuevaDireccion);
                    getEntityManager().persist(nuevaDireccion);
                }
            }

            for (ContactoDao contacto : this.getContactoDao()) {
                if (contacto.getCodigoContacto() == null) {
                    BikContacto nuevoContacto = new BikContacto(contacto);
                    persona.getBikContactoList().add(nuevoContacto);
                    getEntityManager().persist(nuevoContacto);
                }
            }

            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(persona);
            resultado.setMensaje("Persona guardada correctamente.");

            return resultado;

        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar la persona.");
            return resultado;
        }
    }

}
