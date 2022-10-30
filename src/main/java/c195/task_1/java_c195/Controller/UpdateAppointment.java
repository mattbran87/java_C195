package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.DAO.AppointmentCRUD;
import javafx.collections.ObservableList;
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
import static c195.task_1.java_c195.helper.Helper.convertLocalDateTimeToEST;
import static c195.task_1.java_c195.helper.Helper.convertLocalDateTimeToUTC;

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

        Customer customer = CustomerCRUD.getCustomerByID(selectedAppointment.getCustomerID());

        appointmentID.setText(Integer.toString(selectedAppointment.getAppointmentID()));
        appointmentTitle.setText(selectedAppointment.getTitle());
        appointmentDesc.setText(selectedAppointment.getDescription());
        appointmentLoc.setText(selectedAppointment.getLocation());
        appointmentType.setText(selectedAppointment.getType());
        appointmentStartDate.setValue(selectedAppointment.getStart().toLocalDate());
        appointmentEndDate.setValue(selectedAppointment.getEnd().toLocalDate());
        appointmentStartTime.setText(startTimeString);
        appointmentEndTime.setText(endTimeString);

        appointmentCustID.setItems(CustomerCRUD.getAllCustomerNames());
        appointmentCustID.getSelectionModel().select(customer.getName());

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

        String titleInput = "";
        if (appointmentTitle.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Title input is empty.");
            alert.showAndWait();
            return;
        } else {
            titleInput = appointmentTitle.getText();
        }

        String DescInput = "";
        if (appointmentDesc.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Description input is empty.");
            alert.showAndWait();
            return;
        } else {
            DescInput = appointmentDesc.getText();
        }

        String locInput = "";
        if (appointmentLoc.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Location input is empty.");
            alert.showAndWait();
            return;
        } else {
            locInput = appointmentLoc.getText();
        }

        String typeInput = "";
        if (appointmentType.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Type input is empty.");
            alert.showAndWait();
            return;
        } else {
            typeInput = appointmentType.getText();
        }

        LocalDate startDateInput = null;
        if (appointmentStartDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Start Date input is empty.");
            alert.showAndWait();
            return;
        } else {
            startDateInput = appointmentStartDate.getValue();
        }

        LocalDate endDateInput = null;
        if (appointmentEndDate.getValue()  == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "End Date input is empty.");
            alert.showAndWait();
            return;
        } else {
            endDateInput = appointmentEndDate.getValue();
        }

        String startTimeInput = "";
        if (appointmentStartTime.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Start Time input is empty.");
            alert.showAndWait();
            return;
        } else {
            startTimeInput = appointmentStartTime.getText() + ":00";
        }

        String endTimeInput = "";
        if (appointmentEndTime.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "End Time input is empty.");
            alert.showAndWait();
            return;
        } else {
            endTimeInput = appointmentEndTime.getText() + ":00";
        }

        int custIDInput = 0;
        if (appointmentCustID.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Customer ID input is empty.");
            alert.showAndWait();
            return;
        } else {
            Customer customer = CustomerCRUD.getCustomerByName(appointmentCustID.getValue().toString());
            custIDInput = customer.getCustomerID();
        }

        int uidInput = 0;
        if (appointmentUID.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "User ID input is empty.");
            alert.showAndWait();
            return;
        } else {
            uidInput = Integer.parseInt(appointmentUID.getValue().toString());
        }

        int cidInput = 0;
        if (appointmentCID.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Contact ID input is empty.");
            alert.showAndWait();
            return;
        } else {
            cidInput = Integer.parseInt(appointmentCID.getValue().toString());
        }

        LocalTime parsedStartTime = LocalTime.parse(startTimeInput);
        LocalTime parsedEndTime = LocalTime.parse(endTimeInput);

        LocalDateTime newStartDateTime = LocalDateTime.of(startDateInput, parsedStartTime);
        LocalDateTime newEndDateTime = LocalDateTime.of(endDateInput, parsedEndTime);

        ZonedDateTime estStartDateTime = convertLocalDateTimeToEST(newStartDateTime);
        ZonedDateTime estEndDateTime = convertLocalDateTimeToEST(newEndDateTime);

        String startDayOfWeek = estStartDateTime.getDayOfWeek().toString().toUpperCase();
        String endDayOfWeek = estEndDateTime.getDayOfWeek().toString().toUpperCase();

        LocalTime startTimeOfDay = estStartDateTime.toLocalTime();
        LocalTime endTimeOfDay = estEndDateTime.toLocalTime();

        LocalTime scheduleStartTime = LocalTime.of(8, 0, 0);
        LocalTime scheduleEndTime = LocalTime.of(22, 0, 0);

        ObservableList<Appointment> appointmentsOverlapObservableList =
                AppointmentCRUD.getAppointmentsByDateRange(convertLocalDateTimeToUTC(newStartDateTime), convertLocalDateTimeToUTC(newEndDateTime));

        if (appointmentsOverlapObservableList.size() > 0) {
            for (Appointment overlapAppointment : appointmentsOverlapObservableList) {
                Integer overlapAppointmentCustomerID = overlapAppointment.getCustomerID();
                Integer overlapAppointmentID = overlapAppointment.getAppointmentID();
                if (overlapAppointmentCustomerID == custIDInput &&
                        overlapAppointmentID != idInput
                ) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Customer is already scheduled for that time.");
                    alert.showAndWait();
                    return;
                }
            }
        }

        if (startDayOfWeek == "SATURDAY" ||
                startDayOfWeek == "SUNDAY"
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Start day and time is set to a weekend day. Please select a day Monday - Friday.");
            alert.showAndWait();
            return;
        }

        if (endDayOfWeek == "SATURDAY" ||
                endDayOfWeek == "SUNDAY"
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "End day and time is set to a weekend day. Please select a day Monday - Friday.");
            alert.showAndWait();
            return;
        }

        if (startTimeOfDay.isAfter(scheduleEndTime) && startTimeOfDay.isBefore(scheduleStartTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Start time is set outside of business hours of 8am - 10pm EST");
            alert.showAndWait();
            return;
        }

        if (endTimeOfDay.isAfter(scheduleEndTime) && endTimeOfDay.isBefore(scheduleStartTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "End time is set outside of business hours of 8am - 10pm EST");
            alert.showAndWait();
            return;
        }

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
