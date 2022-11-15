package request;

import model.Event;
import model.Person;
import model.User;

/**
 * load request class, builds the object of this request
 */
public class LoadRequest {

    /**
     * an array of all user objects in data
     */
    private User[] users;

    /**
     * an array of all the person objects in data
     */
    private Person[] persons;

    /**
     * an array of all event objects in data
     */
    private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
