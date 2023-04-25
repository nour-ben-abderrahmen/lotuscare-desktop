
package JavaFx;

import DAO.CRUDCategorie;
import DAO.CRUDProduit;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Categorie;
import entities.Produit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.image.Image;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javafx.scene.image.ImageView;


public class InterfaceProduitController implements Initializable {
    CRUDProduit produitDAO=new CRUDProduit() ;
    CRUDCategorie categorieDAO=new CRUDCategorie() ;
    @FXML
    private TextField reffx;
    @FXML
    private TextField descriptionfx;
    @FXML
    private TextField prixfx;
@FXML
private Button btnUpload;
    @FXML
    private TextField imgfx;
    @FXML
    private TextField qstockfx;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<Produit,Integer> IDcol;
    @FXML
    private TableColumn<Produit, Float> PRIXcol;
    @FXML
    private TableColumn<Produit, String> REFcol;
    @FXML
    private TableColumn<Produit, String> DESCRIPTIONcol;
    @FXML
    private TableColumn<Produit, String> IMGAcol;
    @FXML
    private TableColumn<Produit,Categorie> CATcol;
    @FXML
    private TableColumn<Produit,Categorie>  QSTOCKcol;

    @FXML
    private ChoiceBox<String> fxCategorieChoiceBox;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnsupprimer;
    @FXML
    private Button btnsupprimer11;
    @FXML
    private TextField reffx1;
    @FXML
    private TextField descriptionfx1;
    @FXML
    private TextField prixfx1;
    @FXML
    private TextField imgfx1;
    @FXML
    private TextField qstockfx1;
    @FXML
    private ChoiceBox<String> fxCategorieChoiceBox1;
    @FXML
    private Button btnsupprimer111;
    @FXML
    private Button btnsupprimer1;
    @FXML
    private Button btnajouter1;
    @FXML
    private TextField filterField;
    @FXML
    private ImageView image_update;
    public static FileChooser fc = new FileChooser();
    File selectedfile;
    String path;


