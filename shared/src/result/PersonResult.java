package result;

import model.Person;

/**
 * person result class, builds the object of the result of this request
 */
public class PersonResult {

    /**
     * array of all the person objects for the user
     */
    private Person[] data;

    /**
     * string of the output message, success or failure
     */
    private String message;

    /**
     * boolean indicating the success, true for success, false for failure
     */
    private boolean success;

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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
