/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaFx;


import entities.Produit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import javafx.stage.Stage;



public class FXMain extends Application {
    
   @Override
    public void start(Stage primaryStage) {
          try {
        Parent root ;
      
            root = FXMLLoader.load(getClass().getResource("InterfaceProduit.fxml"));
       
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("GESTION DES PRODUITS!");
        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
        primaryStage.show();
     } catch (IOException ex) {
        System.out.println(ex.getMessage());        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
