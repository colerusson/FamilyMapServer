package services;

import dao.*;
import request.LoadRequest;
import result.LoadResult;

import java.sql.Connection;

/**
 * request service class for load request, runs the functionality to actually perform the request
 */
public class LoadService {
    private Database db;
    private EventDao eDao;
    private UserDao uDao;
    private PersonDao pDao;
    /**
     * load method to actually run the request sent int from the user to clear and load data into the database
     * @param loadRequest a request object sent in form the handler
     * @return a register result object obtained from the result package classes
     */
    public LoadResult load(LoadRequest loadRequest) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();

            eDao = new EventDao(conn);
            pDao = new PersonDao(conn);
            uDao = new UserDao(conn);

            eDao.clear();
            pDao.clear();
            uDao.clear();

            int eventsLength = loadRequest.getEvents().length;
            int personsLength = loadRequest.getPersons().length;
            int usersLength = loadRequest.getUsers().length;

            for (int i = 0; i < eventsLength; ++i) {
                eDao.insert(loadRequest.getEvents()[i]);
            }

            for (int i = 0; i < personsLength; ++i) {
                pDao.insert(loadRequest.getPersons()[i]);
            }

            for (int i = 0; i < usersLength; ++i) {
                uDao.insert(loadRequest.getUsers()[i]);
            }

            db.closeConnection(true);

            LoadResult loadResult = new LoadResult();
            if (eventsLength > 0 && personsLength > 0 && usersLength > 0) {
                loadResult.setMessage("Successfully added " + usersLength + " users, " + personsLength + " persons, and "
                        + eventsLength + " events to the database.");
                loadResult.setSuccess(true);
            }
            else {
                loadResult.setSuccess(false);
                loadResult.setMessage("Error: Missing needed data");
            }

            return loadResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            LoadResult loadResult = new LoadResult();
            loadResult.setSuccess(false);
            loadResult.setMessage("Error: error message");
            return loadResult;
        }
    }
}
