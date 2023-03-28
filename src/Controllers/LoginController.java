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

        String result = userService.login(emailInput.getText(), passwordInput.getText());

        if (result == "success") {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Template.fxml"));
            Parent root = loader.load();
            emailInput.getScene().setRoot(root);
        }

    }

}
