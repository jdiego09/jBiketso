package cr.co.coopeagri.opentouch.model.classes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.datafx.controller.context.ApplicationContext;
import org.datafx.crud.jpa.CreateOnceSupplier;

import cr.co.coopeagri.encrypt.v1.Encrypt3Des;
import cr.co.coopeagri.encrypt.v1.KeySistema;
import cr.co.coopeagri.opentouch.model.entitys.CesionFactura;
import cr.co.coopeagri.opentouch.model.entitys.DetFormaPago;
import cr.co.coopeagri.opentouch.model.entitys.DetMovimiento;
import cr.co.coopeagri.opentouch.model.entitys.Dispensado;
import cr.co.coopeagri.opentouch.model.entitys.Factura;
import cr.co.coopeagri.opentouch.model.entitys.FacturaPK;
import cr.co.coopeagri.opentouch.model.entitys.MovimientoIngreso;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaDao;

public class DefaultFacturaDao extends DefaultDaoBase<Factura, FacturaPK>
		implements FacturaDao {

	private String _class = DefaultFacturaDao.class.getName();
	private Logger log = Logger.getLogger(_class);

	EntityManager em = getEntityManager();

	final String pathDir = System.getProperty("user.dir");

	public DefaultFacturaDao(EntityManager entityManager) {
		super(entityManager, Factura.class);
	}

	public DefaultFacturaDao(Supplier<EntityManager> entityManagerSupplier) {
		super(entityManagerSupplier, Factura.class);
	}

	public DefaultFacturaDao(
			CreateOnceSupplier<EntityManager> entityManagerSupplier) {
		super(entityManagerSupplier, Factura.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Resultado<List<Factura>> getByCliente(String cliente) {
		Resultado<List<Factura>> resultado = new Resultado<>();
		Query query = em
				.createQuery("Select f from Factura f Where f.id.codCia = :cia"
						+ "And f.id.tipMov = 'F' " + "And f.codCli = :cliente");
		query.setParameter("cia", Parametros.codcia);
		query.setParameter("cliente", cliente);
		resultado.set((List<Factura>) query.getResultList());
		return (resultado);
	}

	/***
	 * Consulta las devoluciones generadas en openpos tanto de crédito o debito
	 * (según parámetro "tipoDevolucion") desde la fecha indicada hasta la fecha
	 * actual
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Resultado<List<Factura>> getDevoluciones(String tipoDevolucion,
			String codigoTipoCredito, Date fechaDesde) {
		Resultado<List<Factura>> resultado = new Resultado<>();

		try {
			String consulta = "Select f from Factura f left join f.movimientoEgreso me on me.id.tipMov = 'S' and me.nulo = 'N' Where f.id.codCia = :cia and f.id.tipMov = 'D' "
					+ "and f.fecha > :fecha and f.nulo = 'N' and f.aplicado = 'S' and f.codInv = :codInv "
					+ "and f.tipDev in ('E', 'O') and";

			if (tipoDevolucion.equals("contado"))
				consulta = consulta
						+ "(f.tipDocGen in ('F', 'A') or f.tipDocGen = null) and (((f.tipDocGen = 'F' and "
						+ "f.codDocGen not like :credito) or (f.tipDocGen = 'A' and f.codDocGen like '%')) or f.codDocGen is null)";
			else
				consulta = consulta
						+ "(f.tipDocGen in ('F', 'C')) and (((f.tipDocGen = 'F' and "
						+ "f.codDocGen like :credito) or (f.tipDocGen = 'C' and f.codDocGen like '%'))) ";

			consulta = consulta + "and me is null";

			Query query = em.createQuery(consulta);
			query.setParameter("cia", Parametros.codcia);
			query.setParameter("codInv", Parametros.codinv);
			query.setParameter("credito", codigoTipoCredito + "%");
			query.setParameter("fecha", fechaDesde);

			resultado.set((List<Factura>) query.getResultList());
			resultado.setResultado(TipoResultado.SUCCESS);

		} catch (Exception ex) {
			resultado.set(null);
			resultado.setMensaje(ex.getMessage());
			resultado.setResultado(TipoResultado.ERROR);

			log.logp(Level.SEVERE, _class, "getDevoluciones", ex.getMessage(),
					ex);
		}

		return resultado;
	}

	/***
	 * Guarda y aplica la factura ingresada
	 */
	@Override
	public Resultado<Factura> save(Factura factura) {
		Resultado<Factura> resultado = new Resultado<Factura>();
		Resultado<String> aplicado = new Resultado<String>();
		Boolean nueva = true;
		try {
			if (factura.getId().getCodigo() != null){
				nueva = false;
			}
			em.getTransaction().begin();

			// Obtiene el consecutivo temporal de la factura
			StoredProcedureQuery spq = em
					.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ObtenerCodigoTmp");			
			spq.registerStoredProcedureParameter("p_codtmp", String.class, ParameterMode.OUT);
			spq.registerStoredProcedureParameter("p_msjerr", String.class, ParameterMode.OUT);
			if (nueva) {				
				spq.execute();
			}

			if (!nueva
					|| (spq.getOutputParameterValue("p_msjerr") == null || ((String) spq
							.getOutputParameterValue("p_msjerr")).isEmpty())) {

				if (nueva) {
					factura.getId().setCodigo("OTF" + 
							(String) spq.getOutputParameterValue("p_codtmp"));
				}
				em.getTransaction().commit();

				for (int i = 0; i < factura.getDetFactura().size(); i++) {
					factura.getDetFactura().get(i).getId()
							.setCodFac(factura.getId().getCodigo());
					factura.getDetFactura().get(i).getId().setIndice(i + 1); // DFA_INDICE
					factura.getDetFactura().get(i)
							.setOrden(new BigDecimal(i + 1)); // DFA_ORDEN

					// Guarda el tipo de entrada con la que se ingreso la
					// cantidad del artículo,
					// es necesaria para el calculo del redondeo de las
					// cantidades en caso de ingresarse como monto
					if (factura.getDetFactura().get(i).getMetIngreso()
							.equals("M"))
						factura.getDetFactura().get(i)
								.setCodDArt(new BigDecimal(1));
				}

				Long codMov;
				if (nueva)
					codMov = getCodigoMovimiento();
				else
					codMov = factura.getMovimiento().getId().getCodigo();

				factura.getMovimiento().getId().setCodigo(codMov);
				factura.getMovimiento().getId()
						.setTipMov(factura.getId().getTipMov());
				factura.getMovimiento().setCodMovGen(
						factura.getId().getCodigo());
				factura.getMovimiento().setCodApe(
						new BigDecimal(factura.getMovimiento().getId()
								.getCodigo()));
				factura.getMovimiento().setCodTmo(factura.getCodTfa());
				factura.getMovimiento().setTotFac(factura.getTotal());
				factura.getMovimiento().setCodCli(factura.getCodCli());
				factura.getMovimiento().setCodMon(factura.getCodMon());

				// En caso de haber ingresado datos adicionales
				/*
				 * if (factura.getInfoAdicFactura() != null)
				 * factura.getInfoAdicFactura().getId()
				 * .setCodFac(factura.getId().getCodigo());
				 */

				int indice = 1;
				for (DetFormaPago dfp : factura.getMovimiento()
						.getDetFormaPago()) {
					dfp.getId().setCodmov(
							factura.getMovimiento().getId().getCodigo());
					dfp.getId().setIndice(indice);
					indice++;
				}

				if (factura.getMovimientoIngreso() != null) {
					factura.getMovimientoIngreso().setCodMovGen(
							factura.getId().getCodigo());
					for (DetMovimiento dm : factura.getMovimientoIngreso()
							.getDetalleMovimientos())
						dm.getId().setCodigo(
								factura.getMovimientoIngreso().getId()
										.getCodigo());
				}

				em.getTransaction().begin();
				Query query = em.createNativeQuery("Select SYSDATE From Dual");
				Date sysdate = (Date) query.getSingleResult();
				factura.getMovimiento().setFechaApli(sysdate);
				factura.setFecHorFin(sysdate);
				factura.setCanLineas(new BigDecimal(factura.getDetFactura()
						.size()));
				em.getTransaction().commit();

				if (!((String) ApplicationContext.getInstance()
						.getRegisteredObject("usuEncargado"))
						.equals(((String) ApplicationContext.getInstance()
								.getRegisteredObject("usuActual")))) {

					CesionFactura cf;
					if (!nueva && factura.getCesionFactura() != null)
						cf = factura.getCesionFactura();
					else
						cf = new CesionFactura();

					cf.getId().setCodCia(Parametros.codcia);
					cf.getId().setCodInv(Parametros.codinv);
					cf.getId().setCodCajFue(
							(String) ApplicationContext.getInstance()
									.getRegisteredObject("usuActual"));
					cf.getId().setCodFac(factura.getId().getCodigo());
					cf.getId().setCodCajDes(
							(String) ApplicationContext.getInstance()
									.getRegisteredObject("usuEncargado"));
					cf.setFecha(sysdate);
					cf.setCedida("P");
					cf.setFactura(factura);
					factura.setCesionFactura(cf);
				}

				int canBit = factura.getMovimiento().getBitacoraAutorizacion()
						.size();
				if (canBit > 0) {
					for (int i = 0; i < canBit; i++) {
						factura.getMovimiento().getBitacoraAutorizacion()
								.get(i).setTipMov("F");
						factura.getMovimiento().getBitacoraAutorizacion()
								.get(i).setCodMov(new BigDecimal(codMov));
					}
				}

				// Si la factura cuenta con articulos dispensados se
				// actualizaran cada uno de estos movimientos de inventario con
				// los datos de la factura generada
				String errorActualizandoInvMov = "";

				if (factura.getDispensado() != null) {
					for (Dispensado disp : factura.getDispensado()) {
						em.getTransaction().begin();

						StoredProcedureQuery spqActualizarInvMov = em
								.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ActualizaInvMovDispensado");
						spqActualizarInvMov.registerStoredProcedureParameter(
								"p_CodCia", String.class, ParameterMode.IN);
						spqActualizarInvMov.registerStoredProcedureParameter(
								"p_CodInv", String.class, ParameterMode.IN);
						spqActualizarInvMov.registerStoredProcedureParameter(
								"p_CodInvMov", String.class, ParameterMode.IN);
						spqActualizarInvMov.registerStoredProcedureParameter(
								"p_CodFac", String.class, ParameterMode.IN);
						spqActualizarInvMov.registerStoredProcedureParameter(
								"p_CodTipMov", String.class, ParameterMode.IN);
						spqActualizarInvMov.registerStoredProcedureParameter(
								"p_Error", String.class, ParameterMode.OUT);

						spqActualizarInvMov.setParameter("p_CodCia",
								Parametros.codcia);
						spqActualizarInvMov.setParameter("p_CodInv",
								Parametros.codinv);
						spqActualizarInvMov.setParameter("p_CodInvMov",
								disp.getCodigoMovimiento());
						spqActualizarInvMov.setParameter("p_CodFac", factura
								.getId().getCodigo());
						spqActualizarInvMov.setParameter("p_CodTipMov", factura
								.getId().getTipMov());

						spqActualizarInvMov.execute();

						if (spqActualizarInvMov
								.getOutputParameterValue("p_Error") != null) {
							errorActualizandoInvMov = (String) spqActualizarInvMov
									.getOutputParameterValue("p_Error");
							log.logp(
									Level.SEVERE,
									_class,
									"OPENSIDE.SIDE_FAC.Fac_ActualizaInvMovDispensado",
									errorActualizandoInvMov);
						}

						em.getTransaction().commit();
					}
					for (Dispensado disp : factura.getDispensado()) {
						disp.setCodigoFactura(factura.getId().getCodigo());
						disp.setTipoMovFactura(factura.getId().getTipMov());
						disp.setFacturaAplicada("S");
						disp.setFechaAplica(factura.getFecHorFin());
					}
				}

				if (errorActualizandoInvMov.isEmpty()) {
					em.getTransaction().begin();
					if (nueva)
						em.persist(factura);
					else
						factura = em.merge(factura);
					em.getTransaction().commit();
					em.clear();
				} else {
					resultado.set(factura);
					resultado.setMensaje(errorActualizandoInvMov);
					resultado.setResultado(TipoResultado.ERROR);
					return resultado;
				}

			} else {
				em.getTransaction().commit();
				em.clear();
				resultado.set(factura);
				resultado.setMensaje((String) spq
						.getOutputParameterValue("p_Error"));
				resultado.setResultado(TipoResultado.ERROR);
				log.logp(Level.SEVERE, _class,
						"OPENSIDE.SIDE_FAC.Fac_ObtenerNumeroFactura",
						resultado.getMensaje());
				return resultado;
			}

			// Aplica la factura (genera movimientos de inventario y crédito)
			aplicado = aplicarFactura(factura.getId().getCodigo(),
					factura.getCodFacTmp(),factura.getMovimiento().getId().getCodigo());
			if (aplicado.getResultado().equals(TipoResultado.ERROR)) {
				/*
				 * em.getTransaction().begin(); em.remove(factura);
				 * em.getTransaction().commit();
				 */
				if (em.getTransaction().isActive())
					em.getTransaction().rollback();

				resultado.set(factura);
				resultado.setMensaje("Error en DefaultFacturaDao.save. "
						+ aplicado.getMensaje());
				resultado.setResultado(TipoResultado.ERROR);
				log.logp(Level.SEVERE, _class, "save.AplicarFactura",
						resultado.getMensaje());
			} else {
				resultado.set(factura);
				resultado.setMensaje("");
				resultado.setResultado(TipoResultado.SUCCESS);
			}

			return resultado;
		} catch (Exception ex) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			resultado.set(factura);
			resultado.setMensaje("Error en DefaultFacturaDao.save. "
					+ aplicado.getMensaje() + " \n " + ex.getMessage());
			resultado.setResultado(TipoResultado.ERROR);
			log.logp(Level.SEVERE, _class, "save", ex.getMessage(), ex);
			return resultado;
		}
	}

	/***
	 * Obtiene el consecutivo temporal de los movimientos
	 */
	@Override
	public Long getCodigoMovimiento() {
		try {
			em.getTransaction().begin();
			StoredProcedureQuery spq = em
					.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ObtenerCodigoTmp");			
			spq.registerStoredProcedureParameter("p_codtmp", String.class, ParameterMode.OUT);
			spq.registerStoredProcedureParameter("p_msjerr", String.class, ParameterMode.OUT);				
			spq.execute();
			
			String codTemp = (String) spq.getOutputParameterValue("p_codtmp");			
			Long codigo = new BigDecimal(codTemp.replace("OPENSIDE$", "")).longValue();
			
			em.getTransaction().commit();
			return codigo;
		} catch (Exception ex) {
			log.logp(Level.SEVERE, _class, "getCodigoMovimiento",
					ex.getMessage(), ex);
			return null;
		}
	}

	/***
	 * Obtiene el código temporal para facturas
	 */
	@Override
	public String traerCodigoTemp() {
		try {
			em.getTransaction().begin();
			Query query = em
					.createNativeQuery("Select OPENSIDE.Side_Sec_Tdp.NextVal From Dual");
			String codTmp = ((BigDecimal) query.getSingleResult()).toString();
			em.getTransaction().commit();
			return codTmp;
		} catch (Exception ex) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			log.logp(Level.SEVERE, _class, "traerCodigoTemp", ex.getMessage(),
					ex);
			return null;
		}
	}

	/***
	 * Procesa los pagos de conectividad y straplex
	 */
	@Override
	public Resultado<HashMap<String, Object>> procesarPagoTarjeta(
			String tipoMov, String codTemp, Date fechaTra, String tarHab,
			String moneda, String numTarEm, String numTarj, String fecVenc,
			BigDecimal totMto, String track2) {
		Resultado<HashMap<String, Object>> resultado = new Resultado<HashMap<String, Object>>();

		em.getTransaction().begin();
		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ProcesarPagoTarjeta");
		spq.registerStoredProcedureParameter("p_TipMov", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodTemp", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FecTran", Date.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Usuario", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodEst", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Moneda", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_TarHab", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_NumTarEm", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_NumTarj", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_NumTerm", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FecVenc", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_TotMto", BigDecimal.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Track2", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_NumComer", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_NomMaquina", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodTra", BigDecimal.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_CodResp", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_NumAut", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_NumRef", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_NumTrace", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_Procesado", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.INOUT);

		spq.setParameter("p_TipMov", tipoMov);
		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.setParameter("p_CodTemp", codTemp);
		spq.setParameter("p_FecTran", fechaTra);
		spq.setParameter("p_Usuario", ((String) ApplicationContext
				.getInstance().getRegisteredObject("usuActual")));
		spq.setParameter("p_CodEst", Parametros.codest);
		spq.setParameter("p_Moneda", moneda);
		spq.setParameter("p_TarHab", tarHab);
		spq.setParameter("p_NumTarEm", numTarEm);
		spq.setParameter("p_NumTarj", numTarj);
		spq.setParameter("p_NumTerm", Parametros.numter);
		spq.setParameter("p_FecVenc", fecVenc);
		spq.setParameter("p_TotMto", totMto);
		spq.setParameter("p_Track2", track2);
		spq.setParameter("p_NumComer", Parametros.numcom);
		spq.setParameter("p_NomMaquina", Parametros.nommaquina);
		spq.setParameter("p_CodTra", null);
		spq.setParameter("p_CodResp", null);
		spq.setParameter("p_NumAut", null);
		spq.setParameter("p_NumRef", null);
		spq.setParameter("p_NumTrace", null);
		spq.execute();

		resultado.set(null);
		if (((String) spq.getOutputParameterValue("p_Procesado")).equals("S")) {
			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("NumAut", (String) spq.getOutputParameterValue("p_NumAut"));
			res.put("NumRef", (String) spq.getOutputParameterValue("p_NumRef"));
			res.put("NumTrace",
					(String) spq.getOutputParameterValue("p_NumTrace"));
			res.put("CodTra",
					(BigDecimal) spq.getOutputParameterValue("p_CodTra"));
			res.put("CodResp",
					(String) spq.getOutputParameterValue("p_CodResp"));

			resultado.set(res);
			resultado.setResultado(TipoResultado.SUCCESS);
			resultado.setMensaje("Pago procesado satisfactoriamente.");
		} else {
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));
			log.logp(Level.SEVERE, _class, "procesarPagoTarjeta",
					resultado.getMensaje());
		}

		em.getTransaction().commit();
		return resultado;
	}

	/***
	 * Reversa los pagos de conectividad y straplex
	 */
	@Override
	public Resultado<HashMap<String, Object>> reversarPagoTarjeta(
			DetFormaPago detFormaPago, String tipoMov, String codTemp,
			String obs) {
		Resultado<HashMap<String, Object>> resultado = new Resultado<HashMap<String, Object>>();

		try {

			em.getTransaction().begin();
			
			StoredProcedureQuery spq = em
					.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ReversarPagoTarjeta");
			spq.registerStoredProcedureParameter("p_TipMov", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodCia", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodInv", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodTemp", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodEst", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_Usuario", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_Res", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_Moneda", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_FecTran", Date.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_TipCam", BigDecimal.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodTra", BigDecimal.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_NumTrace", String.class,
					ParameterMode.INOUT);
			spq.registerStoredProcedureParameter("p_NumRef", String.class,
					ParameterMode.INOUT);
			spq.registerStoredProcedureParameter("p_NumAut", String.class,
					ParameterMode.INOUT);
			spq.registerStoredProcedureParameter("p_NumTerm", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_NumTarj", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_NumTarEm", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_TarHab", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_NumCaja", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_FecVenc", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_TotMto", BigDecimal.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_NumComer", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_Obs", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_error", String.class,
					ParameterMode.OUT);

			spq.setParameter("p_TipMov", tipoMov);
			spq.setParameter("p_CodCia", Parametros.codcia);
			spq.setParameter("p_CodInv", Parametros.codinv);
			spq.setParameter("p_CodTemp", codTemp);
			spq.setParameter("p_CodEst", Parametros.codest);
			spq.setParameter("p_Usuario", ((String) ApplicationContext
					.getInstance().getRegisteredObject("usuActual")));
			spq.setParameter("p_Res", detFormaPago.getEmisor());
			spq.setParameter("p_Moneda", detFormaPago.getFormaPago()
					.getMoneda().getId().getCodigo());
			spq.setParameter("p_FecTran", Date.from(Instant
					.from(((LocalDate) ApplicationContext.getInstance()
							.getRegisteredObject("fechaTra"))
							.atStartOfDay(ZoneId.systemDefault()))));
			spq.setParameter("p_TipCam", detFormaPago.getFormaPago()
					.getMoneda().getTipCam());
			spq.setParameter("p_CodTra",
					new BigDecimal(detFormaPago.getTelefono()));
			if (tipoMov.equals("C"))
				spq.setParameter("p_NumTrace", detFormaPago.getNumDoc());
			else
				spq.setParameter("p_NumTrace", null);
			spq.setParameter("p_NumRef", detFormaPago.getBanco());

			spq.setParameter("p_NumAut", detFormaPago.getNumAut());
			spq.setParameter("p_NumTerm", Parametros.numter);
			spq.setParameter("p_NumTarj", detFormaPago.getNumTarjeta());
			spq.setParameter("p_NumTarEm", detFormaPago.getNumCta());
			spq.setParameter("p_TarHab", detFormaPago.getBenef());
			spq.setParameter("p_NumCaja", Parametros.codest);
			spq.setParameter("p_FecVenc", detFormaPago.getFechaVence());
			spq.setParameter("p_TotMto", detFormaPago.getMonPag());
			spq.setParameter("p_NumComer", Parametros.numcom);
			spq.setParameter("p_Obs", obs);

			spq.execute();

			resultado.set(null);
			if (spq.getOutputParameterValue("p_error") == null
					|| ((String) spq.getOutputParameterValue("p_error"))
							.isEmpty()) {
				resultado.setResultado(TipoResultado.SUCCESS);
				resultado.setMensaje("Pago reversado satisfactoriamente.");

				String NumAut = (String) spq
						.getOutputParameterValue("p_NumAut");
				String NumRef = (String) spq
						.getOutputParameterValue("p_NumRef");
				String NumTrace = (String) spq
						.getOutputParameterValue("p_NumTrace");

				em.getTransaction().commit();

				em.getTransaction().begin();

				StoredProcedureQuery spqPasarAReal = em
						.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_PasarMovTempAReal");
				spqPasarAReal.registerStoredProcedureParameter("p_CodTra",
						String.class, ParameterMode.IN);
				spqPasarAReal.registerStoredProcedureParameter("p_Error",
						String.class, ParameterMode.INOUT);

				spqPasarAReal.setParameter("p_CodTra",
						detFormaPago.getTelefono());

				spqPasarAReal.execute();

				if (spqPasarAReal.getOutputParameterValue("p_Error") != null
						&& !((String) spqPasarAReal
								.getOutputParameterValue("p_Error")).isEmpty()) {
					resultado
							.setMensaje("Pago reversado satisfactoriamente, ocurrio un error al pasar la transacción a las tablas reales, es necesario realizar el pase en el sistema Openpos. \n"
									+ (String) spqPasarAReal
											.getOutputParameterValue("p_Error"));
				}

				em.getTransaction().commit();

				imprimirVoucher(tipoMov, false, ((String) ApplicationContext
						.getInstance().getRegisteredObject("usuActual")),
						detFormaPago.getNumCta(), NumAut, NumRef, NumTrace,
						detFormaPago.getMonPag(), detFormaPago.getBenef(),
						true, true, Parametros.numter, Parametros.codest,
						new Date());

				imprimirVoucher(tipoMov, false, ((String) ApplicationContext
						.getInstance().getRegisteredObject("usuActual")),
						detFormaPago.getNumCta(), NumAut, NumRef, NumTrace,
						detFormaPago.getMonPag(), detFormaPago.getBenef(),
						false, true, Parametros.numter, Parametros.codest,
						new Date());
			} else {
				resultado.setResultado(TipoResultado.ERROR);
				resultado.setMensaje((String) spq
						.getOutputParameterValue("p_error"));
				log.logp(
						Level.SEVERE,
						_class,
						"reversarPagoTarjeta",
						"Error reversando pago tarjeta: "
								+ resultado.getMensaje());
				em.getTransaction().commit();
			}

			return resultado;
		} catch (Exception ex) {
			log.logp(Level.SEVERE, _class, "reversarPagoTarjeta",
					ex.getMessage(), ex);
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje(ex.getMessage());

			return resultado;
		}
	}

	/***
	 * Pasa
	 */
	public Resultado<Boolean> pasarTDPTemporalAReal(
			MovimientoIngreso movimientoIngreso) {
		Resultado<Boolean> resultado = new Resultado<>();

		try {
			em.getTransaction().begin();

			StoredProcedureQuery spq = em
					.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_PasarTempAReal");
			spq.registerStoredProcedureParameter("p_CodCia", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodInv", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_TipMov", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_CodigoMov", String.class,
					ParameterMode.IN);
			spq.registerStoredProcedureParameter("p_Error", String.class,
					ParameterMode.OUT);

			spq.setParameter("p_CodCia", Parametros.codcia);
			spq.setParameter("p_CodInv", Parametros.codinv);
			spq.setParameter("p_TipMov", movimientoIngreso.getId().getTipMov());
			spq.setParameter("p_CodigoMov",
					String.valueOf(movimientoIngreso.getId().getCodigo()));

			spq.execute();

			if (spq.getOutputParameterValue("p_Error") == null
					|| ((String) spq.getOutputParameterValue("p_Error"))
							.isEmpty()) {
				resultado.set(true);
				resultado.setResultado(TipoResultado.SUCCESS);
			} else {
				resultado.set(false);
				resultado.setMensaje((String) spq
						.getOutputParameterValue("p_Error"));
				resultado.setResultado(TipoResultado.ERROR);
			}

			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			resultado.set(false);
			resultado
					.setMensaje("Error al pasar los movimientos temporales a real. "
							+ ex.getMessage());
			resultado.setResultado(TipoResultado.ERROR);
		}

		return resultado;

	}

	/***
	 * Imprime los voucher de pago con tarjeta
	 */
	@Override
	public Boolean imprimirVoucher(String tipo, Boolean reimpresion,
			String cajero, String tarjeta, String numAut, String numRef,
			String numFac, BigDecimal monto, String tarHab, Boolean original,
			Boolean reversion, String numTer, String codEst, Date fecha) {

		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("pTipo", tipo);
		param.put("pReimpresion", reimpresion);
		param.put("pFecha", fecha);
		param.put("pHora", fecha);
		param.put("pCodEst", codEst);
		param.put("pCajero", cajero);
		param.put("pTerminal", numTer);
		param.put("pTarjeta", tarjeta);
		param.put("pNumAut", numAut);
		param.put("pNumRef", numRef);
		param.put("pNumFac", numFac);
		param.put("pMonto", monto);
		String resumen = "";
		if (original) {
			if (!tarHab.isEmpty())
				resumen = "----------------------------\n" + tarHab.trim()
						+ "\nORIGINAL";
			else
				resumen = "----------------------------\nFirma\nORIGINAL";
		}
		if (!original)
			resumen = "COPIA DEL CLIENTE";

		param.put("pResumen", resumen);

		if (tipo.equals("S")) {
			param.put("pLogo", pathDir
					+ "/reportes/imagenes/credecoop_logo.jpg");
			param.put("pTitulo", "************* CREDECOOP R.L. *************");
		} else {
			param.put("pLogo", pathDir + "/reportes/imagenes/bncr_logo.jpg");
			param.put("pTitulo", "CONECTIVIDAD BNCR");
		}

		if (reversion)
			param.put("pReversion", "S");
		else
			param.put("pReversion", "N");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Crea la conexión para el reporte
			Connection connection = null;
			connection = DriverManager.getConnection(Parametros.url,
					Parametros.usuario, Encrypt3Des.decrypt(
							Parametros.password,
							KeySistema.OPENTOUCH.getValue()));

			if (connection != null) {
				JasperPrint print = JasperFillManager.fillReport(pathDir
						+ "/reportes/rptVoucher.jasper", param, connection);

				JasperPrintManager.printReport(print, false);
				connection.close();
			}

			return true;
		} catch (Exception ex) {
			log.logp(Level.SEVERE, _class, "imprimirVoucher", ex.getMessage(),
					ex);
			return false;
		}
	}

	/***
	 * Imprime las facturas de ventas
	 */
	@Override
	public Boolean imprimirFactura(String numeroFactura, String tipoFactura,
			Boolean original, Boolean imprimeComoContado, Boolean reimpresion,
			Boolean firmas, String codTfa, String tipFacCon, String tipFacCre) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		String nombreImpresora = "";

		param.put("pCodCia", Parametros.codcia);
		param.put("pTipoMov", tipoFactura);
		param.put("pFacCodigo", numeroFactura);
		param.put("pCopia", !original);
		param.put("pRutaLogo", pathDir + "/reportes/imagenes/Logo.jpg");
		if (imprimeComoContado)
			param.put("pImprimeContado", 'S');
		else
			param.put("pImprimeContado", 'N');
		param.put("pReimpresion", reimpresion);
		param.put("pImprimeFirmas", firmas);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Crea la conexión para el reporte
			Connection connection = null;
			connection = DriverManager.getConnection(Parametros.url,
					Parametros.usuario, Encrypt3Des.decrypt(
							Parametros.password,
							KeySistema.OPENTOUCH.getValue()));

			if (connection != null) {

				// Asigna los permisos de aplicación a la conexión del reporte
				Statement stmt = null;
				String query = "BEGIN "
						+ "OPENSIDE.PkG_AppRoles.P_SetParameter('APLICACION', 'OPENTOUCH'); "
						+ "OPENSIDE.PkG_AppRoles.P_AsignaRoles; " + "END;";

				stmt = connection.createStatement();
				stmt.execute(query);
				stmt.close();
				/******/

				boolean usaOtrImp = false;
				String nomRep = "rptFactura.jasper";
				if (Parametros.impvou != null && !Parametros.impvou.isEmpty()) {
					nombreImpresora = Parametros.impvou;
					usaOtrImp = true;
				}

				if ((codTfa.equals(tipFacCre) && Parametros.impdoc
						.equals("FCR"))
						|| (codTfa.equals(tipFacCon) && Parametros.impdoc
								.equals("FCO"))
						|| Parametros.impdoc.equals("A")) {
					nombreImpresora = Parametros.nomimp;
					usaOtrImp = true;
					param = new HashMap<String, Object>();
					nomRep = "rptFacMediaCarta.jasper";
					param.put("pCodCia", Parametros.codcia);
					param.put("pTipoMov", tipoFactura);
					param.put("pFacCodigo", numeroFactura);
					param.put("pReimpresion", reimpresion);
					if (imprimeComoContado)
						param.put("pImprimeContado", "S");
					else
						param.put("pImprimeContado", "N");
				}

				/*
				 * JasperPrint print = JasperFillManager.fillReport(pathDir +
				 * "/reportes/rptFactura.jasper", param, connection);
				 */

				JasperPrint print = JasperFillManager.fillReport(pathDir
						+ "/reportes/" + nomRep, param, connection);

				if (usaOtrImp) {
					PrintService[] services = PrintServiceLookup
							.lookupPrintServices(null, null);
					int selectedService = 0;
					/* Scan found services to see if anyone suits our needs */
					for (int i = 0; i < services.length; i++) {
						if (services[i].getName().toUpperCase()
								.contains(nombreImpresora)) {
							/*
							 * If the service is named as what we are querying
							 * we select it
							 */
							selectedService = i;
						}
					}

					JRPrintServiceExporter exporter;
					exporter = new JRPrintServiceExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,
							print);
					/* We set the selected service and pass it as a paramenter */
					exporter.setParameter(
							JRPrintServiceExporterParameter.PRINT_SERVICE,
							services[selectedService]);
					exporter.setParameter(
							JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
							Boolean.FALSE);
					exporter.setParameter(
							JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
							Boolean.FALSE);
					exporter.exportReport();
				} else {
					JasperPrintManager.printReport(print, false);
				}
				connection.close();
			}

			return true;
		} catch (Exception ex) {
			log.logp(Level.SEVERE, _class, "imprimirFactura", ex.getMessage(),
					ex);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> traerImpuestoVenta() {

		List<Object[]> datIto = null;

		Query query = getEntityManager().createNativeQuery(
				"Select Ito_Codigo, Nvl(Ito_PorIto, 0) "
						+ "From Openside.Side_Ven_Par, Openside.Side_Ven_Ito "
						+ "Where Pven_CodCia = '" + Parametros.codcia + "'"
						+ "And Ito_CodCia(+) = Pven_CodCia "
						+ "And Ito_Codigo(+) = Pven_CodItoInv");
		datIto = query.getResultList();

		return datIto;

	}

	/***
	 * Verifica el pin del usuario
	 */
	@Override
	public Resultado<HashMap<String, Object>> verificarPin(String folio,
			String pin) {
		Resultado<HashMap<String, Object>> resultado = new Resultado<HashMap<String, Object>>();

		em.getTransaction().begin();
		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ValidaPin");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Folio", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_PIN", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodUsr", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Advertencia", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Resultado", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_Folio", folio);
		spq.setParameter("p_PIN", pin);
		spq.execute();

		resultado.set(null);
		if (((String) spq.getOutputParameterValue("p_Resultado")).equals("S")) {
			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("Advertencia",
					(String) spq.getOutputParameterValue("p_Advertencia"));
			res.put("Usuario", (String) spq.getOutputParameterValue("p_CodUsr"));

			resultado.set(res);
			if (res.get("Advertencia") == null
					|| ((String) res.get("Advertencia")).isEmpty())
				resultado.setResultado(TipoResultado.SUCCESS);
			else
				resultado.setResultado(TipoResultado.WARNING);
			resultado.setMensaje("");
		} else {
			HashMap<String, Object> res = new HashMap<String, Object>();
			res.put("Advertencia",
					(String) spq.getOutputParameterValue("p_Advertencia"));

			resultado.set(res);
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));
		}

		em.getTransaction().commit();
		return resultado;
	}

	/***
	 * Consulta si existe una apertura para el cajero en la fecha y estación
	 * indicada
	 * 
	 * @param fecha
	 * @param cajero
	 * @param estacion
	 * @return
	 */
	@Override
	public Resultado<String> consultarExisteApertura(LocalDate fecha,
			String cajero) {
		Resultado<String> resultado = new Resultado<String>();
		try {
			em.getTransaction().begin();
			Query query = em.createNativeQuery("SELECT A.APE_TIPO TIPO "
					+ "FROM OPENSIDE.SIDE_POS_APE A "
					+ "WHERE A.APE_CODCIA = '" + Parametros.codcia + "' "
					+ "AND A.APE_CODINV = '" + Parametros.codinv + "' "
					+ "AND A.APE_CODEST = '" + Parametros.codest + "' "
					+ "AND A.APE_CODCAJ = '" + cajero + "' "
					+ "AND A.APE_FECHA_APE = TO_DATE('"
					+ fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
					+ "', 'DD/MM/YYYY')");
			resultado.set((String) query.getSingleResult());
			em.getTransaction().commit();
			resultado.setResultado(TipoResultado.SUCCESS);
		} catch (NoResultException ex) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			resultado.set(null);
			resultado.setResultado(TipoResultado.SUCCESS);
		} catch (Exception ex) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			resultado.set(null);
			resultado.setMensaje("Error al consultar las aperturas. "
					+ ex.getMessage());
			resultado.setResultado(TipoResultado.ERROR);
			log.logp(Level.SEVERE, _class, "consultarExisteApertura",
					ex.getMessage(), ex);

		}
		return resultado;
	}

	@Override
	@SuppressWarnings("unchecked")
	/***
	 * Consulta las aperturas de tipo cajero para la fecha indicada
	 * @param fecha
	 * @return
	 */
	public Resultado<List<String>> consultarAperturaCajeros(LocalDate fecha) {
		Resultado<List<String>> resultado = new Resultado<List<String>>();
		try {
			em.getTransaction().begin();
			Query query = em.createNativeQuery("SELECT A.APE_CODCAJ CAJERO "
					+ "FROM OPENSIDE.SIDE_POS_APE A "
					+ "WHERE A.APE_CODCIA = '" + Parametros.codcia + "' "
					+ "AND A.APE_CODINV = '" + Parametros.codinv + "' "
					+ "AND A.APE_CODEST = '"
					+ Parametros.codest
					+ "' "
					+ "AND A.APE_TIPO = 'C' "
					// esanchez scre004168 01/03/2016 Se filtran que el cajero
					// no haya realizado el cierre
					+ "AND (A.APE_CIERRE = 'N' OR A.APE_CIERRE IS NULL) "
					+ "AND A.APE_FECHA_APE = TO_DATE('"
					+ fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
					+ "', 'DD/MM/YYYY')");
			resultado.set((List<String>) query.getResultList());
			em.getTransaction().commit();
			resultado.setResultado(TipoResultado.SUCCESS);
		} catch (NoResultException ex) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			resultado.set(null);
			resultado.setResultado(TipoResultado.SUCCESS);
		} catch (Exception ex) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			resultado.set(null);
			resultado.setMensaje("Error al consultar las aperturas. "
					+ ex.getMessage());
			resultado.setResultado(TipoResultado.ERROR);
			log.logp(Level.SEVERE, _class, "consultarAperturaCajeros",
					ex.getMessage(), ex);
		}
		return resultado;
	}

	/***
	 * Valida las aperturas y cierres del usuario
	 */
	@Override
	public Resultado<String> validadAperturaCierre(String usuario,
			LocalDate fechaTra, String iniApe, String tipApe) {
		Resultado<String> resultado = new Resultado<String>();

		Date fechaTran = Date.from(Instant.from(fechaTra.atStartOfDay(ZoneId
				.systemDefault())));

		em.getTransaction().begin();
		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ValidaCierreApertura");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodEst", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FechaTra", Date.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Usuario", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_IniApe", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_TipApe", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.setParameter("p_CodEst", Parametros.codest);
		spq.setParameter("p_FechaTra", fechaTran);
		spq.setParameter("p_Usuario", usuario);
		spq.setParameter("p_IniApe", iniApe);
		spq.setParameter("p_TipApe", tipApe);
		spq.execute();

		resultado.set(null);
		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty()) {
			resultado.set("");
			resultado.setResultado(TipoResultado.SUCCESS);
			resultado.setMensaje("");
		} else {
			resultado.set(null);
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));
			log.logp(Level.SEVERE, _class, "validadAperturaCierre",
					resultado.getMensaje());

		}

		em.getTransaction().commit();
		return resultado;
	}

	/***
	 * Inactiva la apertura del usuario y estación actual
	 */
	@Override
	public String inactivarApertura() {
		String resultado = null;

		em.getTransaction().begin();
		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_InactivarApertura");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodEst", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FechaTra", Date.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Usuario", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.setParameter("p_CodEst", Parametros.codest);
		spq.setParameter("p_FechaTra", Date.from(Instant
				.from(((LocalDate) ApplicationContext.getInstance()
						.getRegisteredObject("fechaTra")).atStartOfDay(ZoneId
						.systemDefault()))));
		spq.setParameter("p_Usuario", ((String) ApplicationContext
				.getInstance().getRegisteredObject("usuActual")));
		spq.execute();

		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty())
			resultado = "";
		else {
			resultado = (String) spq.getOutputParameterValue("p_Error");
			log.logp(Level.SEVERE, _class, "inactivarApertura", resultado);

		}

		em.getTransaction().commit();
		return resultado;
	}

	/***
	 * Valida si el Id ingresado esta activo y le pertenece a un usuario
	 */
	@Override
	public Resultado<HashMap<String, Object>> validarIdUsuario(String Id) {
		Resultado<HashMap<String, Object>> resultado = new Resultado<HashMap<String, Object>>();
		HashMap<String, Object> res = new HashMap<String, Object>();

		em.getTransaction().begin();
		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ValidaIdUsuario");
		spq.registerStoredProcedureParameter("p_Id", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Usuario", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Folio", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Validado", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_Id", Id);
		spq.execute();

		resultado.set(null);
		if (spq.getOutputParameterValue("p_Validado") != null
				&& ((String) spq.getOutputParameterValue("p_Validado"))
						.equals("S")) {

			res.put("usuario",
					((String) spq.getOutputParameterValue("p_Usuario")));
			res.put("folio", ((String) spq.getOutputParameterValue("p_Folio")));
			resultado.set(res);

			if ((String) spq.getOutputParameterValue("p_Error") == null
					|| ((String) spq.getOutputParameterValue("p_Error"))
							.isEmpty()) {
				resultado.setResultado(TipoResultado.SUCCESS);
			} else {
				resultado.setResultado(TipoResultado.WARNING);
				resultado.setMensaje((String) spq
						.getOutputParameterValue("p_Error"));
			}
		} else {
			res.put("usuario",
					((String) spq.getOutputParameterValue("p_Usuario")));
			res.put("folio", ((String) spq.getOutputParameterValue("p_Folio")));
			resultado.set(res);

			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));
			log.logp(Level.SEVERE, _class, "validarIdUsuario",
					resultado.getMensaje());

		}

		em.getTransaction().commit();

		return resultado;
	}

	/***
	 * Obtiene el nombre de un usuario
	 */
	@Override
	public String getNombreUsuario(String pFolio) {
		try {
			Query query = em
					.createNativeQuery("SELECT E.EMP_DES FROM OPENSIDE.SIDE_EMP_EMP E WHERE UPPER(E.EMP_CODIGO) = UPPER('"
							+ pFolio
							+ "') AND E.EMP_CODCIA = '"
							+ Parametros.codcia + "'");
			return (String) query.getSingleResult();
		} catch (Exception ex) {
			log.logp(Level.SEVERE, _class, "getNombreUsuario", ex.getMessage(),
					ex);
			return null;
		}
	}

	/***
	 * Obtiene el usuario del folio ingreado
	 */
	@Override
	public String getFolio(String pUsuario) {
		try {
			Query query = em
					.createNativeQuery("SELECT E.EMP_CODIGO FROM OPENSIDE.SIDE_EMP_EMP E WHERE UPPER(E.EMP_USUARIO) = UPPER('"
							+ pUsuario
							+ "') AND E.EMP_CODCIA = '"
							+ Parametros.codcia + "'");
			return (String) query.getSingleResult();
		} catch (Exception ex) {
			log.logp(Level.SEVERE, _class, "getFolio", ex.getMessage(), ex);
			return null;
		}
	}

	/***
	 * Obtiene la fecha transaccional segun el tipo de jornada y la fecha
	 */
	@Override
	public Resultado<LocalDate> getFechaTransaccional(String tipoJornada,
			Date finJornada) {
		Resultado<LocalDate> resultado = new Resultado<LocalDate>();

		em.getTransaction().begin();
		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ObtenerFechaTran");
		spq.registerStoredProcedureParameter("p_TipoJornada", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FinJor", Date.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FechaTran", Date.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_TipoJornada", tipoJornada);
		spq.setParameter("p_FinJor", finJornada);
		spq.execute();

		Date fechaTran = (Date) spq.getOutputParameterValue("p_FechaTran");
		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty()) {
			resultado.set(fechaTran.toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate());
			resultado.setResultado(TipoResultado.SUCCESS);
			resultado.setMensaje("");
		} else {
			resultado.set(null);
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));
			log.logp(Level.SEVERE, _class, "getFechaTransaccional",
					resultado.getMensaje());
		}

		em.getTransaction().commit();
		return resultado;
	}

	/***
	 * Aplica la factura ingresada
	 */
	@Override
	public Resultado<String> aplicarFactura(String codFac, String codTmp, Long codMov) {

		Resultado<String> resultado = new Resultado<String>();

		em.getTransaction().begin();

		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_AplicarFactura");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodFac", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodTmp", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);
		/**#jcalvo. scre:004698. fecha:06/07/2017.*/
		spq.registerStoredProcedureParameter("p_nomsis", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_codest", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_codmov", BigDecimal.class,
				ParameterMode.IN);
        /*************************************************************/
		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.setParameter("p_CodFac", codFac);
		spq.setParameter("p_CodTmp", codTmp);
		/**#jcalvo. scre:004698. fecha:06/07/2017.*/
		spq.setParameter("p_nomsis", Parametros.nomsis);
		spq.setParameter("p_codest", Parametros.codest);
		spq.setParameter("p_codmov", codMov);
		/*************************************************************/
		spq.execute();

		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty()) {
			resultado.set("");
			resultado.setResultado(TipoResultado.SUCCESS);
			resultado.setMensaje("");
		} else {
			resultado.set(null);
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));

			log.logp(Level.SEVERE, _class, "aplicarFactura",
					resultado.getMensaje());
		}

		em.getTransaction().commit();

		return resultado;

	}

	/***
	 * Consulta las facturas no nulas entre el rango de fechas ingresados para
	 * la estación actual
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Resultado<List<Factura>> getByFechas(Date fecIni, Date fecFin) {

		Resultado<List<Factura>> resultado = new Resultado<>();

		Query query = em.createQuery("Select fac " + "From Factura fac "
				+ "Where fac.id.codCia = :cia " + "And fac.id.tipMov = 'F' "
				+ "And fac.fecha >= :fecIni " + "And fac.fecha <= :fecFin "
				+ "And fac.movimiento.codEst = '" + Parametros.codest + "' "
				+ "And fac.nulo = 'N'" + "Order By fac.fecHorFin Desc");

		query.setParameter("cia", Parametros.codcia);
		query.setParameter("fecIni", fecIni);
		query.setParameter("fecFin", fecFin);
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado.set((List<Factura>) query.getResultList());

		return resultado;
	}

	/***
	 * Valida el cierre de la aplicación
	 */
	@Override
	public Resultado<String> salirApp() {

		Resultado<String> resultado = new Resultado<>();

		Date fechaTra = Date.from(Instant.from(((LocalDate) ApplicationContext
				.getInstance().getRegisteredObject("fechaTra"))
				.atStartOfDay(ZoneId.systemDefault())));

		String codCaj = (String) ApplicationContext.getInstance()
				.getRegisteredObject("usuActual");

		em.getTransaction().begin();

		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_Salir");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodCaj", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FecTra", Date.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodEst", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_TipMen", String.class,
				ParameterMode.OUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.setParameter("p_CodCaj", codCaj);
		spq.setParameter("p_FecTra", fechaTra);
		spq.setParameter("p_CodEst", Parametros.codest);
		spq.execute();

		if (!((String) spq.getOutputParameterValue("p_TipMen") == null)) {

			String mensaje = (String) spq.getOutputParameterValue("p_Error");

			if (((String) spq.getOutputParameterValue("p_TipMen")).equals("A")) {
				resultado.setResultado(TipoResultado.WARNING);
				resultado
						.setMensaje(mensaje + " ¿Está seguro que desea salir?");
				resultado.set("WARNING");
			} else if (((String) spq.getOutputParameterValue("p_TipMen"))
					.equals("E")) {
				resultado.setResultado(TipoResultado.ERROR);
				resultado.set("ERROR");
			}

		} else {
			resultado.setResultado(TipoResultado.SUCCESS);
			resultado.setMensaje("¿Está seguro que desea salir?");
			resultado.set("EXITO");
		}

		em.getTransaction().commit();

		return resultado;

	}

	/***
	 * Obtiene los autorizados de crédito para el cliente ingresado
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Autorizado> traerAutorizadosCredito(String codCli) {

		List<Autorizado> auts = new ArrayList<Autorizado>();
		List<Object[]> resAut = null;

		em.getTransaction().begin();

		StoredProcedureQuery spqUsaCreCom = em
				.createStoredProcedureQuery("Coopeagri.Agri_Creditos.Cre_Traerparcrecom");
		spqUsaCreCom.registerStoredProcedureParameter("p_codcia", String.class,
				ParameterMode.IN);
		spqUsaCreCom.registerStoredProcedureParameter("p_habcrecom",
				String.class, ParameterMode.INOUT);
		spqUsaCreCom.registerStoredProcedureParameter("p_usacrecom",
				String.class, ParameterMode.INOUT);

		spqUsaCreCom.setParameter("p_codcia", Parametros.codcia);
		spqUsaCreCom.execute();

		if (((String) spqUsaCreCom.getOutputParameterValue("p_habcrecom"))
				.equals("S")
				&& ((String) spqUsaCreCom
						.getOutputParameterValue("p_usacrecom")).equals("S")) {
			Query query = em
					.createNativeQuery("select aut_tipo, "
							+ "       aut_cedula, "
							+ "       aut_nombre, "
							+ "       aut_fecini, "
							+ "       aut_fecfin"
							+ " FROM openside.side_cre_aut, openside.side_cre_ope, openside.side_cre_axn "
							+ " WHERE ope_codcia = '"
							+ Parametros.codcia
							+ "' "
							+ "   AND ope_codcli = '"
							+ codCli
							+ "' "
							+ "   and ope_tipo = 'C' "
							+ "   and ope_estado = 'A' "
							+ "   And ope_situacion = 'AD' "
							+ "   and aut_codcia = ope_codcia "
							+ "   and aut_codope = ope_codigo "
							+ "   and aut_cliope = ope_codcli "
							+ "   and TO_DATE(SYSDATE, 'DD/MM/RRRR') <= AUT_FECFIN"
							+ "   AND AXN_CODCIA = AUT_CODCIA "
							+ "   AND AXN_CODOPE = AUT_CODOPE "
							+ "   AND AXN_CEDAUT = AUT_CEDULA "
							+ "   AND AXN_CODNEG = '" + Parametros.codinv
							+ "' " + "   AND AXN_APLNEG = 'S'");

			resAut = query.getResultList();
		} else {
			/*
			 * Query query = em .createNativeQuery(
			 * "SELECT DECODE(AUT_TIPO, 'C', 'CRÉDITO', 'O', 'CON ORDEN') TIPO, "
			 * + "       AUT_CEDULA CEDULA, " + "       AUT_NOMBRE NOMBRE, " +
			 * "       AUT_FECINI FECHA_INI, " + "       AUT_FECFIN FECHA_FIN "
			 * + "  FROM OPENSIDE.SIDE_CRE_AUT, OPENSIDE.SIDE_CRE_AXN " +
			 * " WHERE AUT_CODCIA = '" + Parametros.codcia + "' " +
			 * "   AND AUT_CLIOPE = '" + codCli + "' " +
			 * "   AND TO_DATE(SYSDATE, 'DD/MM/RRRR') <= AUT_FECFIN " +
			 * "   AND AXN_CODCIA(+) = AUT_CODCIA " +
			 * "   AND AXN_CODOPE(+) = AUT_CODOPE " +
			 * "   AND AXN_CEDAUT(+) = AUT_CEDULA " + "   AND AXN_CODNEG(+) = '"
			 * + Parametros.codinv + "' " + "   AND AXN_APLNEG(+) = 'S'");
			 * 
			 * resAut = query.getResultList();
			 */
			resAut = new ArrayList<>();
		}

		for (int i = 0; i < resAut.size(); i++) {
			Autorizado aut = new Autorizado();
			aut.setTipAut((String) resAut.get(i)[0]);
			aut.setCedAut((String) resAut.get(i)[1]);
			aut.setNomAut((String) resAut.get(i)[2]);
			aut.setFecIniAut((Date) resAut.get(i)[3]);
			aut.setFecFinAut((Date) resAut.get(i)[4]);
			auts.add(aut);
		}

		em.getTransaction().commit();
		return auts;
	}

	/***
	 * Actualiza los resumenes de movimientos del cajero actual
	 */
	@Override
	public Resultado<Boolean> actualizarResumenMovimientos(String Aplicar,
			BigDecimal mtoEnt, BigDecimal mtoSal, BigDecimal mtoDep,
			BigDecimal mtoOfp) {

		Resultado<Boolean> resultado = new Resultado<>();

		Date fechaTra = Date.from(Instant.from(((LocalDate) ApplicationContext
				.getInstance().getRegisteredObject("fechaTra"))
				.atStartOfDay(ZoneId.systemDefault())));

		String codCaj = (String) ApplicationContext.getInstance()
				.getRegisteredObject("usuActual");

		em.getTransaction().begin();

		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_ActPosRMov");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodCaj", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_FecTra", Date.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Aplicar", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_MtoEnt", BigDecimal.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_MtoSal", BigDecimal.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_MtoDep", BigDecimal.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_MtoOfp", BigDecimal.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.OUT);

		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.setParameter("p_CodCaj", codCaj);
		spq.setParameter("p_FecTra", fechaTra);
		spq.setParameter("p_Aplicar", Aplicar);
		spq.setParameter("p_MtoEnt", mtoEnt);
		spq.setParameter("p_MtoSal", mtoSal);
		spq.setParameter("p_MtoDep", mtoDep);
		spq.setParameter("p_MtoOfp", mtoOfp);
		spq.execute();

		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty()) {
			resultado.set(true);
			resultado.setResultado(TipoResultado.SUCCESS);
			resultado.setMensaje("");
		} else {
			resultado.set(false);
			resultado.setResultado(TipoResultado.ERROR);
			resultado.setMensaje((String) spq
					.getOutputParameterValue("p_Error"));

			log.logp(Level.SEVERE, _class, "actualizarResumenMovimientos",
					resultado.getMensaje());
		}

		em.getTransaction().commit();

		return resultado;
	}

	@Override
	public Map<String, Object> traerDatosTran(String codTdp) {

		Object[] dats = null;
		Map<String, Object> res = new HashMap<String, Object>();

		Query query = em
				.createNativeQuery("SELECT TDP_TIPPAG, TDP_HECPOR, DTD_NUMAUT, DTD_NUMTER, TDP_CODCAJ, TDP_FECHA"
						+ " FROM OPENSIDE.SIDE_VEN_TDP, OPENSIDE.SIDE_VEN_DTD "
						+ " WHERE TDP_CODIGO = '"
						+ codTdp
						+ "'"
						+ "   AND DTD_CODTDP(+) = TDP_CODIGO");

		dats = (Object[]) query.getSingleResult();

		res.put("tipo", (String) dats[0]);
		res.put("hecpor", (String) dats[1]);
		res.put("codfac", (String) dats[2]);
		if (((String) dats[3]) != null)
			res.put("numter", (String) dats[3]);
		else
			res.put("numter", Parametros.numter);
		res.put("codcaj", (String) dats[4]);
		res.put("fecha", (Date) dats[5]);

		return res;
	}

	@Override
	public Resultado<String> traerTipoCajero(String usuario) {

		Resultado<String> tipCaj = new Resultado<>();

		em.getTransaction().begin();

		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_TraerTipCajero");
		spq.registerStoredProcedureParameter("p_CodCaj", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_TipCaj", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.INOUT);

		spq.setParameter("p_CodCaj", usuario);
		spq.execute();

		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty()) {
			tipCaj.set(((String) spq.getOutputParameterValue("p_TipCaj")));
			tipCaj.setResultado(TipoResultado.SUCCESS);
			tipCaj.setMensaje("");
		} else {
			tipCaj.set("");
			tipCaj.setResultado(TipoResultado.ERROR);
			tipCaj.setMensaje((String) spq.getOutputParameterValue("p_Error"));

			log.logp(Level.SEVERE, _class, "traerTipoCajero",
					tipCaj.getMensaje());
		}

		em.getTransaction().commit();

		return tipCaj;
	}

	@Override
	public Resultado<String> traerReqAutorizacion() {

		Resultado<String> accs = new Resultado<>();

		em.getTransaction().begin();

		StoredProcedureQuery spq = em
				.createStoredProcedureQuery("OPENSIDE.SIDE_FAC.Fac_TraerReqAut");
		spq.registerStoredProcedureParameter("p_CodCia", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_CodInv", String.class,
				ParameterMode.IN);
		spq.registerStoredProcedureParameter("p_LisAcc", String.class,
				ParameterMode.INOUT);
		spq.registerStoredProcedureParameter("p_Error", String.class,
				ParameterMode.INOUT);

		spq.setParameter("p_CodCia", Parametros.codcia);
		spq.setParameter("p_CodInv", Parametros.codinv);
		spq.execute();

		if ((String) spq.getOutputParameterValue("p_Error") == null
				|| ((String) spq.getOutputParameterValue("p_Error")).isEmpty()) {
			accs.set(((String) spq.getOutputParameterValue("p_LisAcc")));
			accs.setResultado(TipoResultado.SUCCESS);
			accs.setMensaje("");
		} else {
			accs.set("");
			accs.setResultado(TipoResultado.ERROR);
			accs.setMensaje((String) spq.getOutputParameterValue("p_Error"));

			log.logp(Level.SEVERE, _class, "traerReqAutorizacion",
					accs.getMensaje());
		}

		em.getTransaction().commit();

		return accs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> traerBodegas() {

		List<Object[]> bodegas = null;

		Query query = getEntityManager().createNativeQuery(
				"SELECT BOD_CODIGO, BOD_DES " + "FROM OPENSIDE.SIDE_INV_BOD "
						+ "WHERE BOD_CODCIA = '" + Parametros.codcia + "' "
						+ "AND BOD_CODINV = '" + Parametros.codinv + "' "
						+ "AND BOD_BLOQUEADA = 'N'");
		bodegas = query.getResultList();

		return bodegas;

	}

}
