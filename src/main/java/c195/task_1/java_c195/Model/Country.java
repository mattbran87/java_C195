package c195.task_1.java_c195.Model;

public class Country {
    private int countryID;
    private String country;

    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * get the country ID
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * get the country name
     * @return country
     */
    public String getCountryName() {
        return country;
    }
}
