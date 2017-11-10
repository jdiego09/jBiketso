package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import jbiketso.model.entities.BikExpediente;
import jbiketso.utils.AppWindowController;

public class UsuariosController implements Initializable {

   BikExpediente expediente;

   @FXML
   private AnchorPane acpRoot;

   @FXML
   private JFXButton jbtnSalir;

   @FXML
   private Button btnGuardar;

   @FXML
   private JFXTextField jtxfCedula, jtxfNombre, jtxfCedulaEncargado, jtxfNombreEncargado, jtxfExpediente, jtxfEstado, jtxfCantidadPersonas, jtxfCantidadDependientes, jtxfIngresoPromedio, jtxfEstSocioEco;
   

   @FXML
   private JFXTextField jtxfFechaIngreso;

   @FXML
   private JFXTextField jtxfFechaSalida;

   @FXML
   private JFXComboBox<?> jcmbTipoAtencion;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @FXML
   void guardarUsuario(ActionEvent event) {

   }

   @FXML
   void limpiar(ActionEvent event) {

   }

   @FXML
   void regresar(ActionEvent event) {
      AppWindowController.getInstance().goHome();
   }

   @FXML
   void agregarMedicamento(ActionEvent event) {

   }

   @FXML
   void agregarPadecimiento(ActionEvent event) {

   }

}
