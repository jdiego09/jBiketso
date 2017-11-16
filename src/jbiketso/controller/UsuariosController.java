package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ExpedienteDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.UsuarioDao;
import jbiketso.model.entities.BikExpediente;
import jbiketso.model.entities.BikMedicamento;
import jbiketso.model.entities.BikPadecimiento;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikSede;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class UsuariosController extends Controller implements Initializable {
    
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
    private JFXTextArea jtxaObservacionesMedicamento;
    
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
    
    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();
    
    @XmlTransient
    ObservableList<GenValorCombo> tiposAtencion = FXCollections
            .observableArrayList();
    
    private void bindPersonaUsuario() {
        //usuario
        this.jtxfCedula.textProperty().bindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedulaProperty());
        this.jtxfNombre.textProperty().bindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompletoProperty());
    }
    
    private void unbindPersonaUsuario() {
        //usuario
        this.jtxfCedula.textProperty().unbindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedulaProperty());
        this.jtxfNombre.textProperty().unbindBidirectional(this.expediente.getExpUsucodigo().getUsuPercodigo().getNombreCompletoProperty());
    }
    
    private void bindPersonaEncargado() {
        //encargado
        this.jtxfCedulaEncargado.textProperty().bindBidirectional(this.expediente.getExpCodencargado().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().bindBidirectional(this.expediente.getExpCodencargado().getNombreCompletoProperty());
    }
    
    private void unbindPersonaEncargado() {
        //encargado
        this.jtxfCedulaEncargado.textProperty().unbindBidirectional(this.expediente.getExpCodencargado().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().unbindBidirectional(this.expediente.getExpCodencargado().getNombreCompletoProperty());
    }
    
    private void bindExpediente() {
        bindPersonaUsuario();
        bindPersonaEncargado();
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
    
    private void unbindPadecimiento() {
        this.jtxfPadecimiento.textProperty().unbindBidirectional(this.padecimiento.getPadecimientoProperty());
        this.jtxaObservacionesPadecimiento.textProperty().unbindBidirectional(this.padecimiento.getObservacionesProperty());
    }
    
    private void bindMedicamento() {
        this.jtxfMedicamento.textProperty().bindBidirectional(this.medicamento.getMedicamentoProperty());
        this.jtxfDosisMedicamento.textProperty().bindBidirectional(this.medicamento.getDosisProperty());
        this.jtxfHorarioMedicamento.textProperty().bindBidirectional(this.medicamento.getHorarioProperty());
        this.jtxaObservacionesMedicamento.textProperty().bindBidirectional(this.medicamento.getObservacionesProperty());
    }
    
    private void unbindMedicamento() {
        this.jtxfMedicamento.textProperty().unbindBidirectional(this.medicamento.getMedicamentoProperty());
        this.jtxfDosisMedicamento.textProperty().unbindBidirectional(this.medicamento.getDosisProperty());
        this.jtxfHorarioMedicamento.textProperty().unbindBidirectional(this.medicamento.getHorarioProperty());
        this.jtxaObservacionesMedicamento.textProperty().unbindBidirectional(this.medicamento.getObservacionesProperty());
    }
    
    private void unbindExpediente() {
        //unbindPersonaUsuario();
        //unbindPersonaEncargado();

        //encargado
        this.jtxfCedulaEncargado.textProperty().bindBidirectional(this.expediente.getExpCodencargado().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().bindBidirectional(this.expediente.getExpCodencargado().getNombreCompletoProperty());
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
        
        this.jtxfCantidadPersonas.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        this.jtxfCantidadDependientes.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        this.jtxfIngresoPromedio.setTextFormatter(new TextFormatter<>(new BigDecimalStringConverter()));
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
    
    private void nuevoExpediente() {
        this.expediente = new BikExpediente(true);
        this.expediente.setExpSedcodigo(Aplicacion.getInstance().getDefaultSede());
    }
    
    private void nuevoPadecimiento() {
        this.padecimiento = new BikPadecimiento();
    }
    
    private void nuevoMedicamento() {
        this.medicamento = new BikMedicamento();
    }
    
    private void init(){
       this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("I", "Inactivo"));
        this.jcmbEstado.setItems(this.estados);
        
        this.tiposAtencion.add(new GenValorCombo("D", "Por día"));
        this.tiposAtencion.add(new GenValorCombo("P", "Permanente 24 horas"));
        this.jcmbTipoAtencion.setItems(this.tiposAtencion);
        nuevoExpediente();
        nuevoPadecimiento();
        nuevoMedicamento();
        bindExpediente();
        bindListaPadecimientos();
        bindListaMedicamentos();
        
        addListenerTablePadecimientos(tbvPadecimiento);
        addListenerTableMedicamentos(tbvMedicamentos);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {        
        init();        
    }
    
    private void addListenerTablePadecimientos(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindPadecimiento();
                this.padecimiento = (BikPadecimiento) newSelection;
                bindPadecimiento();
            }
        });
    }
    
    private void addListenerTableMedicamentos(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindMedicamento();
                this.medicamento = (BikMedicamento) newSelection;
                bindMedicamento();
            }
        });
    }
    
    @FXML
    void agregarMedicamento(ActionEvent event) {
        
    }
    
    @FXML
    void agregarPadecimiento(ActionEvent event) {
        
    }
    
    @FXML
    void eliminarMedicamento(ActionEvent event) {
        
    }
    
    @FXML
    void eliminarPadecimiento(ActionEvent event) {
        
    }
    
    @FXML
    void guardarUsuario(ActionEvent event) {
        //guarda al usuario
        if (this.expediente.getExpUsucodigo().getUsuCodigo() == null || this.expediente.getExpUsucodigo().getUsuCodigo() <= 0) {
            this.expediente.getExpUsucodigo().setUsuCodencargadolegal(this.expediente.getExpCodencargado());
            this.expediente.getExpUsucodigo().setUsuSedcodigo(this.expediente.getExpSedcodigo());
            this.expediente.getExpUsucodigo().setUsuEstado(this.expediente.getExpEstado());
            UsuarioDao.getInstance().setUsuario(this.expediente.getExpUsucodigo());
            Resultado<BikUsuario> resultado = UsuarioDao.getInstance().save();
            
            if (resultado.getResultado().equals(TipoResultado.ERROR)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar Usuario", resultado.getMensaje());
                return;
            }
            this.expediente.setExpUsucodigo(resultado.get());
        }
        //guarda el expediente
        if (this.expediente.getExpCodigo() == null || this.expediente.getExpCodigo() <= 0) {
            ExpedienteDao.getInstance().setExpediente(this.expediente);
            Resultado<BikExpediente> resultado = ExpedienteDao.getInstance().save();
            
            if (resultado.getResultado().equals(TipoResultado.ERROR)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar Expediente", resultado.getMensaje());
                return;
            }
            this.expediente = resultado.get();
            AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Registrar Expediente", resultado.getMensaje());
        }
    }
    
    @FXML
    void limpiar(ActionEvent event) {
        unbindExpediente();
        unbindPadecimiento();
        unbindMedicamento();
        this.listaPadecimientos.clear();
        this.listaMedicamentos.clear();
        nuevoExpediente();
        nuevoPadecimiento();
        nuevoMedicamento();
        bindExpediente();
        bindPadecimiento();
        bindMedicamento();
    }
    
    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }
    
    @FXML
    void cedulaOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            unbindPersonaUsuario();
            unbindPersonaEncargado();
            unbindPadecimiento();
            unbindMedicamento();
            nuevoPadecimiento();
            nuevoMedicamento();
            //busca el expediente por cédula del usuario, si no lo encuetra entonces carga el nombre del usuario con la cédula ingresada.
            BikExpediente buscado = getExpediente(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula());
            if (buscado != null && buscado.getExpCodigo() != null && buscado.getExpCodigo() > 0) {
                this.expediente = buscado;
            } else {
                this.expediente.getExpUsucodigo().setUsuPercodigo(getPersona(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula()).get());
            }
            bindPersonaUsuario();
            bindPersonaEncargado();
            bindPadecimiento();
            bindMedicamento();
        }
    }
    
    @FXML
    void encargadoOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            unbindPersonaUsuario();
            unbindPersonaEncargado();
            unbindPadecimiento();
            unbindMedicamento();
            nuevoPadecimiento();
            nuevoMedicamento();
            this.expediente.setExpCodencargado(getPersona(this.expediente.getExpCodencargado().getPerCedula()).get());
            bindPersonaUsuario();
            bindPersonaEncargado();
            bindPadecimiento();
            bindMedicamento();
        }
    }
    
    private Resultado<BikPersona> getPersona(String cedula) {
        Resultado<BikPersona> resultado = PersonaDao.getInstance().getPersona(cedula);
        if (!resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar persona", resultado.getMensaje());
            return resultado;
        }
        return resultado;
    }
    
    private BikExpediente getExpediente(String cedula) {
        Resultado<BikExpediente> resultado = ExpedienteDao.getInstance().getExpedienteByCedula(cedula);
        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar persona", resultado.getMensaje());
            return resultado.get();
        }
        return resultado.get();
    }

   @Override
   public void initialize() {
      init();
   }
}
