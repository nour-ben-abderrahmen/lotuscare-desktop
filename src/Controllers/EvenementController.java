package Controllers;

import Interfaces.Mylistener;
import Models.Evenement;
import Services.EvenementServiceImp;
import Tools.Statics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


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
import java.util.List;
import java.util.Optional;

public class EvenementController  {
    @FXML
    private VBox chosenEventCard;

    @FXML
    private Label eventTitreLabel;

    @FXML
    private ImageView eventImg;

    @FXML
    private Label eventLieuLabel;

    @FXML
    private Label eventPrixLabel;

    @FXML
    private Label eventDescriptionLabel;
    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private Mylistener mylistener;






    @FXML
    private AnchorPane mapPane;

    @FXML
    private WebView webView;
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

    Evenement event1 = null;

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

                event.setUrl_image(Statics.uploadDirectory2 + url_image);
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

        String url= "file:"+event.getUrl_image();


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

    public void participer(){
        try {
            Alert alert;

            if (event1.getNbr_participant()==0
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("L'évènement "+event1.getTitre()+" n a plus des places disponibles");
                alert.showAndWait();
            } else {


                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Êtes-vous sûr de vouloir participer à l'événement : " + event1.getTitre() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {

                    event1.setNbr_participant(event1.getNbr_participant()-1);
                    event1.setTotal(event1.getTotal()+event1.getPrix());
                   // event1.setUsers(event1.getUsers().add());
                    evenementServiceImp.participerEvent(event1.getId(),event1.getNbr_participant(),event1.getTotal());
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Participer avec Succes!");
                    alert.showAndWait();





                }




            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    url_image = Statics.uploadDirectory2 + url_image;
                    Files.copy(file.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                    evenementServiceImp.updateEvent(Integer.parseInt(event_id.getText()),event_titre.getText(),event_lieu.getText(),Integer.parseInt(event_nbrParticipant.getText()),Date.valueOf(event_date.getValue()),event_description.getText(),Float.valueOf(event_prix.getText()),url_image);
                    // TWILIOO !!!!!!!!!!!!!!!
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
    public void setChosenEvent(Evenement event){
        eventTitreLabel.setText(event.getTitre());
        eventLieuLabel.setText(event.getLieu());
        eventPrixLabel.setText(String.valueOf(event.getPrix())+" DT");
        eventDescriptionLabel.setText(event.getDescription());
        image = new Image("file:"+event.getUrl_image());
        eventImg.setImage(image);
        chosenEventCard.setStyle("-fx-background-color : #9ACD32");
        event1=event;

    }
    public void eventMap(){
        WebEngine webEngine =webView.getEngine();
        webEngine.load("https://maps.google.com/maps?t=&amp;z=13&amp;ie=UTF8&amp;iwloc=&amp;output=embed");
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");

                window.setMember("app", this);

                String script = "var map = new google.maps.Map(document.getElementById('map'), {\n" +
                        "    center: {lat: 33.886917, lng: 9.537499},\n" +
                        "    zoom: 30\n" +
                        "});\n" +
                        "var marker = new google.maps.Marker({\n" +
                        "    position: {lat: 33.886917, lng: 9.537499},\n" +
                        "    map: map,\n" +
                        "google.maps.event.addListener(marker, 'click', function() {\n" +
                        "   var position = marker.getPosition();\n" +
                            "          var latitude = position.lat();\n" +
                            "          var longitude = position.lng();\n" +
                            "          java.getLatLng(latitude, longitude);\n" +
                            "        });\n"+
                        "});";
                window.setMember("java", new Object() {
                    public void getLatLng(double latitude, double longitude) {
                        System.out.println("Latitude : " + latitude + ", Longitude : " + longitude);
                    }
                });
                webEngine.executeScript(script);

            }

        });

    }
    @FXML
    void initialize() throws SQLException {
        if (eventTableView != null) {
            showListEvent();
        }

        if (scroll != null){
            List<Evenement> events = evenementServiceImp.getAllEvents();
            if (events.size()>0){
                setChosenEvent(events.get(0));
                mylistener = new Mylistener() {
                    @Override
                    public void onClickListener(Evenement event) {
                        setChosenEvent(event);
                    }
                };
            }
            int column = 0;
            int row = 1;
            try{
                for(int  i=0;i < events.size();i++){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/GUI/Front/item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(events.get(i),mylistener);
                    if (column==3){
                        column=0;
                        row++;
                    }

                    grid.add(anchorPane,column++,row);

                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane,new Insets(10));
                }}catch (IOException e){
                e.printStackTrace();
            }


        }
        if (webView!=null){
            eventMap();
        }

    }
}
