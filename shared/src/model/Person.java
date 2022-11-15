package model;


import java.util.Objects;

/**
 * Person method class the make a java object of the data in a row in the person table
 */
public class Person {

    /**
     * string of the person's ID
     */
    private String personID;

    /**
     * string of the associated user's username
     */
    private String associatedUsername;

    /**
     * string of the first name of the person
     */
    private String firstName;

    /**
     * string of the last name of the person
     */
    private String lastName;

    /**
     * string of the gender of the person, either 'f' or 'm'
     */
    private String gender;

    /**
     * string of the ID of the father, can be null
     */
    private String fatherID;

    /**
     * string of the ID of the mother, can be null
     */
    private String motherID;

    /**
     * string of the ID of the spouse, can be null
     */
    private String spouseID;

    /**
     * pulic constructor of the Person object, setting each member variable to these parameters
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
                  String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public Person() {
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) && Objects.equals(associatedUsername, person.associatedUsername)
                && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName)
                && Objects.equals(gender, person.gender) && Objects.equals(fatherID, person.fatherID)
                && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }
}
