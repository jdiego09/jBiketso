/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ContactoDao;
import jbiketso.model.dao.DireccionDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikModulos;
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
    private JFXTextField jtxfPrimerApellido;

    @FXML
    private JFXTextField jtxfDetaDireccion;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstadoCivil;

    @FXML
    private JFXTextField jtxfDetContacto;

    @FXML
    private JFXTextField jtxfCedula;

    @FXML
    private JFXTextField jtxfSegundoApellido;

    @FXML
    private TableColumn<BikDireccion, String> tbcDetDireccion;

    @FXML
    private TableView<BikContacto> tbvContactos;

    @FXML
    private Button btnGuardarContacto;

    @FXML
    private Button btnGuardarDireccion;

    @FXML
    private Button btnGuardarPersona;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbGenero;

    @FXML
    private JFXTextField jtxfNacionalidad;

    @FXML
    private JFXTextField jtxfProfesion;

    @FXML
    private TableColumn<BikContacto, String> tbcDetContacto;

    @FXML
    private JFXTextField jtxfNombres;

    @FXML
    private TableColumn<BikContacto, String> tbcTipoContacto;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbTipoContacto;

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

    PersonaDao personaDao;
    DireccionDao direccionDao;
    ContactoDao contactoDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.personaDao = new PersonaDao();
        bindPersona();
        //bindListaDirecciones();
        //bindListaContactos();
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

        jtxfDetContacto.textProperty().bindBidirectional(contactoDao.detContacto);
        jcmbTipoContacto.valueProperty().bindBidirectional(contactoDao.tipo);

    }

    private void bindListaDirecciones() {
        if (direcciones != null) {
            tbvDirecciones.setItems(direcciones);
            tbvDirecciones.refresh();
        }
        tbcDetDireccion.setCellValueFactory(new PropertyValueFactory<>("dir_detalle"));
    }

    private void bindListaContactos() {
        if (contactos != null) {
            tbvContactos.setItems(contactos);
            tbvContactos.refresh();
        }
        tbcDetContacto.setCellValueFactory(new PropertyValueFactory<>("con_detalle"));
        tbcTipoContacto.setCellValueFactory(new PropertyValueFactory<>("con_tipo"));
    }

    @FXML
    private void guardarPersona(ActionEvent event) {
        
        Resultado<BikPersona> resultado = new Resultado<>();
        
        direcciones.stream().forEach(d->personaDao.getDireccionDao().add(new DireccionDao(d)));
        contactos.stream().forEach(d->personaDao.getContactoDao().add(new ContactoDao(d)));
        
        resultado = personaDao.save();
        
       if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar persona", resultado.getMensaje());
            return;
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar persona", resultado.getMensaje());
    }

    @FXML
    private void agregarDireccion(ActionEvent event) {
        new BikDireccion(direccionDao);
    }

}
