package junitTestServer;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import request.PersonRequest;
import result.PersonResult;
import services.ClearService;
import services.FillService;
import services.PersonService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private ClearService clearService;
    private Database db;
    private UserDao uDao;
    private FillService fillService;
    private FillRequest fillRequest;
    private PersonService personService;
    private PersonResult personResult;
    private PersonRequest personRequest;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() {
        clearService = new ClearService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        clearService.clear();
    }

    @Test
    public void personServicePass() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        aDao = new AuthTokenDao(conn);

        uDao.clear();
        aDao.clear();

        User user = new User();
        user.setEmail("email@email");
        user.setGender("m");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPersonID("person_id");
        user.setUsername("username");
        user.setPassword("password");
        uDao.insert(user);

        AuthToken authToken = new AuthToken("auth_token", "username");
        aDao.insert(authToken);

        db.closeConnection(true);

        fillService = new FillService();
        fillRequest = new FillRequest();
        fillRequest.setUsername("username");
        fillRequest.setGenerations(4);
        fillService.fill(fillRequest);

        personService = new PersonService();
        personRequest = new PersonRequest();
        personRequest.setAuthToken("auth_token");
        personResult = personService.personService(personRequest);

        assertNotNull(personResult.getData());
        assertEquals(31, personResult.getData().length);
        assertTrue(personResult.isSuccess());
        assertNull(personResult.getMessage());
    }

    @Test
    public void personServiceFail() throws DataAccessException {
        personService = new PersonService();
        personRequest = new PersonRequest();
        personRequest.setAuthToken("auth_token");
        personResult = personService.personService(personRequest);

        assertNull(personResult.getData());
        assertNotNull(personResult.getMessage());
        assertFalse(personResult.isSuccess());
    }
}
