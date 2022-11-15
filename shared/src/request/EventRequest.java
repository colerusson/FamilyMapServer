package request;

/**
 * event request class, not currently necessary, builds the object of this request
 */
public class EventRequest {

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private String authToken;

}
