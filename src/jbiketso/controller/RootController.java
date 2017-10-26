/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

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
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import jbiketso.utils.Parametros;

/**
 *
 * @author esanchez
 */
public class RootController implements Initializable {

    @FXML
    BorderPane root;
    @FXML
    private Label lblUsuario, lblFechaHora;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUsuario.setText("Usuario: " + Parametros.getInstance().getParametro("Usuario"));

        lblFechaHora.setText("Fecha: " + sdf.format(new Date()));
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> lblFechaHora.setText("Fecha: " + sdf.format(new Date()))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
