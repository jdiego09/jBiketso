package cr.co.coopeagri.opentouch.model.classes;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.datafx.controller.flow.injection.FlowScoped;
import org.datafx.controller.injection.Async;
import org.datafx.crud.jpa.CreateOnceSupplier;

import cr.co.coopeagri.encrypt.v1.Encrypt3Des;
import cr.co.coopeagri.encrypt.v1.KeySistema;
import cr.co.coopeagri.opentouch.model.interfaces.ArticuloDao;
import cr.co.coopeagri.opentouch.model.interfaces.BitacoraAutorizacionDao;
import cr.co.coopeagri.opentouch.model.interfaces.CategoriaDao;
import cr.co.coopeagri.opentouch.model.interfaces.CesionFacturaDao;
import cr.co.coopeagri.opentouch.model.interfaces.ClasifArticuloDao;
import cr.co.coopeagri.opentouch.model.interfaces.ClasificacionPosDao;
import cr.co.coopeagri.opentouch.model.interfaces.ClienteDao;
import cr.co.coopeagri.opentouch.model.interfaces.DaoFactory;
import cr.co.coopeagri.opentouch.model.interfaces.DespachoCombustibleDao;
import cr.co.coopeagri.opentouch.model.interfaces.DetFormaPagoDao;
import cr.co.coopeagri.opentouch.model.interfaces.DispensadoDao;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaDao;
import cr.co.coopeagri.opentouch.model.interfaces.FacturaTemporalDao;
import cr.co.coopeagri.opentouch.model.interfaces.FormaPagoDao;
import cr.co.coopeagri.opentouch.model.interfaces.MonedaDao;
import cr.co.coopeagri.opentouch.model.interfaces.MovimientoDao;
import cr.co.coopeagri.opentouch.model.interfaces.MovimientoDepositoDao;
import cr.co.coopeagri.opentouch.model.interfaces.MovimientoEgresoDao;
import cr.co.coopeagri.opentouch.model.interfaces.MovimientoIngresoDao;
import cr.co.coopeagri.opentouch.model.interfaces.MovimientoSolicitudDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosDenomDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosInventarioDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosPosDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosTouchDao;
import cr.co.coopeagri.opentouch.model.interfaces.ParametrosVentasDao;
import cr.co.coopeagri.opentouch.model.interfaces.TecladoDao;
import cr.co.coopeagri.opentouch.model.interfaces.VendedorDao;

@Async
@FlowScoped
public class DefaultDaoFactory implements DaoFactory {

	private CreateOnceSupplier<EntityManager> ems;
	private CreateOnceSupplier<EntityManagerFactory> emfs;

	private CreateOnceSupplier<FacturaDao> facturaDao;
	private CreateOnceSupplier<CategoriaDao> categoriaDao;
	private CreateOnceSupplier<ArticuloDao> articuloDao;
	private CreateOnceSupplier<ClasifArticuloDao> clasifArticuloDao;
	private CreateOnceSupplier<ParametrosInventarioDao> parametrosInventarioDao;
	private CreateOnceSupplier<TecladoDao> tecladoDao;
	private CreateOnceSupplier<ClienteDao> clienteDao;
	private CreateOnceSupplier<DetFormaPagoDao> detalleFormaPagoDao;
	private CreateOnceSupplier<FormaPagoDao> formaPagoDao;
	private CreateOnceSupplier<MovimientoDao> movimientoDao;
	private CreateOnceSupplier<ParametrosPosDao> parametrosPosDao;
	private CreateOnceSupplier<ParametrosVentasDao> parametrosVentasDao;
	private CreateOnceSupplier<MonedaDao> monedaDao;
	private CreateOnceSupplier<ParametrosDenomDao> parametrosDenomDao;
	private CreateOnceSupplier<MovimientoSolicitudDao> movimientoSolicitudDao;
	private CreateOnceSupplier<MovimientoDepositoDao> movimientoDepositoDao;
	private CreateOnceSupplier<FacturaTemporalDao> facturaTemporalDao;
	private CreateOnceSupplier<ClasificacionPosDao> clasificacionPosDao;
	private CreateOnceSupplier<MovimientoIngresoDao> movimientoIngresoDao;
	private CreateOnceSupplier<MovimientoEgresoDao> movimientoEgresoDao;
	private CreateOnceSupplier<CesionFacturaDao> cesionFacturaDao;
	private CreateOnceSupplier<DispensadoDao> dispensadoDao;
	private CreateOnceSupplier<BitacoraAutorizacionDao> bitacoraAutorizacionDao;
	private CreateOnceSupplier<VendedorDao> vendedorDao;

