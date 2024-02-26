package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KaKaoDocumentResponse {

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("y")
    private double latitude;

    @JsonProperty("x")
    private double longitude;

    @JsonProperty("distance")
    private double distance;

    public static KaKaoDocumentResponse create(String  placeName, String addressName, Double latitude, Double longitude, Double distance) {
        KaKaoDocumentResponse response = new KaKaoDocumentResponse();
        response.placeName = placeName;
        response.addressName = addressName;
        response.latitude = latitude;
        response.longitude = longitude;
        response.distance = distance;
        return response;
    }
}
