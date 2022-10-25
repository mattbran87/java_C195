package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.Customer;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerCRUD {
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList();
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
}
