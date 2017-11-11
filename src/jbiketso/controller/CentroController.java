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
import jbiketso.model.entities.BikSede;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class CentroController implements Initializable {

    private BikCentro centro;
    private BikSede   sede;

    @FXML
    private TableView<BikSede> tbvSedes;

    @FXML
    private TableColumn<BikSede, String> tbcDescripcionSede, tbcTelefonos, tbcFax, tbcEmail;

    @FXML
    private JFXTextField jtxfCedJuridica, jtxjDescripcionSede, jtxfCedEncargadoSede, jtxfEmail, jtxfLogo, jtxfNombreRepreLegal, jtxfNomEncargadoSede;

    @FXML
    private JFXTextField jtxfTelefonos, jtxfNombreSede, jtxfCedRepre, jtxfNombreCentro;

    @FXML
    private JFXButton jbtnBuscarRepre, jbtnBuscarRepreSede, jbtnSalir, jbtnBuscarLogo, jbtnEliminarSede, jbtnAgregarSede;

    @FXML
    private Button btnLimpiar, btnGuardaCentro;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstado;

    @XmlTransient
    private ObservableList<BikSede> sedes = FXCollections
            .observableArrayList();

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
        //bindSede();
        //cargarCentros();
        //bindListaSedes();
        //addListenerTable(tbvSedes);

    }

    private void nuevoCentro() {
        this.centro = new BikCentro();
    }
    
    private void nuevaSede() {
        this.sede = new BikSede();
    }

    private void bindCentro() {
        jtxfCedJuridica.textProperty().bindBidirectional(centro.getCenCedulajuridicaProperty());
        jtxfNombreCentro.textProperty().bindBidirectional(centro.getCenNombreProperty());
        jtxfCedRepre.textProperty().bindBidirectional(centro.getCenCodrepresentantelegal().getPerCedulaProperty());
        jtxfNombreRepreLegal.textProperty().bindBidirectional(centro.getCenCodrepresentantelegal().getNombreCompletoProperty());
        jcmbEstado.valueProperty().bindBidirectional(centro.getCenEstadoProperty());
        jtxfLogo.textProperty().bindBidirectional(centro.getCenLogoProperty());
    }
    
    private void bindSede(){
        jtxfNombreSede.textProperty().bindBidirectional(sede.getSedNombreProperty());
        jtxjDescripcionSede.textProperty().bindBidirectional(sede.getSedDescripcionProperty());
        jtxfCedEncargadoSede.textProperty().bind(sede.getSedCodencargado().getPerCedulaProperty());
        jtxfNomEncargadoSede.textProperty().bindBidirectional(sede.getSedCodencargado().getNombreCompletoProperty());
        jtxfTelefonos.textProperty().bindBidirectional(sede.getSedTelefonosProperty());
        jtxfEmail.textProperty().bindBidirectional(sede.getSedEmailProperty());
    }

    private void unbindCentro() {
        jtxfCedJuridica.textProperty().unbindBidirectional(centro.getCenCedulajuridicaProperty());
        jtxfNombreCentro.textProperty().unbindBidirectional(centro.getCenNombreProperty());
        jtxfCedRepre.textProperty().unbindBidirectional(centro.getCenCodrepresentantelegal().getPerCedulaProperty());
        jtxfNombreRepreLegal.textProperty().unbindBidirectional(centro.getCenCodrepresentantelegal().getNombreCompletoProperty());
        jcmbEstado.valueProperty().unbindBidirectional(centro.getCenEstadoProperty());
        jtxfLogo.textProperty().unbindBidirectional(centro.getCenLogoProperty());
    }

    private void unbindSede(){
        jtxfNombreSede.textProperty().unbindBidirectional(sede.getSedNombreProperty());
        jtxjDescripcionSede.textProperty().unbindBidirectional(sede.getSedDescripcionProperty());
        jtxfCedEncargadoSede.textProperty().bind(sede.getSedCodencargado().getPerCedulaProperty());
        jtxfNomEncargadoSede.textProperty().unbindBidirectional(sede.getSedCodencargado().getNombreCompletoProperty());
        jtxfTelefonos.textProperty().unbindBidirectional(sede.getSedTelefonosProperty());
        jtxfEmail.textProperty().unbindBidirectional(sede.getSedEmailProperty());
    }
    
    private void bindListaSedes() {
        if (sedes != null) {
            tbvSedes.setItems(sedes);
            tbvSedes.refresh();
        }
        tbcDescripcionSede.setCellValueFactory(new PropertyValueFactory<>("sedDescripcion"));
        tbcTelefonos.setCellValueFactory(new PropertyValueFactory<>("sedTelefonos"));
        tbcFax.setCellValueFactory(new PropertyValueFactory<>("sedFax"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("sedEmail"));
    }

    private void addListenerTable(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindSede();
                this.sede = (BikSede) newSelection;
                bindSede();
            }
        });
    }
    
    private void agregarSedeALista(BikSede sede) {
        BikSede nueva = new BikSede();
        nueva.setSedNombre(sede.getSedNombre());
        nueva.setSedDescripcion(sede.getSedDescripcion());
        nueva.setSedTelefonos(sede.getSedTelefonos());
        nueva.setSedFax(sede.getSedFax());
        nueva.setSedEmail(sede.getSedEmail());
        //nueva.setSedCodencargado(this.);
        if (!this.centro.getBikSedeList().contains(nueva)) {
            this.centro.getBikSedeList().add(nueva);
            this.sedes.add(nueva);
        } else {
            this.centro.getBikSedeList().set(this.centro.getBikSedeList().indexOf(nueva), nueva);
        }
        tbvSedes.refresh();
    }
    
    /*
    private void cargarSedes() {
        Resultado<ArrayList<BikCentro>> resultado = CentroDao.getInstance().findAll();
        if (!resultado.getResultado().equals(TipoResultado.ERROR)) {
            centros = FXCollections.observableArrayList(resultado.get());
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar centros", resultado.getMensaje());
        }
    }*/
     
    @FXML
    void limpiarCentro(ActionEvent event) {
        unbindCentro();
        this.sedes.clear();
        nuevoCentro();
        nuevaSede();
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
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar centro", nuevo.getMensaje());
    }
    
    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }
}
