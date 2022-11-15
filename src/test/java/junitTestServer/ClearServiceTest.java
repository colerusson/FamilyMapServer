package junitTestServer;

import dao.*;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.ClearResult;
import services.ClearService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService clearService;
    private ClearResult clearResult;
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
    public void clearPassNoData() throws DataAccessException {
        clearResult = clearService.clear();
        assertTrue(clearResult.isSuccess());
        assertNotNull(clearResult.getMessage());
    }

    @Test
    public void clearPassWithData() throws DataAccessException {
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

        clearResult = clearService.clear();
        assertTrue(clearResult.isSuccess());
        assertNotNull(clearResult.getMessage());
    }

}
