package co.pickcake.admindomain.dto;

import co.pickcake.reservedomain.searchcake.dto.CakeImageDetails;
import co.pickcake.reservedomain.searchcake.dto.CakeProfileImageDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CakeUpdateRequest {

    @NotBlank
    private String cakeName;

    @NotBlank
    private String brand;

    @NotNull
    @Range(min=1000, max = 1000000)
    private int price;

    private String description;

    private CakeProfileImageDto profile;

    private CakeImageDetails cakeImageDetails;
}
