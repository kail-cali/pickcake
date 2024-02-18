package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

import static co.pickcake.aop.util.ErrorCode.DUPLICATED_USER_ALREADY_EXISTS;

public class AlreadyExistUserException extends RuntimeException{

    private final String ERR_CODE = HttpStatus.BAD_REQUEST.toString();
    public AlreadyExistUserException(){
        super(DUPLICATED_USER_ALREADY_EXISTS.toString());
    }

}
