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
    /**
     * @description get all first level divisions in first_level_divisions table
     * @return firstLevelDomainObservableList
     * @throws SQLException
     */
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

    /**
     * @description get the first level division in table that matches division id
     * @param divisionID
     * @return firstLevelDivision
     * @throws SQLException
     */
    public static FirstLevelDivision getFirstLevelDivisionByDivisionID(int divisionID) throws SQLException {
        String sql = "SELECT * from first_level_divisions WHERE Division_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        FirstLevelDivision firstLevelDivision = null;

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            firstLevelDivision = new FirstLevelDivision(
                    id,
                    division,
                    countryID
            );
        }
        return firstLevelDivision;
    }

    /**
     * @description get the first level division in table that matches division name
     * @param divisionName
     * @return firstLevelDivision
     * @throws SQLException
     */
    public static FirstLevelDivision getFirstLevelDivisionByDivisionName(String divisionName) throws SQLException {
        String sql = "SELECT * from first_level_divisions WHERE Division=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        FirstLevelDivision firstLevelDivision = null;

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            firstLevelDivision = new FirstLevelDivision(
                    id,
                    division,
                    countryID
            );
        }
        return firstLevelDivision;
    }

    /**
     * @description get all first level division names that match by country id
     * @param ctryID
     * @return firstLevelDomainObservableList
     * @throws SQLException
     */
    public static ObservableList<String> getAllFirstLevelDivisionNamesByCountryID(int ctryID) throws SQLException {
        ObservableList<String> firstLevelDomainObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions WHERE Country_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, ctryID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String division = rs.getString("Division");

            firstLevelDomainObservableList.add(division);
        }
        return firstLevelDomainObservableList;
    }
}
