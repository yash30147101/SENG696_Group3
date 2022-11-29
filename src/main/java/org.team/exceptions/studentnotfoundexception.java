pack org.team.exceptions;

public class studentNotFoundException extends RuntimeException {

    public studentNotFoundException(String id) {
        super("Could not find student with id " + id);
    }


}
