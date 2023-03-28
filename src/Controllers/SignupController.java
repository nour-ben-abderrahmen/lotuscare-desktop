/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.User;
import Services.ServiceUser;
import Tools.Statics;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.nio.file.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class SignupController implements Initializable {

    @FXML
    private TextField nomInput;
    @FXML
    private TextField prenomInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField confirmPassword;
    @FXML
    private TextField telephonrInput;
    @FXML
    private TextField cinInput;
    @FXML
    private TextField imageInput;

    private File selectedFile = null;
    
    ServiceUser SU;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SU = new ServiceUser();

    }

    @FXML
    private void browseAction(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser(); //outil eli nekhdhou bih el fichier
        final Stage stage = null;// el fenetre eli bech tethal 

        File file = fileChooser.showOpenDialog(stage); //halina el fenetre w recuperina el fichier
        if (file != null) { //ntestiow est ce que fichier null wale
            //Image image1 = new Image(file.toURI().toString());
            //addImage.setImage(image1);//badalna el image
            imageInput.setText(file.getName()); //badalna el input
            selectedFile = file;
        }
    }

    @FXML
    private void signupAction(ActionEvent event) throws IOException{

        
        // Generate random int value from min to max
        int random_int = (int)Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
        String newFileName = random_int+"-"+selectedFile.getName();
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
        String hashedPassword = passwordEncoder.encode(passwordInput.getText());
        User utilisateur = new User(0, nomInput.getText(), prenomInput.getText(), emailInput.getText(), hashedPassword, telephonrInput.getText(), cinInput.getText(), "[\"ROLE_USER\"]", 0, newFileName, "");

        SU.addUser(utilisateur);
        
        
        Path sourceFile = Paths.get(selectedFile.toPath().toString());
        Path targetFile = Paths.get(Statics.uploadDirectory+newFileName);

        Files.copy(sourceFile, targetFile,StandardCopyOption.REPLACE_EXISTING);
        
        
    }

}
