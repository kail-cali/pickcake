package co.pickcake.common.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Embeddable @Getter @Setter
@NoArgsConstructor
public class GeoCode {
    private Double latitude;
    private Double longitude;
    public static GeoCode create(Double latitude, Double longitude) {
        GeoCode geoCode = new GeoCode();
        geoCode.latitude = latitude;
        geoCode.longitude = longitude;
        return  geoCode;
    }
}
