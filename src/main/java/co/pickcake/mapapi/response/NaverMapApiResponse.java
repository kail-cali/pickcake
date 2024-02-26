package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

@Data
public class NaverMapApiResponse {

    @JsonProperty("status")
    private HttpStatus status;
    @JsonProperty("meta")
    private NaverMetaResponse metaResponse;
    @JsonProperty("addresses")
    private List<NaverMapDocumentResponse> documentResponses;

    public static NaverMapApiResponse create(HttpStatus status, NaverMetaResponse meta, List<NaverMapDocumentResponse> documents) {
        NaverMapApiResponse response = new NaverMapApiResponse();
        response.status= status;
        response.metaResponse = meta;
        response.documentResponses = documents;
        return  response;
    }

}
