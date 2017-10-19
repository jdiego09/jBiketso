package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import jbiketso.model.entities.BikPermisoRol;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;

public class PrincipalMenuController implements Initializable {

    private final Integer ALTOBOTON = 35;
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
            btn.setText(m.getModDescripcion());
            btn.setId(m.getModCodigo());
            btn.setMaxWidth(Integer.MAX_VALUE);
            btn.setMinHeight(ALTOBOTON);
            btn.getStyleClass().add("buttonDrawer");
            btn.setOnAction(menuModulosHandler);
            vbxMenu.getChildren().add(btn);
        });

        JFXButton btn = new JFXButton();
        btn.setText("Salir");
        btn.setId("EXT");
        btn.setMaxWidth(Integer.MAX_VALUE);
        btn.setMinHeight(ALTOBOTON);
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
            btn.setId(String.valueOf(m.getProCodigomenu().getMenCodigo()));
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
        btn.getStyleClass().add("buttonDrawer");
        btn.setOnAction(menuPantallasHandler);
        vbxMenu.getChildren().add(btn);
        
    }

    final EventHandler<ActionEvent> menuPantallasHandler = (final ActionEvent event) -> {
        Object source = event.getSource();
        String pantalla = null;
        if (source instanceof JFXButton) {
            pantalla = ((JFXButton) source).getId();
        }
        if (pantalla.equalsIgnoreCase("BCK")) {
            setMenuModulos();
        } else {
            accesaPantalla(pantalla);
        }
        event.consume();
    };

    private void accesaPantalla(String pantalla) {
        AppWindowController.getInstance().abrirVentana(pantalla, "Bikétsö - Principal", true);
    }
}
