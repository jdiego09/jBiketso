package cr.co.coopeagri.opentouch.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.datafx.concurrent.ObservableExecutor;
import org.datafx.control.cell.ActionTableCell;
import org.datafx.control.cell.MoneyTableCell;
import org.datafx.controller.FXMLController;
import org.datafx.controller.context.ApplicationContext;
import org.datafx.controller.context.FXMLApplicationContext;
import org.datafx.controller.flow.FlowException;
import org.datafx.controller.flow.FlowHandler;
import org.datafx.controller.flow.action.ActionMethod;
import org.datafx.controller.flow.action.LinkAction;
import org.datafx.crud.CrudException;

import cr.co.coopeagri.opentouch.model.classes.DefaultDaoFactory;
import cr.co.coopeagri.opentouch.model.classes.FacturaModel;
import cr.co.coopeagri.opentouch.model.classes.Parametros;
import cr.co.coopeagri.opentouch.model.classes.Resultado;
import cr.co.coopeagri.opentouch.model.classes.TipoResultado;
import cr.co.coopeagri.opentouch.model.entitys.Articulo;
import cr.co.coopeagri.opentouch.model.entitys.BitacoraAutorizacion;
import cr.co.coopeagri.opentouch.model.entitys.Categoria;
import cr.co.coopeagri.opentouch.model.entitys.DespachoCombustible;
import cr.co.coopeagri.opentouch.model.entitys.DetFactura;
import cr.co.coopeagri.opentouch.model.entitys.DetFormaPago;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaControllerInterface;
import cr.co.coopeagri.opentouch.teclado.TecladoController;

@FXMLController(value = "/FacturaView.fxml")
public class FacturaController implements FacturaControllerInterface {

	@FXML
	private VBox vbxCategorias;

	@FXML
	private FlowPane flpArticulos;

	@FXML
	private TextField txtIngreso;

	@FXML
	TableView<DetFactura> tbvDetFactura;

	@FXML
	private TableColumn<DetFactura, BigDecimal> tbcCantidad;

	@FXML
	private TableColumn<DetFactura, String> tbcArticulo;

	@FXML
	private TableColumn<DetFactura, String> tbcDescArticulo;

	@FXML
	private TableColumn<DetFactura, BigDecimal> tbcPrecio;

	@FXML
	private TableColumn<DetFactura, BigDecimal> tbcTotal;

	@FXML
	private TableColumn<DetFactura, String> tbcBodega;

	@FXML
	private TableColumn<DetFactura, String> tbcCamBod;

	@FXML
	private TableColumn<DetFactura, String> tbcBtnEliLinea;

	/** #jcalvo. fecha:13/06/2017. Table view de despachos de combustible. */
	@FXML
	TableView<DespachoCombustible> tbvDetDespachos;

	@FXML
	private TableColumn<DespachoCombustible, String> tbcDispensador;

	@FXML
	private TableColumn<DespachoCombustible, String> tbcCombustible;

	@FXML
	private TableColumn<DespachoCombustible, BigDecimal> tbcPreCombustible;

	@FXML
	private TableColumn<DespachoCombustible, BigDecimal> tbcCantLitros;

	@FXML
	private TableColumn<DespachoCombustible, BigDecimal> tbcVentaCombustible;

	@FXML
	private TableColumn<DespachoCombustible, String> tbcSeleccionado;
	/** ----------------------------------------------------------------------- */

	@FXML
	private TextField txtCodCli;

	@FXML
	private Label lblDesCli, lblEstCre, lblDisCre, lblFecHor, lblVenCre,
			lblSubTotGen, lblCodEst, lblCodUsr;

	@FXMLApplicationContext
	private ApplicationContext app;

	@Inject
	private FacturaModel fm;

	@Inject
	private DefaultDaoFactory df;

	@Inject
	private VoxImporterClientService client;

	@FXML
	private TecladoController tcTeclado;

	@FXML
	private ToggleButton btnFacCon;

	@FXML
	private ToggleButton btnFacCre;

	@FXML
	private Button btnFinFac, btnBuscarCli, btnDatosCli, btnReimprimir,
			btnSalir, btnDatosCre, btnBuscar, btnGuardarFactura,
			btnReiniciarFactura, btnOtrasOpciones;

	@FXML
	@LinkAction(CierreController.class)
	private Button btnNext;

	@FXML
	private AnchorPane anpInicioFactura;

	final ToggleGroup tgTipFac = new ToggleGroup();
	final ToggleGroup tgCategorias = new ToggleGroup();

	private static BigDecimal numCero = new BigDecimal("0");
	private BigDecimal canDfp = numCero;

	@PostConstruct
	public void init() throws CrudException, FlowException {

		fm.setFacturaController(this);
		if (fm.getDf() == null) {
			Font.loadFont(Main.class.getResource("/digital-7.ttf")
					.toExternalForm(), 10);
			fm.setDf(df);

			/** #jcalvo. fecha:16/06/2017. */
			fm.cargarParametrosTouch(Parametros.codcia, Parametros.codinv);
			Parametros.turnoactual = fm.getParametrosTouch().getTurnoactual();
			/** ---------------------------------------------------------- */

			fm.cargarCategorias();
			if (Parametros.uso.equals("VC")) {
				fm.cargarVendedores();
			}

			app.register("parInv", fm.cargarParametrosInventario().get());
			app.register("parPos", fm.cargarParametrosPos().get());
			app.register("parVen", fm.cargarParametrosVentas().get());
		} else {
			cargarCategorias(fm.getCategoria());
		}

		fm.setPantalla("Factura");

		client.get().onNewSale(fm::crearLineaDespachoCombustible);
		client.get().onSaleUpdate(fm::crearLineaDespachoCombustible);
		client.get().onShiftChange(fm::cambiarTurno);
		Stream<String> caras = Stream
				.of((Parametros.carasatendidas.split(",")));
		caras.forEach(s -> client.get().subscribe(s.trim()));
		client.run();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
			ObservableExecutor
					.getDefaultInstance()
					.createProcessChain()
					.addRunnableInPlatformThread(
							() -> lblFecHor.setText(sdf.format(new Date()) + ""))
					.repeatInfinite(Duration.seconds(1));
		} catch (Exception ex) {
			System.out
					.print("\n**********************************************************\n");
			ex.printStackTrace();
			System.out
					.print("\n**********************************************************\n");
		}

		/***********************************/
		if (app.getRegisteredObject("folioEncargado") == null) {
			GestorVentanas gv = new GestorVentanas("Pin");
			gv.iniciarVentanaPin(fm, df, true, false);
			PinController controller = gv.mostrarVentanaPin();
			if (!controller.getPm().validacionCorrecta)
				((Stage) btnFinFac.getScene().getWindow()).close();
		}
		/******************************************/

