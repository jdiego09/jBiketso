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
import java.util.Date;
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
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.RolesDao;
import jbiketso.model.dao.UsuariosSistemaDao;
import jbiketso.model.entities.BikRoles;
import jbiketso.model.entities.BikRolesUsuarios;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Encriptor;
import jbiketso.utils.GenValorCombo;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

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
    private TableView<BikRolesUsuarios> tbvRolesUsuario;

    @FXML
    private TableColumn<BikRolesUsuarios, String> tbcCodRolUsuario;

    @XmlTransient
    ObservableList<GenValorCombo> estados = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikUsuariosSistema> usuariosSistema = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikRoles> rolesActivos = FXCollections
            .observableArrayList();

    @XmlTransient
    private ObservableList<BikRolesUsuarios> rolesUsuario = FXCollections
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
        bindListaRolesUsuario();

        jtxfCodigo.setDisable(false);

        jcmbEstado.getSelectionModel().selectFirst();

        addListenerTableUsuariosSistema(tbvUsuarios);

        this.rolesActivos.clear();
        tbvRolesActivos.getItems().clear();
        Resultado<ArrayList<BikRoles>> roles = RolesDao.getInstance().findRolesByEstado();
        roles.get().stream().forEach(this.rolesActivos::add);

        this.usuariosSistema.clear();
        tbvUsuarios.getItems().clear();
        Resultado<ArrayList<BikUsuariosSistema>> usuarios = UsuariosSistemaDao.getInstance().findUsuariosSistema();
        usuarios.get().stream().forEach(this.usuariosSistema::add);

    }

    private void bindUsuarioSistema() {
        jtxfCodigo.textProperty().bindBidirectional(this.usuarioSistema.getUssCodigoProperty());
        jcmbEstado.valueProperty().bindBidirectional(this.usuarioSistema.getUssEstadoProperty());
        // jtpfContrasena.textProperty().bindBidirectional(this.usuarioSistema.getUssContrasenaProperty());
    }

    private void unbindUsuarioSistema() {
        jtxfCodigo.textProperty().unbindBidirectional(this.usuarioSistema.getUssCodigoProperty());
        jcmbEstado.valueProperty().unbindBidirectional(this.usuarioSistema.getUssEstadoProperty());
        // jtpfContrasena.textProperty().unbindBidirectional(this.usuarioSistema.getUssContrasenaProperty());
    }

    private void addListenerTableUsuariosSistema(TableView table) {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                unbindUsuarioSistema();

                this.usuarioSistema = (BikUsuariosSistema) newSelection;

                Resultado<ArrayList<BikRolesUsuarios>> roles = UsuariosSistemaDao.getInstance().getRolesUsuario(this.usuarioSistema.getUssCodigo());
                this.rolesUsuario.clear();
                tbvRolesUsuario.getItems().clear();
                roles.get().stream().forEach(this.rolesUsuario::add);

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

    private void bindListaRolesUsuario() {
        if (this.rolesUsuario != null) {
            tbvRolesUsuario.setItems(this.rolesUsuario);
            tbvRolesUsuario.refresh();
        }
        tbcCodRolUsuario.setCellValueFactory(b -> b.getValue().getRouRolcodigo().getRolCodigoProperty());
    }

    private void traerUsuarioSistema(String codigo) {

        unbindUsuarioSistema();

        Resultado<BikUsuariosSistema> usuario = UsuariosSistemaDao.getInstance().getUsuarioByCodigo(codigo);
        if (usuario.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar funcionario", usuario.getMensaje());
            return;
        }
        if (usuario.get() != null) {
            if (usuario.get().getUssCodigo() != null) {
                this.usuarioSistema = usuario.get();
                jtxfCodigo.setDisable(true);
                // Se cargan los roles del usuario.
                Resultado<ArrayList<BikRolesUsuarios>> roles = UsuariosSistemaDao.getInstance().getRolesUsuario(codigo);
                this.rolesUsuario.clear();
                tbvRolesUsuario.getItems().clear();
                roles.get().stream().forEach(this.rolesUsuario::add);
            }
        }
    }

    private void agregarUsuarioSistemaALista(BikUsuariosSistema usuarioSistema) {
        if (!this.usuariosSistema.contains(usuarioSistema)) {
            this.usuariosSistema.add(usuarioSistema);
        } else {
            this.usuarioSistema.setUssUsuariomodifica(Aplicacion.getInstance().getUsuario().getUssCodigo());
            this.usuarioSistema.setUssFechamodifica(new Date());
            this.usuariosSistema.set(this.usuariosSistema.indexOf(usuarioSistema), usuarioSistema);
        }
        tbvUsuarios.refresh();
    }

    @FXML
    private void guardarUsuarioSistema(ActionEvent event) {

        if (!jtpfContrasena.getText().isEmpty()) {
            if (!jtpfRepeContrasena.getText().isEmpty()) {
                if (!Encriptor.getInstance().encriptar(jtpfContrasena.getText()).equals(Encriptor.getInstance().encriptar(jtpfRepeContrasena.getText()))) {
                    AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar usuario", "Las contraseñas deben iguales.");
                    return;
                }
                this.usuarioSistema.setUssContrasena(Encriptor.getInstance().encriptar(jtpfContrasena.getText()));
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar usuario", "Debe volver a ingresar la contraseña.");
                return;
            }
        }

        UsuariosSistemaDao.getInstance().setUsuarioSistema(this.usuarioSistema);
        Resultado<BikUsuariosSistema> resultado = UsuariosSistemaDao.getInstance().save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Guardar usuario", resultado.getMensaje());
            return;
        }
        AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Guardar usuario", resultado.getMensaje());
        jtpfContrasena.setText(null);
        jtpfRepeContrasena.setText(null);
        agregarUsuarioSistemaALista(resultado.get());
    }

    @FXML
    void codigoUsuarioOnEnterKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
            if (this.usuarioSistema.getUssCodigo() != null) {
                traerUsuarioSistema(this.usuarioSistema.getUssCodigo());
            }
        }
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @FXML
    private void limpiarUsuario() {
        unbindUsuarioSistema();
        this.rolesUsuario.clear();
        nuevoUsuarioSistema();
        bindUsuarioSistema();
        this.jtxfCodigo.requestFocus();
    }

    @FXML
    private void agregarRolUsuario() {

        BikRolesUsuarios rol = new BikRolesUsuarios(this.usuarioSistema, tbvRolesActivos.getSelectionModel().getSelectedItem());
        rol.setRouEstado("A");
        rol.setRouFechaingresa(new Date());
        rol.setRouUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());

        if (!this.usuarioSistema.getBikRolesUsuariosList().contains(rol)) {
            this.usuarioSistema.getBikRolesUsuariosList().add(rol);
            this.rolesUsuario.add(rol);
            tbvRolesUsuario.refresh();
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Agregar rol", "El usuario [" + this.usuarioSistema.getUssCodigo() + "] ya tiene el rol asignado.");
        }

    }

    @FXML
    private void eliminarRolUsuario() {

        BikRolesUsuarios rol = tbvRolesUsuario.getSelectionModel().getSelectedItem();

        if (rol.getRouCodigo() != null && rol.getRouCodigo() > 0) {
            Resultado<String> resultado = UsuariosSistemaDao.getInstance().deleteRol(rol);

            if (resultado.getResultado().equals(TipoResultado.SUCCESS)) {
                this.usuarioSistema.getBikRolesUsuariosList().remove(rol);
                this.rolesUsuario.remove(rol);
                tbvRolesUsuario.refresh();
            } else {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Eliminar rol", resultado.getMensaje());
            }

        } else {
            this.usuarioSistema.getBikRolesUsuariosList().remove(rol);
            this.rolesUsuario.remove(rol);
            tbvRolesUsuario.refresh();
        }

    }

}
