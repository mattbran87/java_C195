package c195.task_1.java_c195.Model;

public class MonthTypeReport {
    private String year;
    private String month;
    private String type;
    private Integer numberOfAppointments;

    public MonthTypeReport(String year, String month, String type, Integer numberOfAppointments) {
        this.year = year;
        this.month = month;
        this.type = type;
        this.numberOfAppointments = numberOfAppointments;
    }

    /**
     * get the year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * set the year
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * get the month
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     * set the month
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * get the type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * set the type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get the number of appointments
     * @return
     */
    public Integer getNumberOfAppointments() {
        return numberOfAppointments;
    }

    /**
     * set the number of appointments
     * @param numberOfAppointments
     */
    public void setNumberOfAppointments(Integer numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
