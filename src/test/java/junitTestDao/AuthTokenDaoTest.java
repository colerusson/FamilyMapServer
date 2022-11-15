package junitTestDao;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {
    private Database db;
    private AuthToken bestAuthToken;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestAuthToken = new AuthToken("authToken_123A", "Gale");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        aDao = new AuthTokenDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        aDao.clear();
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
        aDao.insert(bestAuthToken);
        // Let's use a find method to get the event that we just put in back out.
        AuthToken compareTest = aDao.find(bestAuthToken.getAuthtoken());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        aDao.insert(bestAuthToken);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> aDao.insert(bestAuthToken));
    }

    @Test
    public void findPass() throws DataAccessException {
        // Start by inserting an event into the database.
        aDao.insert(bestAuthToken);
        AuthToken compareTest = aDao.find(bestAuthToken.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        // clear the database
        aDao.clear();
        // assert that null is returned when find is called
        AuthToken notFoundTest = aDao.find(bestAuthToken.getAuthtoken());
        assertNull(notFoundTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        // first clear the database
        aDao.clear();
        // check to see if anything is still in the database
        AuthToken notFoundTest = aDao.find(bestAuthToken.getAuthtoken());
        assertNull(notFoundTest);
    }

    @Test
    public void validatePass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        assertTrue(aDao.validate(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void validateFail() throws DataAccessException {
        aDao.clear();
        assertFalse(aDao.validate(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void getTokenByUsernamePass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        AuthToken compareTest = aDao.getTokenByUsername(bestAuthToken.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void getTokenByUsernameFail() throws DataAccessException {
        aDao.clear();
        // assert that null is returned when find is called
        AuthToken notFoundTest = aDao.getTokenByUsername(bestAuthToken.getUsername());
        assertNull(notFoundTest);
    }

    @Test
    public void deletRowPass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        aDao.deleteRow(bestAuthToken.getAuthtoken());
        assertNull(aDao.find(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void deleteRowFail() throws DataAccessException {
        aDao.clear();
        aDao.deleteRow(bestAuthToken.getAuthtoken());
        assertNull(aDao.find(bestAuthToken.getAuthtoken()));
    }
}
