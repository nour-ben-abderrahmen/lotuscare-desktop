package Controllers;


import Models.Association;
import Services.AssociationServiceImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;



public class AssociationController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField association_nom;
    @FXML
    private TextField association_search;
    @FXML
    private TextField association_description;
    @FXML
    private TableColumn<Association,String> col_association_nom;
    @FXML
    private TableColumn<Association,String> col_association_description;
    @FXML
    private TableView<Association> associationTableView;

    AssociationServiceImp associationServiceImp= new AssociationServiceImp();


public void showListAssociation() throws SQLException {
    ObservableList<Association> ob = FXCollections.observableList(associationServiceImp.getAllAssociations());
    col_association_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    col_association_description.setCellValueFactory(new PropertyValueFactory<>("description"));
    associationTableView.setItems(ob);



}
public void associationSelect(){
   Association association=associationTableView.getSelectionModel().getSelectedItem();
    int num = associationTableView.getSelectionModel().getSelectedIndex();
    if((num - 1)<-1){return;}
    association_nom.setText(String.valueOf(association.getNom()));
    association_description.setText(String.valueOf(association.getDescription()));


}
public void associationUpdate()  {
    Association association=associationTableView.getSelectionModel().getSelectedItem();
    int num = associationTableView.getSelectionModel().getSelectedIndex();
    if((num - 1)<-1){return;}


    try {
        Alert alert;

        if (association_nom.getText().isEmpty()
                || association_description.getText().isEmpty()
                ) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Les champs sont obligatoires");
            alert.showAndWait();
        } else {

            associationServiceImp.updateAssociation(association.getId(),association_nom.getText(),association_description.getText());
            showListAssociation();

        }

    } catch (Exception e) {
        e.printStackTrace();
    }

}
    public void associationReset() {
        association_nom.setText("");
        association_description.setText("");

    }

    public void associationDelete() throws SQLException {
        Association association=associationTableView.getSelectionModel().getSelectedItem();
        int num = associationTableView.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){return;}
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cofirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to DELETE Association: " + association.getNom() + "?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {

           associationServiceImp.deleteAssociation(association.getId());
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Deleted!");
            alert.showAndWait();
            showListAssociation();
            associationReset();


        }
    }

    public void associationAdd(){

        try {
            Alert alert;

            if (association_nom.getText().isEmpty()
                    || association_description.getText().isEmpty()
            ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Les champs sont obligatoires");
                alert.showAndWait();
            } else {
                Association association = new Association();
                association.setNom(association_nom.getText());
                association.setDescription(association_description.getText());
                associationServiceImp.addAssociation(association);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();
                showListAssociation();
                associationReset();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void associationSearch() throws SQLException {

        ObservableList<Association> allAssociation = FXCollections.observableList(associationServiceImp.getAllAssociations());
        FilteredList<Association> filter = new FilteredList<>( allAssociation, e -> true);

        association_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateAssociation -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if (predicateAssociation.getNom().toLowerCase().contains(searchKey)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Association> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(associationTableView.comparatorProperty());
        associationTableView.setItems(sortList);
    }


    public void associationSearch2() throws SQLException {

        ObservableList<Association> allAssociation = FXCollections.observableList(associationServiceImp.getAssociationsParMC(association_search.getText()));
        FilteredList<Association> filter = new FilteredList<>( allAssociation, e -> true);



        SortedList<Association> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(associationTableView.comparatorProperty());
        associationTableView.setItems(sortList);
    }

    public void generatePdfAssociation() throws Exception {
        Association association=associationTableView.getSelectionModel().getSelectedItem();
        int num = associationTableView.getSelectionModel().getSelectedIndex();
        if((num - 1)<-1){return;}
        associationServiceImp.pdfAssociation(association);
        }




    @FXML
    void initialize() throws SQLException {
    showListAssociation();

    }

}
