/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import java.util.ArrayList;
import jbiketso.model.entities.BikModulos;
import jbiketso.model.entities.BikPermisoRol;
import jbiketso.model.entities.BikUsuariosSistema;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.control.Alert;
import jbiketso.model.dao.CentroDao;
import jbiketso.model.entities.BikCentro;
import jbiketso.model.entities.BikSede;

/**
 *
 * @author jdiego
 */
public class Aplicacion {

    private static Aplicacion INSTANCE;

    private static BikUsuariosSistema usuario;
    private static String rolesUsuario;
    private static ArrayList<BikPermisoRol> accesosUsuario;
    private static ArrayList<BikModulos> modulosUsuario;
    private static Integer defaultCentro;
    private static Integer defaultSede;

    private static BikSede sede;
    private static BikCentro centro;

    private static Resultado<Object> resultadoBusqueda;

    private Aplicacion() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (Aplicacion.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new Aplicacion();
                }
            }
        }
    }

    public static Aplicacion getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public BikSede getDefaultSede() {
        if (sede == null) {
            Resultado<BikSede> defSede = CentroDao.getInstance().findSedeByCodigo(defaultSede);
            if (!defSede.getResultado().equals(TipoResultado.SUCCESS)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Traer sede por defecto", defSede.getMensaje());
                return null;
            }
            sede = defSede.get();
        }
        return sede;
    }

    public BikCentro getDefaultCentro() {
        if (centro == null) {
            Resultado<BikCentro> defCentro = CentroDao.getInstance().findCentroByCodigo(defaultCentro);
            if (!defCentro.getResultado().equals(TipoResultado.SUCCESS)) {
                AppWindowController.getInstance().mensaje(Alert.AlertType.ERROR, "Traer centro por defecto", defCentro.getMensaje());
                return null;
            }
            centro = defCentro.get();
        }
        return centro;
    }

    public void cargaProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "biketso.properties";
            input = Aplicacion.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Error al cargar archivo de configuración" + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            if (prop.containsKey("default.centro")) {
                defaultCentro = Integer.valueOf(prop.getProperty("default.centro"));
                System.out.println("def_Centro " + prop.getProperty("default.centro"));
            }
            if (prop.containsKey("default.sede")) {
                defaultSede = Integer.valueOf(prop.getProperty("default.sede"));
                System.out.println("def_Sede " + prop.getProperty("default.sede"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public BikUsuariosSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(BikUsuariosSistema usuario) {
        Aplicacion.usuario = usuario;
    }

    public String getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(String rolesUsuario) {
        Aplicacion.rolesUsuario = rolesUsuario;
    }

    public ArrayList<BikPermisoRol> getAccesosUsuario() {
        return accesosUsuario;
    }

    public void setAccesosUsuario(ArrayList<BikPermisoRol> accesosUsuario) {
        Aplicacion.accesosUsuario = accesosUsuario;
    }

    public ArrayList<BikModulos> getModulosUsuario() {
        return modulosUsuario;
    }

    public void setModulosUsuario(ArrayList<BikModulos> modulosUsuario) {
        Aplicacion.modulosUsuario = modulosUsuario;
    }

    public Resultado<Object> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(Resultado<Object> resultadoBusqueda) {
        Aplicacion.resultadoBusqueda = resultadoBusqueda;
    }

}
