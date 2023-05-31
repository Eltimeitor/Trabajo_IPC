/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author david
 */
public class InicioFXMLController implements Initializable {

    @FXML
    private Button iniciosesion;
    @FXML
    private Button registro;
    @FXML
    private Button sinSesion;
    
    private Club club;
    
    private Member noUser;
    @FXML
    private HBox arriba;
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
        
        
        
        try {
            noUser = club.registerMember("noLog", "noLog", "", "Iniciar Sesion", "noLog", "", 0, null);
        } catch (ClubDAOException ex) {
            Logger.getLogger(InicioFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void iniciosesion(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/autentificarseFXML.fxml"));   
            Parent root = loader.load();
            AutentificarseFXMLController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) iniciosesion.getScene().getWindow();
            myStage.close();
    }

    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registro/registroFXML.fxml"));   
            Parent root = loader.load();
            registroFXMLController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) registro.getScene().getWindow();
            myStage.close();
    }

    @FXML
    private void sinSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/verReservas/verReservasFXML.fxml"));   
            Parent root = loader.load();
            reservasFXMLController controller = loader.getController();
            controller.init(noUser.getNickName(),noUser.getPassword());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) sinSesion.getScene().getWindow();
            myStage.close();
    }
    
}
