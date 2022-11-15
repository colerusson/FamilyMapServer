package junitTestServer;

import dao.*;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventIdRequest;
import result.EventIdResult;
import services.ClearService;
import services.EventIdService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class EventIdServiceTest {
    private ClearService clearService;
    private EventIdService eventIdService;
    private EventIdResult eventIdResult;
    private EventIdRequest eventIdRequest;
    private EventDao eDao;
    private UserDao uDao;
    private AuthTokenDao aDao;
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
    public void eventIdServicePass() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();

        eDao = new EventDao(conn);
        uDao = new UserDao(conn);
        aDao = new AuthTokenDao(conn);

        eDao.clear();
        uDao.clear();
        aDao.clear();

        Event event = new Event();
        event.setEventType("event_type");
        event.setYear(2000);
        event.setAssociatedUsername("username");
        event.setLongitude(20.5F);
        event.setLatitude(80.5F);
        event.setCity("Seattle");
        event.setCountry("USA");
        event.setEventID("event_id");
        event.setPersonID("person_id");
        eDao.insert(event);

        User user = new User();
        user.setEmail("email@email");
        user.setGender("m");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPersonID("personUser_id");
        user.setUsername("username");
        user.setPassword("password");
        uDao.insert(user);

        AuthToken authToken = new AuthToken("auth_token", "username");
        aDao.insert(authToken);

        db.closeConnection(true);

        eventIdService = new EventIdService();
        eventIdRequest = new EventIdRequest();
        eventIdRequest.setEventID("event_id");
        eventIdResult = eventIdService.eventIdService(eventIdRequest, "auth_token");

        assertEquals("username", eventIdResult.getAssociatedUsername());
        assertEquals("event_id", eventIdResult.getEventID());
        assertEquals("event_type", eventIdResult.getEventType());
        assertEquals("Seattle", eventIdResult.getCity());
        assertEquals("USA", eventIdResult.getCountry());
        assertEquals("person_id", eventIdResult.getPersonID());
        assertEquals(20.5F, eventIdResult.getLongitude());
        assertEquals(80.5F, eventIdResult.getLatitude());
        assertEquals(2000, eventIdResult.getYear());
        assertTrue(eventIdResult.isSuccess());
    }

    @Test
    public void eventIdServiceFail() throws DataAccessException {
        eventIdService = new EventIdService();
        eventIdRequest = new EventIdRequest();
        eventIdRequest.setEventID("event_id");
        eventIdResult = eventIdService.eventIdService(eventIdRequest, "auth_token");

        assertFalse(eventIdResult.isSuccess());
        assertNotNull(eventIdResult.getMessage());
        assertNull(eventIdResult.getEventID());
        assertNull(eventIdResult.getYear());
        assertNull(eventIdResult.getPersonID());
        assertNull(eventIdResult.getLatitude());
        assertNull(eventIdResult.getLongitude());
        assertNull(eventIdResult.getAssociatedUsername());
        assertNull(eventIdResult.getEventType());
        assertNull(eventIdResult.getCountry());
        assertNull(eventIdResult.getCity());
    }
}
