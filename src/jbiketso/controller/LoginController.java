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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.persistence.NoResultException;
import jbiketso.model.dao.LoginDao;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.AppWindowController;
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
            Parametros.getInstance().setParametro("Usuario", txtUsuario.getText());
            LoginDao login = new LoginDao();
            BikUsuariosSistema usuario = login.findByUssCodigo(Parametros.getInstance().getParametro("Usuario"));
            try {
                Node boton = (Node) event.getSource();
                AppWindowController.getInstance().setMainStage((Stage) boton.getScene().getWindow());
                AppWindowController.getInstance().abrirVentana("bik_principal", "Bikétsö - Principal", true);

            } catch (NoResultException nre){
                //mensaje el usuario no está registrado
            }catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
           //
        }
    }

}
