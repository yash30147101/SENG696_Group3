pack org.team.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Could not find user");
    }
}
