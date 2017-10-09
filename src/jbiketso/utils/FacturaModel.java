package cr.co.coopeagri.opentouch.model.classes;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
//import java.util.logging.Logger;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.datafx.controller.context.ApplicationContext;
import org.datafx.controller.flow.injection.FlowScoped;
import org.datafx.crud.jpa.CreateOnceSupplier;

import cr.co.coopeagri.opentouch.model.entitys.Articulo;
import cr.co.coopeagri.opentouch.model.entitys.ArticuloPK;
import cr.co.coopeagri.opentouch.model.entitys.BitacoraAutorizacion;
import cr.co.coopeagri.opentouch.model.entitys.Categoria;
import cr.co.coopeagri.opentouch.model.entitys.CesionFactura;
import cr.co.coopeagri.opentouch.model.entitys.Cliente;
import cr.co.coopeagri.opentouch.model.entitys.DespachoCombustible;
import cr.co.coopeagri.opentouch.model.entitys.DetFactura;
import cr.co.coopeagri.opentouch.model.entitys.DetFormaPago;
import cr.co.coopeagri.opentouch.model.entitys.Dispensado;
import cr.co.coopeagri.opentouch.model.entitys.Factura;
import cr.co.coopeagri.opentouch.model.entitys.FacturaTemporal;
import cr.co.coopeagri.opentouch.model.entitys.FormaPago;
import cr.co.coopeagri.opentouch.model.entitys.ParametrosInventario;
import cr.co.coopeagri.opentouch.model.entitys.ParametrosPos;
import cr.co.coopeagri.opentouch.model.entitys.ParametrosTouch;
import cr.co.coopeagri.opentouch.model.entitys.ParametrosVentas;
import cr.co.coopeagri.opentouch.model.entitys.Vendedor;
import cr.co.coopeagri.opentouch.model.interfaces.ArticuloDao;
import cr.co.coopeagri.opentouch.model.interfaces.BitacoraAutorizacionDao;
import cr.co.coopeagri.opentouch.model.interfaces.CategoriaDao;
import cr.co.coopeagri.opentouch.model.interfaces.CesionFacturaDao;
import cr.co.coopeagri.opentouch.model.interfaces.CierreControllerInterface;
import cr.co.coopeagri.opentouch.model.interfaces.ClienteDao;
import cr.co.coopeagri.opentouch.model.interfaces.DaoFactory;
import cr.co.coopeagri.opentouch.model.interfaces.DespachoCombustibleDao;
import cr.co.coopeagri.opentouch.model.interfaces.DispensadoDao;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaControllerInterface;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaDao;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaTemporalDao;
import cr.co.coopeagri.opentouch.model.interfaces.FormaPagoDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosInventarioDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosPosDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosTouchDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosVentasDao;
import cr.co.coopeagri.opentouch.model.interfaces.TecladoDao;
import cr.co.coopeagri.opentouch.model.interfaces.VendedorDao;
import cr.coopeagri.voximporter.Sale;

@FlowScoped
public class FacturaModel {

	// private String _class = FacturaModel.class.getName();
	// private Logger log = Logger.getLogger(_class);

	private DaoFactory df;

	private CreateOnceSupplier<CategoriaDao> categoriaDao;
	private CreateOnceSupplier<ArticuloDao> articuloDao;
	private CreateOnceSupplier<ParametrosInventarioDao> parametrosInventarioDao;
	private CreateOnceSupplier<TecladoDao> tecladoDao;
	private CreateOnceSupplier<ClienteDao> clienteDao;
	private CreateOnceSupplier<FormaPagoDao> formaPagoDao;
	private CreateOnceSupplier<ParametrosPosDao> parametrosPosDao;
	private CreateOnceSupplier<FacturaDao> facturaDao;
	private CreateOnceSupplier<ParametrosVentasDao> parametrosVentasDao;
	private CreateOnceSupplier<FacturaTemporalDao> facturaTemporalDao;
	private CreateOnceSupplier<CesionFacturaDao> cesionfacturaDao;
	private CreateOnceSupplier<DispensadoDao> dispensadoDao;
	private CreateOnceSupplier<BitacoraAutorizacionDao> bitacoraAutorizacionDao;
	// esanchez scre004224 13/04/2016
	private CreateOnceSupplier<VendedorDao> vendedorDao;

	/** jcalvo. fecha:12/06/2017. */
	private CreateOnceSupplier<DespachoCombustibleDao> despachoCombustibleDao;
	private CreateOnceSupplier<ParametrosTouchDao> parametrosTouchDao;
	// -------------------------------------------------------------------------

	private FacturaControllerInterface fc;
	private CierreControllerInterface cc;

	private Factura factura = new Factura();

	private Process processTeclado;

	private SimpleObjectProperty<List<Categoria>> categoria = new SimpleObjectProperty<>();
	private SimpleObjectProperty<List<Articulo>> articulo = new SimpleObjectProperty<>();
	private SimpleObjectProperty<List<FormaPago>> formaPago = new SimpleObjectProperty<>();
	private SimpleObjectProperty<List<BigDecimal>> montos = new SimpleObjectProperty<>();
	private SimpleObjectProperty<List<BigDecimal>> cantidades = new SimpleObjectProperty<>();

	// esanchez scre004224 13/04/2016
	private ObservableList<Vendedor> vendedores = FXCollections
			.observableArrayList();

	// jcalvo. fecha:12/06/2017.
	private ObservableList<DespachoCombustible> despachosCombustible = FXCollections
			.observableArrayList();

	private SimpleStringProperty codCliente = new SimpleStringProperty(null);
	private SimpleStringProperty desCliente = new SimpleStringProperty(null);
	private SimpleStringProperty estCredito = new SimpleStringProperty(null);
	private SimpleStringProperty disCredito = new SimpleStringProperty(null);
	private SimpleStringProperty venCredito = new SimpleStringProperty(null);

	private SimpleStringProperty totRec = new SimpleStringProperty();
	private SimpleStringProperty totPag = new SimpleStringProperty();

	private SimpleStringProperty porDto = new SimpleStringProperty("0.00");
	private SimpleStringProperty totDesc = new SimpleStringProperty("0.00");
	private SimpleStringProperty totImp = new SimpleStringProperty("0.00");
	private SimpleStringProperty totFac = new SimpleStringProperty("0.00");
	private SimpleStringProperty totExo = new SimpleStringProperty("0.00");
	private SimpleStringProperty totServ = new SimpleStringProperty("0.00");

	private SimpleStringProperty placa = new SimpleStringProperty();
	private SimpleStringProperty kilometraje = new SimpleStringProperty();
	private SimpleStringProperty orden = new SimpleStringProperty();
	private SimpleStringProperty chofer = new SimpleStringProperty();
	private SimpleStringProperty observacion = new SimpleStringProperty();
	private SimpleStringProperty idResponsable = new SimpleStringProperty();
	private SimpleStringProperty nomResponsable = new SimpleStringProperty();

	// esanchez scre004224 13/04/2016
	private SimpleStringProperty numMesa = new SimpleStringProperty();
	private SimpleStringProperty numComanda = new SimpleStringProperty();
	private SimpleStringProperty vendedor = new SimpleStringProperty();
	private SimpleBooleanProperty impuestoServicio = new SimpleBooleanProperty(
			false);
	private SimpleStringProperty porImpSer = new SimpleStringProperty("0.00");
	private SimpleStringProperty totImpSer = new SimpleStringProperty("0.00");

	private BigDecimal totalLinea = new BigDecimal("0");
	public SimpleStringProperty total = new SimpleStringProperty();

	private SimpleBooleanProperty imprimeComoContado = new SimpleBooleanProperty(
			false);
	private SimpleBooleanProperty exonerarFactura = new SimpleBooleanProperty(
			false);
	private SimpleBooleanProperty datosCredito = new SimpleBooleanProperty(true);

	private Resultado<ParametrosInventario> parametrosInventario;
	private Resultado<ParametrosPos> parametrosPos;
	private Resultado<ParametrosVentas> parametrosVentas;

	// #jcalvo. fecha:15/06/2017.
	private Resultado<ParametrosTouch> parametrosTouch;
	// ---------------------------------------------------

	private ObservableList<DetFactura> detalle = FXCollections
			.observableList(getFactura().getDetFactura());
	private ObservableList<DetFormaPago> detalleFormaPago = FXCollections
			.observableList(getFactura().getMovimiento().getDetFormaPago());
	private ObservableList<Dispensado> dispensados = FXCollections
			.observableArrayList();

	private static BigDecimal numCero = BigDecimal.ZERO;

	private List<String> codBan = new ArrayList<String>();
	private List<String> desBan = new ArrayList<String>();

	private List<String> codBod = new ArrayList<String>();
	private List<String> desBod = new ArrayList<String>();

	private BigDecimal porIto = numCero;
	private String codIto = null;
	private BigDecimal totGravDto = numCero;
	private BigDecimal totExenDto = numCero;
	private BigDecimal totGrav = numCero;
	private BigDecimal totExen = numCero;
	private BigDecimal totItoDto = numCero;
	private BigDecimal totalIvi = numCero;
	private BigDecimal diasPlazo = numCero;

	private List<Autorizado> auts;

	private String pantalla;

	private Boolean autorizaPlazoCredito;

	// ---------------------------------------------------------------------------------

