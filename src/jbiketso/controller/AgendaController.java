/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.icalendarfx.components.VEvent;
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
        VCalendar vCalendar = new VCalendar();
        ICalendarAgenda agenda = new ICalendarAgenda(vCalendar);
        CalendarPicker calendarPicker = new CalendarPicker();
        calendarPicker.setCalendar(java.util.Calendar.getInstance()); // set to today
        calendarPicker.setPrefWidth(300);
        calendarPicker.setPrefHeight(250);
        calendarPicker.setLayoutX(30);
        calendarPicker.setLayoutY(70);
        calendarPicker.setSkin(new CalendarPickerControlSkin(calendarPicker));
        
        agenda.getCategories().clear();
        
        // bind picker to agenda
        agenda.displayedCalendar().bind(calendarPicker.calendarProperty());
        /*VEvent evento = new VEvent();
        evento.setSummary(summary);
        evento.setDescription(description);
        evento.setDateTimeStart(dateTimeStart);
        evento.setDateTimeEnd(dateTimeEnd);*/
        // bind picker to agenda
        //agenda.displayedCalendar().bind(cldCalendario.calendarProperty());
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

}
