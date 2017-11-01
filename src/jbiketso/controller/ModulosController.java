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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ModuloDao;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class ModulosController implements Initializable {

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private Button btnGuardar, btnLimpiar;

    @FXML
    private JFXTextField jtxfCodigoModulo, jtxfDescripcionModulo;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstadoModulo;

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
    BikModulos moduloSeleccionado;

    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estados.add(new GenValorCombo("A", "Activo"));
        estados.add(new GenValorCombo("I", "Inactivo"));
        jcmbEstadoModulo.setItems(estados);
        nuevoModulo();
        bindModulo()                                                                                                                                                                                                            ;
        cargarModulos();
        bindListaModulos();
        addListenerTable(tbvModulos);

    }

    private void bindModulo() {
        jtxfCodigoModulo.textProperty().bindBidirectional(modulo.codigo);
        jtxfDescripcionModulo.textProperty().bindBidirectional(modulo.descripcion);
        jcmbEstadoModulo.valueProperty().bindBidirectional(modulo.estado);
    }

    private void unbindModulo() {
        jtxfCodigoModulo.textProperty().unbindBidirectional(modulo.codigo);
        jtxfDescripcionModulo.textProperty().unbindBidirectional(modulo.descripcion);
        jcmbEstadoModulo.valueProperty().unbindBidirectional(modulo.estadoProperty());
    }

    private void addListenerTable(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindModulo();
                moduloSeleccionado = (BikModulos) newSelection;
                if (this.modulo == null) {
                    this.modulo = new ModuloDao(moduloSeleccionado.getModCodigo(), moduloSeleccionado.getModDescripcion(), moduloSeleccionado.getModEstado());
                } else {
                    this.modulo.codigo.set(moduloSeleccionado.getModCodigo());
                    this.modulo.descripcion.set(moduloSeleccionado.getModDescripcion());
                    if (moduloSeleccionado.getModEstado().equalsIgnoreCase("a")) {
                        this.modulo.estado.set(new GenValorCombo("A", "Activo"));
                    } else {
                        this.modulo.estado.set(new GenValorCombo("I", "Inactivo"));
                    }
                }
                bindModulo();
            }
        });
    }

    private void cargarModulos() {
        Resultado<ArrayList<BikModulos>> result = this.modulo.findByEstado("A");
        if (!result.getResultado().equals(TipoResultado.ERROR)) {
            modulos = FXCollections.observableArrayList(result.get());
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar módulos", result.getMensaje());
        }
    }

    private void nuevoModulo() {
        this.modulo = new ModuloDao();

    }

    private void bindListaModulos() {
        if (modulos != null) {
            tbvModulos.setItems(modulos);
            tbvModulos.refresh();
        }
        tbcCodigoModulo.setCellValueFactory(new PropertyValueFactory<>("modCodigo"));
        tbcDescipcionModulo.setCellValueFactory(new PropertyValueFactory<>("modDescripcion"));
        tbcEstadoModulo.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
    }

    private void agregarModuloALista(BikModulos modulo) {
        if (!this.modulos.contains(modulo)) {
            this.modulos.add(modulo);
        } else {
            this.modulos.set(this.modulos.indexOf(modulo), modulo);
        }
        tbvModulos.refresh();
    }

    @FXML
    void impiarModulo(ActionEvent event) {
        unbindModulo();
        this.modulo = new ModuloDao();
        bindModulo();
    }

    @FXML
    void guardarModulo(ActionEvent event) {        
        Resultado<BikModulos> nuevo = this.modulo.save();
        if (nuevo.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar módulo", nuevo.getMensaje());
            return;
        }
        agregarModuloALista(nuevo.get());
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar módulo", nuevo.getMensaje());
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

}
