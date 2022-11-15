package services;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.AuthToken;
import model.Event;
import request.EventIdRequest;
import result.EventIdResult;

import java.sql.Connection;

/**
 * request service class for eventID request, runs the functionality to actually perform the request
 */
public class EventIdService {
    private Database db;
    private EventDao eDao;
    private Event event;
    private AuthTokenDao aDao;
    private AuthToken authtoken;

    /**
     * eventID method to actually run the request sent int from the user to get an event
     * @param eventIdRequest a request object sent in form the handler
     * @return a register result object obtained from the result package classes
     */
    public EventIdResult eventIdService(EventIdRequest eventIdRequest, String authToken) throws DataAccessException {
        db = new Database();
        try {
            Connection conn = db.getConnection();
            eDao = new EventDao(conn);
            event = eDao.find(eventIdRequest.getEventID());
            aDao = new AuthTokenDao(conn);

            String eventUser = null;
            String authTokenUser = null;

            if (event != null) {
                eventUser = event.getAssociatedUsername();
                authtoken = aDao.find(authToken);
                authTokenUser = authtoken.getUsername();
            }

            db.closeConnection(true);

            EventIdResult eventResult = new EventIdResult();

            if (eventUser != null && authTokenUser != null) {
                if (eventUser.equals(authTokenUser)) {
                    eventResult.setEventID(event.getEventID());
                    eventResult.setAssociatedUsername(event.getAssociatedUsername());
                    eventResult.setPersonID(event.getPersonID());
                    eventResult.setLatitude(event.getLatitude());
                    eventResult.setLongitude(event.getLongitude());
                    eventResult.setCountry(event.getCountry());
                    eventResult.setCity(event.getCity());
                    eventResult.setEventType(event.getEventType());
                    eventResult.setYear(event.getYear());
                    eventResult.setSuccess(true);
                }
            }
            if (eventResult.getAssociatedUsername() == null) {
                eventResult.setSuccess(false);
                eventResult.setMessage("Error: Event not found");
            }
            return eventResult;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            EventIdResult eventResult = new EventIdResult();
            eventResult.setSuccess(false);
            eventResult.setMessage("Error: Error");
            return eventResult;
        }
    }
}
