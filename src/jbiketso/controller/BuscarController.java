/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Luis Diego
 */
public class BuscarController implements Initializable{

    @FXML
    private AnchorPane acpRoot;
    @FXML
    private TableView<?> tbvResultados;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private JFXTextField jtxfBusqueda;

    @FXML
    private TableColumn<?, ?> tbcResultado;

    @FXML
    void regresar(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
