/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import jbiketso.model.entities.BikCentro;
import jbiketso.utils.Aplicacion;

/**
 * FXML Controller class
 *
 * @author jcalvo
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    private AnchorPane acpRoot;
    @FXML
    private ImageView imgLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    @Override
    public void initialize() {
        init();
    }

    @Override
    public void initialize(String funcion) {
        init();
    }

    private void init() {
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

}
