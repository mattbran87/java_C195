package c195.task_1.java_c195.Model;

public class HoursReport {
    private Integer contactID;
    private String contactName;
    private float hoursScheduled;
    private float hoursWorked;

    public HoursReport(Integer contactID, String contactName, float hoursScheduled, float hoursWorked) {
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

    public float getHoursScheduled() {
        return hoursScheduled;
    }

    public void setHoursScheduled(float hoursScheduled) {
        this.hoursScheduled = hoursScheduled;
    }

    public float getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(float hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
