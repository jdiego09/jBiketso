package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ModulosController implements Initializable{

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private JFXButton jbtnGuardar;

    @FXML
    private JFXTextField jtxfCodigoModulo;

    @FXML
    private JFXTextField jtxfDescripcionModulo;

    @FXML
    private JFXComboBox<?> jcmbEstadoModulo;

    @FXML
    private TableView<?> tbvModulos;

    @FXML
    private TableColumn<?, ?> tbcCodigoModulo;

    @FXML
    private TableColumn<?, ?> tbcDescipcionModulo;

    @FXML
    private TableColumn<?, ?> tbcEstadoModulo;

    @FXML
    private TableColumn<?, ?> tbcUsuarioIngresa;

    @FXML
    private TableColumn<?, ?> tbcFechaIngresa;

    @FXML
    private TableColumn<?, ?> tbcUsuarioModifica;

    @FXML
    private TableColumn<?, ?> tbcFechaModifica;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
