/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.ServiceProduit;
import entities.Categorie;
import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProduitController implements Initializable {

    /**
     * Initializes the controller class.
     */


    @FXML
    private TableView<Produit> view;
    @FXML
    private TableColumn<Categorie, String> CATcol;

    @FXML
    private TableColumn<Produit, String> DESCcol;

    @FXML
    private TableColumn<Produit, Integer> IDcol;

    @FXML
    private TableColumn<Produit, String> IMGcol;

    @FXML
    private TableColumn<Produit, Float> PRIXcol;

    @FXML
    private TableColumn<Produit, Integer> QScol;

    @FXML
    private TableColumn<Produit, String> REFcol;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;
    //@FXML
    //private TextField cat;

    @FXML
    private TextField desc;

    @FXML
    private TextField img;

    @FXML
    private TextField prixx;

    @FXML
    private TextField qs;

    @FXML
    private TextField reff;

    private ObservableList<Produit> observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ServiceProduit crud = new ServiceProduit();
            ObservableList<Produit> data = FXCollections.observableArrayList(crud.showProduct());
            System.out.println("///////");
            System.out.println(data);
            System.out.println("///////");
            REFcol.setCellValueFactory(new PropertyValueFactory<>("ref"));
            DESCcol.setCellValueFactory(new PropertyValueFactory<>("description"));
            IMGcol.setCellValueFactory(new PropertyValueFactory<>("image"));
            PRIXcol.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
            QScol.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("qte_stock"));
            view.setItems(data);
            throw new SQLException("Sample SQLException");
        } catch (SQLException ex) {
            System.out.println("here");
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Ajouter(ActionEvent event) throws ParseException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String getRef = reff.getText();
        String getDescription = desc.getText();
        String getImage = img.getText();
        Float getPrix = Float.parseFloat(prixx.getText());
        int getQte_stock = Integer.parseInt(qs.getText());
        if (getRef.isEmpty() | getDescription.isEmpty() | getImage.isEmpty()) {
            alert.setTitle("Produits");
            alert.setContentText("Impossible d'ajouter un produit avec un champ vide!!");
            alert.show();
        } else {
            Produit p = new Produit(getRef, getDescription, getImage, getPrix, getQte_stock);
            ServiceProduit p1 = new ServiceProduit();
            p1.addProduct(p);
            alert.setTitle("Produits");
            alert.setContentText("Produit ajouté Avec succées");
            alert.show();
            reff.clear();
            desc.clear();
            img.clear();
            prixx.clear();
            qs.clear();
        }
    }
        public void supprimer (ActionEvent event) throws IOException
        {

            int selectedIndex = view.getSelectionModel().getSelectedIndex();

            if (selectedIndex < 0) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Erreur");
                a.setHeaderText("Aucun produit selectionné!");
                a.setContentText("Veuiller selectionner une jeu à supprimer");
                Optional<ButtonType> result = a.showAndWait();
            } else {
                try {
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Confirmation Dialog");
                    alert1.setHeaderText("Look, a Confirmation Dialog");
                    alert1.setContentText("Are you sure to delete?");
                    Optional<ButtonType> result = alert1.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        ServiceProduit ser = new ServiceProduit();
                        ser.removeProduct(view.getSelectionModel().getSelectedItem().getId());
                        ServiceProduit p = new ServiceProduit();
                        ObservableList<Produit> data = FXCollections.observableArrayList(p.showProduct());
                        System.out.println("///////");
                        System.out.println(data);
                        System.out.println("///////");
                        IDcol.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
                        REFcol.setCellValueFactory(new PropertyValueFactory<Produit, String>("ref"));
                        DESCcol.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
                        IMGcol.setCellValueFactory(new PropertyValueFactory<Produit, String>("image"));
                        PRIXcol.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
                        QScol.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("qte_stock"));
                        view.setItems(data);
                        alert1.setTitle("Produit supprimé");
                        alert1.setContentText("Le produit est supprimé Avec succées");
                        alert1.show();
                    } else {

                    }
                    throw new SQLException("Sample SQLException");
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }



        public void show (ActionEvent event) throws ParseException {
            int selectedIndex = view.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucun produit selectionné!");
                alert.setContentText("Veuiller selectionner un produit à modifier");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
               String reff = view.getSelectionModel().getSelectedItem().getRef();
                desc.setText(view.getSelectionModel().getSelectedItem().getDescription());
                img.setText(view.getSelectionModel().getSelectedItem().getImage());
            }
        }
    public void modifier(ActionEvent event) throws ParseException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (reff.getText().isEmpty()| desc.getText().isEmpty() | img.getText().isEmpty()){
            alert.setTitle("Modification produit");
            alert.setContentText("impossible de modifier un produit avec un champ vide!!");
            alert.show();
        }
        else{
            int qs=view.getSelectionModel().getSelectedItem().getQte_stock();
            float prix = Float.parseFloat(prixx.getText());
            Produit p = new Produit(reff.getText(), desc.getText(),img.getText(),prix,qs);
            ServiceProduit crud = new ServiceProduit();
            crud.modifyProduct(p);
            reff.clear();
            desc.clear();
            img.clear();
            prixx.clear();

            ObservableList<Produit> data = FXCollections.observableArrayList(crud.showProduct());
            IDcol.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id"));
            REFcol.setCellValueFactory(new PropertyValueFactory<Produit, String>("ref"));
            DESCcol.setCellValueFactory(new PropertyValueFactory<Produit, String>("description"));
            IMGcol.setCellValueFactory(new PropertyValueFactory<Produit, String>("image"));
            PRIXcol.setCellValueFactory(new PropertyValueFactory<Produit, Float>("prix"));
            QScol.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("qte_stock"));
            alert.setTitle("Modification produit");
            alert.setContentText("produit Modifiée Avec succées");
            alert.show();
        }
    }
   }
