    CRUDCategorie cc = new CRUDCategorie();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      /*for(Categorie categorie:new CRUDCategorie().afficherCategories()){
            fxCategorieChoiceBox.getItems().add(categorie);
            System.out.println(categorie.getId());
        }
       for(Categorie categorie:new CRUDCategorie().afficherCategories()){
            fxCategorieChoiceBox1.getItems().add(categorie);
            System.out.println(categorie.getId());
        }
        table();
*/
       /* try {
            List<String> nomCat = CRUDCategorie.afficherCategories();
            fxCategorieChoiceBox.getItems().addAll((Categorie) nomCat);
        } catch (SQLException ex) {
            System.out.println("Erreur lors du chargement des noms des categories : " + ex.getMessage());
        }*/
        try {
            CRUDProduit crud = new CRUDProduit();
            ObservableList<Produit> data = FXCollections.observableArrayList(crud.afficherProduit());
            System.out.println("///////");
            System.out.println(data);
            System.out.println("///////");
            REFcol.setCellValueFactory(new PropertyValueFactory<>("ref"));
            DESCRIPTIONcol.setCellValueFactory(new PropertyValueFactory<>("description"));
            PRIXcol.setCellValueFactory(new PropertyValueFactory<>("prix"));
            QSTOCKcol.setCellValueFactory(new PropertyValueFactory<>("qte_stock"));
            CATcol.setCellValueFactory(new PropertyValueFactory<>("categorie_id"));

            table.setItems(data);
            throw new SQLException("Sample SQLException");
        } catch (SQLException ex) {
            System.out.println("here");
            Logger.getLogger(InterfaceProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Event listner in order to show the selected item in the text fields
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Only trigger on single click
                Produit produit = table.getSelectionModel().getSelectedItem();
                reffx.setText(produit.getRef());
                descriptionfx.setText(produit.getDescription());
                prixfx.setText(String.valueOf(produit.getPrix()));
                imgfx.setText(produit.getImage());
                qstockfx.setText(String.valueOf(produit.getQte_stock()));
            }
        });


        CRUDCategorie cc = new CRUDCategorie();
        try {
            List<String> catNames = cc.getNomsCat();
            ObservableList<String> observableCatNames = FXCollections.observableArrayList(catNames);
            fxCategorieChoiceBox.setItems(observableCatNames);
            fxCategorieChoiceBox1.setItems(observableCatNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    } 

    @FXML
    private void ajouter_Produit(ActionEvent event) throws MessagingException {

        if(isInputValid()) {
            String ref = reffx1.getText();
            String description = descriptionfx1.getText();
            float prix = Float.parseFloat(prixfx1.getText());
            String image = imgfx1.getText();
            int qte_stock = Integer.parseInt(qstockfx1.getText());
            String cat = fxCategorieChoiceBox1.getValue();

            int categorieId = cc.getIdCategorieByName(cat);
            entities.Produit produit = new entities.Produit(ref, description, prix, image, qte_stock, categorieId);

            produitDAO.ajouterProduit(produit, categorieId);
            table.setItems(FXCollections.observableArrayList(produitDAO.afficherProduit()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Création du produit");
            alert.setHeaderText("Création du produit");
            alert.setContentText("Produit créé!");
            alert.showAndWait();

            prixfx1.setText("");
            reffx1.setText("");
            descriptionfx1.setText("");
            imgfx1.setText("");
            qstockfx1.setText("");
            String smtpHost = "smtp.gmail.com";
            String smtpPort = "587";
            String username = "itaf.daghsen@esprit.tn";
            String password = "50432889itaf";
            String toEmail = "Daghsen13itaf@gmail.com";
            String subject = "New Product Added";
            String messageText = "A new Product has been added: " +ref+" with Only "+ prix +"      Get IT NOW";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
            System.out.println("Email sent successfully.");


        }

    }

  /*  private void ajouter_Produit(ActionEvent event) {


        if(isInputValid()){
            String ref=reffx1.getText();
            String description=descriptionfx1.getText();
            float prix=Float.parseFloat(prixfx1.getText());
            String image=imgfx1.getText();
            int qte_stock=Integer.parseInt(qstockfx1.getText());
          //  Categorie categorie = (Categorie) fxCategorieChoiceBox1.getValue();
            String Cat = fxCategorieChoiceBox1.getValue();
            //We get the name of the cat then w pass it through the method getIdCategorieByName
            // to get its ID and then we pass it to the constructor
            int CategorieId= cc.getIdCategorieByName(Cat);
            entities.Produit p = new entities.Produit(ref,description,prix,image,qte_stock,CategorieId);
            System.out.println(p);

            produitDAO.ajouterProduit(p,CategorieId);
            table.setItems(FXCollections.observableArrayList(produitDAO.afficherProduit()));
        
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creation de la Produit");
            alert.setHeaderText("Creation de la Produit");
            alert.setContentText("Produit crée!");
            alert.showAndWait();


            prixfx.setText("");
            reffx.setText("");
            descriptionfx.setText("");
            imgfx.setText("");
            qstockfx.setText("");
        }
        
        
        
    }*/
    
    public void table(){
     //IDcol.prefWidthProperty().bind(table.widthProperty().divide(8));
       REFcol.prefWidthProperty().bind(table.widthProperty().divide(7));
       DESCRIPTIONcol.prefWidthProperty().bind(table.widthProperty().divide(7));
       PRIXcol.prefWidthProperty().bind(table.widthProperty().divide(7));
       QSTOCKcol.prefWidthProperty().bind(table.widthProperty().divide(7));
       PRIXcol.prefWidthProperty().bind(table.widthProperty().divide(7));
       CATcol.prefWidthProperty().bind(table.widthProperty().divide(7));

    
        table.setItems(FXCollections.observableArrayList(produitDAO.afficherProduit()));
        //IDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        REFcol.setCellValueFactory (new PropertyValueFactory<> ("ref"));
        DESCRIPTIONcol.setCellValueFactory (new PropertyValueFactory<> ("description"));
        PRIXcol.setCellValueFactory (new PropertyValueFactory<> ("prix"));
        IMGAcol.setCellValueFactory (new PropertyValueFactory<> ("image"));
        QSTOCKcol.setCellValueFactory (new PropertyValueFactory<> ("qte_stock"));

       CATcol.setCellFactory (tc -> new TableCell<Produit,Categorie> (){
           @Override
          protected void updateItem (Categorie item, boolean empty) {
            super.updateItem(item,empty);
            if(empty){
            setText(null);
                setStyle("");
               }else{
                 setText(item.getCode_cat().toString());
                }
                }
                });
                CATcol.setCellValueFactory((p)->
                  new SimpleObjectProperty<>(p.getValue().getCategorie()));
                 //x_NomCategorie.setCellValueFactory (new PropertyValueFactory<>("categorie"));

                table.setRowFactory(tv -> {
		     TableRow<Produit> myRow = new TableRow<>();
		     myRow.setOnMouseClicked ((event) -> 
		     {
		        if (event.getClickCount() == 1 && (!myRow.isEmpty()))
		        {
		            int myIndex =  table.getSelectionModel().getSelectedIndex();

		           int id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
		            reffx.setText(table.getItems().get(myIndex).getRef());
                    descriptionfx.setText(table.getItems().get(myIndex).getDescription());
                    prixfx.setText(String.valueOf((Float)table.getItems().get(myIndex).getPrix()));
                    imgfx.setText(table.getItems().get(myIndex).getImage());
                    int qte_stock = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getQte_stock()));
                //    fxCategorieChoiceBox.setValue(table.getItems().get(myIndex).getCategorie());
                         
                           
		           }
		         });
		          return myRow;
                   });
    }
    @FXML
    private void modifier_Produit(ActionEvent event) {
        
        int myIndex = table.getSelectionModel().getSelectedIndex();
        int id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
            String ref = reffx.getText();
           String description = descriptionfx.getText();
           float prix=Float.parseFloat(prixfx.getText());
        String image = imgfx.getText();
        int qte_stock=Integer.parseInt(qstockfx.getText());


        entities.Produit p = new entities.Produit(id,ref,description,prix,image,qte_stock);
                produitDAO.modifierProduit(p);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Produit Registationn");

                alert.setHeaderText("Produit Registation");
                alert.setContentText("Updateddd!");

                alert.showAndWait();
                                table();
        
    }

    @FXML
    private void supprimer_Produit(ActionEvent event) {
        Produit C = table.getSelectionModel().getSelectedItem();
        if (C == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText("Alerte");
            alert.setContentText("Veuillez Choisir un produit à supprimer");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation ");
            alert.setHeaderText(null);
            alert.setContentText("vous êtes sûr de supprimer le produit?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                CRUDProduit cs= new CRUDProduit();
                cs.supprimerProduit(C);

                JOptionPane.showMessageDialog(null, "Produit supprimé");

            }
        }
    }
    
    @FXML
    private void AjouterCategorie(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutCategorie.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (reffx1.getText() == null || reffx1.getText().length() == 0  || reffx1.getText().matches("[0-9]+") ) {
            errorMessage += "Invalide Reference!\n";
        }
        if (descriptionfx1.getText() == null || descriptionfx1.getText().length() == 0 || descriptionfx1.getText().matches("[0-9]+")) {
            errorMessage += "Invalide Description!\n"; 
        }
        

        if (prixfx1.getText() == null || prixfx1.getText().length() == 0) {
            errorMessage += "Invalide Prix!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Float.parseFloat(prixfx1.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalide Prix (must be Float)!\n"; 
            }
        }
        if (fxCategorieChoiceBox1.getValue() == null  ) {
            errorMessage += "Invalide Categorie!\n"; 
        }
        

       
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setTitle("Invalide input");
            alert.setHeaderText("***Please correct input **");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
 @FXML
public void ToPdf(){
 /*  try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream("table.pdf"));
            document.open();

            PdfPTable pdfTable = new PdfPTable(table.getColumns().size());
            addTableHeader(pdfTable, table);
            addRows(pdfTable, table);
            
            document.add(pdfTable);
            
            document.close();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF ");

                alert.setHeaderText("PDF");
                alert.setContentText("Enregistrement effectué avec succès!");

                alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

}
    private void addTableHeader(PdfPTable pdfTable, TableView<Produit> tableView) {
        for (TableColumn<Produit, ?> column : tableView.getColumns()) {
            PdfPCell header = new PdfPCell();
            header.setPhrase(new com.itextpdf.text.Phrase(column.getText()));
            pdfTable.addCell(header);
        }
    }

    private void addRows(PdfPTable pdfTable, TableView<Produit> tableView) {
        for (Produit person : tableView.getItems()) {
            pdfTable.addCell("**");
            pdfTable.addCell(person.getRef());
            pdfTable.addCell(person.getDescription());
            pdfTable.addCell(Float.toString(person.getPrix()));
            pdfTable.addCell(person.getImage());
            pdfTable.addCell(Integer.toString(person.getQte_stock()));
            pdfTable.addCell(person.getCategorie().getCode_cat());
        }
    }
    /*
    @FXML
    public void writeExcel() throws Exception {

        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Mohamed Ali\\Desktop\\Produit.csv"), "UTF-8"));
            CRUDProduit sm = new CRUDProduit();

            List<Produit> metiers = sm.afficherProduit();
            writer.write("Titre,Description,Date,Prix,Client\n");
            for (Produit obj : metiers) {

                writer.write(obj.getTitle());
                writer.write(",");
                writer.write(obj.getDescription());
                writer.write(",");
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                writer.write(formatter.format(obj.getDate_ajout()));
                writer.write(",");
                writer.write(Float.toString(obj.getPrix()));
                writer.write(",");
                writer.write(obj.getUser().getNom());
                writer.write("\n");

            }
            writer.flush();
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EXCEL ");

            alert.setHeaderText("EXCEL");
            alert.setContentText("Enregistrement effectué avec succès!");

            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }
*/
    @FXML
    private void filter(KeyEvent event) {
         ObservableList<Produit> filteredPeople = FXCollections.observableArrayList(produitDAO.afficherProduit());
//To Choose which column to filter by just remove the commented section related to that particular Column
        //or replace it with the column u want to filter by , in our Case im using the Ref
        ObservableList<Produit> newdata = filteredPeople.stream()
                .filter(n -> n.getRef().toLowerCase().contains(filterField.getText().toLowerCase())
//                || n.getDescription().toLowerCase().contains(filterField.getText())
                || n.getRef().toLowerCase().contains(filterField.getText().toLowerCase())
                || n.getRef().toLowerCase().equals(filterField.getText()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        table.setItems(newdata);
    }

    /*@FXML
    void importImage() {
        FileChooser open = new FileChooser();
        file = open.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 200, 66, false, true);
            image= file.getName();
            image.setImage(image);
        }
    */

    public SortedList<Produit> tableViewSearchFilter(ObservableList<Produit> olist) {
        FilteredList<Produit> filteredData = new FilteredList<>(olist, b -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(art -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (art.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1||
                        art.getRef().toLowerCase().indexOf(lowerCaseFilter) != -1 ||

                        art.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1||
                        art.getImage().toLowerCase().indexOf(lowerCaseFilter) != -1||
                        Float.toString(art.getPrix()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        return sortedData;
    }

    @FXML
    private void importerImageUpdate(ActionEvent event) throws MalformedURLException, FileNotFoundException {

        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedfile = fc.showOpenDialog(null);
        if (selectedfile != null) {
            path = selectedfile.getAbsolutePath();
            Image img = new Image(selectedfile.toURI().toString());
            ImageIcon icon = new ImageIcon(path);
            java.awt.Image awtImage = icon.getImage();
            BufferedImage bufferedImage = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(awtImage, 0, 0, null);
            graphics.dispose();
            Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
            image_update.setImage(fxImage);
            image_update.setFitHeight(150);
            image_update.setFitWidth(250);
            imgfx1.setText(path);
        }
    }

}
//up image
//jointure
//update
//comboBox Categories
//