package co.pickcake.admindomain.dto;

import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
public class CakeCreateResponse {

    private int status;
    /* NEEDTOFIX  error dto 필요 */
    private List<ObjectError> errors;

}
