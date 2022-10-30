package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import c195.task_1.java_c195.DAO.AppointmentCRUD;
import c195.task_1.java_c195.DAO.ContactCRUD;
import c195.task_1.java_c195.MainApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

public class Reports {
    public Button appointments;
    public Button customers;
    public Tab apptByType;
    public Tab contactSched;
    public Tab hrsPerUsers;
    public TableView<MonthTypeReport> appointmentsTable;
    public TableColumn appointmentsTableYear;
    public TableColumn appointmentsTableMonth;
    public TableColumn appointmentsTableType;
    public TableColumn appointmentsTableAppts;
    public ComboBox contactCombobox;
    public TableView<ScheduleReport> contactSchedule;
    public TableColumn contactScheduleApptID;
    public TableColumn contactScheduleTitle;
    public TableColumn contactScheduleType;
    public TableColumn contactScheduleDesc;
    public TableColumn contactScheduleStart;
    public TableColumn contactScheduleEnd;
    public TableColumn contactScheduleCustID;
    public TableView<HoursReport> hoursScheduled;
    public TableColumn hoursScheduledContactID;
    public TableColumn hoursScheduledName;
    public TableColumn hoursScheduledHours;
    public TableColumn hoursScheduledHoursYTD;

    public void initialize() throws SQLException {
        // add contact names to combobox
        ObservableList<String> contactNames = ContactCRUD.getAllContactNames();
        contactCombobox.setItems(contactNames);
        // populate appointment month and type table
        appointmentsTableYear.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, MonthTypeReport>("year"));
        appointmentsTableMonth.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, String>("month"));
        appointmentsTableType.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, String>("type"));
        appointmentsTableAppts.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, Integer>("numberOfAppointments"));
        ObservableList<MonthTypeReport> appointmentsTypeMonth = appointmentsByTypeMonth();
        appointmentsTable.setItems(appointmentsTypeMonth);
    }

    public static ObservableList<MonthTypeReport> appointmentsByTypeMonth() throws SQLException {
        ObservableList<MonthTypeReport> monthTypeReports = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = AppointmentCRUD.getAllAppointments();
        Hashtable<String, Hashtable<String, Integer>> monthsTypeTable = new Hashtable<String, Hashtable<String, Integer>>();

        for (Appointment appointment : appointments) {
            LocalDateTime startDate = appointment.getStart();
            String monthYear = startDate.getMonth().toString() + "_" + String.valueOf(startDate.getYear());
            String type = appointment.getType();
            Hashtable<String, Integer> typeTable = new Hashtable<String, Integer>();

            // check if monthYear key exists
            if (monthsTypeTable.containsKey(monthYear)) {
                // check if type key exists
                if (monthsTypeTable.get(monthYear).containsKey(type)) {
                    Integer count = monthsTypeTable.get(monthYear).get(type);
                    count+=1;
                    monthsTypeTable.get(monthYear).put(type, count);
                } else {
                    // else create new typeTable entry and push to monthsTypeTable
                    Hashtable<String, Integer> currentTable = monthsTypeTable.get(monthYear);
                    if (currentTable != null) {
                        typeTable.putAll(currentTable);
                    }
                    typeTable.put(type, 1);
                    monthsTypeTable.put(monthYear, typeTable);
                }
            } else {
                // else create new typeTable entry and push to monthsTypeTable
                Hashtable<String, Integer> currentTable = monthsTypeTable.get(monthYear);
                if (currentTable != null) {
                    typeTable.putAll(currentTable);
                }
                typeTable.put(type, 1);
                monthsTypeTable.put(monthYear, typeTable);
            }
        }

        for (Map.Entry<String, Hashtable<String, Integer>> monthYear : monthsTypeTable.entrySet()) {
            String monthYearKey = monthYear.getKey();
            String[] splitMonthYear = monthYearKey.split("_");
            Map<String, Integer> typeEntry = monthYear.getValue();

            // Iterate typeEntry
            for (Map.Entry<String, Integer> type : typeEntry.entrySet()) {
                String typeKey = type.getKey();
                Integer total = type.getValue();

                MonthTypeReport monthTypeReport = new MonthTypeReport(
                        splitMonthYear[1],
                        splitMonthYear[0],
                        typeKey,
                        total
                );

                monthTypeReports.add(monthTypeReport);
            }
        }

        return monthTypeReports;
    }


    public void apptByTypeSelected(Event event) {

    }

    public void contactSchedSelected(Event event) {

    }

    public void hrsPerUsersSelected(Event event) {

    }

    /**
     * @description go to appointments view
     * @param actionEvent
     * @throws IOException
     */
    public void appointmentsButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @description go to customers view
     * @param actionEvent
     * @throws IOException
     */
    public void customersButton(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Customers.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @description exit program
     * @param actionEvent
     */
    public void exitButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the program?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void contactComboboxClicked(ActionEvent actionEvent) {

    }
}
