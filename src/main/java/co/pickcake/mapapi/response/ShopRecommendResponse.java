package co.pickcake.mapapi.response;

import lombok.Data;

@Data
public class ShopRecommendResponse {

    String addressName;
    Double latitude;
    Double longitude;
    String shopName;
    Double distance;

    public ShopRecommendResponse(KaKaoDocumentResponse o) {
        addressName = o.getAddressName();
        latitude = o.getLatitude();
        longitude = o.getLongitude();
        shopName = o.getPlaceName();
        distance = o.getDistance(); // distance 주입해야 하는데;;;; 코드 쉽게 풀어가느라 좀 많이 애매한 상황
//        .map(request -> new ShopRecommendResponse(request.getAddressName(), request.getLatitude(), request.getLongitude(),
//                request.getPlaceName(),
//                distanceAlgorithm.calculateDistance(first.getLatitude(), first.getLongitude(), request.getLatitude(), request.getLongitude())))
    }


}
