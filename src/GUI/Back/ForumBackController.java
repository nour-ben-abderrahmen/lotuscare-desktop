/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Back;

import Controllers.ForumController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
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
public class ForumBackController implements Initializable {

    @FXML
    private TableColumn<Commentaire, Publication> pubc;
    @FXML
    private TableColumn<Commentaire, String> contenuc;
    @FXML
    private TableColumn<Publication, Integer> idp;
    @FXML
    private TableColumn<Publication, String> tpub;
    @FXML
    private TableColumn<Publication, String> contenup;
    @FXML
    private TableView<Commentaire> tabc;
    @FXML
    private TableView<Publication> tabp;
    @FXML
    private TableColumn<Commentaire, Integer> idcom;
    @FXML
    private TextField idpc;
    @FXML
    private TextField titrep;
    @FXML
    private Button deletep;
    @FXML
    private Button editp;
    @FXML
    private Button addp;
    @FXML
    private Button deletec;
    @FXML
    private Button editc;
    @FXML
    private Button addc;
    @FXML
    private TextArea conpub;
    @FXML
    private TextArea concom;
    @FXML
    private TextField deletefield;
    @FXML
    private TextField id_modif;
    private TextField deletefield3;
    @FXML
    private TextField deletefield2;
    @FXML
    private TextField id_modif2;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                idcom.setCellValueFactory(new PropertyValueFactory<Commentaire,Integer>("id"));

        idp.setCellValueFactory(new PropertyValueFactory<Publication,Integer>("id"));
            tpub.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            contenup.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
            pubc.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            contenuc.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
            ObservableList<Commentaire> listU1 = FXCollections.observableArrayList();
            ServiceCommentaire ps1=new ServiceCommentaire();
        try {
            ps.affpub().forEach(r->{listU.add(r);});
             ps1.affcom().forEach(r->{listU1.add(r);});
            
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabp.setItems(listU);
             tabc.setItems(listU1);
    }    
    
   
        @FXML
    private void addp(ActionEvent event) {
          if(conpub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
           else if(titrep.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir le titre !");
        alert1.showAndWait();}
          else {
     Publication pub= new Publication(titrep.getText(),conpub.getText());
     
     ServicePublication sp=new ServicePublication();
      System.out.println(pub);
     sp.addpub(pub);
      tpub.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            contenup.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabp.setItems(listU);

        }

    }   

    @FXML
    private void deletep(ActionEvent event) throws SQLException {
         ServicePublication sc=new ServicePublication();
        int id = Integer.parseInt(deletefield.getText());
        System.out.println(id);
        sc.suppub(id);   
         JOptionPane.showMessageDialog(null,"Publication supprimée");  
         
          tpub.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            contenup.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabp.setItems(listU);
    }

    @FXML
    private void editp(ActionEvent event) throws SQLException {
        
        
    if(conpub.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
  
           else if(titrep.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir le titre !");
        alert1.showAndWait();}
          else {
        
        ServicePublication sr=new ServicePublication();
       
           int id_R_modif = Integer.parseInt(id_modif.getText()) ;  
           
      Publication p=new Publication(id_R_modif,titrep.getText(),conpub.getText());
       
 System.out.println(p);
        sr.editpub(p);
 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("publication modifier avec succès!");
            JOptionPane.showMessageDialog(null,"publication modifier avec succès!");
        tpub.setCellValueFactory(new PropertyValueFactory<Publication,String>("code_pub"));
            contenup.setCellValueFactory(new PropertyValueFactory<Publication,String>("contenu_pub"));
           
            ObservableList<Publication> listU = FXCollections.observableArrayList();
            ServicePublication ps=new ServicePublication();
           
        try {
            ps.affpub().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabp.setItems(listU);

        }
    
        
        
    }
@FXML
    private void HandleMouseAction(MouseEvent event) {
        
         Publication p = (Publication) tabp.getSelectionModel().getSelectedItem();
          id_modif.setText("" +p.getId());
        titrep.setText("" +p.getCode_pub());
        conpub.setText("" +p.getContenu_pub());
         deletefield.setText("" +p.getId());
        
    }
    
    
    
    @FXML
    private void deletec(ActionEvent event) throws SQLException {
        
        
        ServiceCommentaire sc=new ServiceCommentaire();
        int id = Integer.parseInt(deletefield2.getText());
        System.out.println(id);
        sc.supcom(id);   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire supprimé avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire supprimé avec succès!");     
            pubc.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            contenuc.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
           
        try {
            ps.affcom().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabc.setItems(listU);
        
        
    }

    @FXML
    private void editc(ActionEvent event) throws SQLException {
        
        if(concom.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
  
           else if(idpc.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir l'id pub !");
        alert1.showAndWait();}
          else {
        ServicePublication pub1= new ServicePublication();
             Publication p1;
              p1 = pub1.getPubParId(Integer.parseInt(idpc.getText()));
           ServiceCommentaire sr=new ServiceCommentaire();
       
           int id_R_modif = Integer.parseInt(id_modif2.getText()) ;  
           
      Commentaire c=new Commentaire( id_R_modif,concom.getText(),p1);
       
               System.out.println(c);
        sr.editcom(c);
 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire modifiée avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire modifiée avec succès!");     
            pubc.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
            contenuc.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
           
        try {
            ps.affcom().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabc.setItems(listU);
    }
        
    }

    
    
    
    
    
    @FXML
    private void addc(ActionEvent event) {
              if(concom.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir la description!");
        alert1.showAndWait();}
  
           else if(idpc.getText().isEmpty())  {
        // afficher un message d'erreur indiquant qu'un champ est vide
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Erreur");
        alert1.setHeaderText(null);
        alert1.setContentText("Veuillez remplir l'id pub !");
        alert1.showAndWait();}
          else {
             ServicePublication pub1= new ServicePublication();
             Publication p1;
             p1 = pub1.getPubParId(Integer.parseInt(idpc.getText()));
         Commentaire com1= new Commentaire(concom.getText(),p1);
     
     ServiceCommentaire sp=new ServiceCommentaire();
         System.out.println(com1);
     sp.addcom(com1);
     
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("commentaire ajouté avec succès!");
            JOptionPane.showMessageDialog(null,"commentaire ajouté avec succès!");     
           pubc.setCellValueFactory(new PropertyValueFactory<Commentaire,Publication>("publication_id"));
           contenuc.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
           
            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
           
        try {
            ps.affcom().forEach(r->{listU.add(r);});
        } catch (SQLException ex) {
            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
        }
            tabc.setItems(listU);
    }
         
    
    }

    @FXML
    private void hand(MouseEvent event) {
         Commentaire c = (Commentaire) tabc.getSelectionModel().getSelectedItem();
         
        concom.setText("" +c.getContenu_comm());
        idpc.setText("" +c.getPublication_id());
         deletefield2.setText("" +c.getId());
         id_modif2.setText("" +c.getId());
    }
            
           
       
}
