package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverMetaResponse {
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("count")
    private Integer count;
    public static NaverMetaResponse create(Integer totalCount, Integer page, Integer count) {
        NaverMetaResponse meta = new NaverMetaResponse();
        meta.totalCount = totalCount;
        meta.page = page;
        meta.count = count;
        return meta;
    }
}
