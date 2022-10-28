package c195.task_1.java_c195.Controller;

import c195.task_1.java_c195.DAO.AppointmentCRUD;
import c195.task_1.java_c195.DAO.CustomerCRUD;
import c195.task_1.java_c195.MainApplication;
import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.Customer;
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
    public Button exitButton;

    public void initialize() throws SQLException {
        ObservableList<Customer> allCustomers = CustomerCRUD.getAllCustomers();
        customerTableID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerTableDiv.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTableAddr.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerTableZip.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerTableCtry.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerTable.setItems(allCustomers);
    }
    public void createButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Add Customer");
        stage.setScene(scene);
        stage.show();
    }

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
                    for (Appointment appointment: customerAppoinments) {
                        Integer appointmentID = appointment.getAppointmentID();
                        AppointmentCRUD.deleteAppointment(appointmentID);
                    }
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

    public void apptButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Appointments.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void reportsButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(MainApplication.class.getResource("Reports.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Appointment Scheduler - Reports");
        stage.setScene(scene);
        stage.show();
    }

    public void exitButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}
