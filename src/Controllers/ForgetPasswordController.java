/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import Services.ServiceUser;
import Tools.MailSender;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author YOUSSEF
 */
public class ForgetPasswordController implements Initializable {

    @FXML
    private TextField emailInput;

    int verificationCode = 0;
    @FXML
    private TextField digit1TextField;
    @FXML
    private TextField digit2TextField;
    @FXML
    private TextField digit3TextField;
    @FXML
    private TextField digit4TextField;
    @FXML
    private TextField digit5TextField;
    @FXML
    private TextField digit6TextField;

    private StringBuilder otpBuilder = new StringBuilder();
    @FXML
    private HBox otpContainer;
    @FXML
    private AnchorPane verfiyCodeModal;
    @FXML
    private AnchorPane changePasswordModal;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField confirmInput;

    ServiceUser serviceUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceUser = new ServiceUser();

        digit1TextField.textProperty().addListener((obs, oldText, newText) -> {
            onDigitTextChanged(newText);
        });
        digit2TextField.textProperty().addListener((obs, oldText, newText) -> {
            onDigitTextChanged(newText);
        });
        digit3TextField.textProperty().addListener((obs, oldText, newText) -> {
            onDigitTextChanged(newText);
        });
        digit4TextField.textProperty().addListener((obs, oldText, newText) -> {
            onDigitTextChanged(newText);
        });
        digit5TextField.textProperty().addListener((obs, oldText, newText) -> {
            onDigitTextChanged(newText);
        });
        digit6TextField.textProperty().addListener((obs, oldText, newText) -> {
            onDigitTextChanged(newText);
        });
    }

    private void onDigitTextChanged(String newText) {
        if (newText.length() >= 1) {
            String lastDigit = newText.substring(newText.length() - 1);
            otpBuilder.append(lastDigit);
            if (otpBuilder.length() == 6) {
                validateOTP(otpBuilder.toString());
            }
            focusNextTextField();
        } else if (newText.isEmpty()) {
            otpBuilder.setLength(otpBuilder.length() - 1);
            focusPreviousTextField();
        }
    }

    private void focusNextTextField() {
        int currentIndex = otpContainer.getChildren().indexOf(otpContainer.getScene().getFocusOwner());
        if (currentIndex < 5) {
            TextField nextField = (TextField) otpContainer.getChildren().get(currentIndex + 1);
            nextField.requestFocus();
        }
    }

    private void focusPreviousTextField() {
        int currentIndex = otpContainer.getChildren().indexOf(otpContainer.getScene().getFocusOwner());
        if (currentIndex > 0) {
            TextField previousField = (TextField) otpContainer.getChildren().get(currentIndex - 1);
            previousField.requestFocus();
        }
    }

    @FXML
    private void sendEmail(ActionEvent event) {
        if (emailInput.getText().isEmpty()) {
            System.out.println("email is required");
            return;
        }

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        if (!pattern.matcher(emailInput.getText()).matches()) {
            System.out.println("email is not valid");
            return;
        }

        Random rand = new Random();
        verificationCode = rand.nextInt(900000) + 100000;

        MailSender mailSender = new MailSender();
        mailSender.sendMail(emailInput.getText(), "Reset password code", "Voici le code de verification pour changer votre mot de passe : " + verificationCode);
        verfiyCodeModal.setVisible(true);
    }

    private void validateOTP(String otp) {
        // TODO: Implement OTP validation logic here
        System.out.println(otp);
        if (Integer.toString(verificationCode).equals(otp)) {
            System.out.println("code shih");
            changePasswordModal.setVisible(true);
        } else {
            System.out.println("code ghalet");
        }
    }

    @FXML
    private void saveAction(ActionEvent event) throws IOException {
        if (passwordInput.getText().isEmpty()) {
            System.out.println("password is required");
            addNotifications("erreur", "password is required");
            return;
        }
        if (passwordInput.getText().length() < 8) {
            System.out.println("password should be at least 8 characters");
            addNotifications("erreur", "password must be at least 8 characters");
            return;
        }

        if (checkPassword(passwordInput.getText()) == false) {
            System.out.println("password should be at least 8 characters");
            addNotifications("erreur", "password must contains at least \n one capital letter, lowercase letter, and number");
            return;
        }

        if (confirmInput.getText().isEmpty()) {
            System.out.println("confirm your password");
            addNotifications("erreur", "confirm your password");
            return;
        }

        if (!passwordInput.getText().equals(confirmInput.getText())) {
            System.out.println("passwords dont match");
            addNotifications("erreur", "passwords dont match");
            return;
        }

        serviceUser.changePassword(emailInput.getText(), passwordInput.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
        Parent root = loader.load();
        emailInput.getScene().setRoot(root);

    }

    private boolean checkPassword(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag) {
                return true;
            }
        }
        return false;
    }

    private void addNotifications(String title, String content) {

        if (content != null) {
            if (content.length() > 50) {
                //content = content.substring(0, 49) + "......";
            }
        }
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(content)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.showInformation();
    }

}
