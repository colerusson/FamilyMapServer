package services;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.AuthToken;
import model.Event;
import request.EventRequest;
import result.EventResult;

import java.sql.Connection;


/**
 * request service class for event request, runs the functionality to actually perform the request
 */
public class EventService {
    private Database db;
    private EventDao eDao;
    private AuthTokenDao aDao;
    private Event[] events;
    /**
     * event method to actually run the request sent int from the user to get all events
     * @param eventRequest a request object sent in form the handler
     * @return a register result object obtained from the result package classes
     */
    public EventResult eventService(EventRequest eventRequest) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();
            eDao = new EventDao(conn);
            aDao = new AuthTokenDao(conn);
            AuthToken authToken = aDao.find(eventRequest.getAuthToken());

            if (authToken != null) {
                String username = authToken.getUsername();
                events = eDao.getEventsForUser(username).toArray(new Event[0]);
            }
            db.closeConnection(true);

            EventResult eventResult = new EventResult();

            if (events != null) {
                eventResult.setData(events);
                eventResult.setSuccess(true);
            }
            else {
                eventResult.setMessage("Error: Events not found");
                eventResult.setSuccess(false);
            }

            return eventResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            EventResult eventResult = new EventResult();
            eventResult.setSuccess(false);
            eventResult.setMessage("Error: error message");
            return eventResult;
        }
    }
}
