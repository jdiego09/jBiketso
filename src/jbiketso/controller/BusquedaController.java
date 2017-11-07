/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jbiketso.model.entities.BikPersona;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BusquedaController implements Initializable {

   ArrayList<BikPersona> personasList = new ArrayList<>();

   @FXML
   private JFXButton jbtnCancelar;

   @FXML
   private VBox vbxCriterios;

   @FXML
   private JFXButton jbtnLimpiar;

   @FXML
   private BorderPane root;

   @FXML
   private Pane pnlResultados;

   @FXML
   private JFXButton jbtnFiltrar;

   @FXML
   private TableView<?> tbvResultados;

   @FXML
   private JFXButton jbtnAceptar;

   private Object resultado;
   private EventHandler<KeyEvent> keyEnter;
   private Boolean pressEnter = false;

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
      keyEnter = (KeyEvent event) -> {
         if (event.getCode() == KeyCode.ENTER) {
            pressEnter = true;
            jbtnFiltrar.fire();
            pressEnter = false;

            //sendTabEvent(event);
         } else if (event.getCode() == KeyCode.ESCAPE) {
            //cerrar();
         }
      };
      resultado = null;
   }
/*
   @Override
   public void initialize() {
      Platform.runLater(() -> {
         for (Node object : vbxCriterios.getChildren()) {
            if (object.isFocusTraversable()) {
               object.requestFocus();
               break;
            }
         }
      });
      resultado = null;
   }*/
   
   public Object getResultado() {
        return resultado;
    }

   @FXML
   private void regresar(ActionEvent event) {
      resultado = tbvResultados.getSelectionModel().getSelectedItem();
      jbtnFiltrar.getScene().setRoot(new Pane());
   }

   private void setBusquedaPersonas() {
      ArrayList<JFXTextField> criterios = new ArrayList<>();

      JFXTextField jtxf = new JFXTextField();
      jtxf.getStyleClass().add("edittext");
      jtxf.setId("jtxfCedula");
      jtxf.setPromptText("Cédula");
      jtxf.setLabelFloat(true);
      jtxf.prefWidth(200);
      criterios.add(jtxf);
      jtxf = new JFXTextField();
      jtxf.getStyleClass().add("edittext");
      jtxf.setId("jtxfNombre");
      jtxf.setPromptText("Nombre");
      jtxf.setLabelFloat(true);
      jtxf.prefWidth(200);
      criterios.add(jtxf);
      jtxf = new JFXTextField();
      jtxf.getStyleClass().add("edittext");
      jtxf.setId("jtxfApellido1");
      jtxf.setPromptText("Primer apellido");
      jtxf.setLabelFloat(true);
      jtxf.prefWidth(200);
      criterios.add(jtxf);
      jtxf = new JFXTextField();
      jtxf.getStyleClass().add("edittext");
      jtxf.setId("jtxfApellido2");
      jtxf.setPromptText("Segundo apellido");
      jtxf.setLabelFloat(true);
      jtxf.prefWidth(200);
      criterios.add(jtxf);
      vbxCriterios.getChildren().clear();
      criterios.stream().forEach(vbxCriterios.getChildren()::add);

      JFXButton jbtn = new JFXButton();
      jbtn.setId("jbtnFiltrar");
      jbtn.getStyleClass().add("actionbuttonaceptar");
      jbtn.setText("FILTRAR");
      vbxCriterios.getChildren().add(jbtn);

   }
   /*
   public void busquedaSorteo() {
        try {

            lblTitulo.setText("Búsqueda de Sorteos");
            Label lblCompania = new Label("Compañia");
            ComboBox cmbCompania = new ComboBox();
            cmbCompania.setOnKeyPressed(keyEnter);
            cmbCompania.setItems(cargarCompanias());
            cmbCompania.getSelectionModel().selectFirst();
            Label lblCodigo = new Label("Código");
            TextField txfCodigo = new TextField();
            txfCodigo.setOnKeyPressed(keyEnter);
            Label lblDescripcion = new Label("Descripción");
            TextField txfDescripcion = new TextField();
            txfDescripcion.setOnKeyPressed(keyEnter);

            vbxParametros.getChildren().clear();
            vbxParametros.getChildren().add(lblCompania);
            vbxParametros.getChildren().add(cmbCompania);
            vbxParametros.getChildren().add(lblCodigo);
            vbxParametros.getChildren().add(txfCodigo);
            vbxParametros.getChildren().add(lblDescripcion);
            vbxParametros.getChildren().add(txfDescripcion);

            tbvBusqueda.getColumns().clear();
            tbvBusqueda.getItems().clear();
            TableColumn<SorteoDto, CompaniaDto> tbcCompania = new TableColumn<>("Compania");
            tbcCompania.setPrefWidth(100);
            tbcCompania.setCellValueFactory(cd -> cd.getValue().compania);
            TableColumn<SorteoDto, String> tbcCodigo = new TableColumn<>("Código");
            tbcCodigo.setPrefWidth(100);
            tbcCodigo.setCellValueFactory(cd -> cd.getValue().codigo);
            TableColumn<SorteoDto, String> tbcDescripcion = new TableColumn<>("Descripción");
            tbcDescripcion.setPrefWidth(300);
            tbcDescripcion.setCellValueFactory(cd -> cd.getValue().descripcion);

            tbvBusqueda.getColumns().add(tbcCompania);
            tbvBusqueda.getColumns().add(tbcCodigo);
            tbvBusqueda.getColumns().add(tbcDescripcion);
            tbvBusqueda.refresh();

            btnFiltrar.setOnAction((ActionEvent event) -> {
                tbvBusqueda.getItems().clear();
                SorteoService service = (SorteoService) AppContext.getInstance().get("SorteoService");
                String compania = "%%";
                if (cmbCompania.getSelectionModel().getSelectedItem() != null) {
                    compania = ((CompaniaDto) cmbCompania.getSelectionModel().getSelectedItem()).getCodigo();
                }
                String codigo = "%" + txfCodigo.getText() + "%";
                String descripcion = "%" + txfDescripcion.getText() + "%";

                Respuesta respuesta = service.getSorteos(compania, codigo, descripcion);

                if (respuesta.getEstado()) {
                    ObservableList<SorteoDto> sorteos = FXCollections.observableList((List<SorteoDto>) respuesta.getResultado("Sorteos"));
                    tbvBusqueda.setItems(sorteos);
                    tbvBusqueda.refresh();
                } else {
                    Mensaje.getInstance().showModal(Alert.AlertType.ERROR, "Consultar sorteos", getStage(), respuesta.getMensaje());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BusquedaController.class.getName()).log(Level.SEVERE, "Error consultando los sorteos.", ex);
            Mensaje.getInstance().showModal(Alert.AlertType.ERROR, "Consultar sorteos", getStage(), "Ocurrió un error consultado los sorteos.");
        }
    }*/

}
