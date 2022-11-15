package result;

/**
 * login result class, builds the object of the result of this request
 */
public class LoginResult {

    /**
     * string of the authtoken generated from the login/request
     */
    private String authtoken;

    /**
     * string of the username of the user logging in
     */
    private String username;

    /**
     * string of the ID of the user logging in
     */
    private String personID;

    /**
     * string of the output message, success or failure
     */
    private String message;

    /**
     * boolean indicating the success, true for success, false for failure
     */
    private boolean success;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
