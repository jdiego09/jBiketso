package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import jbiketso.utils.AppWindowController;

public class UsuariosController {

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private Button btnGuardar;

    @FXML
    private JFXTextField jtxfCedula;

    @FXML
    private JFXTextField jtxfNombre;

    @FXML
    private JFXTextField jtxfCedulaEncargado;

    @FXML
    private JFXTextField jtxfNombreEncargado;

    @FXML
    private JFXTextField jtxfExpediente;

    @FXML
    private JFXTextField jtxfFechaIngreso;

    @FXML
    private JFXTextField jtxfFechaSalida;

    @FXML
    private JFXTextField jtxfEstado;

    @FXML
    private JFXComboBox<?> jcmbTipoAtencion;

    @FXML
    private JFXTextField jtxfCantidadPersonas;

    @FXML
    private JFXTextField jtxfCantidadDependientes;

    @FXML
    private JFXTextField jtxfIngresoPromedio;

    @FXML
    private JFXTextField jtxfEstSocioEco;

    @FXML
    void guardarModulo(ActionEvent event) {

    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

}
