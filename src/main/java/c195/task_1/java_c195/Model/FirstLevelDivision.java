package c195.task_1.java_c195.Model;

public class FirstLevelDivision {
    private int divisionID;
    private String division;
    public int countryID;

    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * get the division id
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * get the division
     * @return division
     */
    public String getDivisionName() {
        return division;
    }

    /**
     * get the country id
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }
}
