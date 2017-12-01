/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.utils;

import com.calendarfx.model.Entry;
import jbiketso.model.entities.BikDetalleAgenda;

/**
 *
 * @author jcalvo
 */
public class EventoAgenda extends Entry {

   private BikDetalleAgenda agenda;

   public EventoAgenda() {
   }

   public BikDetalleAgenda getAgenda() {
      return agenda;
   }

   public void setAgenda(BikDetalleAgenda agenda) {
      this.agenda = agenda;
   }

}
