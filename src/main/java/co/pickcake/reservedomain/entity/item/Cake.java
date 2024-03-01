package co.pickcake.reservedomain.entity.item;

import co.pickcake.imagedomain.entity.CakeImages;
import co.pickcake.policies.filename.policy.FileNamePolicy;
import co.pickcake.shopdomain.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="cake")
public class Cake extends Item {

    private String brand;    // -> shop 정보랑 같아야 하며 shop 에서 이름만 미리 가져와서 디비에 저장하도록 함
    private String description;

    @OneToOne(mappedBy = "cake", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CakeImages cakeImages;

    /* TODO SHOP API */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL)
    private List<EventCakeCategory> cakeCategoryList = new ArrayList<>();

    /* 수정 메서드 */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* SEE SHOP-CAKES */
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    /* 연관관계 편의 메서드 */
    public void addCakeCategory(EventCakeCategory eventCakeCategory) {
        cakeCategoryList.add(eventCakeCategory);
        eventCakeCategory.setCake(this);
    }
    /* SEE CAKE-CAKEIMAGE */
    public void setCakeImages(CakeImages cakeImages) {
        this.cakeImages = cakeImages;
    }
    /* SEE `GENERATE-DEFAULT-IMAGE-NAME` */
    /* 생성 메서드 */
    public static Cake createCake(String name, String brand, String description,
                                  int price, int stock) {
        Cake cake = new Cake();
        cake.setName(name);
        cake.setPrice(price);
        cake.setBrand(brand);
        cake.setDescription(description);
        return cake;
    }

    public static Cake createCakeWithImage(String name, String brand, String description,
                                  int price, FileNamePolicy fileNamePolicy) {
        Cake cake = new Cake();
        cake.setName(name);
        cake.setPrice(price);
        cake.setBrand(brand);
        cake.setDescription(description);
        CakeImages.createCakeImages(cake, fileNamePolicy);
        return cake;
    }

}
