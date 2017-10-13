/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jbiketso.model.dao.LoginDao;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Encriptor;
import jbiketso.utils.Parametros;

/**
 * FXML Controller class
 *
 * @author Anayansy
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblHora;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXButton btnIniciarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        if (txtUsuario.getText() != null && !txtUsuario.getText().isEmpty()) {
            if (txtClave.getText() == null || txtClave.getText().isEmpty()) {
               AppWindowController.getInstance().mensaje(AlertType.WARNING, "Error", "Debe indicar la contraseña de acceso.");
               txtClave.requestFocus();
               return;
            }
            
            LoginDao login = new LoginDao();
            BikUsuariosSistema usuario = login.findByUssCodigo(txtUsuario.getText());
            if (usuario.getUssCodigo() != null && !usuario.getUssCodigo().isEmpty()) {
                Parametros.getInstance().setParametro("Usuario", txtUsuario.getText());
                if (usuario.getUssContrasena().equals(Encriptor.getInstance().encriptar(txtClave.getText()))) {
                    Node boton = (Node) event.getSource();
                    AppWindowController.getInstance().setMainStage((Stage) boton.getScene().getWindow());
                    AppWindowController.getInstance().abrirVentana("bik_principal", "Bikétsö - Principal", true);
                } else
                {
                    AppWindowController.getInstance().mensaje(AlertType.ERROR, "Acceso denegado", "Contraseña incorrecta.");
                    txtClave.requestFocus();                    
                    return;
                }
            } else{
                AppWindowController.getInstance().mensaje(AlertType.ERROR, "Acceso denegado", "El usuario: " + txtUsuario.getText() + ", no se encuentra registrado.");
                txtUsuario.requestFocus();
                return;
            }                
        } else {
            AppWindowController.getInstance().mensaje(AlertType.WARNING, "Error", "Debe indicar el código de usuario.");
            txtUsuario.requestFocus();
            return;
        }
    }

}
