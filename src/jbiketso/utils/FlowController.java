/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import cr.co.coopeagri.utilfx.controller.Controller;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author esanchez
 */
public class FlowController {

    private static FlowController INSTANCE = null;
    /**
     * Stage principal de la aplicación
     */
    private static Stage mainStage;

    /**
     * Mapa para el manejo de los loaders de las vistas
     */
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();

    
    private static Application application;
    
    
    /**
     * Constructor privado para evitar que se instancien objetos
     */
    private FlowController() {
    }

    /**
     * Metodo para crear una instancia
     */
    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (FlowController.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
    }

    /**
     * Metodo para obtener una instancia
     *
     * @return Instacia del FlowController
     */
    public static FlowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * El método "clone" es sobreescrito por el siguiente que arroja una
     * excepción:
     *
     * @return Error en caso de intentar clonar
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Metodo para la inicialización del FlowController
     *
     * @param application Clase principal de la aplicación
     * @param stage Stage principal de la aplicación
     */
    public void InitializeFlow(Application application, Stage stage) {
        getInstance();
        AppContext.getInstance().set("Application", application);
        FlowController.application = application;
        FlowController.mainStage = stage;
        try {
            FlowController.mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/cr/co/coopeagri/utilfx/view/RootView.fxml"))));
            FlowController.mainStage.show();
        } catch (Exception ex) {
            Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }
    }

    /**
     * Obtiene el loader de la vista ingresada
     *
     * @param name Nombre de la vista
     * @return Loader de la vista
     */
    private FXMLLoader getLoader(String name) {

        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(application.getClass().getResource("view/" + name + ".fxml"));
                        loader.load();
                        loaders.put(name, loader);
                    } catch (Exception ex) {
                        loader = null;
                        Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }
    
    /**
     * Carga la vista indicada en el panel ingresado
     * 
     * @param pane Panel en el que se cargara la vista
     * @param viewName Nombre de la vista
     */
    public void goViewInPanel(Pane pane, String viewName) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = FlowController.mainStage;
            controller.setStage(stage);
        }
        
        pane.getChildren().clear();
        pane.getChildren().add(loader.getRoot());
        
    }

    /**
     * Carga la vista indicada en el panel central de la aplicación
     *
     * @param viewName Nombre de la vista
     */
    public void goView(String viewName) {
        goView(viewName, "Center", null);
    }
    
    /**
     * Carga la vista indicada en el panel central de la aplicación
     *
     * @param viewName Nombre de la vista
     * @param accion Parámetro para indicar alguna acción adicional a realizarze
     * al cargar la vista
     */
    public void goView(String viewName, String accion) {
        goView(viewName, "Center", accion);
    }

    /**
     * Carga la vista indicada en el panel seleccionado del marco principal
     *
     * @param viewName Nombre de la vista
     * @param location Panel en el cual cargar la vista (Top, Bottom, Left,
     * Right, Center)
     * @param accion Parámetro para indicar alguna acción adicional a realizarze
     * al cargar la vista
     */
    public void goView(String viewName, String location, String accion) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = FlowController.mainStage;
            controller.setStage(stage);
        }
        switch (location) {
            case "Center":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(loader.getRoot());
                break;
            case "Top":
                ((BorderPane) stage.getScene().getRoot()).setTop(loader.getRoot());
                break;
            case "Bottom":
                ((BorderPane) stage.getScene().getRoot()).setBottom(loader.getRoot());
                break;
            case "Right":
                ((BorderPane) stage.getScene().getRoot()).setRight(loader.getRoot());
                break;
            case "Left":
                ((BorderPane) stage.getScene().getRoot()).setLeft(loader.getRoot());
                break;
            default:
                ((BorderPane) stage.getScene().getRoot()).setCenter(loader.getRoot());
        }
    }
    
    public void goViewX(String viewName, String accion) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = FlowController.mainStage;
            controller.setStage(stage);
        }

                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(loader.getRoot());
  
                ((BorderPane) stage.getScene().getRoot()).setTop(null);

                ((BorderPane) stage.getScene().getRoot()).setBottom(null);
 
                ((BorderPane) stage.getScene().getRoot()).setRight(null);

                ((BorderPane) stage.getScene().getRoot()).setLeft(null);

        
    }

    /**
     * Carga la vista indicada en el stage ingresado
     *
     * @param viewName Nombre de la vista
     * @param stage Stage a mostrar
     */
    public void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);

        stage.getScene().setRoot(loader.getRoot());
    }

    /**
     * Carga la vista indicada en una ventana nueva
     *
     * @param viewName Nombre de la vista
     */
    public void goViewInWindow(String viewName) {
        VBox panel = new VBox();
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        Stage stage = new Stage();
        stage.setOnHidden((WindowEvent event) -> {
            controller.setStage(null);
        });
        controller.setStage(stage);
        panel.getChildren().add(loader.getRoot());
        Scene scene = new Scene(panel);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Carga la vista indicada en una ventana nueva de forma modal
     *
     * @param viewName Nombre de la vista
     * @param parentStage Stage padre al que le pertenece la ventana modal
     */
    public void goViewInWindowModal(String viewName, Stage parentStage) {
        VBox panel = new VBox();
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        stage.getIcons().addAll(mainStage.getIcons());
        stage.setTitle(mainStage.getTitle());
        stage.setResizable(false);
        stage.setOnHidden((WindowEvent event) -> {
            controller.setStage(null);
        });
        controller.setStage(stage);
        panel.getChildren().add(loader.getRoot());
        Scene scene = new Scene(panel);
        scene.getStylesheets().addAll(parentStage.getScene().getRoot().getStylesheets());
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.centerOnScreen();
        stage.showAndWait();

    }

    /**
     * Obtiene el Controller de la vista indicada
     *
     * @param viewName Nombre de la vista
     * @return Controller de la vista
     */
    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }

    /**
     * Cierra el stage principal de la aplicación
     */
    public void salir() {
        FlowController.mainStage.close();
    }

}