		lblCodUsr.setText(((String) ApplicationContext.getInstance()
				.getRegisteredObject(("nomEncargado"))));

		fm.iniciarFactura();

		if (fm.getFactura().getCodTfa()
				.equals(fm.getParametrosVentas().getCodTfaCon())) {
			btnFacCon.setSelected(true);
			btnFacCre.setSelected(false);
		} else {
			btnFacCon.setSelected(false);
			btnFacCre.setSelected(true);
		}

		tbcDescArticulo.setCellValueFactory(e -> {
			return e.getValue().getArticulo().desCorProperty();
		});
		tbcDescArticulo.setText("Descripción");

		tbcBtnEliLinea
				.setCellFactory((e) -> {
					return ActionTableCell.createWithButton(
							(s) -> {
								if (!((DetFactura) s).getBloqueado()) {
									fm.getDetalle().remove(s);
								} else {
									if (fm.requiereAut("ANUDISP")) {
										GestorVentanas gv = new GestorVentanas(
												"Pin");
										gv.iniciarVentanaPin(fm, df, false,
												true);
										PinController controller = gv
												.mostrarVentanaPin();
										if (controller.getPm().validacionCorrecta) {

											if (((DetFactura) s).getArticulo()
													.getEsDisp().equals("S"))
												for (int i = 0; i < fm
														.getDispensados()
														.size(); i++)
													if (fm.getDispensados()
															.get(i)
															.getCodigoArticulo()
															.equals(((DetFactura) s)
																	.getArticulo()
																	.getId()
																	.getCodigo())
															&& fm.getDispensados()
																	.get(i)
																	.getCantidad()
																	.equals(((DetFactura) s)
																			.getCantidad())) {
														String codUsr = (String) ApplicationContext
																.getInstance()
																.getRegisteredObject(
																		"usuAutoriza");
														BitacoraAutorizacion baut = fm
																.registarAutorizacion(
																		"ANUDISP",
																		codUsr);

														String codigoMov = fm
																.getDispensados()
																.get(i)
																.getCodigoMovimiento();

														int indice = codigoMov
																.indexOf("-", 4);

														codigoMov = codigoMov
																.substring(indice + 1);

														baut.setCodMov(new BigDecimal(
																codigoMov));
														baut.setTipMov("D");

														if (fm.anularMovimientoDispensado(fm
																.getDispensados()
																.get(i))) {
															fm.registrarBitacoraAnulacionDispensado(baut);
															fm.getDetalle()
																	.remove(s);
														}
														break;
													}
										}
									} else {
										if (((DetFactura) s).getArticulo()
												.getEsDisp().equals("S"))
											for (int i = 0; i < fm
													.getDispensados().size(); i++)
												if (fm.getDispensados()
														.get(i)
														.getCodigoArticulo()
														.equals(((DetFactura) s)
																.getArticulo()
																.getId()
																.getCodigo())
														&& fm.getDispensados()
																.get(i)
																.getCantidad()
																.equals(((DetFactura) s)
																		.getCantidad())) {
													if (fm.anularMovimientoDispensado(fm
															.getDispensados()
															.get(i))) {
														fm.getDetalle().remove(
																s);
													}
													break;
												}
									}
								}
							}, "X");
				});

		tbcCantidad
				.setCellValueFactory(new PropertyValueFactory<DetFactura, BigDecimal>(
						"cantidad"));
		tbcCantidad
				.setCellFactory(e -> {
					MoneyTableCell<DetFactura, BigDecimal> mc = new MoneyTableCell<DetFactura, BigDecimal>();
					mc.setFormat(Parametros.formatCan);
					return mc;
				});

		tbcArticulo
				.setCellValueFactory(new PropertyValueFactory<DetFactura, String>(
						"codArt"));

