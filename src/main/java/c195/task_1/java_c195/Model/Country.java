package c195.task_1.java_c195.Model;

public class Country {
    private int countryID;
    private String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return country;
    }
}
