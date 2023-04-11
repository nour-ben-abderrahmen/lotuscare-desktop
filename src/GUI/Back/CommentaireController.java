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
    @FXML
    private TableColumn<Commentaire, Integer> idcom;

  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                   idcom.setCellValueFactory(new PropertyValueFactory<Commentaire,Integer>("id"));

            pub.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
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
              if(contenucom.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
  
           else if(idpub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir l'id pub !");
        alert1.showAndWait();}
          else {
             ServicePublication pub1= new ServicePublication();
             Publication p1;
             p1 = pub1.getPubParId(Integer.parseInt(idpub.getText()));
         Commentaire com1= new Commentaire(contenucom.getText(),p1);
     
     ServiceCommentaire sp=new ServiceCommentaire();
         System.out.println(com1);
     sp.addcom(com1);
     
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire ajouté avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire ajouté avec succès!");     
            pub.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
           
        try {
            ps.affcom().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listcom.setItems(listU);
    }
         
    
         }
    @FXML
         private void supcom(ActionEvent event) throws SQLException {
        ServiceCommentaire sc=new ServiceCommentaire();
        int id = Integer.parseInt(deletefield2.getText());
        System.out.println(id);
        sc.supcom(id);   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire supprimé avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire supprimé avec succès!");     
            pub.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
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
    
    
    private void editcom(ActionEvent event) throws SQLException {
         if(contenucom.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
  
           else if(idpub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir l'id pub !");
        alert1.showAndWait();}
          else {
        ServicePublication pub1= new ServicePublication();
             Publication p1;
              p1 = pub1.getPubParId(Integer.parseInt(idpub.getText()));
           ServiceCommentaire sr=new ServiceCommentaire();
       
           int id_R_modif = Integer.parseInt(id_modif2.getText()) ;  
           
      Commentaire c=new Commentaire( id_R_modif,contenucom.getText(),p1);
       
               System.out.println(c);
        sr.editcom(c);
 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire modifiée avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire modifiée avec succès!");     
            pub.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
           
        try {
            ps.affcom().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listcom.setItems(listU);
    }
    }

    @FXML
    private void HandleMouseAction(MouseEvent event) {
        
         
         Commentaire c = (Commentaire) listcom.getSelectionModel().getSelectedItem();
         
        contenucom.setText("" +c.getContenu_comm());
        idpub.setText("" +c.getPublication_id());
         deletefield2.setText("" +c.getId());
         id_modif2.setText("" +c.getId());
    }
    
    
}
