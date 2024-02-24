package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DocumentResponse {

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
}
