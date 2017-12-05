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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.BitacoraAtencionDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.UsuarioDao;
import jbiketso.model.entities.BikBitacoraAtencion;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikSede;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BitacoraAtencionController extends Controller implements Initializable {

    BikBitacoraAtencion bitacora;
    BikSede sede;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private Label lblTitulo;

    @FXML
    private Button btnGuardarAtencion;

    @FXML
    private Button btnLimpiarDatos;

    @FXML
    private Button btnImprimir;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipoAtencion;

    @FXML
    private JFXDatePicker jdtpFecha;

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

    @FXML
    private JFXTextArea jtxfDetalle;

    @FXML
    private Button btnAgregarAtencion;

    @XmlTransient
    public ObservableList<BikBitacoraAtencion> detalleBitacora = FXCollections
            .observableArrayList();
    @XmlTransient
    ObservableList<GenValorCombo> tipoAtencion = FXCollections
            .observableArrayList();

    private void setTiposAtencion() {
        tipoAtencion.clear();
        switch (this.getAccion().toLowerCase()) {
            case "q":
                //ingresó en modo consulta
                lblTitulo.setText("Consulta de atención");
                btnAgregarAtencion.setDisable(true);
                btnImprimir.setVisible(true);
                tipoAtencion.add(new GenValorCombo("I", "Ingreso al centro"));
                tipoAtencion.add(new GenValorCombo("S", "Salida del centro"));
                tipoAtencion.add(new GenValorCombo("C", "Chequeo médico"));
                tipoAtencion.add(new GenValorCombo("T", "Toma de signos"));
                tipoAtencion.add(new GenValorCombo("A", "Atención brindada"));
                tipoAtencion.add(new GenValorCombo("%", "Todos"));
                break;
            case "a":
                //Asistencia
                lblTitulo.setText("Control de asistencia");
                tipoAtencion.add(new GenValorCombo("I", "Ingreso al centro"));
                tipoAtencion.add(new GenValorCombo("S", "Salida del centro"));
                break;
            case "c":
                //chequeos médicos
                lblTitulo.setText("Chequeos médicos");
                tipoAtencion.add(new GenValorCombo("C", "Chequeo médico"));
                break;
            case "t":
                //toma de signos                
                lblTitulo.setText("Toma de signos");
                tipoAtencion.add(new GenValorCombo("T", "Toma de signos"));
                break;
            case "n":
                //atención normal
                lblTitulo.setText("Servicio brindado");
                tipoAtencion.add(new GenValorCombo("A", "Atención brindada"));
                break;
        }
        jcmbTipoAtencion.setItems(tipoAtencion);
    }

    private void bindBitacora() {
        jcmbTipoAtencion.valueProperty().bindBidirectional(this.bitacora.getTipoAtencionProperty());
        //jdtpFecha.valueProperty().bindBidirectional(this.bitacora.getFechaProperty());
        jtxfDetalle.textProperty().bindBidirectional(this.bitacora.getDetalleProperty());
    }

    private void bindPersona() {
        jtxfCedula.textProperty().bindBidirectional(this.bitacora.getBiaCodusuario().getUsuPercodigo().getPerCedulaProperty());
        jtxfNombre.textProperty().bindBidirectional(this.bitacora.getBiaCodusuario().getUsuPercodigo().getNombreCompletoProperty());
    }

    private void unbindBitacora() {
        jcmbTipoAtencion.valueProperty().unbindBidirectional(this.bitacora.getTipoAtencionProperty());
        //jdtpFecha.valueProperty().unbindBidirectional(this.bitacora.getFechaProperty());
        jtxfDetalle.textProperty().unbindBidirectional(this.bitacora.getDetalleProperty());
    }

    private void unbindPersona() {
        jtxfCedula.textProperty().unbindBidirectional(this.bitacora.getBiaCodusuario().getUsuPercodigo().getPerCedulaProperty());
        jtxfNombre.textProperty().unbindBidirectional(this.bitacora.getBiaCodusuario().getUsuPercodigo().getNombreCompletoProperty());
    }

    private void bindDetalleBitacora() {
        if (detalleBitacora != null) {
            tbvBitacora.setItems(detalleBitacora);
            tbvBitacora.refresh();
        }
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fechaString"));
        tbcDetalle.setCellValueFactory(new PropertyValueFactory<>("biaDetalle"));
    }

    private void nuevaAtencion() {
        this.bitacora = new BikBitacoraAtencion();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    @FXML
    void agregarAtencion(ActionEvent event) {
        agregarAtencionALista(bitacora);
        unbindBitacora();
        nuevaAtencion();
        bindBitacora();
        this.jcmbTipoAtencion.requestFocus();
    }

    @FXML
    public void busqueda(ActionEvent event) {
// Llamar ventana busqueda
        BusquedaController busquedaController = (BusquedaController) AppWindowController.getInstance().getController("bik_busqueda");
        busquedaController.busquedaUsuarios();
        AppWindowController.getInstance().goViewInWindowModal("bik_busqueda", getStage());
        BikPersona buscado = (BikPersona) busquedaController.getResultado();

        if (buscado != null) {
            this.jtxfCedula.setText(buscado.getPerCedula());
            traerUsuario();
        }
    }

    @FXML
    public void cedulaOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            traerUsuario();
        }
    }

    private void traerUsuario() {
        String cedula = jtxfCedula.getText();
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(cedula);
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            this.jtxfCedula.requestFocus();
            return;
        }
        unbindPersona();
        unbindBitacora();
        Resultado<BikUsuario> resultado = UsuarioDao.getInstance().getUsuarioByCedula(cedula);
        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar usuario", resultado.getMensaje());
            return;
        }
        if (resultado.get() != null && resultado.get().getUsuCodigo() != null && resultado.get().getUsuCodigo() > 0) {
            this.bitacora.setBiaCodusuario(resultado.get());
            //carga el detalle de la atencion recibida
            Resultado<ArrayList<BikBitacoraAtencion>> atencionRecibida = BitacoraAtencionDao.getInstance().getDetalleBitacora(this.bitacora);
            detalleBitacora.clear();
            atencionRecibida.get().stream().forEach(detalleBitacora::add);

        } else {
            nuevaAtencion();
            this.bitacora.getBiaCodusuario().getUsuPercodigo().setPerCedula(cedula);
        }
        bindPersona();
        bindBitacora();
    }

    @FXML
    public void detalleOnKeyPress(KeyEvent event) {

    }

    @FXML
    void guardarAtencion(ActionEvent event) {

    }

    @FXML
    void limpiarDatos(ActionEvent event) {
        unbindBitacora();
        this.detalleBitacora.clear();
        nuevaAtencion();
        bindBitacora();
        this.jcmbTipoAtencion.requestFocus();
    }

    @FXML
    public void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @Override
    public void initialize() {
        init();
        setTiposAtencion();
        jcmbTipoAtencion.getSelectionModel().selectFirst();
        jcmbTipoAtencion.setDisable(true);
        if (this.getAccion().equalsIgnoreCase("q")) {
            btnGuardarAtencion.setDisable(true);
            btnAgregarAtencion.setDisable(true);
        }
    }

    private void init() {
        nuevaAtencion();
        bindBitacora();
        bindDetalleBitacora();
    }

    private void agregarAtencionALista(BikBitacoraAtencion bitacora) {
        if (bitacora != null && !bitacora.getBiaDetalle().isEmpty()) {
            bitacora.setBiaUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
            bitacora.setBiaFechainicio(new Date());
            bitacora.setBiaFechaingresa(new Date());
            bitacora.setBiaCodusuario(bitacora.getBiaCodusuario());
            BitacoraAtencionDao.getInstance().setBitacora(bitacora);
            Resultado<BikBitacoraAtencion> resultado = BitacoraAtencionDao.getInstance().save();

            if (resultado.getResultado().equals(TipoResultado.ERROR)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar atención", resultado.getMensaje());
                return;
            } else {
                 AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar atención", resultado.getMensaje());
                if (!this.detalleBitacora.contains(bitacora)) {
                    this.detalleBitacora.add(bitacora);
                }
            }
        }

        tbvBitacora.refresh();
    }

    @FXML
    void imprimir(ActionEvent event) {

    }
}
