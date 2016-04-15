package zelphinstudios.courseworkapp.system.networking.databases;

public class Account {

    // Variables
    private String username = "";
    private String password = "";

    // Constructor
    public Account(String username_, String password_) {
        username = username_;
        password = password_;
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsername(String username_) {
        username = username_;
    }
    public void setPassword(String password_) {
        password = password_;
    }
}
