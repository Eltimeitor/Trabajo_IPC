/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
public class AutentificarseFXMLController implements Initializable {

    @FXML
    private TextField nickName;
    @FXML
    private PasswordField contras;

    private Club club;
    
    private List<Member> miembros = new ArrayList();
    @FXML
    private Hyperlink linkreg;
    @FXML
    private Button aceptar;
    @FXML
    private Button cerrar;
    @FXML
    private Text labelnick;
    @FXML
    private Text labelcontra;
    
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
        
        
        
        
        miembros = club.getMembers();
        
        
        
        
        
    }    

    
    private boolean autentificarNick(List<Member> nicks){
        String nick;
        String name = nickName.getText();
        for(int i = 0; i < nicks.size(); i++ ){
            nick = nicks.get(i).getNickName();
            if(nick.equals(name)){
                return true;
            }
        }
        
        return false;
    }
    
    private boolean autentificarPass(List<Member> passwords){
        String inipass;
        String pass = contras.getText();
        for(int i = 0; i < passwords.size(); i++ ){
            inipass = passwords.get(i).getPassword();
            if(inipass.equals(pass)){
                return true;
            }
        }
        
        return false;
    }
    
    
    @FXML
    private void accept(ActionEvent event) throws IOException {
        
        boolean validonick = autentificarNick(miembros);
        boolean validopass = autentificarPass(miembros);
        if(validonick && validopass){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/menuFXML.fxml"));   
            Parent root = loader.load();
            menuFXMLController controller = loader.getController();
            controller.init(nickName.getText(),contras.getText());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) aceptar.getScene().getWindow();
            myStage.close();
            
            
            
            
        }
        else{
            if(!validonick){
            labelnick.setText("Usuario incorrecto"); 
            }
            else{
            labelcontra.setText("Contraseña incorrecta");     
            }
       
        }
    }
    
    
    
    @FXML
    private void transicion(ActionEvent event) throws IOException {
        
        Stage stage = (Stage) linkreg.getScene().getWindow();
        Stage nuevaVentana = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/registro/registroFXML.fxml"));
        nuevaVentana.setScene(new Scene(root));
        stage.close();
        nuevaVentana.show();
        
        
        
    }

    @FXML
    private void cancell(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inicio/InicioFXML.fxml"));   
            Parent root = loader.load();
            InicioFXMLController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) aceptar.getScene().getWindow();
            myStage.close();
    }

    @FXML
    private void cambiosnick(KeyEvent event) {
        aceptar.setDisable(nickName.getText().isEmpty() || contras.getText().isEmpty());
    }

    @FXML
    private void cambiospass(KeyEvent event) {
        aceptar.setDisable(nickName.getText().isEmpty() || contras.getText().isEmpty());
    }
    
}
