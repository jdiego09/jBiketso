package jbiketso.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jbiketso.model.dao.SeguridadDao;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Parametros;

public class PrincipalController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblFecha;

    @FXML
    private JFXHamburger hmbMenu;

    @FXML
    private VBox vbxContainer;

    @FXML
    private JFXDrawer drwMenu;
    private Pane panMenu;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppWindowController.getInstance().setMainRoot(root);
        lblUsuario.setText("Usuario: " + Parametros.getInstance().getParametro("Usuario"));

        lblFecha.setText("Fecha: " + sdf.format(new Date()));
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> lblFecha.setText("Fecha: " + sdf.format(new Date()))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        cargarSeguridad();

        panMenu = (Pane) AppWindowController.getInstance().getView("bik_principal_menu");

        drwMenu.setSidePane(panMenu);
        drwMenu.setDefaultDrawerSize(150);
        drwMenu.setOverLayVisible(false);
        drwMenu.setResizableOnDrag(true);

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hmbMenu);

        transition.setRate(
                -1);
        hmbMenu.addEventHandler(MouseEvent.MOUSE_PRESSED,
                (e) -> {
                    transition.setRate(transition.getRate() * -1);
                    transition.play();

                    if (drwMenu.isShown()) {
                        drwMenu.close();
                        panMenu.setPrefWidth(0);
                        drwMenu.setPrefWidth(0);
                    } else {
                        panMenu.setPrefWidth(200);
                        drwMenu.setPrefWidth(200);
                        drwMenu.open();
                    }
                }
        );
    }

    private void cargarSeguridad() {
        SeguridadDao seguridadDao = new SeguridadDao();

        Aplicacion.getInstance().setModulosUsuario(seguridadDao.getModulosUsuario(Aplicacion.getInstance().getRolesUsuario()));
        Aplicacion.getInstance().setAccesosUsuario(seguridadDao.getAccesosUsuario(Aplicacion.getInstance().getRolesUsuario()));

    }

}
