package cr.co.coopeagri.opentouch.model.interfaces;

import javax.persistence.EntityManager;

import cr.co.coopeagri.opentouch.model.classes.Resultado;

public interface DaoFactory {
	public EntityManager getEntityManager();
	
	public default Resultado<Void> beginTransaction(){
    	Resultado<Void> resultado = new Resultado<Void>();
		if (!getEntityManager().getTransaction().isActive()){
			getEntityManager().getTransaction().begin();
		}
		return(resultado);	
	}

	public default Resultado<Void> commitTransaction(){
    	Resultado<Void> resultado = new Resultado<Void>();
		if (getEntityManager().getTransaction().isActive()){
			getEntityManager().getTransaction().commit();
		}
		return(resultado);	
	}

	public default Resultado<Void> rollbackTransaction(){
    	Resultado<Void> resultado = new Resultado<Void>();
		if (getEntityManager().getTransaction().isActive()){
			getEntityManager().getTransaction().rollback();
		}
		return(resultado);	
	}
	
	public FacturaDao getFacturaDao();
	public CategoriaDao getCategoriaDao();
	public ArticuloDao getArticuloDao();
	public ClasifArticuloDao getClasifArticuloDao();
	public ParametrosInventarioDao getParametrosInventarioDao();
	public TecladoDao getTecladoDao();
	public ClienteDao getClienteDao();
	public DetFormaPagoDao getDetFormaPagoDao();
	public MonedaDao getMonedaDao();
	public FormaPagoDao getFormaPagoDao();
	public MovimientoDao getMovimientoDao();
	public ParametrosDenomDao getParametrosDenomDao();
	public ParametrosPosDao getParametrosPosDao();
	public ParametrosVentasDao getParametrosVentasDao();
	public MovimientoSolicitudDao getMovimientoSolicitudDao();
	public MovimientoDepositoDao getMovimientoDepositoDao();	
	public FacturaTemporalDao getFacturaTemporalDao();
	public ClasificacionPosDao getClasificacionPosDao();
	public MovimientoIngresoDao getMovimientoIngresoDao();
	public MovimientoEgresoDao getMovimientoEgresoDao();
	public CesionFacturaDao getCesionFacturaDao();
	public DispensadoDao getDispensadoDao();
	public BitacoraAutorizacionDao getBitacoraAutorizacionDao();
	public VendedorDao getVendedorDao();
	//jcalvo. fecha:12/06/2017.
	public DespachoCombustibleDao getDespachoCombustibleDao();
	public ParametrosTouchDao getParametrosTouchDao();
	
}
