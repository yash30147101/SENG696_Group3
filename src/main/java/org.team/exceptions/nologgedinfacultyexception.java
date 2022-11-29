pack org.team.exceptions;

public class NoLoggedInfacultyException extends RuntimeException {
    public NoLoggedInfacultyException() {
        super("There is no faculty logged in");
    }
}
