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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class ProfileController implements Initializable {

    @FXML
    private ImageView profilePic;
    @FXML
    private Label fullName;
    @FXML
    private Label role;
    @FXML
    private Label telephone;
    @FXML
    private Label cin;
    @FXML
    private TextField firstnameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField cinInput;
    @FXML
    private TextField imageInput;
    @FXML
    private TextField lastnameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField telephoneInput;

    ServiceUser serviceUser;

    private File selectedFile = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        serviceUser = new ServiceUser();

        profilePic.setImage(new Image(Statics.imgPath + serviceUser.currentUser.getImage()));

        fullName.setText(serviceUser.currentUser.getPrenom() + " " + serviceUser.currentUser.getNom());
        role.setText(serviceUser.currentUser.getRoles());
        telephone.setText("Tel :" + serviceUser.currentUser.getTelephone());
        cin.setText("CIN :" + serviceUser.currentUser.getCin());

        firstnameInput.setText(serviceUser.currentUser.getPrenom());
        lastnameInput.setText(serviceUser.currentUser.getNom());
        emailInput.setText(serviceUser.currentUser.getEmail());
        passwordInput.setText("");
        telephoneInput.setText(serviceUser.currentUser.getTelephone());
        cinInput.setText(serviceUser.currentUser.getCin());

        imageInput.setText(serviceUser.currentUser.getImage());
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
    private void updateAction(ActionEvent event) throws IOException {

        if (firstnameInput.getText().isEmpty()) {
            System.out.println("firstname is required");
            return;
        }

        if (lastnameInput.getText().isEmpty()) {
            System.out.println("lastname is required");
            return;
        }

        if (emailInput.getText().isEmpty()) {
            System.out.println("email is required");
            return;
        }

        if (passwordInput.getText().isEmpty()) {
            System.out.println("password is required");
            return;
        }

        if (cinInput.getText().isEmpty()) {
            System.out.println("cin is required");
            return;
        }

        if (telephoneInput.getText().isEmpty()) {
            System.out.println("telephone is required");
            return;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
        if (passwordEncoder.matches(passwordInput.getText(), serviceUser.currentUser.getPassword())) {
            String newFileName = "";
            if (selectedFile != null) {
                int random_int = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
                newFileName = random_int + "-" + selectedFile.getName();
                Path sourceFile = Paths.get(selectedFile.toPath().toString());
                Path targetFile = Paths.get(Statics.uploadDirectory + newFileName);
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }
            if (newFileName.isEmpty()) {
                newFileName = serviceUser.currentUser.getImage();
            }
            String hashedPassword = passwordEncoder.encode(passwordInput.getText());

            User utilisateur = new User(
                    serviceUser.currentUser.getId(),
                    firstnameInput.getText(),
                    lastnameInput.getText(),
                    emailInput.getText(),
                    hashedPassword,
                    telephoneInput.getText(),
                    cinInput.getText(),
                    serviceUser.currentUser.getRoles(),
                    0, newFileName,
                    ""
            );
            serviceUser.modifierUtilisateur(utilisateur);

        } else {
            System.out.println("incorrect password");
        }

    }

}
