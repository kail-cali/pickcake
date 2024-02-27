package co.pickcake.reservedomain.searchcake.response;

import co.pickcake.mapapi.response.KaKaoMetaResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PickCakeMetaResponse {
    @JsonProperty("total_count")
    private Integer totalCount;

    public static PickCakeMetaResponse create(Integer totalCount) {
        PickCakeMetaResponse meta = new PickCakeMetaResponse();
        meta.totalCount = totalCount;
        return meta;
    }
}
