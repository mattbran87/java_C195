package c195.task_1.java_c195.Model;

public class Contact {
    public int contactID;
    public String name;
    public String email;

    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**
     * get the contact id
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * get the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the email
     * @return email
     */
    public String getEmail() {
        return email;
    }
}
