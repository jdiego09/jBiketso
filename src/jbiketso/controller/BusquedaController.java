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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jbiketso.model.entities.BikPersona;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BusquedaController implements Initializable {

   ArrayList<BikPersona> personasList = new ArrayList<>();

   @FXML
   private JFXButton jbtnCancelar;

   @FXML
   private VBox vbxCriterios;

   @FXML
   private JFXButton jbtnLimpiar;

   @FXML
   private BorderPane root;

   @FXML
   private Pane pnlResultados;

   @FXML
   private JFXButton jbtnFiltrar;

   @FXML
   private TableView<?> tbvResultados;

   @FXML
   private JFXButton jbtnAceptar;

   private Object resultado;
   private EventHandler<KeyEvent> keyEnter;
   private Boolean pressEnter = false;

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
      keyEnter = (KeyEvent event) -> {
         if (event.getCode() == KeyCode.ENTER) {
            pressEnter = true;
            jbtnFiltrar.fire();
            pressEnter = false;

            //sendTabEvent(event);
         } else if (event.getCode() == KeyCode.ESCAPE) {
            //cerrar();
         }
      };
      resultado = null;
   }
/*
   @Override
   public void initialize() {
      Platform.runLater(() -> {
         for (Node object : vbxCriterios.getChildren()) {
            if (object.isFocusTraversable()) {
               object.requestFocus();
               break;
            }
         }
      });
      resultado = null;
   }*/
   
   public Object getResultado() {
        return resultado;
    }

   @FXML
   private void regresar(ActionEvent event) {
      resultado = tbvResultados.getSelectionModel().getSelectedItem();
      jbtnFiltrar.getScene().setRoot(new Pane());
   }

   private void setBusquedaPersonas() {
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
