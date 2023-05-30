/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Member;
import model.Club;
import model.ClubDAO;
import model.ClubDAOException; 

/**
 *
 * @author bland
 */
public class registroFXMLController implements Initializable {

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellidos;
   
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_nickname;
    @FXML
    private PasswordField txt_contrasena;
    @FXML
    private Text lblnombre;
    @FXML
    private Text lblapellido;
    @FXML
    private Text lbltelefono;
    @FXML
    private Text lblnickname;
    @FXML
    private Text lblcontrasena;
    @FXML
    private ComboBox<ImageView> imagen;
    @FXML
    private ImageView imgAvatar;
    
    private Club club;
    
    private String login;
    
    private List<Member> miembros = new ArrayList();
    @FXML
    private TextField txt_tarjeta;
    @FXML
    private TextField txt_svc;
    @FXML
    private Text lblsvc;
    @FXML
    private Button cancelar;
    @FXML
    private Button registrar;
    @FXML
    private Text existente;
    @FXML
    private Text lbltarjeta;
    
    Image avatar1;
    Image avatar2;
    Image avatar3;
    Image avatar4;
    Image avatar5;
    
    Image woman;
    Image woman2;
    Image woman3;
    Image woman4;
    Image woman5;
    Image woman6;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(registroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(registroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        miembros = club.getMembers();
        
        
         ObservableList<ImageView> avatars = FXCollections.observableArrayList();
         Image Predeterminado; 
         
         
         
         ImageView view1 = new ImageView();
         
         
         ImageView view2 = new ImageView();
         
        
         ImageView view3 = new ImageView();
         
         
         ImageView view4 = new ImageView();
         
         
         ImageView view5 = new ImageView();
         
         
         ImageView view6 = new ImageView();
        
          
         ImageView view7 = new ImageView();
         
          
         ImageView view8 = new ImageView();
         
         
         ImageView view9 = new ImageView();
         
        
         ImageView view10 = new ImageView();
         
          
         ImageView view11 = new ImageView();
         
         try {
             
            Predeterminado = new Image(new FileInputStream("src\\resources\\default.png"), 50, 60, false, false);
            view1.setImage(Predeterminado);
            
            avatar1 = new Image(new FileInputStream("src\\resources\\men.png"), 50, 60, false, false);
            view1.setImage(avatar1);
           
            avatar2 = new Image(new FileInputStream("src\\resources\\men2.png"), 50, 60, false, false);
            view2.setImage(avatar2);
           
            avatar3 = new Image(new FileInputStream("src\\resources\\men3.png"), 50, 60, false, false);
            view3.setImage(avatar3);
           
            avatar4 = new Image(new FileInputStream("src\\resources\\men4.png"), 50, 60, false, false);
            view4.setImage(avatar4);
            
            avatar5 = new Image(new FileInputStream("src\\resources\\men5.png"), 50, 60, false, false);
            view5.setImage(avatar5);
            
            woman = new Image(new FileInputStream("src\\resources\\woman.png"), 50, 60, false, false);
            view6.setImage(woman);
            
            woman2 = new Image(new FileInputStream("src\\resources\\woman2.png"), 50, 60, false, false);
            view7.setImage(woman2);
            
            woman3 = new Image(new FileInputStream("src\\resources\\woman3.png"), 50, 60, false, false);
            view8.setImage(woman3);
            
            woman4 = new Image(new FileInputStream("src\\resources\\woman4.png"), 50, 60, false, false);
            view9.setImage(woman4);
            
            woman5 = new Image(new FileInputStream("src\\resources\\woman5.png"), 50, 60, false, false);
            view10.setImage(woman5);
            
            woman6 = new Image(new FileInputStream("src\\resources\\woman6.png"), 50, 60, false, false);
            view11.setImage(woman6);
            
            
            avatars.addAll(view1, view2, view3, view4, view5, view6, view7, view8, view9, view10, view11);
            imagen.setItems(avatars);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(registroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void accept(ActionEvent event)throws ClubDAOException, IOException {
        String name = txt_nombre.getText(); 
        String surname = txt_apellidos.getText();
        String password = txt_contrasena.getText();
        String telephone = txt_telefono.getText();
        String nickName = txt_nickname.getText();
        String tarjeta = txt_tarjeta.getText();
        int svc = 0;
        if((!txt_svc.getText().equals(""))) Integer.parseInt(txt_svc.getText());
        Image avatar = imgAvatar.getImage();
        boolean valido = false;
        
        if (txt_nombre.getText().isEmpty()){
       lblnombre.setText("Campo requerido"); 
       valido = true;
       }else {
           lblnombre.setText("");
       }
        if (txt_apellidos.getText().isEmpty()){
       lblapellido.setText("Campo requerido");
       valido = true;
       }else {
           lblapellido.setText("");
       }
       if (txt_contrasena.getText().isEmpty()){
       lblcontrasena.setText("Campo requerido");
       valido = true;
       }
       else if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$")){
       lblcontrasena.setText("La contraseña debe incluir numeros\ny al menos 6 caracteres"); 
       valido = true;
       }
       else {
           lblcontrasena.setText("");
       }
        if (txt_telefono.getText().isEmpty()){
       lbltelefono.setText("Campo requerido"); 
       valido = true;
       }
        else if(txt_telefono.getText().matches("^[a-zA-Z]*$")){
            lbltelefono.setText("Formato incorrecto"); 
            valido = true;  
        }
        else {
           lbltelefono.setText("");
       }
        if (txt_nickname.getText().isEmpty()){
       lblnickname.setText("Campo requerido"); 
       valido = true;
       }else {
           lblnickname.setText("");
       }
        
        if(!tarjeta.matches("^(?=.*[0-9])(?=\\S+$).{16,}$") && !tarjeta.isEmpty()){
            lbltarjeta.setText("Informacion incorrecta");
            valido = true;
        }
        else{
            
            lbltarjeta.setText("");
            
            
            if(txt_svc.getText().isEmpty() && !tarjeta.isEmpty()){
                lblsvc.setText("Campo requerido");
                valido = true;
            }
            else if(tarjeta.isEmpty()){
                txt_svc.setText("");
            }
            else if(!txt_svc.getText().matches("^(?=.*[0-9])(?=\\S+$).{3,}$")){
                lblsvc.setText("Informacion incorrecta");
                valido = true;
            }
            else if(txt_svc.getText().matches("(?=.*[a-z])")){
                lblsvc.setText("Informacion incorrecta");
                valido = true;
            }
            else{
                lblsvc.setText("");
            }
        }
        
        
        
        
        if(!club.existsLogin(txt_nickname.getText()) && !valido){
            login = nickName;
            club.registerMember(name, surname, telephone, nickName, password, tarjeta, svc, avatar);
            
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle("Usuario registrado");
            alert.setHeaderText("Usuario registrado correctamente en el sistema");
            alert.setContentText("A continuación se redirigirá a la pantalla de inicio de sesion");
            alert.showAndWait();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmlapplication/autentificarseFXML.fxml"));   
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) registrar.getScene().getWindow();
            myStage.close();
        }
        else{
            if(club.existsLogin(txt_nickname.getText())){
            existente.setText("NickName ya registrado en el sistema");
            }
        }
    }

    @FXML
    private void elegirAvatar(ActionEvent event) {
        
      Image img = null;
      ImageView seleccionado = imagen.getValue();
      
    
       if  (avatar1.equals(seleccionado.getImage())){
            imgAvatar.setImage(avatar1);
             
       } else if (avatar2.equals(seleccionado.getImage())){
           imgAvatar.setImage(avatar2);
           } else if (avatar3.equals(seleccionado.getImage())){
           imgAvatar.setImage(avatar3);
           }  else if (avatar4.equals(seleccionado.getImage())){
           imgAvatar.setImage(avatar4);
           }
                
             else if (avatar5.equals(seleccionado.getImage())){
           imgAvatar.setImage(avatar5);
           }  else if (woman.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman);
           }
        else if (woman.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman);
           }
       else if (woman2.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman2);
           }
       else if (woman3.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman3);
           }
       else if (woman4.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman4);
           }
       else if (woman5.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman5);
           }
       else if (woman6.equals(seleccionado.getImage())){
           imgAvatar.setImage(woman6);
           }
  }

    @FXML
    private void examinarImagen(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
               // new FileChooser.ExtensionFilter("All Images", "."),
               // new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Obtener la imagen seleccionada
        Stage stage = new Stage();
        File imgFile = fileChooser.showOpenDialog(stage);

        // Mostar la imagen
        if (imgFile != null) {
            
            String url = imgFile.getAbsolutePath();
            Image avatar;
            try {
                avatar = new Image(new FileInputStream(url));
                imgAvatar.imageProperty().setValue(avatar);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(registroFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

    @FXML
    private void cancell(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelar.getScene().getWindow();
        Stage nuevaVentana = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/inicio/inicioFXML.fxml"));
        nuevaVentana.setScene(new Scene(root));
        stage.close();
        nuevaVentana.show();
    }

   
    }
    

