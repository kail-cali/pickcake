package co.pickcake.authdomain.dto;

import co.pickcake.common.entity.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class SignupRequest {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;

    private String userName;

    private Address address;
}
