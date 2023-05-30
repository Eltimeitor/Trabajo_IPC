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
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author david
 */
public class reservasUsuarioFXMLController implements Initializable {

    @FXML
    private DatePicker picker;
    @FXML
    private Text errorHora;
    @FXML
    private Button GoBack;
    @FXML
    private ListView<String> lvPista1;
    @FXML
    private ListView<String> lvPista2;
    @FXML
    private ListView<String> lvPista3;
    @FXML
    private ListView<String> lvPista4;
    @FXML
    private ListView<String> lvPista5;
    @FXML
    private ListView<String> lvPista6;
    
    private Club club;
    private String login;
    private String contra;
    private Member user;
    @FXML
    private Button eliminar;
    private eliminarReservaUsuarioFXMLController controller;
    private reservasUsuarioFXMLController thiscontroller;
    private Stage stage;
    private String hora;
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
        inicializarListView();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/eliminarReserva/eliminarReservaUsuarioFXML.fxml")); 
        Parent root;
        try {
            root = loader.load();
            controller = loader.getController();
            
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(reservasUsuarioFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    
    public void init(String log, String pass, reservasUsuarioFXMLController controller){
        this.login = log;
        this.contra = pass;
        user = club.getMemberByCredentials(login, contra);
        thiscontroller = controller;
       inicializarListView();
    }



    @FXML
    private void goback(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/menuFXML.fxml")); 
            Parent root = loader.load();
            menuFXMLController controller = loader.getController();
            controller.init(login, contra);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) GoBack.getScene().getWindow();
            myStage.close();
            
            
            if((this.controller.getMyStage().isShowing())){
                this.stage.close();
            }
    }

    @FXML
    private void eliminarReserva(ActionEvent event) throws IOException {
            
            
            if(!(controller.getMyStage().isShowing())){
                   
                
                
                controller.init(login, contra, thiscontroller,picker.getValue());
                stage.setResizable(false);
                stage.show();
            }
           
            
    }
    
        @FXML
    private void inicializarListView() {
        inicializarListView(picker.getValue());
    }
    
    public void inicializarListView(LocalDate value) {
        
        List<Booking> reservaPista = club.getForDayBookings(value);
        
        ObservableList<String> reservasPista1 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista2 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista3 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista4 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista5 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista6 = FXCollections.observableArrayList();
        
        
        
        for(int i = 0; i < reservaPista.size();i++){
            switch (reservaPista.get(i).getCourt().getName().toUpperCase().replace(" ","")) {
                case "PISTA1":
                    if(reservaPista.get(i).getMember().getNickName().equals(login))
                        reservasPista1.add(reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA2":
                    if(reservaPista.get(i).getMember().getNickName().equals(login))
                        reservasPista2.add(reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA3":
                    if(reservaPista.get(i).getMember().getNickName().equals(login))
                        reservasPista3.add(reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA4":
                    if(reservaPista.get(i).getMember().getNickName().equals(login))
                        reservasPista4.add(reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA5":
                    if(reservaPista.get(i).getMember().getNickName().equals(login))
                        reservasPista5.add(reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA6":
                    if(reservaPista.get(i).getMember().getNickName().equals(login))
                        reservasPista6.add(reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                default:
                    System.out.println("Pista no encontrada:" + reservaPista.get(i).getCourt().getName().toUpperCase().trim() );
                       
            }
        }
        lvPista1.setItems(reservasPista1);
        lvPista2.setItems(reservasPista2);
        lvPista3.setItems(reservasPista3);
        lvPista4.setItems(reservasPista4);
        lvPista5.setItems(reservasPista5);
        lvPista6.setItems(reservasPista6);
        
        picker.setValue(value);
    }
    
    public void eliminarList(LocalDate ld, String pista, LocalTime horas){
        
        this.hora = horas.toString();
        List<Booking> reservaPista = club.getForDayBookings(ld);
        Booking aux = null;
        for(Booking b : reservaPista){
            if(pista.equalsIgnoreCase(b.getCourt().getName()) && b.getFromTime().getHour() == horas.getHour()){
                aux = b;
                break;
            }
        }
        if(aux == null){
            Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setTitle("Error al eliminar reserva");
                alert.setHeaderText("Reserva inexistente");
                alert.setContentText("No exite la reserva que desea eliminar");
                alert.showAndWait();
        }else if(!fechaCorrecta(aux)){
            Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setTitle("Error al eliminar reserva");
                alert.setHeaderText("No es posible eliminar la reserva");
                alert.setContentText("No es posible eliminar un reserva pasada");
                alert.showAndWait();
        }else if(!horaCorrecta(aux)){
            Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setTitle("Error al eliminar reserva");
                alert.setHeaderText("No es posible eliminar la reserva");
                alert.setContentText("No es posible eliminar una reserva con\n un plazo menor a 24h");
                alert.showAndWait();
        }else{
            
            try {
                club.removeBooking(aux);
                inicializarListView(picker.getValue());
            } catch (ClubDAOException ex) {
                Logger.getLogger(reservasUsuarioFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
        
        
    }
    
    private boolean fechaCorrecta(Booking b){
        LocalDateTime ldt = LocalDateTime.now().minusHours(24);
        return ldt.toLocalDate().compareTo(b.getMadeForDay()) <= 0;       
    }
    
    private boolean horaCorrecta(Booking b){
        LocalDateTime ldt = LocalDateTime.now().plusHours(24);
        if(b.getMadeForDay().compareTo(ldt.toLocalDate()) == 0){
            return ldt.toLocalTime().compareTo(b.getFromTime()) < 0;
        }
        else if(b.getMadeForDay().compareTo(ldt.toLocalDate()) < 0){
            return false;
        }
        return true;
    }
    
}
