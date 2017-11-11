package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.entities.BikExpediente;
import jbiketso.model.entities.BikMedicamento;
import jbiketso.model.entities.BikPadecimiento;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;

public class UsuariosController implements Initializable {

    BikExpediente expediente;
    BikPadecimiento padecimiento;
    BikMedicamento medicamento;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private Button btnGuardar;

    @FXML
    private JFXTextField jtxfCedula, jtxfNombre, jtxfCedulaEncargado, jtxfNombreEncargado, jtxfExpediente, jtxfCantidadPersonas, jtxfCantidadDependientes, jtxfIngresoPromedio, jtxfEstSocioEco;

    @FXML
    private JFXDatePicker jdtpFechaIngreso, jdtpFechaSalida;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipoAtencion, jcmbEstado;

    @FXML
    private JFXTextField jtxfPadecimiento;

    @FXML
    private JFXTextArea jtxaObservacionesPadecimiento;
    @FXML
    private JFXButton jbtnEliminarPadecimiento;

    @FXML
    private JFXButton jbtnAgregarPadecimiento;
    @FXML
    private TableView<BikPadecimiento> tbvPadecimiento;

    @FXML
    private TableColumn<BikPadecimiento, String> tbcPadecimiento;

    @FXML
    private TableColumn<BikPadecimiento, String> tbcObservacionesPadecimiento;

    @FXML
    private JFXTextField jtxfMedicamento;

    @FXML
    private JFXTextField jtxfDosisMedicamento;

    @FXML
    private JFXTextField jtxfHorarioMedicamento;

    @FXML
    private JFXTextArea jtxaObservacionesMedicameto;

    @FXML
    private JFXButton jbtnAgregarMedicamento;
    
    @FXML
    private JFXButton jbtnEliminarMedicamento;
    @FXML
    private TableView<BikMedicamento> tbvMedicamentos;

    @FXML
    private TableColumn<BikMedicamento, String> tbcMedicamento;

    @FXML
    private TableColumn<BikMedicamento, String> tbcDosis;

    @FXML
    private TableColumn<BikMedicamento, String> tbcHorario;

    @FXML
    private TableColumn<BikMedicamento, String> tbcObservacionesMedicamento;

    @XmlTransient
    private ObservableList<BikPadecimiento> listaPadecimientos = FXCollections.observableArrayList();
    @XmlTransient
    private ObservableList<BikMedicamento> listaMedicamentos = FXCollections.observableArrayList();

    private void nuevoExpediente() {
        this.expediente = new BikExpediente();
    }

    private void bindUsuario() {
        //usuario
        this.jtxfCedula.textProperty().bindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedulaProperty());
        this.jtxfNombre.textProperty().bindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompletoProperty());
        //encargado
        this.jtxfCedulaEncargado.textProperty().bindBidirectional(this.expediente.getExpCodencargado().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().bindBidirectional(this.expediente.getExpCodencargado().getNombreCompletoProperty());
        //expediente
        this.jtxfExpediente.textProperty().bindBidirectional(this.expediente.getCodigoProperty());
        this.jdtpFechaIngreso.valueProperty().bindBidirectional(this.expediente.getExpFechaIngresoProperty());
        this.jdtpFechaSalida.valueProperty().bindBidirectional(this.expediente.getExpFechaSalidaProperty());
        this.jcmbEstado.valueProperty().bindBidirectional(this.expediente.getEstadoProperty());
        this.jcmbTipoAtencion.valueProperty().bindBidirectional(this.expediente.getTipoAtencionProperty());
        this.jtxfCantidadPersonas.textProperty().bindBidirectional(this.expediente.getPersonasHogarProperty());
        this.jtxfCantidadDependientes.textProperty().bindBidirectional(this.expediente.getPersonasDependientesProperty());
        this.jtxfIngresoPromedio.textProperty().bindBidirectional(this.expediente.getIngresoPromedioProperty());
        this.jtxfEstSocioEco.textProperty().bindBidirectional(this.expediente.getEstudioSocioEconomicoProperty());
    }

    private void bindPadecimiento() {
        this.jtxfPadecimiento.textProperty().bindBidirectional(this.padecimiento.getPadecimientoProperty());
        this.jtxaObservacionesPadecimiento.textProperty().bindBidirectional(this.padecimiento.getObservacionesProperty());
    }

    private void unbindUsuario() {
        //usuario
        this.jtxfCedula.textProperty().unbindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedulaProperty());
        this.jtxfNombre.textProperty().unbindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompletoProperty());
        //encargado
        this.jtxfCedulaEncargado.textProperty().unbindBidirectional(this.expediente.getExpCodencargado().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().unbindBidirectional(this.expediente.getExpCodencargado().getNombreCompletoProperty());
        //expediente
        this.jtxfExpediente.textProperty().unbindBidirectional(this.expediente.getCodigoProperty());
        this.jdtpFechaIngreso.valueProperty().unbindBidirectional(this.expediente.getExpFechaIngresoProperty());
        this.jdtpFechaSalida.valueProperty().unbindBidirectional(this.expediente.getExpFechaSalidaProperty());
        this.jcmbEstado.valueProperty().unbindBidirectional(this.expediente.getEstadoProperty());
        this.jcmbTipoAtencion.valueProperty().unbindBidirectional(this.expediente.getTipoAtencionProperty());
        this.jtxfCantidadPersonas.textProperty().unbindBidirectional(this.expediente.getPersonasHogarProperty());
        this.jtxfCantidadDependientes.textProperty().unbindBidirectional(this.expediente.getPersonasDependientesProperty());
        this.jtxfIngresoPromedio.textProperty().unbindBidirectional(this.expediente.getIngresoPromedioProperty());
        this.jtxfEstSocioEco.textProperty().unbindBidirectional(this.expediente.getEstudioSocioEconomicoProperty());
    }

    public void bindListaPadecimientos() {
        if (listaPadecimientos != null) {
            tbvPadecimiento.setItems(listaPadecimientos);
            tbvPadecimiento.refresh();
        }
        tbcPadecimiento.setCellValueFactory(new PropertyValueFactory<>("padPadecimiento"));
        tbcObservacionesPadecimiento.setCellValueFactory(new PropertyValueFactory<>("padObservaciones"));
    }

    public void bindListaMedicamentos() {
        if (listaMedicamentos != null) {
            tbvMedicamentos.setItems(listaMedicamentos);
            tbvMedicamentos.refresh();
        }
        tbcMedicamento.setCellValueFactory(new PropertyValueFactory<>("medMedicamento"));
        tbcDosis.setCellValueFactory(new PropertyValueFactory<>("medDosis"));
        tbcHorario.setCellValueFactory(new PropertyValueFactory<>("medHorario"));
        tbcObservacionesMedicamento.setCellValueFactory(new PropertyValueFactory<>("medObservaciones"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        

        nuevoExpediente();
        unbindUsuario();
        bindListaPadecimientos();
        bindListaMedicamentos();

        addListenerTablePadecimientos(tbvPadecimiento);
        //addListenerTableMedicamentos(tbvMedicamentos);

    }

    private void addListenerTablePadecimientos(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //unbindDireccion();
                //this.direccion = (BikDireccion) newSelection;
                //bindDireccion();
            }
        });
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
