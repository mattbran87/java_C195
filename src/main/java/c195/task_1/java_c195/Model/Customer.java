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

    public Customer(Integer customerID, String name, String address, String postalCode, String phoneNumber,
                    String division, Integer divisionID, String country) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.division = division;
        this.divisionID = divisionID;
        this.country = country;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDivision() {
        return division;
    }

    public String getCountry() {
        return country;
    }

    public Integer getDivisionID() {
        return divisionID;
    }
}
