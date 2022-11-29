pack org.team.exceptions;

public class studentAmkaExistsException extends Exception{

    public studentAmkaExistsException(String id) { super("Could not register with this id " + id); }

}
