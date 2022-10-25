package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.User;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCRUD {
    public static ObservableList<Integer> getAllUserIDs() throws SQLException {
        ObservableList<Integer> userList = FXCollections.observableArrayList();
        String queryString = "SELECT User_ID from users";
        PreparedStatement preparedString = JDBC.openConnection().prepareStatement(queryString);
        ResultSet resultSet = preparedString.executeQuery();
        while (resultSet.next()) {
            int userID = resultSet.getInt("User_ID");
            userList.add(userID);
        }
        return userList;
    }

    public static User getUserByID(Integer id) throws SQLException {
        String sql = "SELECT * from users WHERE User_ID = '" + id + "'";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        User user = null;

        while (rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            user = new User(
                    userID,
                    userName,
                    ""
            );
        }
        return user;
    }
}
