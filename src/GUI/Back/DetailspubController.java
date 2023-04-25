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
import Tools.Statics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
//  public void dis(){
//      com.setCellValueFactory(new PropertyValueFactory<Commentaire,String>("contenu_comm"));
//        
//         ObservableList<Commentaire> listU = FXCollections.observableArrayList();
//            ServiceCommentaire ps=new ServiceCommentaire();
//            System.out.println(p1);
//            ps.findcomparid(p1.getId()).forEach(r->{listU.add(r);});
//            tablecom.setItems(listU);
//  }
}
