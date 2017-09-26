
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class PrincipalMenuController implements Initializable {
    
    @FXML
    private VBox vbxMenu;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        for (int i = 0;i<10;i++){
            JFXButton btn = new JFXButton();            
            btn.setText("Botón " + i);      
            btn.setMaxWidth(Integer.MAX_VALUE);
            btn.getStyleClass().add("buttonDrawer");;
            vbxMenu.getChildren().add(btn);
        }
    }    
    
}
