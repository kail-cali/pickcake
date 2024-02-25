package co.pickcake.shopdomain.entity;

import co.pickcake.aop.datetime.AuditOnTime;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.common.entity.Address;
import co.pickcake.reservedomain.entity.ReserveInfo;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.sns.entity.Instagram;
import co.pickcake.sns.entity.Naver;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends AuditOnTime {

    @Id @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    private String shopName;
    /* 역할과 권한 분리 때문에 추가하였으나 괸리 측면에서 고민이 필요 , 특히 Cascade 적용하지 않을 수 있기 때문에 양방향 연관관계가 필요한 지 검토 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Cake> cakeList = new ArrayList<>();

    @Embedded
    private Address address;
    private String phoneNumber;
    private String siteUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="reserve_info_id")
    private ReserveInfo reserveInfo;

    /* sns 는 OneToOne 으로 불러오도록 우선 수정
//    * 조회 시 null 체크 필수 */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Naver naver;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Instagram instagram;




    /* 수정 메서드 */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setReserveInfo(ReserveInfo reserveInfo) {
        this.reserveInfo = reserveInfo;
    }
    public void setNaver(Naver naver) {
        this.naver = naver;
    }
    public void setInstagram(Instagram instagram) {
        this.instagram = instagram;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /* 연관 관계 편의 메서드 */
    /* SHOP-CAKES */
    public void addCake(Cake cake) {
        this.cakeList.add(cake);
        cake.setShop(this);
    }
    /* 생성 편의 메서드 */
    /* 최소 정보 입력 조건으로 가게 생성 */
    public static Shop createShop(String shopName, String siteUrl, String phoneNumber, String city, String street, String zipCode) {
        Shop shop = new Shop();
        shop.shopName = shopName;
        shop.setSiteUrl(siteUrl);
        shop.setPhoneNumber(phoneNumber);
        shop.setAddress(Address.createAddress(city, street, zipCode));
        return shop;
    }
    /* 픽업 및 예약 정보와 함께 생성 */
    public static Shop createShop(String shopName, String siteUrl, String phoneNumber,
                                  String city, String street, String zipCode,
                                  Boolean onSiteSaleOnly, Boolean needReservation, Integer needReservationBeforeDay,
                                  LocalTime open, LocalTime close) {
        Shop shop = new Shop();
        shop.shopName = shopName;
        shop.setSiteUrl(siteUrl);
        shop.setPhoneNumber(phoneNumber);
        shop.setAddress(Address.createAddress(city, street, zipCode));
        shop.setReserveInfo(ReserveInfo.create(onSiteSaleOnly, needReservation, needReservationBeforeDay, open, close));
        return shop;
    }
    /* naver sns 정보와 함께 생성 */
    public static Shop createShop(String shopName, String siteUrl, String phoneNumber,
                                  String city, String street, String zipCode,
                                  Boolean onSiteSaleOnly, Boolean needReservation, Integer needReservationBeforeDay,
                                  LocalTime open, LocalTime close,
                                  String naverSnsUrl) {
        Shop shop = new Shop();
        shop.shopName = shopName;
        shop.setSiteUrl(siteUrl);
        shop.setPhoneNumber(phoneNumber);
        shop.setAddress(Address.createAddress(city, street, zipCode));
        shop.setReserveInfo(ReserveInfo.create(onSiteSaleOnly, needReservation, needReservationBeforeDay, open, close));
        shop.setNaver(Naver.create(shop, naverSnsUrl));

        return shop;
    }
    public static Shop createShop(String shopName, String siteUrl, String phoneNumber,
                                  String city, String street, String zipCode,
                                  Boolean onSiteSaleOnly, Boolean needReservation, Integer needReservationBeforeDay,
                                  LocalTime open, LocalTime close,
                                  String naverSnsUrl, String instarUrl) {
        Shop shop = new Shop();
        shop.shopName = shopName;
        shop.setSiteUrl(siteUrl);
        shop.setPhoneNumber(phoneNumber);
        shop.setAddress(Address.createAddress(city, street, zipCode));
        shop.setReserveInfo(ReserveInfo.create(onSiteSaleOnly, needReservation, needReservationBeforeDay, open, close));
        shop.setNaver(Naver.create(shop, naverSnsUrl));
        shop.setInstagram(Instagram.create(shop, instarUrl));
        return shop;
    }


}
