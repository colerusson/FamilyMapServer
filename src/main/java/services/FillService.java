package services;

import dao.*;
import model.Event;
import helpers.FamilyTree;
import model.Person;
import model.User;
import request.FillRequest;
import result.FillResult;

import java.io.File;
import java.sql.Connection;
import java.util.UUID;

/**
 * request service class for fill request, runs the functionality to actually perform the request
 */
public class FillService {
    private Database db;
    private PersonDao pDao;
    private EventDao eDao;
    private UserDao uDao;
    private User user;
    int eventsAdded;
    int peopleAdded;
    private FamilyTree familyTree;
    int DOB;

    /**
     * fill method to actually run the request and fill the map with data
     * @param fillRequest a request object sent in from the handler
     * @return a register result object obtained from the result package classes
     */
    public FillResult fill(FillRequest fillRequest) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();
            pDao = new PersonDao(conn);
            uDao = new UserDao(conn);
            eDao = new EventDao(conn);

            FillResult fillResult = new FillResult();
            int generations = fillRequest.getGenerations();
            String username = fillRequest.getUsername();

            user = uDao.find(username);

            if (generations < 1 || user == null) {
                db.closeConnection(true);
                fillResult = new FillResult();
                fillResult.setSuccess(false);
                fillResult.setMessage("Error: invalid request");
                return fillResult;
            }

            pDao.deletePersonsByUsername(username);
            eDao.deleteEventsByUsername(username);

            String filePathFemale = "json/fnames.json";
            File fileFemale = new File(filePathFemale);
            String filePathMale = "json/mnames.json";
            File fileMale = new File(filePathMale);
            String filePathSurnames = "json/snames.json";
            File fileSurnames = new File(filePathSurnames);
            String filePathLocations = "json/locations.json";
            File fileLocations = new File(filePathLocations);

            familyTree = new FamilyTree();
            familyTree.generateFemaleNames(fileFemale);
            familyTree.generateMaleNames(fileMale);
            familyTree.generateLastNames(fileSurnames);
            familyTree.generateLocations(fileLocations);

            // set the date of birth for the root user to 2000
            DOB = 2000;
            // call generate person function, which will call the recursive function
            generatePerson(user, generations, DOB);

            db.closeConnection(true);

            fillResult.setSuccess(true);
            if (eventsAdded > 0 && peopleAdded > 0) {
                fillResult.setMessage("Successfully added " + peopleAdded + " persons and " + eventsAdded + " events to the database.");
                fillResult.setSuccess(true);
            }
            else {
                fillResult.setSuccess(false);
                fillResult.setMessage("Error: Invalid data");
            }

            return fillResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            FillResult fillResult = new FillResult();
            fillResult.setSuccess(false);
            fillResult.setMessage("Error: error message");

            return fillResult;
        }
    }

    // This is the entry point function for the recursive family tree generation
    // the user is passed in, along with his/her DOB, and the number of generations we want
    private void generatePerson(User user, int generations, int DOB) throws  DataAccessException {
        Person mother;
        Person father;

        // the recursive part of the function is called, decrementing the DOB 22 years
        mother = generatePersonRecursion(user.getUsername(), "f", generations, DOB-22);
        father = generatePersonRecursion(user.getUsername(), "m", generations, DOB-22);

        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());

        // the info for the last person is generated here after the recursion
        Event marriage = familyTree.generateEvent(DOB-5, user.getUsername(), father.getPersonID(), "marriage");
        eDao.insert(marriage);
        eventsAdded++;
        marriage.setPersonID(mother.getPersonID());
        marriage.setEventID(UUID.randomUUID().toString());
        eDao.insert(marriage);
        eventsAdded++;

        Person person = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(),user.getLastName(), user.getGender(), father.getPersonID(), mother.getPersonID(), null);
        Event birth = familyTree.generateEvent(DOB, user.getUsername(), person.getPersonID(), "birth");
        eDao.insert(birth);
        eventsAdded++;
        Event death = familyTree.generateEvent(DOB, user.getUsername(), person.getPersonID(), "death");
        eDao.insert(death);
        eventsAdded++;

        pDao.insert(person);
        pDao.insert(mother);
        pDao.insert(father);
        peopleAdded+=3;
    }

    // this is the recursive helper function for generating the family tree
    private Person generatePersonRecursion(String username, String gender, int generations, int DOB) throws DataAccessException {
        Person mother = new Person();
        Person father = new Person();
        Person person = null;

        // if generations is more than one, then we step inside, call the function again recursively
        // we decrement the generations by 1 each time, so we basically work our way all the way back to the oldest generation
        if (generations > 1) {
            mother = generatePersonRecursion(username, "f", generations - 1, DOB-22);
            father = generatePersonRecursion(username, "m", generations - 1, DOB-22);
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            // marriage event is handled here so that the two married people have the same info linking them together
            Event marriage = familyTree.generateEvent(DOB-5, username, father.getPersonID(), "marriage");
            eDao.insert(marriage);
            marriage.setPersonID(mother.getPersonID());
            marriage.setEventID(UUID.randomUUID().toString());

            eDao.insert(marriage);
            pDao.insert(mother);
            pDao.insert(father);
            eventsAdded+=2;
            peopleAdded+=2;
        }


        // generate a person based on gender, and add needed events
        if (gender.equals("f")) {
            String firstName = familyTree.getName("f");
            String lastName = familyTree.getName("l");

            person = new Person(UUID.randomUUID().toString(), username, firstName, lastName, "f", father.getPersonID(), mother.getPersonID(), null);
            Event birth = familyTree.generateEvent(DOB, username, person.getPersonID(), "birth");
            eDao.insert(birth);

            Event death = familyTree.generateEvent(DOB, username, person.getPersonID(), "death");
            eDao.insert(death);
            eventsAdded+=2;
        }
        else if (gender.equals("m")) {
            String firstName = familyTree.getName("m");
            String lastName = familyTree.getName("l");

            person = new Person(UUID.randomUUID().toString(), username, firstName, lastName, "m", father.getPersonID(), mother.getPersonID(), null);
            Event birth = familyTree.generateEvent(DOB, username, person.getPersonID(), "birth");
            eDao.insert(birth);

            Event death = familyTree.generateEvent(DOB, username, person.getPersonID(), "death");
            eDao.insert(death);
            eventsAdded+=2;
        }

        return person;
    }
}
