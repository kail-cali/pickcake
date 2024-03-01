package co.pickcake.config;

import co.pickcake.mapapi.distance.DistanceAlgorithm;
import co.pickcake.mapapi.distance.HaversineDistance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapDistanceConfig {
    @Bean
    public DistanceAlgorithm distanceAlgorithm() {
        return new HaversineDistance();
    }
}
