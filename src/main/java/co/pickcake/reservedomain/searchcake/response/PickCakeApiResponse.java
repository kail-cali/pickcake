package co.pickcake.reservedomain.searchcake.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class PickCakeApiResponse {
    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("meta")
    private PickCakeMetaResponse metaResponse;
    @JsonProperty("documents")
    private List<PickCakeDocumentResponse> documentResponses;
    public static PickCakeApiResponse create(HttpStatus status, PickCakeMetaResponse meta, List<PickCakeDocumentResponse> documents) {
        PickCakeApiResponse response = new PickCakeApiResponse();
        response.status= status;
        response.metaResponse = meta;
        response.documentResponses = documents;
        return response;
    }
}
