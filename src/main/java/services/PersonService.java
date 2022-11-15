package services;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.AuthToken;
import model.Person;
import request.PersonRequest;
import result.PersonResult;

import java.sql.Connection;

/**
 * request service class for person request, runs the functionality to actually perform the request
 */
public class PersonService {
    private Database db;
    private PersonDao pDao;
    private AuthTokenDao aDao;
    private Person[] persons;
    /**
     * person method to actually run the request sent int from the user to return all persons
     * @param personRequest a request object sent in form the handler
     * @return a register result object obtained from the result package classes
     */
    public PersonResult personService (PersonRequest personRequest) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();
            pDao = new PersonDao(conn);
            aDao = new AuthTokenDao(conn);
            AuthToken authToken = aDao.find(personRequest.getAuthToken());

            if (authToken != null) {
                String username = authToken.getUsername();
                persons = pDao.getPersonsForUser(username).toArray(new Person[0]);
            }

            db.closeConnection(true);

            PersonResult personResult = new PersonResult();

            if (persons != null) {
                personResult.setData(persons);
                personResult.setSuccess(true);
            }
            else {
                personResult.setSuccess(false);
                personResult.setMessage("Error: Persons not found");
            }

            return personResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            PersonResult personResult = new PersonResult();
            personResult.setSuccess(false);
            personResult.setMessage("Error: TODO: figure out error message");
            return  personResult;
        }
    }
}
