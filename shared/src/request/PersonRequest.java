package request;

/**
 * person request class, builds the object of this request
 */
public class PersonRequest {
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private String authToken;
}
