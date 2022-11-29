pack org.team.exceptions;

public class studentEmailExistsException extends Exception {

    public studentEmailExistsException(String email) {
        super("Could not register with this email " + email);
    }

}
