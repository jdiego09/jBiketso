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
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jbiketso.model.dao.PersonaDao;
import jbiketso.model.dao.UsuarioDao;
import jbiketso.model.entities.BikPersona;
import jbiketso.model.entities.BikUsuario;
import jbiketso.utils.Aplicacion;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 * FXML Controller class
 *
 * @author jdiego
 */
public class BusquedaController extends Controller implements Initializable {

    BikPersona personaSeleccionada;
    private static HashMap<String, Object> filtros = new HashMap<>();

    @FXML
    private Label lblTitulo;
    @FXML
    private JFXButton jbtnCancelar;
    @FXML
    private Pane pnlCriterios;
    @FXML
    private JFXButton jbtnLimpiar;
    @FXML
    private BorderPane root;
    @FXML
    private Pane pnlResultados;
    @FXML
    private JFXButton jbtnFiltrar;
    @FXML
    private TableView tbvResultados;
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

    @Override
    public void initialize() {
        Platform.runLater(() -> {
            for (Node object : pnlCriterios.getChildren()) {
                if (object.isFocusTraversable()) {
                    object.requestFocus();
                    break;
                }
            }
        });
        resultado = null;
    }

    public Object getResultado() {
        return resultado;
    }

    @FXML
    private void regresar(ActionEvent event) {
        closeWindow();
    }

