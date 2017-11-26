/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

/**
 *
 * @author jcalvo
 */
public abstract class Controller {

    protected String busqueda;

    public abstract void initialize();
    public abstract void initialize(String funcion);
}
