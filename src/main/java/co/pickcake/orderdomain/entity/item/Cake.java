package co.pickcake.orderdomain.entity.item;

import co.pickcake.imagedomain.entity.CakeImages;
import co.pickcake.orderdomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.policies.policy.policy.FileNamePolicy;
import co.pickcake.shopdomain.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CAKE")
public class Cake extends Item {


//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name="uuid2")
//    @Column(name = "item_uuid", columnDefinition = "Binary(16)")
//    private UUID itemUid;

    @Value("${BRAND_NAME_NOT_FOUND:Default}")
    private String brand;

    private String description;




    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cake_images_id")
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
        cake.setStockQuantity(stock);
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
