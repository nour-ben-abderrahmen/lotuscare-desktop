package Controllers;


import Models.Association;
import Models.Don;
import Models.Evenement;
import Services.AssociationServiceImp;
import Services.DonServiceImp;
import Services.EvenementServiceImp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DonController {
    @FXML
    private TextField don_somme;

    @FXML
    private Button don_clear;

    @FXML
    private Button don_add;

    @FXML
    private ComboBox<Evenement> don_event;

    @FXML
    private ComboBox<Association> don_association;

    @FXML
    private TableView<Don> donTableView;

    @FXML
    private TableColumn<Don, Float> col_don_somme;

    @FXML
    private TableColumn<Don, String > col_don_event;

    @FXML
    private TableColumn<Don, String> col_don_association;

    @FXML
    private TextField don_search;

    DonServiceImp donServiceImp= new DonServiceImp();
    EvenementServiceImp evenementServiceImp=new EvenementServiceImp();
    AssociationServiceImp associationServiceImp=new AssociationServiceImp();
    public void showListDons() throws SQLException {
        ObservableList<Don> ob = FXCollections.observableList(donServiceImp.getAllDons());

        col_don_somme.setCellValueFactory(new PropertyValueFactory<>("somme"));

       // col_don_association.setCellValueFactory(new PropertyValueFactory<>("association"));
        col_don_association.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssociation().getNom()));

        col_don_event.setCellValueFactory(cellData -> {
            Evenement event = cellData.getValue().getEvenement().get(0);


                String titre = event.getTitre();

            return new SimpleStringProperty(titre);
        });



        donTableView.setItems(ob);

        ObservableList<Evenement> obEvent = FXCollections.observableList(evenementServiceImp.getAllEventsDate());
        don_event.setItems(obEvent);
        don_event.setCellFactory(param -> new ListCell<Evenement>() {
            @Override
            protected void updateItem(Evenement event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    setText(event.getTitre());
                }
            }
        });
        don_event.setButtonCell(new ListCell<Evenement>() {
            @Override
            protected void updateItem(Evenement event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    setText(event.getTitre());
                }
            }
        });


        ObservableList<Association> obAssociation = FXCollections.observableList(associationServiceImp.getAllAssociations());
        don_association.setItems(obAssociation);
        don_association.setCellFactory(param -> new ListCell<Association>() {
            @Override
            protected void updateItem(Association association, boolean empty) {
                super.updateItem(association, empty);
                if (empty || association == null) {
                    setText(null);
                } else {
                    setText(association.getNom());
                }
            }
        });
        don_association.setButtonCell(new ListCell<Association>() {
            @Override
            protected void updateItem(Association association, boolean empty) {
                super.updateItem(association, empty);
                if (empty || association == null) {
                    setText(null);
                } else {
                    setText(association.getNom());
                }
            }
        });
    }

    public void updateSomme(){
        if (don_event.getSelectionModel().getSelectedItem() != null){
            don_somme.setText(Float.toString(don_event.getSelectionModel().getSelectedItem().getTotal()));

        }else {
            don_somme.setText("");
        }
    }

    public void donAdd(){

        try {
            Alert alert;

            if (don_event.getSelectionModel().getSelectedItem() == null
                    || don_association.getSelectionModel().getSelectedItem() == null
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Les champs sont obligatoires");
                alert.showAndWait();
            } else {
                Don don = new Don();
                don.setSomme(Float.valueOf(don_somme.getText()));
                don.setAssociation(don_association.getSelectionModel().getSelectedItem());
                Evenement event=don_event.getSelectionModel().getSelectedItem();
                List<Evenement> listEvent = new ArrayList<>();
                listEvent.add(event);
                don.setEvenement(listEvent);
                evenementServiceImp.updateEventTotal(event.getId());

                donServiceImp.addDon(don);





                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();
                showListDons();
                donReset();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void donReset(){
        don_somme.setText("");
        don_event.setValue(null);
        don_association.setValue(null);
    }
    @FXML
    void initialize() throws SQLException {
        showListDons();



    }


}
