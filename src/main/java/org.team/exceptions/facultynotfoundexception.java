pack org.team.exceptions;

public class facultyNotFoundException extends RuntimeException {

    public facultyNotFoundException(String id) {
        super("Could not find faculty with id " + id);
    }
}
