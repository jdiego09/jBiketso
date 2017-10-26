/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.entities.BikPersona;

/**
 *
 * @author Luis Diego
 */
public class PersonaController implements Initializable{
    
    @FXML
    private JFXTextField jtxfCedula;

    @FXML
    private JFXTextField jtxfNombres;

    @FXML
    private JFXTextField jtxfPrimerApellido;

    @FXML
    private JFXTextField jtxfSegundoApellido;

    @FXML
    private JFXComboBox<?> jcmbEstadoCivil;

    @FXML
    private JFXTextField jtxfNacionalidad;

    @FXML
    private DatePicker dtpFechaNacimiento;

    @FXML
    private JFXComboBox<?> jcmbGenero;

    @FXML
    private JFXTextField jtxfProfesion;
    
    @FXML
    private JFXButton jbtnGuardar;

    @FXML
    private JFXButton jbtnSalir;
   
    PersonaDao persona;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.persona = new PersonaDao();
    }
  
    private void bindPersona(){
        
        jtxfCedula.textProperty().bindBidirectional(persona.cedula);
        jtxfNombres.textProperty().bindBidirectional(persona.nombres);
        jtxfPrimerApellido.textProperty().bindBidirectional(persona.primerApellido);
        jtxfSegundoApellido.textProperty().bindBidirectional(persona.segundoApellido);
        //jcmbEstadoCivil.o.bindBidirectional(persona.estadoCivil);
        jtxfNacionalidad.textProperty().bindBidirectional(persona.nacionalidad);
        //dtpFechaNacimiento.textProperty().bindBidirectional(persona.fechaNacimiento);
        //jcmbGenero.textProperty().bindBidirectional(persona.genero);
        jtxfProfesion.textProperty().bindBidirectional(persona.profesion);
         
    }
    
    @FXML
    private void guardarPersona(ActionEvent event){
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
