package co.pickcake.aop.advice;

import co.pickcake.aop.errors.RestErrorDto;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public RestErrorDto illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new RestErrorDto("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(IllegalAccessException.class)
    public RestErrorDto illegalAccessHandler(IllegalAccessException e) {
        log.error("[exceptionHandler] ex", e);
        return new RestErrorDto("ACCESS_DNEIED", e.getMessage());
    }

    



    @ExceptionHandler
    public ResponseEntity<RestErrorDto> userExHandler(ExecutionControl.UserException e) {
        log.error("[exceptionHandler] ex", e);
        RestErrorDto errorDto = new RestErrorDto("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }


}
