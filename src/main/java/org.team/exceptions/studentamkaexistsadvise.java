pack org.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class studentAmkaExistsAdvise {

    @ResponseBody
    @ExceptionHandler(studentAmkaExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //409
    String studentAmkaExistsExceptionHandler(studentAmkaExistsException e) {
        return e.getMessage();
    }

}
