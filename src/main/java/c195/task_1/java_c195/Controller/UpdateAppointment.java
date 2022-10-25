package c195.task_1.java_c195.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.Contact;
import c195.task_1.java_c195.Model.Customer;
import c195.task_1.java_c195.Model.User;
import c195.task_1.java_c195.DAO.ContactCRUD;
import c195.task_1.java_c195.DAO.CustomerCRUD;
import c195.task_1.java_c195.DAO.UserCRUD;
import c195.task_1.java_c195.MainApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static c195.task_1.java_c195.DAO.AppointmentCRUD.addAppointment;

public class UpdateAppointment {
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

    public Appointment appointment;

    public void passAppointmentData(Appointment selectedAppointment) throws SQLException {
        ZonedDateTime startDateTimeUTC = selectedAppointment.getStartDateTime().toInstant(OffsetDateTime.now().getOffset()).atZone(ZoneOffset.UTC);
        ZonedDateTime endDateTimeUTC = selectedAppointment.getEndDateTime().toInstant(OffsetDateTime.now().getOffset()).atZone(ZoneOffset.UTC);

        ZonedDateTime localStartDateTime = startDateTimeUTC.withZoneSameInstant(TimeZone.getDefault().toZoneId());
        ZonedDateTime localEndDateTime = endDateTimeUTC.withZoneSameInstant(TimeZone.getDefault().toZoneId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startTimeString = localStartDateTime.format(formatter);
        String endTimeString = localEndDateTime.format(formatter);

        appointmentID.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        appointmentTitle.setText(selectedAppointment.getTitle());
        appointmentDesc.setText(selectedAppointment.getDescription());
        appointmentLoc.setText(selectedAppointment.getLocation());
        appointmentType.setText(selectedAppointment.getType());
        appointmentStartDate.setValue(selectedAppointment.getStartDateTime().toLocalDate());
        appointmentEndDate.setValue(selectedAppointment.getEndDateTime().toLocalDate());
        appointmentStartTime.setText(startTimeString);
        appointmentEndTime.setText(endTimeString);

        appointmentCustID.setItems(CustomerCRUD.getAllCustomerIDs());
        appointmentCustID.getSelectionModel().select(selectedAppointment.getCustomerID());

        appointmentUID.setItems(UserCRUD.getAllUserIDs());
        appointmentUID.getSelectionModel().select(selectedAppointment.getUserID());

        appointmentCID.setItems(ContactCRUD.getAllContactsByID());
        appointmentCID.getSelectionModel().select(selectedAppointment.getContactID());

        appointment = selectedAppointment;
    }

    public void saveEditAppointment(ActionEvent actionEvent) throws SQLException {
        int idInput = Integer.parseInt(appointmentID.getText());
        String titleInput = appointmentTitle.getText();
        String DescInput = appointmentDesc.getText();
        String locInput = appointmentLoc.getText();
        String typeInput = appointmentType.getText();
        LocalDate startDateInput = appointmentStartDate.getValue();
        LocalDate endDateInput = appointmentEndDate.getValue();
        String startTimeInput = appointmentStartTime.getText();
        String endTimeInput = appointmentEndTime.getText();
        int custIDInput = Integer.parseInt(appointmentCustID.getValue().toString());
        int uidInput = Integer.parseInt(appointmentUID.getValue().toString());
        int cidInput = Integer.parseInt(appointmentCID.getValue().toString());

        LocalTime parsedStartTime = LocalTime.parse(startTimeInput + ":00");
        LocalTime parsedEndTime = LocalTime.parse(endTimeInput + ":00");

        LocalDateTime newStartDateTime = LocalDateTime.of(startDateInput, parsedStartTime);
        LocalDateTime newEndDateTime = LocalDateTime.of(endDateInput, parsedEndTime);

        appointment.setAppointmentID(idInput);
        appointment.setTitle(titleInput);
        appointment.setDescription(DescInput);
        appointment.setLocation(locInput);
        appointment.setType(typeInput);
        appointment.setStartDateTime(newStartDateTime);
        appointment.setEndDateTime(newEndDateTime);
        appointment.setCustomerID(custIDInput);
        appointment.setUserID(uidInput);
        appointment.setContactID(cidInput);

        int updateAppointment = addAppointment(appointment);
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
