/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

/**
 *
 * @author Luis Diego
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.CentroDao;
import jbiketso.model.dao.FuncionarioDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.PuestoDao;
import jbiketso.model.entities.BikFuncionario;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikPuesto;
import jbiketso.model.entities.BikSede;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class FuncionarioController extends Controller {

    private BikFuncionario funcionario;

    @FXML
    private JFXButton jbtnBuscarPuesto, jbtnBuscarSede, jbtnSalir, jbtnBuscarFuncionario;

    @FXML
    private Button btnLimpiar, btnGuardaFuncionario;

    @FXML
    private JFXTextField jtxfCedulaFuncionario, jtxfCodSede, jtxfSalarioBase, jtxfNombreFuncionario, jtxfDesSede, jtxfDesPuesto, jtxfCodPuesto, jtxfObservacion;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipo, jcmbEstado;

    @FXML
    private JFXDatePicker jdtpFechaIngreso, jdtpFechaSalida;

    @XmlTransient
    private ObservableList<GenValorCombo> tipos = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @Override
    public void initialize() {
        iniciarForma();
    }

    private void nuevoFuncionario() {
        this.funcionario = new BikFuncionario();
        this.funcionario.setFunPercodigo(new BikPersona());
        this.funcionario.setFunPuecodigo(new BikPuesto());
        this.funcionario.setFunSedcodigo(new BikSede());
    }

    private void iniciarForma() {

        this.tipos.clear();
        this.tipos.add(new GenValorCombo("P", "Permanente"));
        this.tipos.add(new GenValorCombo("V", "Voluntario"));
        jcmbTipo.setItems(this.tipos);
        jcmbTipo.getSelectionModel().selectFirst();

        this.estados.clear();
        this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("I", "Inactivo"));
        jcmbEstado.setItems(this.estados);
        jcmbEstado.getSelectionModel().selectFirst();

        if (this.funcionario != null) {
            unbindFuncionario();
        }

        nuevoFuncionario();
        bindFuncionario();

        // Se habilitan las campos.
        this.jtxfNombreFuncionario.setDisable(false);
        this.jtxfDesPuesto.setDisable(false);
        this.jtxfDesSede.setDisable(false);
        this.jdtpFechaIngreso.setDisable(false);

        this.jtxfCedulaFuncionario.requestFocus();
        
        this.jcmbEstado.valueProperty().addListener(new ChangeListener<GenValorCombo>() {
            @Override
            public void changed(ObservableValue<? extends GenValorCombo> observable, GenValorCombo oldValue, GenValorCombo newValue) {
                if (funcionario.getFunCodigo() != null && funcionario.getFunCodigo() > 0 && newValue != null && newValue.getCodigo().equalsIgnoreCase("e")) {
                    jdtpFechaSalida.setValue(LocalDate.now());
                } else {
                    jdtpFechaSalida.setValue(null);
                }
            }
        });

    }

    private void bindFuncionario() {
        // Persona
        jtxfCedulaFuncionario.textProperty().bindBidirectional(this.funcionario.getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().bindBidirectional(this.funcionario.getFunPercodigo().getNombreCompletoProperty());
        // Puesto
        jtxfCodPuesto.textProperty().bindBidirectional(this.funcionario.getFunPuecodigo().getPueCodigoProperty(), new NumberStringConverter());
        jtxfDesPuesto.textProperty().bindBidirectional(this.funcionario.getFunPuecodigo().getPueDescripcionProperty());
        // Sede
        jtxfCodSede.textProperty().bindBidirectional(this.funcionario.getFunSedcodigo().getSedCodigoProperty(), new NumberStringConverter());
        jtxfDesSede.textProperty().bindBidirectional(this.funcionario.getFunSedcodigo().getSedDescripcionProperty());
        // Funcionario
        jtxfSalarioBase.textProperty().bindBidirectional(this.funcionario.getSalarioBaseProperty(), new DecimalFormat("###,###,##0.00"));
        jtxfObservacion.textProperty().bindBidirectional(this.funcionario.getObservacionesProperty());
        jcmbTipo.valueProperty().bindBidirectional(this.funcionario.getTipoProperty());
        jcmbEstado.valueProperty().bindBidirectional(this.funcionario.getEstadoProperty());
        jdtpFechaIngreso.valueProperty().bindBidirectional(this.funcionario.getFechaIngresoProperty());
        jdtpFechaSalida.valueProperty().bindBidirectional(this.funcionario.getFechaSalidaProperty());
    }

    private void unbindFuncionario() {
        // Persona
        jtxfCedulaFuncionario.textProperty().unbindBidirectional(this.funcionario.getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().unbindBidirectional(this.funcionario.getFunPercodigo().getNombreCompletoProperty());
        // Puesto
        jtxfCodPuesto.textProperty().unbindBidirectional(this.funcionario.getFunPuecodigo().getPueCodigoProperty());
        jtxfDesPuesto.textProperty().unbindBidirectional(this.funcionario.getFunPuecodigo().getPueDescripcionProperty());
        // Sede
        jtxfCodSede.textProperty().unbindBidirectional(this.funcionario.getFunSedcodigo().getSedCodigoProperty());
        jtxfDesSede.textProperty().unbindBidirectional(this.funcionario.getFunSedcodigo().getSedDescripcionProperty());
        // Funcionario
        jtxfSalarioBase.textProperty().unbindBidirectional(this.funcionario.getSalarioBaseProperty());
        jtxfObservacion.textProperty().unbindBidirectional(this.funcionario.getObservacionesProperty());
        jcmbTipo.valueProperty().unbindBidirectional(this.funcionario.getTipoProperty());
        jcmbEstado.valueProperty().unbindBidirectional(this.funcionario.getEstadoProperty());
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
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(this.funcionario.getFunPercodigo().getPerCedula());
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            return;
        }
        unbindFuncionario();
        // Se busca el funcionario
        BikFuncionario buscado = getFuncionario(this.funcionario.getFunPercodigo().getPerCedula());
        if (buscado != null && buscado.getFunCodigo() != null && buscado.getFunCodigo() > 0) {
            this.funcionario = buscado;
            // Se deshabilitan las campos.
            this.jtxfNombreFuncionario.setDisable(true);
            this.jtxfDesPuesto.setDisable(true);
            this.jtxfDesSede.setDisable(true);
            this.jdtpFechaIngreso.setDisable(true);
        } else {
            BikPersona personaBuscada = PersonaDao.getInstance().getPersona(this.funcionario.getFunPercodigo().getPerCedula()).get();
            if (personaBuscada != null && personaBuscada.getPerCodigo() != null && personaBuscada.getPerCodigo() > 0) {
                this.funcionario.setFunPercodigo(personaBuscada);
                // Se deshabilita el campo.
                this.jtxfNombreFuncionario.setDisable(true);
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar funcionario", "No se ha registrado la persona con la cédula indicada, ingrese primero a la persona e intente nuevamente.");
            }
        }
        bindFuncionario();
    }

    private void traerPuesto() {

        unbindFuncionario();

        BikPuesto puestoBuscado = PuestoDao.getInstance().getPuestoByCodigo(this.funcionario.getFunPuecodigo().getPueCodigo()).get();
        if (puestoBuscado != null && puestoBuscado.getPueCodigo() != null && puestoBuscado.getPueCodigo() > 0) {
            this.funcionario.setFunPuecodigo(puestoBuscado);
            this.jtxfDesPuesto.setDisable(true);
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar puesto", "No se ha registrado el puesto con el código indicado, ingrese primero el puesto e intente nuevamente.");
        }

        bindFuncionario();

    }

    private void traerSede() {

        unbindFuncionario();

        BikSede sedeBuscada = CentroDao.getInstance().findSedeByCodigo(this.funcionario.getFunSedcodigo().getSedCodigo()).get();
        if (sedeBuscada != null && sedeBuscada.getSedCodigo() != null && sedeBuscada.getSedCodigo() > 0) {
            this.funcionario.setFunSedcodigo(sedeBuscada);
            this.jtxfDesSede.setDisable(true);
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar sede", "No se ha registrado la sede con el código indicado, ingrese primero la sede e intente nuevamente.");
        }

        bindFuncionario();
    }

    @FXML
    void cedulaFuncionarioOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (this.jtxfCedulaFuncionario.getText() != null) {
                traerFuncionario();
            }
        }
    }

    @FXML
    void codigoPuestoOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (this.funcionario.getFunPuecodigo().getPueCodigo() != null && this.funcionario.getFunPuecodigo().getPueCodigo() != 0) {
                traerPuesto();
            }
        }
    }

    @FXML
    void codigoSedeOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (this.funcionario.getFunSedcodigo().getSedCodigo() != null && this.funcionario.getFunSedcodigo().getSedCodigo() != 0) {
                traerSede();
            }
        }
    }

    @FXML
    public void guardarFuncionario() {

        FuncionarioDao.getInstance().setFuncionario(this.funcionario);
        Resultado<BikFuncionario> resultado = FuncionarioDao.getInstance().save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar Funcionario", resultado.getMensaje());
            return;
        } else {
            this.funcionario = resultado.get();
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Registrar Funcionario", resultado.getMensaje());

    }

    @FXML
    void limpiar() {
        unbindFuncionario();
        nuevoFuncionario();
        bindFuncionario();
        // Se habilitan las campos.
        this.jtxfNombreFuncionario.setDisable(false);
        this.jtxfDesPuesto.setDisable(false);
        this.jtxfDesSede.setDisable(false);
        this.jdtpFechaIngreso.setDisable(false);
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

}
