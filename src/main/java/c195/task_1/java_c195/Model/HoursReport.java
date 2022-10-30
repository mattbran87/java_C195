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

    /**
     * get the contact id
     * @return
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * set the contact id
     * @param contactID
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    /**
     * get the contact name
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * set the contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * get the hours scheduled
     * @return
     */
    public float getHoursScheduled() {
        return hoursScheduled;
    }

    /**
     * set the hours scheduled
     * @param hoursScheduled
     */
    public void setHoursScheduled(float hoursScheduled) {
        this.hoursScheduled = hoursScheduled;
    }

    /**
     * get the hours worked
     * @return
     */
    public float getHoursWorked() {
        return hoursWorked;
    }

    /**
     * set the hours worked
     * @param hoursWorked
     */
    public void setHoursWorked(float hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
