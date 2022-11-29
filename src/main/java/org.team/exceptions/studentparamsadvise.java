pack org.team.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class studentParamsAdvise {

    @ResponseBody
    @ExceptionHandler(studentParamsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //409
    String studentParamsExceptionHandler(studentParamsException e) { return e.getMessage(); }
}
