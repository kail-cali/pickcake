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

//    @JsonProperty("addressElements")
//    private List<NaverMapDto> elements;

    @JsonProperty("y")
    private String latitude;

    @JsonProperty("x")
    private String longitude;

    @JsonProperty("distance")
    private double distance;

    public static class NaverMapDto {

        @JsonProperty("types")
        private List<String> types;

        @JsonProperty("longName")
        private String longName;

    }
}
