/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import cr.co.coopeagri.utilfx.clases.FlowController;
import cr.co.coopeagri.utilfx.controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author esanchez
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    AnchorPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }
    
    @FXML
    public void onActionSorteo(){
        FlowController.getInstance().goView("SorteoView", "");
    }
}
