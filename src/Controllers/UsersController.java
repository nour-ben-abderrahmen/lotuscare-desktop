/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import Models.User;
import Services.ServiceUser;
import Tools.LotuscareConnexion;
import Tools.Statics;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class UsersController implements Initializable {

    @FXML
    private Label BonjourPrenom;
    @FXML
    private Button TousFilterBtn;
    @FXML
    private Button ClientsFilterBtn;
    @FXML
    private Button AdminFilterBtn;
    @FXML
    private VBox usersList;

    @FXML
    private Pane ModalPane;
    @FXML
    private ComboBox<String> roleComboBox;

    ServiceUser serviceUser;
    List<User> utilisateurs;
    List<User> AllUsers;

    public int userToUpdateIndex;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        serviceUser = new ServiceUser();
        utilisateurs = new ArrayList<>();
        AllUsers = new ArrayList<>();

        try {
            utilisateurs = serviceUser.getUsers();
            AllUsers = utilisateurs;
        } catch (SQLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }

        BonjourPrenom.setText("Bonjour " + serviceUser.currentUser.getPrenom());
        displayUsers(utilisateurs, serviceUser.currentUser);

        roleComboBox.setItems(FXCollections.observableArrayList("[\"ROLE_ADMIN\"]", "[\"ROLE_USER\"]"));
        userToUpdateIndex = 0;
        ModalPane.setVisible(false);

    }

    private void displayUsers(List<User> users, User currentUser) {

        usersList.getChildren().clear();
        for (int i = 0; i < users.size(); i++) {
            User current = users.get(i);
            int currentIndex = i;
            Pane userPane = new Pane();
            userPane.setBackground(Background.EMPTY);
            userPane.setPrefHeight(55);
            userPane.setPrefWidth(745);
            userPane.setLayoutX(0);
            userPane.setLayoutY(0);

            userPane.setOnMouseEntered((MouseEvent event) -> {
                userPane.setBackground(new Background(new BackgroundFill(Color.web("#f0f2f5"), new CornerRadii(7), Insets.EMPTY)));
            });

            userPane.setOnMouseExited((MouseEvent event) -> {
                userPane.setBackground(Background.EMPTY);
            });

            ImageView img = new ImageView();
            img.setFitHeight(44);
            img.setFitWidth(44);
            img.setPreserveRatio(false);
            img.setLayoutX(5);
            img.setLayoutY(5);
            Image image = new Image(Statics.imgPath + current.getImage());
            img.setImage(image);

            Label fullName = new Label();
            fullName.setPrefHeight(44);
            fullName.setPrefWidth(134);
            fullName.setLayoutX(51);
            fullName.setLayoutY(5);
            fullName.setTextFill(Color.BLACK);
            fullName.setText(current.getPrenom() + " " + current.getNom());

            Label cin = new Label();
            cin.setPrefHeight(44);
            cin.setPrefWidth(71);
            cin.setLayoutX(173);
            cin.setLayoutY(5);
            cin.setTextFill(Color.BLACK);
            cin.setText(current.getCin());

            Label email = new Label();
            email.setPrefHeight(44);
            email.setPrefWidth(186);
            email.setLayoutX(247);
            email.setLayoutY(5);
            email.setTextFill(Color.BLACK);
            email.setText(current.getEmail());

            Label tel = new Label();
            tel.setPrefHeight(44);
            tel.setPrefWidth(71);
            tel.setLayoutX(436);
            tel.setLayoutY(5);
            tel.setTextFill(Color.BLACK);
            tel.setText(current.getTelephone());

            Label role = new Label();
            role.setPrefHeight(44);
            role.setPrefWidth(107);
            role.setLayoutX(509);
            role.setLayoutY(5);
            role.setTextFill(Color.BLACK);
            role.setText(current.getRoles());

            Image updateImg = new Image(Statics.imgPath + "edit.png", 20, 20, false, true);
            Button updateBtn = new Button("", new ImageView(updateImg));
            updateBtn.setPrefHeight(44);
            updateBtn.setPrefWidth(40);
            updateBtn.setLayoutX(662);
            updateBtn.setLayoutY(5);

            Image deleteImg = new Image(Statics.imgPath + "delete.png", 20, 20, false, true);
            Button deleteBtn = new Button("", new ImageView(deleteImg));
            deleteBtn.setPrefHeight(44);
            deleteBtn.setPrefWidth(40);
            deleteBtn.setLayoutX(705);
            deleteBtn.setLayoutY(5);

            userPane.getChildren().add(img);
            userPane.getChildren().add(fullName);
            userPane.getChildren().add(cin);
            userPane.getChildren().add(email);
            userPane.getChildren().add(tel);
            userPane.getChildren().add(role);
            if (current.getId() != currentUser.getId()) {
                userPane.getChildren().add(updateBtn);
                userPane.getChildren().add(deleteBtn);
            }

            updateBtn.setOnMouseClicked((MouseEvent event) -> {
                roleComboBox.getSelectionModel().select(current.getRoles());
                userToUpdateIndex = currentIndex;
                ModalPane.setVisible(true);
            });

            deleteBtn.setOnMouseClicked((MouseEvent event) -> {
                int input = JOptionPane.showConfirmDialog(null, "Etes-vous sure de supprimer cet utilisateur ?", "Confirmer la supression",
                        JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                if (input == 0) {
                    serviceUser.supprimerUtilisateur(current.getId());
                    utilisateurs.remove(current);
                    //addNotifications("succés", "utilisateur supprimé avec succés");
                    displayUsers(utilisateurs, currentUser);
                }
            });

            usersList.getChildren().add(userPane);
        }

    }

    @FXML
    private void FilterTous(ActionEvent event) {
        defaultStateButtons();
        TousFilterBtn.setTextFill(Color.web("#5b4ebd"));
        TousFilterBtn.setStyle("-fx-background-color :#ffffff");

        utilisateurs = AllUsers;
        displayUsers(utilisateurs, serviceUser.currentUser);
    }

    @FXML
    private void FilterClients(ActionEvent event) {
        defaultStateButtons();
        ClientsFilterBtn.setTextFill(Color.web("#5b4ebd"));
        ClientsFilterBtn.setStyle("-fx-background-color :#ffffff");

        utilisateurs = AllUsers.stream().filter((user) -> {
            return user.getRoles().equals("[\"ROLE_USER\"]");
        }).collect(Collectors.toList());
        displayUsers(utilisateurs, serviceUser.currentUser);
    }

    @FXML
    private void FilterAdministrateur(ActionEvent event) {
        defaultStateButtons();
        AdminFilterBtn.setTextFill(Color.web("#5b4ebd"));
        AdminFilterBtn.setStyle("-fx-background-color :#ffffff");

        utilisateurs = AllUsers.stream().filter((user) -> {
            return user.getRoles().equals("[\"ROLE_ADMIN\"]");
        }).collect(Collectors.toList());
        displayUsers(utilisateurs, serviceUser.currentUser);
    }

    private void defaultStateButtons() {

        TousFilterBtn.setTextFill(Color.web("#000000"));
        TousFilterBtn.setStyle("-fx-background-color :#ffffff");

        AdminFilterBtn.setTextFill(Color.web("#000000"));
        AdminFilterBtn.setStyle("-fx-background-color :#ffffff");

        ClientsFilterBtn.setTextFill(Color.web("#000000"));
        ClientsFilterBtn.setStyle("-fx-background-color :#ffffff");

    }

    @FXML
    private void closeModal(ActionEvent event) {
        ModalPane.setVisible(false);
    }

    @FXML
    private void confirmUpadteRole(ActionEvent event) {
        int input = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment modifier le role ?", "Choisir une option...",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        // 0=yes, 1=no, 2=cancel
        if (input == 0) {
            serviceUser.ModifierRoleById(roleComboBox.getValue(), utilisateurs.get(userToUpdateIndex).getId());

            User updatedUser = utilisateurs.get(userToUpdateIndex);
            updatedUser.setRoles(roleComboBox.getValue());

            utilisateurs.set(userToUpdateIndex, updatedUser);
            //addNotifications("succés", "Role modifié avec succés");
            displayUsers(utilisateurs, serviceUser.currentUser);
            ModalPane.setVisible(false);
        }
    }

    @FXML
    private void toExcel(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        List<User> users = serviceUser.getUsers();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Liste des utilisateurs");
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell((short) 0).setCellValue("id");
        rowhead.createCell((short) 1).setCellValue("Nom");
        rowhead.createCell((short) 2).setCellValue("Prenom");
        rowhead.createCell((short) 3).setCellValue("Email");
        rowhead.createCell((short) 4).setCellValue("Telephone");
        rowhead.createCell((short) 5).setCellValue("CIN");
        rowhead.createCell((short) 6).setCellValue("Roles");

        for (int i = 0; i < users.size(); i++) {
            HSSFRow row = sheet.createRow((short) i);
            row.createCell((short) 0).setCellValue(users.get(i).getId());
            row.createCell((short) 1).setCellValue(users.get(i).getNom());
            row.createCell((short) 2).setCellValue(users.get(i).getPrenom());

            row.createCell((short) 3).setCellValue(users.get(i).getEmail());
            row.createCell((short) 4).setCellValue(users.get(i).getTelephone());
            row.createCell((short) 5).setCellValue(users.get(i).getCin());
            row.createCell((short) 6).setCellValue(users.get(i).getRoles());
        }

        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\YOUSSEF\\Desktop\\RapportUsers.xls");
        try {
            wb.write(fileOut);
        } catch (IOException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileOut.close();
        System.out.println("Data is saved in excel file.");
    }
}
