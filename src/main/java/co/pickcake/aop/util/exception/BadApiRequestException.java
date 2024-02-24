package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

import static co.pickcake.aop.util.ErrorCode.METHOD_ARGUMENT_NOT_VALID;

public class BadApiRequestException extends RuntimeException {
    private final String ERR_CODE = HttpStatus.BAD_REQUEST.toString();

    public BadApiRequestException() {
        super(METHOD_ARGUMENT_NOT_VALID.toString());
    }
}
