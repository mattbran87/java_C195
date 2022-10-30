package c195.task_1.java_c195.Model;

public class Customer {
    private Integer customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Integer divisionID;
    private String division;
    private String country;

    public Customer(Integer customerID, String name, String address, String postalCode, String phoneNumber, Integer divisionID) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
    }

    /**
     * get customer id
     * @return customerID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     get customer name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     get customer address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     get postal code
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     get the customer phone number
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     get the division string
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     the the country string
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     get the division id
     * @return divisionID
     */
    public Integer getDivisionID() {
        return divisionID;
    }

    /**
     set the customer id
     * @param customerID
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
     set the customer name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     set the address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     set the postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * set the phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * set the division id
     * @param divisionID
     */
    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * set the division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * set the country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
