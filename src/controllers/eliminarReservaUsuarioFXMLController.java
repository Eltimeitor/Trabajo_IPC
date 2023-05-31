/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
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
        if(LocalTime.now().getHour()>21 || LocalTime.now().getHour()<9)
            hora.setValue("9:00");
        else
            hora.setValue((LocalTime.now().getHour() + 1)+":00");
        

    }    
    public void init(String log, String pass, reservasUsuarioFXMLController controller,LocalDate hora){
        this.login = log;
        this.contra = pass;
        user = club.getMemberByCredentials(login, contra);
        userController = controller;
        picker.setValue(hora);
        userController.fechaVisible();
        Stage myStage = (Stage) GoBack.getScene().getWindow();
        myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    goback();
                }
            });
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
    private void goback() {
        Stage myStage = (Stage) GoBack.getScene().getWindow();
        myStage.close();
        userController.inicializarTableViewUsuario();
        userController.fechaNoVisible();
    }
    public Stage getMyStage(){
        return (Stage) GoBack.getScene().getWindow();
    }

    

    @FXML
    private void eliminar(ActionEvent event) {
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Reserva");
        alert.setHeaderText("¿Desea eliminar la reserva de la pista " + pista.getValue() + " a las " + hora.getValue() + "h" + "?");
        alert.setContentText("No es posible reservar a una hora anterior a la actual\n ni reservar en una fecha anterior a la actual");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm",Locale.US);
            LocalTime h = LocalTime.parse(hora.getValue(),dtf);
            userController.eliminarList(picker.getValue(), pista.getValue(),h);
            if(fechaCorrecta() && horaCorrecta()){
            Alert alertSI = new Alert((Alert.AlertType.INFORMATION));
                alertSI.setTitle("Reserva eliminada");
                alertSI.setHeaderText("Reserva eliminada correctamente");
                alertSI.setContentText("Reserva de la pista " + pista.getValue() + " a las " + hora.getValue() + "h eliminada correctamente");
                alertSI.showAndWait();
            }
        }else {
            Alert alertNO = new Alert((Alert.AlertType.INFORMATION));
                alertNO.setTitle("Error al eliminar reserva");
                alertNO.setHeaderText("Reserva inexistente");
                alertNO.setContentText("No exite la reserva que desea eliminar");
                alertNO.showAndWait();
        }
        

        
    }

    @FXML
    private void inicializarListView() {
        userController.inicializarTableView(picker.getValue());
       
    }
    private boolean fechaCorrecta(){
        LocalDateTime ldt = LocalDateTime.now().minusHours(24);
        return ldt.toLocalDate().compareTo(picker.getValue()) <= 0;       
    }
    
    private boolean horaCorrecta(){
        LocalDateTime ldt = LocalDateTime.now().plusHours(24);
        if(picker.getValue().compareTo(ldt.toLocalDate()) == 0){
            return ldt.toLocalTime().getHour() < Integer.parseInt(hora.getValue().substring(0,2));
        }
        else if(picker.getValue().compareTo(ldt.toLocalDate()) < 0){
            return false;
        }
        return true;
    }
    public void setPicker(LocalDate value){
        picker.setValue(value);
    }
}
