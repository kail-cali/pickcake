package co.pickcake.authdomain.dto;

import co.pickcake.common.entity.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberUpdateRequest {

    @NotEmpty
    private String email;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String userName;

    private Address address;
    public static MemberUpdateRequest create(String email, String newPassword, String userName, Address address) {
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.email = email;
        request.newPassword = newPassword;
        request.userName = userName;
        request.address = address;
        return  request;
    }
    public static MemberUpdateRequest create(String email, String newPassword, String userName) {
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.email = email;
        request.newPassword = newPassword;
        request.userName = userName;
        return request;
    }
}
