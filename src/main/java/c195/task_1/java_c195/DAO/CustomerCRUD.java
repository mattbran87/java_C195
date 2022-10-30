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
    /**
     * get all customers from customer table
     * @return customersObservableList
     * @throws SQLException
     */
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

    /**
     * get all customer ids from customers table
     * @return customersObservableList
     * @throws SQLException
     */
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

    public static ObservableList<String> getAllCustomerNames() throws SQLException {
        ObservableList<String> customersObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_Name from customers";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String name = rs.getString("Customer_Name");
            customersObservableList.add(name);
        }

        return customersObservableList;
    }

    /**
     * get customer from customers table that matches the Customer ID
     * @param id
     * @return customer
     * @throws SQLException
     */
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

    public static Customer getCustomerByName(String name) throws SQLException {
        String sql = "SELECT * from customers WHERE Customer_Name = '" + name + "'";
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

    /**
     * generate new customer ID
     * @return maxID
     * @throws SQLException
     */
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

    /**
     * delete customer from customers table that matches customer ID
     * @param customerID
     * @return int 0 for fail 1 for success
     * @throws SQLException
     */
    public static int deleteCustomer(int customerID) throws SQLException {
        try {
            String query = "DELETE FROM customers WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.openConnection().prepareStatement(query);
            ps.setInt(1, customerID);
            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    /**
     * add new customer to the customers table
     * @param customer
     * @return int 0 for fail 1 for success
     * @throws SQLException
     */
    public static int addCustomer(Customer customer) throws SQLException {
        try {
            String query = "INSERT INTO customers (" +
                    "Customer_ID, " +
                    "Customer_Name, " +
                    "Address, " +
                    "Postal_Code, " +
                    "Phone, " +
                    "Division_ID" +
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

    /**
     * update existing customer record in the customers table by matching customer id
     * @param customer
     * @return int 0 for fail 1 for success
     * @throws SQLException
     */
    public static int updateCustomer(Customer customer) throws SQLException {
        try {
            String query = "UPDATE customers " +
                    "SET Customer_ID = ?, " +
                    "Customer_Name = ?, " +
                    "Address = ?, " +
                    "Postal_Code = ?, " +
                    "Phone = ?, " +
                    "Division_ID = ? " +
                    "WHERE Customer_ID = ?";

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
