/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.scene.control.Button;
import Models.Publication;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Services.ServicePublication;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author Nour
 */
public class ForumController implements Initializable {

    @FXML
    private TextArea contenupub;
    @FXML
    private TextField codepub;
    @FXML
    private Button btadd;
    @FXML
    private Button btdelete;
    @FXML
    private AnchorPane listviewlabel;
    @FXML
    private TableView<Publication> listpub;    
    @FXML
    private Button editpub;
    @FXML
    private TableColumn<Publication, String> titre;
    @FXML
    private TableColumn<Publication, String> description;
    @FXML
    private TextField deletefield;
    @FXML
    private TextField id_modif;
    @FXML
    private TableColumn<Publication, Integer> colid;
 
    
    
   
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        colid.setCellValueFactory(new PropertyValueFactory<Publication,Integer>("id"));
            titre.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            description.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
            
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listpub.setItems(listU);
    }    

    
     
    
   
    @FXML
    private void addpub(ActionEvent event) {
          if(contenupub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
           else if(codepub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir le titre !");
        alert1.showAndWait();}
          else {
     Publication pub= new Publication(codepub.getText(),contenupub.getText());
     
     ServicePublication sp=new ServicePublication();
      System.out.println(pub);
     sp.addpub(pub);
      titre.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            description.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listpub.setItems(listU);

        }

    }
    
    
    
    @FXML
    private void suppub(ActionEvent event) throws SQLException {
        
           ServicePublication sc=new ServicePublication();
        int id = Integer.parseInt(deletefield.getText());
        System.out.println(id);
        sc.suppub(id);   
         JOptionPane.showMessageDialog(null,"Publication supprimée");  
         
          titre.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            description.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listpub.setItems(listU);

        }
         
    
    
    
   
    @FXML
    private void editpub(ActionEvent event) throws SQLException {
        if(contenupub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
  
           else if(codepub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir le titre !");
        alert1.showAndWait();}
          else {
        
        ServicePublication sr=new ServicePublication();
       
           int id_R_modif = Integer.parseInt(id_modif.getText()) ;  
           
      Publication p=new Publication(id_R_modif,codepub.getText(),contenupub.getText());
       
 System.out.println(p);
        sr.editpub(p);
 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("publication modifier avec succès!");
            JOptionPane.showMessageDialog(null,"publication modifier avec succès!");
        titre.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            description.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            listpub.setItems(listU);

        }
    
    }
    

    @FXML
    private void HandleMouseAction(MouseEvent event) {
        
         Publication p = (Publication) listpub.getSelectionModel().getSelectedItem();
          id_modif.setText("" +p.getId());
        codepub.setText("" +p.getCode_pub());
        contenupub.setText("" +p.getContenu_pub());
         deletefield.setText("" +p.getId());
        
    }

   

}
     
    
    

