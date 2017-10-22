package jbiketso.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppWindowController {

    private static String VENTANAPRINCIPAL = "bik_principal";
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    private static HashMap<String, Parent> roots = new HashMap<>();
    private static AppWindowController INSTANCE;

    private Stage mainStage;
    private BorderPane mainRoot;
    private FXMLLoader loader;

    private AppWindowController() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (AppWindowController.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new AppWindowController();
                }
            }
        }
    }

    public static AppWindowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public BorderPane getMainRoot() {
        return mainRoot;
    }

    public void setMainRoot(BorderPane mainRoot) {
        this.mainRoot = mainRoot;
    }
    
    

    public FXMLLoader getLoader(String view) {
        return loaders.get(view);
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public boolean cargarLogin(String ventana) {
        if (loaders.get(ventana) == null) {
            loaders.put(ventana, new FXMLLoader(getClass().getResource(Parametros.getInstance().getParametro("pathLogin") + ventana + ".fxml")));
        }
        try {
            if (roots.get(ventana) == null) {
                roots.put(ventana, loaders.get(ventana).load());
            }
        } catch (IOException ex) {
            Logger.getLogger(AppWindowController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean cargarView(String ventana) {
        if (loaders.get(ventana) == null) {
            loaders.put(ventana, new FXMLLoader(getClass().getResource(Parametros.getInstance().getParametro("pathViews") + ventana + ".fxml")));
        }
        try {
            if (roots.get(ventana) == null) {
                roots.put(ventana, loaders.get(ventana).load());
            }
        } catch (IOException ex) {
            Logger.getLogger(AppWindowController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public Parent getView(String ventana) {
        if (roots.get(ventana) == null) {
            cargarView(ventana);
        }
        return roots.get(ventana);
    }

    public void abrirVentana(String ventana, String titulo, boolean resize) {
        if (cargarView(ventana)) {
            Scene scene = new Scene(roots.get(ventana));
            if (mainStage == null) {
                mainStage = new Stage();
                mainStage.initStyle(StageStyle.UNDECORATED);
            }
            mainStage.setScene(null);
            mainStage.centerOnScreen();
            mainStage.setScene(scene);
            mainStage.setTitle(titulo);
            mainStage.setResizable(resize);
            mainStage.show();
        }
    }

    public void abrirVentanaEnPrincipal(String ventana) {
        if (cargarView(ventana)) {
            Stage stage = new Stage();
            stage.initOwner(mainStage.getOwner());
            stage.setScene(new Scene(roots.get(ventana)));
            stage.showAndWait();
        }
    }

    public void mensaje(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public boolean mensajeConfimacion(String titulo, String mensaje) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        final Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void cerrarAplicacion() {
        Platform.exit();
    }

}
