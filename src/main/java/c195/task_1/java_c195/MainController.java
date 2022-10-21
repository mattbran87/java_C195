package c195.task_1.java_c195;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import c195.task_1.java_c195.helper.JDBC;
import c195.task_1.java_c195.DAO.userLogin;

import java.io.IOException;

// import DAO.appointmentAccess;


public class MainController {
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

    public void loginButtonClicked(ActionEvent mouseEvent) throws IOException {
        // get form values
        String usernameInput = username.getText();
        String passwordInput = password.getText();
        int userId = userLogin.userValidation(usernameInput, passwordInput);

        if (userId == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Username or password not found or incorrect. Please try again.");
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
            alert.setTitle("Login Error");
            alert.setContentText("An unknown error has occurred. Please try again.");
            alert.show();
        }
    }

    public void exitButtonClicked(ActionEvent mouseEvent) {
        System.exit(0);
//        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//        stage.close();
    }
}