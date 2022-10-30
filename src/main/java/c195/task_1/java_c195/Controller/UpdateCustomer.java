package c195.task_1.java_c195.Controller;


import c195.task_1.java_c195.DAO.CountryCRUD;
import c195.task_1.java_c195.DAO.CustomerCRUD;
import c195.task_1.java_c195.DAO.FirstLevelDivisionCRUD;
import c195.task_1.java_c195.DAO.UserCRUD;
import c195.task_1.java_c195.Model.Country;
import c195.task_1.java_c195.Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import c195.task_1.java_c195.MainApplication;
import c195.task_1.java_c195.Model.Customer;

import static c195.task_1.java_c195.DAO.CustomerCRUD.updateCustomer;

public class UpdateCustomer {

    public Button saveButton;
    public Button cancelButton;
    public ComboBox country;
    public ComboBox division;
    public TextField customerID;
    public TextField customerName;
    public TextField address;
    public TextField zipcode;
    public TextField phoneNumber;
    public Customer customer;

    /**
     * @description load the selected customer user selected from the previous view's table. Load the record data into
     * the matching inputs.
     * @param selectedCustomer
     * @throws SQLException
     */
    public void passCustomerData(Customer selectedCustomer) throws SQLException {
        customerID.setText(Integer.toString(selectedCustomer.getCustomerID()));

        customerName.setText(selectedCustomer.getName());
        address.setText(selectedCustomer.getAddress());
        zipcode.setText(selectedCustomer.getPostalCode());
        phoneNumber.setText(selectedCustomer.getPhoneNumber());

        FirstLevelDivision firstLevelDivision = FirstLevelDivisionCRUD.getFirstLevelDivisionByDivisionID(selectedCustomer.getDivisionID());
        Country countryObject = CountryCRUD.getCountryByID(firstLevelDivision.getCountryID());

        country.setItems(CountryCRUD.getAllCountryNames());
        country.getSelectionModel().select(countryObject.getCountryName());

        division.setItems(FirstLevelDivisionCRUD.getAllFirstLevelDivisionNamesByCountryID(countryObject.getCountryID()));
        division.getSelectionModel().select(firstLevelDivision.getDivisionName());

        customer = selectedCustomer;
    }

    /**
     * @description update existing customer in the database. all inputs are validated for null values. If all inputs are not
     * empty then customer record is updated and user forwarded to customers view.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void saveButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        int idInput = Integer.parseInt(customerID.getText());

        int countryInput = 0;
        if (country.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Country input is empty.");
            alert.showAndWait();
            return;
        } else {
            String countryValue = country.getValue().toString();
            Country country = CountryCRUD.getCountryByName(countryValue);
            countryInput = country.getCountryID();
        }

        int divisionInput = 0;
        if (division.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Division input is empty.");
            alert.showAndWait();
            return;
        } else {
            String divisionValue = division.getValue().toString();
            FirstLevelDivision firstLevelDivision = FirstLevelDivisionCRUD.getFirstLevelDivisionByDivisionName(divisionValue);
            divisionInput = firstLevelDivision.getDivisionID();
        }

        String customerNameInput = "";
        if (customerName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Customer Name input is empty.");
            alert.showAndWait();
            return;
        } else {
            customerNameInput = customerName.getText();
        }

        String addressInput = "";
        if (address.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Address input is empty.");
            alert.showAndWait();
            return;
        } else {
            addressInput = address.getText();
        }

        String zipcodeInput = "";
        if (zipcode.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Zipcode input is empty.");
            alert.showAndWait();
            return;
        } else {
            zipcodeInput = zipcode.getText();
        }

        String phoneNumberInput = "";
        if (phoneNumber.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Phone Number input is empty.");
            alert.showAndWait();
            return;
        } else {
            phoneNumberInput = phoneNumber.getText();
        }

        customer = new Customer(
                idInput,
                customerNameInput,
                addressInput,
                zipcodeInput,
                phoneNumberInput,
                divisionInput
        );

        int addCustomer = updateCustomer(customer);

        if (addCustomer > 0) {
            Parent parent = FXMLLoader.load(MainApplication.class.getResource("Customers.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Appointment Scheduler - Customers");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error has occurred. Could not save new appointment.");
            alert.showAndWait();
        }
    }

    /**
     * @description user is forwarded back to customers view
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Customers.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @description when user selects a new division update the country id combobox with related countries
     * @param actionEvent
     * @throws SQLException
     */
    public void onSelectionChanged(ActionEvent actionEvent) throws SQLException {
        if (country.getValue() != null) {
            String countryValue = country.getValue().toString();
            Country country = CountryCRUD.getCountryByName(countryValue);
            int countryID = country.getCountryID();
            division.setItems(FirstLevelDivisionCRUD.getAllFirstLevelDivisionNamesByCountryID(countryID));
        } else {
            return;
        }
    }
}
