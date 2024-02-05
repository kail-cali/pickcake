package co.pickcake.shopdomain.entity;

import co.pickcake.common.entity.Address;
import co.pickcake.orderdomain.entity.item.Cake;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Shop {

    @Id @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    private String shopName;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Cake> cakeList = new ArrayList<>();

    @Embedded
    private Address address;

    /* api call */
    private String siteUrl;



    /* 수정 메서드 */

    /* 연관 관계 편의 메서드 */
    /* SHOP-CAKES */
    public void addCake(Cake cake) {
        this.cakeList.add(cake);
        cake.setShop(this);
    }


    /* Dto 로 db 조회용 */
//    public static Shop createShop(ShopInfoDto shopInfo) {
//
//    }

    /* Entity 조회 용 */
    public static Shop createShop(String brandName, String url, String city, String street, String zipCode) {
        Shop shop = new Shop();
        shop.shopName = brandName;
        shop.siteUrl = url;
        shop.address = Address.createAddress(city, street, zipCode);

        return shop;
    }





}
