package request;

/**
 * register request class, builds the object of this request
 */
public class RegisterRequest {

    /**
     * string of the username of the user registering
     */
    private String username;

    /**
     * string of the password of the user registering
     */
    private String password;

    /**
     * string of the email of the user registering
     */
    private String email;

    /**
     * string of the first name of the user registering
     */
    private String firstName;

    /**
     * string of the last name of the user registering
     */
    private String lastName;

    /**
     * string of the gender of the user registering, either 'f' or 'm'
     */
    private String gender;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
