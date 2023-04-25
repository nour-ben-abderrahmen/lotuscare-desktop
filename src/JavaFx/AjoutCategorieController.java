
package JavaFx;

import DAO.CRUDCategorie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.SendSMS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AjoutCategorieController implements Initializable {

    @FXML
    private TextField idcode;
    @FXML
    private TextField idnom;
    CRUDCategorie cat=new CRUDCategorie() ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
     @FXML
    private void ajouter_cat(ActionEvent event) {
 
        
            String code_cat=idcode.getText();
            String nom_cat=idnom.getText();
            entities.Categorie c = new entities.Categorie(code_cat,nom_cat);
            cat.ajoutercategorie(c);
            
        
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creation du Categorie");
            alert.setHeaderText("Creation du Categorie");
            alert.setContentText("Categorie créé!");
            alert.showAndWait();


           
            idcode.setText("");
         idnom.setText("");




     }
     @FXML
    private void Cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceProduit.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.show();

    }
}
