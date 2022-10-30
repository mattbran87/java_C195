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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberOfAppointments() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(Integer numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}
