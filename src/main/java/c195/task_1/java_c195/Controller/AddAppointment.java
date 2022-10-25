package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointment {
    public TextField appointmentID;
    public TextField appointmentTitle;
    public TextArea appointmentDesc;
    public TextField appointmentLoc;
    public TextField appointmentType;
    public DatePicker appointmentStartDate;
    public DatePicker appointmentEndDate;
    public TextField appointmentStartTime;
    public TextField appointmentEndTime;
    public ComboBox appointmentCustID;
    public ComboBox appointmentUID;
    public ComboBox appointmentCID;
    public Button saveAppointment;
    public Button cancelAppointment;

    public void saveEditAppointment(ActionEvent actionEvent) {

    }

    public void cancelEditAppointment(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
