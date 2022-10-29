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

    /**
     * @description set the appointment id
     * @param id
     */
    public void setAppointmentID(Integer id) {
        this.appointmentID = id;
    }

    /**
     * @description get the appointment id
     * @return id
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * @description set the title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
      @description get the title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
      @description set the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
      @description get the description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
      @description set the location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
      @description get the location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
      @description set the type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
      @description get the type
     * @return type
     */
    public String getType() {
        return  type;
    }

    /**
      @description set the start time
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
      @description get the start time
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
      @description set the end time
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
      @description get the end time
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
      @description set the creation date
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
      @description get the creation date
     * @return createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
      @description set the created by field
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
      @description get the created by field
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
      @description set the last update datetime
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
      @description get the last update datetime
     * @return lastUpdate
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
      @description set the last updated by field
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
      @description get the last updated by field
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
      @description set the customer id
     * @param customerID
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
      @description get the customer id
     * @return customerID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
      @description set the set user id
     * @param userID
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
      @description get the set user id
     * @return userID
     */
    public Integer getUserID() {
        return userID;
    }

    /**
      @description set the contact id
     * @param contactID
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    /**
      @description get the contact id
     * @return contactID
     */
    public Integer getContactID() {
        return contactID;
    }
}
