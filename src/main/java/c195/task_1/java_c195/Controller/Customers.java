package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.DAO.AppointmentCRUD;
import c195.task_1.java_c195.DAO.CountryCRUD;
import c195.task_1.java_c195.DAO.CustomerCRUD;
import c195.task_1.java_c195.DAO.FirstLevelDivisionCRUD;
import c195.task_1.java_c195.MainApplication;
import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.Country;
import c195.task_1.java_c195.Model.Customer;
import c195.task_1.java_c195.Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Customers {

    public TableView<Customer> customerTable;
    public TableColumn customerTableID;
    public TableColumn customerTableName;
    public TableColumn customerTableDiv;
    public TableColumn customerTableAddr;
    public TableColumn customerTableZip;
    public TableColumn customerTableCtry;
    public TableColumn customerTablePhone;
    public Button customerCreate;
    public Button customerUpdate;
    public Button customerDelete;
    public Button apptButton;
    public Button reportsButton;
    public TextField customerSearchInput;
    public ComboBox customerSearchCombobox;
    public Button customerSearchButton;
    public Button exitButton;

    /**
     * on view initialization the customer objects get country and division names set. View table is populated with customer
     * object data.
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        ObservableList<Customer> allCustomers = CustomerCRUD.getAllCustomers();
        for (Customer customer : allCustomers) {
            FirstLevelDivision firstLevelDivision = FirstLevelDivisionCRUD.getFirstLevelDivisionByDivisionID(customer.getDivisionID());
            Country country = CountryCRUD.getCountryByID(firstLevelDivision.getCountryID());

            customer.setCountry(country.getCountryName());
            customer.setDivision(firstLevelDivision.getDivisionName());
        }
        customerTableID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerTableName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        customerTableDiv.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
        customerTableAddr.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        customerTableZip.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        customerTableCtry.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
        customerTablePhone.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNumber"));
        customerTable.setItems(allCustomers);

        ObservableList<String> columnSearchList = FXCollections.observableArrayList();
        columnSearchList.addAll("Customer_ID", "Customer_Name", "Address", "Postal_Code", "Phone");

        customerSearchCombobox.setItems(columnSearchList);
    }

    /**
     * go to AddCustomer view to add a new customer record
     * @param actionEvent
     * @throws IOException
     */
    public void createButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * go to UpdateCustomer view with the selected customer record data loaded in view inputs
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void updateButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No customer selected.");
            alert.showAndWait();
            return;
        }

        FXMLLoader parent = new FXMLLoader();
        parent.setLocation(MainApplication.class.getResource("UpdateCustomer.fxml"));
        Parent scene = parent.load();
        UpdateCustomer controller = parent.getController();
        controller.passCustomerData(selectedCustomer);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler - Update Customer");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * delete selected customer from customers table and customers associated appointments. Function uses lambda
     * to delete each appointment for the customer using the appointment ID for each associated appointment.
     * @param actionEvent
     */
    public void deleteButtonClicked(ActionEvent actionEvent) {
        try {
            int customerID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
            String name = customerTable.getSelectionModel().getSelectedItem().getName();
            // confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + name + "? This will also delete their appointments.");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                // first get any associated appointments and delete them all
                ObservableList<Appointment> customerAppoinments = AppointmentCRUD.getAllCustomerAppointments(customerID);
                if (customerAppoinments.size() > 0) {

                    // lambda expression 1. Try catch required by text editor
                    customerAppoinments.forEach( (Appointment appointment) -> {
                        try {
                            AppointmentCRUD.deleteAppointment(appointment.getAppointmentID());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } );
                }
                // then delete customer
                CustomerCRUD.deleteCustomer(customerID);
                // reset table
                ObservableList<Customer> allCustomersList = CustomerCRUD.getAllCustomers();
                customerTable.setItems(allCustomersList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * go to appointments view
     * @param actionEvent
     * @throws IOException
     */
    public void apptButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * go to reports view
     * @param actionEvent
     * @throws IOException
     */
    public void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Reports.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Reports");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * exit program
     * @param actionEvent
     */
    public void exitButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the program?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void clickSearchButton(ActionEvent actionEvent) throws SQLException {
        String inputValue = "";
        String comboValue = "";

        if (customerSearchInput.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Search Value input is empty.");
            alert.showAndWait();
            return;
        } else {
            inputValue = customerSearchInput.getText();
        }

        if (customerSearchCombobox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Search Column input is empty.");
            alert.showAndWait();
            return;
        } else {
            comboValue = customerSearchCombobox.getValue().toString();
        }

        ObservableList<Customer> allCustomers = CustomerCRUD.getAllCustomersBySearchField(inputValue, comboValue);
        // correct fields for table
        for (Customer customer : allCustomers) {
            FirstLevelDivision firstLevelDivision = FirstLevelDivisionCRUD.getFirstLevelDivisionByDivisionID(customer.getDivisionID());
            Country country = CountryCRUD.getCountryByID(firstLevelDivision.getCountryID());

            customer.setCountry(country.getCountryName());
            customer.setDivision(firstLevelDivision.getDivisionName());
        }
        // set columns
        customerTable.setItems(allCustomers);

        if (allCustomers.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No results found.");
            alert.showAndWait();
        }
    }

    public void resetSearchResults(ActionEvent actionEvent) throws SQLException {
        ObservableList<Customer> allCustomers = CustomerCRUD.getAllCustomers();
        for (Customer customer : allCustomers) {
            FirstLevelDivision firstLevelDivision = FirstLevelDivisionCRUD.getFirstLevelDivisionByDivisionID(customer.getDivisionID());
            Country country = CountryCRUD.getCountryByID(firstLevelDivision.getCountryID());

            customer.setCountry(country.getCountryName());
            customer.setDivision(firstLevelDivision.getDivisionName());
        }

        customerSearchInput.setText("");
        customerSearchCombobox.setValue("");

        customerTable.setItems(allCustomers);
    }
}
