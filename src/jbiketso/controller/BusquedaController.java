/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.entities.BikPersona;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BusquedaController implements Initializable {
    ArrayList<BikPersona> personasList = new ArrayList<>();
    
    
    @FXML
    private BorderPane root;

    @FXML
    private VBox vbxCriterios;

    @FXML
    private Pane pnlResultados;

    @FXML
    private JFXButton jbtnAceptar;

    @FXML
    private JFXButton jbtnCancelar;
    
    @FXML
    private Pane tbvResultados;
    
    @XmlTransient
    private ObservableList<BikPersona> listaPersonas = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private void setBusquedaPersonas(){
        ArrayList<JFXTextField> criterios = new ArrayList<>();
        
        JFXTextField jtxf = new JFXTextField();
        jtxf.getStyleClass().add("edittext");
        jtxf.setId("jtxfCedula");
        jtxf.setPromptText("Cédula");
        jtxf.setLabelFloat(true);
        jtxf.prefWidth(200);
        criterios.add(jtxf);
        jtxf = new JFXTextField();
        jtxf.getStyleClass().add("edittext");
        jtxf.setId("jtxfNombre");
        jtxf.setPromptText("Nombre");
        jtxf.setLabelFloat(true);
        jtxf.prefWidth(200);
        criterios.add(jtxf);
        jtxf = new JFXTextField();
        jtxf.getStyleClass().add("edittext");
        jtxf.setId("jtxfApellido1");
        jtxf.setPromptText("Primer apellido");
        jtxf.setLabelFloat(true);
        jtxf.prefWidth(200);
        criterios.add(jtxf);
        jtxf = new JFXTextField();
        jtxf.getStyleClass().add("edittext");
        jtxf.setId("jtxfApellido2");
        jtxf.setPromptText("Segundo apellido");
        jtxf.setLabelFloat(true);
        jtxf.prefWidth(200);
        criterios.add(jtxf);
        vbxCriterios.getChildren().clear();
        criterios.stream().forEach(vbxCriterios.getChildren()::add);
        
        JFXButton jbtn = new JFXButton();
        jbtn.setId("jbtnFiltrar");
        jbtn.getStyleClass().add("actionbuttonaceptar");
        jbtn.setText("FILTRAR");
        vbxCriterios.getChildren().add(jbtn);
        
    }
    
}
