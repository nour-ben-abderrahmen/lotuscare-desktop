/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import GUI.Back.DetailspubController;
import javafx.scene.control.Button;
import Models.Publication;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Services.ServicePublication;
import Tools.Statics;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

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
    private TableView<Publication> listpub;    
    @FXML
    private Button editpub;
    private TableColumn<Publication, String> titre;
    private TableColumn<Publication, String> description;
    @FXML
    private TextField deletefield;
    @FXML
    private TextField id_modif;
    private TableColumn<Publication, Integer> colid;
    private ListView<Publication> listview;
    @FXML
    private TextField imageInput;
   private File selectedFile = null;
    @FXML
    private ListView<Publication> liste;
    @FXML
    private Button Refresh;
    @FXML
    private Button com;
    
    
   
    
    
    
    @Override
   
public void initialize(URL url, ResourceBundle rb) {
 ServicePublication sh = new ServicePublication();
    List<Publication> publications = sh.afficher2();
    
     
    liste.setCellFactory((ListView<Publication> liste) -> {return new ListCell<Publication>() {
            @Override
            protected void updateItem(Publication Publication, boolean empty) {super.updateItem(Publication, empty);

                if (empty || Publication == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-background-color: #FFFFFF;-fx-border-width: 1px; -fx-border-color: red;-fx-padding: 5");
                    int random_int = (int)Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
                        String newFileName = random_int+"-qrCode.png";
                        //String path=Statics.uploadDirectory1+newFileName;
                        
                        Publication p=sh.getPubParId(Publication.getId());
                        
                        String data=Statics.URL+p.getCode_pub();
                       
                        try{
                            generateQRCode(data,newFileName);
                            System.out.println("done");
                        }catch(WriterException e1){
                            System.out.println(e1.getMessage());
                        }catch(IOException ex){
                            System.out.println(ex.getMessage());
                        }
                    setPrefWidth(500);
                    
                    Circle cir = new Circle();
                    cir.setRadius(35);
                    /////////
                    Label nom = new Label(Publication.getCode_pub());
                    nom.setTranslateX(200);
                    nom.setContentDisplay(ContentDisplay.CENTER);
                    nom.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-font-size: 12px;-fx-padding: 5 0 10 0;");
                    ////////////
                    Label adresse = new Label(Publication.getContenu_pub());
                    adresse.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-font-size: 20px;-fx-padding: 5 0 10 0;");
                    /////////////////
                    Label a = new Label(String.valueOf(Publication.getId()));
                    a.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-font-size: 20px;-fx-padding: 5 0 10 0;");
                    
                    
                    /////////////
                    String imagePath ="File:/C:/xampp/htdocs/New folder/pidev/pidev/Digi-Dreamers/public/uploadsforum/imagespublication/";
                    ImageView imageView = new ImageView(new Image(imagePath+Publication.getUrl_image_pub()));
                    imageView.setFitWidth(400);
                    imageView.setFitHeight(200);
                    HBox hbox1 = new HBox();
                    hbox1.setSpacing(10);
                    hbox1.setAlignment(Pos.CENTER);
                    hbox1.getChildren().add(imageView);
                    ImageView imageView1;
            try {
                imageView1 = new ImageView(new Image(new FileInputStream(Statics.uploadDirectory1+newFileName)));
                imageView1.setFitWidth(150);
                imageView1.setFitHeight(150);
                imageView1.setPreserveRatio(true);
                imageView1.setStyle("-fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
                hbox1.getChildren().add(imageView1);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
                 
                        
                    
                    HBox hbox = new HBox();
                    hbox.setSpacing(5);
                    hbox.setAlignment(Pos.CENTER);
                    vbox.getChildren().addAll(nom,hbox1,adresse,hbox);
                   
                    setGraphic(vbox);
                }
            }
        };
    });

       
        liste.getItems().addAll(publications);
        

    }   

    @FXML
    private void addpub(ActionEvent event) throws IOException {
        if (contenupub.getText().isEmpty() || codepub.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; //Empêche l'envoi du formulaire
        }    
        int random_int = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
        String newFileName = random_int + "-" + selectedFile.getName();
        ServicePublication sh = new ServicePublication();
        Publication h = new Publication();
        h.setCode_pub(codepub.getText());
        h.setContenu_pub(contenupub.getText());  
        h.setUrl_image_pub(newFileName);
        sh.addpub(h);
        Path sourceFile = Paths.get(selectedFile.toPath().toString());
        Path targetFile = Paths.get(Statics.uploadDirectory + newFileName);
        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
               // Alert alert = new Alert(Alert.AlertType.INFORMATION);
           //alert.setContentText("Publication ajouté");
         // alert.show();
         notif1("LotusCare","Publication Ajoutée");

    }
    
    @FXML
    private void suppub(ActionEvent event) throws SQLException {
        
           ServicePublication sc=new ServicePublication();
        int id = Integer.parseInt(deletefield.getText());
        System.out.println(id);
        sc.suppub(id);   
         notif2("LotusCare","Publication supprimée");
       
         
         
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
    private void HandleMouseAction(MouseEvent event) throws IOException {
        
         
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/detailspub.fxml"));
                    Parent root = loader.load();
                    Stage st=new Stage();
                    st.setScene(new Scene(root));
                    st.setTitle("Detail pub");
                    DetailspubController dpc=loader.getController();
                    dpc.getpub(liste.getSelectionModel().getSelectedItem());
                    Stage st1=(Stage) Refresh.getScene().getWindow();
                    st1.close();
                    st.show();
        
        
        
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
    public void notif1(String title, String text){
   Image img = new Image("assets/logo.png");
    Notifications notificationBuilder = Notifications.create()
    .title(title)
    .text(text)
            .graphic(new ImageView(img))
            .hideAfter(Duration.seconds(8))
            .position(Pos.BOTTOM_RIGHT);
    notificationBuilder.show();
}
    
    @FXML
    private void Refresh(ActionEvent event) {

        liste.getItems().clear();
ServicePublication sh = new ServicePublication();
    List<Publication> publications = sh.afficher2();
    
     
    liste.setCellFactory((ListView<Publication> liste) -> {return new ListCell<Publication>() {
            @Override
            protected void updateItem(Publication Publication, boolean empty) {super.updateItem(Publication, empty);

                if (empty || Publication == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    
                    VBox vbox = new VBox();
                    vbox.setSpacing(10);
                    vbox.setStyle("-fx-background-color: #FFFFFF;-fx-border-width: 1px; -fx-border-color: red;-fx-padding: 5");
                    int random_int = (int)Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
                        String newFileName = random_int+"-qrCode.png";
                        //String path=Statics.uploadDirectory1+newFileName;
                        
                        Publication p=sh.getPubParId(Publication.getId());
                        
                        String data=Statics.URL+"Le titre est  "+p.getCode_pub()+" et le contenu "+p.getContenu_pub();
                       
                        try{
                            generateQRCode(data,newFileName);
                            System.out.println("done");
                        }catch(WriterException e1){
                            System.out.println(e1.getMessage());
                        }catch(IOException ex){
                            System.out.println(ex.getMessage());
                        }
                    setPrefWidth(500);
                    
                    Circle cir = new Circle();
                    cir.setRadius(35);
                    /////////
                    Label nom = new Label(Publication.getCode_pub());
                    nom.setTranslateX(200);
                    nom.setContentDisplay(ContentDisplay.CENTER);
                    nom.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-font-size: 12px;-fx-padding: 5 0 10 0;");
                    ////////////
                    Label adresse = new Label(Publication.getContenu_pub());
                    adresse.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-font-size: 20px;-fx-padding: 5 0 10 0;");
                    /////////////////
                    Label a = new Label(String.valueOf(Publication.getId()));
                    a.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-font-size: 20px;-fx-padding: 5 0 10 0;");
                    
                    
                    /////////////
                    String imagePath ="File:/C:/xampp/htdocs/New folder/pidev/pidev/Digi-Dreamers/public/uploadsforum/imagespublication/";
                    ImageView imageView = new ImageView(new Image(imagePath+Publication.getUrl_image_pub()));
                    imageView.setFitWidth(300);
                    imageView.setFitHeight(220);
                    HBox hbox1 = new HBox();
                    hbox1.setSpacing(10);
                    hbox1.setAlignment(Pos.CENTER);
                    hbox1.getChildren().add(imageView);
                    ImageView imageView1;
            try {
                imageView1 = new ImageView(new Image(new FileInputStream(Statics.uploadDirectory1+newFileName)));
                imageView1.setFitWidth(150);
                imageView1.setFitHeight(150);
                imageView1.setPreserveRatio(true);
                imageView1.setStyle("-fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
                hbox1.getChildren().add(imageView1);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
                 
                        
                    
                    HBox hbox = new HBox();
                    hbox.setSpacing(5);
                    hbox.setAlignment(Pos.CENTER);
                    vbox.getChildren().addAll(nom,hbox1,adresse,hbox);
                   
                    setGraphic(vbox);
                }
            }
        };
    });

       
        liste.getItems().addAll(publications);
        

    } 
    private static void generateQRCode(String text,String nom) throws IOException, WriterException{
        QRCodeWriter writer= new QRCodeWriter();
        BitMatrix bm = writer.encode(text, BarcodeFormat.QR_CODE, 300, 300);
        
        MatrixToImageWriter.writeToPath(bm, "PNG", new File("C:/Users/Omar/Desktop/qrcode/"+nom).toPath());
        
    }

    @FXML
    private void oncomclick(ActionEvent event) throws IOException {
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/commentaire.fxml"));
                    Parent root = loader.load();
                    liste.getScene().setRoot(root);
    }
}

      


     
    
    

