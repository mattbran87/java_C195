package c195.task_1.java_c195;

import c195.task_1.java_c195.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    /**
     * start application and forward user to login view
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Scheduling Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * load database and any contextual data at start of application
     * @param args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        JDBC.closeConnection();
        launch();
    }
}