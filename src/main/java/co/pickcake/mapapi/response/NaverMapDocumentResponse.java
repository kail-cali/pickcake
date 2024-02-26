package co.pickcake.mapapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NaverMapDocumentResponse {

    @JsonProperty("roadAddress")
    private String roadAddress;
    @JsonProperty("jibunAddress")
    private String jibunAddress;
    @JsonProperty("y")
    private String latitude;
    @JsonProperty("x")
    private String longitude;
    @JsonProperty("distance")
    private double distance;
    //    @JsonProperty("addressElements")
//    private List<NaverMapDto> elements;
    public static class NaverMapDto {
        @JsonProperty("types")
        private List<String> types;
        @JsonProperty("longName")
        private String longName;
    }
    public static NaverMapDocumentResponse create(String roadAddress, String jibunAddress,
                                                  String latitude, String longitude, Double distance) {
        NaverMapDocumentResponse document = new NaverMapDocumentResponse();
        document.roadAddress = roadAddress;
        document.jibunAddress = jibunAddress;
        document.latitude = latitude;
        document.longitude = longitude;
        document.distance = distance;
        return document;
    }
}
