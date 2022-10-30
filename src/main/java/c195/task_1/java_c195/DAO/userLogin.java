package c195.task_1.java_c195.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import c195.task_1.java_c195.helper.JDBC;
import c195.task_1.java_c195.Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class userLogin extends User {
    /**
     * @description create user login constructor
     * @param userID
     * @param userName
     * @param userPassword
     */
    public userLogin(int userID, String userName, String userPassword) {
        super(userID, userName, userPassword);
    }

    /**
     * @description validate username and password
     * @param username
     * @param password
     * @return userID or 0 if failed
     */
    public static int userValidation(String username, String password) {
        try {
            String queryString = "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password +"'";

            PreparedStatement preparedString = JDBC.openConnection().prepareStatement(queryString);
            ResultSet resultSet = preparedString.executeQuery();
            resultSet.next();
            if (resultSet.getString("User_Name").equals(username)) {
                if (resultSet.getString("Password").equals(password)) {
                    return resultSet.getInt("User_ID");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
