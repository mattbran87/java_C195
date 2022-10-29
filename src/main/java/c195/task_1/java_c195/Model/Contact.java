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
     * @description
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @description
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @description
     * @return email
     */
    public String getEmail() {
        return email;
    }
}
