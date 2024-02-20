package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

import static co.pickcake.aop.util.ErrorCode.NO_DATA_EXISTS;

public class NoDataException extends RuntimeException {
    private final String ERR_CODE = HttpStatus.BAD_REQUEST.toString(); // 403
    public NoDataException() {
        super(NO_DATA_EXISTS.toString());
    }
}
