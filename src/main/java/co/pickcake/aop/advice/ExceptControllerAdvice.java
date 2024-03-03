package co.pickcake.aop.advice;

import co.pickcake.aop.errors.RestErrorDto;
import co.pickcake.aop.util.ErrorCode;
import co.pickcake.aop.util.exception.AlreadyExistUserException;
import co.pickcake.aop.util.exception.NoDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ExceptControllerAdvice {

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<RestErrorDto> noDataExHandler(NoDataException e) {
        log.error("[exceptionHandler] ex", e);
        RestErrorDto errorDto = new RestErrorDto(ErrorCode.NO_DATA_EXISTS.toString(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.OK);
    }
    @ExceptionHandler(AlreadyExistUserException.class)
    public ResponseEntity<RestErrorDto> alreadyExistUserHandler(AlreadyExistUserException e) {
        log.info("[exceptionHandler] ex", e);
        RestErrorDto errorDto = new RestErrorDto(ErrorCode.DUPLICATED_USER_ALREADY_EXISTS.toString(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public RestErrorDto illegalExHandler(IllegalArgumentException e) {
        return new RestErrorDto("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorDto> invalidRequestHandler(MethodArgumentNotValidException e) {
        log.error("[exceptionHandler] ex", e);
        RestErrorDto errorDto = new RestErrorDto(ErrorCode.METHOD_ARGUMENT_NOT_VALID.toString(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(IllegalAccessException.class)
    public RestErrorDto illegalAccessHandler(IllegalAccessException e) {
        log.error("[exceptionHandler] ex", e);
        return new RestErrorDto("ACCESS_DENIED", e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InternalError.class)
    public RestErrorDto jsonProcessingFailed(JsonProcessingException e) {
        return new RestErrorDto(ErrorCode.BAD_REQUEST.toString(), e.getMessage());
    }

}
