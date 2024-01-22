package co.pickcake.orderdomain.entity.item;

import co.pickcake.common.entity.ItemDate;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="C")
public class Cake extends Item {


    public void setItemUid(String itemUid) {
        this.itemUid = itemUid;
    }

    private String brand;




    private String itemUid;

    private String description;

    @OneToMany
//    @JoinColumn(name = "category_id")
    private List<CakeCategory> cakeCategoryList = new ArrayList<>();

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
