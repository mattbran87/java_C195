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

import static c195.task_1.java_c195.DAO.AppointmentCRUD.upDateAppointment;

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

    /**
     * customer object. Object is instantiated in the passAppointmentData function
     */
    public Appointment appointment;

    /**
     * load the selected appointment user selected from the previous view's table. Load the record data into
     * the matching inputs.
     * @param selectedAppointment
     * @throws SQLException
     */
    public void passAppointmentData(Appointment selectedAppointment) throws SQLException {
        ZonedDateTime startDateTimeUTC = selectedAppointment.getStart().toInstant(OffsetDateTime.now().getOffset()).atZone(ZoneOffset.UTC);
        ZonedDateTime endDateTimeUTC = selectedAppointment.getEnd().toInstant(OffsetDateTime.now().getOffset()).atZone(ZoneOffset.UTC);

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
        appointmentStartDate.setValue(selectedAppointment.getStart().toLocalDate());
        appointmentEndDate.setValue(selectedAppointment.getEnd().toLocalDate());
        appointmentStartTime.setText(startTimeString);
        appointmentEndTime.setText(endTimeString);

        appointmentCustID.setItems(CustomerCRUD.getAllCustomerIDs());
        appointmentCustID.getSelectionModel().select(selectedAppointment.getCustomerID());

        appointmentUID.setItems(UserCRUD.getAllUserIDs());
        appointmentUID.getSelectionModel().select(selectedAppointment.getUserID());

        appointmentCID.setItems(ContactCRUD.getAllContactIDs());
        appointmentCID.getSelectionModel().select(selectedAppointment.getContactID());

        appointment = selectedAppointment;
    }

    /**
     * update existing appointment in the database. all inputs are validated for null values and determines if
     * the appointment date and time falls within business hours. If outside of business hours appointment is not valid. if
     * appointment passes validation the record is updated and user is forwarded back to appointments view.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void saveEditAppointment(ActionEvent actionEvent) throws SQLException, IOException {
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

        User user = UserCRUD.getUserByID(appointment.getUserID());

        appointment.setAppointmentID(idInput);
        appointment.setTitle(titleInput);
        appointment.setDescription(DescInput);
        appointment.setLocation(locInput);
        appointment.setType(typeInput);
        appointment.setStart(newStartDateTime);
        appointment.setEnd(newEndDateTime);
        appointment.setLastUpdatedBy(user.getUsername());
        appointment.setCustomerID(custIDInput);
        appointment.setUserID(uidInput);
        appointment.setContactID(cidInput);

        int updateAppointment = upDateAppointment(appointment);

        if (updateAppointment > 0) {
            Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Appointment Scheduler - Appointments");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error has occurred. Could not update appointment.");
            alert.showAndWait();
        }
    }

    /**
     * user is forwarded back to appointments view
     * @param actionEvent
     * @throws IOException
     */
    public void cancelEditAppointment(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
