package junitTestServer;

import dao.DataAccessException;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;
import services.ClearService;
import services.LoadService;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    private ClearService clearService;
    private LoadService loadService;
    private LoadRequest loadRequest;
    private LoadResult loadResult;
    private Event[] events;
    private User[] users;
    private Person[] persons;

    @BeforeEach
    public void setUp() {
        clearService = new ClearService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        clearService.clear();
    }

    @Test
    public void loadPass() throws DataAccessException {
        events = new Event[3];
        users = new User[2];
        persons = new Person[3];

        for (int i = 0; i < 3; ++i) {
            Event event = new Event();
            StringBuilder toAdd = new StringBuilder("event_id");
            for (int j = 0; j < i; ++j) {
                toAdd.append("*");
            }
            event.setEventType("event_type");
            event.setYear(2000);
            event.setAssociatedUsername("username");
            event.setLongitude(20.5F);
            event.setLatitude(80.5F);
            event.setCity("Seattle");
            event.setCountry("USA");
            event.setEventID(toAdd.toString());
            event.setPersonID("person_id");
            events[i] = event;
        }

        for (int i = 0; i < 2; ++i) {
            User user = new User();
            StringBuilder toAdd = new StringBuilder("username");
            StringBuilder toAddPerson = new StringBuilder("person_id");
            for (int j = 0; j < i; ++j) {
                toAdd.append("*");
                toAddPerson.append("*");
            }
            user.setEmail("email@email");
            user.setGender("m");
            user.setFirstName("firstName");
            user.setLastName("lastName");
            user.setPersonID(toAddPerson.toString());
            user.setUsername(toAdd.toString());
            user.setPassword("password");
            users[i] = user;
        }

        for (int i = 0; i < 3; ++i) {
            Person person = new Person();
            StringBuilder toAdd = new StringBuilder("person_id");
            for (int j = 0; j < i; ++j) {
                toAdd.append("*");
            }
            person.setPersonID(toAdd.toString());
            person.setGender("m");
            person.setFirstName("firstName");
            person.setLastName("lastName");
            person.setAssociatedUsername("username");
            person.setFatherID("father_id");
            person.setMotherID("mother_id");
            person.setSpouseID("spouse_id");
            persons[i] = person;
        }

        loadService = new LoadService();
        loadRequest = new LoadRequest();
        loadRequest.setEvents(events);
        loadRequest.setPersons(persons);
        loadRequest.setUsers(users);

        loadResult = loadService.load(loadRequest);

        assertNotNull(loadResult.getMessage());
        assertTrue(loadResult.isSuccess());
    }

    @Test
    public void loadFail() throws DataAccessException {
        loadService = new LoadService();
        loadRequest = new LoadRequest();
        loadResult = loadService.load(loadRequest);

        assertFalse(loadResult.isSuccess());
        assertNotNull(loadResult.getMessage());
    }
}
