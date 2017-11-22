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
import java.io.File;
import java.util.ArrayList;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.CentroDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.entities.BikCentro;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikSede;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class CentroController extends Controller {

    private BikCentro centro;
    private BikSede sede;
    private BikPersona encargadoCentro;
    private BikPersona encargadoSede;

    @FXML
    private TableView<BikSede> tbvSedes;

    @FXML
    private TableColumn<BikSede, String> tbcDescripcionSede, tbcTelefonos, tbcEmail;

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

    private void nuevoCentro() {
        this.centro = new BikCentro();
    }

    private void nuevaSede() {
        this.sede = new BikSede();
    }

    private void nuevaPersonaEncargadoCentro() {
        this.encargadoCentro = new BikPersona();
    }

    private void nuevaPersonaEncargadoSede() {
        this.encargadoSede = new BikPersona();
    }

    private void bindCentro() {
        jtxfCedJuridica.textProperty().bindBidirectional(centro.getCenCedulajuridicaProperty());
        jtxfNombreCentro.textProperty().bindBidirectional(centro.getCenNombreProperty());
        jtxfCedRepre.textProperty().bindBidirectional(this.encargadoCentro.getPerCedulaProperty());
        jtxfNombreRepreLegal.textProperty().bindBidirectional(this.encargadoCentro.getNombreCompletoProperty());
        jcmbEstado.valueProperty().bindBidirectional(centro.getCenEstadoProperty());
        jtxfLogo.textProperty().bindBidirectional(centro.getCenLogoProperty());
    }

    private void unbindCentro() {
        jtxfCedJuridica.textProperty().unbindBidirectional(centro.getCenCedulajuridicaProperty());
        jtxfNombreCentro.textProperty().unbindBidirectional(centro.getCenNombreProperty());
        jtxfCedRepre.textProperty().unbindBidirectional(this.encargadoCentro.getPerCedulaProperty());
        jtxfNombreRepreLegal.textProperty().unbindBidirectional(this.encargadoCentro.getNombreCompletoProperty());
        jcmbEstado.valueProperty().unbindBidirectional(centro.getCenEstadoProperty());
        jtxfLogo.textProperty().unbindBidirectional(centro.getCenLogoProperty());
    }

    private void bindSede() {
        jtxfNombreSede.textProperty().bindBidirectional(sede.getSedNombreProperty());
        jtxjDescripcionSede.textProperty().bindBidirectional(sede.getSedDescripcionProperty());
        jtxfCedEncargadoSede.textProperty().bindBidirectional(this.encargadoSede.getPerCedulaProperty());
        jtxfNomEncargadoSede.textProperty().bindBidirectional(this.encargadoSede.getNombreCompletoProperty());
        jtxfTelefonos.textProperty().bindBidirectional(sede.getSedTelefonosProperty());
        jtxfEmail.textProperty().bindBidirectional(sede.getSedEmailProperty());
    }

    private void unbindSede() {
        jtxfNombreSede.textProperty().unbindBidirectional(sede.getSedNombreProperty());
        jtxjDescripcionSede.textProperty().unbindBidirectional(sede.getSedDescripcionProperty());
        jtxfCedEncargadoSede.textProperty().unbindBidirectional(this.encargadoSede.getPerCedulaProperty());
        jtxfNomEncargadoSede.textProperty().unbindBidirectional(this.encargadoSede.getNombreCompletoProperty());
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
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("sedEmail"));
    }

    private void addListenerTable(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindSede();
                this.sede = (BikSede) newSelection;
                obtenerRepreLegal(this.sede.getSedCodencargado().getPerCedula(), "S");
                bindSede();
            }
        });
    }

    private void agregarSedeALista(BikSede sede) {
        BikSede nueva = new BikSede();
        nueva.setSedCodigo(sede.getSedCodigo());
        nueva.setSedNombre(sede.getSedNombre());
        nueva.setSedDescripcion(sede.getSedDescripcion());
        nueva.setSedTelefonos(sede.getSedTelefonos());
        nueva.setSedFax(sede.getSedFax());
        nueva.setSedEmail(sede.getSedEmail());
        nueva.setSedCodencargado(this.encargadoSede);
        nueva.setSedCencodigo(this.centro);
        if (!this.centro.getBikSedeList().contains(nueva)) {
            this.centro.getBikSedeList().add(nueva);
            this.sedes.add(nueva);
        } else {
            this.centro.getBikSedeList().set(this.centro.getBikSedeList().indexOf(nueva), nueva);
        }
        tbvSedes.refresh();
    }

    private void cargarSedes(BikCentro centro) {
        Resultado<ArrayList<BikSede>> listaSedes = CentroDao.getInstance().getSedes(centro);
        this.sedes.clear();
        listaSedes.get().stream().forEach(this.sedes::add);
        if (!listaSedes.getResultado().equals(TipoResultado.ERROR)) {
            this.sedes = FXCollections.observableArrayList(listaSedes.get());
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Consultar sedes", listaSedes.getMensaje());
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

    @FXML
    void limpiarCentro(ActionEvent event) {
        unbindCentro();
        this.sedes.clear();
        nuevoCentro();
        nuevaSede();
        nuevaPersonaEncargadoCentro();
        nuevaPersonaEncargadoSede();
        bindCentro();
        jtxfCedJuridica.setDisable(false);
        jtxfNombreRepreLegal.setDisable(false);
    }

    public void iniciarForma() {

        estados.clear();
        estados.add(new GenValorCombo("A", "Activo"));
        estados.add(new GenValorCombo("I", "Inactivo"));
        jcmbEstado.setItems(estados);

        jtxfCedJuridica.setDisable(false);
        jtxfNombreRepreLegal.setDisable(false);
        jtxfNomEncargadoSede.setDisable(false);

        if (this.centro != null) {
            unbindCentro();
        }
        if (this.sede != null) {
            unbindSede();
        }

        nuevoCentro();
        nuevaSede();
        nuevaPersonaEncargadoCentro();
        nuevaPersonaEncargadoSede();
        bindCentro();
        bindSede();
        bindListaSedes();
        
        jcmbEstado.getSelectionModel().selectFirst();

        addListenerTable(tbvSedes);

    }

    @Override
    public void initialize() {
        iniciarForma();
    }

    @FXML
    void guardarCentro(ActionEvent event) {
        CentroDao.getInstance().setCentro(this.centro);
        this.centro.setCenCodrepresentantelegal(this.encargadoCentro);
        Resultado<BikCentro> nuevo = CentroDao.getInstance().save();
        if (nuevo.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar centro", nuevo.getMensaje());
            return;
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar centro", nuevo.getMensaje());
    }

    private void validaCedulaJuridica() {
        String cedJuridica = jtxfCedJuridica.getText();
        unbindCentro();
        unbindSede();
        nuevaSede();
        Resultado<BikCentro> resultado = CentroDao.getInstance().getCentro(cedJuridica);
        if (resultado.get() != null && resultado.get().getCenCodigo() != null && resultado.get().getCenCodigo() > 0) {
            this.centro = resultado.get();
            obtenerRepreLegal(this.centro.getCenCodrepresentantelegal().getPerCedula(), "C");
            cargarSedes(this.centro);
            jtxfCedJuridica.setDisable(true);
        } else {
            nuevoCentro();
            this.centro.setCenCedulajuridica(cedJuridica);
        }
        bindCentro();
        bindSede();
    }

    @FXML
    void cedulaJuridicaOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            validaCedulaJuridica();
        }
    }

    private void obtenerRepreLegal(String cedula, String dato) {
        if (dato.equals("C")) {
            this.encargadoCentro = getPersona(cedula).get();
            if (this.encargadoCentro.getNombreCompleto() != null) {
                jtxfNombreRepreLegal.setDisable(true);
            } else {
                jtxfNombreRepreLegal.setDisable(false);
            }
        } else {
            this.encargadoSede = getPersona(cedula).get();
            if (this.encargadoSede.getNombreCompleto() != null) {
                jtxfNomEncargadoSede.setDisable(true);
            } else {
                jtxfNomEncargadoSede.setDisable(false);
            }
        }

    }

    @FXML
    void cedulaRepreCentroOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            unbindCentro();
            unbindSede();
            nuevaSede();
            obtenerRepreLegal(this.encargadoCentro.getPerCedula(), "C");
            bindCentro();
            bindSede();
        }
    }

    @FXML
    void cedEncargadoSedeOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            unbindCentro();
            unbindSede();
            obtenerRepreLegal(this.encargadoSede.getPerCedula(), "S");
            bindCentro();
            bindSede();
        }
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @FXML
    private void buscarArchivoLogo() {

        FileChooser rutaLogo = new FileChooser();
        rutaLogo.setTitle("Ruta logo centro");
        rutaLogo.getExtensionFilters().addAll(
                new ExtensionFilter("PNG", "*.png"),
                new ExtensionFilter("JPG", "*.jpg"));

        File archivoLogo = rutaLogo.showOpenDialog(null);

        if (archivoLogo != null) {
            this.centro.setCenLogo(archivoLogo.getAbsolutePath());
        }

    }

    @FXML
    private void agregarSede() {
        agregarSedeALista(this.sede);
    }

    @FXML
    private void eliminarSede() {
        Resultado<String> resultado = CentroDao.getInstance().deleteSede(this.sede);
        if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            this.centro.getBikSedeList().remove(this.sede);
            this.sedes.remove(this.sede);
            nuevaSede();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar sede", resultado.getMensaje());
        }
    }

}