	public FacturaModel() {
		categoriaDao = new CreateOnceSupplier<>(() -> df.getCategoriaDao());
		articuloDao = new CreateOnceSupplier<>(() -> df.getArticuloDao());
		tecladoDao = new CreateOnceSupplier<>(() -> df.getTecladoDao());
		clienteDao = new CreateOnceSupplier<>(() -> df.getClienteDao());
		parametrosInventarioDao = new CreateOnceSupplier<>(
				() -> df.getParametrosInventarioDao());
		formaPagoDao = new CreateOnceSupplier<>(() -> df.getFormaPagoDao());
		parametrosPosDao = new CreateOnceSupplier<>(
				() -> df.getParametrosPosDao());
		facturaDao = new CreateOnceSupplier<>(() -> df.getFacturaDao());
		parametrosVentasDao = new CreateOnceSupplier<>(
				() -> df.getParametrosVentasDao());
		facturaTemporalDao = new CreateOnceSupplier<>(
				() -> df.getFacturaTemporalDao());
		cesionfacturaDao = new CreateOnceSupplier<>(
				() -> df.getCesionFacturaDao());
		dispensadoDao = new CreateOnceSupplier<>(() -> df.getDispensadoDao());
		bitacoraAutorizacionDao = new CreateOnceSupplier<>(
				() -> df.getBitacoraAutorizacionDao());
		vendedorDao = new CreateOnceSupplier<>(() -> df.getVendedorDao());

		/** #jcalvo. fecha:12/06/2017. */
		despachoCombustibleDao = new CreateOnceSupplier<>(
				() -> df.getDespachoCombustibleDao());
		parametrosTouchDao = new CreateOnceSupplier<>(
				() -> df.getParametrosTouchDao());
		/** ------------------------------------------------ */

		factura.numOrdProperty().bindBidirectional(numComanda);
		factura.docProperty().bindBidirectional(numMesa);
		factura.codvenProperty().bindBidirectional(vendedor);
		factura.aplImpSerProperty().bindBidirectional(impuestoServicio);
		impuestoServicio.set(Parametros.impserv.equals("S"));

		categoria.addListener(new ChangeListener<List<Categoria>>() {
			@Override
			public void changed(
					ObservableValue<? extends List<Categoria>> observable,
					List<Categoria> oldValue, List<Categoria> newValue) {
				fc.cargarCategorias(newValue);
			}
		});

		articulo.addListener(new ChangeListener<List<Articulo>>() {
			@Override
			public void changed(
					ObservableValue<? extends List<Articulo>> observable,
					List<Articulo> oldValue, List<Articulo> newValue) {
				fc.cargarArticulos(newValue);
			}
		});

		formaPago.addListener(new ChangeListener<List<FormaPago>>() {
			@Override
			public void changed(
					ObservableValue<? extends List<FormaPago>> observable,
					List<FormaPago> oldValue, List<FormaPago> newValue) {
				cc.cargarFormasPago(newValue);
			}

		});

		montos.addListener(new ChangeListener<List<BigDecimal>>() {
			@Override
			public void changed(
					ObservableValue<? extends List<BigDecimal>> observable,
					List<BigDecimal> oldValue, List<BigDecimal> newValue) {
				if (cc != null)
					cc.cargarMontos(newValue);
			}

		});

		setAutorizaPlazoCredito(false);

		exonerarFactura.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				String tipCaj = (String) ApplicationContext.getInstance()
						.getRegisteredObject("tipoCajero");
				if (!tipCaj.equals("S")) {
					if (requiereAut("EXONFAC")) {
						if (cc.mostrarVentanaPin()) {
							exonerarFactura(newValue);
						} else {
							exonerarFactura.set(false);
						}
					}
				} else {
					exonerarFactura(newValue);
				}
			} else {
				exonerarFactura(newValue);
			}
		});

		impuestoServicio
				.addListener((observable, oldValue, newValue) -> {
					BigDecimal totFac = numCero;
					if (!newValue) {
						factura.setPorItoSer(BigDecimal.ZERO);
						totFac = getFactura().getTotal();
						totPag.setValue(Parametros.formatMon.format(totFac
								.subtract(new BigDecimal(totRec.get()
										.replaceAll(",", "")))));

						getFactura().setTotal(totFac);
					} else {
						factura.setPorItoSer(parametrosInventario.get()
								.getPorItoSer());
						totFac = getFactura().getTotal();
						getFactura().setTotal(totFac);

						totPag.setValue(Parametros.formatMon.format(totFac
								.subtract(new BigDecimal(totRec.get()
										.replaceAll(",", "")))));
					}
					mostrarMtoImpServ();
				});
		agregarListener();
	}

	public void agregarListener() {

		codCliente.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				if (newValue != null) {
					codCliente.set(newValue.toUpperCase());

					if (newValue.length() == 7) {
						cargarCliente(newValue);
					} else if (newValue.length() < 7) {
						desCliente.set(null);
						estCredito.set(null);
						disCredito.set(null);
						venCredito.set(null);
						getFactura().setPorDto1(numCero); // FAC_PORDTO1
					} else if (newValue.length() > 7)
						codCliente.set(oldValue);
				} else {
					desCliente.set(null);
					estCredito.set(null);
					disCredito.set(null);
					venCredito.set(null);
				}
				mostrarMtoDescuento();
				mostrarMtoImpServ();
			}

		});

		totRec.addListener((observable, oldValue, newValue) -> {
			calcularTotalPagar(newValue);
		});

		detalleFormaPago.addListener(new ListChangeListener<DetFormaPago>() {
			@Override
			public void onChanged(Change<? extends DetFormaPago> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						c.getList()
								.get(0)
								.monPagProperty()
								.addListener(
										(observable, oldValue, newValue) -> {
											totRec.setValue(Parametros.formatMon
													.format((new BigDecimal(
															totRec.getValue()
																	.replaceAll(
																			",",
																			""))
															.subtract(oldValue))
															.add(newValue))
													.toString());
										});
					} else if (c.wasUpdated()) {

					} else if (c.wasRemoved()) {
						totRec.setValue((new BigDecimal(totRec.get()
								.replaceAll(",", "")).subtract(c.getRemoved()
								.get(0).getMonPag())).toString());
					} else if (c.wasPermutated()) {

					} else if (c.wasReplaced()) {

					}
				}
			}
		});

		detalle.addListener(new ListChangeListener<DetFactura>() {
			@Override
			public void onChanged(Change<? extends DetFactura> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						calcularTotalGeneral();
					} else if (c.wasUpdated()) {

					} else if (c.wasRemoved()) {
						/** #jcalvo. fecha:12/06/2017. */
						if (c.getRemoved().get(0).getDespacho() != null) {
							getDespachoCombustible()
									.stream()
									.filter(d -> d.getId().equals(
											c.getRemoved().get(0).getDespacho()
													.getId())).findFirst()
									.get().setIndEstado("P");
						}
						calcularTotalGeneral();
					} else if (c.wasPermutated()) {

					}
				}
			}
		});

		getFactura().totalProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (getFactura().getMovimientoIngreso() != null) {
						totFac.setValue(Parametros.formatMon
								.format(getFactura().getTotal().add(
										getFactura().getMovimientoIngreso()
												.getTotFac())));
					} else {
						totFac.setValue(Parametros.formatMon
								.format(getFactura().getTotal()));
					}
				});

		getFactura().subTotProperty().addListener(
				(observable, oldValue, newValue) -> {
					total.setValue(Parametros.formatMon.format(newValue));
				});
	}

	public void agregarListenerDespachosCombustible(DespachoCombustible dc) {
		dc.setIndEstado("P");
		dc.indEstadoProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if (newValue.equals("A")) {
					// agrega el despacho al detalle de la factura
					ArticuloPK a = new ArticuloPK();
					a.setCodcia(dc.getCodCia());
					a.setCodinv(dc.getCodInventario());
					a.setCodigo(dc.getCodArticulo());
					Articulo artDespachado = articuloDao.get().getById(a).get();
					if (!getDetalle()
							.stream()
							.filter(d -> Objects.nonNull(d.getDespacho())
									&& d.getDespacho().getId()
											.equals(dc.getId())).findFirst()
							.isPresent()) {
						agregarDetalle(artDespachado);
						getDetalle().get(0).setDespacho(dc);
						// asigna el precio indicado por el dispensado
						getDetalle().get(0).setPrecio(dc.getPrecio());
						cambiarCantidadDetalle(artDespachado, dc.getVolumen(),
								getFactura().getCodTfa(), 0, "C");
					}
				} else {
					if (getDetalle()
							.stream()
							.filter(d -> Objects.nonNull(d.getDespacho())
									&& d.getDespacho().getId()
											.equals(dc.getId())).findFirst()
							.isPresent()) {
						getDetalle().remove(
								getDetalle()
										.stream()
										.filter(d -> Objects.nonNull(d
												.getDespacho().getId())
												&& d.getDespacho().getId()
														.equals(dc.getId()))
										.findFirst().get());
					}
				}
				/** #jcalvo. fecha:20/06/2017. */
				calcularTotalGeneral();
			}
		});
	}

	// ---------------------------------------------------------------------------------
	public void exonerarFactura(boolean exonerar) {
		BigDecimal totFac = numCero;
		if (exonerar) {
			totExo.set(totImp.get());
			getFactura().setExoner("S");
			totFac = getFactura().getTotal();
			totPag.setValue(Parametros.formatMon.format(totFac
					.subtract(new BigDecimal(totRec.get().replaceAll(",", "")))));
		} else {
			getFactura().setExoner("N");
			totFac = getFactura().getTotal();

			totPag.setValue(Parametros.formatMon.format(totFac
					.subtract(new BigDecimal(totRec.get().replaceAll(",", "")))));

			totExo.set("0.00");
		}
		exonerarFactura.set(exonerar);
	}

	public void mostrarMtoImpServ() {
		porImpSer.setValue(Parametros.formatMon.format(getFactura()
				.getPorItoSer()) + "%");
		totImpSer.setValue(Parametros.formatMon.format(getFactura()
				.getMtoItoSer()));
	}

	public void mostrarMtoDescuento() {
		porDto.setValue(Parametros.formatMon.format(getFactura().getPorDto1())
				+ "%");
		totDesc.setValue(Parametros.formatMon.format(getFactura().getSubTotd()));
		totImp.set(Parametros.formatMon.format(getFactura().getTotIto1()));
		if (totRec.get() != null) {
			BigDecimal totalFactura = getFactura().getTotal();

			if (getFactura().getMovimientoIngreso() != null)
				totalFactura = totalFactura.add(getFactura()
						.getMovimientoIngreso().getTotFac());

			if (totalFactura.subtract(
					new BigDecimal(totRec.get().replaceAll(",", "")))
					.compareTo(BigDecimal.ZERO) >= 0)
				totPag.setValue(Parametros.formatMon.format(totalFactura
						.subtract(new BigDecimal(totRec.get().replaceAll(",",
								"")))));
			else
				totPag.setValue(Parametros.formatMon.format(numCero));
		} else
			totPag.setValue(Parametros.formatMon
					.format(getFactura().getTotal()));

		if (getFactura().getMovimientoIngreso() != null)
			totServ.setValue(Parametros.formatMon.format(getFactura()
					.getMovimientoIngreso().getTotFac()));
		else
			totServ.setValue("0.00");

		if (getFactura().getMovimientoIngreso() != null) {
			totFac.setValue(Parametros.formatMon.format(getFactura().getTotal()
					.add(getFactura().getMovimientoIngreso().getTotFac())));
		} else {
			totFac.setValue(Parametros.formatMon
					.format(getFactura().getTotal()));
		}
	}

	public void calcularTotalPagar(String montoNuevo) {
		BigDecimal totalFactura = getFactura().getTotal();
		if (getFactura().getMovimientoIngreso() != null)
			totalFactura = totalFactura.add(getFactura().getMovimientoIngreso()
					.getTotFac());

		if ((totalFactura.subtract(new BigDecimal(montoNuevo
				.replaceAll(",", "")))).compareTo(numCero) == -1) {
			totPag.setValue(Parametros.formatMon.format(numCero));
		} else {
			totPag.setValue(Parametros.formatMon.format(totalFactura
					.subtract(new BigDecimal(montoNuevo.replaceAll(",", "")))));
		}
	}

	public Date obtenerFechaActual() {
		return parametrosInventarioDao.get().traerFecHorServidor().get();
	}

	public FacturaControllerInterface getFacturaController() {
		return this.fc;
	}

	public void setFacturaController(FacturaControllerInterface fc) {
		this.fc = fc;
	}

	public CierreControllerInterface getCierreController() {
		return this.cc;
	}

	public void setCierreController(CierreControllerInterface cc) {
		this.cc = cc;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Factura getFactura() {
		return factura;
	}

	public DaoFactory getDf() {
		return df;
	}

	public void setDf(DaoFactory df) {
		this.df = df;
	}

	public ObservableList<DetFactura> getDetalle() {
		return detalle;
	}

	public void setDetalle(ObservableList<DetFactura> detalle) {
		this.detalle = detalle;
	}

	public ObservableList<DetFormaPago> getDetalleFormaPago() {
		return detalleFormaPago;
	}

	public void setDetalleFormaPago(
			ObservableList<DetFormaPago> detalleFormaPago) {
		this.detalleFormaPago = detalleFormaPago;
	}

	public ObservableList<Dispensado> getDispensados() {
		return dispensados;
	}

	public void setDispensados(ObservableList<Dispensado> dispensados) {
		this.dispensados = dispensados;
	}

	public String getTotal() {
		return total.get();
	}

	public void setTotal(String Total) {
		this.total.set(Total);
	}

	public SimpleStringProperty totalProperty() {
		return total;
	}

	public ParametrosPos getParametrosPos() {
		return parametrosPos.get();
	}

	public ParametrosInventario getParametrosInventario() {
		return parametrosInventario.get();
	}

	public ParametrosVentas getParametrosVentas() {
		return parametrosVentas.get();
	}

	/** #jcalvo. fecha:16/06/2017. */
	public ParametrosTouch getParametrosTouch() {
		return parametrosTouch.get();
	}

	/***/

	public String getCodCliente() {
		return this.codCliente.get();
	}

	public void setCodCliente(String codCliente) {
		this.codCliente.set(codCliente);
	}

	public SimpleStringProperty codClienteProperty() {
		return this.codCliente;
	}

	public String getDesCliente() {
		return this.desCliente.get();
	}

	public void setDesCliente(String desCliente) {
		this.desCliente.set(desCliente);
	}

	public SimpleStringProperty desClienteProperty() {
		return this.desCliente;
	}

	public String getEstCredito() {
		return this.estCredito.get();
	}

	public void setEstCredito(String estCredito) {
		this.estCredito.set(estCredito);
	}

	public SimpleStringProperty estCreditoProperty() {
		return this.estCredito;
	}

	public String getDisCredito() {
		return this.disCredito.get();
	}

	public void setDisCredito(String disCredito) {
		this.disCredito.set(disCredito);
	}

	public SimpleStringProperty disCreditoProperty() {
		return this.disCredito;
	}

	public String getVenCredito() {
		return this.venCredito.get();
	}

	public void setVenCredito(String venCredito) {
		this.venCredito.set(venCredito);
	}

	public SimpleStringProperty venCreditoProperty() {
		return this.venCredito;
	}

	public String getTotRec() {
		return totRec.get();
	}

	public void setTotRec(String totRec) {
		this.totRec.set(totRec);
	}

	public SimpleStringProperty totRecProperty() {
		return totRec;
	}

	public String getTotPag() {
		return totPag.get();
	}

	public void setTotPag(String totPag) {
		this.totPag.set(totPag);
	}

	public SimpleStringProperty totPagProperty() {
		return totPag;
	}

	public String getPorDto() {
		return porDto.get();
	}

	public void setPorDto(String porDto) {
		this.porDto.set(porDto);
	}

	public SimpleStringProperty porDtoProperty() {
		return porDto;
	}

	public String getTotDesc() {
		return totDesc.get();
	}

	public void setTotDesc(String totDesc) {
		this.totDesc.set(totDesc);
	}

	public SimpleStringProperty totDescProperty() {
		return totDesc;
	}

	public String getTotImp() {
		return totImp.get();
	}

	public void setTotImp(String totImp) {
		this.totImp.set(totImp);
	}

	public SimpleStringProperty TotImpProperty() {
		return totImp;
	}

	public String getTotFac() {
		return totFac.get();
	}

	public void setTotFac(String totFac) {
		this.totFac.set(totFac);
	}

	public SimpleStringProperty totFacProperty() {
		return totFac;
	}

	public String getTotExo() {
		return totExo.get();
	}

	public void setTotExo(String totExo) {
		this.totExo.set(totExo);
	}

	public SimpleStringProperty totExoProperty() {
		return totExo;
	}

	public String getTotServ() {
		return totServ.get();
	}

	public void setTotServ(String totServ) {
		this.totServ.set(totServ);
	}

	public SimpleStringProperty getTotServProperty() {
		return totServ;
	}

	public List<String> getCodBod() {
		return codBod;
	}

	public void setCodBod(List<String> codBod) {
		this.codBod = codBod;
	}

	public List<String> getDesBod() {
		return desBod;
	}

	public void setDesBod(List<String> desBod) {
		this.desBod = desBod;
	}

	public Boolean getImprimeComoContado() {
		return imprimeComoContado.get();
	}

	public void setImprimeComoContado(Boolean imprimeComoContado) {
		this.imprimeComoContado.set(imprimeComoContado);
	}

	public SimpleBooleanProperty ImprimeComoContadoProperty() {
		return imprimeComoContado;
	}

	public String getPlaca() {
		return placa.get();
	}

	public void setPlaca(String placa) {
		this.placa.set(placa);
	}

	public SimpleStringProperty getPlacaProperty() {
		return placa;
	}

	public String getKilometraje() {
		return kilometraje.get();
	}

	public void setKilometraje(String kilometraje) {
		this.kilometraje.set(kilometraje);
	}

	public SimpleStringProperty getKilometrajeProperty() {
		return kilometraje;
	}

	public void setIdResponsable(String idResponsable) {
		this.idResponsable.set(idResponsable);
	}

	public SimpleStringProperty getIdResponsableProperty() {
		return idResponsable;
	}

	public void setNomResponsable(String nomResponsable) {
		this.nomResponsable.set(nomResponsable);
	}

	public SimpleStringProperty getNomResponsableProperty() {
		return nomResponsable;
	}

	public String getOrden() {
		return orden.get();
	}

	public void setOrden(String orden) {
		this.orden.set(orden);
	}

	public SimpleStringProperty getOrdenProperty() {
		return orden;
	}

	public String getChofer() {
		return chofer.get();
	}

	public void setChofer(String chofer) {
		this.chofer.set(chofer);
	}

	public SimpleStringProperty getChoferProperty() {
		return chofer;
	}

	public String getObservacion() {
		return observacion.get();
	}

	public void setObservacion(String observacion) {
		this.observacion.set(observacion);
	}

	public SimpleStringProperty observacionProperty() {
		return observacion;
	}

	public Boolean getDatosCredito() {
		return datosCredito.get();
	}

	public void setDatosCredito(Boolean datosCredito) {
		this.datosCredito.set(datosCredito);
	}

	public SimpleBooleanProperty datosCreditoProperty() {
		return datosCredito;
	}

	public List<Autorizado> getAuts() {
		return auts;
	}

	public void setAuts(List<Autorizado> auts) {
		this.auts = auts;
	}

	public BigDecimal getDiasPlazo() {
		return diasPlazo;
	}

	public void setDiasPlazo(BigDecimal diasPlazo) {
		this.diasPlazo = diasPlazo;
	}

	public Boolean getExonerarFactura() {
		return exonerarFactura.get();
	}

	public void setExonerarFactura(Boolean exonerarFactura) {
		this.exonerarFactura.set(exonerarFactura);
	}

	public SimpleBooleanProperty exonerarFacturaProperty() {
		return this.exonerarFactura;
	}

	// ---------------------------------------------------------------------------------
	public List<Categoria> getCategoria() {
		return categoria.get();
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria.set(categoria);
	}

	public List<BigDecimal> getMontos() {
		return montos.get();
	}

	public void setMontos(List<BigDecimal> montos) {
		this.montos.set(montos);
	}

	public List<FormaPago> getFormaPago() {
		return formaPago.get();
	}

	public void setFormaPago(List<FormaPago> formaPago) {
		this.formaPago.set(formaPago);
	}

	public List<BigDecimal> getCantidades() {
		return cantidades.get();
	}

	public void setCantidades(List<BigDecimal> cantidades) {
		this.cantidades.set(cantidades);
	}

	public String getNumMesa() {
		return numMesa.get();
	}

	public void setNumMesa(String numMesa) {
		this.numMesa.set(numMesa);
	}

	public SimpleStringProperty getNumMesaProperty() {
		return numMesa;
	}

	public String getNumComanda() {
		return numComanda.get();
	}

	public void setNumComanda(String numComanda) {
		this.numComanda.set(numComanda);
	}

	public SimpleStringProperty getNumComandaProperty() {
		return numComanda;
	}

	public String getVendedor() {
		return vendedor.get();
	}

	public void setVendedor(String vendedor) {
		this.vendedor.set(vendedor);
	}

	public SimpleStringProperty getVendedorProperty() {
		return vendedor;
	}

	public ObservableList<Vendedor> getVendedores() {
		return vendedores;
	}

	public void setVendedores(ObservableList<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}

	/** #jcalvo. fecha:13/06/2017. */
	public ObservableList<DespachoCombustible> getDespachoCombustible() {
		return despachosCombustible;
	}

	public void setDespachosCombustible(
			ObservableList<DespachoCombustible> despachosCombustible) {
		this.despachosCombustible = despachosCombustible;
	}

	public SimpleBooleanProperty getImpuestoServicio() {
		return impuestoServicio;
	}

	public void setImpuestoServicio(SimpleBooleanProperty impuestoServicio) {
		this.impuestoServicio = impuestoServicio;
	}

	public SimpleBooleanProperty impuestoServicioProperty() {
		return this.impuestoServicio;
	}

	public SimpleStringProperty getPorImpSer() {
		return porImpSer;
	}

	public void setPorImpSer(SimpleStringProperty porImpSer) {
		this.porImpSer = porImpSer;
	}

	public SimpleStringProperty getTotImpSer() {
		return totImpSer;
	}

	public void setTotImpSer(SimpleStringProperty totImpSer) {
		this.totImpSer = totImpSer;
	}

	public void cargarCategorias() {
		categoria.set(null);
		categoria.set(categoriaDao.get().findByVisible().get());
	}

	public void cargarVendedores() {
		vendedores.clear();
		vendedores.setAll(vendedorDao.get().findByEstado("A").get());
	}

	/** #jcalvo. fecha:13/06/2017. */
	public void cargarDespachosCombustible() {
		despachosCombustible.clear();
		// #jcalvo. scre:04698. fecha:19/07/2017. turno
		despachosCombustible.setAll(despachoCombustibleDao
				.get()
				.findByCarasAtendidas(Parametros.carasatendidas,
						Parametros.turnoactual).get());

		for (DespachoCombustible x : despachosCombustible) {
			agregarListenerDespachosCombustible(x);
		}
	}

	public boolean validarTurnoDespachosCombustible(DespachoCombustible dc) {
		return dc.getTurno().equalsIgnoreCase(Parametros.turnoactual);
	}

	/**********************************************************************************************/

	public void cargarArticulos(Categoria categoria) {
		articulo.set(null);
		articulo.set(articuloDao.get().findByCategoria(categoria).get());
	}

	public void cargarFormasPago() {
		formaPago.set(null);
		formaPago.set(formaPagoDao.get().findByVisible(false).get());
	}

	public void cargarMontos() {
		montos.set(null);
		montos.set(tecladoDao.get().findByTipo("M").get());
	}

	// ---------------------------------------------------------------------------------
	public Resultado<ParametrosInventario> cargarParametrosInventario() {
		if (parametrosInventario == null) {
			parametrosInventario = parametrosInventarioDao.get().findByCodigo();
		}
		return parametrosInventario;
	}

	public Resultado<ParametrosPos> cargarParametrosPos() {
		if (parametrosPos == null) {
			parametrosPos = parametrosPosDao.get().findByCodigo();
		}
		return parametrosPos;
	}

	public Resultado<ParametrosVentas> cargarParametrosVentas() {
		if (parametrosVentas == null) {
			parametrosVentas = parametrosVentasDao.get().findByCodigo();
		}
		return parametrosVentas;
	}

	/** #jcalvo. fecha:16/06/2017. */
	public void cargarParametrosTouch(String codcia, String codinv) {
		if (parametrosTouch == null) {
			parametrosTouch = parametrosTouchDao.get().findByInventario(codcia,
					codinv);
		}
	}

	/** ------------------------------------------------------------------ */
	public List<BigDecimal> obtenerTeclado(String tipo) {
		if (tipo.equals("M")) {
			if (montos.get() == null)
				montos.set(tecladoDao.get().findByTipo(tipo).get());

			return montos.get();
		} else if (tipo.equals("C")) {
			if (cantidades.get() == null)
				cantidades.set(tecladoDao.get().findByTipo(tipo).get());

			return cantidades.get();
		}

		return null;
	}

	// ---------------------------------------------------------------------------------
	public void iniciarFactura() {

		if (getFactura().getCodFacTmp() == null) {
			List<Object[]> ito;
			ito = facturaDao.get().traerImpuestoVenta();
			ito.forEach((b) -> {
				codIto = ((String) b[0]);
				porIto = ((BigDecimal) b[1]);
			});
			getFactura().getId().setCodCia(Parametros.codcia); // FAC_CODCIA
			getFactura().setCodInv(Parametros.codinv); // FAC_CODINV
			getFactura().setCodBod(obtenerBodega(null)); // FAC_CODBOD
			getFactura().getId().setTipMov("F"); // FAC_TIPMOV
			getFactura().setCodTfa("FCO"); // FAC_CODTFA
			getFactura().setNegOri(Parametros.codinv);
			getFactura().setFecha(
					Date.from(Instant.from(((LocalDate) ApplicationContext
							.getInstance().getRegisteredObject("fechaTra"))
							.atStartOfDay(ZoneId.systemDefault())))); // FAC_FECHA
			getFactura().setFecVen(
					Date.from(Instant.from(((LocalDate) ApplicationContext
							.getInstance().getRegisteredObject("fechaTra"))
							.atStartOfDay(ZoneId.systemDefault())))); // FAC_FECVEN
			getFactura().setCodTfa(parametrosVentas.get().getCodTfaCon()); // FAC_CODTFA
			getFactura().setCodMon("C"); // FAC_CODMON
			getFactura().setCodPre(parametrosInventario.get().getTprIni()); // FAC_CODPRE
			getFactura().setCodIto1(codIto); // FAC_CODITO1
			getFactura().setPorIto1(porIto); // FAC_PROITO1

			getFactura()
					.setDtoDesImp(parametrosInventario.get().getDtoDesImp());

			getFactura().getMovimiento().getId().setCodCia(Parametros.codcia);
			getFactura().getMovimiento().getId().setCodInv(Parametros.codinv);
			getFactura().getMovimiento().setTipMovGen("F");
			getFactura().getMovimiento().setCodEst(Parametros.codest);
			getFactura().getMovimiento().setFechaTran(
					Date.from(Instant.from(((LocalDate) ApplicationContext
							.getInstance().getRegisteredObject("fechaTra"))
							.atStartOfDay(ZoneId.systemDefault()))));
			getFactura().getMovimiento().setTipCam(BigDecimal.ONE);
			getFactura().setCajAut(
					((String) ApplicationContext.getInstance()
							.getRegisteredObject("usuEncargado")));
			getFactura().setCodFacTmp(obtenerCodTemp());
		}
	}

	public void continuarFactura() {
		getFactura().setHecPor(
				((String) ApplicationContext.getInstance().getRegisteredObject(
						"usuActual")));
		getFactura().getMovimiento().setCodCaj(
				((String) ApplicationContext.getInstance().getRegisteredObject(
						"usuActual")));
	}

	public void cambiarCodTfa(String Tfa) {
		if (Tfa.equals("C")) {
			getFactura().setCodTfa(parametrosVentas.get().getCodTfaCon());
			fc.habilitarBotonCredito(false);
		} else if (Tfa.equals("R")) {
			getFactura().setCodTfa(parametrosVentas.get().getCodTfaCre());

		}
		if (codCliente.get() != null && codCliente.get().length() == 7)
			cargarCliente(codCliente.get());
	}

	public Resultado<List<Factura>> obtenerFacturasFechas(Date fecIni,
			Date fecFin) {
		return facturaDao.get().getByFechas(fecIni, fecFin);
	}

	public Resultado<String> salirOpenTouch() {
		return facturaDao.get().salirApp();
	}

	public List<Autorizado> obtenerAutorizados(String codCli) {
		return facturaDao.get().traerAutorizadosCredito(codCli);
	}

	// ---------------------------------------------------------------------------------

	public Boolean reversarFormasPago(
			ObservableList<DetFormaPago> detallesFormaPago, String obs) {
		/** #jcalvo. fecha:28/06/2017. */
		detallesFormaPago.clear();

		return true;
	}

	// ---------------------------------------------------------------------------------

	private void cargarCliente(String codCliente) {
		Resultado<Cliente> res = obtenerCliente(codCliente);
		if (!res.getResultado().equals(TipoResultado.ERROR)) {

			// Descripción del cliente
			if (res.get().getDescripcion() != null) {
				desCliente.set(res.get().getDescripcion());
			} else {
				desCliente.set(null);
			}

			Resultado<Map<String, Object>> dat = obtenerDatosCliente(codCliente);
			if (!dat.getResultado().equals(TipoResultado.ERROR)) {

				// Estado del crédito
				if (((String) dat.get().get("estado")) != null) {
					estCredito
							.set("Est: " + ((String) dat.get().get("estado")));
				} else {
					estCredito.set(null);
				}

				// Monto disponible del crédito
				if (((BigDecimal) dat.get().get("disponible")) != null) {
					Parametros.formatNum.applyPattern("###,###,##0.00");
					disCredito.set("Disp: "
							+ Parametros.formatNum.format(((BigDecimal) dat
									.get().get("disponible"))));
					Parametros.formatNum.applyPattern("###");
				} else {
					disCredito.set(null);
				}

				// Fecha de vencimiento del crédito
				if ((Date) (dat.get().get("vencimiento")) != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						venCredito.set("Venc: "
								+ sdf.format(((Date) dat.get().get(
										"vencimiento"))));
					} catch (Exception e) {
						fc.mostrarMensaje("Error al covertir fechas",
								e.getMessage());
					}
				} else {
					venCredito.set(null);
				}

				// Plazo de días del cliente
				if (dat.get().get("diascredito") != null
						&& ((BigDecimal) dat.get().get("diascredito"))
								.compareTo(BigDecimal.ZERO) == 1) {
					diasPlazo = (BigDecimal) dat.get().get("diascredito");
				}

				if (getFactura().getCodTfa().equals(
						parametrosVentas.get().getCodTfaCre())) {
					// Verificar si el cliente de crédito tiene autorizados
					auts = obtenerAutorizados(codCliente);
					if (auts != null && !auts.isEmpty()) {
						try {
							fc.mostrarMensajeInfo(
									"Información de crédito",
									"Existen autorizados para cliente ["
											+ codCliente
											+ "]. Favor verifique datos adicionales del crédito.");
						} catch (Exception ex) {

						}
					}
					fc.habilitarBotonCredito(true);
				}

				BigDecimal porDto = numCero;

				/**/
				Boolean aplicarDescuento = true;

				if (getFactura().getMovimiento().getDetFormaPago() != null) {
					for (DetFormaPago dfp : getFactura().getMovimiento()
							.getDetFormaPago())
						if (dfp.getFormaPago().getDtoCli().equals("N"))
							aplicarDescuento = false;
				}

				if (aplicarDescuento) {
					if (((BigDecimal) dat.get().get("pordescuento")) != null) {
						porDto = ((BigDecimal) dat.get().get("pordescuento"));
					}

					getFactura().setCodDto2(
							((String) dat.get().get("coddescuento")));

					if (porDto.compareTo(numCero) == 1) {
						getFactura().setCodDto1("C"); // FAC_CODDTO1
					}
					getFactura().setIndDtoGen("S");
				} else {
					getFactura().setCodDto2(null);
					getFactura().setCodDto1(null);
				}
				/**/

				getFactura().setCodCli(codCliente); // FAC_CODCLI
				getFactura().setDesCli(getDesCliente()); // FAC_DESCLI
				getFactura().setEstadoHh(
						((String) dat.get().get("aplbonificacion"))); // FAC_ESTADOHH

				calculaItoDtoMto(codCliente, porDto);
			} else {
				if (getPantalla().equals("Factura"))
					fc.mostrarMensaje("Error al obtener los datos del cliente",
							dat.getMensaje());
				else
					cc.mostrarMensaje("Error al obtener los datos del cliente",
							dat.getMensaje());
			}

		} else {
			if (getPantalla().equals("Factura"))
				fc.mostrarMensaje("Error al obtener los datos del cliente",
						res.getMensaje());
			else
				cc.mostrarMensaje("Error al obtener los datos del cliente",
						res.getMensaje());
		}
	}

	// ---------------------------------------------------------------------------------
	public Resultado<BigDecimal> obtenerPrecio(Articulo articulo) {
		df.beginTransaction();
		Resultado<BigDecimal> resultado = articuloDao.get()
				.obtenerPrecioArticulo(parametrosInventario.get().getTprIni(),
						articulo.getId().getCodigo(), articulo.getCodUni(),
						articulo.getEsMasa());
		df.commitTransaction();
		return resultado;
	}

	public String obtenerBodega(Articulo articulo) {
		String bodega = null;
		if (articulo != null && articulo.getCodBodVen() != null) {
			bodega = articulo.getCodBodVen();
		} else if (Parametros.codbod != null) {
			bodega = Parametros.codbod;
		} else if (parametrosInventario.get().getCodBodFac() != null) {
			bodega = parametrosInventario.get().getCodBodFac();
		}
		return bodega;
	}

	public Map<String, Object> validarArticuloBloqueado(Articulo articulo) {

		df.beginTransaction();
		Map<String, Object> res = new HashMap<String, Object>();
		Resultado<String> resultado = articuloDao.get()
				.validarArticuloBloqueado(articulo);

		res.put("estado",
				!resultado.getResultado().equals(TipoResultado.SUCCESS));
		res.put("mensaje", resultado.getMensaje());
		df.commitTransaction();
		return res;
	}

	public Map<String, String> validarExistenciaArticulo(Articulo articulo,
			BigDecimal cantidad) {

		Map<String, String> res = new HashMap<String, String>();
		if (cantidad.compareTo(numCero) == 1) {

			df.beginTransaction();
			Resultado<String> resultado = articuloDao.get()
					.validarExistenciaArticulo(articulo, cantidad);
			res.put("tipval", resultado.get());
			res.put("mensaje", resultado.getMensaje());
			if (resultado.getResultado().equals(TipoResultado.ERROR)) {
				res.put("estado", "ERROR");
			} else {
				res.put("estado", "EXITO");
			}
			df.commitTransaction();

		} else {
			res.put("estado", "EXITO");
		}
		return res;
	}

	public Resultado<Articulo> obtenerImpuestoVenta(String codigo) {
		Resultado<Articulo> resultado = new Resultado<>();
		df.beginTransaction();
		resultado = articuloDao.get().traerImpuestoVenta(codigo);
		df.commitTransaction();
		return resultado;
	}

	public Resultado<Articulo> obtenerArticulo(String codigo) {
		Resultado<Articulo> resArticulo = new Resultado<>();
		Resultado<Articulo> resItoVta = new Resultado<>();
		Resultado<BigDecimal> resPrecio = new Resultado<>();

		df.beginTransaction();

		resArticulo = articuloDao.get().findByCodigo(codigo);

		if (!resArticulo.resultado.equals(TipoResultado.ERROR)) {
			resPrecio = obtenerPrecio(resArticulo.get());
			if (!resPrecio.resultado.equals(TipoResultado.ERROR)) {

				resArticulo.get().setPreArt(resPrecio.get());
				resArticulo.get()
						.setCodBodFac(obtenerBodega(resArticulo.get()));

				resItoVta = obtenerImpuestoVenta(resArticulo.get().getId()
						.getCodigo());
				if (!resItoVta.resultado.equals(TipoResultado.ERROR)) {
					resArticulo.get().setPorIto(resItoVta.get().getPorIto());
				} else {
					resArticulo.setMensaje(resItoVta.getMensaje());
				}
			} else {
				resArticulo.setMensaje(resPrecio.getMensaje());
			}
		}
		df.commitTransaction();

		return resArticulo;
	}

	public Resultado<Articulo> obtenerArticulo(Articulo articulo,
			BigDecimal cantidad, String tipFac) {
		Resultado<Articulo> resultado = new Resultado<>();

		if (cantidad.compareTo(numCero) == 1) {
			if (tipFac != null) {
				df.beginTransaction();

				resultado = articuloDao.get().traerArticuloLinea(articulo,
						cantidad, tipFac);

				df.commitTransaction();
			} else {
				resultado.setResultado(TipoResultado.ERROR);
				resultado.setMensaje("Debe definir el tipo de factura.");
				resultado.set(articulo);
				resultado.get().setTipVal("E");
			}
		} else {
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje("La cantidad a vender debe ser mayor a 0.");
			resultado.set(articulo);
			resultado.get().setTipVal("E");
		}
		return resultado;
	}

	public Resultado<Articulo> obtenerDescuento(Articulo articulo,
			BigDecimal cantidad, String tipFac) {

		Resultado<Articulo> resultado = new Resultado<>();

		if (cantidad.compareTo(numCero) == 1) {
			if (tipFac != null) {
				df.beginTransaction();
				resultado = articuloDao.get().traerDescuentoArticulo(articulo,
						cantidad, tipFac);

				df.commitTransaction();
			} else {
				resultado.setResultado(TipoResultado.ERROR);
				resultado.setMensaje("Debe definir el tipo de factura.");
				resultado.set(articulo);
				resultado.get().setTipVal("E");
			}
		} else {
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje("La cantidad a vender debe ser mayor a 0.");
			resultado.set(articulo);
			resultado.get().setTipVal("E");
		}

		return resultado;
	}

	public Resultado<FormaPago> obtenerFormaPago(String codigoFp) {

		Resultado<FormaPago> fp = new Resultado<>();

		fp = formaPagoDao.get().findByCodigo(codigoFp);

		return fp;
	}

	public String obtenerCodTemp() {
		return facturaDao.get().traerCodigoTemp();
	}

	public String inactivarApertura() {
		return facturaDao.get().inactivarApertura();
	}

	// ---------------------------------------------------------------------------------

	public Resultado<Cliente> obtenerCliente(String codigo) {
		return clienteDao.get().findByCodigo(codigo);
	}

	public Resultado<Map<String, Object>> obtenerDatosCliente(String codigo) {

		Boolean facturaCredito = false;
		if (getFactura().getCodTfa().equals(
				getParametrosVentas().getCodTfaCre()))
			facturaCredito = true;

		df.beginTransaction();
		Resultado<Map<String, Object>> cliente = clienteDao.get()
				.obtenerDatosCliente(codigo, facturaCredito);
		df.commitTransaction();
		return cliente;
	}

	public Resultado<List<Cliente>> obtenerClienteDescripcion(String descripcion) {
		return clienteDao.get().obtenerDatosClienteDes(descripcion);
	}

	// ---------------------------------------------------------------------------------
	public Map<String, Object> crearLineaArticulo(Articulo articulo) {

		Resultado<Articulo> art = new Resultado<>();
		Map<String, Object> res = new HashMap<String, Object>();
		DetFactura detFac = new DetFactura();

		art = obtenerArticulo(articulo.getId().getCodigo());

		res.put("resultado", art.getResultado());
		res.put("mensaje", art.getMensaje());

		if (!art.getResultado().equals(TipoResultado.ERROR)) {
			Map<String, Object> val = validarArticuloBloqueado(art.get());
			if ((boolean) val.get("estado")) {
				res.put("resultado", TipoResultado.ERROR);
				res.put("mensaje", (String) val.get("mensaje"));
			} else {
				detFac.getId().setCodCia(Parametros.codcia); // DFA_CODCIA
				detFac.getId().setTipMov("F"); // DFA_TIPMOV

				detFac.setOrden(numCero); // DFA_ORDEN
				detFac.setCodInv(Parametros.codinv); // DFA_CODINV
				detFac.setCodArt(articulo.getId().getCodigo()); // DFA_CODART
				detFac.setCodTpr(null); // DFA_CODTPR
				detFac.setOferta(numCero); // DFA_OFERTA
				detFac.setResOfeArt(numCero); // DFA_RESOFEART
				detFac.setCodUniMed(art.get().getCodUniFac()); // DFA_CODUNIMED
				detFac.setCodBar(art.get().getCodBarra()); // DFA_CODBAR
				detFac.setPrecio(art.get().getPreArt()); // DFA_PRECIO
				detFac.setCodBod(art.get().getCodBodFac()); // DFA_CODBOD
				detFac.setTotal(numCero); // DFA_TOTAL
				detFac.setCantidad(numCero); // DFA_CAN
				detFac.setTipCap(art.get().getTipCap()); // DFA_TIPCAP
				detFac.setPorIto1(art.get().getPorIto()); // DFA_PORITO1
				detFac.setApdTog(art.get().getAplDescGen()); // DFA_APDTOG
				detFac.setDtoDesImp(parametrosInventario.get().getDtoDesImp());
				detFac.setIncItoAfePre(parametrosInventario.get()
						.getIncItoAfePre());
				detFac.setArticulo(art.get());
				res.put("detalle", detFac);

				if (getDetalle() == null || getDetalle().size() == 0) {
					getFactura().setFecHorIni(
							parametrosInventarioDao.get().traerFecHorServidor()
									.get());
				}
			}
		}

		return res;
	}

	public void cambiarCantidadDetalle(Articulo articulo, BigDecimal cantidad,
			String tipFac, Integer indice, String metIng) {
		Resultado<Articulo> resultado = obtenerDescuento(articulo, cantidad,
				tipFac);
		getDetalle().get(indice).setTipDto(resultado.get().getTipDto()); // DFA_TIPDTO
		getDetalle().get(indice).setCodDto(resultado.get().getCodDto()); // DFA_CODDTO
		getDetalle().get(indice).setCodGru(resultado.get().getCodGru()); // DFA_CODGRU
		getDetalle().get(indice).setCodRdto(resultado.get().getCodRan()); // DFA_CODRDTO
		getDetalle().get(indice).setPorDesc(resultado.get().getDtoLin()); // DFA_PORDESC
		getDetalle().get(indice).setMetIngreso(metIng);
		getDetalle().get(indice).setCantidad(cantidad); // DFA_CAN
	}

	public void crearLineaCantidad(Articulo articulo, BigDecimal cantidad,
			String tipFac, Integer indice, BigDecimal total, String metIng,
			Boolean bloquear) {
		getDetalle().get(indice).setTotal(total); // DFA_TOTAL
		getDetalle().get(indice).setBloqueado(bloquear); // Bloquear el detalle
		cambiarCantidadDetalle(articulo, cantidad, tipFac, indice, metIng);
		calcularTotalGeneral();

		if (bloquear) {
			if (!generarMovimientoDispensado(indice)) {
				getDetalle().get(indice).setTotal(BigDecimal.ZERO); // DFA_TOTAL
				getDetalle().get(indice).setBloqueado(false); // Bloquear el
																// detalle
				cambiarCantidadDetalle(articulo, BigDecimal.ZERO, tipFac,
						indice, metIng);
				calcularTotalGeneral();
			}
		}
	}

	public void agregarDetalle(Articulo articulo) {
		DetFactura detalleFactura = null;
		Map<String, Object> resultado = crearLineaArticulo(articulo);

		Resultado<Articulo> art = new Resultado<>();
		Map<String, Object> res = new HashMap<String, Object>();

		if (!((TipoResultado) resultado.get("resultado"))
				.equals(TipoResultado.ERROR)) {
			detalleFactura = (DetFactura) resultado.get("detalle");

			res.put("resultado", art.getResultado());
			res.put("mensaje", art.getMensaje());

			getDetalle().add(0, detalleFactura);
		} else {
			res.put("resultado", TipoResultado.ERROR);
			res.put("mensaje", (String) resultado.get("mensaje"));
		}
	}

	public Boolean generarMovimientoDispensado(Integer indice) {
		Dispensado dispensado = new Dispensado();
		dispensado.setCantidad(getDetalle().get(indice).getCantidad());
		dispensado.setCodigoArticulo(getDetalle().get(indice).getArticulo()
				.getId().getCodigo());
		dispensado.setCodigoBodega(getDetalle().get(indice).getArticulo()
				.getCodBodFac());
		dispensado.setCodigoCajero((String) ApplicationContext.getInstance()
				.getRegisteredObject("usuActual"));
		dispensado.setCodigoUnidad(getDetalle().get(indice).getArticulo()
				.getCodUni());
		dispensado.setPrecio(getDetalle().get(indice).getPrecio());
		dispensado.setTotal(getDetalle().get(indice).getTotal());
		dispensado.setMonItoTot((getDetalle().get(indice).getMonItoTot1()));
		dispensado.setFechaTransaccional(Date.from(Instant
				.from(((LocalDate) ApplicationContext.getInstance()
						.getRegisteredObject("fechaTra")).atStartOfDay(ZoneId
						.systemDefault()))));
		dispensado.setFecha(parametrosInventarioDao.get().traerFecHorServidor()
				.get());

		Resultado<Dispensado> procesar = dispensadoDao.get()
				.procesarDispensado(dispensado, "A");

		if (procesar.getResultado().equals(TipoResultado.SUCCESS)) {
			getDetalle().get(indice).setCodDisp(dispensado.getId().getCodigo());
			dispensados.add(dispensado);
			dispensadoDao.get().imprimirDispensado(
					dispensado.getId().getCodigo());
		} else {
			fc.mostrarMensaje("Generar dispensado", procesar.getMensaje());
			return false;
		}

		return true;
	}

	public Boolean anularMovimientoDispensado(Dispensado dispensado) {
		dispensado.setNulo("S");
		dispensado.setUsuarioAnula((String) ApplicationContext.getInstance()
				.getRegisteredObject("usuActual"));
		dispensado.setFechaNulo(parametrosInventarioDao.get()
				.traerFecHorServidor().get());

		Resultado<Dispensado> procesar = dispensadoDao.get()
				.procesarDispensado(dispensado, "N");

		if (procesar.getResultado().equals(TipoResultado.SUCCESS)) {
			dispensados.remove(dispensado);
			return true;
		} else {
			dispensado = procesar.get();
			fc.mostrarMensaje("Anular dispensado", procesar.getMensaje());
			return false;
		}
	}

	public void registrarBitacoraAnulacionDispensado(
			BitacoraAutorizacion bitacoraAutorizacion) {
		Resultado<BitacoraAutorizacion> resultado = bitacoraAutorizacionDao
				.get().save(bitacoraAutorizacion);

		if (resultado.getResultado().equals(TipoResultado.ERROR))
			System.out.println(resultado.getMensaje());
	}

	public void calcularTotalGeneral() {
		getFactura().setSubTot(numCero);
		totalLinea = numCero;
		for (int i = 0; i < getDetalle().size(); i++) {
			totalLinea = totalLinea.add(getDetalle().get(i).getTotal());
		}
		getFactura().setSubTot(totalLinea);
	}

	// ---------------------------------------------------------------------------------
	public void obtenerBancos() {

		if (codBan == null || codBan.size() == 0) {
			List<Object[]> bncs;
			bncs = formaPagoDao.get().traerBancos();

			codBan.clear();
			desBan.clear();
			bncs.forEach((b) -> {
				codBan.add((String) b[0]);
				desBan.add((String) b[1]);
			});
		}
	}

	public boolean obtenerBonifActiva() {
		return formaPagoDao.get().traerCalcBonActivo() != null;
	}

	public Map<String, Object> crearLineaFormaPago(FormaPago formaPago) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			Resultado<FormaPago> fp = formaPagoDao.get().findByCodigo(
					formaPago.getId().getCodigo());

			res.put("validacion", "N");
			res.put("resultado", TipoResultado.SUCCESS);

			if (!fp.getResultado().equals(TipoResultado.ERROR)) {
				DetFormaPago dfp = new DetFormaPago();

				if (fp.get().getDaVuel().equals("N")
						&& formaPago.getId().getCodigo() != parametrosPos.get()
								.getTpagBon()) {
				}

				if (fp.get().getMixta().equals("N")
						&& getDetalleFormaPago().size() == 1) {
					res.put("resultado", TipoResultado.ERROR);
					res.put("mensaje",
							"La forma de pago ["
									+ formaPago.getId().getCodigo()
									+ "] no se puede combinar con otras formas de pago.");
					return res;
				}

				if (fp.get().getPagServ().equals("N")
						&& getFactura().getTotIng().compareTo(numCero) == 1) {
					res.put("resultado", TipoResultado.ERROR);
					res.put("mensaje",
							"La forma de pago ["
									+ formaPago.getId().getCodigo()
									+ "] no se puede utilizar para el pago de servicios.");
					return res;
				}

				boolean tpagBon = formaPago.equals(parametrosPos.get()
						.getTpagBon());

				if (tpagBon) {
					if (getFactura().getCodCli() == null) {
						res.put("resultado", TipoResultado.ERROR);
						res.put("mensaje", "Debe especificar el cliente.");
						return res;
					}
					if (!obtenerBonifActiva()) {
						res.put("resultado", TipoResultado.ERROR);
						res.put("mensaje",
								"No se permite utilizar la forma de pago ['"
										+ formaPago.getId().getCodigo()
										+ "'] porque no hay un cálculo de bonificación activo.");
						return res;
					}
				}

				String aplDtoCli = "S";

				for (int i = 0; i < getDetalleFormaPago().size(); i++) {
					if (tpagBon
							&& formaPago
									.getId()
									.getCodigo()
									.equals(getDetalleFormaPago().get(i)
											.getFormaPago().getId().getCodigo())) {
						res.put("resultado", TipoResultado.ERROR);
						res.put("mensaje",
								"Solo puede ingresar la forma de pago ['"
										+ formaPago.getId().getCodigo()
										+ "'] una vez.");
						break;
					}
					if (!getDetalleFormaPago().get(i).getFormaPago()
							.getDtoCli().equals("S")
							&& aplDtoCli.equals("S")) {
						aplDtoCli = "N";
					}
				}

				if (res.get("resultado").equals(TipoResultado.ERROR)) {
					return res;
				}

				if (aplDtoCli.equals("S") && fp.get().getDtoCli().equals("N")) {
					if (getFactura().getPorDto1().compareTo(numCero) == 1
							&& getFactura().getCodDto1().equals("C")) {

						Map<String, Object> resFp = new HashMap<String, Object>();

						resFp = cc
								.mostrarMensaje(
										"Agregar forma de pago",
										"La forma de pago ['"
												+ formaPago.getId().getCodigo()
												+ "'] no permite descuentos por cliente.  El descuento por cliente será eliminado, ¿Desea continuar?",
										"P");

						if (((String) resFp.get("resultado")).equals("YES")) {
							quitarDescuento();
							aplDtoCli = "N";
						} else {
							res.put("resultado", TipoResultado.ERROR);
							res.put("mensaje", "");
							res.put("validacion", "P");
							return res;
						}

					}
				}

				dfp.getId().setCodCia(Parametros.codcia); // DFP_CODCIA
				dfp.getId().setTipMov("F"); // DFP_TIPMOV
				dfp.getId().setCodInv(Parametros.codinv); // DFP_CODINV

				// Traer los datos del pago, si aplica
				obtenerBancos();
				Map<String, Object> datPago = null;
				if (formaPago.getNumDoc().equals("S")) {
					datPago = cc.verificarFormaPago(formaPago, desBan);
				}
				if (datPago != null) {
					dfp.setNumDoc((String) datPago.get("documento")); // DFP_NUMDOC
					dfp.setEmisor((String) datPago.get("emisor")); // DFP_EMISOR
					dfp.setBenef((String) datPago.get("beneficiario")); // DFP_BENEF
					dfp.setTelefono((String) datPago.get("telefono")); // DFP_TELEF
					dfp.setNumCta((String) datPago.get("numtar")); // DFP_NUMCTA
					if (formaPago.getBanco().equals("S")
							&& datPago.get("banco") != null
							&& ((int) datPago.get("banco")) != -1)
						dfp.setBanco(codBan.get((int) datPago.get("banco"))); // DFP_BANCO
				}

				dfp.setTipCam(fp.get().getTipCam()); // DFP_TIPCAM
				// DFP_INDICE
				dfp.setMtoMon(numCero); // DFP_MTOMON
				dfp.setAplDtoCli(aplDtoCli);
				if (fp.get().getMedCap().equals("S")) {
					dfp.setMedCap("D"); // DFP_MEDCAP
				}

				if (datPago != null && (Boolean) datPago.get("error")) {
					res.put("resultado", TipoResultado.ERROR);
					res.put("mensaje",
							"Se agregara la forma de pago con datos incompletos.");
				} else {
					res.put("resultado", fp.getResultado());
				}

				if (fp.get().getDaVuel().equals("S"))
					dfp.setMonPag(numCero); // DFP_MONPAG
				else {
					dfp.setMonPag(new BigDecimal(getTotPag()
							.replaceAll(",", "")));
					setTotRec(Parametros.formatMon
							.format((new BigDecimal(getTotRec().replaceAll(",",
									"")).add(new BigDecimal(getTotPag()
									.replaceAll(",", ""))))));
					setTotPag("0.00");
				}

				dfp.setFormaPago(fp.get());
				res.put("detalle", dfp);

			} else {
				res.put("resultado", fp.getResultado());
				res.put("mensaje", fp.getMensaje());
			}

			return res;

		} catch (Exception ex) {
			System.out
					.print("\n******************************************************************\n");
			System.out
					.print("\n******************************************************************\n");
			ex.printStackTrace();
			System.out
					.print("\n******************************************************************\n");
			System.out
					.print("\n******************************************************************\n");
			res.put("resultado", TipoResultado.ERROR);
			res.put("mensaje", ex.getMessage());
			return res;
		}
	}

	public void quitarDescuento() {
		totPag.set(Parametros.formatMon.format(new BigDecimal(totPag.get()
				.replaceAll(",", "")).add(new BigDecimal(totDesc.get()
				.replaceAll(",", "")))));
		porDto.set("0.00%");
		totDesc.set("0.00");
		getFactura().setPorDto1(numCero);
		getFactura().setIndDtoGen("N");
	}

	public void calculaItoDtoMto(String codCli, BigDecimal porDto) {

		String aplDtoCli = "S";
		// Verificar si existe una forma de pago que aplique
		// descuento por cliente
		for (int i = 0; i < getDetalleFormaPago().size(); i++) {
			if (getDetalleFormaPago().get(i).getAplDtoCli().equals("N")) {
				aplDtoCli = "N";
				break;
			}
		}

		if (porDto.compareTo(numCero) == 0 && aplDtoCli.equals("S")) {

			Resultado<Map<String, Object>> dat = obtenerDatosCliente(codCli);

			if (!dat.getResultado().equals(TipoResultado.ERROR)) {
				if (((BigDecimal) dat.get().get("pordescuento")) != null) {
					porDto = ((BigDecimal) dat.get().get("pordescuento"));
				}
			} else {
				codCliente.set(parametrosPos.get().getCodCliOca());
			}

		}
		// Verificar que exista un detalle de artículos para
		// calcular los montos de
		// total gravado descuento y total exento descuento
		totGravDto = numCero;
		totExenDto = numCero;
		totGrav = numCero;
		totExen = numCero;
		totItoDto = numCero;
		totalIvi = numCero;
		getDetalle().forEach(
				(d) -> {

					if (d.getArticulo().getIndGrav().equals("S")
							&& d.getArticulo().getAplDescGen().equals("S")) {

						if (d.getMetIngreso().equals("M"))
							totGravDto = totGravDto.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(0, RoundingMode.HALF_EVEN));
						else
							totGravDto = totGravDto.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(2, RoundingMode.HALF_EVEN));
					}

					if (d.getArticulo().getIndGrav().equals("N")
							&& d.getArticulo().getAplDescGen().equals("S")) {

						if (d.getMetIngreso().equals("M"))
							totExenDto = totExenDto.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(0, RoundingMode.HALF_EVEN));
						else
							totExenDto = totExenDto.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(2, RoundingMode.HALF_EVEN));
					}

					if (d.getArticulo().getIndGrav().equals("S")
							&& d.getArticulo().getAplDescGen().equals("N")) {
						if (d.getMetIngreso().equals("M"))
							totGrav = totGrav.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(0, RoundingMode.HALF_EVEN));
						else
							totGrav = totGrav.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(2, RoundingMode.HALF_EVEN));
					}

					if (d.getArticulo().getIndGrav().equals("N")
							&& d.getArticulo().getAplDescGen().equals("N")) {
						if (d.getMetIngreso().equals("M"))
							totExen = totExen.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(0, RoundingMode.HALF_EVEN));
						else
							totExen = totExen.add(d.getCantidad()
									.multiply(d.getPreNet())
									.setScale(2, RoundingMode.HALF_EVEN));
					}

					if (d.getArticulo().getIndGrav().equals("S")
							&& d.getArticulo().getAplDescGen().equals("S")) {
						totItoDto = totItoDto.add(d.getCantidad().multiply(

						d.getMonItoUni1()));
					}

					if (d.getMetIngreso().equals("M"))
						totalIvi = totalIvi.add(d.getCantidad()
								.multiply(d.getMonItoUni1())
								.setScale(2, RoundingMode.HALF_EVEN));
					else
						totalIvi = totalIvi.add(d.getCantidad().multiply(
								d.getMonItoUni1()));
				});
		if (porDto.compareTo(numCero) == 1
				&& aplDtoCli.equals("S")
				&& (totGravDto.compareTo(numCero) == 1 || totExenDto
						.compareTo(numCero) == 1)) {
			getFactura().setVtaGraDto(totGravDto); // FAC_VTAGRADTO
			getFactura().setVtaExeDto(totExenDto); // FAC_VTAEXEDTO

			getFactura().setVtaGra(totGrav.add(totGravDto)); // FAC_VTAGRA
			getFactura().setVtaExe(totExen.add(totExenDto)); // FAC_VTAEXE
			getFactura().setItoVtaDto(totItoDto); // FAC_ITOVTADTO
			getFactura().setTotIto1(totalIvi); // FAC_TOTITO1
			getFactura().setSubToti(totalIvi); // FAC_SUBTOTI
			getFactura().setPorDto1(porDto); // FAC_PORDTO1
		} else {
			getFactura().setVtaGraDto(totGravDto); // FAC_VTAGRADTO
			getFactura().setVtaExeDto(totExenDto); // FAC_VTAEXEDTO
			getFactura().setVtaGra(totGrav.add(totGravDto)); // FAC_VTAGRA
			getFactura().setVtaExe(totExen.add(totExenDto)); // FAC_VTAEXE
			getFactura().setItoVtaDto(totItoDto); // FAC_ITOVTADTO
			getFactura().setTotIto1(totalIvi); // FAC_TOTITO1
			getFactura().setSubToti(totalIvi); // FAC_SUBTOTI
			getFactura().setPorDto1(porDto); // FAC_PORDTO1
			// esanchez scre004257 04/05/2016
			// FAC_TOTAL
		}

	}

	public Boolean imprimirFactura() {
		Boolean imprimir = false;
		Boolean debeImprimir = true;
		Boolean copia = false;

		if (getFactura().getCodTfa().equals(
				parametrosVentas.get().getCodTfaCon())
				&& !imprimeComoContado.get() == true) {

			debeImprimir = false;
		}

		for (int j = 0; j < factura.getMovimiento().getDetFormaPago().size(); j++) {
			if (factura.getMovimiento().getDetFormaPago().get(j).getFormaPago()
					.getImpCop().equals("S")) {
				copia = true;
			}
		}

		if (debeImprimir) {
			if (copia) {
				imprimir = facturaDao.get().imprimirFactura(
						factura.getId().getCodigo(),
						factura.getId().getTipMov(), false, false, false, true,
						factura.getCodTfa(),
						getParametrosVentas().getCodTfaCon(),
						getParametrosVentas().getCodTfaCre());
				imprimir = facturaDao.get().imprimirFactura(
						factura.getId().getCodigo(),
						factura.getId().getTipMov(), false, false, false, true,
						factura.getCodTfa(),
						getParametrosVentas().getCodTfaCon(),
						getParametrosVentas().getCodTfaCre());
			} else {
				Boolean comoContado = false;
				if (parametrosVentas.get().getCodTfaCon()
						.equals(factura.getCodTfa()))
					comoContado = !imprimeComoContado.get();
				else
					comoContado = imprimeComoContado.get();

				imprimir = facturaDao.get().imprimirFactura(
						factura.getId().getCodigo(),
						factura.getId().getTipMov(), true, comoContado, false,
						false, factura.getCodTfa(),
						getParametrosVentas().getCodTfaCon(),
						getParametrosVentas().getCodTfaCre());

				if (parametrosVentas.get().getCodTfaCre()
						.equals(factura.getCodTfa())
						&& (Parametros.impdoc.equals("FCO") || Parametros.impdoc
								.equals("N")))
					imprimir = facturaDao.get().imprimirFactura(
							factura.getId().getCodigo(),
							factura.getId().getTipMov(), false, comoContado,
							false, false, factura.getCodTfa(),
							getParametrosVentas().getCodTfaCon(),
							getParametrosVentas().getCodTfaCre());
			}
		}
		return imprimir;

	}

	public Boolean reimprimirFactura(Factura factura) {
		Boolean imprimir = false;
		Boolean copia = false;

		for (int j = 0; j < factura.getMovimiento().getDetFormaPago().size(); j++) {
			if (factura.getMovimiento().getDetFormaPago().get(j).getFormaPago()
					.getImpCop().equals("S")) {
				copia = true;
			}
		}

		if (copia) {
			imprimir = facturaDao.get().imprimirFactura(
					factura.getId().getCodigo(), "F", false, false, true, true,
					factura.getCodTfa(), getParametrosVentas().getCodTfaCon(),
					getParametrosVentas().getCodTfaCre());
			imprimir = facturaDao.get().imprimirFactura(
					factura.getId().getCodigo(), "F", false, false, true, true,
					factura.getCodTfa(), getParametrosVentas().getCodTfaCon(),
					getParametrosVentas().getCodTfaCre());
		} else {
			imprimir = facturaDao.get().imprimirFactura(
					factura.getId().getCodigo(), "F", true, false, true, false,
					factura.getCodTfa(), getParametrosVentas().getCodTfaCon(),
					getParametrosVentas().getCodTfaCre());

			if (factura.getCodTfa()
					.equals(getParametrosVentas().getCodTfaCre())
					&& (Parametros.impdoc.equals("FCO") || Parametros.impdoc
							.equals("N")))
				imprimir = facturaDao.get().imprimirFactura(
						factura.getId().getCodigo(), "F", false, false, true,
						false, factura.getCodTfa(),
						getParametrosVentas().getCodTfaCon(),
						getParametrosVentas().getCodTfaCre());
		}
		return imprimir;
	}

	public boolean reimprimirVoucher(DetFormaPago dfp) {
		boolean reimp = false;
		Map<String, Object> res = facturaDao.get().traerDatosTran(
				dfp.getTelefono());
		String numAut = null;

		if (((String) res.get("tipo")).equals("S"))
			numAut = dfp.getNumDoc();
		else
			numAut = ((String) res.get("codfac"));

		reimp = facturaDao.get().imprimirVoucher(((String) res.get("tipo")),
				true, ((String) res.get("hecpor")), dfp.getNumCta(), numAut,
				dfp.getBanco(), dfp.getNumDoc(), dfp.getMonPag(),
				dfp.getBenef(), true, false, ((String) res.get("numter")),
				((String) res.get("codcaj")), ((Date) res.get("fecha")));

		reimp = facturaDao.get().imprimirVoucher(((String) res.get("tipo")),
				true, ((String) res.get("hecpor")), dfp.getNumCta(), numAut,
				dfp.getBanco(), dfp.getNumDoc(), dfp.getMonPag(),
				dfp.getBenef(), false, false, ((String) res.get("numter")),
				((String) res.get("codcaj")), ((Date) res.get("fecha")));
		return reimp;
	}

	public Resultado<String> guardarFactura() {
		Resultado<String> resultado = new Resultado<String>();

		Resultado<LocalDate> fechaTranRes = facturaDao.get()
				.getFechaTransaccional(getParametrosInventario().getJorNor(),
						getParametrosInventario().getFinJor());

		if (fechaTranRes.getResultado() == TipoResultado.SUCCESS) {

			LocalDate fechaTranAct = (LocalDate) ApplicationContext
					.getInstance().getRegisteredObject("fechaTra");

			if (fechaTranAct.compareTo(fechaTranRes.get()) != 0) {
				Map<String, Object> resultadoConfirmacion = cc.mostrarMensaje(
						"Generar factura",
						"La factura se guardara con la fecha transaccional: "
								+ fechaTranAct.format(DateTimeFormatter
										.ofPattern("dd/MM/yyyy"))
								+ ", ¿Desea continuar?", "S");

				if (((String) resultadoConfirmacion.get(resultado))
						.compareTo("NO") == 0) {
					resultado.set("Generar factura");
					resultado.setMensaje("Operación cancelada por el usuario");
					resultado.setResultado(TipoResultado.WARNING);
					return resultado;
				}
			}

		} else {
			resultado.set("Generar factura");
			resultado.setMensaje("Error al consultar la fecha transaccional. "
					+ fechaTranRes.getMensaje());
			resultado.setResultado(TipoResultado.ERROR);
			return resultado;
		}

		if (getFactura().getCodCli().equals(getParametrosPos().getCodCliOca()))
			getFactura().setDesCli(desCliente.get());

		if (getFactura().getCodTfa().equals(
				parametrosVentas.get().getCodTfaCon())) {

			BigDecimal totalFactura = getFactura().getTotal();

			if (getFactura().getMovimientoIngreso() != null)
				totalFactura = totalFactura.add(getFactura()
						.getMovimientoIngreso().getTotFac());

			factura.getMovimiento().setCambio(
					new BigDecimal(totRec.get().replaceAll(",", ""))
							.subtract(totalFactura));
		} else {
			factura.getMovimiento().setCambio(factura.getTotal());
		}

		if (getFactura().getCodTfa().equals(
				parametrosVentas.get().getCodTfaCre())) {
			getFactura().setPlazo(diasPlazo);

			LocalDate sysdate = obtenerFechaActual().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();

			sysdate = sysdate.minusDays(1);
			Date fecVen = Date.from(Instant.from((sysdate.plusDays(diasPlazo
					.longValue())).atStartOfDay(ZoneId.systemDefault())));

			getFactura().setFecVen(fecVen);
		}

		getFactura().setConsOpet(parametrosPos.get().getOpeTfac());

		if (dispensados.size() > 0)
			factura.setDispensado(dispensados);

		getFactura().setObservacion(generarObservacionFactura(false));

		Resultado<Factura> resultadoSave = facturaDao.get().save(factura);

		if (resultadoSave.getResultado().equals(TipoResultado.SUCCESS)
				&& getFactura().getMovimientoIngreso() != null) {
			Resultado<Boolean> actualizaRMovimientos = facturaDao.get()
					.actualizarResumenMovimientos("S",
							getFactura().getMovimientoIngreso().getTotFac(),
							BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

			resultado.set("Generar factura");

			resultado.setMensaje(actualizaRMovimientos.getMensaje());
			resultado.setResultado(actualizaRMovimientos.getResultado());
		} else {
			resultado.set("Generar factura");
			resultado.setMensaje(resultadoSave.getMensaje());
			resultado.setResultado(resultadoSave.getResultado());
		}
		return resultado;
	}

	// Generar la observación de la factura
	public String generarObservacionFactura(boolean saltoBlanco) {

		String obs = "";

		if (placa.get() != null)
			obs = obs + "Placa: " + placa.get().trim() + "\n";
		else if (saltoBlanco)
			obs = obs + "Placa: " + "\n";

		if (kilometraje.get() != null)
			obs = obs + "Kilometraje: " + kilometraje.get().trim() + "\n";
		else if (saltoBlanco)
			obs = obs + "Kilometraje: " + "\n";

		if (orden.get() != null)
			obs = obs + "Orden: " + orden.get().trim() + "\n";
		else if (saltoBlanco)
			obs = obs + "Orden: " + "\n";

		if (chofer.get() != null)
			obs = obs + "Chofer/Móvil: " + chofer.get().trim() + "\n";
		else if (saltoBlanco)
			obs = obs + "Chofer/Móvil: " + "\n";

		if (observacion.get() != null)
			obs = obs + "Observación: " + observacion.get().trim() + "\n";
		else if (saltoBlanco)
			obs = obs + "Observación: " + "\n";

		if (idResponsable.get() != null)
			obs = obs + "Id Responsable: " + idResponsable.get().trim() + "\n";
		else if (saltoBlanco)
			obs = obs + "Id Responsable: " + "\n";

		if (nomResponsable.get() != null)
			obs = obs + "Nom Responsable: " + nomResponsable.get().trim();
		else if (saltoBlanco)
			obs = obs + "Nom Responsable: ";

		return obs;
	}

	public void ejecutarTecladoEnPantalla() {
		try {
			if (processTeclado == null
					|| (processTeclado != null && !processTeclado.isAlive())) {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
						Parametros.urlteclado);
				processTeclado = pb.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reiniciarFactura(boolean validarFacTmp) {
		factura = new Factura();

		codCliente = new SimpleStringProperty(null);
		desCliente = new SimpleStringProperty(null);
		estCredito = new SimpleStringProperty(null);
		disCredito = new SimpleStringProperty(null);
		venCredito = new SimpleStringProperty(null);

		placa = new SimpleStringProperty(null);
		kilometraje = new SimpleStringProperty(null);
		orden = new SimpleStringProperty(null);
		chofer = new SimpleStringProperty(null);
		observacion = new SimpleStringProperty(null);
		idResponsable = new SimpleStringProperty(null);
		nomResponsable = new SimpleStringProperty(null);
		numComanda = new SimpleStringProperty(null);
		numMesa = new SimpleStringProperty(null);
		vendedor = new SimpleStringProperty(null);

		factura.numOrdProperty().bindBidirectional(numComanda);
		factura.docProperty().bindBidirectional(numMesa);
		factura.codvenProperty().bindBidirectional(vendedor);

		totRec = new SimpleStringProperty();
		totPag = new SimpleStringProperty();

		porDto = new SimpleStringProperty("0.00");
		totDesc = new SimpleStringProperty("0.00");
		totFac = new SimpleStringProperty("0.00");
		porImpSer = new SimpleStringProperty("0.00");
		totImpSer = new SimpleStringProperty("0.00");

		totalLinea = new BigDecimal("0");
		total = new SimpleStringProperty();

		imprimeComoContado = new SimpleBooleanProperty(false);

		dispensados = FXCollections.observableArrayList();
		detalle = FXCollections.observableList(getFactura().getDetFactura());
		detalleFormaPago = FXCollections.observableList(getFactura()
				.getMovimiento().getDetFormaPago());

		autorizaPlazoCredito = false;

		agregarListener();
		//jcalvo. fecha:25/09/217.
		desmarcarDespachosCombustible();
		if (validarFacTmp) {
			FacturaTemporal ft = traerFacturaTemporal("N");
			if (ft != null) {
				cargarFacturaTemporal(ft);
			}
		}
	}

	public String getPantalla() {
		return pantalla;
	}

	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}

	public Resultado<String> verificarFPAplicaVuelto() {
		Resultado<String> resultado = new Resultado<String>();

		BigDecimal totalNoAplicaVuelto = new BigDecimal(0);
		String descripciones = "";

		for (DetFormaPago dfp : getDetalleFormaPago()) {
			if (dfp.getFormaPago().getDaVuel().equals("N")) {
				totalNoAplicaVuelto = totalNoAplicaVuelto.add(dfp.getMonPag());
				if (descripciones.isEmpty())
					descripciones = dfp.getFormaPago().getEtiqueta();
				else
					descripciones = descripciones + ", "
							+ dfp.getFormaPago().getEtiqueta();
			}
		}

		BigDecimal total = getFactura().getTotal();

		if (getFactura().getMovimientoIngreso() != null)
			total = total.add(getFactura().getMovimientoIngreso().getTotFac());

		if (total.compareTo(totalNoAplicaVuelto) >= 0) {
			resultado.set("");
			resultado.setMensaje("");
			resultado.setResultado(TipoResultado.SUCCESS);
		} else {
			resultado.set("Validar formas de pago");
			resultado
					.setMensaje("El total de la factura es menor que el monto de las formas de pago que permiten vuelto ["
							+ descripciones
							+ "], elimine las necesarias para poder continuar.");
			resultado.setResultado(TipoResultado.ERROR);
		}

		return resultado;
	}

	public Resultado<HashMap<String, Object>> reversarFormaPagoTarjeta(
			DetFormaPago detFormaPago, String obs) {
		String tipoMov = "";
		if (getParametrosPos().getCodTpagCon().equals(
				detFormaPago.getFormaPago().getId().getCodigo()))
			tipoMov = "C";
		else if (getParametrosPos().getCodTpagAv().equals(
				detFormaPago.getFormaPago().getId().getCodigo()))
			tipoMov = "S";

		return facturaDao.get().reversarPagoTarjeta(detFormaPago, tipoMov,
				getFactura().getCodFacTmp(), obs);
	}

	public Resultado<Boolean> validaVuelto() {

		Resultado<Boolean> res = new Resultado<>();

		BigDecimal maxVuelto = new BigDecimal("0");

		for (int i = 0; i < getDetalleFormaPago().size(); i++) {
			if (getDetalleFormaPago().get(i).getFormaPago().getDaVuel()
					.equals("S")) {
				if (getDetalleFormaPago().get(i).getFormaPago().getTipMto()
						.equals("A")) {
					maxVuelto = maxVuelto.add(getDetalleFormaPago().get(i)
							.getFormaPago().getMaxVuelto());
				} else {
					maxVuelto = maxVuelto.add(getDetalleFormaPago().get(i)
							.getFormaPago().getMaxVuelto()
							.multiply(getDetalleFormaPago().get(i).getMonPag())
							.divide(new BigDecimal(100)));
				}
			}
		}

		BigDecimal recibido = new BigDecimal(totRec.get().replaceAll(",", ""));

		if (recibido.subtract(getFactura().getTotal()).compareTo(maxVuelto) == 1) {
			res.set(false);
			res.setMensaje("El monto del vuelto no puede ser mayor que ["
					+ Parametros.formatMon.format(maxVuelto) + "].");
		} else {
			res.set(true);
		}
		return res;
	}

	public Resultado<Boolean> actualizarResumenMovs(BigDecimal mtoEnt,
			BigDecimal mtoSal, BigDecimal mtoDep, BigDecimal mtoOfp) {
		return facturaDao.get().actualizarResumenMovimientos("S", mtoEnt,
				mtoSal, mtoDep, mtoOfp);
	}

	public Resultado<String> obtenerTipoCajero(String cajero) {
		return facturaDao.get().traerTipoCajero(cajero);
	}

	public Resultado<String> obtenerAccsAutorizacion() {
		return facturaDao.get().traerReqAutorizacion();
	}

	public boolean requiereAut(String codAcc) {
		return ((String) ApplicationContext.getInstance().getRegisteredObject(
				"requiereAut")).contains(codAcc);
	}

	public void obtenerBodegas() {
		codBod.clear();
		desBod.clear();
		facturaDao.get().traerBodegas().forEach((b) -> {
			codBod.add((String) b[0]);
			desBod.add((String) b[1]);
		});

	}

	public boolean cambiarBodega(Articulo articulo, BigDecimal cantidad) {

		String codBodNue = articulo.getCodBodFac();

		Resultado<Articulo> art = obtenerArticulo(articulo.getId().getCodigo());
		art.get().setCodBodFac(codBodNue);

		if (!art.getResultado().equals(TipoResultado.ERROR)) {
			// Validar artículo bloqueado en la bodega
			Map<String, Object> val = validarArticuloBloqueado(art.get());
			if ((boolean) val.get("estado")) {
				fc.mostrarMensaje("Validar artículo bloqueado",
						(String) val.get("mensaje"));
				return false;
			}
		} else {
			fc.mostrarMensaje("Validar artículo bloqueado", art.getMensaje());
			return false;
		}

		if (cantidad.compareTo(numCero) == 1) {
			// Validar la existencia
			Map<String, String> exis = validarExistenciaArticulo(articulo,
					cantidad);

			if ((exis.get("tipval").equals("V"))
					&& exis.get("estado").equals("ERROR")) {

				Map<String, Object> res = fc.mostrarMensaje(
						"Validación de existencias", exis.get("mensaje"), "P");

				if (res.get("resultado").equals("NO")) {
					return false;
				}

			} else {
				if (exis.get("estado").equals("ERROR")) {
					fc.mostrarMensaje("Error", exis.get("mensaje"));
					return false;
				}
			}
		}
		return true;
	}

	public List<Prefijo> obtenerPrefijos(String desPrf) {
		return articuloDao.get().traerPrefijos(
				parametrosInventario.get().getCodTclaPrf(), desPrf);
	}

	public List<Marca> obtenerMarcas(String codVclaPrf, String desMar) {
		return articuloDao.get().traerMarcas(
				parametrosInventario.get().getCodTclaPrf(), codVclaPrf,
				parametrosInventario.get().getCodTclaMar(), desMar);
	}

	public List<Articulo> obtenerArticulos(String codVclaPrf,
			String codVclaMar, String desArt) {
		return articuloDao.get().traerArticulos(
				parametrosInventario.get().getCodTclaPrf(), codVclaPrf,
				parametrosInventario.get().getCodTclaMar(), codVclaMar, desArt);
	}

	public List<String> obtenerCodigosBarras(Articulo articulo) {
		return articuloDao.get().traerCodigosBarras(
				articulo.getId().getCodigo(), articulo.getCodUni());
	}

	public Boolean getAutorizaPlazoCredito() {
		return autorizaPlazoCredito;
	}

	public void setAutorizaPlazoCredito(Boolean autorizaPlazoCredito) {
		this.autorizaPlazoCredito = autorizaPlazoCredito;
	}

	// ---------------------------------------------------------------------------------
	public Resultado<Boolean> guardarFacturaTemporal(Factura factura) {

		FacturaTemporal ft = traerFacturaTemporal("N");
		Resultado<Boolean> resultado = new Resultado<>();
		if (ft != null) {
			Map<String, Object> res = fc
					.mostrarMensaje(
							"Guardar factura temporal",
							"¿Existe una factura temporal sin aplicar. Desea sobreescribirla?",
							"P");
			if (((String) res.get("resultado")).equals("YES")) {
				resultado = facturaTemporalDao.get().guardarFacturaTemporal(
						factura);
			}
		} else {
			resultado = facturaTemporalDao.get()
					.guardarFacturaTemporal(factura);
		}

		if (resultado.get() != null) {
			if (resultado.get()) {
				dispensados.clear();
				setDatosCredito(true);
			}
		} else {
			resultado.setResultado(TipoResultado.WARNING);
		}

		return resultado;
	}

	public FacturaTemporal traerFacturaTemporal(String estado) {
		return facturaTemporalDao.get().findById(estado).get();
	}

	public void cargarFacturaTemporal(FacturaTemporal facturaTemporal) {

		boolean seCargo = true;

		/*********************************************************************************************************************************************/
		iniciarFactura();

		factura.setCliCam(facturaTemporal.getCliCam());
		factura.setCodTfa(facturaTemporal.getCodTfa());
		factura.setHecPor(facturaTemporal.getHecPor());
		factura.setTotIto1(facturaTemporal.getTotIto1());
		factura.setCodDto1(facturaTemporal.getCodDto1());
		factura.setPorDto1(facturaTemporal.getPorDto1());
		factura.setTotDto1(facturaTemporal.getTotDto1());
		factura.setCodDto2(facturaTemporal.getCodDto2());
		factura.setSubTotd(facturaTemporal.getSubTotd());
		factura.setSubToti(facturaTemporal.getSubToti());
		factura.setTotal(facturaTemporal.getTotal());
		if (facturaTemporal.getObs() != null) {
			String obs[] = facturaTemporal.getObs().split("\n");
			placa.set(obs[0].replace("Placa: ", ""));
			kilometraje.set(obs[1].replace("Kilometraje: ", ""));
			orden.set(obs[2].replace("Orden: ", ""));
			chofer.set(obs[3].replace("Chofer/Móvil: ", ""));
			observacion.set(obs[4].replace("Observación: ", ""));
			idResponsable.set(obs[5].replace("Id Responsable: ", ""));
			nomResponsable.set(obs[6].replace("Nom Responsable: ", ""));
		}
		factura.setImpreso(facturaTemporal.getImpreso());
		factura.setNulo(facturaTemporal.getNulo());
		factura.setFecNul(facturaTemporal.getFecNul());
		factura.setAplicado(facturaTemporal.getAplicado());
		factura.setAfeSal(facturaTemporal.getAfeSal());
		factura.setAfeCre(facturaTemporal.getAfeCre());
		factura.setPrefer(facturaTemporal.getPrefer());
		factura.setPorAge(facturaTemporal.getPorAge());
		factura.setCodCxc(facturaTemporal.getCodCxc());
		factura.setPlazo(facturaTemporal.getPlazo());
		factura.setTipDocGen(facturaTemporal.getTipDocGen());
		factura.setCodDocGen(facturaTemporal.getCodDocGen());
		factura.setEstadoHh(null);
		factura.setCodSis(facturaTemporal.getCodSis());
		factura.setComPag(facturaTemporal.getComPag());
		factura.setFecHorIni(facturaTemporal.getFecHorIni());
		factura.setFecHorFin(facturaTemporal.getFecHorFin());
		factura.setUsrAnu(facturaTemporal.getUsrAnu());

		if (facturaTemporal.getCodCli() != null)
			codCliente.set(facturaTemporal.getCodCli());

		for (int i = 0; i < facturaTemporal.getDetFactura().size(); i++) {

			DetFactura df = new DetFactura();

			df.getId().setCodCia(
					facturaTemporal.getDetFactura().get(i).getId().getCodCia());
			df.getId().setTipMov(
					facturaTemporal.getDetFactura().get(i).getId().getTipMov());
			df.setCodInv(facturaTemporal.getDetFactura().get(i).getCodInv());
			df.setCodArt(facturaTemporal.getDetFactura().get(i).getCodArt());
			df.setCodBod(facturaTemporal.getDetFactura().get(i).getCodBod());
			df.setCosto(facturaTemporal.getDetFactura().get(i).getCosto());
			df.setCodUniMed(facturaTemporal.getDetFactura().get(i)
					.getCodUniMed());
			df.setPrecio(facturaTemporal.getDetFactura().get(i).getPrecio());
			df.setTotal(facturaTemporal.getDetFactura().get(i).getTotal());
			df.setCodTpr(facturaTemporal.getDetFactura().get(i).getCodTpr());
			df.setOferta(facturaTemporal.getDetFactura().get(i).getOferta());
			df.setResOfeArt(facturaTemporal.getDetFactura().get(i)
					.getResOfeArt());
			df.setPorDesc(facturaTemporal.getDetFactura().get(i).getPorDesc());
			df.setDtoLin(facturaTemporal.getDetFactura().get(i).getDtoLin());
			df.setModPmo(facturaTemporal.getDetFactura().get(i).getModPmo());
			df.setCanDev(facturaTemporal.getDetFactura().get(i).getCanDev());
			df.setAplIto(facturaTemporal.getDetFactura().get(i).getAplIto());
			df.setCodDArt(facturaTemporal.getDetFactura().get(i).getCodDart());
			if (facturaTemporal.getDetFactura().get(i).getCodDart()
					.compareTo(numCero) == 1)
				df.setMetIngreso("M");
			else
				df.setMetIngreso("C");
			df.setPorIto1(facturaTemporal.getDetFactura().get(i).getPorIto1());
			df.setMonItoUni1(facturaTemporal.getDetFactura().get(i)
					.getMonItoUni1());
			df.setMonItoTot1(facturaTemporal.getDetFactura().get(i)
					.getMonItoTot1());
			df.setSecLinea(facturaTemporal.getDetFactura().get(i).getSecLinea());
			df.setCodBar(facturaTemporal.getDetFactura().get(i).getCodBar());
			df.setTipCap(facturaTemporal.getDetFactura().get(i).getTipCap());
			df.setTipDto(facturaTemporal.getDetFactura().get(i).getTipDto());
			df.setApdTog(facturaTemporal.getDetFactura().get(i).getApdTog());
			df.setPreNet(facturaTemporal.getDetFactura().get(i).getPreNet());
			df.setCodDto(facturaTemporal.getDetFactura().get(i).getCodDto());
			df.setCodGru(facturaTemporal.getDetFactura().get(i).getCodGru());
			df.setCodRdto(facturaTemporal.getDetFactura().get(i).getCodRdto());
			df.setCodgEqu(facturaTemporal.getDetFactura().get(i).getCodGequ());
			df.setPregEqu(facturaTemporal.getDetFactura().get(i).getPregEqu());
			df.setDtoDesImp(parametrosInventario.get().getDtoDesImp());
			df.setIncItoAfePre(parametrosInventario.get().getIncItoAfePre());

			// jcalvo. sass#880. integración Vox - OpenTouch. fecha:25/09/2017.
			if (facturaTemporal.getDetFactura().get(i).getIndMovDst() != null) {
				Resultado<DespachoCombustible> resultado = despachoCombustibleDao
						.get().getById(
								facturaTemporal.getDetFactura().get(i)
										.getIndMovDst());
				if (resultado.resultado == TipoResultado.SUCCESS) {
					DespachoCombustible dc = resultado.get();
					df.setDespacho(dc);
					dc.setIndEstado("A");
				}
			}

			if (facturaTemporal.getDetFactura().get(i).getCodDisp() != null) {
				Dispensado disp = dispensadoDao.get().findByCodigo(
						facturaTemporal.getDetFactura().get(i).getCodDisp());
				dispensados.add(disp);
				df.setBloqueado(true);
			}

			if (facturaTemporal.getDetFactura().get(i).getCodArt() != null) {

				Articulo art = obtenerArticulo(
						facturaTemporal.getDetFactura().get(i).getCodArt())
						.get();

				Map<String, Object> vab = validarArticuloBloqueado(art);
				if ((boolean) vab.get("estado")) {

					fc.mostrarMensaje("Validar artículo bloqueado",
							(String) vab.get("mensaje"));
					seCargo = false;

				} else {

					Map<String, String> vea = validarExistenciaArticulo(art,
							facturaTemporal.getDetFactura().get(i).getCan());
					if ((vea.get("tipval").equals("V"))
							&& vea.get("estado").equals("ERROR")
							&& facturaTemporal.getDetFactura().get(i)
									.getCodDisp() == null) {

						Map<String, Object> res = fc.mostrarMensaje(
								"Validación de existencias",
								vea.get("mensaje"), "P");
						if (res.get("resultado").equals("YES")) {
							Resultado<Articulo> resultado = obtenerDescuento(
									art, facturaTemporal.getDetFactura().get(i)
											.getCan(),
									facturaTemporal.getCodTfa());
							df.setTipDto(resultado.get().getTipDto());
							df.setCodDto(resultado.get().getCodDto());
							df.setCodGru(resultado.get().getCodGru());
							df.setCodRdto(resultado.get().getCodRan());
							df.setPorDesc(resultado.get().getDtoLin());
							df.setCantidad(facturaTemporal.getDetFactura()
									.get(i).getCan());
						} else {
							seCargo = false;
						}

					} else {

						if (vea.get("estado").equals("ERROR")
								&& facturaTemporal.getDetFactura().get(i)
										.getCodDisp() == null) {

							fc.mostrarMensaje("Error", vea.get("mensaje"));
							df.setCantidad(BigDecimal.ZERO);
							seCargo = false;

						} else {
							Resultado<Articulo> resultado = obtenerDescuento(
									art, facturaTemporal.getDetFactura().get(i)
											.getCan(),
									facturaTemporal.getCodTfa());
							df.setTipDto(resultado.get().getTipDto());
							df.setCodDto(resultado.get().getCodDto());
							df.setCodGru(resultado.get().getCodGru());
							df.setCodRdto(resultado.get().getCodRan());
							df.setPorDesc(resultado.get().getDtoLin());
							df.setCantidad(facturaTemporal.getDetFactura()
									.get(i).getCan());
						}
					}
					df.setArticulo(art);
					factura.getDetFactura().add(df);
					factura.getDetFactura().get(i).setFactura(factura);
				}

			}
		}
		if (seCargo) {
			facturaTemporalDao.get().aplicarFacturaTemporal(
					facturaTemporal.getId().getCodigo());
		}

		codCliente.set(facturaTemporal.getCodCli());
		factura.setSubTot(facturaTemporal.getSubTot());
		total.set(Parametros.formatMon.format(facturaTemporal.getTotal()));
	}

	public List<CesionFactura> obtenerFacturasCedidas() {
		return cesionfacturaDao.get().traerFacturasCedidas();
	}

	public Resultado<String> actOtrasTablas(CesionFactura cesionFactura,
			BigDecimal total, String accion) {
		return cesionfacturaDao.get().actializarOtrasTablas(cesionFactura,
				total, accion);
	}

	public List<Dispensado> obtenerDispensadosPendientes() {
		return dispensadoDao.get().findByEstado();
	}

	public BitacoraAutorizacion registarAutorizacion(String control,
			String codUsr) {

		BitacoraAutorizacion aut = new BitacoraAutorizacion();

		aut.getId().setCodcia(Parametros.codcia);
		aut.getId().setCodInv(Parametros.codinv);
		aut.setCodCapc(control);
		aut.setFecAut(new Date());
		aut.setUsrAut(codUsr);

		return aut;
	}

	// ---------------------------------------------------------------------------------
	public void crearLineaDespachoCombustible(Sale sale) {
		int indiceDespacho;
		Resultado<DespachoCombustible> resultado = despachoCombustibleDao.get()
				.findByIdTransaction(sale.getTurno(), sale.getIdTransaccion());
		boolean esPruebaSurtidor;
		if (resultado.resultado == TipoResultado.SUCCESS) {

			esPruebaSurtidor = Parametros.pruebassurtidor.contains(resultado
					.get().getTipoVenta());

			indiceDespacho = getDespachoCombustible().indexOf(resultado.get());

			if (indiceDespacho < 0 && !esPruebaSurtidor) {
				// excluye las pruebas de surtidor y solamente incluye los
				// despachos correspondientes a ventas
				DespachoCombustible nuevo = resultado.get();
				// agrega el listener al nuevo despacho
				agregarListenerDespachosCombustible(nuevo);
				//
				getDespachoCombustible().add(0, nuevo);
			} else {
				if (esPruebaSurtidor) {
					// desmarca el despacho y lo elimina
					getDespachoCombustible().get(indiceDespacho).setIndEstado(
							"P");
					getDespachoCombustible().remove(indiceDespacho);
				}
			}
		}
	}

	public void cambiarTurno(String turno) {
		Parametros.turnoactual = turno;
	}

	public void mostrarMensaje(String encabezado, String mensaje) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(encabezado);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public boolean actMovs() {
		boolean actMovs = true;

		if (getFactura().getCodTfa().equals(
				getParametrosVentas().getCodTfaCon())) {

			BigDecimal mtoOfp = new BigDecimal("0");

			for (int i = 0; i < getDetalleFormaPago().size(); i++) {
				if (getDetalleFormaPago().get(i).getFormaPago().getDepCaj()
						.equals("N")) {
					mtoOfp = mtoOfp.add(getDetalleFormaPago().get(i)
							.getMonPag());
				}
			}

			Resultado<Boolean> res = actualizarResumenMovs(getFactura()
					.getTotal(), BigDecimal.ZERO, BigDecimal.ZERO, mtoOfp);

			if (res.getResultado().equals(TipoResultado.ERROR)) {
				mostrarMensaje("Error al aplicar factura", res.getMensaje());
				actMovs = false;
			}
		}
		return actMovs;
	}

	public void aplicarComoEfectivo() {
		if (getFactura().getTotal().compareTo(BigDecimal.ZERO) > 0) {
			if (reversarFormasPago(getDetalleFormaPago(),
					"Reversión al quitar formas de pago para aplicar como solo efectivo.")) {
				Resultado<FormaPago> fp = new Resultado<>();
				fp = obtenerFormaPago(getParametrosPos().getCodTpagIni());
				if (fp.getResultado().equals(TipoResultado.SUCCESS)) {
					// #formaPago
					Map<String, Object> result = crearLineaFormaPago(fp.get());
					DetFormaPago dfp = (DetFormaPago) result.get("detalle");

					if (dfp != null) {
						getDetalleFormaPago().add(0, dfp);

						BigDecimal totalFactura = getFactura().getTotal();

						if (getFactura().getMovimientoIngreso() != null)
							totalFactura = totalFactura.add(getFactura()
									.getMovimientoIngreso().getTotFac());

						getDetalleFormaPago().get(0).setMtoMon(totalFactura);
						getDetalleFormaPago().get(0).setMonPag(totalFactura);

						Resultado<String> resultado = guardarFactura();

						if (resultado.getResultado() != TipoResultado.SUCCESS)
							mostrarMensaje(resultado.get(),
									resultado.getMensaje());
						else {
							actMovs();
							imprimirFactura();

							String inacApe = inactivarApertura();
							if (!inacApe.isEmpty())
								mostrarMensaje(
										"Error al inactivar la apertura",
										inacApe);
							/**
							 * #jcalvo. scre:004698. fecha:11/07/2017. se
							 * incluye segundo parámetro true, para quitar los
							 * despachos de combustible atendidos.
							 */
							reiniciarFactura(true, true);
							iniciarFactura();
						}
					}
				}
			}
		}
	}

	/** #jcalvo. scre:004698. fecha:11/07/2017. */
	public void eliminarDespachosAtendidos(boolean aplicar) {
		if (aplicar) {
			despachosCombustible.removeIf(d -> d.getIndEstado().get()
					.equalsIgnoreCase("A"));
		}
	}

	public void desmarcarDespachosCombustible() {
		despachosCombustible.stream().forEach(dc -> dc.setIndEstado("P"));		
	}

	/*************************************************/

	public void reiniciarFactura(boolean validarFacTmp, boolean aplicar) {
		eliminarDespachosAtendidos(aplicar);
		reiniciarFactura(validarFacTmp);
	}

	public void imprimirTicketVox() {
		if (!despachosCombustible.stream()
				.filter(d -> d.getIndEstado().get().equalsIgnoreCase("A"))
				.findAny().isPresent()) {
			mostrarMensaje("Imprimir Ticket Vox",
					"NO se ha seleccionado ningún dispensado de combustible.");
			return;
		}
		despachosCombustible
				.stream()
				.filter(d -> d.getIndEstado().get().equalsIgnoreCase("A"))
				.forEach(
						c -> despachoCombustibleDao.get().imprimirTicketVox(
								c.getIdVox(), c.getCara(), c.getDesProducto(),
								c.getPrecio(), c.getVolumen(), c.getVenta()));
	}
}
