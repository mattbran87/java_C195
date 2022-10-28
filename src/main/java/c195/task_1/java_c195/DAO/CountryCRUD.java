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

    public static ObservableList<Country> getCountryByID(int countryID) throws SQLException {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from countries WHERE Country_ID=?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
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
}
