package c195.task_1.java_c195.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    public Appointment(int id, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.appointmentID = id;
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

    public void setAppointmentID(Integer id) {
        this.appointmentID = id;
    }
    public Integer getAppointmentID() {
        return appointmentID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return  type;
    }
    public void setStartDateTime(LocalDateTime start) {
        this.start = start;
    }
    public LocalDateTime getStartDateTime() {
        return start;
    }
    public void setEndDateTime(LocalDateTime end) {
        this.end = end;
    }
    public LocalDateTime getEndDateTime() {
        return end;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreateBy() {
        return createdBy;
    }
    public void setLastUpdateDateTime(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdate;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
    public Integer getCustomerID() {
        return customerID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }
    public Integer getContactID() {
        return contactID;
    }
}
