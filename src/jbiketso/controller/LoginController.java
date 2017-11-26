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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jbiketso.model.dao.LoginDao;
import jbiketso.model.entities.BikRolesUsuarios;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Encriptor;
import jbiketso.utils.Parametros;

/**
 * FXML Controller class
 *
 * @author Anayansy
 */
public class LoginController extends Controller {

    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXButton btnIniciarSesion;

    @FXML
    private void iniciarSesion(ActionEvent event) {
        login();
    }

    private void login() {
        if (txtUsuario.getText() != null && !txtUsuario.getText().isEmpty()) {
            if (txtClave.getText() == null || txtClave.getText().isEmpty()) {
                AppWindowController.getInstance().mensaje(AlertType.WARNING, "Error", "Debe indicar la contraseña de acceso.");
                txtClave.requestFocus();
                return;
            }

            LoginDao login = new LoginDao();
            BikUsuariosSistema usuario = login.findByUssCodigo(txtUsuario.getText());
            if (usuario.getUssCodigo() != null && !usuario.getUssCodigo().isEmpty()) {
                if (usuario.getUssContrasena().equals(Encriptor.getInstance().encriptar(txtClave.getText()))) {
                    Parametros.getInstance().setParametro("Usuario", txtUsuario.getText());
                    Aplicacion.getInstance().setUsuario(usuario);
                    String roles = "";
                    String sep = "";
                    for (BikRolesUsuarios r : Aplicacion.getInstance().getUsuario().getBikRolesUsuariosList()) {
                        if (roles == null || roles.isEmpty()) {
                            sep = "";
                        } else {
                            sep = ",";
                        }
                        roles = roles + sep + r.getBikRolesUsuariosPK().getRouRolcodigo();
                    }
                    Aplicacion.getInstance().setRolesUsuario(roles);
                    AppWindowController.getInstance().initApplication();

                    AppWindowController.getInstance().abrirVentanaEnPrincipal("bik_principal", "Center");
                } else {
                    AppWindowController.getInstance().mensaje(AlertType.ERROR, "Acceso denegado", "Contraseña incorrecta.");
                    txtClave.requestFocus();
                    return;
                }
            } else {
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

    @Override
    public void initialize() {

    }

    @FXML
    void iniciarSesionOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    void onKeyPressLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @Override
    public void initialize(String funcion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
