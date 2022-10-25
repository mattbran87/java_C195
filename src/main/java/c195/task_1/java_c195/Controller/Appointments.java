package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.MainApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import c195.task_1.java_c195.helper.JDBC;
import c195.task_1.java_c195.DAO.AppointmentCRUD;
import c195.task_1.java_c195.Model.Appointment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Appointments {
    public TableView<Appointment> appointmentsTable;
    public TableColumn appointmentsTableID;
    public TableColumn appointmentsTableTitle;
    public TableColumn appointmentsTableType;
    public TableColumn appointmentsTableDesc;
    public TableColumn appointmentsTableLoc;
    public TableColumn appointmentsTableStart;
    public TableColumn appointmentsTableEnd;
    public TableColumn appointmentsTableCustID;
    public TableColumn appointmentsTableCID;
    public TableColumn appointmentsTableUID;
    public ToggleGroup tableFilter;
    public RadioButton radioNone;
    public RadioButton radioWeek;
    public RadioButton radioMonth;
    public Button createButton;
    public Button updateButton;
    public Button deleteButton;
    public Button customersButton;
    public Button reportsButton;
    public Button exitButton;


    public void initialize() throws SQLException {
        // get all appointments
        ObservableList<Appointment> allAppointments = AppointmentCRUD.getAllAppointments();
        // set columns
//        appointmentsTableID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
//        appointmentsTableTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
//        appointmentsTableType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
//        appointmentsTableDesc.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
//        appointmentsTableLoc.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
//        appointmentsTableStart.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
//        appointmentsTableEnd.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("end"));
//        appointmentsTableCustID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
//        appointmentsTableUID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
//        appointmentsTableCID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
//        // set table values
//        appointmentsTable.setItems(allAppointments);

        appointmentsTableID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentsTableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsTableType.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsTableDesc.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsTableLoc.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentsTableStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentsTableEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentsTableCustID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentsTableUID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTableCID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentsTable.setItems(allAppointments);
    }

    public void radioNoneClicked(ActionEvent actionEvent) throws SQLException {
        // get all appointments
        ObservableList<Appointment> allAppointments = AppointmentCRUD.getAllAppointments();
        appointmentsTable.setItems(allAppointments);
    }

    public void radioWeekClicked(ActionEvent actionEvent) throws SQLException {
        // get all appointments
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7Days = LocalDateTime.now().plusDays(7);

        ObservableList<Appointment> allAppointments = AppointmentCRUD.getAppointmentsByDateRange(now.format(formatter), nowPlus7Days.format(formatter));
        appointmentsTable.setItems(allAppointments);
    }

    public void radioMonthClicked(ActionEvent actionEvent) throws SQLException {
        // get all appointments
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus1Month = LocalDateTime.now().plusMonths(1);

        ObservableList<Appointment> allAppointments = AppointmentCRUD.getAppointmentsByDateRange(now.format(formatter), nowPlus1Month.format(formatter));
        appointmentsTable.setItems(allAppointments);
    }

    public void createButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void updateButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No appointment selected.");
            alert.showAndWait();
            return;
        }

        FXMLLoader parent = new FXMLLoader();
        parent.setLocation(MainApplication.class.getResource("UpdateAppointment.fxml"));
        Parent scene = parent.load();
        UpdateAppointment controller = parent.getController();
        controller.passAppointmentData(selectedAppointment);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler - Update Appointment");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        try {
            // connect to db
            Connection connection = JDBC.openConnection();
            // get appointment values
            int appointmentID = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID();
            String title = appointmentsTable.getSelectionModel().getSelectedItem().getTitle();
            // confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + title + "?");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                AppointmentCRUD.deleteAppointment(appointmentID);
                // reset table
                ObservableList<Appointment> allAppointmentsList = AppointmentCRUD.getAllAppointments();
                appointmentsTable.setItems(allAppointmentsList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customersButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Reports.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void exitButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}

