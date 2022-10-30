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
import java.time.temporal.ChronoUnit;
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
    public TableView<Appointment> contactSchedule;
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
        // populate Appointments By Type & Month
        appointmentsTableYear.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, String>("year"));
        appointmentsTableMonth.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, String>("month"));
        appointmentsTableType.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, String>("type"));
        appointmentsTableAppts.setCellValueFactory(new PropertyValueFactory<MonthTypeReport, Integer>("numberOfAppointments"));
        ObservableList<MonthTypeReport> appointmentsTypeMonth = appointmentsByTypeMonth();
        appointmentsTable.setItems(appointmentsTypeMonth);
        // populate Hours Scheduled Per Contact
        hoursScheduledContactID.setCellValueFactory(new PropertyValueFactory<HoursReport, Integer>("contactID"));
        hoursScheduledName.setCellValueFactory(new PropertyValueFactory<HoursReport, String>("contactName"));
        hoursScheduledHours.setCellValueFactory(new PropertyValueFactory<HoursReport, String>("hoursScheduled"));
        hoursScheduledHoursYTD.setCellValueFactory(new PropertyValueFactory<HoursReport, Integer>("hoursWorked"));
        ObservableList<HoursReport> hoursReports = scheduledHoursPerContact();
        hoursScheduled.setItems(hoursReports);
    }

    /**
     * @description get all appointments and builds an ObservableArrayList of MonthTypeReport objects and returns it to
     * be displayed in the Appointments By Type & Month report
     * @return monthTypeReports
     * @throws SQLException
     */
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

    /**
     * description get all appointments and builds an ObservableArrayList of HoursReport objects and returns it to
     * be displayed in the Hours Scheduled Per Contact report
     * @return hoursReports
     * @throws SQLException
     */
    public static ObservableList<HoursReport> scheduledHoursPerContact() throws SQLException {
        ObservableList<HoursReport> hoursReports = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = AppointmentCRUD.getAllAppointments();
        Hashtable<Integer, Hashtable<String, Float>> contactHours = new Hashtable<Integer, Hashtable<String, Float>>();

        for (Appointment appointment : appointments) {
            Integer contactID = appointment.getContactID();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = appointment.getStart();
            LocalDateTime end = appointment.getEnd();
            long minutesDifference = ChronoUnit.MINUTES.between(start, end);
            float hours = minutesDifference / 60;
            boolean scheduled = false;
            boolean worked = false;
            Hashtable<String, Float> hoursTable = new Hashtable<String, Float>();

            if (start.isAfter(now)) {
                scheduled = true;
            } else {
                worked = true;
            }

            if (contactHours.containsKey(contactID)) {
                // check if type key hoursWorked and hoursScheduled keys exist
                if (contactHours.get(contactID).containsKey("hoursWorked") && contactHours.get(contactID).containsKey("hoursScheduled")) {
                    if (worked) {
                        float hoursWorked = contactHours.get(contactID).get("hoursWorked");
                        float hoursScheduled = contactHours.get(contactID).get("hoursScheduled");
                        hoursTable.put("hoursWorked", hoursWorked + hours);
                        hoursTable.put("hoursScheduled", hoursScheduled);
                    }
                    if (scheduled) {
                        float hoursWorked = contactHours.get(contactID).get("hoursWorked");
                        float hoursScheduled = contactHours.get(contactID).get("hoursScheduled");
                        hoursTable.put("hoursWorked", hoursWorked);
                        hoursTable.put("hoursScheduled", hoursScheduled + hours);
                    }
                    contactHours.put(contactID, hoursTable);
                } else {
                    // else create new typeTable entry and push to contactHours
                    Hashtable<String, Float> currentTable = contactHours.get(contactID);
                    if (currentTable != null) {
                        hoursTable.putAll(currentTable);
                    }
                    if (worked) {
                        hoursTable.put("hoursWorked", hours);
                        hoursTable.put("hoursScheduled", 0f);
                    }
                    if (scheduled) {
                        hoursTable.put("hoursWorked", 0f);
                        hoursTable.put("hoursScheduled", hours);
                    }
                    contactHours.put(contactID, hoursTable);
                }
            } else {
                // else create new hoursTable entry and push to contactHours
                if (worked) {
                    hoursTable.put("hoursWorked", hours);
                    hoursTable.put("hoursScheduled", 0f);
                }
                if (scheduled) {
                    hoursTable.put("hoursWorked", 0f);
                    hoursTable.put("hoursScheduled", hours);
                }
                contactHours.put(contactID, hoursTable);
            }
        }

        for (Map.Entry<Integer, Hashtable<String, Float>> contactHoursData : contactHours.entrySet()) {
            Integer contactId = contactHoursData.getKey();
            Contact contact = ContactCRUD.getContactByID(contactId);
            String contactName = contact.getName();
            float hoursWorkedValue = 0f;
            float hoursScheduledValue = 0f;
            Map<String, Float> hoursWorkedScheduled = contactHoursData.getValue();

            for (Map.Entry<String, Float> type : hoursWorkedScheduled.entrySet()) {
                String typeKey = type.getKey();
                float total = type.getValue();

                if (typeKey == "hoursWorked") {
                    hoursWorkedValue = total;
                }

                if (typeKey == "hoursScheduled") {
                    hoursScheduledValue = total;
                }
            }

            HoursReport hoursReport = new HoursReport(
                    contactId,
                    contactName,
                    hoursWorkedValue,
                    hoursScheduledValue
            );
            hoursReports.add(hoursReport);
        }

        return hoursReports;
    }

    /**
     * @description when user selects a contact from the combobox the function updates the contactSchedule table with records
     * matching the selected name
     * @param actionEvent
     */
    public void contactComboboxClicked(ActionEvent actionEvent) throws SQLException {
        String contactName = contactCombobox.getValue().toString();
        Contact contact = ContactCRUD.getContactByName(contactName);
        ObservableList<Appointment> appointments = AppointmentCRUD.getAllAppointmentsByContactID(contact.contactID);
        contactScheduleApptID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        contactScheduleTitle.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("title"));
        contactScheduleType.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("type"));
        contactScheduleDesc.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("description"));
        contactScheduleStart.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("start"));
        contactScheduleEnd.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("end"));
        contactScheduleCustID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        contactSchedule.setItems(appointments);
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
}
