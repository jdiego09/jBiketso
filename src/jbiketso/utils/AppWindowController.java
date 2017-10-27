package jbiketso.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppWindowController {

    private static final String APP_FORMINI = "bik_principal";

    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    private static HashMap<String, Pane> roots = new HashMap<>();
    private static AppWindowController INSTANCE;

    private Stage mainStage;
    private BorderPane mainRoot;

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

    public Parent getViewRoot(String view) {
        return roots.get(view);
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

    public Pane getView(String ventana) {
        if (roots.get(ventana) == null) {
            cargarView(ventana);
        }
        return roots.get(ventana);
    }

    public void initApplication() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        if (cargarView(APP_FORMINI)) {
            Scene scene = new Scene(roots.get(APP_FORMINI));
            if (mainStage == null) {
                mainStage = new Stage();
                mainStage.initStyle(StageStyle.UNIFIED);
            }
            mainStage.setX(bounds.getMinX());
            mainStage.setY(bounds.getMinY());
            mainStage.setWidth(bounds.getWidth());
            mainStage.setHeight(bounds.getHeight());
            mainStage.setMaximized(true);
            mainStage.setResizable(true);
            mainStage.centerOnScreen();
            mainStage.setScene(scene);
            mainStage.setTitle("Bikétsö");
            mainStage.show();
        }
    }

    public void abrirVentana(String ventana, String titulo, boolean resize) {
        if (cargarView(ventana)) {
            Scene scene = new Scene(roots.get(ventana));
            if (mainStage == null) {
                mainStage = new Stage();
            }

            mainStage.initStyle(StageStyle.UNIFIED);
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
            mainStage.setScene(scene);
            mainStage.setTitle(titulo);
            mainStage.setResizable(resize);
            mainStage.show();
        }
    }

    public void abrirVentanaEnPrincipal(String ventana, String location) {
        if (cargarView(ventana)) {
            switch (location) {
                case "Center":
                    ((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).getChildren().clear();
                    ((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).getChildren().add(getViewRoot(ventana));
                    ((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).setAlignment(Pos.CENTER);
                    break;
                case "Top":
                    ((BorderPane) mainStage.getScene().getRoot()).setTop(getViewRoot(ventana));
                    break;
                case "Bottom":
                    ((BorderPane) mainStage.getScene().getRoot()).setBottom(getViewRoot(ventana));
                    break;
                case "Right":
                    ((BorderPane) mainStage.getScene().getRoot()).setRight(getViewRoot(ventana));
                    break;
                case "Left":
                    ((BorderPane) mainStage.getScene().getRoot()).setLeft(getViewRoot(ventana));
                    break;
                default:
                    ((BorderPane) mainStage.getScene().getRoot()).setCenter(getViewRoot(ventana));
            }
        }
    }

    public void loadHomeImage() {
        AnchorPane pane = new AnchorPane();
        ImageView imageView = new ImageView();
        Image image = new Image("file:/view/images/logo.png");

        imageView.fitWidthProperty().bind(pane.widthProperty());
        imageView.fitHeightProperty().bind(pane.heightProperty());
        imageView.setImage(image);
        ((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).getChildren().clear();
        ((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).getChildren().add(pane);
        pane.setPrefSize(((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).getPrefWidth(), ((VBox) ((BorderPane) mainStage.getScene().getRoot()).getCenter()).getPrefHeight());
    }

    public void goHome() {
        loadHomeImage();
    }

    public void cerrarVentana() {
        mainStage.setScene(null);
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
