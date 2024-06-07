package co.pickcake.shop.dto;

import co.pickcake.common.entity.Address;
import co.pickcake.reservation.searchcake.dto.ReserveInfoItem;
import co.pickcake.shop.domain.Shop;
import co.pickcake.sns.dto.SnsItem;
import lombok.Data;

@Data
public class ShopSearch {

    private Long shopId;
    private Address address;
    private Double latitude;
    private Double longitude;

    private String phone;
    private String siteUrl;

    private SnsItem naver;
    private SnsItem instargram;

    private ReserveInfoItem reserveInfoItem;


    public ShopSearch(Shop s) {
        shopId= s.getId();
        address = s.getAddress();
        phone = s.getPhoneNumber();
        siteUrl = s.getSiteUrl();

        if (s.getNaver() != null) {
            naver = new SnsItem(s.getNaver());
        }
        if (s.getInstagram() != null) {
            instargram = new SnsItem(s.getInstagram());
        }
        if (s.getReserveInfo() != null) {
            reserveInfoItem = new ReserveInfoItem(s.getReserveInfo());
        }
        if (s.getGeoCode() != null) {
            latitude = s.getGeoCode().getLatitude();
            longitude = s.getGeoCode().getLongitude();
        }
    }
}
