/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class PersonaDao extends BaseDao<Integer, BikPersona> {

    public SimpleStringProperty cedula;
    public SimpleStringProperty nombres;
    public SimpleStringProperty primerApellido;
    public SimpleStringProperty segundoApellido;
    public ObjectProperty<Date> fechaNacimiento;
    public ObjectProperty<GenValorCombo> genero;
    public SimpleStringProperty nacionalidad;
    public ObjectProperty<GenValorCombo> estadoCivil;
    public SimpleStringProperty profesion;

    public ArrayList<DireccionDao> direccionDao;
    public ArrayList<ContactoDao> contactoDao;

    private BikPersona persona;

    public PersonaDao(BikPersona persona) {

        this.cedula = new SimpleStringProperty();
        this.nombres = new SimpleStringProperty();
        this.primerApellido = new SimpleStringProperty();
        this.segundoApellido = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        this.genero = new SimpleObjectProperty();
        this.nacionalidad = new SimpleStringProperty();
        this.estadoCivil = new SimpleObjectProperty();
        this.profesion = new SimpleStringProperty();
        this.direccionDao = new ArrayList<>();

        this.cedula.set(persona.getPerCedula());
        this.nombres.set(persona.getPerNombres());
        this.primerApellido.set(persona.getPerPrimerapellido());
        this.segundoApellido.set(persona.getPerSegundoapellido());
        this.fechaNacimiento.set(persona.getPerFechanacimiento());
        if (persona.getPerGenero().equalsIgnoreCase("m")) {
            this.genero.set(new GenValorCombo("M", "Masculino"));
        } else {
            this.genero.set(new GenValorCombo("F", "Femenino"));
        }
        this.nacionalidad.set(persona.getPerNacionalidad());
        if (persona.getPerEstadocivil().equalsIgnoreCase("s")) {
            this.genero.set(new GenValorCombo("S", "Soltero"));
        } else if (persona.getPerEstadocivil().equalsIgnoreCase("c")) {
            this.genero.set(new GenValorCombo("C", "Casado"));
        } else if (persona.getPerEstadocivil().equalsIgnoreCase("d")) {
            this.genero.set(new GenValorCombo("D", "Divorsiado"));
        } else if (persona.getPerEstadocivil().equalsIgnoreCase("u")) {
            this.genero.set(new GenValorCombo("U", "Unión libre"));
        } else if (persona.getPerEstadocivil().equalsIgnoreCase("o")) {
            this.genero.set(new GenValorCombo("O", "Otro"));
        }
        this.profesion.set(persona.getPerProfesion());
        //this.direccionDao = persona.getBikDireccionList(new ArrayList());
    }

    public PersonaDao() {
        this.cedula = new SimpleStringProperty();
        this.nombres = new SimpleStringProperty();
        this.primerApellido = new SimpleStringProperty();
        this.segundoApellido = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        this.genero = new SimpleObjectProperty();
        this.nacionalidad = new SimpleStringProperty();
        this.estadoCivil = new SimpleObjectProperty();
        this.profesion = new SimpleStringProperty();
        this.direccionDao = new ArrayList<>();
        this.persona = new BikPersona();
    }

    public PersonaDao(String cedula, String nombres, String primerApellido, String segundoApellido, Date fechaNacimiento, String genero, String nacionalidad, String estadoCivil, String profesion, ArrayList<DireccionDao> direccionDao, ArrayList<ContactoDao> contactoDao) {

        this.cedula = new SimpleStringProperty();
        this.nombres = new SimpleStringProperty();
        this.primerApellido = new SimpleStringProperty();
        this.segundoApellido = new SimpleStringProperty();
        this.fechaNacimiento = new SimpleObjectProperty();
        this.genero = new SimpleObjectProperty();
        this.nacionalidad = new SimpleStringProperty();
        this.estadoCivil = new SimpleObjectProperty();
        this.profesion = new SimpleStringProperty();
        this.direccionDao = new ArrayList<>();

        this.cedula.set(cedula);
        this.nombres.set(nombres);
        this.primerApellido.set(primerApellido);
        this.segundoApellido.set(segundoApellido);
        this.fechaNacimiento.set(fechaNacimiento);
        if (genero.equalsIgnoreCase("m")) {
            this.genero.set(new GenValorCombo("M", "Masculino"));
        } else {
            this.genero.set(new GenValorCombo("F", "Femenino"));
        }
        this.nacionalidad.set(nacionalidad);
        if (estadoCivil.equalsIgnoreCase("s")) {
            this.genero.set(new GenValorCombo("S", "Soltero"));
        } else if (estadoCivil.equalsIgnoreCase("c")) {
            this.genero.set(new GenValorCombo("C", "Casado"));
        } else if (estadoCivil.equalsIgnoreCase("d")) {
            this.genero.set(new GenValorCombo("D", "Divorsiado"));
        } else if (estadoCivil.equalsIgnoreCase("u")) {
            this.genero.set(new GenValorCombo("U", "Unión libre"));
        } else if (estadoCivil.equalsIgnoreCase("o")) {
            this.genero.set(new GenValorCombo("O", "Otro"));
        }
        this.profesion.set(profesion);
        //this.direccionDao = direccionDao;

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

    public GenValorCombo getGenero() {
        return genero.get();
    }

    public void setGenero(GenValorCombo genero) {
        this.genero.set(genero);
    }

    public String getNacionalidad() {
        return nacionalidad.get();
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad.set(nacionalidad);
    }

    public GenValorCombo getEstadoCivil() {
        return estadoCivil.get();
    }

    public void setEstadoCivil(GenValorCombo estadoCivil) {
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

            persona = new BikPersona(0, this.persona.getPerCedula(),
                    this.persona.getPerNombres(),
                    this.persona.getPerPrimerapellido(),
                    this.persona.getPerSegundoapellido(),
                    this.persona.getPerFechanacimiento(),
                    this.persona.getPerGenero(),
                    this.persona.getPerNacionalidad(),
                    this.persona.getPerEstadocivil(),
                    this.persona.getPerProfesion());
            persona.setPerFechanacimiento(new Date());
            persona = (BikPersona) super.save(persona);

            if (persona.getPerCodigo() != null) {

                if (persona.getBikDireccionList().isEmpty()) {
                    for (DireccionDao direccion : this.getDireccionDao()) {
                        if (direccion.getCodigoDireccion() == null) {
                            BikDireccion nuevaDireccion = new BikDireccion(0, direccion.detDireccion.get());
                            nuevaDireccion.setDirPercodigo(persona);
                            persona.getBikDireccionList().add(nuevaDireccion);
                            getEntityManager().persist(nuevaDireccion);
                        }
                    }
                }
//
//                if (!persona.getBikContactoList().isEmpty()) {
//                    for (ContactoDao contacto : this.getContactoDao()) {
//                        if (contacto.getCodigoContacto() == null) {
//                            BikContacto nuevoContacto = new BikContacto(contacto);
//                            nuevoContacto.setConPercodigo(persona);
//                            persona.getBikContactoList().add(nuevoContacto);
//                            getEntityManager().persist(nuevoContacto);
//                        }
//                    }
//                }
//
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(persona);
                resultado.setMensaje("Persona guardada correctamente.");

            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(persona);
                resultado.setMensaje("No se pudo guardar la persona.");
            }

            return resultado;

        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar la persona.");
            return resultado;
        }
    }

}
