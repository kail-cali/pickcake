package co.pickcake.mapapi.distance;


public class HaversineDistance implements DistanceAlgorithm {

    @Override
    public Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        Double earthRadius = 6371D; //Kilometers
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
