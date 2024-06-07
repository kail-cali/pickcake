package co.pickcake.reservation.domain.item;

import co.pickcake.image.domain.CakeImages;
import co.pickcake.policies.filename.policy.FileNamePolicy;
import co.pickcake.shop.domain.Shop;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="cake")
public class Cake extends Item {

    private String brand;
    private String description;

    @OneToOne(mappedBy = "cake", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CakeImages cakeImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL)
    private List<EventCakeCategory> cakeCategoryList = new ArrayList<>();

    /* 수정 메서드 */
    public void setDescription(String description) {
        this.description = description;
    }

    /* 연관관계 편의 메서드 */

    /* SEE SHOP-BRAND */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /* SEE SHOP-CAKES */
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void addCakeCategory(EventCakeCategory eventCakeCategory) {
        cakeCategoryList.add(eventCakeCategory);
        eventCakeCategory.setCake(this);
    }
    /* SEE CAKE-CAKEIMAGE */
    public void setCakeImages(CakeImages cakeImages) {
        this.cakeImages = cakeImages;
    }

    /* 생성 메서드 */
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
