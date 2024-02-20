package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

import static co.pickcake.aop.util.ErrorCode.NO_AUTHENTICATION_EXISTS;

public class AuthAccessDeniedException extends RuntimeException {
    private final String ERR_CODE = HttpStatus.FORBIDDEN.toString(); // 403
    public AuthAccessDeniedException() {
        super(NO_AUTHENTICATION_EXISTS.toString());
    }

}
