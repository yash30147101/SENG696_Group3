pack org.team.exceptions;

public class departmentNotFoundException extends RuntimeException {

    public departmentNotFoundException(Long id) {
        super("Could not find department with id " + id);
    }
}
