package junitTestDao;

import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User bestUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        // and a new event with random data
        bestUser = new User("myUsername", "myPassword", "myname@email.com",
                "Bob", "Johnson", "m", "bobJ42");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        uDao = new UserDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        uDao.clear();
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
        uDao.insert(bestUser);
        // Let's use a find method to get the event that we just put in back out.
        User compareTest = uDao.find(bestUser.getUsername());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        uDao.insert(bestUser);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        // Start by inserting an event into the database.
        uDao.insert(bestUser);
        // Let's use a find method to get the event that we just put in back out.
        User compareTest = uDao.find(bestUser.getUsername());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        // clear the database
        uDao.clear();
        // assert that null was returned for finding
        User notFoundTest = uDao.find(bestUser.getUsername());
        assertNull(notFoundTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        // clear the database
        uDao.clear();
        // check to see if database is empty
        User notFoundTest = uDao.find(bestUser.getUsername());
        assertNull(notFoundTest);
    }

    @Test
    public void validatePass() throws DataAccessException {
        uDao.insert(bestUser);
        assertTrue(uDao.validate(bestUser.getUsername(), bestUser.getPassword()));
    }

    @Test
    public void validateFail() throws DataAccessException {
        uDao.clear();
        assertFalse(uDao.validate(bestUser.getUsername(), bestUser.getPassword()));
    }

    @Test
    public void getUserByNamePass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.getUserByName(bestUser.getFirstName(), bestUser.getLastName());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void getUserByNameFail() throws DataAccessException {
        uDao.clear();
        User nullTest = uDao.getUserByName(bestUser.getFirstName(), bestUser.getLastName());
        assertNull(nullTest);
    }

    @Test
    public void getUserByPersonIDPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.getUserByPersonID(bestUser.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void getUserByPersonIDFail() throws DataAccessException {
        uDao.clear();
        User nullTest = uDao.getUserByPersonID(bestUser.getPersonID());
        assertNull(nullTest);
    }

    @Test
    public void deleteRowPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.deleteRow(bestUser.getUsername());
        assertNull(uDao.find(bestUser.getUsername()));
    }

    @Test
    public void deleteRowFail() throws DataAccessException {
        uDao.clear();
        uDao.deleteRow(bestUser.getUsername());
        assertNull(uDao.find(bestUser.getUsername()));
    }

}
