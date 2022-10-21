package c195.task_1.java_c195.Model;

import java.sql.Timestamp;

public class Appointment {
    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    public Appointment(int appointmentID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return  type;
    }

    public Timestamp getStartDateTime() {
        return start;
    }

    public Timestamp getEndDateTime() {
        return end;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getCreateBy() {
        return createdBy;
    }

    public Timestamp getLastUpdateDateTime() {
        return lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getContactID() {
        return contactID;
    }
}
