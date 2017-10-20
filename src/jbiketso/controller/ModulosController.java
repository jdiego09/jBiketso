package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.xml.bind.annotation.XmlTransient;
import jbiketso.model.entities.BikModulos;

public class ModulosController implements Initializable {

   @FXML
   private JFXButton jbtnSalir;

   @FXML
   private JFXButton jbtnGuardar;

   @FXML
   private JFXTextField jtxfCodigoModulo;

   @FXML
   private JFXTextField jtxfDescripcionModulo;

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

   @FXML
   private TableColumn<?, ?> tbcUsuarioIngresa;

   @FXML
   private TableColumn<?, ?> tbcFechaIngresa;

   @FXML
   private TableColumn<?, ?> tbcUsuarioModifica;

   @FXML
   private TableColumn<?, ?> tbcFechaModifica;

   @XmlTransient
   public ObservableList<BikModulos> modulos;

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   private void bindModulo() {
      if (modulos != null) {
         tbvModulos.setItems(modulos);
         tbvModulos.refresh();
      }
   }

}
