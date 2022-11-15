package junitTestDao;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDaoTest {
    private Database db;
    private Event bestEvent;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDao.insert(bestEvent);
        // Let's use a find method to get the event that we just put in back out.
        Event compareTest = eDao.find(bestEvent.getEventID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        eDao.insert(bestEvent);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> eDao.insert(bestEvent));
    }

    @Test
    public void findPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        // clear the database
        eDao.clear();
        // assert that null is returned when find is called
        Event notFoundTest = eDao.find(bestEvent.getEventID());
        assertNull(notFoundTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        // first clear the database
        eDao.clear();
        // check to see if anything is still in the database
        Event notFoundTest = eDao.find(bestEvent.getEventID());
        assertNull(notFoundTest);
    }

    @Test
    public void getEventsForUserPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Eating_123A", "Gale", "Ron123A",
                38.9f, 145.1f, "England", "London",
                "Eating_Around", 2015);
        eDao.insert(newEvent2);
        int size = eDao.getEventsForUser(bestEvent.getAssociatedUsername()).size();
        assertEquals(3, size);
    }

    @Test
    public void getEventsForUserFail() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Eating_123A", "Dale", "Ron123A",
                38.9f, 145.1f, "England", "London",
                "Eating_Around", 2015);
        eDao.insert(newEvent2);
        int size = eDao.getEventsForUser(bestEvent.getAssociatedUsername()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void getEventsByTypePass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Biking_123B", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Biking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Biking_123C", "Gale", "Rod123A",
                37.9f, 145.1f, "England", "London",
                "Biking_Around", 2012);
        eDao.insert(newEvent2);
        int size = eDao.getEventsByType(bestEvent.getEventType()).size();
        assertEquals(3, size);
    }

    @Test
    public void getEventsByTypeFail() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Biking_123B", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Biking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Biking_123C", "Gale", "Rod123A",
                37.9f, 145.1f, "England", "London",
                "Hiking_Around", 2012);
        eDao.insert(newEvent2);
        int size = eDao.getEventsByType(bestEvent.getEventType()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void getEventsByYearPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2016);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Walking_123B", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2016);
        eDao.insert(newEvent2);
        int size = eDao.getEventsByYear(bestEvent.getYear()).size();
        assertEquals(3, size);
    }

    @Test
    public void getEventsByYearFail() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Walking_123B", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2016);
        eDao.insert(newEvent2);
        int size = eDao.getEventsByYear(bestEvent.getYear()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void getEventsByCountryPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "Japan", "Tokyo",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Walking_123B", "Gale", "Tod123A",
                36.9f, 141.1f, "Japan", "Tokyo",
                "Walking_Around", 2016);
        eDao.insert(newEvent2);
        int size = eDao.getEventsByCountry(bestEvent.getCountry()).size();
        assertEquals(3, size);
    }

    @Test
    public void getEventsByCountryFail() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Walking_123B", "Gale", "Tod123A",
                36.9f, 141.1f, "Japan", "Tokyo",
                "Walking_Around", 2016);
        eDao.insert(newEvent2);
        int size = eDao.getEventsByCountry(bestEvent.getCountry()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void deleteRowPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Eating_123A", "Gale", "Ron123A",
                38.9f, 145.1f, "England", "London",
                "Eating_Around", 2015);
        eDao.insert(newEvent2);
        eDao.deleteRow(bestEvent.getEventID());
        assertNull(eDao.find(bestEvent.getEventID()));
        assertNotNull(eDao.find(newEvent2.getEventID()));
        assertEquals(2, eDao.getEventsForUser(newEvent.getAssociatedUsername()).size());
    }

    @Test
    public void deleteRowFail() throws DataAccessException {
        eDao.clear();
        eDao.deleteRow(bestEvent.getEventID());
        assertNull(eDao.find(bestEvent.getEventID()));
    }

    @Test
    public void deleteEventsByUsernamePass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event newEvent = new Event("Walking_123A", "Gale", "Tod123A",
                36.9f, 141.1f, "USA", "Seattle",
                "Walking_Around", 2015);
        eDao.insert(newEvent);
        Event newEvent2 = new Event("Eating_123A", "John", "Ron123A",
                38.9f, 145.1f, "England", "London",
                "Eating_Around", 2015);
        eDao.insert(newEvent2);
        eDao.deleteEventsByUsername(bestEvent.getAssociatedUsername());
        assertNull(eDao.find(bestEvent.getAssociatedUsername()));
        assertNull(eDao.find(newEvent.getAssociatedUsername()));
        assertNotNull(eDao.find(newEvent2.getEventID()));
        assertEquals(1, eDao.getEventsForUser(newEvent2.getAssociatedUsername()).size());
    }

}
