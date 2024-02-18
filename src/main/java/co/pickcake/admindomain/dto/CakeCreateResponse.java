package co.pickcake.admindomain.dto;

import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
public class CakeCreateResponse {

    private int status;
    private List<ObjectError> errors;

}
