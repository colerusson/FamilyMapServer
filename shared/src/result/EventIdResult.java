package result;

/**
 * eventID result class, builds the object of the result of this request
 */
public class EventIdResult {

    /**
     * string of the username of the associated user
     */
    private String associatedUsername;

    /**
     * string of the ID of the event
     */
    private String eventID;

    /**
     * string of the person's ID
     */
    private String personID;

    /**
     * Float of latitude of the event, so decimals can be included
     */
    private Float latitude;

    /**
     * Float of the longitude of the event, so decimals can be included
     */
    private Float longitude;

    /**
     * string of the country of the event
     */
    private String country;

    /**
     * string of the city of the event
     */
    private String city;

    /**
     * string of the event type
     */
    private String eventType;

    /**
     * int of the year of the event
     */
    private Integer year;

    /**
     * string of the output message, success or failure
     */
    private String message;

    /**
     * boolean indicating the success, true for success, false for failure
     */
    private boolean success;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
