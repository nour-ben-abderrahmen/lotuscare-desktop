/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.ServiceUser;
import Tools.LocalStorage;
import Tools.Statics;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class TemplateController implements Initializable {

    @FXML
    private Pane sidebar;
    @FXML
    private Button dashBtn;
    @FXML
    private Button usersBtn;
    @FXML
    private Button forumBtn;
    @FXML
    private Button eventBtn;
    @FXML
    private Button produitBtn;
    @FXML
    private Pane navbar;
    @FXML
    private ImageView navbar_img;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Pane userPopup;
    @FXML
    private ImageView userPopup_img;
    @FXML
    private Label userPopup_fullname;
    @FXML
    private Label userPopup_role;

    ServiceUser SU;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SU = new ServiceUser();

        userPopup.setVisible(false);
        addNavBarImgBorder();
        addUserPopupImgBorder();
        addUserPopupBorder();
        addNavbarBorder();

        userPopup_fullname.setText(SU.currentUser.getPrenom() + " " + SU.currentUser.getNom());
        String prettierRole = SU.currentUser.getRoles();
        prettierRole = prettierRole.replace("[", "");
        prettierRole = prettierRole.replace("]", "");
        prettierRole = prettierRole.replace("ROLE_", "");
        userPopup_role.setText(prettierRole.toLowerCase());

        Image img = new Image(Statics.imgPath + SU.currentUser.getImage());
        navbar_img.setImage(img);
        navbar_img.setSmooth(false);
        navbar_img.setPreserveRatio(false);

        userPopup_img.setImage(img);
        userPopup_img.setSmooth(false);
        userPopup_img.setPreserveRatio(false);

        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Dashboard.fxml"));
            anchor.getChildren().setAll(pane);
            dashBtn.setTextFill(Color.web("#696cff"));
            dashBtn.setStyle("-fx-background-color :#696cff29");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void defaultStateButtons() {
        dashBtn.setTextFill(Color.web("#a19595"));
        dashBtn.setStyle("-fx-background-color :#ffffff");

        usersBtn.setTextFill(Color.web("#a19595"));
        usersBtn.setStyle("-fx-background-color :#ffffff");

        forumBtn.setTextFill(Color.web("#a19595"));
        forumBtn.setStyle("-fx-background-color :#ffffff");

        eventBtn.setTextFill(Color.web("#a19595"));
        eventBtn.setStyle("-fx-background-color :#ffffff");

        produitBtn.setTextFill(Color.web("#a19595"));
        produitBtn.setStyle("-fx-background-color :#ffffff");

    }

    private void addNavBarImgBorder() {
        Rectangle clip = new Rectangle(navbar_img.getFitWidth(), navbar_img.getFitHeight());
        clip.setArcWidth(navbar_img.getFitWidth());
        clip.setArcHeight(navbar_img.getFitHeight());
        navbar_img.setClip(clip);
    }

    private void addUserPopupImgBorder() {
        Rectangle clip = new Rectangle(userPopup_img.getFitWidth(), userPopup_img.getFitHeight());
        clip.setArcWidth(userPopup_img.getFitWidth());
        clip.setArcHeight(userPopup_img.getFitHeight());
        userPopup_img.setClip(clip);
    }

    private void addUserPopupBorder() {
        Rectangle userPopupClip = new Rectangle(userPopup.getPrefWidth(), userPopup.getPrefHeight());
        userPopupClip.setArcWidth(20);
        userPopupClip.setArcHeight(20);
        userPopup.setClip(userPopupClip);
    }

    private void addNavbarBorder() {
        Rectangle userPopupClip = new Rectangle(navbar.getPrefWidth(), navbar.getPrefHeight());
        userPopupClip.setArcWidth(20);
        userPopupClip.setArcHeight(20);
        navbar.setClip(userPopupClip);
    }

    @FXML
    private void redirectToDashboard(ActionEvent event) {
        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Dashboard.fxml"));
            anchor.getChildren().setAll(pane);
            defaultStateButtons();
            dashBtn.setTextFill(Color.web("#696cff"));
            dashBtn.setStyle("-fx-background-color :#696cff29");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void redirectToUsers(ActionEvent event) {

        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Users.fxml"));
            anchor.getChildren().setAll(pane);
            defaultStateButtons();
            usersBtn.setTextFill(Color.web("#696cff"));
            usersBtn.setStyle("-fx-background-color :#696cff29");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void redirectToForum(ActionEvent event) {

        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Forum.fxml"));
            anchor.getChildren().setAll(pane);
            defaultStateButtons();
            forumBtn.setTextFill(Color.web("#696cff"));
            forumBtn.setStyle("-fx-background-color :#696cff29");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void redirectToEvents(ActionEvent event) {

        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Event.fxml"));
            anchor.getChildren().setAll(pane);
            defaultStateButtons();
            eventBtn.setTextFill(Color.web("#696cff"));
            eventBtn.setStyle("-fx-background-color :#696cff29");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void redirectToProduit(ActionEvent event) {

        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Produit.fxml"));
            anchor.getChildren().setAll(pane);
            defaultStateButtons();
            produitBtn.setTextFill(Color.web("#696cff"));
            produitBtn.setStyle("-fx-background-color :#696cff29");
        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void togglePopup(MouseEvent event) {
        userPopup.setVisible(!userPopup.visibleProperty().get());
    }

    @FXML
    private void redirectToProfile(ActionEvent event) {
        AnchorPane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/GUI/Profile.fxml"));
            anchor.getChildren().setAll(pane);
            defaultStateButtons();
            userPopup.setVisible(!userPopup.visibleProperty().get());

        } catch (IOException ex) {
            Logger.getLogger(TemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        LocalStorage localStorage = new LocalStorage();
        localStorage.deleteStorage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
        Parent root = loader.load();
        sidebar.getScene().setRoot(root);
    }

}
