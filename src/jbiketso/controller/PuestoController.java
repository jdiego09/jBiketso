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
import jbiketso.model.dao.PuestoDao;
import jbiketso.model.entities.BikPuesto;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class PuestoController extends Controller implements Initializable {

    @FXML
    private AnchorPane acpRoot;

    BikPuesto puesto;
    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikPuesto> listaPuestos = FXCollections.observableArrayList();

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpiar;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private JFXTextField jtxfPuesto;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstado;

    @FXML
    private TableView<BikPuesto> tbvPuestos;

    @FXML
    private TableColumn<BikPuesto, Integer> tbcCodigo;

    @FXML
    private TableColumn<BikPuesto, String> tbcDescripcion;

    @FXML
    private TableColumn<BikPuesto, String> tbcEstado;

    @FXML
    private JFXButton jbtnEliminar;

    @FXML
    void eliminar(ActionEvent event) {
        Resultado<String> resultado = PuestoDao.getInstance().deletePuesto(puesto);
        if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Eliminar pesto", "Puesto eliminado exitosamente.");
            cargarPuestos();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar puesto", resultado.getMensaje());
        }
    }

    @FXML
    void guardarPuesto(ActionEvent event) {
        if (this.puesto.getPueDescripcion() != null && !this.puesto.getPueDescripcion().isEmpty()) {
            PuestoDao.getInstance().setPuesto(this.puesto);
            Resultado<BikPuesto> nuevo = PuestoDao.getInstance().save();
            if (nuevo.getResultado().equals(TipoResultado.ERROR)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar puesto", nuevo.getMensaje());
                return;
            }
            agregarPuestoALista(nuevo.get());
            AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar módulo", nuevo.getMensaje());
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Guardar módulo", "Debe indicar la descripción del puesto.");
        }
    }

    @FXML
    void salir(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @FXML
    void limpiar(ActionEvent event) {
        unbindPuesto();
        nuevoPuesto();
        bindPuesto();
    }

    private void agregarPuestoALista(BikPuesto nuevo) {
        if (!this.listaPuestos.contains(nuevo)) {
            this.listaPuestos.add(nuevo);
        } else {
            this.listaPuestos.set(this.listaPuestos.indexOf(nuevo), nuevo);
        }
        tbvPuestos.refresh();
    }

    private void bindPuesto() {
        this.jtxfPuesto.textProperty().bindBidirectional(this.puesto.getPueDescripcionProperty());
        this.jcmbEstado.valueProperty().bindBidirectional(this.puesto.getEstadoProperty());
    }

    private void unbindPuesto() {
        this.jtxfPuesto.textProperty().unbindBidirectional(this.puesto.getPueDescripcionProperty());
        this.jcmbEstado.valueProperty().unbindBidirectional(this.puesto.getEstadoProperty());
    }

    private void nuevoPuesto() {
        this.puesto = new BikPuesto();
    }

    private void init() {
        this.estados.clear();
        this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("I", "Egresado"));
        this.jcmbEstado.setItems(this.estados);
        nuevoPuesto();
        cargarPuestos();
        bindPuesto();
        bindListaPuestos();
        this.jcmbEstado.getSelectionModel().selectFirst();
        addListenerTable(tbvPuestos);
    }

    private void cargarPuestos() {
        Resultado<ArrayList<BikPuesto>> result = PuestoDao.getInstance().findAll();
        if (!result.getResultado().equals(TipoResultado.ERROR)) {
            listaPuestos.clear();
            result.get().stream().forEach(listaPuestos::add);
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar puestos", result.getMensaje());
        }
    }

    private void addListenerTable(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindPuesto();
                this.puesto = (BikPuesto) newSelection;
                bindPuesto();
            }
        });
    }

    public void bindListaPuestos() {
        if (listaPuestos != null) {
            tbvPuestos.setItems(listaPuestos);
            tbvPuestos.refresh();
        }
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("pueCodigo"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("pueDescripcion"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
    }

    @Override
    public void initialize() {
        init();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

}
