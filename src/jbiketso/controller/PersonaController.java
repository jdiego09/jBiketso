/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class PersonaController extends Controller implements Initializable {

    private BikPersona persona;
    private BikDireccion direccion;
    private BikContacto contacto;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXTextField jtxfPrimerApellido, jtxfDetaDireccion, jtxfDetaContacto, jtxfCedula, jtxfSegundoApellido, jtxfNacionalidad, jtxfProfesion, jtxfNombres;

    @FXML
    private JFXButton jbtnBuscar, jbtnSalir;

    @FXML
    private TableColumn<BikDireccion, String> tbcDetDireccion;

    @FXML
    private TableView<BikContacto> tbvContactos;

    @FXML
    private Button btnAgregarContacto, btnAgregarDireccion, btnGuardarPersona, btnLimpiar, btnEliminarDireccion, btnEliminarContacto;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbGenero, jcmbTipoContacto, jcmbEstadoCivil;

    @FXML
    private TableColumn<BikContacto, String> tbcDetContacto, tbcTipoContacto;

    @FXML
    private DatePicker dtpFechaNacimiento;

    @FXML
    private TableView<BikDireccion> tbvDirecciones;

    @XmlTransient
    public ObservableList<BikDireccion> direcciones = FXCollections
            .observableArrayList();

    @XmlTransient
    public ObservableList<BikContacto> contactos = FXCollections
            .observableArrayList();

    @XmlTransient
    ObservableList<GenValorCombo> generos = FXCollections
            .observableArrayList();

    @XmlTransient
    ObservableList<GenValorCombo> estadosCivil = FXCollections
            .observableArrayList();

    @XmlTransient
    ObservableList<GenValorCombo> tiposContacto = FXCollections
            .observableArrayList();

    private void init() {
        generos.clear();
        generos.add(new GenValorCombo("M", "Masculino"));
        generos.add(new GenValorCombo("F", "Femenino"));
        jcmbGenero.setItems(generos);

        estadosCivil.clear();
        estadosCivil.add(new GenValorCombo("S", "Soltero"));
        estadosCivil.add(new GenValorCombo("C", "Casado"));
        estadosCivil.add(new GenValorCombo("D", "Divorciado"));
        estadosCivil.add(new GenValorCombo("U", "Unión libre"));
        estadosCivil.add(new GenValorCombo("O", "Otro"));
        jcmbEstadoCivil.setItems(estadosCivil);

        tiposContacto.clear();
        tiposContacto.add(new GenValorCombo("T", "Teléfono"));
        tiposContacto.add(new GenValorCombo("C", "Correo"));
        tiposContacto.add(new GenValorCombo("F", "Fax"));
        jcmbTipoContacto.setItems(tiposContacto);

        if (this.persona != null) {
            unbindPersona();
        }
        if (this.direccion != null) {
            unbindDireccion();
        }
        if (this.contacto != null) {
            unbindContacto();
        }
        nuevaPersona();
        nuevaDireccion();
        nuevoContacto();
        bindPersona();
        bindDireccion();
        bindContacto();
        bindListaDirecciones();
        bindListaContactos();

        jcmbGenero.getSelectionModel().selectFirst();
        jcmbEstadoCivil.getSelectionModel().selectFirst();
        jcmbTipoContacto.getSelectionModel().selectFirst();

        addListenerTableDireccion(tbvDirecciones);
        addListenerTableContacto(tbvContactos);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    @Override
    public void initialize() {
        init();
    }

    private void addListenerTableDireccion(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindDireccion();
                this.direccion = (BikDireccion) newSelection;
                bindDireccion();
            }
        });
    }

    private void addListenerTableContacto(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindContacto();
                this.contacto = (BikContacto) newSelection;
                bindContacto();
            }
        });
    }

    private void bindPersona() {
        jtxfCedula.textProperty().bindBidirectional(persona.getPerCedulaProperty());
        jtxfNombres.textProperty().bindBidirectional(persona.getPerNombresProperty());
        jtxfPrimerApellido.textProperty().bindBidirectional(persona.getPerPrimerapellidoProperty());
        jtxfSegundoApellido.textProperty().bindBidirectional(persona.getPerSegundoapellidoProperty());
        jcmbEstadoCivil.valueProperty().bindBidirectional(persona.getPerEstadocivilProperty());
        jtxfNacionalidad.textProperty().bindBidirectional(persona.getPerNacionalidadProperty());
        dtpFechaNacimiento.valueProperty().bindBidirectional(persona.getPerFechanacimientoProperty());
        jcmbGenero.valueProperty().bindBidirectional(persona.getPerGeneroProperty());
        jtxfProfesion.textProperty().bindBidirectional(persona.getPerProfesionProperty());
    }

    private void unbindPersona() {
        jtxfCedula.textProperty().unbindBidirectional(persona.getPerCedulaProperty());
        jtxfNombres.textProperty().unbindBidirectional(persona.getPerNombresProperty());
        jtxfPrimerApellido.textProperty().unbindBidirectional(persona.getPerPrimerapellidoProperty());
        jtxfSegundoApellido.textProperty().unbindBidirectional(persona.getPerSegundoapellidoProperty());
        jcmbEstadoCivil.valueProperty().unbindBidirectional(persona.getPerEstadocivilProperty());
        jtxfNacionalidad.textProperty().unbindBidirectional(persona.getPerNacionalidadProperty());
        dtpFechaNacimiento.valueProperty().unbindBidirectional(persona.getPerFechanacimientoProperty());
        jcmbGenero.valueProperty().unbindBidirectional(persona.getPerGeneroProperty());
        jtxfProfesion.textProperty().unbindBidirectional(persona.getPerProfesionProperty());
    }

    private void bindDireccion() {
        jtxfDetaDireccion.textProperty().bindBidirectional(direccion.getDetalleDireccionProperty());
    }

    private void unbindDireccion() {
        jtxfDetaDireccion.textProperty().unbindBidirectional(direccion.getDetalleDireccionProperty());
    }

    private void bindContacto() {
        jtxfDetaContacto.textProperty().bindBidirectional(contacto.getDetalleContactoProperty());
        jcmbTipoContacto.valueProperty().bindBidirectional(contacto.getTipoContactoProperty());
    }

    private void unbindContacto() {
        jtxfDetaContacto.textProperty().unbindBidirectional(contacto.getDetalleContactoProperty());
        jcmbTipoContacto.valueProperty().unbindBidirectional(contacto.getTipoContactoProperty());
    }

    private void nuevaPersona() {
        this.persona = new BikPersona();
        this.persona.setBikDireccionList(new ArrayList<>());
        this.persona.setBikContactoList(new ArrayList<>());
        jtxfCedula.setDisable(false);
    }

    private void nuevaDireccion() {
        this.direccion = new BikDireccion();
    }

    private void nuevoContacto() {
        this.contacto = new BikContacto();
    }

    private void bindListaDirecciones() {
        if (direcciones != null) {
            tbvDirecciones.setItems(direcciones);
            tbvDirecciones.refresh();
        }
        tbcDetDireccion.setCellValueFactory(new PropertyValueFactory<>("dirDetalle"));
    }

    private void bindListaContactos() {
        if (contactos != null) {
            tbvContactos.setItems(contactos);
            tbvContactos.refresh();
        }
        tbcDetContacto.setCellValueFactory(new PropertyValueFactory<>("conDetalle"));
        tbcTipoContacto.setCellValueFactory(new PropertyValueFactory<>("descripcionTipoContacto"));
    }

    private void agregarDireccionALista(BikDireccion direccion) {
        BikDireccion nueva = new BikDireccion();
        nueva.setDirDetalle(direccion.getDirDetalle());
        nueva.setDirPercodigo(this.persona);

        if (!this.persona.getBikDireccionList().contains(nueva)) {
            this.persona.getBikDireccionList().add(nueva);
            this.direcciones.add(nueva);
        } else {
            this.persona.getBikDireccionList().set(this.persona.getBikDireccionList().indexOf(nueva), nueva);
            this.direcciones.set(this.direcciones.indexOf(nueva), nueva);
        }
        tbvDirecciones.refresh();
    }

    @FXML
    private void guardarPersona(ActionEvent event) {
        Resultado<String> validaPersona = PersonaDao.getInstance().validaPersona(this.persona);
        if (validaPersona.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Guardar Persona", validaPersona.getMensaje());
            return;
        }
        PersonaDao.getInstance().setPersona(this.persona);
        Resultado<BikPersona> resultado = PersonaDao.getInstance().save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar persona", resultado.getMensaje());
            return;
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar persona", resultado.getMensaje());
    }

    private void validarPersona() {
        String cedula = jtxfCedula.getText();
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(cedula);
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            return;
        }
        unbindPersona();
        unbindDireccion();
        unbindContacto();
        nuevaDireccion();
        nuevoContacto();
        Resultado<BikPersona> resultado = PersonaDao.getInstance().getPersona(cedula);
        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar persona", resultado.getMensaje());
            return;
        }
        if (resultado.get() != null && resultado.get().getPerCodigo() != null && resultado.get().getPerCodigo() > 0) {
            this.persona = resultado.get();
            jtxfCedula.setDisable(true);
            //carga las direcciones
            Resultado<ArrayList<BikDireccion>> direccionesResult = PersonaDao.getInstance().getDirecciones(this.persona);
            direcciones.clear();
            direccionesResult.get().stream().forEach(direcciones::add);

            //carga los contactos
            Resultado<ArrayList<BikContacto>> contactosResult = PersonaDao.getInstance().getContactos(this.persona);
            contactos.clear();
            contactosResult.get().stream().forEach(contactos::add);
        } else {
            nuevaPersona();
            this.persona.setPerCedula(cedula);
        }
        bindPersona();
        bindDireccion();
        bindContacto();
    }

    @FXML
    void buscarPersona(ActionEvent event) {

    }

    @FXML
    private void agregarContacto(ActionEvent event) {
        agregarContactoALista(contacto);
        unbindContacto();
        nuevoContacto();
        bindContacto();
    }

    @FXML
    private void agregarDireccion(ActionEvent event) {
        agregarDireccionALista(direccion);
        unbindDireccion();
        nuevaDireccion();
        bindDireccion();
    }

    @FXML
    private void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @FXML
    private void limpiarPersona(ActionEvent event) {
        unbindPersona();
        unbindDireccion();
        unbindContacto();
        this.direcciones.clear();
        this.contactos.clear();
        nuevaPersona();
        nuevaDireccion();
        nuevoContacto();
        bindPersona();
        bindDireccion();
        bindContacto();
    }

    private void agregarContactoALista(BikContacto contacto) {
        BikContacto nuevo = new BikContacto();
        nuevo.setConTipo(contacto.getConTipo());
        nuevo.setConDetalle(contacto.getConDetalle());
        nuevo.setConPercodigo(this.persona);
        if (!this.persona.getBikContactoList().contains(nuevo)) {
            this.persona.getBikContactoList().add(nuevo);
            this.contactos.add(nuevo);
        } else {
            this.persona.getBikContactoList().set(this.persona.getBikContactoList().indexOf(nuevo), nuevo);
            this.contactos.set(this.contactos.indexOf(nuevo), nuevo);
        }
        tbvContactos.refresh();
    }

    @FXML
    void eliminarContacto(ActionEvent event) {
        Resultado<String> resultado = PersonaDao.getInstance().deleteContacto(contacto);
        if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            this.persona.getBikContactoList().remove(contacto);
            this.contactos.remove(contacto);
            nuevoContacto();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar contacto", resultado.getMensaje());
        }
    }

    @FXML
    void eliminarDireccion(ActionEvent event) {
        Resultado<String> resultado = PersonaDao.getInstance().deleteDireccion(direccion);
        if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
            this.persona.getBikDireccionList().remove(direccion);
            this.direcciones.remove(direccion);
            nuevaDireccion();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar dirección", resultado.getMensaje());
        }
    }

    @FXML
    void cedulaOnEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
            validarPersona();
        }
    }
}
