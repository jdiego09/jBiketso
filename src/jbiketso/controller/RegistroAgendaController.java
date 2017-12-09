/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import jbiketso.model.dao.DetalleAgendaDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.UsuarioDao;
import jbiketso.model.entities.BikDetalleAgenda;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class RegistroAgendaController extends Controller implements Initializable {

    BikDetalleAgenda detalle;
    @FXML
    private AnchorPane acpRoot;

    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private JFXDatePicker jdpFechaInicio;

    @FXML
    private JFXDatePicker jdpFechaFin;

    @FXML
    private JFXTextField jtxfTitulo;

    @FXML
    private JFXTextArea jtxaDescripcion;

    @FXML
    private JFXTextField jtxfCedulaUsuario;

    @FXML
    private JFXTextField jtxfNombreUsuario;

    @FXML
    private JFXTextField jtxfCedulaFuncionario;

    @FXML
    private JFXTextField jtxfNombreFuncionario;

    @FXML
    private JFXTimePicker jtpHoraInicio;

    @FXML
    private JFXTimePicker jtpHoraFin;

    @FXML
    private JFXButton btnBuscaUsuario;

    @FXML
    private JFXButton btnBuscaFuncionario;

    private void init() {
        nuevoDetalleAgenda();
        bindDetalleAgenda();
        bindUsuario();
        bindFuncionario();
    }

    private void nuevoDetalleAgenda() {
        this.detalle = new BikDetalleAgenda();
    }

    private void bindUsuario() {
        jtxfCedulaUsuario.textProperty().bindBidirectional(this.detalle.getDeaCodusuario().getUsuPercodigo().getPerCedulaProperty());
        jtxfNombreUsuario.textProperty().bindBidirectional(this.detalle.getDeaCodusuario().getUsuPercodigo().getNombreCompletoProperty());
    }

    private void bindDetalleAgenda() {
        jtxfTitulo.textProperty().bindBidirectional(this.detalle.getTituloProperty());
        jtxaDescripcion.textProperty().bindBidirectional(this.detalle.getDetalleProperty());
    }

    private void unbindUsuario() {
        jtxfCedulaUsuario.textProperty().unbindBidirectional(this.detalle.getDeaCodusuario().getUsuPercodigo().getPerCedulaProperty());
        jtxfNombreUsuario.textProperty().unbindBidirectional(this.detalle.getDeaCodusuario().getUsuPercodigo().getNombreCompletoProperty());
    }

    private void bindFuncionario() {
        jtxfCedulaFuncionario.textProperty().bindBidirectional(this.detalle.getDeaFuncodigo().getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().bindBidirectional(this.detalle.getDeaFuncodigo().getFunPercodigo().getNombreCompletoProperty());
    }

    private void unbindFuncionario() {
        jtxfCedulaFuncionario.textProperty().unbindBidirectional(this.detalle.getDeaFuncodigo().getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().unbindBidirectional(this.detalle.getDeaFuncodigo().getFunPercodigo().getNombreCompletoProperty());
    }

    private void unbindDetalleAgenda() {
        jtxfTitulo.textProperty().unbindBidirectional(this.detalle.getTituloProperty());
        jtxaDescripcion.textProperty().unbindBidirectional(this.detalle.getDetalleProperty());
    }

    private void traerUsuario() {
        Resultado<String> cedulaValida = PersonaDao.getInstance().cedulaValida(jtxfCedulaUsuario.getText());
        if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Cédula", cedulaValida.getMensaje());
            this.jtxfCedulaUsuario.requestFocus();
            return;
        }
        unbindUsuario();
        Resultado<BikUsuario> resultado = UsuarioDao.getInstance().getUsuarioByCedula(jtxfCedulaUsuario.getText());
        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Buscar usuario", resultado.getMensaje());
            return;
        }
        if (resultado.get() != null && resultado.get().getUsuCodigo() != null && resultado.get().getUsuCodigo() > 0) {
            this.detalle.setDeaCodusuario(resultado.get());

        }
        bindUsuario();
    }

    @FXML
    void buscaFuncionario(ActionEvent event) {

    }

    @FXML
    void buscaUsuario(ActionEvent event) {
// Llamar ventana busqueda
        BusquedaController busquedaController = (BusquedaController) AppWindowController.getInstance().getController("bik_busqueda");
        busquedaController.busquedaUsuarios();
        AppWindowController.getInstance().goViewInWindowModal("bik_busqueda", getStage());
        BikPersona buscado = (BikPersona) busquedaController.getResultado();
        if (buscado != null) {
            this.jtxfCedulaUsuario.setText(buscado.getPerCedula());
            traerUsuario();
        }
    }

    @FXML
    void guardarDetalleAgenda(ActionEvent event) {
        DetalleAgendaDao.getInstance().setAgenda(detalle);
        Resultado<BikDetalleAgenda> resultado = DetalleAgendaDao.getInstance().save();

        if (resultado.getResultado().equals(TipoResultado.ERROR)) {
            AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar detalle agenda", resultado.getMensaje());
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Registrar detalle agenda", resultado.getMensaje());
        }
    }

    @FXML
    void regresar(ActionEvent event) {
        AppWindowController.getInstance().goHome();
    }

    @Override
    public void initialize() {
        init();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

}
