package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import jbiketso.model.entities.BikPermisoRol;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;

public class PrincipalMenuController implements Initializable {

    private final Integer ALTOBOTON = 40;
    @FXML
    private ImageView imgMenu;

    @FXML
    private VBox vbxMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        setMenuModulos();
    }

    private void setMenuModulos() {
        vbxMenu.getChildren().clear();

        Aplicacion.getInstance().getModulosUsuario().stream().forEach(m -> {
            JFXButton btn = new JFXButton();
            btn.getStyleClass().add("buttonDrawer");
            btn.getStyleClass().add("buttonmenu" + m.getModCodigo().toLowerCase());
            btn.setText(m.getModDescripcion());
            btn.setId(m.getModCodigo());
            btn.setMaxWidth(Integer.MAX_VALUE);
            btn.setMinHeight(ALTOBOTON);
            btn.setOnAction(menuModulosHandler);
            vbxMenu.getChildren().add(btn);
        }
        );

        JFXButton btn = new JFXButton();
        btn.getStyleClass().add("buttonDrawer");
        btn.getStyleClass().add("buttonmenuext");
        btn.setText("Salir");
        btn.setId("EXT");
        btn.setMaxWidth(Integer.MAX_VALUE);
        btn.setMinHeight(ALTOBOTON);

        btn.getStyleClass().add("buttonmenu");
        btn.getStyleClass().add("buttonDrawer");
        btn.setOnAction(menuModulosHandler);

        vbxMenu.getChildren().add(btn);
    }

    final EventHandler<ActionEvent> menuModulosHandler = (final ActionEvent event) -> {
        Object source = event.getSource();
        String modulo = null;
        if (source instanceof JFXButton) {
            modulo = ((JFXButton) source).getId();
        }
        if (modulo.equalsIgnoreCase("EXT")) {
            if (AppWindowController.getInstance().mensajeConfimacion("Salir", "¿Desea salir del sistema?")) {
                AppWindowController.getInstance().cerrarAplicacion();
            }
        } else {
            accesaModulo(modulo);
        }
        event.consume();
    };

    private void accesaModulo(String modulo) {
        ArrayList<BikPermisoRol> menuPantallas = new ArrayList<>();
        Aplicacion.getInstance().getAccesosUsuario()
                .stream().filter(m -> m.getProCodigomenu().getMenModcodigo().getModCodigo().equalsIgnoreCase(modulo)).forEach(menuPantallas::add);
        setMenuPantallas(menuPantallas);
    }

    private void setMenuPantallas(ArrayList<BikPermisoRol> menuPantallas) {
        vbxMenu.getChildren().clear();

        menuPantallas.stream().forEach(m -> {
            JFXButton btn = new JFXButton();
            btn.setText(m.getProCodigomenu().getMenEtiqueta());
            if (m.getProCodigomenu().getMenFuncion() != null && !m.getProCodigomenu().getMenFuncion().isEmpty()) {
                btn.setId(m.getProCodigomenu().getMenPantalla() + "-" + m.getProCodigomenu().getMenFuncion());
            } else {
                btn.setId(m.getProCodigomenu().getMenPantalla());
            }
            btn.getStyleClass().add("buttonmenu");
            btn.setMaxWidth(Integer.MAX_VALUE);
            btn.setMinHeight(ALTOBOTON);
            btn.getStyleClass().add("buttonDrawer");
            btn.setOnAction(menuPantallasHandler);
            vbxMenu.getChildren().add(btn);
        });

        JFXButton btn = new JFXButton();
        btn.setText("Regresar");
        btn.setId("BCK");
        btn.setMaxWidth(Integer.MAX_VALUE);
        btn.setMinHeight(ALTOBOTON);
        btn.getStyleClass().add("buttonmenu");
        btn.getStyleClass().add("buttonDrawer");
        btn.setOnAction(menuPantallasHandler);
        vbxMenu.getChildren().add(btn);

    }

    final EventHandler<ActionEvent> menuPantallasHandler = (final ActionEvent event) -> {
        Object source = event.getSource();
        String pantalla = null;
        String funcion = null;
        if (source instanceof JFXButton) {
            if (((JFXButton) source).getId().indexOf("-") >= 0) {
                pantalla = ((JFXButton) source).getId().substring(0, ((JFXButton) source).getId().indexOf("-"));
                funcion = ((JFXButton) source).getId().substring(((JFXButton) source).getId().indexOf("-") + 1);
            } else {
                pantalla = ((JFXButton) source).getId();
            }
        } else {
            pantalla = "BCK";
        }
        if (pantalla.equalsIgnoreCase("BCK")) {
            AppWindowController.getInstance().goHome();
            setMenuModulos();
        } else {
            accesaPantalla(pantalla, funcion);
        }
        event.consume();
    };

    private void accesaPantalla(String pantalla, String funcion) {
        AppWindowController.getInstance().abrirVentanaEnPrincipal(pantalla, "Center", funcion);
    }
}
