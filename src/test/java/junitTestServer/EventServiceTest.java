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
import request.EventRequest;
import request.FillRequest;
import result.EventResult;
import services.ClearService;
import services.EventService;
import services.FillService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private ClearService clearService;
    private Database db;
    private UserDao uDao;
    private FillService fillService;
    private FillRequest fillRequest;
    private EventService eventService;
    private EventResult eventResult;
    private EventRequest eventRequest;
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
    public void eventServicePass() throws DataAccessException {
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

        eventService = new EventService();
        eventRequest = new EventRequest();
        eventRequest.setAuthToken("auth_token");
        eventResult = eventService.eventService(eventRequest);

        assertNotNull(eventResult.getData());
        assertEquals(92, eventResult.getData().length);
        assertTrue(eventResult.isSuccess());
        assertNull(eventResult.getMessage());
    }

    @Test
    public void eventServiceFail() throws DataAccessException {
        eventService = new EventService();
        eventRequest = new EventRequest();
        eventRequest.setAuthToken("auth_token");
        eventResult = eventService.eventService(eventRequest);

        assertNull(eventResult.getData());
        assertNotNull(eventResult.getMessage());
        assertFalse(eventResult.isSuccess());
    }
}
