package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ExpedienteDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.UsuarioDao;
import jbiketso.model.entities.BikExpediente;
import jbiketso.model.entities.BikMedicamento;
import jbiketso.model.entities.BikPadecimiento;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Formater;
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
        this.jtxfCedulaEncargado.textProperty().bindBidirectional(this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().bindBidirectional(this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getNombreCompletoProperty());
    }

    private void unbindPersonaEncargado() {
        //encargado
        this.jtxfCedulaEncargado.textProperty().unbindBidirectional(this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedulaProperty());
        this.jtxfNombreEncargado.textProperty().unbindBidirectional(this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getNombreCompletoProperty());
    }

    private void bindExpediente() {
        bindPersonaUsuario();
        bindPersonaEncargado();
        //expediente
        this.jtxfExpediente.textProperty().bindBidirectional(this.expediente.getCodigoProperty(), new NumberStringConverter());
        this.jdtpFechaIngreso.valueProperty().bindBidirectional(this.expediente.getExpFechaIngresoProperty());
        this.jdtpFechaSalida.valueProperty().bindBidirectional(this.expediente.getExpFechaSalidaProperty());
        this.jcmbEstado.valueProperty().bindBidirectional(this.expediente.getEstadoProperty());
        this.jcmbTipoAtencion.valueProperty().bindBidirectional(this.expediente.getTipoAtencionProperty());
        this.jtxfCantidadPersonas.textProperty().bindBidirectional(this.expediente.getPersonasHogarProperty(), new NumberStringConverter());
        this.jtxfCantidadDependientes.textProperty().bindBidirectional(this.expediente.getPersonasDependientesProperty(), new NumberStringConverter());
        this.jtxfIngresoPromedio.textProperty().bindBidirectional(this.expediente.getIngresoPromedioProperty(), new DecimalFormat("###,###,##0.00"));
        this.jtxfEstSocioEco.textProperty().bindBidirectional(this.expediente.getEstudioSocioEconomicoProperty(), new NumberStringConverter());

        this.jtxfCantidadPersonas.setTextFormatter(Formater.getInstance().integerFormat());
        this.jtxfCantidadDependientes.setTextFormatter(Formater.getInstance().integerFormat());
        this.jtxfEstSocioEco.setTextFormatter(Formater.getInstance().integerFormat());
        
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
        this.jtxaObservacionesMedicamento.textProperty().bindBidirectional(this.medicamento.getObservacionesProperty());
    }

    private void unbindMedicamento() {
        this.jtxfMedicamento.textProperty().unbindBidirectional(this.medicamento.getMedicamentoProperty());
        this.jtxaObservacionesMedicamento.textProperty().unbindBidirectional(this.medicamento.getObservacionesProperty());
    }

    private void unbindExpediente() {
        unbindPersonaUsuario();
        unbindPersonaEncargado();

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
        tbcObservacionesMedicamento.setCellValueFactory(new PropertyValueFactory<>("medObservaciones"));
    }

    private void nuevoExpediente() {
        this.expediente = new BikExpediente(true);
        this.expediente.setExpSedcodigo(Aplicacion.getInstance().getDefaultSede());
        this.expediente.setExpUsucodigo(new BikUsuario());
        this.expediente.getExpUsucodigo().setUsuPercodigo(new BikPersona());
        this.expediente.getExpUsucodigo().setUsuCodencargadolegal(new BikPersona());
    }

    private void nuevoPadecimiento() {
        this.padecimiento = new BikPadecimiento();
    }

    private void nuevoMedicamento() {
        this.medicamento = new BikMedicamento();
    }

    private void init() {
        this.estados.clear();
        this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("I", "Inactivo"));
        this.jcmbEstado.setItems(this.estados);

        this.tiposAtencion.clear();
        this.tiposAtencion.add(new GenValorCombo("D", "Por día"));
        this.tiposAtencion.add(new GenValorCombo("P", "Permanente 24 horas"));
        this.jcmbTipoAtencion.setItems(this.tiposAtencion);

        if (this.expediente != null) {
            unbindExpediente();
        }
        if (this.medicamento != null) {
            unbindMedicamento();
        }

        if (this.padecimiento != null) {
            unbindPadecimiento();
        }
        nuevoExpediente();
        nuevoPadecimiento();
        nuevoMedicamento();
        bindExpediente();
        bindListaPadecimientos();
        bindListaMedicamentos();
        this.jcmbEstado.getSelectionModel().selectFirst();
        this.jcmbTipoAtencion.getSelectionModel().selectFirst();

        addListenerTablePadecimientos(tbvPadecimiento);
        addListenerTableMedicamentos(tbvMedicamentos);

        this.jtxfCedula.requestFocus();
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
        BikMedicamento nuevo = new BikMedicamento();
        nuevo.setMedMedicamento(medicamento.getMedMedicamento());
        nuevo.setMedObservaciones(medicamento.getMedObservaciones());
        nuevo.setCodigoExpediente(this.expediente);

        if (!this.expediente.getBikMedicamentoList().contains(nuevo)) {
            this.expediente.getBikMedicamentoList().add(nuevo);
            this.listaMedicamentos.add(nuevo);
        } else {
            this.expediente.getBikMedicamentoList().set(this.expediente.getBikMedicamentoList().indexOf(nuevo), nuevo);
            this.listaMedicamentos.set(this.listaMedicamentos.indexOf(nuevo), nuevo);
        }
        tbvMedicamentos.refresh();
    }

    @FXML
    void agregarPadecimiento(ActionEvent event) {
        BikPadecimiento nuevo = new BikPadecimiento();
        nuevo.setPadPadecimiento(padecimiento.getPadPadecimiento());
        nuevo.setPadObservaciones(padecimiento.getPadObservaciones());
        nuevo.setCodigoExpediente(this.expediente);

        if (!this.expediente.getBikPadecimientoList().contains(nuevo)) {
            this.expediente.getBikPadecimientoList().add(nuevo);
            this.listaPadecimientos.add(nuevo);
        } else {
            this.expediente.getBikPadecimientoList().set(this.expediente.getBikPadecimientoList().indexOf(nuevo), nuevo);
            this.listaPadecimientos.set(this.listaPadecimientos.indexOf(nuevo), nuevo);
        }
        tbvPadecimiento.refresh();
    }

    @FXML
    void eliminarMedicamento(ActionEvent event) {
        Resultado<String> resultado = ExpedienteDao.getInstance().deleteMedicamento(medicamento);
        if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            this.expediente.getBikMedicamentoList().remove(medicamento);
            this.listaMedicamentos.remove(medicamento);
            nuevoMedicamento();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar medicamento", resultado.getMensaje());
        }
    }

    @FXML
    void eliminarPadecimiento(ActionEvent event) {
        Resultado<String> resultado = ExpedienteDao.getInstance().deletePadecimiento(padecimiento);
        if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            this.expediente.getBikPadecimientoList().remove(padecimiento);
            this.listaPadecimientos.remove(padecimiento);
            nuevoMedicamento();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar padecimiento", resultado.getMensaje());
        }
    }

    @FXML
    void guardarUsuario(ActionEvent event) {
        //guarda el expediente
        this.expediente.setExpSedcodigo(Aplicacion.getInstance().getDefaultSede());
        traerUsuario();
        traerEncargado();
        this.expediente.getExpUsucodigo().setUsuEstado(this.expediente.getExpEstado());
        this.expediente.getExpUsucodigo().setUsuSedcodigo(this.expediente.getExpSedcodigo());
        ExpedienteDao.getInstance().setExpediente(this.expediente);
        Resultado<BikExpediente> resultado = ExpedienteDao.getInstance().save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar Expediente", resultado.getMensaje());
            return;
        } else {
            this.expediente = resultado.get();
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Registrar Expediente", resultado.getMensaje());

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
            if (this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula() != null && !this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula().isEmpty()) {
                if (this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula() != null && !this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula().isEmpty()) {
                    if (this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula().equalsIgnoreCase(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula())) {
                        AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula usuario", "El usuario no puede ser el mismo encargado.");
                        this.jtxfCedula.requestFocus();
                        return;
                    }
                }
                traerUsuario();
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula usuario", "Debe indicar la cédula del usuario.");
                this.jtxfCedula.requestFocus();
            }
        }
    }

    private void traerUsuario() {
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula());
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            return;
        }
        unbindExpediente();
        unbindPadecimiento();
        unbindMedicamento();
        nuevoPadecimiento();
        nuevoMedicamento();
        //busca el expediente por cédula del usuario, si no lo encuetra entonces carga el nombre del usuario con la cédula ingresada.
        BikExpediente buscado = getExpediente(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula());
        if (buscado != null && buscado.getExpCodigo() != null && buscado.getExpCodigo() > 0) {
            this.expediente = buscado;
            //carga los padecimientos
            Resultado<ArrayList<BikPadecimiento>> padecimientosResult = ExpedienteDao.getInstance().getPadecimientosActivos(this.expediente);
            listaPadecimientos.clear();
            padecimientosResult.get().stream().forEach(listaPadecimientos::add);

            //carga los medicamentos
            Resultado<ArrayList<BikMedicamento>> contactosResult = ExpedienteDao.getInstance().getMedicamentosActivos(this.expediente);
            listaMedicamentos.clear();
            contactosResult.get().stream().forEach(listaMedicamentos::add);
        } else {
            BikUsuario usuBuscado = UsuarioDao.getInstance().getUsuarioByCedula(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula()).get();
            if (usuBuscado != null && usuBuscado.getUsuCodigo() != null && usuBuscado.getUsuCodigo() > 0) {
                this.expediente.setExpUsucodigo(usuBuscado);
            } else {
                BikPersona perBuscada = getPersona(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula()).get();
                if (perBuscada != null && perBuscada.getPerCodigo() != null && perBuscada.getPerCodigo() > 0) {
                    this.expediente.getExpUsucodigo().setUsuPercodigo(perBuscada);
                } else {
                    AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar usuario", "No se ha registrado la persona con la cédula indicada, ingrese primero a la persona e intente nuevamente.");
                }
            }
        }
        bindExpediente();
        bindPadecimiento();
        bindMedicamento();
    }

    @FXML
    void encargadoOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
            if (this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula() != null && !this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula().isEmpty()) {
                if (this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula() != null && !this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula().isEmpty()) {
                    if (this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula().equalsIgnoreCase(this.expediente.getExpUsucodigo().getUsuPercodigo().getPerCedula())) {
                        AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula encargado", "El encargado no puede ser el mismo usuario.");
                        this.jtxfCedulaEncargado.requestFocus();
                        return;
                    }
                }
                traerEncargado();
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula encargado", "Debe indicar la cédula del encargado.");
                this.jtxfCedulaEncargado.requestFocus();
            }
        }
    }

    private void traerEncargado() {
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula());
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            return;
        }
        unbindPersonaUsuario();
        unbindPersonaEncargado();
        unbindPadecimiento();
        unbindMedicamento();
        nuevoPadecimiento();
        nuevoMedicamento();
        if (this.expediente.getExpUsucodigo().getUsuCodencargadolegal() == null || this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCodigo() == null || this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCodigo() <= 0) {
            BikPersona encargado = getPersona(this.expediente.getExpUsucodigo().getUsuCodencargadolegal().getPerCedula()).get();
            if (encargado != null && encargado.getPerCodigo() != null && encargado.getPerCodigo() > 0) {
                this.expediente.getExpUsucodigo().setUsuCodencargadolegal(encargado);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar encargado", "No se ha registrado una persona con la cédula indicada, ingrese a la persona e intente nuevamente.");
            }
        }

        bindPersonaUsuario();
        bindPersonaEncargado();
        bindPadecimiento();
        bindMedicamento();
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
