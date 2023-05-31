/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Member;
import model.Club;
import model.ClubDAO;
import model.ClubDAOException; 

/**
 * FXML Controller class
 *
 * @author david
 */
public class menuFXMLController implements Initializable {

    @FXML
    private Text textouser;
    @FXML
    private ImageView imagenuser;
    private Image avatar;
    
    private String login;
    private String contra;
    Member user;
    
    private Club club;
    
    private List<Member> miembros = new ArrayList();
    @FXML
    private Button reserva;
    @FXML
    private Button verperfil;
    @FXML
    private Button reservas;
    @FXML
    private Button misReservas;
    @FXML
    private Button cerrarSesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(menuFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(menuFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
    }    
    
    
    public void init(String log, String pass){
        try {
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(menuFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(menuFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.login = log;
        textouser.setText(login);
        this.contra = pass;
        user = club.getMemberByCredentials(log, pass);
        imagenuser.setImage(user.getImage());
    }

    @FXML
    private void abrirPerfil(MouseEvent event) throws IOException {
        if(login.equals("Iniciar Sesion") && contra.equals("noLog")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/autentificarseFXML.fxml"));   
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) verperfil.getScene().getWindow();
            myStage.close();
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/perfil/perfilFXML.fxml"));   
            Parent root = loader.load();
            perfilFXMLController controller = loader.getController();
            controller.init(login,contra);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) verperfil.getScene().getWindow();
            myStage.close();
        }
    }

    @FXML
    private void reservar(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reserva/reservaFXML.fxml"));   
            Parent root = loader.load();
            reservaFXMLController controller = loader.getController();
            controller.init(login,contra);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) reserva.getScene().getWindow();
            myStage.close();
    }

    @FXML
    private void verReservas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/verReservas/verReservasFXML.fxml"));   
            Parent root = loader.load();
            reservasFXMLController controller = loader.getController();
            controller.init(login,contra);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) reserva.getScene().getWindow();
            myStage.close();
    }

    @FXML
    private void verMisReservas(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservasUsuario/reservasUsuarioFXML.fxml"));   
            Parent root = loader.load();
            reservasUsuarioFXMLController controller = loader.getController();
            controller.init(login,contra,controller);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) misReservas.getScene().getWindow();
            myStage.close();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/inicio/inicioFXML.fxml"));   
            Parent root;
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) misReservas.getScene().getWindow();
            myStage.close();
    }

    

    

    
    
    
}
