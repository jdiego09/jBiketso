package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.dao.ModuloDao;
import jbiketso.model.entities.BikModulos;
import jbiketso.utils.AppWindowController;

public class ModulosController implements Initializable {

   @FXML
   private AnchorPane acpRoot;

   @FXML
   private JFXButton jbtnSalir, jbtnGuardar, jbtnEliminar;

   @FXML
   private JFXTextField jtxfCodigoModulo, jtxfDescripcionModulo;

   @FXML
   private JFXComboBox<?> jcmbEstadoModulo;

   @FXML
   private TableView<BikModulos> tbvModulos;

   @FXML
   private TableColumn<BikModulos, String> tbcCodigoModulo;

   @FXML
   private TableColumn<BikModulos, String> tbcDescipcionModulo;

   @FXML
   private TableColumn<BikModulos, String> tbcEstadoModulo;

   @XmlTransient
   public ObservableList<BikModulos> modulos = FXCollections
           .observableArrayList();

   ModuloDao modulo;
   BikModulos moduloSeleccionado;

   @Override
   public void initialize(URL location, ResourceBundle resources) {

      nuevoModulo();
      cargarModulos();
      bindListaModulos();
      addListenerTable(tbvModulos);

   }

   private void bindModulo() {
      jtxfCodigoModulo.textProperty().bindBidirectional(modulo.codigo);
      jtxfDescripcionModulo.textProperty().bindBidirectional(modulo.descripcion);
   }

   private void unbindModulo() {
      jtxfCodigoModulo.textProperty().unbindBidirectional(modulo.codigo);
      jtxfDescripcionModulo.textProperty().unbindBidirectional(modulo.descripcion);
   }

   private void addListenerTable(TableView table) {
      table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
         if (newSelection != null) {
            moduloSeleccionado = (BikModulos) newSelection;
            if (this.modulo == null) {
               this.modulo = new ModuloDao(moduloSeleccionado.getModCodigo(), moduloSeleccionado.getModDescripcion(), moduloSeleccionado.getModEstado());
            } else {
               this.modulo.codigo.set(moduloSeleccionado.getModCodigo());
               this.modulo.descripcion.set(moduloSeleccionado.getModDescripcion());
            }
            bindModulo();
         }
      });
   }

   private void cargarModulos() {
      modulos = FXCollections.observableArrayList(this.modulo.findByEstado("A"));
   }

   private void nuevoModulo() {
      this.modulo = new ModuloDao();
   }

   private void bindListaModulos() {
      if (modulos != null) {
         tbvModulos.setItems(modulos);
         tbvModulos.refresh();
      }
      tbcCodigoModulo.setCellValueFactory(new PropertyValueFactory<>("modCodigo"));
      tbcDescipcionModulo.setCellValueFactory(new PropertyValueFactory<>("modDescripcion"));
      tbcEstadoModulo.setCellValueFactory(new PropertyValueFactory<>("descripcionEstado"));
   }

   private void agregarModuloALista(BikModulos modulo) {
      if (!this.modulos.contains(modulo)) {
         this.modulos.add(modulo);
      } else {
         this.modulos.set(this.modulos.indexOf(modulo), modulo);
      }
      tbvModulos.refresh();
   }

   @FXML
   void guardarModulo(ActionEvent event) {
      this.modulo = new ModuloDao(jtxfCodigoModulo.getText(), jtxfDescripcionModulo.getText(), "A");
      BikModulos nuevo = this.modulo.save();
      agregarModuloALista(nuevo);
      unbindModulo();
   }

   @FXML
   void regresar(ActionEvent event) {
      AppWindowController.getInstance().goHome();
   }

   @FXML
   void eliminarModulo(ActionEvent event) {
      this.modulo.delete();
      if (this.modulos.contains(moduloSeleccionado)) {
         this.modulos.remove(moduloSeleccionado);
      }
      unbindModulo();
      nuevoModulo();
   }

}
