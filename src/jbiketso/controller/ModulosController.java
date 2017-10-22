package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ModuloDao;
import jbiketso.model.entities.BikModulos;

public class ModulosController implements Initializable {

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXButton jbtnSalir, jbtnGuardar;

    @FXML
    private JFXTextField jtxfCodigoModulo, jtxfDescripcionModulo;

    @FXML
    private JFXComboBox<?> jcmbEstadoModulo;

    @FXML
    private TableView<BikModulos> tbvModulos;

    @FXML
    private TableColumn<BikModulos, String> tbcCodigoModulo;

    @FXML
    private TableColumn<BikModulos, String> tbcDescipcionModulo;

    @FXML
    private TableColumn<BikModulos, String> tbcEstadoModulo;

    @XmlTransient
    public ObservableList<BikModulos> modulos = FXCollections
            .observableArrayList();

    ModuloDao modulo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nuevoModulo();
        cargarModulos();
        bindModulo();

    }

    private void cargarModulos() {
        modulos = FXCollections.observableArrayList(this.modulo.findByEstado("A"));
    }

    private void nuevoModulo() {
        this.modulo = new ModuloDao();
    }

    private void bindModulo() {
        if (modulos != null) {
            tbvModulos.setItems(modulos);
            tbvModulos.refresh();
        }
        tbcCodigoModulo.setCellValueFactory(new PropertyValueFactory<>("modCodigo"));
        tbcDescipcionModulo.setCellValueFactory(new PropertyValueFactory<>("modDescripcion"));
        tbcEstadoModulo.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
    }

    @FXML
    void guardarModulo(ActionEvent event) {
        this.modulo = new ModuloDao(jtxfCodigoModulo.getText(), jtxfDescripcionModulo.getText(), "A");
        this.modulos.add(this.modulo.save());
        
    }

    @FXML
    void regresar(ActionEvent event) {
        jbtnSalir.getScene().getWindow().hide();
    }

}
