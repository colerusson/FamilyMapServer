package services;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * request service class for login request, runs the functionality to actually perform the request
 */
public class LoginService {
    private Database db;
    private AuthTokenDao aDao;
    private UserDao uDao;
    private User user;
    private AuthToken authToken;
    /**
     * login method to actually run the request sent int from the user to login
     * @param loginRequest a request object sent in form the handler
     * @return a register result object obtained from the result package classes
     */
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();

            aDao = new AuthTokenDao(conn);
            uDao = new UserDao(conn);

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            String authTokenString;
            String personID;

            LoginResult loginResult = new LoginResult();

            if (uDao.validate(username, password)) {
                user = uDao.find(username);
                personID = user.getPersonID();
                UUID uuid = UUID.randomUUID();
                authTokenString = uuid.toString().substring(0, 8);
                authToken = new AuthToken(authTokenString, username);
                if (aDao.validate(authTokenString)) {
                    aDao.deleteRow(authTokenString);
                }
                aDao.insert(authToken);
                loginResult.setAuthtoken(authTokenString);
                loginResult.setUsername(username);
                loginResult.setPersonID(personID);
                loginResult.setSuccess(true);
            }
            else {
                loginResult.setSuccess(false);
                loginResult.setMessage("Error: User not found");
            }

            db.closeConnection(true);

            return loginResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            LoginResult loginResult = new LoginResult();
            loginResult.setSuccess(false);
            loginResult.setMessage("Error, error message");
            return loginResult;
        }
    }
}
