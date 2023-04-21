/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lotuscare;

import Controllers.TemplateController;
import Models.User;
import Services.ServiceUser;
import Tools.LocalStorage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 *
 * @author Nour
 */
public class Lotuscare extends Application {

    ServiceUser SU;

    @Override
    public void start(Stage primaryStage) {

        try {
            LocalStorage localStorage = new LocalStorage();
            User storedUser = localStorage.getStoredUser();

            if (storedUser.getId() == 0) { //stored user exists
                SU = new ServiceUser();
                SU.updateCurrentUser(storedUser);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/GUI/Front/eventsFront.fxml"));
                    Scene scene = new Scene(root, 1366, 768);
                    primaryStage.setTitle("Lotuscare");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
                Scene scene = new Scene(root, 1366, 768);
                primaryStage.setTitle("Se connecter");
                primaryStage.setScene(scene);
                primaryStage.show();
            }

        } catch (IOException ex) {
            try {
                System.out.println("error get storedUser");
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
                Scene scene = new Scene(root, 1366, 768);
                primaryStage.setTitle("Se connecter");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex1) {
                Logger.getLogger(Lotuscare.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
