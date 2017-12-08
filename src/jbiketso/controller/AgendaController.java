/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import jbiketso.model.dao.AgendaDao;
import jbiketso.model.entities.BikDetalleAgenda;
import jbiketso.utils.EventoAgenda;
import jbiketso.utils.Formater;
import jbiketso.utils.Resultado;
import jbiketso.utils.TipoResultado;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.time.DateTimeStart;
import jfxtras.internal.scene.control.skin.CalendarPickerControlSkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.scene.control.CalendarPicker;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

/**
 * FXML Controller class
 *
 * @author jcalvo
 */
public class AgendaController extends Controller implements Initializable {

    VCalendar vCalendar;
    ICalendarAgenda agenda;
    CalendarPicker calendarPicker;
    @FXML
    private JFXButton jbtnSalir;

    @FXML
    private AnchorPane acpRoot;

    @FXML
    void regresar(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vCalendar = new VCalendar();
        vCalendar.setVEvents(new ArrayList<>());
        agenda = new ICalendarAgenda(vCalendar);
        calendarPicker = new CalendarPicker();
        calendarPicker.setCalendar(java.util.Calendar.getInstance()); // set to today
        calendarPicker.setPrefWidth(300);
        calendarPicker.setPrefHeight(250);
        calendarPicker.setLayoutX(30);
        calendarPicker.setLayoutY(70);
        calendarPicker.setSkin(new CalendarPickerControlSkin(calendarPicker));

        // bind picker to agenda
        agenda.displayedCalendar().bind(calendarPicker.calendarProperty());
                
                
        // bind picker to agenda
        agenda.selectedAppointments().addListener((ListChangeListener.Change<? extends Agenda.Appointment> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("jota-nuevo evento-" + c.toString());
                }
                if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        //permutate
                    }
                } else if (c.wasUpdated()) {
                    System.out.println("jota-actualiza evento-" + c.toString());
                } else {
                    for (Agenda.Appointment a : c.getRemoved()) {
                    }
                    c.getAddedSubList().forEach((a) -> {
                        System.out.println("jota-" + a.toString());
                    });
                }
            }
        });

        traerEventos();
        agenda.setSkin(new AgendaDaySkin(agenda));
        agenda.setPrefWidth(500);
        agenda.setPrefHeight(450);
        agenda.setLayoutX(345);
        agenda.setLayoutY(70);

        acpRoot.getChildren().addAll(agenda, calendarPicker);
    }

    @Override
    public void initialize() {
    }

    private void traerEventos() {
        Resultado<ArrayList<BikDetalleAgenda>> detalleAgenda = AgendaDao.getInstance().getDetalleAgenda(Date.valueOf(LocalDate.now().withDayOfMonth(1)), Date.valueOf(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())));
        if (detalleAgenda.getResultado().equals(TipoResultado.SUCCESS)) {
            ArrayList<VEvent> eventos = new ArrayList<>();

            detalleAgenda.get().stream().forEach(d -> {
                VEvent evento = new VEvent();
                evento.setSummary(d.getDeaTitulo());
                evento.setDescription(d.getDeaDetalle());
                evento.setDateTimeStart(LocalDateTime.ofInstant(d.getDeaFechainicio().toInstant(), ZoneId.systemDefault()));
                evento.setDateTimeEnd(LocalDateTime.ofInstant(d.getDeaFechafin().toInstant(), ZoneId.systemDefault()));
                eventos.add(evento);                           
            });
             vCalendar.getVEvents().addAll(eventos);    
        }

    }

}
