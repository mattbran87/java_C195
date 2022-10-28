package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.Customer;
import c195.task_1.java_c195.Model.User;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static c195.task_1.java_c195.helper.Helper.convertLocalDateTimeToUTC;

public class CustomerCRUD {
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from customers";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Integer customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Integer divisionId = rs.getInt("Division_ID");

            Customer customer = new Customer(
                    customerID,
                    customerName,
                    address,
                    postalCode,
                    phone,
                    divisionId
            );

            customersObservableList.add(customer);
        }
        return customersObservableList;
    }

    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {
        ObservableList<Integer> customersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID from customers";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Customer_ID");
            customersObservableList.add(id);
        }

        return customersObservableList;
    }

    public static Customer getCustomerByID(Integer id) throws SQLException {
        String sql = "SELECT * from customers WHERE Customer_ID = '" + id + "'";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Customer customer = null;
        while(rs.next()) {
            Integer customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Integer divisionId = rs.getInt("Division_ID");

            customer = new Customer(
                    customerID,
                    customerName,
                    address,
                    postalCode,
                    phone,
                    divisionId
            );

        }
        return customer;
    }

    public static int generateNewID() throws SQLException {
        String query = "SELECT MAX(Customer_ID) AS max_id FROM customers";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        Integer maxID = 0;
        if (rs.next()) {
            maxID = rs.getInt("max_id") + 1;
        }
        return maxID;
    }

    public static int deleteCustomer(int customerID) throws SQLException {
        String query = "DELETE FROM customers WHERE Customer_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(query);
        ps.setInt(1, customerID);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    public static int addCustomer(Customer customer) throws SQLException {
        try {
            String query = "INSERT INTO customers (" +
                    "customerID, " +
                    "name, " +
                    "address, " +
                    "postalCode, " +
                    "phoneNumber, " +
                    "divisionID" +
                    ") VALUES (?,?,?,?,?,?)";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(query);

            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhoneNumber());
            ps.setInt(6, customer.getDivisionID());

            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    public static int updateCustomer(Customer customer) throws SQLException {
        try {
            String query = "UPDATE customers " +
                    "SET customerID = ?, " +
                    "name, " +
                    "address, " +
                    "postalCode, " +
                    "phoneNumber, " +
                    "divisionID" +
                    "WHERE customerID = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(query);

            ps.setInt(1, customer.getCustomerID());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhoneNumber());
            ps.setInt(6, customer.getDivisionID());
            ps.setInt(7, customer.getCustomerID());

            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }
}
