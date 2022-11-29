pack org.team.exceptions;

public class studentParamsException extends Exception{
    public studentParamsException() {
        super("Could not register. Multiple parameters are wrong.");
    }

}
