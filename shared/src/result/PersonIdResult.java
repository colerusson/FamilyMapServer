package result;

/**
 * personID result class, builds the object of the result of this request
 */
public class PersonIdResult {

    /**
     * string of the username with the associated user for the person
     */
    private String associatedUsername;

    /**
     * string of the ID of the person
     */
    private String personID;

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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
