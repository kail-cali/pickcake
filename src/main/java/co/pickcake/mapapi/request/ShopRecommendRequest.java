package co.pickcake.mapapi.request;

import co.pickcake.common.entity.Address;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ShopRecommendRequest {

    @NotEmpty
    ShopType type;
    @NotEmpty
    String keyword;
    @NotEmpty
    Address userCurrentaddress;

    public static ShopRecommendRequest create(ShopType type, String keyword, Address address ) {
        ShopRecommendRequest request = new ShopRecommendRequest();
        request.type = type;
        request.keyword = keyword;
        request.userCurrentaddress = address;
        return request;

    }
}
