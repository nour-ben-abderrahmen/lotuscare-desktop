package Controllers;

import Models.Evenement;
import Services.EvenementServiceImp;
import Tools.Statics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

public class EvenementController {
    @FXML
    private TableColumn<Evenement,String> col_event_titre;
    @FXML
    private TableColumn<Evenement,String> col_event_date;
    @FXML
    private TableColumn<Evenement,String> col_event_lieu;
    @FXML
    private TableColumn<Evenement,String> col_event_NbrParticipant;
    @FXML
    private TableColumn<Evenement,String> col_event_prix;

    @FXML
    private TableView<Evenement> eventTableView;
    @FXML
    private TextField event_search;
    @FXML
    private AnchorPane root;
    @FXML
    private Button ajouter;
    @FXML
    private TextField event_titre;

private File file = null;
private Image image = null;
    @FXML
    private TextField event_lieu;

    @FXML
    private TextArea event_description;

    @FXML
    private TextField event_nbrParticipant;

    @FXML
    private DatePicker event_date;

    @FXML
    private TextField event_prix;

    @FXML
    private ImageView event_image;

    @FXML
    private Button event_import;

    @FXML
    private Button event_clear;

    @FXML
    private Button event_add;
    @FXML
    private Button event_save;

    @FXML
    private TextField event_id;

    @FXML
    private AnchorPane root1;
    @FXML
    private AnchorPane root2;
    private String url_image=null;

    EvenementServiceImp evenementServiceImp = new EvenementServiceImp();
    public void showListEvent() throws SQLException {
        ObservableList<Evenement> ob = FXCollections.observableList(evenementServiceImp.getAllEvents());
        col_event_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_event_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_event_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        col_event_NbrParticipant.setCellValueFactory(new PropertyValueFactory<>("nbr_participant"));
        col_event_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        eventTableView.setItems(ob);
    }
    public void eventSearch() throws SQLException {
        ObservableList<Evenement> allEvents = FXCollections.observableList(evenementServiceImp.getAllEvents());
        FilteredList<Evenement> filter = new FilteredList<>( allEvents, e -> true);
        event_search.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateAssociation -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateAssociation.getTitre().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Evenement> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(eventTableView.comparatorProperty());
        eventTableView.setItems(sortList);
    }

    @FXML
    void ajouterEvent() throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("/GUI/Back/ajouterEvent.fxml"));
        root.getChildren().removeAll();
        root.getChildren().setAll(fxmlLoader);



    }


    public void eventAdd(){

        try {
            Alert alert;

            if (event_titre.getText().isEmpty()
                    || event_description.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Les champs sont obligatoires");
                alert.showAndWait();
            } else {

                Evenement event = new Evenement();
                event.setTitre(event_titre.getText());
                event.setDescription(event_description.getText());
                event.setLieu(event_lieu.getText());
                event.setNbr_participant( Integer.valueOf(event_nbrParticipant.getText()));
                event.setDate(Date.valueOf(event_date.getValue()));
                event.setPrix(Float.valueOf(event_prix.getText()));

                Path destPath = Paths.get(Statics.uploadDirectory2 + url_image);
                Files.copy(file.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

                event.setUrl_image(url_image);
                event.setLon("222");
                event.setLat("55");
                event.setGouv("Medenine");
                event.setTotal(0);


                evenementServiceImp.addEvent(event);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();


                Parent fmxlLoader = FXMLLoader.load(getClass().getResource("/GUI/Back/evenement.fxml"));
                root1.getChildren().removeAll();
                root1.getChildren().setAll(fmxlLoader);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eventDelete() throws SQLException {
        Evenement event=eventTableView.getSelectionModel().getSelectedItem();
        int num = eventTableView.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){return;}
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cofirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to DELETE Event : " + event.getTitre() + "?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {

            evenementServiceImp.deleteEvent(event.getId());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Deleted!");
            alert.showAndWait();
            showListEvent();



        }
    }


    public void eventUpdate() throws IOException {
        Evenement event=eventTableView.getSelectionModel().getSelectedItem();
        int num = eventTableView.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){return;}
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Back/updateEvent.fxml"));
        Parent root = loader.load();


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        TextField event_titre = (TextField) loader.getNamespace().get("event_titre");
        TextField event_id = (TextField) loader.getNamespace().get("event_id");
        TextField event_lieu = (TextField) loader.getNamespace().get("event_lieu");
        TextArea event_description = (TextArea) loader.getNamespace().get("event_description");
        TextField event_nbrParticipant = (TextField) loader.getNamespace().get("event_nbrParticipant");
        TextField event_prix = (TextField) loader.getNamespace().get("event_prix");
        DatePicker event_date = (DatePicker) loader.getNamespace().get("event_date");
        ImageView event_image= (ImageView) loader.getNamespace().get("event_image");

        event_id.setText(String.valueOf(event.getId()));
        event_titre.setText(event.getTitre());
        event_description.setText(event.getDescription());
        event_lieu.setText(event.getLieu());
        event_nbrParticipant.setText(String.valueOf(event.getNbr_participant()));
        event_prix.setText(String.valueOf(event.getPrix()));
        event_date.setValue(Instant.ofEpochMilli(event.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        String url= "file://"+Statics.uploadDirectory2 +event.getUrl_image();
        System.out.println(event.getUrl_image());
        System.out.println(url);

        try{
            Image image = new Image(url, 200, 66, false, true);
            event_image.setImage(image);
        }catch (Exception exception){
            event_image.setImage(null);
            System.out.println(exception.getMessage());

        }
        stage.show();
    }


    public void eventReset(){
        event_titre.setText("");
        event_description.setText("");
        event_prix.setText("");
        event_lieu.setText("");
        event_nbrParticipant.setText("");
        event_date.setValue(null);
        event_image.setImage(null);
    }

    public void eventUpdate2(){


        try {
            Alert alert;

            if (event_titre.getText().isEmpty()
                    || event_description.getText().isEmpty()  || event_prix.getText().isEmpty() || event_lieu.getText().isEmpty()
                    || event_nbrParticipant.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Les champs sont obligatoires");
                alert.showAndWait();
            } else {


                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Event : " + event_titre.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {

                    Path destPath = Paths.get(Statics.uploadDirectory2 + url_image);
                    Files.copy(file.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                    evenementServiceImp.updateEvent(Integer.parseInt(event_id.getText()),event_titre.getText(),event_lieu.getText(),Integer.parseInt(event_nbrParticipant.getText()),Date.valueOf(event_date.getValue()),event_description.getText(),Float.valueOf(event_prix.getText()),url_image);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();





                }




            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void importImage() {
        FileChooser open = new FileChooser();
        file = open.showOpenDialog(root1.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 200, 66, false, true);
            url_image= file.getName();
            event_image.setImage(image);
        }
    } @FXML
    void importImage2() {
        FileChooser open = new FileChooser();
        file = open.showOpenDialog(root2.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 200, 66, false, true);
            url_image= file.getName();
            event_image.setImage(image);
        }
    }
    @FXML
    void initialize() throws SQLException {
        if (eventTableView != null) {
            showListEvent();
        }

    }
}
