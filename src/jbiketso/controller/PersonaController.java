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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jbiketso.model.dao.PersonaDao;

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
    private JFXComboBox<?> jcmbEstadoCivil;

    @FXML
    private JFXTextField jtxfDetContacto;

    @FXML
    private JFXTextField jtxfCedula;

    @FXML
    private JFXTextField jtxfSegundoApellido;

    @FXML
    private TableColumn<?, ?> tbcDetDireccion;

    @FXML
    private TableView<?> tbvContactos;

    @FXML
    private Button btnGuardarContacto;

    @FXML
    private Button btnGuardarDireccion;

    @FXML
    private Button btnGuardarPersona;

    @FXML
    private JFXComboBox<?> jcmbGenero;

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
    private JFXComboBox<?> jcmbTipoContacto;

    @FXML
    private DatePicker dtpFechaNacimiento;

    @FXML
    private TableView<?> tbvDirecciones;

    PersonaDao personaDao;

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
        dtpFechaNacimiento.valueProperty().bindBidirectional(personaDao.fechaNacimiento);
        jcmbGenero.valueProperty().bindBidirectional(personaDao.genero);
        jtxfProfesion.textProperty().bindBidirectional(personaDao.profesion);

    }

    @FXML
    private void guardarPersona(ActionEvent event) {
        this.persona = new PersonaDao(jtxfCedula.getText(),
                jtxfNombres.getText(),
                jtxfProfesion.getText(),
                jtxfSegundoApellido.getText(),
                dtpFechaNacimiento.getValue(),
                jcmbGenero.getValue().toString(),
                jtxfNacionalidad.getText(),
                jcmbEstadoCivil.getValue().toString(),
                jtxfProfesion.getText(),
                null);
    }

}
