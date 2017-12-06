/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import jbiketso.model.entities.BikAccionesPersonal;

/**
 *
 * @author Luis Diego
 */
public class AccionPersonalDao extends BaseDao<Integer, BikAccionesPersonal> {
    
    private static AccionPersonalDao INSTANCE;
    private BikAccionesPersonal accionPersonal;
    
    public AccionPersonalDao() {
        
    }
    
    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (AccionPersonalDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new AccionPersonalDao();
                }
            }
        }
    }
    
    public static AccionPersonalDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setAccionPersonal(BikAccionesPersonal accionPersonal) {
        this.accionPersonal = accionPersonal;
    }
    
    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
}
