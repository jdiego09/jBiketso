
package jbiketso;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jbiketso.controller.LoginController;
import jbiketso.utils.AppWindowController;
import jbiketso.utils.Parametros;
//hola

public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parametros.getInstance().setParametro("pathViews", "/jbiketso/view/");
        AppWindowController.getInstance().abrirVentana("bik_login", "Bikétsö - Inicio", false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
