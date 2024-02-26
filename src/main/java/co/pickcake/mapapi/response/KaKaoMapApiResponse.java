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
    private KaKaoMetaResponse metaResponse;
    @JsonProperty("documents")
    private List<KaKaoDocumentResponse> documentResponses;

    public static KaKaoMapApiResponse create(HttpStatus status, KaKaoMetaResponse meta, List<KaKaoDocumentResponse> documents) {
        KaKaoMapApiResponse response = new KaKaoMapApiResponse();
        response.metaResponse = meta;
        response.documentResponses = documents;
        response.status = status;
        return response;
    }
}
