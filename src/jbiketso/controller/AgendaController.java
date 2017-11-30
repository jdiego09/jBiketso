/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiketso.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import jfxtras.scene.control.CalendarPicker;
import jfxtras.scene.control.agenda.Agenda;

/**
 * FXML Controller class
 *
 * @author jcalvo
 */
public class AgendaController extends Controller implements Initializable {

   CalendarView calendarView = new CalendarView();
   Calendar calendario = new Calendar("citas");

   @FXML
   private JFXButton jbtnSalir;

   @FXML
   private Agenda agdAgenda;
   @FXML
   private CalendarPicker cldCalendario;

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
      Thread updateTimeThread;
      // TODO
      updateTimeThread = new Thread("Calendar: Update Time Thread") {
         @Override
         public void run() {
            while (true) {
               Platform.runLater(() -> {
                  calendarView.setToday(LocalDate.now());
                  calendarView.setTime(LocalTime.now());
               });

               try {
                  // update every 10 seconds
                  sleep(10000);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
      };
      updateTimeThread.setPriority(Thread.MIN_PRIORITY);
      updateTimeThread.setDaemon(true);
      updateTimeThread.start();
      
   }

   @Override
   public void initialize() {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   private void init() {
      CalendarSource fuente = new CalendarSource("origen");

      fuente.getCalendars().add(calendario);
      this.agdAgenda = new Agenda();
   }

}
