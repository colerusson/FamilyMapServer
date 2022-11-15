package services;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.AuthToken;
import model.Person;
import request.PersonIdRequest;
import result.PersonIdResult;

import java.sql.Connection;

/**
 * request service class for personID request, runs the functionality to actually perform the request
 */
public class PersonIdService {
    private Database db;
    private PersonDao pDao;
    private Person person;
    private AuthTokenDao aDao;
    private AuthToken authtoken;

    /**
     * personID method to actually run the request sent int from the user to get a certain person
     * @param personIdRequest a request object sent in form the handler
     * @return a register result object obtained from the result package classes
     */
    public PersonIdResult personIdService (PersonIdRequest personIdRequest, String authToken) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();
            pDao = new PersonDao(conn);
            person = pDao.find(personIdRequest.getPersonID());
            aDao = new AuthTokenDao(conn);

            String personUser = null;
            String authTokenUser = null;

            if (person != null) {
                personUser = person.getAssociatedUsername();
                authtoken = aDao.find(authToken);
                authTokenUser = authtoken.getUsername();
            }

            db.closeConnection(true);

            PersonIdResult personResult = new PersonIdResult();

            if (personUser != null && authTokenUser != null) {
                if (personUser.equals(authTokenUser)) {
                    personResult.setAssociatedUsername(person.getAssociatedUsername());
                    personResult.setPersonID(person.getPersonID());
                    personResult.setGender(person.getGender());
                    personResult.setFirstName(person.getFirstName());
                    personResult.setLastName(person.getLastName());
                    personResult.setFatherID(person.getFatherID());
                    personResult.setMotherID(person.getMotherID());
                    personResult.setSpouseID(person.getSpouseID());
                    personResult.setSuccess(true);
                }
            }

            if (personResult.getAssociatedUsername() == null) {
                personResult.setSuccess(false);
                personResult.setMessage("Error: Person not found");
            }

            return personResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            PersonIdResult personResult = new PersonIdResult();
            personResult.setSuccess(false);
            personResult.setMessage("Error: Error");
            return personResult;
        }
    }
}
