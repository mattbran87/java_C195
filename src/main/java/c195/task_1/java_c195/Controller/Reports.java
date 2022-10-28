package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.DAO.AppointmentCRUD;
import c195.task_1.java_c195.MainApplication;
import c195.task_1.java_c195.Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Reports {
    public Button appointments;
    public Button customers;
    public Tab apptByType;
    public Tab contactSched;
    public Tab hrsPerUsers;

    public void apptByTypeSelected(Event event) {

    }

    public void contactSchedSelected(Event event) {

    }

    public void hrsPerUsersSelected(Event event) {

    }

    public void appointmentsButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void customersButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Customers.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Customers");
        stage.setScene(scene);
        stage.show();
    }

    public void exitButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the program?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
