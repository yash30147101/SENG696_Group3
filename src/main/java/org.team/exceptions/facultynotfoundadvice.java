pack org.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class facultyNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoLoggedInstudentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String noLoggedInfacultyHandler(NoLoggedInstudentException e) {
        return e.getMessage();
    }
}
