/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import java.util.ArrayList;
import jbiketso.model.entities.BikMenu;
import jbiketso.model.entities.BikModulos;
import jbiketso.model.entities.BikPermisoRol;
import jbiketso.model.entities.BikUsuariosSistema;

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

}
