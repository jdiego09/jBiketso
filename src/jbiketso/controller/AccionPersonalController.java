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
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.AccionPersonalDao;
import jbiketso.model.dao.FuncionarioDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.entities.BikAccionesPersonal;
import jbiketso.model.entities.BikEvaluacion;
import jbiketso.model.entities.BikFuncionario;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Formater;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class AccionPersonalController extends Controller {

    private BikAccionesPersonal accionPersonal;
    private BikEvaluacion evaluacion;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXButton jbtnSalir, jbtnBuscarFuncionario;

    @FXML
    private Button btnLimpiar, btnGuardaAccion;

    @FXML
    private JFXTextField jtxfCedulaFuncionario, jtxfNombreFuncionario, jtxfObservacionesAccion, jtxfCalificacion, jtxfObservacionesEvaluacion;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstado, jcmbTipoAccion, jcmbTipoEvaluacion;

    @FXML
    private JFXDatePicker jdtpFechaFinal, jdtpFechaInicial;

    @FXML
    private TableView<BikAccionesPersonal> tbvAccionesPersonal;

    @FXML
    private TableColumn<BikAccionesPersonal, String> tbcTipo, tbcEstado;

    @FXML
    private TableColumn<BikAccionesPersonal, String> tbcFechaInicial, tbcFechaFinal;

    @FXML
    private TableView<BikEvaluacion> tbvEvaluaciones;

    @FXML
    private TableColumn<BikEvaluacion, String> tbcTipoEvaluacion;

    @FXML
    private TableColumn<BikEvaluacion, Integer> tbcCalificacion;

    @FXML
    private TableColumn<BikEvaluacion, String> tbcObservacionEvaluacion;

    @FXML
    private Tab tabEvaluaciones;

    @XmlTransient
    private ObservableList<BikAccionesPersonal> accionesPersonal = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikEvaluacion> evaluaciones = FXCollections
            .observableArrayList();

    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @XmlTransient
    ObservableList<GenValorCombo> tiposAccion = FXCollections
            .observableArrayList();

    @XmlTransient
    ObservableList<GenValorCombo> tiposEvaluacion = FXCollections
            .observableArrayList();

    @Override
    public void initialize() {
        iniciarPantalla();
    }

    private void nuevaAccion() {
        this.accionPersonal = new BikAccionesPersonal();
        this.accionPersonal.setAccFuncodigo(new BikFuncionario());
        this.accionPersonal.getAccFuncodigo().setFunPercodigo(new BikPersona());
        this.accionPersonal.setBikEvaluacionList(new ArrayList<>());
    }

    private void nuevaEvaluacion() {
        this.evaluacion = new BikEvaluacion();
    }

    private void iniciarPantalla() {

        nuevaAccion();
        nuevaEvaluacion();

        this.estados.clear();
        this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("I", "Inactivo"));
        jcmbEstado.setItems(this.estados);
        jcmbEstado.getSelectionModel().selectFirst();

        this.tiposAccion.clear();
        this.tiposAccion.add(new GenValorCombo("ING", "Ingreso"));
        this.tiposAccion.add(new GenValorCombo("VAC", "Vacaciones"));
        this.tiposAccion.add(new GenValorCombo("REN", "Renuncia"));
        this.tiposAccion.add(new GenValorCombo("DES", "Despido"));
        this.tiposAccion.add(new GenValorCombo("CAL", "Calificación funcionario"));
        this.tiposAccion.add(new GenValorCombo("INC", "Incapacidad"));
        this.tiposAccion.add(new GenValorCombo("PER", "Permiso"));
        jcmbTipoAccion.setItems(this.tiposAccion);
        jcmbTipoAccion.getSelectionModel().selectFirst();

        this.tiposEvaluacion.clear();
        this.tiposEvaluacion.add(new GenValorCombo("Q", "Quincenal"));
        this.tiposEvaluacion.add(new GenValorCombo("M", "Mensual"));
        this.tiposEvaluacion.add(new GenValorCombo("T", "Trimestral"));
        this.tiposEvaluacion.add(new GenValorCombo("S", "Semestral"));
        this.tiposEvaluacion.add(new GenValorCombo("A", "Anual funcionario"));
        jcmbTipoEvaluacion.setItems(this.tiposEvaluacion);
        jcmbTipoEvaluacion.getSelectionModel().selectFirst();

        if (this.accionPersonal != null) {
            unbindAccionPersonal();
        }

        if (this.evaluacion != null) {
            unbindEvaluacion();
        }

        bindAccionPersonal();
        bindEvaluacion();

        bindListaAccionesPersonal();
        bindListaEvaluaciones();

        addListenerTableAccionesPersonal(tbvAccionesPersonal);
        addListenerTableEvaluaciones(tbvEvaluaciones);

        this.jtxfNombreFuncionario.setDisable(false);
        this.jtxfCedulaFuncionario.setDisable(false);

        this.jtxfCedulaFuncionario.requestFocus();

    }

    private void bindAccionPersonal() {
        // Acción de personal
        jtxfCedulaFuncionario.textProperty().bindBidirectional(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().bindBidirectional(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getNombreCompletoProperty());
        jcmbTipoAccion.valueProperty().bindBidirectional(this.accionPersonal.getAccTipoProperty());
        jdtpFechaInicial.valueProperty().bindBidirectional(this.accionPersonal.getAccFechainicioProperty());
        jdtpFechaFinal.valueProperty().bindBidirectional(this.accionPersonal.getAccFechafinalProperty());
        jcmbEstado.valueProperty().bindBidirectional(this.accionPersonal.getAccEstadoProperty());
        jtxfObservacionesAccion.textProperty().bindBidirectional(this.accionPersonal.getAccObservacionesProperty());
    }

    private void unbindAccionPersonal() {
        // Acción de personal
        jtxfCedulaFuncionario.textProperty().unbindBidirectional(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().unbindBidirectional(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getNombreCompletoProperty());
        jcmbTipoAccion.valueProperty().unbindBidirectional(this.accionPersonal.getAccTipoProperty());
        jdtpFechaInicial.valueProperty().unbindBidirectional(this.accionPersonal.getAccFechainicioProperty());
        jdtpFechaFinal.valueProperty().unbindBidirectional(this.accionPersonal.getAccFechafinalProperty());
        jcmbEstado.valueProperty().unbindBidirectional(this.accionPersonal.getAccEstadoProperty());
        jtxfObservacionesAccion.textProperty().unbindBidirectional(this.accionPersonal.getAccObservacionesProperty());
    }

    private void bindEvaluacion() {
        // Evaluación
        jcmbTipoEvaluacion.valueProperty().bindBidirectional(this.evaluacion.getEvaTipoProperty());
        jtxfCalificacion.textProperty().bindBidirectional(this.evaluacion.getEvaCalificacionProperty(), new NumberStringConverter());
        jtxfObservacionesEvaluacion.textProperty().bindBidirectional(this.evaluacion.getEvaObservacionesProperty());
    }

    private void unbindEvaluacion() {
        // Evaluación
        jcmbTipoEvaluacion.valueProperty().unbindBidirectional(this.evaluacion.getEvaTipoProperty());
        jtxfCalificacion.textProperty().unbindBidirectional(this.evaluacion.getEvaCalificacionProperty());
        jtxfObservacionesEvaluacion.textProperty().unbindBidirectional(this.evaluacion.getEvaObservacionesProperty());
    }

    private void addListenerTableAccionesPersonal(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindAccionPersonal();
                this.accionPersonal = (BikAccionesPersonal) newSelection;
                bindAccionPersonal();
            }
        });
    }

    private void addListenerTableEvaluaciones(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindEvaluacion();
                this.evaluacion = (BikEvaluacion) newSelection;
                bindEvaluacion();
            }
        });
    }

    private void bindListaAccionesPersonal() {
        if (this.accionesPersonal != null) {
            tbvAccionesPersonal.setItems(this.accionesPersonal);
            tbvAccionesPersonal.refresh();
        }
        tbcTipo.setCellValueFactory(new PropertyValueFactory<>("descripcionTipoAccion"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
        tbcFechaInicial.setCellValueFactory(new PropertyValueFactory<>("accFechainicio"));
        tbcFechaInicial.setCellValueFactory(b -> new SimpleStringProperty(Formater.getInstance().formatFecha.format(b.getValue().getAccFechainicio())));
        tbcFechaFinal.setCellValueFactory(new PropertyValueFactory<>("accFechafinal"));
        tbcFechaFinal.setCellValueFactory(b -> new SimpleStringProperty(Formater.getInstance().formatFecha.format(b.getValue().getAccFechafinal())));
    }

    private void bindListaEvaluaciones() {
        if (this.evaluaciones != null) {
            tbvEvaluaciones.setItems(this.evaluaciones);
            tbvEvaluaciones.refresh();
        }
        tbcTipoEvaluacion.setCellValueFactory(new PropertyValueFactory<>("descripcionTipoEvaluacion"));
        tbcCalificacion.setCellValueFactory(new PropertyValueFactory<>("evaCalificacion"));
        tbcObservacionEvaluacion.setCellValueFactory(new PropertyValueFactory<>("evaObservaciones"));
    }

    private void agregarAccionPersonalALista(BikAccionesPersonal accionPersonal) {
        if (this.accionPersonal != null && (this.accionPersonal.getAccCodigo() == null || this.accionPersonal.getAccCodigo() <= 0)) {
            BikAccionesPersonal nueva = new BikAccionesPersonal();
            nueva.setAccTipo(this.accionPersonal.getAccTipo());
            nueva.setAccFechainicio(this.accionPersonal.getAccFechainicio());
            nueva.setAccFechafinal(this.accionPersonal.getAccFechafinal());
            nueva.setAccEstado(this.accionPersonal.getAccTipo());
            nueva.setAccUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
            nueva.setAccFechaingresa(new Date());
            nueva.setAccFuncodigo(this.accionPersonal.getAccFuncodigo());
            if (!this.accionesPersonal.contains(nueva)) {
                this.accionesPersonal.add(nueva);
            }
        } else {
            this.accionPersonal.setAccUsuariomodifica(Aplicacion.getInstance().getUsuario().getUssCodigo());
            this.accionPersonal.setAccFechamodifica(new Date());
            this.accionesPersonal.set(this.accionesPersonal.indexOf(accionPersonal), accionPersonal);
        }
        tbvAccionesPersonal.refresh();
    }

    private void agregarEvaluacionALista(BikEvaluacion evaluacion) {
        if (this.evaluacion != null && (this.evaluacion.getEvaCodigo() == null || this.evaluacion.getEvaCodigo() <= 0)) {
            BikEvaluacion nueva = new BikEvaluacion();
            nueva.setEvaTipo(this.evaluacion.getEvaTipo());
            nueva.setEvaCalificacion(this.evaluacion.getEvaCalificacion());
            nueva.setEvaObservaciones(this.evaluacion.getEvaObservaciones());
            nueva.setEvaUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
            nueva.setEvaFechaingresa(new Date());
            nueva.setEvaAcccodigo(this.accionPersonal);
            if (!this.accionPersonal.getBikEvaluacionList().contains(nueva)) {
                this.accionPersonal.getBikEvaluacionList().add(nueva);
                this.evaluaciones.add(nueva);
            }
        } else {
            this.evaluacion.setEvaUsuariomodifica(Aplicacion.getInstance().getUsuario().getUssCodigo());
            this.evaluacion.setEvaFechamodifica(new Date());
            this.evaluaciones.set(this.evaluaciones.indexOf(evaluacion), evaluacion);
        }
        tbvEvaluaciones.refresh();
    }

    private BikFuncionario getFuncionario(String cedula) {
        Resultado<BikFuncionario> resultado = FuncionarioDao.getInstance().getFuncionarioByCedula(cedula);
        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar funcionario", resultado.getMensaje());
            return resultado.get();
        }
        return resultado.get();
    }

    private void traerFuncionario() {
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedula());
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            return;
        }
        unbindAccionPersonal();
        // Se busca el funcionario
        BikFuncionario buscado = getFuncionario(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedula());
        if (buscado != null && buscado.getFunCodigo() != null && buscado.getFunCodigo() > 0) {
            this.accionPersonal.setAccFuncodigo(buscado);
            // Se deshabilitan las campos.
            this.jtxfCedulaFuncionario.setDisable(true);
            this.jtxfNombreFuncionario.setDisable(true);
            // Se cargan las acciones de personal
            Resultado<ArrayList<BikAccionesPersonal>> acciones = AccionPersonalDao.getInstance().findAccionesPersonal(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedula());
            this.accionesPersonal.clear();
            acciones.get().stream().forEach(this.accionesPersonal::add);
        } else {
            BikPersona personaBuscada = PersonaDao.getInstance().getPersona(this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedula()).get();
            if (personaBuscada != null && personaBuscada.getPerCodigo() != null && personaBuscada.getPerCodigo() > 0) {
                this.accionPersonal.getAccFuncodigo().setFunPercodigo(personaBuscada);
                // Se deshabilita el campo.
                this.jtxfCedulaFuncionario.setDisable(true);
                this.jtxfNombreFuncionario.setDisable(true);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar funcionario", "No se ha registrado la persona con la cédula indicada, ingrese primero a la persona e intente nuevamente.");
            }
        }
        bindAccionPersonal();
    }

    @FXML
    private void guardarAccionPersonal() {
        AccionPersonalDao.getInstance().setAccionPersonal(this.accionPersonal);
        Resultado<BikAccionesPersonal> resultado = AccionPersonalDao.getInstance().save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar persona", resultado.getMensaje());
            return;
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar persona", resultado.getMensaje());
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @FXML
    void cedulaFuncionarioOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (this.accionPersonal.getAccFuncodigo().getFunPercodigo().getPerCedula() != null) {
                traerFuncionario();
            }
        }
    }

    @FXML
    void agregarEvaluacionOnKeyPress(ActionEvent event) {
        agregarEvaluacionALista(this.evaluacion);
        unbindEvaluacion();
        nuevaEvaluacion();
        bindEvaluacion();
        this.jcmbTipoEvaluacion.requestFocus();
    }

    @FXML
    private void limpiarAccionPersonal() {
        unbindAccionPersonal();
        unbindEvaluacion();
        this.accionesPersonal.clear();
        nuevaAccion();
        nuevaEvaluacion();
        bindAccionPersonal();
        bindEvaluacion();
        this.jtxfCedulaFuncionario.setDisable(false);
        this.jtxfNombreFuncionario.setDisable(false);
        tbvAccionesPersonal.getItems().clear();
    }

}
