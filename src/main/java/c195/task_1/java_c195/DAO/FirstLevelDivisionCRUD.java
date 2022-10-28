package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.Appointment;
import c195.task_1.java_c195.Model.FirstLevelDivision;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionCRUD {

    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivision> firstLevelDomainObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(
                    id,
                    division,
                    countryID
            );

            firstLevelDomainObservableList.add(firstLevelDivision);
        }
        return firstLevelDomainObservableList;
    }

    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisionsByDivisionID(int divisionID) throws SQLException {
        ObservableList<FirstLevelDivision> firstLevelDomainObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(
                    id,
                    division,
                    countryID
            );

            firstLevelDomainObservableList.add(firstLevelDivision);
        }
        return firstLevelDomainObservableList;
    }

    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisionsByCountryID(int ctryID) throws SQLException {
        ObservableList<FirstLevelDivision> firstLevelDomainObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions WHERE Country_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, ctryID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivision firstLevelDivision = new FirstLevelDivision(
                    id,
                    division,
                    countryID
            );

            firstLevelDomainObservableList.add(firstLevelDivision);
        }
        return firstLevelDomainObservableList;
    }
}
