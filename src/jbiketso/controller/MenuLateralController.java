/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author esanchez
 */
public class MenuLateralController extends Controller implements Initializable {

    @FXML
    AnchorPane root;

    @FXML
    Button btnDesplegar;

    @FXML
    ToolBar tlbModulos;

    @FXML
    ToggleButton tgbInicio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<MenuDto> modulos = (List<MenuDto>) AppContext.getInstance().get("Modulos");
        ToggleGroup grupo = new ToggleGroup();
        tgbInicio.setToggleGroup(grupo);
        grupo.selectToggle(tgbInicio);
        if (modulos != null) {
            for (MenuDto modulo : modulos) {
                ToggleButton tgb = new ToggleButton(modulo.getEtiqueta());
                tgb.setToggleGroup(grupo);
                tgb.setPrefSize(155, 44);
                tgb.setMaxSize(155, 44);
                tgb.setMinSize(155, 44);
                tgb.setOnAction((ActionEvent event) -> {
                    if (!tgb.isSelected()) {
                        tgbInicio.fire();
                    } else {
                        EncabezadoController encabezado = (EncabezadoController) FlowController.getInstance().getController("EncabezadoView");
                        encabezado.setMenu(modulo.getCodigo());
                    }
                });
                
                if(!modulo.getAsignado()) {
                    tgb.setDisable(true);
                }

                tlbModulos.getItems().add(tgb);
            }
        }
    }

    @FXML
    private void irInicio(ActionEvent event) {
        if(!tgbInicio.isSelected())
            tgbInicio.fire();
        EncabezadoController encabezado = (EncabezadoController) FlowController.getInstance().getController("EncabezadoView");
        encabezado.setMenu("Inicio");
    }

    public void desplegarMenu() {
        if (root.getTranslateX() >= 0.0) {
            TranslateTransition translateTransition
                    = new TranslateTransition(Duration.millis(500), root);
            translateTransition.setFromX(0);
            translateTransition.setToX(-168);
            btnDesplegar.setText(">");

            Timeline timeline = new Timeline();
            Duration time = new Duration(500);
            KeyValue keyValue = new KeyValue(root.prefWidthProperty(), 0);
            KeyFrame keyFrame = new KeyFrame(time, keyValue);
            timeline.getKeyFrames().add(keyFrame);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(
                    timeline,
                    translateTransition
            );
            parallelTransition.play();

        } else {
            TranslateTransition translateTransition
                    = new TranslateTransition(Duration.millis(500), root);
            translateTransition.setFromX(-168);
            translateTransition.setToX(0);
            btnDesplegar.setText("<");

            Timeline timeline = new Timeline();
            Duration time = new Duration(500);
            KeyValue keyValue = new KeyValue(root.prefWidthProperty(), 193);
            KeyFrame keyFrame = new KeyFrame(time, keyValue);
            timeline.getKeyFrames().add(keyFrame);

            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(
                    timeline,
                    translateTransition
            );
            parallelTransition.play();
        }
    }

    @Override
    public void initialize() {
        
    }
}
