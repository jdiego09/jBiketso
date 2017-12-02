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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import jbiketso.model.dao.SeguridadDao;
import jbiketso.model.entities.BikCentro;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Parametros;

public class PrincipalController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
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
        loadImage();
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
        Aplicacion.getInstance().setMenuPrincipal(hmbMenu);               
    }

    private void loadImage() {
        BikCentro centro = Aplicacion.getInstance().getDefaultCentro();
        if (centro != null && centro.getCenLogo() != null && !centro.getCenLogo().isEmpty()) {
            imgLogo.setImage(null);
            try {
                Image img = new Image("file:" + centro.getCenLogo());
                if (!img.isError()) {
                    imgLogo.setImage(img);
                } else {
                    imgLogo.setImage(new Image(getClass().getResourceAsStream("/logo.png")));
                }
            } catch (Exception ex) {
                imgLogo.setImage(new Image(getClass().getResourceAsStream("/logo.png")));
            }
        } else {
            imgLogo.setImage(new Image(getClass().getResourceAsStream("/logo.png")));
        }
    }

    private void cargarSeguridad() {
        SeguridadDao seguridadDao = new SeguridadDao();

        Aplicacion.getInstance().setModulosUsuario(seguridadDao.getModulosUsuario(Aplicacion.getInstance().getRolesUsuario()));
        Aplicacion.getInstance().setAccesosUsuario(seguridadDao.getAccesosUsuario(Aplicacion.getInstance().getRolesUsuario()));

    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
}
