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
import java.util.Comparator;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
public class reservaFXMLController implements Initializable {

    @FXML
    private Button reservar;
    
    private Club club;
    private String login;
    private String contra;
    private Member user;

    private Court court;
    private LocalDateTime bookingDate;
    private LocalDate madeForDay;
    private LocalTime fromTime;
    @FXML
    private DatePicker picker;
    
    private int currentWeek;
    
    private int numDayNow;
    
    private LocalDate dia;
    
    private int diaPedido;
    
    private int mesPedido;
    
    private int yearPedido;
    @FXML
    private Button GoBack;
    @FXML
    private Text errorHora;
    @FXML
    private ChoiceBox<String> hora;
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
    private ChoiceBox<String> pista;
    @FXML
    private CheckBox consecutivas;
    
    private boolean dosHoras;

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
        inicializarHoras();
        inicializarPistas();
        hora.setValue((LocalTime.now().getHour() + 1)+":00");
        inicializarListView();
        
      
        
    }
    
    private void inicializarHoras(){
        ObservableList<String> horas = FXCollections.observableArrayList();
        
        for(int i = 9; i <= 21;i++){
            horas.add((i<10?"0":"")+i+":00");
        }
        
        hora.setItems(horas);
        //Problema, si son las 23h pasa a poner 24, valor que pertenece al siguiente día
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
                    reservasPista1.set(reservaPista.get(i).getFromTime().getHour()-9,reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA2":
                    reservasPista2.set(reservaPista.get(i).getFromTime().getHour()-9,reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA3":
                    reservasPista3.set(reservaPista.get(i).getFromTime().getHour()-9,reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA4":
                    reservasPista4.set(reservaPista.get(i).getFromTime().getHour()-9,reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA5":
                    reservasPista5.set(reservaPista.get(i).getFromTime().getHour()-9,reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
                    break;
                case "PISTA6":
                    reservasPista6.set(reservaPista.get(i).getFromTime().getHour()-9,reservaPista.get(i).getFromTime().getHour()+":00h - "+ (reservaPista.get(i).getFromTime().getHour() + 1) +":00h" + "\n" +reservaPista.get(i).getMember().getNickName());
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
    

    
    @FXML
    private void reservar(ActionEvent event) throws ClubDAOException, IOException{
        boolean usuarioCancela = false;
        if(this.login.equals("Iniciar Sesion")){ 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/autentificarseFXML.fxml"));   
            Parent root = loader.load();
            AutentificarseFXMLController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) reservar.getScene().getWindow();
            myStage.close();
        }else{
            List<Booking> reservas = club.getForDayBookings(picker.getValue());
            boolean noApta = false;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm",Locale.US);
            fromTime = LocalTime.parse(hora.getValue(),dtf);
            for(int i = 0; i < reservas.size(); i++){
                Booking reserva = reservas.get(i); 
                noApta = reserva.getFromTime().getHour() == (fromTime.getHour()) && reserva.isInCourt(pista.getValue());
                if(noApta){
                    break;
                }
            }
            if(!noApta){
                boolean paid = user.checkHasCreditInfo();
                court = club.getCourt(pista.getValue());
                //getCurrentDate();
                //getHour();
                //DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy",Locale.US);
                ///madeForDay = LocalDate.parse(picker.getValue(),dt);
                
                
                if(dosHoras){
                    
                    boolean mas2h2 = false;
                    List<Booking> reservaPista = club.getForDayBookings(picker.getValue());
                        boolean entra = true;
                        String a = hora.getValue().substring(0,2);
                        
                        int c = Integer.parseInt(a) + 1;
                        
                        a = c + ":00";
                        
                        for(Booking b : reservaPista){
                            if((b.getBookingDate().getHour() == Integer.parseInt(hora.getValue().substring(0,2))) ){
                                entra = false;
                              
                            }
                            if((b.getBookingDate().getHour() == Integer.parseInt(hora.getValue().substring(0,2)) + 1) ){
                                entra = false;
                                
                            }
                            if((b.getBookingDate().getHour() == Integer.parseInt(hora.getValue().substring(0,2))) && !(b.getMember().equals(user))){
                                entra = false;
                                
                            }
                            if((b.getBookingDate().getHour() == Integer.parseInt(hora.getValue().substring(0,2) + 1)) && !(b.getMember().equals(user))){
                                entra = false;
                                
                            }
                            
                        }
                        if(reservaPista.isEmpty()){
                            entra = true;
                        }
                        if(c == 22){
                           entra = false;
                        }
                        if(entra){
                            Alert alert = new Alert((AlertType.CONFIRMATION));
                            alert.setTitle("Reserva");
                            alert.setHeaderText("¿Desea reservar la pista " + pista.getValue() + " de " + hora.getValue() + "h" + " a " + (c+1) + ":00h "+"?");
                            alert.setContentText("No es posible reservar a una hora anterior a la actual\n ni reservar en una fecha anterior a la actual");
                            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
                            if (result == ButtonType.OK) {
                                entra = true;
                            }else {
                                entra = false;
                                usuarioCancela = true;
                            }
                            
                    List<Booking> reservaPistaUsuario = club.getUserBookings(login);
                    List<Booking> reservaPista2 = club.getForDayBookings(picker.getValue());
                        boolean entra2 = true;
                        for(Booking b: reservaPista2){
                            if(b.getFromTime().getHour() == Integer.parseInt(hora.getValue().substring(0,2))&& b.getMember().getNickName().equals(user.getNickName())){
                                entra2 = false;
                                break;
                            }
                            
                            if(b.getFromTime().getHour() == Integer.parseInt(hora.getValue().substring(0,2))- 2 && b.belongsToMember(login)&& b.getCourt().getName().equalsIgnoreCase(pista.getValue())){
                               
                                    for(Booking ub:reservaPistaUsuario){
                                        if(b.getFromTime().getHour() + 1 == ub.getFromTime().getHour()&& ub.getCourt().getName().equalsIgnoreCase(b.getCourt().getName())){
                                            mas2h2 = true;
                                            break;
                                        }
                                    
                                }   
                            }
                    
                            
                        }
                        }
                        
                        
                        if(!mas2h2){
                        if(entra){
                            if(fechaCorrecta() && horaCorrecta()){
                                try{
                                    dtf = DateTimeFormatter.ofPattern("HH:mm",Locale.US);
                                    fromTime = LocalTime.parse(a,dtf);
                                    Booking registerBookingDuplicated = club.registerBooking(LocalDateTime.now(), picker.getValue(), fromTime, paid, court, user);
                                    fromTime = LocalTime.parse(hora.getValue(),dtf);
                                    Booking registerBooking = club.registerBooking(LocalDateTime.now(), picker.getValue(), fromTime, paid, court, user);
                            }
                                catch(Exception e){
                                    Alert alert = new Alert((AlertType.ERROR));
                                    alert.setTitle("Error en la reserva");
                                    alert.setHeaderText("No es posible rereservar 2 horas seguidas");
                                    alert.setContentText("Horario no disponible para dicha reserva");
                                    alert.showAndWait();
                            }
                            
                            }
                            else{
                                if(!usuarioCancela){
                                    Alert alert = new Alert((AlertType.ERROR));
                                    alert.setTitle("Error en la reserva");
                                    alert.setHeaderText("No es posible realizar la reserva");
                                    alert.setContentText("No es posible reservar a una hora anterior a la actual\n ni reservar en una fecha anterior a la actual");
                                    alert.showAndWait();
                                }
                        }
                        }
                        }
                        else{
                            Alert alert = new Alert((AlertType.ERROR));
                            alert.setTitle("Error en la reserva");
                            alert.setHeaderText("No es posible rereservar mas de 2 horas seguidas");
                            alert.setContentText("Horario no disponible para dicha reserva");
                            alert.showAndWait();
                            
                            }
                        }
                 else{
                    
                    boolean mas2h = false;
                    List<Booking> reservaPistaUsuario = club.getUserBookings(login);
                    List<Booking> reservaPista = club.getForDayBookings(picker.getValue());
                        boolean entra = true;
                        for(Booking b: reservaPista){
                            if(b.getFromTime().getHour() == Integer.parseInt(hora.getValue().substring(0,2))&& b.getMember().getNickName().equals(user.getNickName())){
                                entra = false;
                                break;
                            }
                            
                            if(b.getFromTime().getHour() == Integer.parseInt(hora.getValue().substring(0,2))- 2 && b.belongsToMember(login)&& b.getCourt().getName().equalsIgnoreCase(pista.getValue())){
                               
                                    for(Booking ub:reservaPistaUsuario){
                                        if(b.getFromTime().getHour() + 1 == ub.getFromTime().getHour()&& ub.getCourt().getName().equalsIgnoreCase(b.getCourt().getName())){
                                            mas2h = true;
                                            break;
                                        }
                                    
                                }   
                            }
                    
                            
                        }
                        
                    if(entra){
                            Alert alert = new Alert((AlertType.CONFIRMATION));
                            alert.setTitle("Reserva");
                            alert.setHeaderText("¿Desea reservar la pista " + pista.getValue() + " de " + hora.getValue() + "h" + " a " + (Integer.parseInt(hora.getValue().substring(0,2))+1) + ":00h "+"?");
                            alert.setContentText("No es posible reservar a una hora anterior a la actual\n ni reservar en una fecha anterior a la actual");
                            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
                            if (result == ButtonType.OK) {
                                entra = true;
                            }else {
                                entra = false;
                                usuarioCancela = true;
                            }
                            
                        }
                        
                    if(entra){
                        if(fechaCorrecta() && horaCorrecta()){
                            if(!mas2h){
                                if(!usuarioCancela){
                                    try{
                                    Booking registerBookingUnica = club.registerBooking(LocalDateTime.now(), picker.getValue(), fromTime, paid, court, user);
                                    }
                                    catch(Exception e){
                                        Alert alert = new Alert((AlertType.ERROR));
                                        alert.setTitle("Error en la reserva");
                                        alert.setHeaderText("No es posible realizar la reserva");
                                        alert.setContentText("No es posible reservar si otro usuario ya ha reservadi la pista");
                                        alert.showAndWait();
                                    }
                                }
                                
                            }
                            else{
                                Alert alert = new Alert((AlertType.ERROR));
                                alert.setTitle("Error en la reserva");
                                alert.setHeaderText("No es posible rereservar mas de 2 horas seguidas");
                                alert.setContentText("Horario no disponible para dicha reserva");
                                alert.showAndWait();
                            }
                        }
                        else{
                            Alert alert = new Alert((AlertType.ERROR));
                            alert.setTitle("Error en la reserva");
                            alert.setHeaderText("No es posible realizar la reserva");
                            alert.setContentText("No es posible reservar a una hora anterior a la actual\n ni reservar en una fecha anterior a la actual");
                            alert.showAndWait();
                        }
                    }
                    else{
                        Alert alert = new Alert((AlertType.ERROR));
                        alert.setTitle("Error en la reserva");
                        alert.setHeaderText("No es posible realizar la reserva");
                        alert.setContentText("No es posible reservar mas de 2 horas consecutivas\n ni reservar 2 pistas distintas a la misma hora");
                        alert.showAndWait();

                    }
                }
                
                inicializarListView();
            }else{
                //Sacar alert para decir reserva ya existente
                Alert alert = new Alert((AlertType.INFORMATION));
                alert.setTitle("Error en la reserva");
                alert.setHeaderText("No es posible realizar la reserva");
                alert.setContentText(pista.getValue() + " ya reservada \n " + "Por favor seleccione otra pista u hora");
                alert.showAndWait();
                
            }
        }
    }
   
    private boolean fechaCorrecta(){
        return picker.getValue().compareTo(LocalDate.now())>=0;       
    }
    
    private boolean horaCorrecta(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm",Locale.US);
        LocalTime lc = LocalTime.parse(hora.getValue(),dtf);
        if(picker.getValue().compareTo(LocalDate.now())>0) return true;
        return lc.compareTo(LocalTime.now())>0;
    }
    
    
    
    public void init(String log, String pass){
        this.login = log;
        this.contra = pass;
        user = club.getMemberByCredentials(login, contra);
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
}

    @FXML
    private void horasCosecutivas(ActionEvent event) {
        dosHoras = consecutivas.isSelected();
    }



    
}