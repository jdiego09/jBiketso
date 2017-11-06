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
import java.net.URL;
import java.text.Format;
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
import jbiketso.model.dao.CentroDao;
import jbiketso.model.entities.BikCentro;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class CentroController implements Initializable {

    @FXML
    private AnchorPane acpRoot;
    
    @FXML
    private JFXTextField jtxfRepreLegal, jtxfCedJuridica, jtxfLogo, jtxfNombre;

    @FXML
    private JFXButton jbtnSalir, jbtnBuscarRepre;

    @FXML
    private Button btnGuardaCentro, btnLimpiar;

    @FXML
    private TableView<BikCentro> tbvCentros;

    @FXML
    private TableColumn<BikCentro, String> tbcRepreLegal, tbcCedJuridica, tbcNombre, tbcEstado;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstado;

    @XmlTransient
    private ObservableList<BikCentro> centros = FXCollections
            .observableArrayList();

    BikCentro centro;

    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        estados.add(new GenValorCombo("A", "Activo"));
        estados.add(new GenValorCombo("I", "Inactivo"));
        jcmbEstado.setItems(estados);

        nuevoCentro();
        bindCentro();
        cargarCentros();
        bindListaModulos();
        addListenerTable(tbvCentros);

    }

    private void nuevoCentro() {
        this.centro = new BikCentro();
    }

    private void bindCentro() {
        //jtxfRepreLegal.textProperty().bindBidirectional(centro.getCenCodrepresentantelegalProperty(), Format());
        jtxfCedJuridica.textProperty().bindBidirectional(centro.getCenCedulajuridicaProperty());
        jtxfLogo.textProperty().bindBidirectional(centro.getCenLogoProperty());
        jtxfNombre.textProperty().bindBidirectional(centro.getCenNombreProperty());
        jcmbEstado.valueProperty().bindBidirectional(centro.getCenEstadoProperty());
    }
    
    private void unbindCentro() {
        jtxfRepreLegal.textProperty().unbindBidirectional(centro.getCenCodrepresentantelegalProperty());
        //jtxfCedJuridica.textProperty().unbindBidirectional(centro.getCenCedulajuridicaProperty());
        jtxfLogo.textProperty().unbindBidirectional(centro.getCenLogoProperty());
        jtxfNombre.textProperty().unbindBidirectional(centro.getCenNombreProperty());
        jcmbEstado.valueProperty().unbindBidirectional(centro.getCenEstadoProperty());
    }
    
    private void cargarCentros() {
        Resultado<ArrayList<BikCentro>> resultado = CentroDao.getInstance().findAll();
        if (!resultado.getResultado().equals(TipoResultado.ERROR)) {
            centros = FXCollections.observableArrayList(resultado.get());
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar centros", resultado.getMensaje());
        }
    }
    
    private void bindListaModulos() {
        if (centros != null) {
            tbvCentros.setItems(centros);
            tbvCentros.refresh();
        }
        tbcRepreLegal.setCellValueFactory(new PropertyValueFactory<>("cenCodrepresentantelegal"));
        tbcCedJuridica.setCellValueFactory(new PropertyValueFactory<>("cenCedulajuridica"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("cenNombre"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("cenEstado"));
    }

    private void addListenerTable(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindCentro();
                this.centro = (BikCentro) newSelection;
                bindCentro();
            }
        });
    }
    
    private void agregarCentroALista(BikCentro centro) {
        if (!this.centros.contains(centro)) {
            this.centros.add(centro);
        } else {
            this.centros.set(this.centros.indexOf(centro), centro);
        }
        tbvCentros.refresh();
    }
    
    @FXML
    void limpiarCentro(ActionEvent event) {
        unbindCentro();
        nuevoCentro();
        bindCentro();
    }
    
    @FXML
    void guardarCentro(ActionEvent event) {
        CentroDao.getInstance().setCentro(this.centro);
        Resultado<BikCentro> nuevo = CentroDao.getInstance().save();
        if (nuevo.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar centro", nuevo.getMensaje());
            return;
        }
        agregarCentroALista(nuevo.get());
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar centro", nuevo.getMensaje());
    }
    
    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

}
