package junitTestServer;

import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.LoginResult;
import services.ClearService;
import services.LoginService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private ClearService clearService;
    private LoginService loginService;
    private LoginRequest loginRequest;
    private LoginResult loginResult;
    private UserDao uDao;
    private Database db;

    @BeforeEach
    public void setUp() {
        clearService = new ClearService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        clearService.clear();
    }

    @Test
    public void loginPass() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);

        User user = new User();
        user.setEmail("email@email");
        user.setGender("m");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPersonID("person_id");
        user.setUsername("username");
        user.setPassword("password");
        uDao.insert(user);

        db.closeConnection(true);

        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        loginService = new LoginService();
        loginResult = loginService.login(loginRequest);

        assertTrue(loginResult.isSuccess());
        assertNotNull(loginResult.getAuthtoken());
        assertEquals("person_id", loginResult.getPersonID());
        assertEquals("username", loginResult.getUsername());
        assertNull(loginResult.getMessage());
    }

    @Test
    public void loginFail() throws DataAccessException {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        loginService = new LoginService();
        loginResult = loginService.login(loginRequest);

        assertFalse(loginResult.isSuccess());
        assertNotNull(loginResult.getMessage());
        assertNull(loginResult.getPersonID());
        assertNull(loginResult.getUsername());
        assertNull(loginResult.getAuthtoken());
    }
}
