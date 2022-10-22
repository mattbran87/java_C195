package c195.task_1.java_c195;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import c195.task_1.java_c195.DAO.userLogin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    public Label mainLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label locationLabel;
    @FXML
    public Label locationValue;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Button loginButton;
    @FXML
    public Button exitButton;

    private static final String FILENAME = "language_files/login";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
//            Locale.setDefault(new Locale("fr", "FR"));

            rb = ResourceBundle.getBundle("language_files/login", Locale.getDefault());
            mainLabel.setText(rb.getString("mainLabel"));
            usernameLabel.setText(rb.getString("usernameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            locationLabel.setText(rb.getString("locationLabel"));
            locationValue.setText(rb.getString("locationValue"));
            loginButton.setText(rb.getString("loginButton"));
            exitButton.setText(rb.getString("exitButton"));

        } catch(MissingResourceException e) {
            System.out.println("Resource file missing: " + e);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void loginButtonClicked(ActionEvent mouseEvent) throws IOException {
        // get form values
        String usernameInput = username.getText();
        String passwordInput = password.getText();
        int userId = userLogin.userValidation(usernameInput, passwordInput);

        ResourceBundle rb = ResourceBundle.getBundle("language_files/login", Locale.getDefault());

        if (userId == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("loginFailedTitle"));
            alert.setContentText(rb.getString("loginFailed"));
            alert.show();
        } else if (userId > 0) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Appointments.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("loginFailedTitle"));
            alert.setContentText(rb.getString("loginFailedUnknown"));
            alert.show();
        }
    }

    public void exitButtonClicked(ActionEvent mouseEvent) {
        System.exit(0);
    }
}