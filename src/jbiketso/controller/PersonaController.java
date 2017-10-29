/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.DireccionDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.GenValorCombo;

/**
 *
 * @author Luis Diego
 */
public class PersonaController implements Initializable {

    @FXML
    private JFXTextField jtxfPrimerApellido;

    @FXML
    private JFXTextField jtxfDetaDireccion;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstadoCivil;

    @FXML
    private JFXTextField jtxfDetContacto;

    @FXML
    private JFXTextField jtxfCedula;

    @FXML
    private JFXTextField jtxfSegundoApellido;

    @FXML
    private TableColumn<?, ?> tbcDetDireccion;

    @FXML
    private TableView<BikContacto> tbvContactos;

    @FXML
    private Button btnGuardarContacto;

    @FXML
    private Button btnGuardarDireccion;

    @FXML
    private Button btnGuardarPersona;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbGenero;

    @FXML
    private JFXTextField jtxfNacionalidad;

    @FXML
    private JFXTextField jtxfProfesion;

    @FXML
    private TableColumn<?, ?> tbcDetContacto;

    @FXML
    private JFXTextField jtxfNombres;

    @FXML
    private TableColumn<?, ?> tbcTipoContacto;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipoContacto;

    @FXML
    private DatePicker dtpFechaNacimiento;

    @FXML
    private TableView<BikDireccion> tbvDirecciones;

    @XmlTransient
    public ObservableList<BikDireccion> direcciones = FXCollections
            .observableArrayList();

    @XmlTransient
    public ObservableList<BikContacto> contactos = FXCollections
            .observableArrayList();

    PersonaDao personaDao;
    DireccionDao direccionDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.personaDao = new PersonaDao();
    }

    private void bindPersona() {

        jtxfCedula.textProperty().bindBidirectional(personaDao.cedula);
        jtxfNombres.textProperty().bindBidirectional(personaDao.nombres);
        jtxfPrimerApellido.textProperty().bindBidirectional(personaDao.primerApellido);
        jtxfSegundoApellido.textProperty().bindBidirectional(personaDao.segundoApellido);
        jcmbEstadoCivil.valueProperty().bindBidirectional(personaDao.estadoCivil);
        jtxfNacionalidad.textProperty().bindBidirectional(personaDao.nacionalidad);
        //dtpFechaNacimiento.valueProperty().bindBidirectional(personaDao.fechaNacimiento);
        jcmbGenero.valueProperty().bindBidirectional(personaDao.genero);
        jtxfProfesion.textProperty().bindBidirectional(personaDao.profesion);

    }

    @FXML
    private void guardarPersona(ActionEvent event) {

    }

    @FXML
    private void agregarDireccion(ActionEvent event) {
        //contactos.add(new BikDireccion(personaDao));
    }

}
