package c195.task_1.java_c195.DAO;

import c195.task_1.java_c195.Model.Country;
import c195.task_1.java_c195.Model.FirstLevelDivision;
import c195.task_1.java_c195.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryCRUD {
    /**
     * @description get all countries from countries table
     * @return countryObservableList
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from countries";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(
                    id,
                    countryName
            );

            countryObservableList.add(country);
        }
        return countryObservableList;
    }

    /**
     * @description get all country ids from countries table
     * @return countryObservableList
     * @throws SQLException
     */
    public static ObservableList<Integer> getAllCountryIDs() throws SQLException {
        ObservableList<Integer> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID from countries";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            countryObservableList.add(id);
        }
        return countryObservableList;
    }

    /**
     * @description get all country names from countries table
     * @return countryObservableList
     * @throws SQLException
     */
    public static ObservableList<String> getAllCountryNames() throws SQLException {
        ObservableList<String> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Country from countries";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String countryName = rs.getString("Country");
            countryObservableList.add(countryName);
        }
        return countryObservableList;
    }

    /**
     * @description get country from countries table with matching country id
     * @param countryID
     * @return country
     * @throws SQLException
     */
    public static Country getCountryByID(int countryID) throws SQLException {
        String sql = "SELECT * from countries WHERE Country_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        Country country = null;

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            country = new Country(
                    id,
                    countryName
            );
        }
        return country;
    }

    /**
     * @description get country from countries table with matching country name
     * @param name
     * @return country
     * @throws SQLException
     */
    public static Country getCountryByName(String name) throws SQLException {
        String sql = "SELECT * from countries WHERE Country=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        Country country = null;

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            country = new Country(
                    id,
                    countryName
            );
        }
        return country;
    }
}
