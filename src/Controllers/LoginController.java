/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import javafx.util.Duration;
import javafx.geometry.Pos;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class LoginController implements Initializable {

    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;

    ServiceUser userService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = new ServiceUser();
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        if (emailInput.getText().isEmpty()) {
            addNotifications("erreur", "veuillez remplir le champ email");
            return;
        }
        if (passwordInput.getText().isEmpty()) {
            addNotifications("erreur", "veuillez remplir le champ password");
            return;
        }

        String result = userService.login(emailInput.getText(), passwordInput.getText());
        if (result != "success") {
            addNotifications("erreur", result);
            return;
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/Template.fxml"));
            Parent root = loader.load();
            emailInput.getScene().setRoot(root);
        }

    }

    @FXML
    private void redirectToSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Signup.fxml"));
        Parent root = loader.load();
        emailInput.getScene().setRoot(root);
    }

    private void addNotifications(String title, String content) {

        if (content != null) {
            if (content.length() > 50) {
                content = content.substring(0, 49) + "......";
            }
        }
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(content)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.showInformation();
    }

    @FXML
    private void redirectToForgetPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ForgetPassword.fxml"));
        Parent root = loader.load();
        emailInput.getScene().setRoot(root);
    }

}
