package model;

import java.util.Objects;

/**
 * Authtoken method class the make a java object of the data in a row in the authtoken table
 */
public class AuthToken {

    /**
     * the string of the authoken
     */
    private String authtoken;

    /**
     * the string of the username
     */
    private String username;

    /**
     * public constructor of an AuthToken object
     * @param authtoken authtoken member variable set to this
     * @param username username member variable set to this
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken token = (AuthToken) o;
        return Objects.equals(authtoken, token.authtoken) && Objects.equals(username, token.username);
    }
}
