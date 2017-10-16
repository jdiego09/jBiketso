package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.AppWindowController;

public class PrincipalMenuController implements Initializable {

    @FXML
    private VBox vbxMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        setOpcionesMenu();
    }

    private void setOpcionesMenu() {
        vbxMenu.getChildren().clear();

        Aplicacion.getInstance().getModulosUsuario().stream().forEach(m -> {
            JFXButton btn = new JFXButton();
            btn.setText(m.getDescripcionModulo());
            btn.setId(m.getCodigoModulo());
            btn.setMaxWidth(Integer.MAX_VALUE);
            btn.getStyleClass().add("buttonDrawer");
            btn.setOnAction(menuHandler);
            vbxMenu.getChildren().add(btn);
        });

        JFXButton btn = new JFXButton();
        btn.setText("Salir");
        btn.setId("EXT");
        btn.setMaxWidth(Integer.MAX_VALUE);
        btn.getStyleClass().add("buttonDrawer");
        btn.setOnAction(menuHandler);
        vbxMenu.getChildren().add(btn);
    }

    final EventHandler<ActionEvent> menuHandler = (final ActionEvent event) -> {
        Object source = event.getSource();
        String id = null;
        if (source instanceof JFXButton) {
            id = ((JFXButton) source).getId();
        }
        if (id.equalsIgnoreCase("EXT")){
            if (AppWindowController.getInstance().mensajeConfimacion("Salir", "¿Desea salir del sistema?")){
                AppWindowController.getInstance().cerrarAplicacion();
            }
        } else {
            accesaModulo(id);
        }
        event.consume();
    };
    
    private void accesaModulo(String modulo){
        
    }

}
