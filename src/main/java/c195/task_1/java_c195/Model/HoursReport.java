package c195.task_1.java_c195.Model;

public class HoursReport {
    private Integer contactID;
    private String contactName;
    private Integer hoursScheduled;
    private Integer hoursWorked;

    public HoursReport(Integer contactID, String contactName, Integer hoursScheduled, Integer hoursWorked) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.hoursScheduled = hoursScheduled;
        this.hoursWorked = hoursWorked;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getHoursScheduled() {
        return hoursScheduled;
    }

    public void setHoursScheduled(Integer hoursScheduled) {
        this.hoursScheduled = hoursScheduled;
    }

    public Integer getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Integer hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
