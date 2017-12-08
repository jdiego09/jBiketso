/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.RolesDao;
import jbiketso.model.entities.BikRoles;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;

/**
 *
 * @author Luis Diego
 */
public class UsuariosSistemaController extends Controller {

    private BikUsuariosSistema usuarioSistema;

    @FXML
    private AnchorPane apRoot;

    @FXML
    private JFXButton jbtnSalir, jbtnAgregarRol, jbtnEliminarRol;

    @FXML
    private Button btnLimpiar, btnGuardaUsuario;

    @FXML
    private JFXTextField jtxfCodigo;

    @FXML
    private JFXPasswordField jtpfContrasena, jtpfRepeContrasena;

    @FXML
    private JFXComboBox<GenValorCombo> jcmbEstado;

    @FXML
    private TableView<BikUsuariosSistema> tbvUsuarios;

    @FXML
    private TableColumn<BikUsuariosSistema, String> tbcCodigo, tbcEstado;

    @FXML
    private TableView<BikRoles> tbvRolesActivos;

    @FXML
    private TableColumn<BikRoles, String> tbcCodRolActivo;

    @FXML
    private TableView<BikRoles> tbvRolesUsuario;

    @FXML
    private TableColumn<BikRoles, String> tbcCodRolUsuario;

    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikUsuariosSistema> usuariosSistema = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikRoles> rolesActivos = FXCollections
            .observableArrayList();

    @Override
    public void initialize() {
        iniciarPantalla();
    }

    private void nuevoUsuarioSistema() {
        this.usuarioSistema = new BikUsuariosSistema();
        this.usuarioSistema.setBikRolesUsuariosList(new ArrayList<>());
    }

    private void iniciarPantalla() {

        nuevoUsuarioSistema();

        this.estados.clear();
        this.estados.add(new GenValorCombo("A", "Activo"));
        this.estados.add(new GenValorCombo("I", "Inactivo"));
        jcmbEstado.setItems(this.estados);

        if (this.usuarioSistema != null) {
            unbindUsuarioSistema();
        }

        bindUsuarioSistema();

        bindListaUsuariosSistema();
        bindListaRolesActivos();

        jcmbEstado.getSelectionModel().selectFirst();

        addListenerTableUsuariosSistema(tbvUsuarios);

        this.rolesActivos.clear();
        tbvRolesActivos.getItems().clear();
        Resultado<ArrayList<BikRoles>> roles = RolesDao.getInstance().findRolesByEstado();
        roles.get().stream().forEach(this.rolesActivos::add);
    }

    private void bindUsuarioSistema() {
        jtxfCodigo.textProperty().bindBidirectional(this.usuarioSistema.getUssCodigoProperty());
        jcmbEstado.valueProperty().bindBidirectional(this.usuarioSistema.getUssEstadoProperty());
    }

    private void unbindUsuarioSistema() {
        jtxfCodigo.textProperty().unbindBidirectional(this.usuarioSistema.getUssCodigoProperty());
        jcmbEstado.valueProperty().unbindBidirectional(this.usuarioSistema.getUssEstadoProperty());
    }

    private void addListenerTableUsuariosSistema(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                unbindUsuarioSistema();
                this.usuarioSistema = (BikUsuariosSistema) newSelection;
                bindUsuarioSistema();
            }
        });
    }

    private void bindListaUsuariosSistema() {
        if (this.usuariosSistema != null) {
            tbvUsuarios.setItems(this.usuariosSistema);
            tbvUsuarios.refresh();
        }
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("ussCodigo"));
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
    }

    private void bindListaRolesActivos() {
        if (this.rolesActivos != null) {
            tbvRolesActivos.setItems(this.rolesActivos);
            tbvRolesActivos.refresh();
        }
        tbcCodRolActivo.setCellValueFactory(new PropertyValueFactory<>("rolCodigo"));
    }

    @FXML
    void regresar(ActionEvent event) {

    }

}
