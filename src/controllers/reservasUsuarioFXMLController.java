/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import Clases.ReservasTabla;
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
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<ReservasTabla> ReservasUser;
    @FXML
    private TableColumn<ReservasTabla, String> pistaColumn;
    @FXML
    private TableColumn<ReservasTabla, String> diaColumn;
    @FXML
    private TableColumn<ReservasTabla, String> horaColumn;
    
    @FXML
    private TableColumn<ReservasTabla, String> pagadaColumn;
    @FXML
    private Label fecha_txt;
   
    
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
        fechaNoVisible();
        
    }    
    public void init(String log, String pass, reservasUsuarioFXMLController controller){
        this.login = log;
        this.contra = pass;
        user = club.getMemberByCredentials(login, contra);
        thiscontroller = controller;
        inicializarTableViewUsuario();
    }
    @FXML
   private void inicializarTableView() {
        inicializarTableView(picker.getValue());
        controller.setPicker(picker.getValue());
    }
    public void inicializarTableView(LocalDate value) {
        picker.setValue(value);
        List<Booking> reservaPista = club.getForDayBookings(value);

        ObservableList<ReservasTabla> reservasPista1 = FXCollections.observableArrayList();
       
        
        for(int i = 0; i < reservaPista.size();i++){
           
                    if(reservaPista.get(i).getMember().getNickName().equals(login)){
                        reservasPista1.add(new ReservasTabla(reservaPista.get(i)));
        }
                   
        } 
                      
           
       
        ReservasUser.setItems(reservasPista1);
        
        pistaColumn.setCellValueFactory((pistaFila)->{return pistaFila.getValue().pista;});
        diaColumn.setCellValueFactory((diaFila)->{return diaFila.getValue().dia;});
        horaColumn.setCellValueFactory((horaFila)->{return horaFila.getValue().hora;});
        pagadaColumn.setCellValueFactory((paidFila)->{return paidFila.getValue().paid;});
    }
  public void inicializarTableViewUsuario() {
            

        List<Booking> reservaPista = club.getUserBookings(user.getNickName());

        ObservableList<ReservasTabla> reservasPista1 = FXCollections.observableArrayList();
       
        
        for(int i = reservaPista.size()-1; i > reservaPista.size()-11&&i>=0;i--){     
            reservasPista1.add(new ReservasTabla(reservaPista.get(i)));

        } 
                      
           
       
        ReservasUser.setItems(reservasPista1);
        
        pistaColumn.setCellValueFactory((pistaFila)->{return pistaFila.getValue().pista;});
        diaColumn.setCellValueFactory((diaFila)->{return diaFila.getValue().dia;});
        horaColumn.setCellValueFactory((horaFila)->{return horaFila.getValue().hora;});
        pagadaColumn.setCellValueFactory((paidFila)->{return paidFila.getValue().paid;});
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
            
            inicializarTableView(LocalDate.now()); 
        
            if(!(controller.getMyStage().isShowing())){
                controller.init(login, contra, thiscontroller,picker.getValue());
                stage.setResizable(false);
                stage.show();
                eliminar.setVisible(false);
            }
           
            
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
                 inicializarTableView(picker.getValue()); 
        } catch (ClubDAOException ex) {
                Logger.getLogger(reservasUsuarioFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
               
        
        
    }
    public void fechaVisible(){
        picker.setVisible(true);
        fecha_txt.setVisible(true);   
    }
    public void fechaNoVisible(){
        picker.setVisible(false);
        fecha_txt.setVisible(false);  
        eliminar.setVisible(true);
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
