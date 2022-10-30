package c195.task_1.java_c195.Model;

public class User {
    public int userID;
    public String username;
    public String password;

    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * get the user id
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * get the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get password
     * @return password
     */
    public String getPassword() {
        return password;
    }
}
