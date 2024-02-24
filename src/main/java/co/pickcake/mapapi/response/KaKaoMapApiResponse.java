package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class KaKaoMapApiResponse {
    @JsonProperty("status")
    private HttpStatus status;
    @JsonProperty("meta")
    private MetaResponse metaResponse;
    @JsonProperty("documents")
    private List<DocumentResponse> documentResponses;
}
