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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import jbiketso.model.dao.DetalleAgendaDao;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.UsuarioDao;
import jbiketso.model.entities.BikDetalleAgenda;
import jbiketso.model.entities.BikFuncionario;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Formater;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

public class RegistroAgendaController extends Controller implements Initializable {
    
    BikDetalleAgenda detalle;
    BikUsuario usuario;
    BikFuncionario funcionario;
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
        this.usuario = new BikUsuario();
        this.funcionario = new BikFuncionario();
        this.funcionario.setFunPercodigo(new BikPersona());
    }
    
    private void bindUsuario() {
        jtxfCedulaUsuario.textProperty().bindBidirectional(this.usuario.getUsuPercodigo().getPerCedulaProperty());
        jtxfNombreUsuario.textProperty().bindBidirectional(this.usuario.getUsuPercodigo().getNombreCompletoProperty());
    }
    
    private void bindDetalleAgenda() {
        jtxfTitulo.textProperty().bindBidirectional(this.detalle.getTituloProperty());
        jtxaDescripcion.textProperty().bindBidirectional(this.detalle.getDetalleProperty());
        jdpFechaInicio.setValue(LocalDate.now());
        jdpFechaFin.setValue(LocalDate.now());
    }
    
    private void unbindUsuario() {
        jtxfCedulaUsuario.textProperty().unbindBidirectional(this.usuario.getUsuPercodigo().getPerCedulaProperty());
        jtxfNombreUsuario.textProperty().unbindBidirectional(this.usuario.getUsuPercodigo().getNombreCompletoProperty());
    }
    
    private void bindFuncionario() {
        jtxfCedulaFuncionario.textProperty().bindBidirectional(this.funcionario.getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().bindBidirectional(this.funcionario.getFunPercodigo().getNombreCompletoProperty());
    }
    
    private void unbindFuncionario() {
        jtxfCedulaFuncionario.textProperty().unbindBidirectional(this.funcionario.getFunPercodigo().getPerCedulaProperty());
        jtxfNombreFuncionario.textProperty().unbindBidirectional(this.funcionario.getFunPercodigo().getNombreCompletoProperty());
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
        if (jdpFechaInicio.getValue() != null && jtpHoraInicio.getValue() != null
                && (detalle.getDeaTitulo() != null && !detalle.getDeaTitulo().isEmpty())
                && (detalle.getDeaDetalle() != null && !detalle.getDeaDetalle().isEmpty())) {
            detalle.setDeaAgecodigo(Aplicacion.getInstance().getDefaultAgenda());
            
            String fechaInicio = jdpFechaInicio.getValue().toString() + " " + jtpHoraInicio.getValue().toString();
            LocalDateTime dateTimeInicio = LocalDateTime.parse(fechaInicio, Formater.getInstance().formatterFechaHora);
            String fechaFin = jdpFechaFin.getValue().toString() + " " + jtpHoraFin.getValue().toString();
            LocalDateTime dateTimeFin = LocalDateTime.parse(fechaFin, Formater.getInstance().formatterFechaHora);
            /*
            Resultado<Boolean> existeEvento = DetalleAgendaDao.getInstance().existeEvento(Date.from(LocalDateTime.parse(fechaInicio, Formater.getInstance().formatterFechaHora)..atZone(ZoneId.systemDefault())), java.sql.Date.valueOf(fechaFin));
            if (existeEvento.getResultado().equals(TipoResultado.ERROR)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Validar fechas evento", existeEvento.getMensaje());
            }
            if (existeEvento.get()) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Validar fechas evento", existeEvento.getMensaje());
            }*/
            detalle.setDeaFechainicio(Date.from(dateTimeInicio.atZone(ZoneId.systemDefault()).toInstant()));
            detalle.setDeaFechafin(Date.from(dateTimeFin.atZone(ZoneId.systemDefault()).toInstant()));
            detalle.setDeaUsuarioingresa(Aplicacion.getInstance().getUsuario().getUssCodigo());
            detalle.setDeaFechaingresa(new Date());
            DetalleAgendaDao.getInstance().setAgenda(detalle);
            Resultado<BikDetalleAgenda> resultado = DetalleAgendaDao.getInstance().save();
            
            if (resultado.getResultado().equals(TipoResultado.ERROR)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Registrar detalle agenda", resultado.getMensaje());
            } else {
                Aplicacion.getInstance().getDetalleAgenda().add(detalle);
                AppWindowController.getInstance().mensaje(Alert.AlertType.INFORMATION, "Registrar detalle agenda", resultado.getMensaje());
                Aplicacion.getInstance().traerEventos();
                unbindDetalleAgenda();
                unbindUsuario();
                unbindFuncionario();
                nuevoDetalleAgenda();
                bindDetalleAgenda();
                bindUsuario();
                bindFuncionario();
            }
        } else {
            AppWindowController.getInstance().mensaje(Alert.AlertType.WARNING, "Registrar detalle agenda", "Debe completar la información del evento.");
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
