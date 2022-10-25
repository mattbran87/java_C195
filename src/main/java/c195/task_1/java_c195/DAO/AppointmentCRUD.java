package c195.task_1.java_c195.DAO;


import c195.task_1.java_c195.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import c195.task_1.java_c195.helper.JDBC;
import c195.task_1.java_c195.Model.Appointment;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static int deleteAppointment(int customer, Connection connection) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, customer);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    public static int addAppointment(Appointment appointment) throws SQLException {
        User user = UserCRUD.getUserByID(appointment.getUserID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        Instant startDateTime = appointment.getStartDateTime().toInstant(ZoneOffset.UTC);
        Instant endDateTime = appointment.getEndDateTime().toInstant(ZoneOffset.UTC);

        String formattedStart = startDateTime.atOffset(ZoneOffset.UTC).format(formatter);
        String formattedEnd = endDateTime.atOffset(ZoneOffset.UTC).format(formatter);

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
            ps.setString(6, formattedStart);
            ps.setString(7, formattedEnd);
            ps.setString(8, now.format(formatter));
            ps.setString(9, user.getUsername());
            ps.setString(10, now.format(formatter));
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
        User user = UserCRUD.getUserByID(appointment.getUserID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        Instant startDateTime = appointment.getStartDateTime().toInstant(ZoneOffset.UTC);
        Instant endDateTime = appointment.getEndDateTime().toInstant(ZoneOffset.UTC);

        String formattedStart = startDateTime.atOffset(ZoneOffset.UTC).format(formatter);
        String formattedEnd = endDateTime.atOffset(ZoneOffset.UTC).format(formatter);

        //TODO: complete conversion to UTC.
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
            ps.setString(6, formattedStart);
            ps.setString(7, formattedEnd);
            ps.setString(8, now.format(formatter));
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
