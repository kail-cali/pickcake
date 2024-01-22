package co.pickcake.orderdomain.entity.item;

import co.pickcake.shopdomain.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CAKE")
public class Cake extends Item {



    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2")
    @Column(columnDefinition = "Binary(16)")
    private UUID itemUid;

    @Value("${BRAND_NAME_NOT_FOUND:Default}")
    private String brand;

    private String description;

    private String imagePath;

    /* TODO SHOP API */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id")
    private Shop shop;


    @OneToMany
    private List<CakeCategory> cakeCategoryList = new ArrayList<>();

    /* 수정 메서드 */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setItemUid(UUID itemUid) {
        this.itemUid = itemUid;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /* 연관관계 편의 메서드 */
    public void addCakeCategory(CakeCategory cakeCategory) {
        cakeCategoryList.add(cakeCategory);
    }

    /* 생성 메서드 */
    public static Cake createCake(String brand, CakeCategory... cakeCategories) {
        Cake cake = new Cake();
        for (CakeCategory cakeCategory: cakeCategories) {
            cake.addCakeCategory(cakeCategory);
        }
        cake.setBrand(brand);
        return cake;
    }
    public static Cake createCake(String brand, String description, String imagePath) {
        Cake cake = new Cake();
        cake.setBrand(brand);
        cake.setDescription(description);
        cake.setImagePath(imagePath);

        return cake;
    }

}
