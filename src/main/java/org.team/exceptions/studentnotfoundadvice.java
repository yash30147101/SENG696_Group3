pack org.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class studentNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoLoggedInstudentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String noLoggedInUserHandler(NoLoggedInstudentException e) {
        return e.getMessage();
    }
}
