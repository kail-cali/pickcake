package co.pickcake.reservation.searchcake.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@RequiredArgsConstructor
public class CakeSimpleSearchRequest {


    private String name = "케이크";
    @PositiveOrZero
    private Integer offset= 0;
    @Range(min=1, max = 20)
    private Integer limit = 10;
    @NotBlank
    private String categoryName = "파티";


}
