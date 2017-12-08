/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import jbiketso.model.entities.BikAccionesPersonal;
import jbiketso.model.entities.BikUsuariosSistema;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class UsuariosSistemaDao extends BaseDao<Integer, BikUsuariosSistema> {
    
    private static UsuariosSistemaDao INSTANCE;
    private BikUsuariosSistema usuarioSistema;
    
    public UsuariosSistemaDao() {
        
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (UsuariosSistemaDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new UsuariosSistemaDao();
                }
            }
        }
    }

    public static UsuariosSistemaDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setUsuarioSistema(BikUsuariosSistema usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }    
    
}