    public void busquedaPersonas() {
        lblTitulo.setText("Búsqueda de personas");
        ArrayList<JFXTextField> criterios = new ArrayList<>();

        JFXTextField jtxfCedula = new JFXTextField();
        JFXTextField jtxfNombre = new JFXTextField();
        JFXTextField jtxfPrimerApellido = new JFXTextField();
        JFXTextField jtxfSegundoApellido = new JFXTextField();
        jtxfCedula.getStyleClass().add("edittext");
        jtxfCedula.setId("cedula");
        jtxfCedula.setText("%");
        jtxfCedula.setPromptText("Cédula");
        jtxfCedula.setLabelFloat(true);
        jtxfCedula.prefWidth(210);
        jtxfCedula.prefHeight(35);
        jtxfCedula.setLayoutX(8);
        jtxfCedula.setLayoutY(20);
        criterios.add(jtxfCedula);

        jtxfNombre.getStyleClass().add("edittext");
        jtxfNombre.setId("nombre");
        jtxfNombre.setPromptText("Nombre");
        jtxfNombre.setText("%");
        jtxfNombre.setLabelFloat(true);
        jtxfNombre.prefWidth(210);
        jtxfNombre.prefHeight(35);
        jtxfNombre.setLayoutX(8);
        jtxfNombre.setLayoutY(80);
        criterios.add(jtxfNombre);

        jtxfPrimerApellido.getStyleClass().add("edittext");
        jtxfPrimerApellido.setId("apellido1");
        jtxfPrimerApellido.setPromptText("Primer apellido");
        jtxfPrimerApellido.setText("%");
        jtxfPrimerApellido.setLabelFloat(true);
        jtxfPrimerApellido.prefWidth(210);
        jtxfPrimerApellido.prefHeight(35);
        jtxfPrimerApellido.setLayoutX(8);
        jtxfPrimerApellido.setLayoutY(140);
        criterios.add(jtxfPrimerApellido);

        jtxfSegundoApellido.getStyleClass().add("edittext");
        jtxfSegundoApellido.setId("apellido2");
        jtxfSegundoApellido.setPromptText("Segundo apellido");
        jtxfSegundoApellido.setText("%");
        jtxfSegundoApellido.setLabelFloat(true);
        jtxfSegundoApellido.prefWidth(210);
        jtxfSegundoApellido.prefHeight(35);
        jtxfSegundoApellido.setLayoutX(8);
        jtxfSegundoApellido.setLayoutY(200);
        criterios.add(jtxfSegundoApellido);
        pnlCriterios.getChildren().clear();
        criterios.stream().forEach(pnlCriterios.getChildren()::add);

        TableColumn<BikPersona, String> tbcCedula = new TableColumn<>("Cédula");
        tbcCedula.setPrefWidth(100);
        tbcCedula.setCellValueFactory(cd -> cd.getValue().getPerCedulaProperty());
        TableColumn<BikPersona, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setPrefWidth(400);
        tbcNombre.setCellValueFactory(cd -> cd.getValue().getNombreCompletoProperty());
        tbvResultados.getColumns().clear();
        tbvResultados.getItems().clear();
        tbvResultados.getColumns().add(tbcCedula);
        tbvResultados.getColumns().add(tbcNombre);
        tbvResultados.refresh();

        tbvResultados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.resultado = newSelection;
            }
        });

        setBuscaPersonasListener(jtxfCedula);
        setBuscaPersonasListener(jtxfNombre);
        setBuscaPersonasListener(jtxfPrimerApellido);
        setBuscaPersonasListener(jtxfSegundoApellido);

        jbtnFiltrar.setOnAction((ActionEvent event) -> {
            filtros.clear();
            filtros.put(jtxfCedula.getId(), jtxfCedula.getText());
            filtros.put(jtxfNombre.getId(), jtxfNombre.getText());
            filtros.put(jtxfPrimerApellido.getId(), jtxfPrimerApellido.getText());
            filtros.put(jtxfSegundoApellido.getId(), jtxfSegundoApellido.getText());
            buscarPersonas();
        });
        jbtnLimpiar.setOnAction((ActionEvent event) -> {
            filtros.clear();
            jtxfCedula.setText("%");
            jtxfNombre.setText("%");
            jtxfPrimerApellido.setText("%");
            jtxfSegundoApellido.setText("%");
            buscarPersonas();
        });

    }

    private void buscarPersonas() {
        if (filtros.get("cedula") == null) {
            filtros.put("cedula", "");
        }
        if (filtros.get("nombre") == null) {
            filtros.put("nombre", "");
        }
        if (filtros.get("apellido1") == null) {
            filtros.put("apellido1", "");
        }
        if (filtros.get("apellido2") == null) {
            filtros.put("apellido2", "");
        }
        Resultado<ArrayList<BikPersona>> personas = PersonaDao.getInstance().getPersonasFiltro(filtros.get("cedula").toString(), filtros.get("nombre").toString(), filtros.get("apellido1").toString(), filtros.get("apellido2").toString());
        if (personas.getResultado().equals(TipoResultado.SUCCESS)) {
            tbvResultados.setItems(FXCollections.observableArrayList(personas.get()));
            tbvResultados.refresh();
        }
    }

    public void busquedaUsuarios() {
        lblTitulo.setText("Búsqueda de usuarios");
        ArrayList<JFXTextField> criterios = new ArrayList<>();

        JFXTextField jtxfCedula = new JFXTextField();
        JFXTextField jtxfNombre = new JFXTextField();
        JFXTextField jtxfPrimerApellido = new JFXTextField();
        JFXTextField jtxfSegundoApellido = new JFXTextField();
        jtxfCedula.getStyleClass().add("edittext");
        jtxfCedula.setId("cedula");
        jtxfCedula.setText("%");
        jtxfCedula.setPromptText("Cédula");
        jtxfCedula.setLabelFloat(true);
        jtxfCedula.prefWidth(210);
        jtxfCedula.prefHeight(35);
        jtxfCedula.setLayoutX(8);
        jtxfCedula.setLayoutY(20);
        criterios.add(jtxfCedula);

        jtxfNombre.getStyleClass().add("edittext");
        jtxfNombre.setId("nombre");
        jtxfNombre.setPromptText("Nombre");
        jtxfNombre.setText("%");
        jtxfNombre.setLabelFloat(true);
        jtxfNombre.prefWidth(210);
        jtxfNombre.prefHeight(35);
        jtxfNombre.setLayoutX(8);
        jtxfNombre.setLayoutY(80);
        criterios.add(jtxfNombre);

        jtxfPrimerApellido.getStyleClass().add("edittext");
        jtxfPrimerApellido.setId("apellido1");
        jtxfPrimerApellido.setPromptText("Primer apellido");
        jtxfPrimerApellido.setText("%");
        jtxfPrimerApellido.setLabelFloat(true);
        jtxfPrimerApellido.prefWidth(210);
        jtxfPrimerApellido.prefHeight(35);
        jtxfPrimerApellido.setLayoutX(8);
        jtxfPrimerApellido.setLayoutY(140);
        criterios.add(jtxfPrimerApellido);

        jtxfSegundoApellido.getStyleClass().add("edittext");
        jtxfSegundoApellido.setId("apellido2");
        jtxfSegundoApellido.setPromptText("Segundo apellido");
        jtxfSegundoApellido.setText("%");
        jtxfSegundoApellido.setLabelFloat(true);
        jtxfSegundoApellido.prefWidth(210);
        jtxfSegundoApellido.prefHeight(35);
        jtxfSegundoApellido.setLayoutX(8);
        jtxfSegundoApellido.setLayoutY(200);
        criterios.add(jtxfSegundoApellido);
        pnlCriterios.getChildren().clear();
        criterios.stream().forEach(pnlCriterios.getChildren()::add);

        TableColumn<BikPersona, String> tbcCedula = new TableColumn<>("Cédula");
        tbcCedula.setPrefWidth(100);
        tbcCedula.setCellValueFactory(cd -> cd.getValue().getPerCedulaProperty());
        TableColumn<BikPersona, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setPrefWidth(400);
        tbcNombre.setCellValueFactory(cd -> cd.getValue().getNombreCompletoProperty());
        tbvResultados.getColumns().clear();
        tbvResultados.getItems().clear();
        tbvResultados.getColumns().add(tbcCedula);
        tbvResultados.getColumns().add(tbcNombre);
        tbvResultados.refresh();

        tbvResultados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.resultado = newSelection;
            }
        });

        setBuscaUsuariosListener(jtxfCedula);
        setBuscaUsuariosListener(jtxfNombre);
        setBuscaUsuariosListener(jtxfPrimerApellido);
        setBuscaUsuariosListener(jtxfSegundoApellido);

        jbtnFiltrar.setOnAction((ActionEvent event) -> {
            filtros.clear();
            filtros.put(jtxfCedula.getId(), jtxfCedula.getText());
            filtros.put(jtxfNombre.getId(), jtxfNombre.getText());
            filtros.put(jtxfPrimerApellido.getId(), jtxfPrimerApellido.getText());
            filtros.put(jtxfSegundoApellido.getId(), jtxfSegundoApellido.getText());
            buscarUsuarios();
        });
        jbtnLimpiar.setOnAction((ActionEvent event) -> {
            filtros.clear();
            jtxfCedula.setText("%");
            jtxfNombre.setText("%");
            jtxfPrimerApellido.setText("%");
            jtxfSegundoApellido.setText("%");
            buscarUsuarios();
        });

    }

    private void buscarUsuarios() {
        if (filtros.get("cedula") == null) {
            filtros.put("cedula", "");
        }
        if (filtros.get("nombre") == null) {
            filtros.put("nombre", "");
        }
        if (filtros.get("apellido1") == null) {
            filtros.put("apellido1", "");
        }
        if (filtros.get("apellido2") == null) {
            filtros.put("apellido2", "");
        }
        Resultado<ArrayList<BikPersona>> usuarios = UsuarioDao.getInstance().getUsuarioFiltro(filtros.get("cedula").toString(), filtros.get("nombre").toString(), filtros.get("apellido1").toString(), filtros.get("apellido2").toString());
        if (usuarios.getResultado().equals(TipoResultado.SUCCESS)) {
            tbvResultados.setItems(FXCollections.observableArrayList(usuarios.get()));
            tbvResultados.refresh();
        }
    }
    
    private void setBuscaUsuariosListener(TextField textfield) {
        textfield.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                filtros.put(textfield.getId(), textfield.getText());
                buscarUsuarios();
            }
        });
    }
    
    private void setBuscaPersonasListener(TextField textfield) {
        textfield.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                filtros.put(textfield.getId(), textfield.getText());
                buscarPersonas();
            }
        });
    }

    @FXML
    void aceptarBusqueda(ActionEvent event) {
        Resultado<Object> resultado = new Resultado<>();
        resultado.set(tbvResultados.getSelectionModel().getSelectedItem());
        Aplicacion.getInstance().setResultadoBusqueda(resultado);
        closeWindow();
    }

    private void closeWindow() {

        jbtnAceptar.getScene().getWindow().hide();
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
