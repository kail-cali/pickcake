package co.pickcake.aop.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestErrorDto {
    private String code;
    private String message;
}
