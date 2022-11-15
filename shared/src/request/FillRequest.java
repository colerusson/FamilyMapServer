package request;

/**
 * fill request class, builds the object of this request
 */
public class FillRequest {

    /**
     * string of the username
     */
    private String username;

    /**
     * int of the number of generations to fill with data for the user's map
     */
    private int generations;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
