/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import jbiketso.model.entities.BikBitacoraAtencion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BitacoraAtencionController extends Controller implements Initializable {

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipoAtencion;

    @FXML
    private JFXDatePicker jdtpFecha;

    @FXML
    private JFXTextField jtxfDetalle;

    @FXML
    private JFXTextField jtxfCedula;

    @FXML
    private JFXButton btnBusqueda;

    @FXML
    private JFXTextField jtxfNombre;

    @FXML
    private TableView<BikBitacoraAtencion> tbvBitacora;

    @FXML
    private TableColumn<BikBitacoraAtencion, LocalDate> tbcFecha;

    @FXML
    private TableColumn<BikBitacoraAtencion, String> tbcDetalle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    @FXML
    void busqueda(ActionEvent event) {

    }

    @FXML
    void cedulaOnEnterKey(KeyEvent event) {

    }

    @FXML
    void guardarModulo(ActionEvent event) {

    }

    @FXML
    void impiarModulo(ActionEvent event) {

    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @Override
    public void initialize() {
        init();
    }

    private void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
