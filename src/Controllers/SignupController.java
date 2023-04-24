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
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
    private TextField telephoneInput;
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
    private void signupAction(ActionEvent event) throws IOException {

        if (nomInput.getText().isEmpty()) {
            System.out.println("firstname is required");
            addNotifications("erreur", "firstname is required");
            return;
        }

        if (prenomInput.getText().isEmpty()) {
            System.out.println("lastname is required");
            addNotifications("erreur", "lastname is required");
            return;
        }

        if (emailInput.getText().isEmpty()) {
            System.out.println("email is required");
            addNotifications("erreur", "email is required");
            return;
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

        if (!pattern.matcher(emailInput.getText()).matches()) {
            addNotifications("erreur", "email is not valid");
            return;
        }

        if (passwordInput.getText().isEmpty()) {
            System.out.println("password is required");
            addNotifications("erreur", "password is required");
            return;
        }

        if (passwordInput.getText().length() < 8) {
            System.out.println("password should be at least 8 characters");
            addNotifications("erreur", "password must be at least 8 characters");
            return;
        }

        if (checkPassword(passwordInput.getText()) == false) {
            System.out.println("password should be at least 8 characters");
            addNotifications("erreur", "password must contains at least \n one capital letter, lowercase letter, and number");
            return;
        }

        if (confirmPassword.getText().isEmpty()) {
            System.out.println("confirm your password");
            addNotifications("erreur", "confirm your password");
            return;
        }

        if (!passwordInput.getText().equals(confirmPassword.getText())) {
            System.out.println("passwords dont match");
            addNotifications("erreur", "passwords dont match");
            return;
        }

        if (cinInput.getText().isEmpty()) {
            System.out.println("cin is required");
            addNotifications("erreur", "cin is required");
            return;
        }

        if (telephoneInput.getText().isEmpty()) {
            System.out.println("telephone is required");
            addNotifications("erreur", "telephone is required");
            return;
        }

        if (selectedFile == null) {
            System.out.println("profile image is required");
            addNotifications("erreur", "profile image is required");
            return;
        }

        // Generate random int value from min to max
        int random_int = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
        String newFileName = random_int + "-" + selectedFile.getName();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
        String hashedPassword = passwordEncoder.encode(passwordInput.getText());
        User utilisateur = new User(0, nomInput.getText(), prenomInput.getText(), emailInput.getText(), hashedPassword, telephoneInput.getText(), cinInput.getText(), "[\"ROLE_USER\"]", 0, newFileName, "");

        SU.addUser(utilisateur);

        Path sourceFile = Paths.get(selectedFile.toPath().toString());
        Path targetFile = Paths.get(Statics.uploadDirectory + newFileName);

        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        addNotifications("success", "user registred successfully");
        redirectToLogin();
    }

    private boolean checkPassword(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag) {
                return true;
            }
        }
        return false;
    }

    private void redirectToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
        Parent root = loader.load();
        emailInput.getScene().setRoot(root);
    }

    private void addNotifications(String title, String content) {

        if (content != null) {
            if (content.length() > 50) {
                //content = content.substring(0, 49) + "......";
            }
        }
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(content)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.showInformation();
    }
}
