package co.pickcake.mapapi.distance;

import org.springframework.stereotype.Component;

@Component
public interface DistanceAlgorithm {

    Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2);
}
