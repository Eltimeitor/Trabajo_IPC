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
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
public class reservasFXMLController implements Initializable {

    
    private Club club;
    private String login;
    private String contra;
    private Member user;

    @FXML
    private DatePicker picker;
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
    
    @FXML
    private TextField usuarioReserva;
    @FXML
    private Text errorHora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        try {
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(AutentificarseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AutentificarseFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        picker.setValue(LocalDate.now());
        inicializarListView();
        
      
        
    }
    
    
    
    @FXML
    private void inicializarListView(){
        
        List<Booking> reservaPista = club.getForDayBookings(picker.getValue());
        ObservableList<String> reservasPista1 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista2 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista3 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista4 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista5 = FXCollections.observableArrayList();
        ObservableList<String> reservasPista6 = FXCollections.observableArrayList();
        
        for(int i = 9 ; i <= 21; i++){
            reservasPista1.add(i+":00h - "+(i+1)+":00h\nPista libre");
            reservasPista2.add(i+":00h - "+(i+1)+":00h\nPista libre");
            reservasPista3.add(i+":00h - "+(i+1)+":00h\nPista libre");
            reservasPista4.add(i+":00h - "+(i+1)+":00h\nPista libre");
            reservasPista5.add(i+":00h - "+(i+1)+":00h\nPista libre");
            reservasPista6.add(i+":00h - "+(i+1)+":00h\nPista libre");
        }
        
        for(int i = 0; i < reservaPista.size();i++){
            switch (reservaPista.get(i).getCourt().getName().toUpperCase().replace(" ","")) {
                case "PISTA1":
                    reservasPista1.set(reservaPista.get(i).getFromTime().getHour() - 9,reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA2":
                    reservasPista2.set(reservaPista.get(i).getFromTime().getHour() - 9,reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA3":
                    reservasPista3.set(reservaPista.get(i).getFromTime().getHour() - 9,reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA4":
                    reservasPista4.set(reservaPista.get(i).getFromTime().getHour() - 9,reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA5":
                    reservasPista5.set(reservaPista.get(i).getFromTime().getHour() - 9,reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA6":
                    reservasPista6.set(reservaPista.get(i).getFromTime().getHour() - 9,reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
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
        
        
    }
    

    
    
    
    private boolean fechaCorrecta(){
        //Comprobar que la fecha sea mayor a la actual
       return false;
    }
    
    public void init(String log, String pass){
        this.login = log;
        this.contra = pass;
        user = club.getMemberByCredentials(login, contra);
    }
    
    @FXML
    private void goback(ActionEvent event) throws IOException {
        if(login.equals("Iniciar Sesion")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inicio/inicioFXML.fxml")); 
            Parent root = loader.load();
            InicioFXMLController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) GoBack.getScene().getWindow();
            myStage.close();
    }
        else{
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
        }
}

    @FXML
    private void buscarReservasUsuario(ActionEvent event) {
        System.out.println("entra");
    if(club.existsLogin(usuarioReserva.getText())){
        System.out.println("aqui tambin");
            List<Booking> reservaPista = club.getUserBookings(usuarioReserva.getText());
            ObservableList<String> reservasPista1 = FXCollections.observableArrayList();
            ObservableList<String> reservasPista2 = FXCollections.observableArrayList();
            ObservableList<String> reservasPista3 = FXCollections.observableArrayList();
            ObservableList<String> reservasPista4 = FXCollections.observableArrayList();
            ObservableList<String> reservasPista5 = FXCollections.observableArrayList();
            ObservableList<String> reservasPista6 = FXCollections.observableArrayList();
        
        for(int i = 0; i < reservaPista.size();i++){
            switch (reservaPista.get(i).getCourt().getName().toUpperCase().replace(" ","")) {
                case "PISTA1":
                    if(picker.getValue().equals(reservaPista.get(i).getMadeForDay())){
                            reservasPista1.add(reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    }
                    break;
                case "PISTA2":
                    if(picker.getValue().equals(reservaPista.get(i).getMadeForDay())){
                        reservasPista2.add(reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    }
                    break;
                case "PISTA3":
                    if(picker.getValue().equals(reservaPista.get(i).getMadeForDay())){
                        reservasPista3.add(reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    }
                    break;
                case "PISTA4":
                    if(picker.getValue().equals(reservaPista.get(i).getMadeForDay())){
                        reservasPista4.add(reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    }
                    break;
                case "PISTA5":
                    if(picker.getValue().equals(reservaPista.get(i).getMadeForDay())){
                        reservasPista5.add(reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    }
                    break;
                case "PISTA6":
                    if(picker.getValue().equals(reservaPista.get(i).getMadeForDay())){
                        reservasPista6.add(reservaPista.get(i).getFromTime().getHour()+":00 - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00" + "\n" +reservaPista.get(i).getMember().getNickName());
                    }
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
        }
    }

}