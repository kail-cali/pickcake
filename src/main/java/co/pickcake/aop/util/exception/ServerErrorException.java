package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends RuntimeException {
    private final String ERR_CODE = HttpStatus.BAD_REQUEST.toString(); // 403

    public ServerErrorException( ) {
        super();
    }

}
