/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import java.util.ArrayList;
import jbiketso.model.entities.BikRolesUsuarios;

/**
 *
 * @author jdiego
 */
public class Aplicacion {
    private static Aplicacion INSTANCE;
    
    private static ArrayList<BikRolesUsuarios> rolesUsuario;
    private static ArrayList<AccesoPantalla> accesosUsuario;
          
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
}
