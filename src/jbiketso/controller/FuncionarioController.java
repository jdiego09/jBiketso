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
import com.jfoenix.controls.JFXTextField;
import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import jbiketso.model.dao.FuncionarioDao;
import jbiketso.model.dao.PersonaDao;
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
    private BikPersona personaFuncionario;
    private BikPuesto puesto;
    private BikSede sede;

    @FXML
    private JFXButton jbtnBuscarPuesto, jbtnBuscarSede, jbtnSalir, jbtnBuscarFuncionario;

    @FXML
    private TableColumn<BikFuncionario, String> tbcPuesto, tbcNombreFuncionario;

    @FXML
    private Button btnLimpiar, btnGuardaFuncionario;

    @FXML
    private JFXTextField jtxfCedulaFuncionario, jtxfCodSede, jtxfSalarioBase, jtxfNombreFuncionario, jtxfDesSede, jtxjDesPuesto, jtxfCodPuesto, jtxfObservacion;

    @FXML
    private TableView<BikFuncionario> tbvFuncionarios;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipo, jcmbEstado;

    @XmlTransient
    private ObservableList<BikFuncionario> funcionarios = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<GenValorCombo> tipos = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @FXML
    void regresar(ActionEvent event) {

    }

    @Override
    public void initialize() {

    }

    private void nuevoFuncionario() {
        this.funcionario = new BikFuncionario();
    }

    private void nuevaPersonaFuncionario() {
        this.personaFuncionario = new BikPersona();
    }

    private void nuevoPuesto() {
        this.puesto = new BikPuesto();
    }

    private void nuevaSede() {
        this.sede = new BikSede();
    }

    private void iniciarForma() {

        this.tipos.clear();
        this.tipos.add(new GenValorCombo("P", "Permanente"));
        this.tipos.add(new GenValorCombo("V", "Voluntario"));
        jcmbTipo.getItems().clear();
        jcmbTipo.setItems(this.tipos);
        jcmbTipo.getSelectionModel().selectFirst();

        this.estados.clear();
        this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("V", "Inactivo"));
        jcmbEstado.getItems().clear();
        jcmbEstado.setItems(this.estados);
        jcmbEstado.getSelectionModel().selectFirst();

        if (this.funcionario != null) {
            unbindFuncionario();
        }

        this.funcionarios.clear();

        nuevoFuncionario();
        nuevaPersonaFuncionario();
        nuevoPuesto();
        nuevaSede();
        bindFuncionario();
        bindListaFuncionarios();

        addListenerTable(tbvFuncionarios);

    }

    private void bindFuncionario() {
        // Persona
        jtxfCedulaFuncionario.textProperty().bindBidirectional(this.funcionario.getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().bindBidirectional(this.funcionario.getFunPercodigo().getNombreCompletoProperty());
        // Puesto
        //jtxfCodPuesto.textProperty().bindBidirectional(this.funcionario.getFunPuecodigo().getPueCodigoProperty());
        jtxjDesPuesto.textProperty().bindBidirectional(this.funcionario.getFunPuecodigo().getPueDescripcionProperty());
        // Sede
        jtxfCodSede.textProperty().bindBidirectional(this.funcionario.getFunSedcodigo().getSedCodigoProperty(), new NumberStringConverter());
        jtxfDesSede.textProperty().bindBidirectional(this.funcionario.getFunSedcodigo().getSedDescripcionProperty());
        // Funcionario
        jtxfSalarioBase.textProperty().bindBidirectional(this.funcionario.getSalarioBaseProperty(), new DecimalFormat("###,###,##0.00"));
        jtxfObservacion.textProperty().bindBidirectional(this.funcionario.getObservacionesProperty());
        jcmbTipo.valueProperty().bindBidirectional(this.funcionario.getTipoProperty());
        jcmbEstado.valueProperty().bindBidirectional(this.funcionario.getEstadoProperty());
    }

    private void unbindFuncionario() {
        // Persona
        jtxfCedulaFuncionario.textProperty().unbindBidirectional(this.funcionario.getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().unbindBidirectional(this.funcionario.getFunPercodigo().getNombreCompletoProperty());
        // Puesto
        jtxfCodPuesto.textProperty().unbindBidirectional(this.funcionario.getFunPuecodigo().getPueCodigoProperty());
        jtxjDesPuesto.textProperty().unbindBidirectional(this.funcionario.getFunPuecodigo().getPueDescripcionProperty());
        // Sede
        jtxfCodSede.textProperty().unbindBidirectional(this.funcionario.getFunSedcodigo().getSedCodigoProperty());
        jtxfDesSede.textProperty().unbindBidirectional(this.funcionario.getFunSedcodigo().getSedDescripcionProperty());
        // Funcionario
        jtxfSalarioBase.textProperty().unbindBidirectional(this.funcionario.getSalarioBaseProperty());
        jtxfObservacion.textProperty().unbindBidirectional(this.funcionario.getObservacionesProperty());
        jcmbTipo.valueProperty().unbindBidirectional(this.funcionario.getTipoProperty());
        jcmbEstado.valueProperty().unbindBidirectional(this.funcionario.getEstadoProperty());
    }

    private void bindListaFuncionarios() {
        if (this.funcionarios != null) {
            tbvFuncionarios.setItems(this.funcionarios);
            tbvFuncionarios.refresh();
        }
        tbcNombreFuncionario.setCellValueFactory(new PropertyValueFactory<>(""));
        tbcPuesto.setCellValueFactory(new PropertyValueFactory<>(""));
    }

    private void addListenerTable(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindFuncionario();
                this.sede = (BikSede) newSelection;
                //obtenerRepreLegal(this.sede.getSedCodencargado().getPerCedula(), "S");
                bindFuncionario();
            }
        });
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
        if (buscado != null && buscado.getFunCodigo() != null) {
            this.funcionario = buscado;
        }
    }

    @FXML
    void cedulaFuncionarioOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (this.funcionario.getFunPercodigo().getPerCedula() != null && !this.funcionario.getFunPercodigo().getPerCedula().isEmpty()) {
                traerFuncionario();
            }
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula usuario", "Debe indicar la cédula del usuario.");
            this.jtxfCedulaFuncionario.requestFocus();
        }
    }

}
