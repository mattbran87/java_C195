package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.Contact;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ContactCRUD {
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from contacts";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("contactID");
            String name = rs.getString("Name");
            String email = rs.getString("Email");
            Contact contact = new Contact(
                    id,
                    name,
                    email
            );
            contactsObservableList.add(contact);
        }

        return contactsObservableList;
    }

    public static ObservableList<Integer> getAllContactIDs() throws SQLException {
        ObservableList<Integer> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID from contacts";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Contact_ID");
            contactsObservableList.add(id);
        }

        return contactsObservableList;
    }
}
