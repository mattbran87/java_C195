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

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivisionName() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }
}
