package junitTestDao;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestPerson = new Person("GeraldR85", "myUsername", "Gerald",
                "Rust", "m", "father45", "mother72",
                "spouse123");

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        pDao = new PersonDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        pDao.clear();
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
        pDao.insert(bestPerson);
        // Let's use a find method to get the event that we just put in back out.
        Person compareTest = pDao.find(bestPerson.getPersonID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        // Start by inserting an event into the database.
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        // clear the database
        pDao.clear();
        // assert that null is returned when find is called
        Person notFoundTest = pDao.find(bestPerson.getPersonID());
        assertNull(notFoundTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        // first clear the database
        pDao.clear();
        // check to see if anything is still in the database
        Person notFoundTest = pDao.find(bestPerson.getPersonID());
        assertNull(notFoundTest);
    }

    @Test
    public void getPersonsForUserPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername", "John",
                "Rust", "m", "father45", "mother72",
                "spouse123");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myUsername", "Katy",
                "Rust", "f", "father46", "mother71",
                "spouse1233");
        pDao.insert(newPerson2);
        int size = pDao.getPersonsForUser(bestPerson.getAssociatedUsername()).size();
        assertEquals(3, size);
    }

    @Test
    public void getPersonsForUserFail() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername", "Gerald",
                "Rust", "m", "father45", "mother72",
                "spouse123");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "notMyUsername", "Gerald",
                "Rust", "m", "father45", "mother72",
                "spouse123");
        pDao.insert(newPerson2);
        int size = pDao.getPersonsForUser(bestPerson.getAssociatedUsername()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void getPersonsByGenderPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername1", "Bob",
                "Rust", "m", "father455", "mother772",
                "spouse1623");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myUsername2", "Gary",
                "Rust", "m", "father435", "mother752",
                "spouse1233");
        pDao.insert(newPerson2);
        int size = pDao.getPersonsByGender(bestPerson.getGender()).size();
        assertEquals(3, size);
    }

    @Test
    public void getPersonsByGenderFail() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUser", "Mike",
                "Rust", "m", "father45", "mother72",
                "spouse123");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myname", "Lucy",
                "Rusty", "f", "father4t5", "mother7r2",
                "spouse1e23");
        pDao.insert(newPerson2);
        int size = pDao.getPersonsByGender(bestPerson.getGender()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void getPersonsByLastNamePass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername3", "Mary",
                "Rust", "f", "father45", "mother72",
                "spouse123");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myUsername5", "Lisa",
                "Rust", "f", "father45", "mother72",
                "spouse123");
        pDao.insert(newPerson2);
        int size = pDao.getPersonsByLastName(bestPerson.getLastName()).size();
        assertEquals(3, size);
    }

    @Test
    public void getPersonsByLastNameFail() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername5", "Ron",
                "Rust", "m", "father435", "mother725",
                "spouse1236");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myUsername6", "Gerald",
                "Rolls", "m", "father435", "mother723",
                "spouse1223");
        pDao.insert(newPerson2);
        int size = pDao.getPersonsByLastName(bestPerson.getLastName()).size();
        assertNotEquals(3, size);
    }

    @Test
    public void deleteRowPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername", "Ron",
                "Rust", "m", "father435", "mother725",
                "spouse1236");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myUsername", "Gerald",
                "Rolls", "m", "father435", "mother723",
                "spouse1223");
        pDao.insert(newPerson2);
        pDao.deleteRow(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getPersonID()));
        assertNotNull(pDao.find(newPerson2.getPersonID()));
        assertEquals(2, pDao.getPersonsForUser(newPerson2.getAssociatedUsername()).size());
    }

    @Test
    public void deleteRowFail() throws DataAccessException {
        pDao.clear();
        pDao.deleteRow(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    public void deletePersonssByUsernamePass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person newPerson = new Person("GeraldR86", "myUsername", "Ron",
                "Rust", "m", "father435", "mother725",
                "spouse1236");
        pDao.insert(newPerson);
        Person newPerson2 = new Person("GeraldR87", "myUsername6", "Gerald",
                "Rolls", "m", "father435", "mother723",
                "spouse1223");
        pDao.insert(newPerson2);
        pDao.deletePersonsByUsername(bestPerson.getAssociatedUsername());
        assertNull(pDao.find(bestPerson.getAssociatedUsername()));
        assertNull(pDao.find(newPerson.getAssociatedUsername()));
        assertNotNull(pDao.find(newPerson2.getPersonID()));
        assertEquals(1, pDao.getPersonsForUser(newPerson2.getAssociatedUsername()).size());
    }

}
