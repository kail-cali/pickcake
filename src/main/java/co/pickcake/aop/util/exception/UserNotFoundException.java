package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

import static co.pickcake.aop.util.ErrorCode.NO_SUCH_USER_FOUND;

public class UserNotFoundException extends  RuntimeException {
    private final String ERR_CODE = HttpStatus.BAD_REQUEST.toString();

    public UserNotFoundException(){
        super(NO_SUCH_USER_FOUND.toString());
    }

}