	/** #jcalvo. fecha:15/06/2017. */
	private CreateOnceSupplier<DespachoCombustibleDao> despachoCombustibleDao;
	private CreateOnceSupplier<ParametrosTouchDao> parametrosTouchDao;

	/** ------------------------------------------------------------------------ */
	public DefaultDaoFactory() {

		emfs = new CreateOnceSupplier<>(this::crearEntityManagerFactory);
		ems = new CreateOnceSupplier<>(this::crearEntityManager);

		facturaDao = new CreateOnceSupplier<>(() -> new DefaultFacturaDao(ems));
		categoriaDao = new CreateOnceSupplier<>(() -> new DefaultCategoriaDao(
				ems));
		articuloDao = new CreateOnceSupplier<>(
				() -> new DefaultArticuloDao(ems));
		clasifArticuloDao = new CreateOnceSupplier<>(
				() -> new DefaultClasifArticuloDao(ems));
		parametrosInventarioDao = new CreateOnceSupplier<>(
				() -> new DefaultParametrosInventarioDao(ems));
		tecladoDao = new CreateOnceSupplier<>(() -> new DefaultTecladoDao(ems));
		clienteDao = new CreateOnceSupplier<>(() -> new DefaultClienteDao(ems));
		detalleFormaPagoDao = new CreateOnceSupplier<>(
				() -> new DefaultDetFormaPagoDao(ems));
		formaPagoDao = new CreateOnceSupplier<>(() -> new DefaultFormaPagoDao(
				ems));
		movimientoDao = new CreateOnceSupplier<>(
				() -> new DefaultMovimientoDao(ems));
		parametrosPosDao = new CreateOnceSupplier<>(
				() -> new DefaultParametrosPosDao(ems));
		parametrosVentasDao = new CreateOnceSupplier<>(
				() -> new DefaultParametrosVentasDao(ems));
		monedaDao = new CreateOnceSupplier<>(() -> new DefaultMonedaDao(ems));
		parametrosDenomDao = new CreateOnceSupplier<>(
				() -> new DefaultParametrosDenomDao(ems));
		movimientoSolicitudDao = new CreateOnceSupplier<>(
				() -> new DefaultMovimientoSolicitudDao(ems));
		movimientoDepositoDao = new CreateOnceSupplier<>(
				() -> new DefaultMovimientoDepositoDao(ems));
		facturaTemporalDao = new CreateOnceSupplier<>(
				() -> new DefaultFacturaTemporalDao(ems));
		clasificacionPosDao = new CreateOnceSupplier<>(
				() -> new DefaultClasificacionPosDao(ems));
		movimientoIngresoDao = new CreateOnceSupplier<>(
				() -> new DefaultMovimientoIngresoDao(ems));
		movimientoEgresoDao = new CreateOnceSupplier<>(
				() -> new DefaultMovimientoEgresoDao(ems));
		cesionFacturaDao = new CreateOnceSupplier<>(
				() -> new DefaultCesionFacturaDao(ems));
		dispensadoDao = new CreateOnceSupplier<>(
				() -> new DefaultDispensadoDao(ems));
		bitacoraAutorizacionDao = new CreateOnceSupplier<>(
				() -> new DefaultBitacoraAutorizacionDao(ems));
		vendedorDao = new CreateOnceSupplier<>(
				() -> new DefaultVendedorDao(ems));
		/** #jcalvo. fecha:15/06/2017. */
		despachoCombustibleDao = new CreateOnceSupplier<>(
				() -> new DefaultDespachoCombustibleDao(ems));
		parametrosTouchDao = new CreateOnceSupplier<>(
				() -> new DefaultParametrosTouchDao(ems));
		/**
		 * --------------------------------------------------------------------
		 * ----
		 */
	}

