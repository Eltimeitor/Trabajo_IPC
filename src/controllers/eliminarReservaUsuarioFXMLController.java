/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author david
 */
public class eliminarReservaUsuarioFXMLController implements Initializable {

    @FXML
    private DatePicker picker;
    @FXML
    private ComboBox<String> hora;
    @FXML
    private Button GoBack;
    private Club club;
    private String login;
    private String contra;
    private Member user;
    @FXML
    private Button eliminar;
    reservasUsuarioFXMLController userController;
    @FXML
    private ComboBox<String> pista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(reservasUsuarioFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reservasUsuarioFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        picker.setValue(LocalDate.now());
        picker.setValue(LocalDate.now());
        inicializarHoras();
        inicializarPistas();
        hora.setValue((LocalTime.now().getHour() + 1)+":00");
        

    }    
    public void init(String log, String pass, reservasUsuarioFXMLController controller,LocalDate hora){
        this.login = log;
        this.contra = pass;
        user = club.getMemberByCredentials(login, contra);
        userController = controller;
        picker.setValue(hora);
        userController.fechaVisible();
    } 
    
    private void inicializarHoras(){
        ObservableList<String> horas = FXCollections.observableArrayList();
        
        for(int i = 9; i <= 21;i++){
            horas.add((i<10?"0":"")+i+":00");
        }
        
        hora.setItems(horas);
        
        hora.setValue((LocalTime.now().getHour() + 1)+":00");
    }
    
    private void inicializarPistas(){
        ObservableList<String> pistas = FXCollections.observableArrayList();
        
        for(Court c : club.getCourts()){
            pistas.add(c.getName());
        }
        
        pista.setItems(pistas);
        pista.setValue(pistas.get(0));
    }
    

    @FXML
    private void goback(ActionEvent event) {
        Stage myStage = (Stage) GoBack.getScene().getWindow();
        myStage.close();
    }
    public Stage getMyStage(){
        return (Stage) GoBack.getScene().getWindow();
    }

    

    @FXML
    private void eliminar(ActionEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm",Locale.US);
        LocalTime h = LocalTime.parse(hora.getValue(),dtf);
        userController.eliminarList(picker.getValue(), pista.getValue(),h);
        Stage myStage = (Stage) GoBack.getScene().getWindow();
        myStage.close();
        
    }

    @FXML
    private void inicializarListView() {
        userController.inicializarTableView(picker.getValue());
       
    }
}
