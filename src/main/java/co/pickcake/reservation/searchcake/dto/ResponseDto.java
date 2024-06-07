package co.pickcake.reservation.searchcake.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<D> {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("data")
    private D data;
}
