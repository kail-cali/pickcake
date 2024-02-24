package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class KaKaoMapApiResponse {
    @JsonProperty("meta")
    private MetaResponse metaResponse;
    @JsonProperty("documents")
    private List<DocumentResponse> documentResponses;
}
