/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import jbiketso.model.entities.BikDetalleAgenda;
import jfxtras.icalendarfx.components.VEvent;

/**
 *
 * @author jcalvo
 */
public class EventoAgenda extends VEvent {

    BikDetalleAgenda eventoBD;

    public EventoAgenda() {
    }

    public EventoAgenda(VEvent source) {
        super(source);
    }

    public BikDetalleAgenda getEventoBD() {
        return eventoBD;
    }

    public void setEventoBD(BikDetalleAgenda eventoBD) {
        this.eventoBD = eventoBD;
        
        this.setSummary(this.eventoBD.getDeaTitulo());
        this.setDescription(this.eventoBD.getDeaDetalle());
        this.setDateTimeStart(this.eventoBD.getDeaFechainicio().toString());
        this.setDateTimeEnd(this.eventoBD.getDeaFechafin().toString());
    }
}
