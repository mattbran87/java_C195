package c195.task_1.java_c195;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import c195.task_1.java_c195.DAO.userLogin;
import c195.task_1.java_c195.Controller.Appointments;
import c195.task_1.java_c195.DAO.AppointmentCRUD;
import c195.task_1.java_c195.Model.Appointment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    public Label mainLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label locationLabel;
    @FXML
    public Label locationValue;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Button loginButton;
    @FXML
    public Button exitButton;

    /**
     * When page is initially loaded translate input labels. Defaults to US English. Function uses lambda
     * expression to assign the System.exit() function to the exit button.
     * @param url URL of the current page
     * @param rb gets the language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // second lambda expression.
        exitButton.setOnAction( mouseEvent -> System.exit(0));
        try {
            // get location
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            //Locale.setDefault(new Locale("fr", "FR"));

            // get resource bundle and add stored language values
            rb = ResourceBundle.getBundle("language_files/login", Locale.getDefault());
            mainLabel.setText(rb.getString("mainLabel"));
            usernameLabel.setText(rb.getString("usernameLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            locationLabel.setText(rb.getString("locationLabel"));
            locationValue.setText(locale.getCountry());
            loginButton.setText(rb.getString("loginButton"));
            exitButton.setText(rb.getString("exitButton"));

        } catch(MissingResourceException e) {
            System.out.println("Language files missing: " + e);
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * When login button is clicked the user is logged into the system unless incorrect username and password is given
     * @param mouseEvent click on the login button
     * @throws IOException
     * @throws SQLException
     */
    public void loginButtonClicked(ActionEvent mouseEvent) throws IOException, SQLException {
        // get form values
        String usernameInput = username.getText();
        String passwordInput = password.getText();
        int userId = userLogin.userValidation(usernameInput, passwordInput);

        ResourceBundle rb = ResourceBundle.getBundle("language_files/login", Locale.getDefault());

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        if (userId == 0) {
            // log error
            outputFile.print(usernameInput + " had a failed login attempt on " + LocalDateTime.now() + "\n");
            // generate alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("loginFailedTitle"));
            alert.setContentText(rb.getString("loginFailed"));
            alert.show();
        } else if (userId > 0) {
            ObservableList<Appointment> getAllAppointments = AppointmentCRUD.getAllAppointments();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime nowPlus15 = LocalDateTime.now().plusMinutes(15);
            LocalDateTime nowMinus15 = LocalDateTime.now().minusMinutes(15);
            LocalDateTime startTime;
            String getAppointmentTitle = "";
            LocalDateTime upcomingAppointmentTime = null;
            boolean hasAppointments = false;

            outputFile.print(usernameInput + " logged in on " + LocalDateTime.now() + "\n");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Appointments.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Appointment Scheduler - Appointments");
            stage.setScene(scene);
            stage.show();

            //check for upcoming appointments if user is validated
            for (Appointment appointment : getAllAppointments) {
                startTime = appointment.getStart();

                // check if appointment is scheduled within 15 minutes before or after login time
                if (startTime.isAfter(nowMinus15) && startTime.isBefore(nowPlus15)) {
                    getAppointmentTitle = appointment.getTitle();
                    upcomingAppointmentTime = startTime;
                    hasAppointments = true;
                }
            }
            if (hasAppointments) {
                String displayTime = upcomingAppointmentTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment " + getAppointmentTitle + " is starting at" + displayTime + ".");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("15 Minute Reminder");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are no upcoming appointments.");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("There are no upcoming appointments");
            }
        } else {
            outputFile.print(usernameInput + " an unknown error on " + LocalDateTime.now() + "\n");
            // generate alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("loginFailedTitle"));
            alert.setContentText(rb.getString("loginFailedUnknown"));
            alert.show();
        }
        outputFile.close();

    }
}