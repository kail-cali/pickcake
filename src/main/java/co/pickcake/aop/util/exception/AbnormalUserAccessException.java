package co.pickcake.aop.util.exception;

import org.springframework.http.HttpStatus;

/* 해당 에러가 발생할 수 있는 케이스에 대해 반드시 체크해야함 */
public class AbnormalUserAccessException extends RuntimeException {
    private final String ERR_CODE = HttpStatus.BAD_REQUEST.toString();

    public AbnormalUserAccessException() {
        super("잘못된 접근 입니다.");
    }
}
