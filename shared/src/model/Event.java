package model;

import java.util.Objects;

/**
 * Event method class the make a java object of the data in a row in the event table
 */
public class Event {

    /**
     * string of the eventID
     */
    private String eventID;

    /**
     * string of the associatedUsername for the user to the event
     */
    private String associatedUsername;

    /**
     * string of the personID of the event
     */
    private String personID;

    /**
     * Float of the latitude of the event, so decimals can be included
     */
    private Float latitude;

    /**
     * Float of the longitude of the event, so decimals can be included
     */
    private Float longitude;

    /**
     * string of the country name where the event is
     */
    private String country;

    /**
     * string of the city name where the event is
     */
    private String city;

    /**
     * string of the event type
     */
    private String eventType;

    /**
     * int of the year the event occurred
     */
    private Integer year;

    /**
     * public constructor, setting each member variable equal to these parameters
     */
    public Event(String eventID, String username, String personID, Float latitude, Float longitude,
                 String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public Event() {

    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
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

    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * overridden equals method to compare objects
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername)
                && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude)
                && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country)
                && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }

}
