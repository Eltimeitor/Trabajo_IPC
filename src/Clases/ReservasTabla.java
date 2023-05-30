/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Booking;

/**
 *
 * @author ivpri
 */
public class ReservasTabla {

public StringProperty pista = new SimpleStringProperty();
public final StringProperty dia = new SimpleStringProperty();
public final StringProperty hora = new SimpleStringProperty();
public final StringProperty paid = new SimpleStringProperty();
public ReservasTabla(Booking reserva) {
this.pista.setValue(reserva.getCourt().getName());
this.dia.setValue(reserva.getMadeForDay().toString());
this.hora.setValue(reserva.getFromTime().toString());
this.paid.setValue(reserva.getPaid()?"✓":"✕");
}
}

