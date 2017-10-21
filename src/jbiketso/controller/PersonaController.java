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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jbiketso.model.entities.BikPersona;

/**
 *
 * @author Luis Diego
 */
public class PersonaController implements Initializable{

    @FXML
    private JFXTextField txtCedula;
    
    @FXML
    private JFXTextField txtNombres;
    
    @FXML
    private JFXTextField txtPrimerApellido;
    
    @FXML
    private JFXTextField txtSegundoApellido;
    
    @FXML
    private JFXComboBox cmbGenero;
    
    @FXML
    private JFXTextField txtNacionalidad;
    
    @FXML
    private JFXComboBox cmbEstadoCivil;
    
    @FXML
    private JFXTextField txtProfesion;
    
    @FXML
    private DatePicker dtpFechaNacimiento;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    @FXML
    private void guardarPersona(ActionEvent event){
        BikPersona persona;
    }
}
