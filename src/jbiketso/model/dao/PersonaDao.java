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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jbiketso.model.entities.BikContacto;
import jbiketso.model.entities.BikDireccion;
import jbiketso.model.entities.BikPersona;
import jbiketso.utils.Parametros;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;

/**
 *
 * @author Luis Diego
 */
public class PersonaDao extends BaseDao<Integer, BikPersona> {

    private static PersonaDao INSTANCE;
    private BikPersona persona;

    private PersonaDao() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (PersonaDao.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new PersonaDao();
                }
            }
        }
    }

    public static PersonaDao getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void setPersona(BikPersona persona) {
        this.persona = persona;
    }

    //para que solamente exista una instancia del objeto
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * *
     * Función para obtener la información de una persona de acuerdo a su número
     * de identificación.
     *
     * @param cedula número de identificación de la persona buscada
     * @return la información de la persona buscada
     */
    public Resultado<BikPersona> getPersona(String cedula) {
        Resultado<BikPersona> result = new Resultado<>();
        try {
            Query query = getEntityManager().createNamedQuery("BikPersona.findByPerCedula");
            query.setParameter("perCedula", cedula);
            persona = (BikPersona) query.getSingleResult();

            result.setResultado(TipoResultado.SUCCESS);
            result.set(persona);
            return result;
        } catch (NoResultException nre) {
            result.setResultado(TipoResultado.WARNING);
            result.setMensaje("La persona con la cédula [" + cedula + "], no se encuentra registrada");
            return result;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            result.setResultado(TipoResultado.ERROR);
            result.setMensaje("Error al traer información de la persona con la cédula [" + cedula + "].");
            return result;
        }
    }

    public Resultado<String> cedulaValida(String cedula) {
        Resultado<String> resultado = new Resultado<>();
        if (cedula != null && !cedula.isEmpty()) {
            if (cedula.length() < 9 || cedula.length() > 25) {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.setMensaje("La cédula debe contener entre 9 y 25 caracteres.");
            } else {
                resultado.setResultado(TipoResultado.SUCCESS);
            }
        } else {
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Debe indicar el número de cédula.");
        }
        return resultado;
    }

    public Resultado<String> validaPersona(BikPersona persona) {
        Resultado<String> resultado = new Resultado<>();
        if (persona != null) {
            Resultado<String> cedulaValida = cedulaValida(persona.getPerCedula());
            if (cedulaValida.getResultado().equals(TipoResultado.ERROR)) {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.setMensaje(cedulaValida.getMensaje());
            }
            if (persona.getPerNombres() == null || persona.getPerNombres().isEmpty()) {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.setMensaje("No se ha indicado el nombre de la persona.");
            }
            if (persona.getPerPrimerapellido() == null || persona.getPerPrimerapellido().isEmpty()) {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.setMensaje("Debe indicar al menos el primer apellido de la persona.");
            }
            resultado.setResultado(TipoResultado.SUCCESS);
        } else {
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("No hay ninguna persona seleccionada.");
        }

        return resultado;
    }

    public Resultado<ArrayList<BikDireccion>> getDirecciones(BikPersona persona) {
        Resultado<ArrayList<BikDireccion>> resultado = new Resultado<>();
        ArrayList<BikDireccion> listaDirecciones = new ArrayList<>();
        List<BikDireccion> direcciones;
        try {
            Query query = getEntityManager().createNamedQuery("BikDireccion.findByCodigoPersona");
            query.setParameter("codigoPersona", persona.getPerCodigo());
            direcciones = query.getResultList();
            direcciones.stream().forEach(listaDirecciones::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaDirecciones);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer direcciones de [" + persona.getNombreCompleto() + "].");
            return resultado;
        }
    }

    public Resultado<ArrayList<BikPersona>> getTodasLasPersonas() {
        Resultado<ArrayList<BikPersona>> resultado = new Resultado<>();
        ArrayList<BikPersona> listaPersonas = new ArrayList<>();
        List<BikPersona> personas;
        try {
            Query query = getEntityManager().createNamedQuery("BikPersona.findAll");
            personas = query.getResultList();
            personas.stream().forEach(listaPersonas::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaPersonas);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer personas.");
            return resultado;
        }
    }

    public Resultado<ArrayList<BikPersona>> getPersonasFiltro(String cedula, String nombre, String primerApellido, String segundoApellido) {
        Resultado<ArrayList<BikPersona>> resultado = new Resultado<>();
        ArrayList<BikPersona> listaPersonas = new ArrayList<>();
        List<BikPersona> personas;
        try {
            Query query = getEntityManager().createNamedQuery("BikPersona.findPersonas");
            query.setParameter("cedula", cedula + "%");
            query.setParameter("nombre", nombre + "%");
            query.setParameter("primerapellido", primerApellido + "%");
            query.setParameter("segundoapellido", segundoApellido + "%");
            personas = query.getResultList();
            personas.stream().forEach(listaPersonas::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaPersonas);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer personas.");
            return resultado;
        }
    }

    public Resultado<ArrayList<BikContacto>> getContactos(BikPersona persona) {
        Resultado<ArrayList<BikContacto>> resultado = new Resultado<>();
        ArrayList<BikContacto> listaContactos = new ArrayList<>();
        List<BikContacto> contactos;
        try {
            Query query = getEntityManager().createNamedQuery("BikContacto.findByCodigoPersona");
            query.setParameter("codigoPersona", persona.getPerCodigo());
            contactos = query.getResultList();
            contactos.stream().forEach(listaContactos::add);
            resultado.setResultado(TipoResultado.SUCCESS);
            resultado.set(listaContactos);
            return resultado;
        } catch (NoResultException nre) {
            resultado.setResultado(TipoResultado.WARNING);
            return resultado;
        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al traer contactos de [" + persona.getNombreCompleto() + "].");
            return resultado;
        }
    }

    // Procedimiento para guardar la información de la persona.
    public Resultado<BikPersona> save() {
        Resultado<BikPersona> resultado = new Resultado<>();
        try {
            persona = (BikPersona) super.save(persona);

            if (persona.getPerCodigo() != null) {
                resultado.setResultado(TipoResultado.SUCCESS);
                resultado.set(persona);
                resultado.setMensaje("Persona guardada correctamente.");

            } else {
                resultado.setResultado(TipoResultado.ERROR);
                resultado.set(persona);
                resultado.setMensaje("No se pudo guardar la persona.");
            }

            return resultado;

        } catch (Exception ex) {
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al guardar la persona.");
            return resultado;
        }
    }

    public Resultado<String> deleteDireccion(BikDireccion direccion) {
        Resultado<String> resultado = new Resultado<>();
        try {

            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(direccion);
            BikDireccion existe = (BikDireccion) getEntityManager().find(BikDireccion.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(direccion)) {
                    direccion = getEntityManager().merge(direccion);
                }
                getEntityManager().remove(direccion);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar dirección de la persona.");
            return resultado;
        }
    }

    public Resultado<String> deleteContacto(BikContacto contacto) {
        Resultado<String> resultado = new Resultado<>();
        try {

            getEntityManager().getTransaction().begin();
            Integer id = (Integer) Parametros.PERSISTENCEUTIL.getIdentifier(contacto);
            BikContacto existe = (BikContacto) getEntityManager().find(BikContacto.class, id);

            if (existe != null) {
                if (!getEntityManager().contains(contacto)) {
                    contacto = getEntityManager().merge(contacto);
                }
                getEntityManager().remove(contacto);
            }
            getEntityManager().getTransaction().commit();
            resultado.setResultado(TipoResultado.SUCCESS);
            return resultado;
        } catch (Exception ex) {
            getEntityManager().getTransaction().rollback();
            Logger.getLogger(PersonaDao.class.getName()).log(Level.SEVERE, null, ex);
            resultado.setResultado(TipoResultado.ERROR);
            resultado.setMensaje("Error al eliminar contacto de la persona.");
            return resultado;
        }
    }

}
