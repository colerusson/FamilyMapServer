package dao;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object class for AuthToken
 */
public class AuthTokenDao {

    /**
     * Connection object created to generate a connection to the database
     */
    private final Connection conn;

    /**
     * Constructor that stores the connection object in this dao class
     * @param conn the connection member variable
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * method to insert an authtoken into the table
     * @param authToken an authtoken method object
     * @throws DataAccessException error in accessing the table
     */
    public void insert(AuthToken authToken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthtoken());
            stmt.setString(2, authToken.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * @param authToken an authoken object
     * @return authtoken model object
     * @throws DataAccessException an error in accessing data
     */
    public AuthToken find(String authToken) throws DataAccessException {
        AuthToken authtoken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authtoken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }

    }

    /**
     * method to validate the user making the request
     * @param authToken a string that is the authToken of the user
     * @return a boolean confirming the validation status
     * @throws DataAccessException error in accessing the table
     */
    public boolean validate(String authToken) throws DataAccessException {
        if (find(authToken) != null) {
            return true;
        }
        return false;
    }

    /**
     * method to get the authtoken with the given user
     * @param username the string username of the user
     * @return the authToken data object
     * @throws DataAccessException error in accessing the table
     */
    public AuthToken getTokenByUsername(String username) throws DataAccessException {
        AuthToken authtoken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authtoken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }

    /**
     * method to delete a row in the table
     * @throws DataAccessException error in accessing the table
     */
    public void deleteRow(String authtoken) throws DataAccessException {
        String sql = "DELETE FROM Authtoken WHERE authtoken = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting rows from the authtoken table");
        }
    }

    /**
     * method to delete the table
     * @throws DataAccessException error in accessing the table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }
}