	private EntityManagerFactory crearEntityManagerFactory() {

		// new Parametros();

		String url = Parametros.url;
		String usuario = Parametros.usuario;
		String password = "";

		try {
			password = Encrypt3Des.decrypt(Parametros.password,
					KeySistema.OPENTOUCH.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Properties propiedades = new Properties();
		if (usuario != null) {
			propiedades.put("javax.persistence.jdbc.user", usuario);
		}
		if (password != null) {
			propiedades.put("javax.persistence.jdbc.password", password);
		}
		if (url != null) {
			propiedades.put("javax.persistence.jdbc.url", url);
		}
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(
				"opentouch", propiedades);
		return emf;
	}

	private EntityManager crearEntityManager() {

		EntityManager em = emfs.get().createEntityManager();

		em.getTransaction().begin();

		Query query = em
				.createNativeQuery("BEGIN "
						+ "OPENSIDE.PkG_AppRoles.P_SetParameter('APLICACION', 'OPENTOUCH'); "
						+ "OPENSIDE.PkG_AppRoles.P_AsignaRoles; " + "END;");
		query.executeUpdate();

		em.getTransaction().commit();

		return em;
	}

	@Override
	public EntityManager getEntityManager() {
		return ems.get();
	}

	@Override
	public FacturaDao getFacturaDao() {
		return facturaDao.get();
	}

	@Override
	public CategoriaDao getCategoriaDao() {
		return categoriaDao.get();
	}

	@Override
	public ArticuloDao getArticuloDao() {
		return articuloDao.get();
	}

	@Override
	public ClasificacionPosDao getClasificacionPosDao() {
		return clasificacionPosDao.get();
	}

	@Override
	public ClasifArticuloDao getClasifArticuloDao() {
		return clasifArticuloDao.get();
	}

	@Override
	public ParametrosInventarioDao getParametrosInventarioDao() {
		return parametrosInventarioDao.get();
	}

	@Override
	public TecladoDao getTecladoDao() {
		return tecladoDao.get();
	}

	@Override
	public ClienteDao getClienteDao() {
		return clienteDao.get();
	}

	@Override
	public DetFormaPagoDao getDetFormaPagoDao() {
		return detalleFormaPagoDao.get();
	}

	@Override
	public DispensadoDao getDispensadoDao() {
		return dispensadoDao.get();
	}

	@Override
	public FormaPagoDao getFormaPagoDao() {
		return formaPagoDao.get();
	}

	@Override
	public MonedaDao getMonedaDao() {
		return monedaDao.get();
	}

	@Override
	public MovimientoDao getMovimientoDao() {
		return movimientoDao.get();
	}

	@Override
	public MovimientoDepositoDao getMovimientoDepositoDao() {
		return movimientoDepositoDao.get();
	}

	@Override
	public MovimientoEgresoDao getMovimientoEgresoDao() {
		return movimientoEgresoDao.get();
	}

	@Override
	public MovimientoIngresoDao getMovimientoIngresoDao() {
		return movimientoIngresoDao.get();
	}

	@Override
	public MovimientoSolicitudDao getMovimientoSolicitudDao() {
		return movimientoSolicitudDao.get();
	}

	@Override
	public ParametrosDenomDao getParametrosDenomDao() {
		return parametrosDenomDao.get();
	}

	@Override
	public ParametrosPosDao getParametrosPosDao() {
		return parametrosPosDao.get();
	}

	@Override
	public ParametrosVentasDao getParametrosVentasDao() {
		return parametrosVentasDao.get();
	}

	@Override
	public FacturaTemporalDao getFacturaTemporalDao() {
		return facturaTemporalDao.get();
	}

	@Override
	public CesionFacturaDao getCesionFacturaDao() {
		return cesionFacturaDao.get();
	}

	@Override
	public BitacoraAutorizacionDao getBitacoraAutorizacionDao() {
		return bitacoraAutorizacionDao.get();
	}

	@Override
	public VendedorDao getVendedorDao() {
		return vendedorDao.get();
	}

	/** #jcalvo. fecha:15/06/2017. */
	@Override
	public DespachoCombustibleDao getDespachoCombustibleDao() {
		return despachoCombustibleDao.get();
	}

	@Override
	public ParametrosTouchDao getParametrosTouchDao() {
		return parametrosTouchDao.get();
	}
	/** ------------------------------------------------------------------------ */

}
