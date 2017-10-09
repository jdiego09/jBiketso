package cr.co.coopeagri.opentouch.model.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cr.co.coopeagri.opentouch.model.classes.Autorizado;
import cr.co.coopeagri.opentouch.model.classes.Resultado;
import cr.co.coopeagri.opentouch.model.entitys.DetFormaPago;
import cr.co.coopeagri.opentouch.model.entitys.Factura;
import cr.co.coopeagri.opentouch.model.entitys.FacturaPK;

public interface FacturaDao extends DaoBase<Factura, FacturaPK> {
	public Resultado<List<Factura>> getByCliente(String cliente);

	Resultado<HashMap<String, Object>> procesarPagoTarjeta(String tipoMov, String codTemp,
			Date fechaTra, String tarHab2, String moneda, String numTarEm,
			String numTarj, String fecVenc, BigDecimal totMto, String track2);

	Boolean imprimirVoucher(String tipo, Boolean reimpresion, String cajero, 
			String tarjeta,	String numAut, String numRef, String numFac, 
			BigDecimal monto, String tarHab, Boolean original, Boolean reversion, 
			String numTer, String codEst, Date fecha);

	List<Object[]> traerImpuestoVenta();
	
	Resultado<HashMap<String, Object>> verificarPin(String folio, String pin);

	String getNombreUsuario(String pFolio);

	Resultado<LocalDate> getFechaTransaccional(String tipoJornada, Date finJornada);

	Long getCodigoMovimiento();

	Resultado<String> aplicarFactura(String codFac, String codTmp, Long codMov);

	String traerCodigoTemp();

	String inactivarApertura();

	Resultado<HashMap<String, Object>> reversarPagoTarjeta(
			DetFormaPago detFormaPago, String tipoMov, String codTemp, String obs);

	Resultado<List<Factura>> getByFechas(Date fecIni, Date fecFin);

	Resultado<String> salirApp();

	Boolean imprimirFactura(String numeroFactura, String tipoFactura,
			Boolean original, Boolean imprimeComoContado, Boolean reimpresion,
			Boolean firmas, String codTfa, String tipFacCon, String tipFacCre);

	List<Autorizado> traerAutorizadosCredito(String codCli);

	Resultado<Boolean> actualizarResumenMovimientos(String Aplicar, BigDecimal mtoEnt,
			BigDecimal mtoSal, BigDecimal mtoDep, BigDecimal mtoOfp);

	Map<String, Object> traerDatosTran(String codTdp);

	Resultado<String> traerTipoCajero(String usuario);

	Resultado<String> traerReqAutorizacion();

	List<Object[]> traerBodegas();

	Resultado<List<Factura>> getDevoluciones(String tipoDevolucion,
			String codigoTipoCredito, Date fechaDesde);


	Resultado<HashMap<String, Object>> validarIdUsuario(String Id);

	Resultado<String> validadAperturaCierre(String usuario, LocalDate fechaTra,
			String iniApe, String tipApe);

	Resultado<String> consultarExisteApertura(LocalDate fecha, String cajero);

	Resultado<List<String>> consultarAperturaCajeros(LocalDate fecha);

	String getFolio(String pUsuario);

	
}
