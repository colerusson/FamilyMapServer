package junitTestServer;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.PersonIdRequest;
import result.PersonIdResult;
import services.ClearService;
import services.PersonIdService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonIdServiceTest {
    private ClearService clearService;
    private PersonIdService personIdService;
    private PersonIdResult personIdResult;
    private PersonIdRequest personIdRequest;
    private PersonDao pDao;
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
    public void personIdServicePass() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();

        pDao = new PersonDao(conn);
        uDao = new UserDao(conn);
        aDao = new AuthTokenDao(conn);

        pDao.clear();
        uDao.clear();
        aDao.clear();

        User user = new User();
        user.setEmail("email@email");
        user.setGender("m");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPersonID("personUser_id");
        user.setUsername("username");
        user.setPassword("password");
        uDao.insert(user);

        Person person = new Person();
        person.setPersonID("person_id");
        person.setGender("m");
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setAssociatedUsername("username");
        person.setFatherID("father_id");
        person.setMotherID("mother_id");
        person.setSpouseID("spouse_id");
        pDao.insert(person);

        AuthToken authToken = new AuthToken("auth_token", "username");
        aDao.insert(authToken);

        db.closeConnection(true);

        personIdService = new PersonIdService();
        personIdRequest = new PersonIdRequest();
        personIdRequest.setPersonID("person_id");
        personIdResult = personIdService.personIdService(personIdRequest, "auth_token");

        assertEquals("username", personIdResult.getAssociatedUsername());
        assertEquals("m", personIdResult.getGender());
        assertEquals("firstName", personIdResult.getFirstName());
        assertEquals("lastName", personIdResult.getLastName());
        assertEquals("father_id", personIdResult.getFatherID());
        assertEquals("mother_id", personIdResult.getMotherID());
        assertEquals("spouse_id", personIdResult.getSpouseID());
        assertEquals("person_id", personIdResult.getPersonID());
        assertTrue(personIdResult.isSuccess());
        assertNull(personIdResult.getMessage());
    }

    @Test
    public void personIdServiceFail() throws DataAccessException {
        personIdService = new PersonIdService();
        personIdRequest = new PersonIdRequest();
        personIdRequest.setPersonID("person_id");
        personIdResult = personIdService.personIdService(personIdRequest, "auth_token");

        assertFalse(personIdResult.isSuccess());
        assertNotNull(personIdResult.getMessage());
        assertNull(personIdResult.getAssociatedUsername());
        assertNull(personIdResult.getGender());
        assertNull(personIdResult.getFirstName());
        assertNull(personIdResult.getLastName());
        assertNull( personIdResult.getFatherID());
        assertNull(personIdResult.getMotherID());
        assertNull(personIdResult.getSpouseID());
        assertNull(personIdResult.getPersonID());
    }
}
