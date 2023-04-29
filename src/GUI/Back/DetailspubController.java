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
import Tools.Statics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class DetailspubController implements Initializable {

    @FXML
    private Label titrepub;
    @FXML
    private Label descripub;
    @FXML
    private ImageView imgpub;
    @FXML
    private TableView<Commentaire> tablecom;
    @FXML
    private TableColumn<Commentaire, String> com;
    private Publication p1;
    @FXML
    private Button retour;
    @FXML
    private TextField idpc;
    @FXML
    private TextField id_modif2;
    @FXML
    private TextField deletefield2;
    @FXML
    private Button deletec;
    @FXML
    private Button editc;
    @FXML
    private Button addc;
    @FXML
    private TextArea concom;
    @FXML
    private TextArea contenupub;
    @FXML
    private TextField codepub;
    @FXML
    private Button btdelete;
    @FXML
    private Button editpub;
    @FXML
    private TextField deletefield;
    @FXML
    private TextField id_modif;
    @FXML
    private TextField imageInput;
    
    private File selectedFile = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         
        
        //dis();
  
    }    
    
    
    
  public void getpub (Publication p) throws FileNotFoundException{
      titrepub.setText(p.getCode_pub());
      descripub.setText(p.getContenu_pub());
      imgpub.setImage(new Image(new FileInputStream(Statics.uploadDirectory+p.getUrl_image_pub())));
     imgpub.setFitWidth(400);
     imgpub.setFitHeight(400);
     p1=p;
      System.out.println(p1);
      com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
        
         ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
            System.out.println(p1);
            ps.findcomparid(p1.getId()).forEach(r->{listU.add(r);});
            tablecom.setItems(listU);
  }

    @FXML
    private void retour(MouseEvent event) throws IOException {
         
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/Forum.fxml"));
                    Parent root = loader.load();
                    tablecom.getScene().setRoot(root);
    }


    @FXML
    private void sendid(MouseEvent event) {
        
         Commentaire c = (Commentaire) tablecom.getSelectionModel().getSelectedItem();
         
        concom.setText("" +c.getContenu_comm());  
         deletefield2.setText("" +c.getId());
         id_modif2.setText("" +c.getId());
        
        
        
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
            com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
        
         ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
            System.out.println(p1);
            ps.findcomparid(p1.getId()).forEach(r->{listU.add(r);});
            tablecom.setItems(listU);}
        
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
            com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));        
         ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
            System.out.println(p1);
            ps.findcomparid(p1.getId()).forEach(r->{listU.add(r);});
            tablecom.setItems(listU);
           
//            ObservableList<Commentaire> listU = FXCollections.observableArrayList();
//            ServiceCommentaire ps=new ServiceCommentaire();
//           
//        try {
//            ps.affcom().forEach(r->{listU.add(r);});
//        } catch (SQLException ex) {
//            Logger.getLogger(ForumController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            tablecom.setItems(listU);
        
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
          
com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));        
         ObservableList<Commentaire> listU = FXCollections.observableArrayList();
            ServiceCommentaire ps=new ServiceCommentaire();
            System.out.println(p1);
            ps.findcomparid(p1.getId()).forEach(r->{listU.add(r);});
            tablecom.setItems(listU);
        
    }
    }

    @FXML
    private void sendid2(MouseEvent event) {
          
        int publicationId = p1.getId();
        idpc.setText(String.valueOf(publicationId));
        id_modif.setText("" +p1.getId());
        codepub.setText("" +p1.getCode_pub());
        contenupub.setText("" +p1.getContenu_pub());
        imageInput.setText(""+p1.getUrl_image_pub());
         deletefield.setText("" +p1.getId());
    }

    
    
    @FXML
    private void suppub(ActionEvent event) throws SQLException {
        
           ServicePublication sc=new ServicePublication();
        int id = Integer.parseInt(deletefield.getText());
        System.out.println(id);
        sc.suppub(id);   
         notif2("LotusCare","Publication supprimée");
         
         
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/Forum.fxml"));
                    Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(DetailspubController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    deletefield.getScene().setRoot(root);
       
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
           
      Publication p=new Publication(id_R_modif,codepub.getText(),contenupub.getText(),imageInput.getText());
       
 System.out.println(p);
        sr.editpub(p);
 
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
           alert.setContentText("publication modifié avec succès!");
            JOptionPane.showMessageDialog(null,"publication modifier avec succès!");
        }
    }

    @FXML
    private void browseAction(ActionEvent event) {
        
         final FileChooser fileChooser = new FileChooser(); 
        final Stage stage = null; 

        File file = fileChooser.showOpenDialog(stage); 
        if (file != null) {    
            imageInput.setText(file.getName()); 
           selectedFile = file;
        }
    }
    
     public void notif2(String title, String text){
   Image img = new Image("assets/logo.png");
    Notifications notificationBuilder = Notifications.create()
    .title(title)
    .text(text)
            .graphic(new ImageView(img))
            .hideAfter(Duration.seconds(8))
            .position(Pos.BOTTOM_RIGHT);
    notificationBuilder.show();
}
    
    
    

}