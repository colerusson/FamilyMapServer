package request;

/**
 * login request class, builds the object of this request
 */
public class LoginRequest {

    /**
     * string of the username of the user logging in
     */
    private String username;

    /**
     * string of the password of the user logging in
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
