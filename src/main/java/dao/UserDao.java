package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object class for User
 */
public class UserDao {

    /**
     * Connection object created to generate a connection to the database
     */
    private final Connection conn;

    /**
     * Constructor that stores the connection object in this dao class
     * @param conn the connection member variable
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * method to insert a user into the user data table
     * @param user user method object
     * @throws DataAccessException error in accessing the table
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO User (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * method to validate a user based on their username and password at login
     * @param username the username of the user logging in or making request
     * @param password the password of the user logging in or making request
     * @return boolean confirming the success status of user credentials
     * @throws DataAccessException error in accessing the table
     */
    public boolean validate(String username, String password) throws DataAccessException {
        if (find(username) != null) {
            if (find(username).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to return the user based on username
     * @param username the string ID of the desired user
     * @return a user method object
     * @throws DataAccessException error in accessing the table
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }


    /**
     * method to return a user based on first and last name of user
     * @param firstName the string of first name of the desired user
     * @param lastName the string of last name of the desired user
     * @return a user method object
     * @throws DataAccessException error in accessing the table
     */
    public User getUserByName(String firstName, String lastName) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE firstName = ? AND lastName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * method to return a user based on the personID of the user
     * @param personID the personID string of the user
     * @return a user method object
     * @throws DataAccessException error in accessing the table
     */
    public User getUserByPersonID(String personID) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * method to delete a row in the table
     * @throws DataAccessException error in accessing the table
     */
    public void deleteRow(String username) throws DataAccessException {
        String sql = "DELETE FROM user WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting rows from the user table");
        }
    }

    /**
     * method to delete the table
     * @throws DataAccessException error in accessing the table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM User";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

}
