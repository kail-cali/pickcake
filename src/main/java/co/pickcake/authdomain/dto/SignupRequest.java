package co.pickcake.authdomain.dto;

import co.pickcake.common.entity.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;


@Data
//@Builder
public class SignupRequest {

    @NotEmpty
    private String userId;
    private String email;
    @NotEmpty
    private String password;
    private String userName;
    private Address address;

    public SignupRequest() {

    }

    public static SignupRequest create(String userId, String email, String password, String userName, Address address) {
        SignupRequest request = new SignupRequest();
        request.userId = userId;
        request.email = email;
        request.password = password;
        request.userName = userName;
        request.address = address;
        return  request;
    }

    public static SignupRequest create(String userId, String email, String password, String userName) {
        SignupRequest request = new SignupRequest();
        request.userId = userId;
        request.email = email;
        request.password = password;
        request.userName = userName;
        return request;
    }
}