		tbcArticulo
				.setCellFactory(new Callback<TableColumn<DetFactura, String>, TableCell<DetFactura, String>>() {
					@Override
					public TableCell<DetFactura, String> call(
							TableColumn<DetFactura, String> p) {

						return new TableCell<DetFactura, String>() {

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (!isEmpty()) {

									if (getTableRow().getItem() != null
											&& ((DetFactura) getTableRow()
													.getItem()).getArticulo()
													.getEsDisp().equals("S"))
										getTableRow().setId("row_dispensado");
									else
										getTableRow().setId("row_normal");

									if (!empty)
										setText(empty ? null : item);
									else {
										setText("");
										getTableRow().setId("row_normal");
									}
								} else {
									setText("");
									getTableRow().setId("row_normal");
								}
							}
						};
					}
				});

		tbcPrecio
				.setCellValueFactory(new PropertyValueFactory<DetFactura, BigDecimal>(
						"preFin"));
		tbcPrecio
				.setCellFactory(e -> {
					MoneyTableCell<DetFactura, BigDecimal> mc = new MoneyTableCell<DetFactura, BigDecimal>();
					mc.setFormat(Parametros.formatMon);
					return mc;
				});
		tbcTotal.setCellValueFactory(new PropertyValueFactory<DetFactura, BigDecimal>(
				"total"));
		tbcTotal.setCellFactory(e -> {
			MoneyTableCell<DetFactura, BigDecimal> mc = new MoneyTableCell<DetFactura, BigDecimal>();
			mc.setFormat(Parametros.formatMon);
			return mc;
		});
		tbcBodega
				.setCellValueFactory(new PropertyValueFactory<DetFactura, String>(
						"codBod"));

		tbcCamBod.setCellFactory((c) -> {
			return ActionTableCell.createWithButton(
					(s) -> {
						if (!((DetFactura) s).getBloqueado()) {
							fm.obtenerBodegas();
							GestorVentanas gv = new GestorVentanas(
									"CambiarBodega");
							gv.iniciarVentanaCambioBodega(fm.getDesBod(),
									fm.getCodBod(),
									((DetFactura) s).getCodBod());
							CambiarBodegaController controller = gv
									.mostrarVentanaCambioBodega();

							if (controller.codBodega != null) {
								Articulo artAct = ((DetFactura) s)
										.getArticulo();
								artAct.setCodBodFac(controller.codBodega);
								if (fm.cambiarBodega(artAct,
										((DetFactura) s).getCantidad())) {
									((DetFactura) s)
											.setCodBod(controller.codBodega);
								}
							}
						}
					}, "C.B.");
		});

		tbvDetFactura.setItems(fm.getDetalle());

		/*
		 * #jcalvo. fecha:13/06/2017. carga tabla de despachos de combustible y
		 * asigna el evento del click
		 */

		tbvDetDespachos.setItems(fm.getDespachoCombustible());
		tbcDispensador
				.setCellValueFactory(new PropertyValueFactory<DespachoCombustible, String>(
						"cara"));
		tbcCombustible
				.setCellValueFactory(new PropertyValueFactory<DespachoCombustible, String>(
						"desProducto"));
		tbcPreCombustible
				.setCellValueFactory(new PropertyValueFactory<DespachoCombustible, BigDecimal>(
						"precio"));
		tbcPreCombustible
				.setCellFactory(e -> {
					MoneyTableCell<DespachoCombustible, BigDecimal> mc = new MoneyTableCell<DespachoCombustible, BigDecimal>();
					mc.setFormat(Parametros.formatMon);
					return mc;
				});
		tbcCantLitros
				.setCellValueFactory(new PropertyValueFactory<DespachoCombustible, BigDecimal>(
						"volumen"));
		tbcCantLitros
				.setCellFactory(e -> {
					MoneyTableCell<DespachoCombustible, BigDecimal> mc = new MoneyTableCell<DespachoCombustible, BigDecimal>();
					mc.setFormat(Parametros.formatCan);
					return mc;
				});
		tbcVentaCombustible
				.setCellValueFactory(new PropertyValueFactory<DespachoCombustible, BigDecimal>(
						"venta"));
		tbcVentaCombustible
				.setCellFactory(e -> {
					MoneyTableCell<DespachoCombustible, BigDecimal> mc = new MoneyTableCell<DespachoCombustible, BigDecimal>();
					mc.setFormat(Parametros.formatMon);
					return mc;
				});

		tbcSeleccionado.setCellValueFactory(dp -> dp.getValue()
				.checkSeleccionadoProperty());

		tbcSeleccionado
				.setCellFactory(new Callback<TableColumn<DespachoCombustible, String>, TableCell<DespachoCombustible, String>>() {

					@Override
					public TableCell<DespachoCombustible, String> call(
							TableColumn<DespachoCombustible, String> p) {
						return new TableCell<DespachoCombustible, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								String idEstilo = "tbvDespachos_notSelected";
								if (!isEmpty()) {
									if (getTableRow().getItem() != null
											&& ((DespachoCombustible) getTableRow()
													.getItem()).getIndEstado()
													.get()
													.equalsIgnoreCase("A")) {
										idEstilo = "tbvDespachos_selected";
									}
								}
								;
								setText(item);
								getTableRow().setId(idEstilo);
							}
						};
					}
				});

		tbvDetDespachos.setRowFactory(tbv -> {
			TableRow<DespachoCombustible> fila = new TableRow<>();
			fila.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (fila.getItem() != null) {
						String marcado = "A";
						if (fila.getItem().getIndEstado().get()
								.equalsIgnoreCase(marcado)) {
							marcado = "P";
						}
						// valida que el dispensado seleccionado pertenezca al
						// turno actual.
						if (!fm.validarTurnoDespachosCombustible(fila.getItem())
								&& marcado.equalsIgnoreCase("A")) {
							Map<String, Object> confirmar = mostrarMensaje(
									"Dispensado de combustible en otro turno",
									"El dispensado de combustible seleccionado pertenece a un turno diferente al turno actual. ¿Desea continuar?",
									"S");
							if (!confirmar.get("resultado").equals("YES")) {
								marcado = "P";
							}
						}

						fila.getItem().setIndEstado(marcado);
					}
				};
			});
			return fila;
		});

		tbvDetDespachos.getSelectionModel().setSelectionMode(
				SelectionMode.SINGLE);
		// ---------------------------------------------------------------------
		tcTeclado.ingresoDatos.set("");
		txtIngreso.textProperty().bindBidirectional(tcTeclado.ingresoDatos);
		txtIngreso.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if (tcTeclado.getModo().equals("M")) {
					if (!newValue.isEmpty()
							&& !tcTeclado.getBtnNumeros().isSelected()) {
						try {
							new BigDecimal(newValue);
							tecladoEnter();
						} catch (Exception ex) {

						}
						txtIngreso.setText("");
					}
				}
			}
		});
		tcTeclado.setMontos(fm.obtenerTeclado("M"));
		tcTeclado.setCantidades(fm.obtenerTeclado("C"));

		txtIngreso.setOnMouseClicked((e) -> {
			txtIngreso.selectAll();
		});

		lblSubTotGen.textProperty().bindBidirectional(fm.totalProperty());

		tcTeclado.setIntro(this::tecladoEnter);

		tbvDetFactura.setRowFactory(t -> {
			TableRow<DetFactura> fila = new TableRow<>();
			fila.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (fila.getItem() != null) {
						if (fila.getItem().getDespacho() != null) {
							setDespachosCombustible();
						} else {
							tbvDetDespachos.setVisible(false);
							tcTeclado.setModo(fila.getItem().getMetIngreso());
						}
					}
				};
			});
			return fila;
		});

		btnFacCon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				btnFacCon.setSelected(true);
				btnFacCre.setSelected(false);
				fm.cambiarCodTfa("C");
			}

		});
		btnFacCon.setToggleGroup(tgTipFac);
		btnFacCon.setSelected(true);

		btnFacCre.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				btnFacCre.setSelected(true);
				btnFacCon.setSelected(false);
				fm.cambiarCodTfa("R");
			}

		});
		btnFacCre.setToggleGroup(tgTipFac);

		txtCodCli.setOnMouseClicked((e) -> {
			fm.ejecutarTecladoEnPantalla();
			txtCodCli.selectAll();
		});

		txtCodCli.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (txtCodCli.getText() != null
							&& txtCodCli.getText().length() > 7)
						txtCodCli.setText(txtCodCli.getText().substring(0, 7));
					else if (newValue != null)
						txtCodCli.textProperty().set(newValue.toUpperCase());
				});

		txtCodCli.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					while (txtCodCli.getText().length() < 7
							&& !txtCodCli.getText().trim().isEmpty()) {
						txtCodCli.setText(txtCodCli.getText(0, 1)
								+ "0"
								+ txtCodCli.getText(1, txtCodCli.getText()
										.length()));
					}
				}
			}
		});

		txtCodCli.textProperty().bindBidirectional(fm.codClienteProperty());
		lblDesCli.textProperty().bindBidirectional(fm.desClienteProperty());
		lblEstCre.textProperty().bindBidirectional(fm.estCreditoProperty());
		lblDisCre.textProperty().bindBidirectional(fm.disCreditoProperty());
		lblVenCre.textProperty().bindBidirectional(fm.venCreditoProperty());
		btnDatosCre.disableProperty().bindBidirectional(
				fm.datosCreditoProperty());

		txtIngreso.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tecladoEnter();
			}
		});

		txtIngreso.setOnKeyPressed((e) -> {
			txtIngreso.positionCaret(txtIngreso.getText().length());
		});

		txtIngreso.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String oldValue, String newValue) {
				if (!tcTeclado.getModo().equals("D")) {
					try {
						if (!txtIngreso.getText().trim().isEmpty()) {
							if (txtIngreso.getText().endsWith(".")) {
								if (txtIngreso.getText().indexOf(".") == txtIngreso
										.getText().lastIndexOf("."))
									txtIngreso.setText(txtIngreso.getText());
								else
									txtIngreso.setText(txtIngreso.getText()
											.substring(
													0,
													txtIngreso.getText()
															.length() - 1));
							} else
								Parametros.formatNum
										.applyPattern("###,###,###.###");
							txtIngreso.setText(Parametros.formatNum
									.format(new BigDecimal(txtIngreso.getText()
											.replaceAll(",", ""))));
							Parametros.formatNum.applyPattern("###");
						}
					} catch (Exception ex) {
						try {
							new BigDecimal(oldValue);
							txtIngreso.setText(oldValue);
						} catch (Exception ex1) {
							try {
								new Integer(newValue.substring(newValue
										.length() - 1));
								tcTeclado.ingresoDatos.set(newValue
										.substring(newValue.length() - 1));
								txtIngreso.setText(newValue.substring(newValue
										.length() - 1));
							} catch (Exception ex2) {
								tcTeclado.ingresoDatos.set("");
								txtIngreso.setText("");
							}
						}
					}
				}
			}
		});

		btnFinFac.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (fm.getFactura().getSubTot().compareTo(numCero) == 1) {
					// #jcalvo. fecha:28/06/2017. elimina los detalles en cero.
					fm.getDetalle().removeIf(d -> d.getTotal().equals(numCero));

					fm.calculaItoDtoMto(txtCodCli.getText(), fm.getFactura()
							.getPorDto1());

					if (fm.getDetalleFormaPago().size() < 1) {
						fm.setTotRec(Parametros.formatMon.format(numCero));
						fm.calcularTotalPagar(Parametros.formatMon
								.format(canDfp));
					} else {
						fm.getDetalleFormaPago().forEach((d) -> {
							canDfp = canDfp.add(d.getMonPag());
						});
						if (fm.getTotRec().equals(
								Parametros.formatMon.format(canDfp)))
							fm.calcularTotalPagar(Parametros.formatMon
									.format(canDfp));
						else
							fm.setTotRec(Parametros.formatMon.format(canDfp));
					}
					fm.mostrarMtoDescuento();

					if ((fm.getFactura().getCodTfa().equals(fm
							.getParametrosVentas().getCodTfaCon()))
							|| (fm.getFactura()
									.getCodTfa()
									.equals(fm.getParametrosVentas()
											.getCodTfaCre()) && (fm
									.getDisCredito() != null && !fm
									.getDisCredito().trim().isEmpty()))) {
						Boolean EliminarFP = true;

						if (fm.getFactura()
								.getCodTfa()
								.equals(fm.getParametrosVentas().getCodTfaCre())
								&& fm.getDetalleFormaPago().size() > 0) {
							Map<String, Object> confirmar = mostrarMensaje(
									"Eliminar formas de pago",
									"Existen formas de pago asociadas a la factura, estas seran eliminadas si desea proceder con la factura de credito. ¿Desea continuar?",
									"S");

							if (confirmar.get("resultado").equals("YES"))
								EliminarFP = fm.reversarFormasPago(
										fm.getDetalleFormaPago(),
										"Reversión al quitar formas de pago, para facturar de crédito.");
							else
								EliminarFP = false;
						}

						if (EliminarFP) {
							if (fm.getEstCredito() != null
									&& fm.getEstCredito()
											.replaceAll("Est: ", "")
											.equals("CERRADO")) {
								mostrarMensaje("Error al aplicar factura",
										"El crédito del cliente ["
												+ fm.getFactura().getCodCli()
												+ "] se encuentra cerradao.");
							} else {

								Date fecVen = null;
								try {
									if (fm.getVenCredito() != null) {
										fecVen = new SimpleDateFormat(
												"dd/MM/yyyy").parse(fm
												.getVenCredito().replaceAll(
														"Venc: ", ""));
									}
								} catch (Exception e2) {
									e2.printStackTrace();
								}
								Date sysdate = fm.obtenerFechaActual();

								if (fm.getFactura()
										.getCodTfa()
										.equals(fm.getParametrosVentas()
												.getCodTfaCre())
										&& fecVen != null
										&& !fecVen.after(sysdate)) {
									mostrarMensaje("Error al aplicar factura",
											"El crédito del cliente ["
													+ fm.getFactura()
															.getCodCli()
													+ "] ha expirado.");

								} else if (fm
										.getFactura()
										.getCodTfa()
										.equals(fm.getParametrosVentas()
												.getCodTfaCre())
										&& fm.getFactura()
												.getTotal()
												.compareTo(
														new BigDecimal(
																fm.getDisCredito()
																		.replaceAll(
																				"Disp: ",
																				"")
																		.replaceAll(
																				",",
																				""))) > 0) {
									mostrarMensaje("Error al aplicar factura",
											"El monto de la factura supera al monto disponible del crédito.");
								} else {
									fm.setExonerarFactura(false);
									fm.getImpuestoServicio().set(false);
									fm.mostrarMtoImpServ();
									try {

										/***********************************/
										// esanchez scre004224 06/04/2016
										Boolean continuar = true;

										if (Parametros.valpin.equals("S")) {
											GestorVentanas gv = new GestorVentanas(
													"Pin");
											gv.iniciarVentanaPin(fm, df, true,
													false);
											PinController controller = gv
													.mostrarVentanaPin();
											continuar = controller.getPm().validacionCorrecta;
										}
										if (continuar) {
											/******************************************/
											((FlowHandler) app
													.getRegisteredObject("flowHandler"))
													.handle("next");
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Validar datos de factura");
						alert.setHeaderText(null);
						alert.setContentText("El cliente ingresado no aplica para facturas de credito.");

						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Validar datos de factura");
					alert.setHeaderText(null);
					alert.setContentText("Debe definirse el total de la factura.");

					alert.showAndWait();
				}
			}

		});

		btnBuscarCli.setGraphic(new ImageView(new Image(getClass()
				.getResourceAsStream("/imagenes/consultar_24x24.png"))));

		btnBuscarCli.setOnAction((b) -> {
			GestorVentanas gv = new GestorVentanas("BuscarCliente");
			gv.iniciarVentanaBusquedaCliente(fm);
			BuscarClienteController controllerCli = gv
					.mostrarVentanaBusquedaCliente();
			lblDesCli.setText(null);
			lblEstCre.setText(null);
			lblDisCre.setText(null);
			lblVenCre.setText(null);
			txtCodCli.setText(controllerCli.codCli.get());
		});

		btnDatosCli.setGraphic(new ImageView(new Image(getClass()
				.getResourceAsStream("/imagenes/hoja24x24.png"))));

		btnDatosCli.setOnAction((e) -> {
			mostrarDatosAdicionalesCliente();
		});

		btnReimprimir.setOnAction((r) -> {
			GestorVentanas gv = new GestorVentanas("ReimprimirFactura");
			gv.iniciarVentanaReimprimirFactura(fm);
			gv.mostrarVentanaReimprimirFactura();
		});

		btnDatosCre.setGraphic(new ImageView(new Image(getClass()
				.getResourceAsStream("/imagenes/credito24x24.png"))));

		btnDatosCre.setOnAction((d) -> {
			GestorVentanas gv = new GestorVentanas("DatosCredito");
			gv.iniciarVentanaDatosCredito(fm);
			gv.mostrarVentanaDatosCredito();
		});

		btnSalir.setOnAction((s) -> {
			Resultado<String> res = fm.salirOpenTouch();
			if (res.getResultado().equals(TipoResultado.ERROR)) {
				mostrarMensaje("Error", res.getMensaje());

			} else {
				Map<String, Object> resMen = mostrarMensaje("Advertencia",
						res.getMensaje(), "M");
				if (((String) resMen.get("resultado")).equals("YES")) {
					fm.inactivarApertura();
					client.close();
					System.exit(0);
				}

			}
		});
		lblCodEst.setText("Estación: " + Parametros.codest);

		btnBuscar.setOnAction((b) -> {
			GestorVentanas gv = new GestorVentanas("BuscarArticulo");
			gv.iniciarVentanaBuscarArticulo(fm);
			BuscarArticuloController controller = gv
					.mostrarVentanaBuscarArticulo();
			if (controller.resArticulo.getResultado().equals(
					TipoResultado.SUCCESS)) {
				tcTeclado.setModo(controller.resArticulo.get().getMetIngDef());
				tbvDetFactura.getSelectionModel().selectFirst();
			} else if (controller.resArticulo.getResultado().equals(
					TipoResultado.ERROR)) {
				mostrarMensaje("Error al agregar línea en la factura",
						controller.resArticulo.getMensaje());
			}
		});

		btnGuardarFactura
				.setOnAction((f) -> {
					Map<String, Object> confirmar = null;

					if (fm.getFactura().getMovimientoIngreso() != null) {
						confirmar = mostrarMensaje(
								"Guardar factura",
								"El ingreso asociado a la factura se eliminara. ¿Desea continuar?",
								"S");
					}
					if ((confirmar == null)
							|| (confirmar.get("resultado") != null && confirmar
									.get("resultado").equals("YES"))) {
						if (fm.getFactura()
								.getCodTfa()
								.equals(fm.getParametrosVentas().getCodTfaCon())
								&& fm.getDetalleFormaPago().size() > 0) {
							confirmar = mostrarMensaje(
									"Eliminar formas de pago",
									"Existen formas de pago asociadas a la factura, estas seran eliminadas al guardar la factura. ¿Desea continuar?",
									"S");

							Boolean dfpAplicados = false;

							for (DetFormaPago dfp : fm.getDetalleFormaPago()) {
								if (dfp.getAplicada().equals("S"))
									dfpAplicados = true;
							}

							if (confirmar.get("resultado").equals("YES")) {
								if (dfpAplicados) {
									GestorVentanas gv = new GestorVentanas(
											"Pin");
									gv.iniciarVentanaPin(fm, df, false, true);
									PinController controller = gv
											.mostrarVentanaPin();
									Boolean val = controller.getPm().validacionCorrecta;

									if (val) {
										fm.reversarFormasPago(
												fm.getDetalleFormaPago(),
												"Reversión al guardar una factura. Usuario: "
														+ ((String) ApplicationContext
																.getInstance()
																.getRegisteredObject(
																		"usuActual"))
														+ new Date().toString());
									}
								}
							}
						}
					}
					// jcalvo. fecha:25/09/2017. guarda la factura

					Resultado<Boolean> res = fm.guardarFacturaTemporal(fm
							.getFactura());
					if (res.getResultado().equals(TipoResultado.ERROR)) {
						mostrarMensaje("Error al guardar factura",
								res.getMensaje());
					} else if (res.getResultado().equals(TipoResultado.WARNING)) {

					} else {
						fm.reiniciarFactura(false);
						try {
							init();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});

		btnReiniciarFactura.setOnAction((f) -> {
			reiniciarFactura();
		});

		btnOtrasOpciones.setOnAction((o) -> {
			GestorVentanas gv = new GestorVentanas("OtrasOpciones");
			gv.iniciarVentanaOtrasOpciones(fm, df);
			gv.mostrarVentanaOtrasOpciones();
		});
	}

	@Override
	public void cargarCategorias(List<Categoria> categorias) {
		vbxCategorias.getChildren().clear();
		if (categorias != null) {
			categorias.forEach(e -> {
				ToggleButton btn = new ToggleButton(e.getEtiqueta());
				btn.setMinSize(145, 70);
				btn.setMaxSize(145, 70);
				btn.setWrapText(true);
				btn.setToggleGroup(tgCategorias);
				btn.setId("boton-categoria");
				btn.setTextAlignment(TextAlignment.CENTER);

				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						final Categoria categoria = e;
						if (e.getIndcomb().equals("S")
								&& fm.getParametrosTouch().getModcomb()
										.equals("A")) {
							setDespachosCombustible();
						} else {
							tbvDetDespachos.setVisible(false);
							fm.cargarArticulos(categoria);
						}
					}
				});
				vbxCategorias.getChildren().add(btn);
			});
			if (categorias.get(0) != null) {
				if (categorias.get(0).getIndcomb().equals("S")
						&& fm.getParametrosTouch().getModcomb().equals("A")) {
					setDespachosCombustible();
				} else {
					tbvDetDespachos.setVisible(false);
					fm.cargarArticulos(categorias.get(0));
				}
				/**
				 * #jcalvo. fecha:20/06/2017. Marca por defecto el botón de la
				 * primer categoría [combustible]
				 **/
				ToggleButton b = (ToggleButton) vbxCategorias.getChildren()
						.get(0);
				b.setSelected(true);
			}
		}
	}

	/** #jcalvo. fecha:16/06/2017. */
	private void setDespachosCombustible() {
		if (fm.getDespachoCombustible().isEmpty()) {
			fm.cargarDespachosCombustible();
			tbvDetDespachos.setItems(fm.getDespachoCombustible());
		}
		tbvDetDespachos.refresh();
		flpArticulos.getChildren().clear();
		crearBotonPagoRapido();
		// crearBotonNuevoDespacho() -> solo para pruebas
		/*
		 * crearBotonNuevoDespacho();
		 */
		crearBotonTicketVox();
		tbvDetDespachos.setVisible(true);
		tbvDetDespachos.requestFocus();
	}

	public void crearBotonPagoRapido() {
		// Se crea un botón para aplicar como solo efectivo
		Button btnSe = new Button("APLICAR PAGO RÁPIDO");
		btnSe.setMinSize(145, 60);
		btnSe.setMaxSize(145, 60);
		btnSe.setWrapText(true);
		btnSe.setTextAlignment(TextAlignment.CENTER);
		btnSe.setId("boton-verde");

		btnSe.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				aplicarPagoRapido();
			}
		});
		flpArticulos.getChildren().add(btnSe);
	}

	public void crearBotonNuevoDespacho() {
		// Se crea un botón para aplicar como solo efectivo
		Button btnSe = new Button("Nuevo Despacho Combustible");
		btnSe.setMinSize(145, 60);
		btnSe.setMaxSize(145, 60);
		btnSe.setWrapText(true);
		btnSe.setTextAlignment(TextAlignment.CENTER);
		btnSe.setId("boton-verde");
		flpArticulos.getChildren().add(btnSe);
	}

	public void crearBotonTicketVox() {
		// Se crea un botón para aplicar como solo efectivo
		Button btnTickVox = new Button("TICKET VOX");
		btnTickVox.setMinSize(145, 60);
		btnTickVox.setMaxSize(145, 60);
		btnTickVox.setWrapText(true);
		btnTickVox.setTextAlignment(TextAlignment.CENTER);
		btnTickVox.setId("boton_dispensado");

		btnTickVox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fm.imprimirTicketVox();
			}
		});
		flpArticulos.getChildren().add(btnTickVox);
	}

	/***********************************************/

	@Override
	public void cargarArticulos(List<Articulo> articulo) {

		flpArticulos.getChildren().clear();
		if (articulo != null) {
			articulo.forEach(a -> {
				Button btnArt = new Button(a.getEtiqueta());
				btnArt.setMinSize(145, 70);
				btnArt.setMaxSize(145, 70);
				btnArt.setWrapText(true);
				btnArt.setTextAlignment(TextAlignment.CENTER);
				if (a.getEstilo() != null)
					btnArt.setId(a.getEstilo());
				else if (a.getEsDisp().equals("S"))
					btnArt.setId("boton_dispensado");

				btnArt.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						final Articulo articulo = a;

						DetFactura df = crearLinea(articulo);
						if (df != null) {
							fm.getDetalle().add(0, df);
							tbvDetFactura.getSelectionModel().selectFirst();
							tbvDetFactura.scrollTo(0);
						}
					}
				});
				flpArticulos.getChildren().add(btnArt);
			});
		}
	}

	public ApplicationContext getApp() {
		return app;
	}

	public void setApp(ApplicationContext app) {
		this.app = app;
	}

	private void tecladoEnter() {
		String mensError = null;

		if (fm.getDetalle().size() >= 1 || tcTeclado.getModo().equals("D")) {

			if (!txtIngreso.getText().isEmpty()
					&& new BigDecimal(txtIngreso.getText().replaceAll(",", ""))
							.compareTo(numCero) != 0) {

				BigDecimal canArt = numCero;
				BigDecimal total = numCero;
				Boolean errorFraccionar = false;

				int indice = tbvDetFactura.getSelectionModel()
						.getSelectedIndex();

				if (indice == -1 && !tcTeclado.getModo().equals("D")) {
					mostrarMensaje("Modificar detalle",
							"Es necesario seleccionar un detalle.");
				} else {

					String tipFac = fm.getFactura().getCodTfa();

					if (tcTeclado.getModo().equals("N")) {

						if (tbvDetFactura.getSelectionModel().getSelectedItem()
								.getArticulo().getUsaFraFac().equals("N")) {
							try {
								new Integer(txtIngreso.getText());
								canArt = new BigDecimal(txtIngreso.getText());
							} catch (Exception ex) {
								mostrarMensaje("Modificar detalle",
										"El artículo seleccionado no permite fraccionamiento.");
								errorFraccionar = true;
							}
						} else
							canArt = new BigDecimal(txtIngreso.getText()
									.replaceAll(",", ""));

					} else if (tcTeclado.getModo().equals("M")) {

						total = new BigDecimal(txtIngreso.getText().replaceAll(
								",", ""));
						canArt = new BigDecimal(txtIngreso.getText()
								.replaceAll(",", "")).divide(fm.getDetalle()
								.get(indice).getPrecio(), 3,
								RoundingMode.HALF_EVEN);

						if (tbvDetFactura.getSelectionModel().getSelectedItem()
								.getArticulo().getUsaFraFac().equals("N")) {
							try {
								new Integer(canArt.toString());
							} catch (Exception ex) {
								mostrarMensaje("Modificar detalle",
										"El artículo seleccionado no permite fraccionamiento.");
								errorFraccionar = true;
							}
						}
					}

					if (!errorFraccionar) {
						if (tcTeclado.getModo().equals("N")
								|| tcTeclado.getModo().equals("M")) {

							if (!tbvDetFactura.getSelectionModel()
									.getSelectedItem().getBloqueado()) {
								// Validar la existencia
								Map<String, String> exis = fm
										.validarExistenciaArticulo(fm
												.getDetalle().get(indice)
												.getArticulo(), canArt);

								if ((exis.get("tipval").equals("V"))
										&& exis.get("estado").equals("ERROR")) {

									Alert alert = new Alert(
											AlertType.CONFIRMATION);
									alert.setTitle("Validación de existencias");
									alert.setHeaderText(null);
									alert.setContentText(exis.get("mensaje"));

									Optional<ButtonType> result = alert
											.showAndWait();
									if (result.get() == ButtonType.OK) {

										if (tbvDetFactura.getSelectionModel()
												.getSelectedItem()
												.getArticulo().getEsDisp()
												.equals("S")) {

											alert = new Alert(
													AlertType.CONFIRMATION);
											alert.setTitle("Generar dispensado");
											alert.setHeaderText(null);
											alert.setContentText("Se generara el movimiento de dispensado por lo tanto la línea del articulo no podrá modificarse, ¿Desea continuar?");

											Optional<ButtonType> validarDispensado = alert
													.showAndWait();
											if (validarDispensado.get() == ButtonType.OK) {

												GestorVentanas gv = new GestorVentanas(
														"Pin");
												gv.iniciarVentanaPin(fm, df,
														false, false);
												PinController controller = gv
														.mostrarVentanaPin();

												if (controller.getPm().validacionCorrecta) {
													fm.crearLineaCantidad(
															fm.getDetalle()
																	.get(indice)
																	.getArticulo(),
															canArt,
															tipFac,
															indice,
															total,
															tcTeclado.getModo(),
															true);
												}
											}
										} else
											fm.crearLineaCantidad(fm
													.getDetalle().get(indice)
													.getArticulo(), canArt,
													tipFac, indice, total,
													tcTeclado.getModo(), false);
									} else {
										if (fm.getDetalle().get(indice) != null) {
											fm.getDetalle()
													.remove(fm.getDetalle()
															.get(indice));
										}
									}
								} else {
									if (!exis.get("estado").equals("ERROR")) {

										if (tbvDetFactura.getSelectionModel()
												.getSelectedItem()
												.getArticulo().getEsDisp()
												.equals("S")) {

											Alert alert = new Alert(
													AlertType.CONFIRMATION);
											alert.setTitle("Generar dispensado");
											alert.setHeaderText(null);
											alert.setContentText("Se generara el movimiento de dispensado por lo tanto la línea del articulo no podrá modificarse, ¿Desea continuar?");

											Optional<ButtonType> validarDispensado = alert
													.showAndWait();
											if (validarDispensado.get() == ButtonType.OK) {
												GestorVentanas gv = new GestorVentanas(
														"Pin");
												gv.iniciarVentanaPin(fm, df,
														false, false);
												PinController controller = gv
														.mostrarVentanaPin();

												if (controller.getPm().validacionCorrecta) {
													fm.crearLineaCantidad(
															fm.getDetalle()
																	.get(indice)
																	.getArticulo(),
															canArt,
															tipFac,
															indice,
															total,
															tcTeclado.getModo(),
															true);
												}
											}
										} else
											fm.crearLineaCantidad(fm
													.getDetalle().get(indice)
													.getArticulo(), canArt,
													tipFac, indice, total,
													tcTeclado.getModo(), false);
									} else {
										Alert alert = new Alert(AlertType.ERROR);

										alert.setTitle("Error");
										alert.setHeaderText(null);
										alert.setContentText(exis
												.get("mensaje"));

										alert.showAndWait();
									}
								}
							}
						} else {
							Resultado<Articulo> art = fm
									.obtenerArticulo(txtIngreso.getText());
							if (!art.getResultado().equals(TipoResultado.ERROR)) {
								DetFactura df = crearLinea(art.get());
								if (df != null) {
									fm.getDetalle().add(0, df);
									tbvDetFactura.getSelectionModel().select(0);
								}
							} else {
								Alert alert = new Alert(AlertType.ERROR);

								alert.setTitle("Obtener información del artículo");
								alert.setHeaderText(null);
								alert.setContentText(art.getMensaje());

								alert.showAndWait();
							}

						}
						tcTeclado.ingresoDatos.set("");
					}
				}
			} else {
				if (tcTeclado.getModo().equals("N")) {
					mensError = "Debe definir la cantidad de venta para el artículo.";
				} else if (tcTeclado.getModo().equals("M")) {
					mensError = "Debe definir el monto de venta para el artículo.";
				} else {
					mensError = "Debe definir el código del artículo.";
				}
				Alert alert = new Alert(AlertType.ERROR);

				alert.setTitle("Validacion de artículo bloqueado");
				alert.setHeaderText(null);
				alert.setContentText(mensError);

				alert.showAndWait();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);

			alert.setTitle("Agregar detalle factura");
			alert.setHeaderText(null);
			alert.setContentText("Debe definir al menos un detalle para la factura.");

			alert.showAndWait();
		}
	}

	private DetFactura crearLinea(Articulo articulo) {

		tcTeclado.setModo("D");
		DetFactura detalleFactura = null;
		Map<String, Object> resultado = fm.crearLineaArticulo(articulo);

		if (!((TipoResultado) resultado.get("resultado"))
				.equals(TipoResultado.ERROR)) {
			detalleFactura = (DetFactura) resultado.get("detalle");
			tcTeclado.setModo(articulo.getMetIngDef());
		} else {
			Alert alert = new Alert(AlertType.ERROR);

			alert.setTitle("Agregar línea de factura");
			alert.setHeaderText(null);
			alert.setContentText((String) resultado.get("mensaje"));
			alert.showAndWait();
		}
		return detalleFactura;
	}

	@ActionMethod("facCon")
	public void cambiarTipoCon() {
		btnFacCon.setSelected(true);
		btnFacCre.setSelected(false);
		fm.getFactura().setCodTfa("FCO");
	}

	@ActionMethod("facCre")
	public void cambiarTipoCre() {
		btnFacCre.setSelected(true);
		btnFacCon.setSelected(false);
		fm.getFactura().setCodTfa("FCR");
	}

	@Override
	public void inicioFactura() {
		lblDesCli.setText("");
		lblDesCli.setVisible(false);
		lblEstCre.setText("");
		lblEstCre.setVisible(false);
		lblDisCre.setText("");
		lblDisCre.setVisible(false);
		lblVenCre.setText("");
		lblVenCre.setVisible(false);
	}

	@Override
	public void mostrarMensaje(String encabezado, String mensaje) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(encabezado);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}

	@Override
	public void mostrarMensajeInfo(String encabezado, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(encabezado);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);

		alert.showAndWait();
	}

	@Override
	public Map<String, Object> mostrarMensaje(String encabezado,
			String mensaje, String tipo) {

		Map<String, Object> res = new HashMap<String, Object>();

		if (tipo.equals("N")) {
			Alert alert = new Alert(AlertType.ERROR);

			alert.setTitle(encabezado);
			alert.setHeaderText(null);
			alert.setContentText(mensaje);

			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);

			alert.setTitle(encabezado);
			alert.setHeaderText(null);
			alert.setContentText(mensaje);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				res.put("resultado", "YES");
			} else {
				res.put("resultado", "NO");
			}
		}
		return res;
	}

	@Override
	public void mostrarDatosAdicionalesCliente() {
		GestorVentanas gv = new GestorVentanas("DatosCliente");
		gv.iniciarVentanaDatosCliente(fm);
		gv.mostrarVentanaDatosCliente();
	}

	@Override
	public void habilitarBotonCredito(Boolean estado) {
		btnDatosCre.setDisable(!estado);
	}

	@Override
	public void aplicarPagoRapido() {
		if (fm.getFactura().getSubTot().compareTo(numCero) == 1) {

			fm.getDetalle().removeIf(d -> d.getTotal().equals(numCero));

			fm.calculaItoDtoMto(txtCodCli.getText(), fm.getFactura()
					.getPorDto1());

			if (fm.getDetalleFormaPago().size() < 1) {
				fm.setTotRec(Parametros.formatMon.format(numCero));
				fm.calcularTotalPagar(Parametros.formatMon.format(canDfp));
			} else {
				fm.getDetalleFormaPago().forEach((d) -> {
					canDfp = canDfp.add(d.getMonPag());
				});
				if (fm.getTotRec().equals(Parametros.formatMon.format(canDfp)))
					fm.calcularTotalPagar(Parametros.formatMon.format(canDfp));
				else
					fm.setTotRec(Parametros.formatMon.format(canDfp));
			}
			fm.mostrarMtoDescuento();

			if ((fm.getFactura().getCodTfa().equals(fm.getParametrosVentas()
					.getCodTfaCon()))
					|| (fm.getFactura().getCodTfa()
							.equals(fm.getParametrosVentas().getCodTfaCre()) && (fm
							.getDisCredito() != null && !fm.getDisCredito()
							.trim().isEmpty()))) {
				Boolean EliminarFP = true;

				// /**Si es factura de crédito
				if (fm.getFactura().getCodTfa()
						.equals(fm.getParametrosVentas().getCodTfaCre())
						&& fm.getDetalleFormaPago().size() > 0) {
					Map<String, Object> confirmar = mostrarMensaje(
							"Eliminar formas de pago",
							"Existen formas de pago asociadas a la factura, estas seran eliminadas si desea proceder con la factura de credito. ¿Desea continuar?",
							"S");

					if (confirmar.get("resultado").equals("YES"))
						EliminarFP = fm
								.reversarFormasPago(fm.getDetalleFormaPago(),
										"Reversión al quitar formas de pago, para facturar de crédito.");
					else
						EliminarFP = false;
				}

				if (EliminarFP) {
					if (fm.getEstCredito() != null
							&& fm.getEstCredito().replaceAll("Est: ", "")
									.equals("CERRADO")) {
						mostrarMensaje("Error al aplicar factura",
								"El crédito del cliente ["
										+ fm.getFactura().getCodCli()
										+ "] se encuentra cerradao.");
					} else {

						Date fecVen = null;
						try {
							if (fm.getVenCredito() != null) {
								fecVen = new SimpleDateFormat("dd/MM/yyyy")
										.parse(fm.getVenCredito().replaceAll(
												"Venc: ", ""));
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						Date sysdate = fm.obtenerFechaActual();

						if (fm.getFactura()
								.getCodTfa()
								.equals(fm.getParametrosVentas().getCodTfaCre())
								&& fecVen != null && !fecVen.after(sysdate)) {
							mostrarMensaje("Error al aplicar factura",
									"El crédito del cliente ["
											+ fm.getFactura().getCodCli()
											+ "] ha expirado.");

						} else if (fm
								.getFactura()
								.getCodTfa()
								.equals(fm.getParametrosVentas().getCodTfaCre())
								&& fm.getFactura()
										.getTotal()
										.compareTo(
												new BigDecimal(fm
														.getDisCredito()
														.replaceAll("Disp: ",
																"")
														.replaceAll(",", ""))) > 0) {
							mostrarMensaje("Error al aplicar factura",
									"El monto de la factura supera al monto disponible del crédito.");
						} else {
							fm.setExonerarFactura(false);
							fm.getImpuestoServicio().set(false);
							fm.mostrarMtoImpServ();
							try {
								Boolean continuar = true;

								if (Parametros.valpin.equals("S")) {
									GestorVentanas gv = new GestorVentanas(
											"Pin");
									gv.iniciarVentanaPin(fm, df, true, false);
									PinController controller = gv
											.mostrarVentanaPin();
									continuar = controller.getPm().validacionCorrecta;
								}
								if (continuar) {
									/** #jcalvo. fecha:28/06/2017. */
									fm.continuarFactura();
									fm.aplicarComoEfectivo();
									reiniciarFactura();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Validar datos de factura");
				alert.setHeaderText(null);
				alert.setContentText("El cliente ingresado no aplica para facturas de credito.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Validar datos de factura");
			alert.setHeaderText(null);
			alert.setContentText("Debe definirse el total de la factura.");
			alert.showAndWait();
		}
	}

	private void reiniciarFactura() {

		Boolean EliminarFP = true;

		if (fm.getFactura().getCodTfa()
				.equals(fm.getParametrosVentas().getCodTfaCon())
				&& fm.getDetalleFormaPago().size() > 0) {
			Map<String, Object> confirmar = mostrarMensaje(
					"Eliminar formas de pago",
					"Existen formas de pago asociadas a la factura, estas seran eliminadas al reiniciar la factura. ¿Desea continuar?",
					"S");

			Boolean dfpAplicados = false;

			for (DetFormaPago dfp : fm.getDetalleFormaPago()) {
				if (dfp.getAplicada().equals("S"))
					dfpAplicados = true;
			}

			if (confirmar.get("resultado").equals("YES")) {
				if (dfpAplicados) {
					GestorVentanas gv = new GestorVentanas("Pin");
					gv.iniciarVentanaPin(fm, df, false, true);
					PinController controller = gv.mostrarVentanaPin();
					Boolean val = controller.getPm().validacionCorrecta;

					if (val)
						EliminarFP = fm.reversarFormasPago(
								fm.getDetalleFormaPago(),
								"Reversión al reiniciar la factura. Usuario: "
										+ ((String) ApplicationContext
												.getInstance()
												.getRegisteredObject(
														"usuActual"))
										+ new Date().toString());
					else
						EliminarFP = false;
				} else
					EliminarFP = fm.reversarFormasPago(
							fm.getDetalleFormaPago(),
							"Reversión al reiniciar la factura. Usuario: "
									+ ((String) ApplicationContext
											.getInstance().getRegisteredObject(
													"usuActual"))
									+ new Date().toString());
			} else
				EliminarFP = false;
		}

		if (EliminarFP) {
			if (fm.getDispensados() != null && fm.getDispensados().size() > 0)
				mostrarMensaje(
						"Eliminar dispensados",
						"Existen detalles de artículos dispensados, eliminelos antes de reiniciar la factura.");
			else {
				fm.reiniciarFactura(true);
				tbvDetFactura.getSelectionModel().selectLast();
				tbvDetFactura.requestFocus();
				try {
					init();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}
}
