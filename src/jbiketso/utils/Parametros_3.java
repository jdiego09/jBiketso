package cr.co.coopeagri.opentouch.model.classes;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.Properties;

public class Parametros {

	private FileInputStream configFile;

	public static String url;
	public static String usuario;
	public static String password;
	public static String codcia;
	public static String codinv;
	public static String codbod;
	public static String nomsis;
	public static String codsis;
	public static String codest;
	public static String carasatendidas;
	public static String numter;
	public static String numcom;
	public static String urlteclado;
	public static String nommaquina;
	public static String tiempoespera;
	public static String rutalog;
	public static String nomimp;
	public static String impdoc;
	public static String impfac;
	public static String impserv;
	public static String uso;
	public static String valpin;
	public static String valmesa;
	public static String nummesa;
	public static String valcomanda;
	public static String numcomanda;
	public static String valvendedor;
	public static String vendedor;
	public static String esperartarjeta;
	public static String impvou;
	public static String turnoactual;
	public static String voximporterhost;
	public static int voximporterport;
	public static String pruebassurtidor;

	/** #jcalvo. fecha:21/06/2017. */
	public final static DecimalFormat formatCan = new DecimalFormat("##0.00#");
	public final static DecimalFormat formatMon = new DecimalFormat(
			"###,##0.00");
	public final static DecimalFormat formatNum = new DecimalFormat("###");

	/** -------------------------- */

	public Parametros() {
		try {

			configFile = new FileInputStream("properties.ini");
			Properties appProperties = new Properties();
			appProperties.load(configFile);
			configFile.close();

			if (appProperties.getProperty("conexion.url") != null) {
				url = appProperties.getProperty("conexion.url");
			}
			if (appProperties.getProperty("conexion.usuario") != null) {
				usuario = appProperties.getProperty("conexion.usuario");
			}
			if (appProperties.getProperty("conexion.password") != null) {
				password = appProperties.getProperty("conexion.password");
			}
			if (appProperties.getProperty("parametros.codcia") != null) {
				codcia = appProperties.getProperty("parametros.codcia");
			}
			if (appProperties.getProperty("parametros.codinv") != null) {
				codinv = appProperties.getProperty("parametros.codinv");
			}
			if (appProperties.getProperty("parametros.codbod") != null) {
				codbod = appProperties.getProperty("parametros.codbod");
			}
			if (appProperties.getProperty("parametros.nomsis") != null){
				nomsis = appProperties.getProperty("parametros.nomsis");
			}
			if (appProperties.getProperty("parametros.codsis") != null) {
				codsis = appProperties.getProperty("parametros.codsis");
			}
			if (appProperties.getProperty("parametros.codest") != null) {
				codest = appProperties.getProperty("parametros.codest");
			}
			if (appProperties.getProperty("parametros.carasatendidas") != null) {
				carasatendidas = appProperties.getProperty("parametros.carasatendidas");
			}			
			if (appProperties.getProperty("parametros.numter") != null) {
				numter = appProperties.getProperty("parametros.numter");
			}
			if (appProperties.getProperty("parametros.numcom") != null) {
				numcom = appProperties.getProperty("parametros.numcom");
			}
			if (appProperties.getProperty("parametros.urlteclado") != null) {
				urlteclado = appProperties.getProperty("parametros.urlteclado");
			}
			if (appProperties.getProperty("parametros.tiempoespera") != null) {
				tiempoespera = appProperties
						.getProperty("parametros.tiempoespera");
			}
			if (appProperties.getProperty("parametros.rutalog") != null) {
				rutalog = appProperties.getProperty("parametros.rutalog");
			}
			if (appProperties.getProperty("parametros.nomimp") != null) {
				nomimp = appProperties.getProperty("parametros.nomimp");
			}
			if (appProperties.getProperty("parametros.impdoc") != null) {
				impdoc = appProperties.getProperty("parametros.impdoc");
			}
			if (appProperties.getProperty("parametros.impfac") != null) {
				impfac = appProperties.getProperty("parametros.impfac");
			}
			if (appProperties.getProperty("parametros.impserv") != null) {
				impserv = appProperties.getProperty("parametros.impserv");
			}
			if (appProperties.getProperty("parametros.uso") != null) {
				uso = appProperties.getProperty("parametros.uso");
			}
			if (appProperties.getProperty("parametros.valpin") != null) {
				valpin = appProperties.getProperty("parametros.valpin");
			}
			if (appProperties.getProperty("parametros.valmesa") != null) {
				valmesa = appProperties.getProperty("parametros.valmesa");
			}
			if (appProperties.getProperty("parametros.nummesa") != null) {
				nummesa = appProperties.getProperty("parametros.nummesa");
			}
			if (appProperties.getProperty("parametros.valcomanda") != null) {
				valcomanda = appProperties.getProperty("parametros.valcomanda");
			}
			if (appProperties.getProperty("parametros.numcomanda") != null) {
				numcomanda = appProperties.getProperty("parametros.numcomanda");
			}
			if (appProperties.getProperty("parametros.valvendedor") != null) {
				valvendedor = appProperties
						.getProperty("parametros.valvendedor");
			}
			if (appProperties.getProperty("parametros.vendedor") != null) {
				vendedor = appProperties.getProperty("parametros.vendedor");
			}
			if (appProperties.getProperty("parametros.esperartarjeta") != null) {
				esperartarjeta = appProperties
						.getProperty("parametros.esperartarjeta");
			}
			if (appProperties.getProperty("parametros.impvou") != null) {
				impvou = appProperties.getProperty("parametros.impvou");
			}			
			if (appProperties.getProperty("parametros.voximporter.host") != null) {
				voximporterhost = appProperties.getProperty("parametros.voximporter.host");
			}			
			if (appProperties.getProperty("parametros.voximporter.port") != null) {
				voximporterport = Integer.valueOf(appProperties.getProperty("parametros.voximporter.port")).intValue();
			}
			if (appProperties.getProperty("parametros.pruebassurtidor") != null){
				pruebassurtidor = appProperties.getProperty("parametros.pruebassurtidor");
			}
			
			nommaquina = InetAddress.getLocalHost().getHostName().toUpperCase();
		} catch (IOException io) {
			System.out.println("Archivo de configuración no encontrado");
		}
	}

	public static String formatNum(BigDecimal numero, String mascara) {
		String mascaraAnt = formatNum.toPattern();
		String resultado;
		formatNum.applyPattern(mascara);
		resultado = formatNum.format(numero);
		formatNum.applyPattern(mascaraAnt);
		return resultado;
	}

}
