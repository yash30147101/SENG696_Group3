pack org.team.exceptions;

public class NoLoggedInstudentException extends RuntimeException {
    public NoLoggedInstudentException() {
        super("There is no student logged in");
    }
}
