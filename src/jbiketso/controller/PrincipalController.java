package jbiketso.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.AgendaDao;
import jbiketso.model.dao.SeguridadDao;
import jbiketso.model.entities.BikCentro;
import jbiketso.model.entities.BikDetalleAgenda;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.DateUtil;
import jbiketso.utils.Formater;
import jbiketso.utils.Parametros;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;
import jfxtras.icalendarfx.components.VEvent;

public class PrincipalController extends Controller implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private VBox vbxContainer;
    @FXML
    private TableView<BikDetalleAgenda> tbvEventos;
    @FXML
    private TableColumn<BikDetalleAgenda, String> tbcHora;
    @FXML
    private TableColumn<BikDetalleAgenda, String> tbcEvento;

    @FXML
    private ImageView imgLogo;
    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblFecha;

    @FXML
    private JFXHamburger hmbMenu;

    @FXML
    private JFXDrawer jdrwMenu;
    private Pane panMenu;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

    public void init() {
        lblUsuario.setText("Usuario: " + Parametros.getInstance().getParametro("Usuario"));

        lblFecha.setText("Fecha: " + sdf.format(new Date()));
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> lblFecha.setText("Fecha: " + sdf.format(new Date()))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        cargarSeguridad();

        panMenu = (Pane) AppWindowController.getInstance().getView("bik_principal_menu");

        jdrwMenu.setSidePane(panMenu);
        jdrwMenu.setPrefWidth(200);
        jdrwMenu.setOverLayVisible(false);
        jdrwMenu.setResizableOnDrag(true);
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hmbMenu);

        transition.setRate(
                -1);
        hmbMenu.addEventHandler(MouseEvent.MOUSE_PRESSED,
                (e) -> {
                    Aplicacion.getInstance().setEventoMenu(e);
                    transition.setRate(transition.getRate() * -1);
                    transition.play();

                    if (jdrwMenu.isShown()) {
                        jdrwMenu.close();
                        panMenu.setPrefWidth(0);
                        jdrwMenu.setPrefWidth(0);
                    } else {
                        panMenu.setPrefWidth(200);
                        jdrwMenu.setPrefWidth(200);
                        jdrwMenu.setDefaultDrawerSize(200);
                        jdrwMenu.open();
                    }
                }
        );
        Aplicacion.getInstance().setHamburgerMenu(hmbMenu);

        Aplicacion.getInstance().getDetalleAgenda().addListener((ListChangeListener.Change<? extends BikDetalleAgenda> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {                    
                    tbvEventos.refresh();
                }
                if (c.wasPermutated()) {
                    tbvEventos.refresh();
                } else if (c.wasUpdated()) {
                    tbvEventos.refresh();
                } else {
                    tbvEventos.refresh();
                }
            }
        });
    }

    private void loadImage() {
        root.getStyleClass().clear();
        BikCentro centro = Aplicacion.getInstance().getDefaultCentro();
        if (centro != null && centro.getCenLogo() != null && !centro.getCenLogo().isEmpty()) {
            root.getStyleClass().add("-fx-background-image: url('" + centro.getCenLogo() + "'); -fx-background-repeat: stretch; -fx-background-size: stretch; -fx-background-position: center center;");
        } else {
            root.getStyleClass().add("-fx-background-image: url('../view/images/biketso.png'); -fx-background-repeat: stretch; -fx-background-size: stretch; -fx-background-position: center center;");

        }
    }

    private void bindEventos() {
        if (Aplicacion.getInstance().getDetalleAgenda() != null) {
            tbvEventos.setItems(Aplicacion.getInstance().getDetalleAgenda());
            tbvEventos.refresh();
        }
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("biaFechainicio"));
        tbcHora.setCellValueFactory(b -> new SimpleStringProperty(Formater.getInstance().formatHour.format(b.getValue().getDeaFechainicio())));
        tbcEvento.setCellValueFactory(new PropertyValueFactory<>("deaTitulo"));
    }

    private void cargarSeguridad() {
        SeguridadDao seguridadDao = new SeguridadDao();

        Aplicacion.getInstance().setModulosUsuario(seguridadDao.getModulosUsuario(Aplicacion.getInstance().getRolesUsuario()));
        Aplicacion.getInstance().setAccesosUsuario(seguridadDao.getAccesosUsuario(Aplicacion.getInstance().getRolesUsuario()));

    }

    @Override
    public void initialize() {
        init();
        Aplicacion.getInstance().traerEventos();
        bindEventos();
        loadImage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    @FXML
    void irAgenda(ActionEvent event) {
        AppWindowController.getInstance().abrirVentanaEnPrincipal("bik_agenda", "Center");
    }

}
