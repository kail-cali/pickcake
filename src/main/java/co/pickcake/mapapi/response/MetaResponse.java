package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetaResponse {
    @JsonProperty("total_count")
    private Integer totalCount;
}
