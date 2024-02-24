package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetaResponse {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("count")
    private Integer count;
}
