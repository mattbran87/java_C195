package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.Contact;
import c195.task_1.java_c195.Model.Customer;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ContactCRUD {
    /**
     * get all contacts in contacts table
     * @return contactsObservableList
     * @throws SQLException
     */
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

    /**
     * get all the contact ids
     * @return contactsObservableList
     * @throws SQLException
     */
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

    /**
     * get all contact names in contacts
     * @return contactsObservableList
     * @throws SQLException
     */
    public static ObservableList<String> getAllContactNames() throws SQLException {
        ObservableList<String> contactsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name from contacts";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String name = rs.getString("Contact_Name");
            contactsObservableList.add(name);
        }

        return contactsObservableList;
    }

    /**
     * get contact by contact id
     * @param ID
     * @return contact
     * @throws SQLException
     */
    public static Contact getContactByID(Integer ID) throws SQLException {
        String sql = "SELECT * from contacts WHERE Contact_ID = '" + ID + "'";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Contact contact = null;

        while(rs.next()) {
            Integer contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            contact = new Contact(
                    contactID,
                    name,
                    email
            );

        }
        return contact;
    }

    /**
     * get contact by contact name
     * @param contactName
     * @return contact
     * @throws SQLException
     */
    public static Contact getContactByName(String contactName) throws SQLException {
        String sql = "SELECT * from contacts WHERE Contact_Name = '" + contactName + "'";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Contact contact = null;

        while(rs.next()) {
            Integer contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            contact = new Contact(
                    contactID,
                    name,
                    email
            );

        }
        return contact;
    }
}
