/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Back;

import Controllers.ForumController;
import Models.Commentaire;
import Models.Publication;
import Services.ServiceCommentaire;
import Services.ServicePublication;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class CommentaireController implements Initializable {

    @FXML
    private TextArea contenucom;
    @FXML
    private Button btaddcom;
    @FXML
    private Button btdeletecom;
    @FXML
    private Button bteditcom;
    @FXML
    private TextField deletefield2;
    @FXML
    private TextField idpub;
    @FXML
    private TableView<Commentaire> listcom;
    @FXML
    private TableColumn<Commentaire, Publication> pub;
    @FXML
    private TableColumn<Commentaire, String> com;
    @FXML
    private TextField id_modif2;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            idpub.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            contenucom.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
           
        try {
            ps.affcom().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listcom.setItems(listU);
    }    

    @FXML
    
         private void addcom(ActionEvent event) {
         Commentaire com= new Commentaire(contenucom.getText(),idpub.getText());
     
     ServiceCommentaire sp=new ServiceCommentaire();
     
     sp.addcom(com);
    
    }

    @FXML
         private void supcom(ActionEvent event) throws SQLException {
        ServiceCommentaire sc=new ServiceCommentaire();
        int id = Integer.parseInt(deletefield2.getText());
        sc.supcom(id);   
         JOptionPane.showMessageDialog(null,"Commentaire supprimée"); 
    }
    
    @FXML
    private void editcom(ActionEvent event) throws SQLException {
        
           ServiceCommentaire sr=new ServiceCommentaire();
       
           int id_R_modif = Integer.parseInt(id_modif2.getText()) ;  
           
      Commentaire c=new Commentaire(id_R_modif,contenucom.getText(),idpub.getText());
       

        sr.editcom(c);
 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire modifiée avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire modifiée avec succès!");
        
    }

    @FXML
    private void HandleMouseAction(MouseEvent event) {
        
    }
    
}
