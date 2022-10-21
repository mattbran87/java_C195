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

    public int getContactID() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
