/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.entities.BikBitacoraAtencion;
import jbiketso.model.entities.BikSede;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BitacoraAtencionController extends Controller implements Initializable {

    BikBitacoraAtencion bitacora;
    BikSede sede;
    @XmlTransient
    public ObservableList<BikBitacoraAtencion> detalleBitacora = FXCollections
            .observableArrayList();

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
    private JFXTextArea jtxfDetalle;

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

    @XmlTransient
    ObservableList<GenValorCombo> tipoAtencion = FXCollections
            .observableArrayList();

    private void setTiposAtencion() {
        tipoAtencion.clear();
        tipoAtencion.add(new GenValorCombo("A", "Asistencia"));
        tipoAtencion.add(new GenValorCombo("C", "Chequeo médico"));
        tipoAtencion.add(new GenValorCombo("I", "Ingreso al centro"));
        tipoAtencion.add(new GenValorCombo("S", "Salida del centro"));
        tipoAtencion.add(new GenValorCombo("T", "Toma de signos"));
        tipoAtencion.add(new GenValorCombo("%", "Todos"));

        jcmbTipoAtencion.setItems(tipoAtencion);
    }

    private void bindBitacora() {
        jcmbTipoAtencion.valueProperty().bindBidirectional(this.bitacora.getTipoAtencionProperty());
        jdtpFecha.valueProperty().bindBidirectional(this.bitacora.getFechaInicioProperty());
        jtxfCedula.textProperty().bindBidirectional(this.bitacora.getBikUsuario().getUsuPercodigo().getPerCedulaProperty());
        jtxfNombre.textProperty().bindBidirectional(this.bitacora.getBikUsuario().getUsuPercodigo().getNombreCompletoProperty());
        jtxfDetalle.textProperty().bindBidirectional(this.bitacora.getDetalleProperty());
    }

    private void unbindBitacora() {
        jcmbTipoAtencion.valueProperty().unbindBidirectional(this.bitacora.getTipoAtencionProperty());
        jdtpFecha.valueProperty().unbindBidirectional(this.bitacora.getFechaInicioProperty());
        jtxfCedula.textProperty().unbindBidirectional(this.bitacora.getBikUsuario().getUsuPercodigo().getPerCedulaProperty());
        jtxfNombre.textProperty().unbindBidirectional(this.bitacora.getBikUsuario().getUsuPercodigo().getNombreCompletoProperty());
        jtxfDetalle.textProperty().unbindBidirectional(this.bitacora.getDetalleProperty());
    }

    private void bindDetalleBitacora() {
        if (detalleBitacora != null) {
            tbvBitacora.setItems(detalleBitacora);
            tbvBitacora.refresh();
        }
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("biaFechainicio"));
        tbcDetalle.setCellValueFactory(new PropertyValueFactory<>("biaDetalle"));
    }

    private void nuevaAtencion() {
        this.bitacora = new BikBitacoraAtencion();
        this.bitacora.setBikUsuario(new BikUsuario());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    @FXML
    private Button btnAgregar;

    @FXML
    void agregar(MouseEvent event) {
        agregarAtencionALista(bitacora);
        unbindBitacora();
        nuevaAtencion();
        bindBitacora();
        this.jcmbTipoAtencion.requestFocus();
    }

    @FXML
    void busqueda(ActionEvent event) {

    }

    @FXML
    void cedulaOnEnterKey(KeyEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

    @FXML
    void limpiar(ActionEvent event) {
        unbindBitacora();
        this.detalleBitacora.clear();
        nuevaAtencion();
        bindBitacora();
        this.jcmbTipoAtencion.requestFocus();
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
        setTiposAtencion();
        nuevaAtencion();
        bindBitacora();
        bindDetalleBitacora();
        jcmbTipoAtencion.getSelectionModel().selectFirst();
    }

    @Override
    public void initialize(String funcion) {
    }

    private void agregarAtencionALista(BikBitacoraAtencion bitacora) {
        //                                                                   (bitacora.getBikBitacoraAtencionPK().getBiaCodigo() == null || bitacora.getBikBitacoraAtencionPK().getBiaCodigo() <= 0) &&
        if (bitacora != null && bitacora.getBikBitacoraAtencionPK() != null && !bitacora.getBiaDetalle().isEmpty()) {
            BikBitacoraAtencion nueva = new BikBitacoraAtencion();
            nueva.setBiaDetalle(bitacora.getBiaDetalle());
            nueva.setBiaUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
            nueva.setBiaFechaingresa(new Date());
            nueva.setBikUsuario(this.bitacora.getBikUsuario());
            if (!this.detalleBitacora.contains(nueva)) {
                this.detalleBitacora.add(nueva);
            }
        } else {
            bitacora.setBiaUsuariomodifica(Aplicacion.getInstance().getUsuario().getUssCodigo());
            bitacora.setBiaFechamodifica(new Date());
            this.detalleBitacora.set(this.detalleBitacora.indexOf(bitacora), bitacora);
        }
        tbvBitacora.refresh();
    }
}
