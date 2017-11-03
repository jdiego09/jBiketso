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
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ContactoDao;
import jbiketso.model.dao.DireccionDao;
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
public class PersonaController implements Initializable {

    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXTextField jtxfPrimerApellido, jtxfDetaDireccion, jtxfDetaContacto, jtxfCedula, jtxfSegundoApellido, jtxfNacionalidad, jtxfProfesion, jtxfNombres;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private TableColumn<BikDireccion, String> tbcDetDireccion;

    @FXML
    private TableView<BikContacto> tbvContactos;

    @FXML
    private Button btnAgregarContacto, btnAgregarDireccion, btnGuardarPersona, btnLimpiar;

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

    PersonaDao personaDao;
    DireccionDao direccionDao;
    ContactoDao contactoDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.personaDao = new PersonaDao();
        this.direccionDao = new DireccionDao();
        this.contactoDao = new ContactoDao();

        generos.add(new GenValorCombo("M", "Masculino"));
        generos.add(new GenValorCombo("F", "Femenino"));
        jcmbGenero.setItems(generos);

        estadosCivil.add(new GenValorCombo("S", "Soltero"));
        estadosCivil.add(new GenValorCombo("C", "Casado"));
        estadosCivil.add(new GenValorCombo("D", "Divorciado"));
        estadosCivil.add(new GenValorCombo("U", "Unión libre"));
        estadosCivil.add(new GenValorCombo("O", "Otro"));
        jcmbEstadoCivil.setItems(estadosCivil);

        tiposContacto.add(new GenValorCombo("T", "Teléfono"));
        tiposContacto.add(new GenValorCombo("C", "Correo"));
        tiposContacto.add(new GenValorCombo("F", "Fax"));
        jcmbTipoContacto.setItems(tiposContacto);

        bindPersona();
        bindListaDirecciones();
        bindListaContactos();

    }

    private void bindPersona() {

        jtxfCedula.textProperty().bindBidirectional(personaDao.cedula);
        jtxfNombres.textProperty().bindBidirectional(personaDao.nombres);
        jtxfPrimerApellido.textProperty().bindBidirectional(personaDao.primerApellido);
        jtxfSegundoApellido.textProperty().bindBidirectional(personaDao.segundoApellido);
        jcmbEstadoCivil.valueProperty().bindBidirectional(personaDao.estadoCivil);
        jtxfNacionalidad.textProperty().bindBidirectional(personaDao.nacionalidad);
        //dtpFechaNacimiento.valueProperty().bindBidirectional(personaDao.fechaNacimiento);
        jcmbGenero.valueProperty().bindBidirectional(personaDao.genero);
        jtxfProfesion.textProperty().bindBidirectional(personaDao.profesion);

        jtxfDetaDireccion.textProperty().bindBidirectional(direccionDao.detDireccion);

        jtxfDetaContacto.textProperty().bindBidirectional(contactoDao.detContacto);
        jcmbTipoContacto.valueProperty().bindBidirectional(contactoDao.tipo);

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
        tbcTipoContacto.setCellValueFactory(new PropertyValueFactory<>("conTipo"));
    }

    private void agregarDireccionALista(BikDireccion direccion) {
        /*if (!this.direcciones.contains(direccion)) {
            this.direcciones.add(direccion);
        } else {
            this.direcciones.set(this.direcciones.indexOf(direccion), direccion);
        }*/
        this.direcciones.add(direccion);
        tbvDirecciones.refresh();
    }

    @FXML
    private void guardarPersona(ActionEvent event) {

        Resultado<BikPersona> resultado = new Resultado<>();

        if(personaDao.getDireccionDao().isEmpty())
            personaDao.setDireccionDao(new ArrayList<>());
        
        direcciones.stream().forEach(d -> personaDao.getDireccionDao().add(new DireccionDao(d)));
        //contactos.stream().forEach(d -> personaDao.getContactoDao().add(new ContactoDao(d)));

        resultado = personaDao.save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar persona", resultado.getMensaje());
            return;
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar persona", resultado.getMensaje());
    }

    @FXML
    private void agregarDireccion(ActionEvent event) {
        agregarDireccionALista(new BikDireccion(direccionDao));
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @FXML
    void limpiarModulo(ActionEvent event) {

    }

}
