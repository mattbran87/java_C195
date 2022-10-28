package c195.task_1.java_c195.DAO;


import c195.task_1.java_c195.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import c195.task_1.java_c195.helper.JDBC;
import c195.task_1.java_c195.Model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static c195.task_1.java_c195.helper.Helper.convertLocalDateTimeToUTC;
import static c195.task_1.java_c195.helper.Helper.getCurrentFormattedTimeUTC;

public class AppointmentCRUD {
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(
                    id,
                    title,
                    description,
                    location,
                    type,
                    start,
                    end,
                    customerID,
                    userID,
                    contactID
            );
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

    public static ObservableList<Appointment> getAppointmentsByDateRange(String startDate, String endDate) throws SQLException {
        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN '"+ startDate + "' AND '" + endDate + "'";

        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(
                    id,
                    title,
                    description,
                    location,
                    type,
                    start,
                    end,
                    customerID,
                    userID,
                    contactID
            );
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

    public static ObservableList<Appointment> getAllCustomerAppointments(int customersID) throws SQLException {
        ObservableList<Appointment> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments WHERE Customer_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);

        ps.setInt(1, customersID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(
                    id,
                    title,
                    description,
                    location,
                    type,
                    start,
                    end,
                    customerID,
                    userID,
                    contactID
            );
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

    public static int generateNewID() throws SQLException {
        String query = "SELECT MAX(Appointment_ID) AS max_id FROM appointments";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        Integer maxID = 0;
        if (rs.next()) {
            maxID = rs.getInt("max_id") + 1;
        }
        return maxID;
    }

    public static int deleteAppointment(int appointmentID) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(query);
        ps.setInt(1, appointmentID);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    public static int addAppointment(Appointment appointment) throws SQLException {
        User user = UserCRUD.getUserByID(appointment.getUserID());

        try {
            String query = "INSERT INTO appointments (" +
                    "Appointment_ID, " +
                    "Title, " +
                    "Description, " +
                    "Location, " +
                    "Type, " +
                    "Start, " +
                    "End, " +
                    "Create_Date, " +
                    "Created_By, " +
                    "Last_Update, " +
                    "Last_Updated_By, " +
                    "Customer_ID, " +
                    "User_ID, " +
                    "Contact_ID" +
                    ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(query);

            ps.setInt(1, appointment.getAppointmentID());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setString(6, convertLocalDateTimeToUTC(appointment.getStart()));
            ps.setString(7, convertLocalDateTimeToUTC(appointment.getEnd()));
            ps.setString(8, getCurrentFormattedTimeUTC());
            ps.setString(9, user.getUsername());
            ps.setString(10, getCurrentFormattedTimeUTC());
            ps.setString(11, user.getUsername());
            ps.setInt(12, appointment.getCustomerID());
            ps.setInt(13, appointment.getUserID());
            ps.setInt(14, appointment.getContactID());

            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    public static int upDateAppointment(Appointment appointment) throws SQLException {
        // get user object by user id on appointment
        User user = UserCRUD.getUserByID(appointment.getUserID());

        try {
            String query = "UPDATE appointments " +
                    "SET Appointment_ID = ?, " +
                    "Title = ?, " +
                    "Description = ?, " +
                    "Location = ?, " +
                    "Type = ?, " +
                    "Start = ?, " +
                    "End = ?, " +
                    "Last_Update = ?, " +
                    "Last_Updated_By = ?, " +
                    "Customer_ID = ?, " +
                    "User_ID = ?, " +
                    "Contact_ID = ? " +
                    "WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(query);

            ps.setInt(1, appointment.getAppointmentID());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setString(6, convertLocalDateTimeToUTC(appointment.getStart()));
            ps.setString(7, convertLocalDateTimeToUTC(appointment.getEnd()));
            ps.setString(8, getCurrentFormattedTimeUTC());
            ps.setString(9, user.getUsername());
            ps.setInt(10, appointment.getCustomerID());
            ps.setInt(11, appointment.getUserID());
            ps.setInt(12, appointment.getContactID());
            ps.setInt(13, appointment.getAppointmentID());

            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }
}
